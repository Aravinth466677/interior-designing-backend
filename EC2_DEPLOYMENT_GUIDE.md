# 🚀 AWS EC2 Deployment Guide (Region: `ap-south-1`)

This guide provides step-by-step instructions to deploy the **Life Designer Backend** to your running AWS EC2 instance in **Mumbai (`ap-south-1`)**.

---

## Step 1: Configure AWS EC2 Security Group (Inbound Rules)

Before connecting to your EC2 instance, ensure its **Security Group** allows the following inbound ports:

1. Go to your AWS EC2 Console: [ap-south-1 EC2 Instances](https://ap-south-1.console.aws.amazon.com/ec2/home?region=ap-south-1#Instances:instanceState=running).
2. Click on your **Instance ID** -> Select the **Security** tab -> Click on your **Security Group**.
3. Click **Edit Inbound Rules** and add the following rules:

| Type | Protocol | Port Range | Source | Description |
| :--- | :--- | :--- | :--- | :--- |
| **SSH** | TCP | `22` | `0.0.0.0/0` (or your IP) | For SSH access |
| **Custom TCP** | TCP | `8080` | `0.0.0.0/0` | Spring Boot API & Swagger UI |
| **HTTP** | TCP | `80` | `0.0.0.0/0` | Standard Web Traffic |

---

## Step 2: Transfer Your Project Code to EC2

### Option A: Using Git (Recommended)
If your repository is on GitHub / GitLab:

1. **Connect to your EC2 instance via SSH:**
   ```bash
   ssh -i /path/to/your-key.pem ubuntu@<YOUR-EC2-PUBLIC-IP>
   ```
   *(Note: Use `ec2-user` instead of `ubuntu` if using Amazon Linux).*

2. **Clone your project on EC2:**
   ```bash
   git clone https://github.com/Aravinth466677/interior-designing-backend.git
   cd interior-designing-backend
   ```

---

### Option B: Uploading Files directly using SCP / Git Bash

If uploading directly from your local machine:
```powershell
scp -i "C:\path\to\your-key.pem" -r "d:\Aravind\*" ubuntu@<YOUR-EC2-PUBLIC-IP>:~/app/
```

---

## Step 3: Run the Automated Deployment Script

Once inside the project directory on your EC2 instance:

1. Make the deployment script executable:
   ```bash
   chmod +x deploy-ec2.sh
   ```

2. Run the deployment script:
   ```bash
   ./deploy-ec2.sh
   ```

The script will automatically:
- Install Docker, Docker Compose, and Java 21.
- Build the Spring Boot executable `.jar`.
- Start both the **PostgreSQL 16** database and **Spring Boot Backend** containers.

---

## Step 4: Verify Deployment

Once the script completes, test your live EC2 deployment in your browser:

- **Root API Health:** `http://<YOUR-EC2-PUBLIC-IP>:8080/`
- **Swagger Interactive API Documentation:** `http://<YOUR-EC2-PUBLIC-IP>:8080/swagger-ui/index.html`

---

## 🛠️ Useful Management Commands on EC2

- **View Live Application Logs:**
  ```bash
  docker compose logs -f backend-app
  ```

- **View Database Logs:**
  ```bash
  docker compose logs -f postgres-db
  ```

- **Restart Services:**
  ```bash
  docker compose restart
  ```

- **Stop Services:**
  ```bash
  docker compose down
  ```
