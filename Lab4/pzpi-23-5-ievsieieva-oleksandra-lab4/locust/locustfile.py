from locust import HttpUser, task, between
import random

class SmartLightUser(HttpUser):
    wait_time = between(0.5, 1.5)
    token = None

    def on_start(self):
        """Логін при старті кожного віртуального юзера"""
        # Спочатку реєструємось (може вже існувати — ігноруємо помилку)
        self.client.post(
            "/signUp",
            json={"username": "loadtest", "password": "test1234"},
            name="[Auth] SignUp"
        )
        # Логін
        response = self.client.post(
            "/signIn",
            json={"username": "loadtest", "password": "test1234"},
            name="[Auth] SignIn"
        )
        if response.status_code == 200:
            self.token = response.json().get("token")

    def headers(self):
        return {"Authorization": f"Bearer {self.token}"} if self.token else {}

    # --- Найчастіші: читання (вага 5) ---

    @task(5)
    def get_all_lamps(self):
        self.client.get("/lamp", headers=self.headers(), name="[Lamp] GET all")

    @task(5)
    def get_all_groups(self):
        self.client.get("/group", headers=self.headers(), name="[Group] GET all")

    # --- Середні: читання по ID (вага 3) ---

    @task(3)
    def get_lamp_by_id(self):
        lamp_id = random.randint(1, 5)
        self.client.get(
            f"/lamp/getById?lampId={lamp_id}",
            headers=self.headers(),
            name="[Lamp] GET by ID"
        )

    @task(3)
    def get_group_by_id(self):
        group_id = random.randint(1, 3)
        self.client.get(
            f"/group/getById?groupId={group_id}",
            headers=self.headers(),
            name="[Group] GET by ID"
        )

    # --- Рідкі: аналітика (важкий запит, вага 1) ---

    @task(1)
    def get_analytics(self):
        self.client.get("/analytics", headers=self.headers(), name="[Analytics] GET")
