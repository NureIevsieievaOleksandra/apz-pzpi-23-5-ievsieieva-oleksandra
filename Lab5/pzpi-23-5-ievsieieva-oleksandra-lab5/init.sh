#!/bin/bash
set -e

DOCKER_USERNAME="illyaevseevdev"
IMAGE="ktor-app"
TAG="latest"
NAMESPACE="smartlight"
DB_DUMP="docker_localhost-2026_05_27_17_12_39-dump.sql"
DB_USER="light"
DB_NAME="light"

if [ -z "$DOCKER_PAT" ]; then
  echo "DOCKER_PAT not found. export DOCKER_PAT= to ~/.bashrc"
  echo "   Запусти: export DOCKER_PAT='твій_токен'"
  exit 1
fi


if [ ! -f "$DB_DUMP" ]; then
  echo "DB dump not found: $DB_DUMP"
  exit 1
fi

echo "[1/6] Build Docker image..."
docker build -t $IMAGE:$TAG .

echo "[2/6] Docker Hub login..."
echo $DOCKER_PAT | docker login -u $DOCKER_USERNAME --password-stdin

echo "[3/6] Push image to Docker Hub..."
docker tag $IMAGE:$TAG $DOCKER_USERNAME/$IMAGE:$TAG
docker push $DOCKER_USERNAME/$IMAGE:$TAG

echo "[4/6] Apply Kubernetes manifests..."
kubectl apply -f src/main/kotlin/k8s/namespace.yaml
kubectl apply -f src/main/kotlin/k8s/postgres/
kubectl apply -f src/main/kotlin/k8s/ktor/

echo "Wait for PostgreSQL..."
kubectl rollout status deployment/postgres -n $NAMESPACE --timeout=120s

POSTGRES_POD=$(kubectl get pods -n $NAMESPACE -l app=postgres -o jsonpath='{.items[0].metadata.name}')
echo "PostgreSQL pod: $POSTGRES_POD"

echo "Waiting for PostgreSQL to accept connections..."
until kubectl exec -n $NAMESPACE $POSTGRES_POD -- pg_isready -U $DB_USER -d $DB_NAME 2>/dev/null; do
  echo "   Not ready, retrying in 3s..."
  sleep 3
done

echo "[5/6] Restore init dump..."
kubectl exec -n $NAMESPACE -i $POSTGRES_POD -- psql -U $DB_USER -d $DB_NAME < $DB_DUMP
echo "Completed!"

echo "[6/6] Wait for ktor-app..."
kubectl rollout status deployment/ktor-app -n $NAMESPACE --timeout=120s

echo ""
echo "Init completed!"
echo ""
kubectl get pods -n $NAMESPACE