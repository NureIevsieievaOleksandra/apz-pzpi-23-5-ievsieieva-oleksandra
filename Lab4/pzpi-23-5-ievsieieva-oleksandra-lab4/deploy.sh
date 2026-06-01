#!/bin/bash
# =============================================================
# SmartLight Kubernetes Deploy Script
# =============================================================

set -e

NAMESPACE="smartlight"
IMAGE_NAME="smartlight:latest"

echo "======================================"
echo " SmartLight K8s Deploy"
echo "======================================"

# 1. Збудувати Docker image
echo ""
echo "[1/5] Збираємо Docker image..."
docker build -t $IMAGE_NAME .
echo "✅ Image зібрано: $IMAGE_NAME"

# 2. Створити ConfigMap з init.sql
echo ""
echo "[2/5] Створюємо ConfigMap з init.sql..."
kubectl create configmap postgres-init-sql \
  --from-file=init.sql=./db/init.sql \
  --namespace=$NAMESPACE \
  --dry-run=client -o yaml | kubectl apply -f -
echo "✅ ConfigMap створено"

# 3. Застосувати всі маніфести
echo ""
echo "[3/5] Застосовуємо k8s маніфести..."
kubectl apply -f k8s/00-namespace.yaml
kubectl apply -f k8s/01-postgres-secret.yaml
kubectl apply -f k8s/02-postgres.yaml
kubectl apply -f k8s/03-backend.yaml
kubectl apply -f k8s/04-hpa.yaml
echo "✅ Маніфести застосовано"

# 4. Чекаємо поки PostgreSQL буде готовий
echo ""
echo "[4/5] Чекаємо готовності PostgreSQL..."
kubectl wait --for=condition=ready pod \
  -l app=postgres \
  -n $NAMESPACE \
  --timeout=120s
echo "✅ PostgreSQL готовий"

# 5. Чекаємо поки бекенд буде готовий
echo ""
echo "[5/5] Чекаємо готовності бекенду..."
kubectl wait --for=condition=ready pod \
  -l app=smartlight-backend \
  -n $NAMESPACE \
  --timeout=120s
echo "✅ Бекенд готовий"

echo ""
echo "======================================"
echo " Деплой завершено!"
echo "======================================"
echo ""
echo "API доступне на: http://localhost:80"
echo ""
echo "Корисні команди:"
echo "  kubectl get pods -n $NAMESPACE"
echo "  kubectl get hpa -n $NAMESPACE"
echo "  kubectl scale deployment smartlight-backend --replicas=3 -n $NAMESPACE"
echo "  kubectl logs -f -l app=smartlight-backend -n $NAMESPACE"
