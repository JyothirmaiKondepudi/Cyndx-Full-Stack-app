# Cyndx-Full-Stack-app


AWS-Powered Form Management System -Backend Development Challenge


This repo contains the source code to build a full stack application with React frontend and Flask backend along with AWS Postgres SQL as its core. Below are the details on how to run it on local and host on AWS.

This application is a form -management service where user can enter feild like- full name, age, address, email id, preferred contact, and phone number. Entered form submissions will be displayed in a table in the bottom. 

# Tech Stack:

1. Frontend: React 

2. Backend: Python-Falsk

3. Database: Amazon RDS PostgreSQL

4. CI/CD: GitHub Actions (build, test, deploy)

5. Monitoring: CloudWatch 

      
# Local Development: 
1. Clone the repo:
   
       git clone https://github.com/JyothirmaiKondepudi/Cyndx-Full-Stack-app.git
   
       cd Cyndx-Full-Stack-app
   
3. Frontend :
   
        cd frontend
           
        npm ci
           
        npm start
   
5. Backend :
   
       cd backend
   
       python -m venv venv
   
       source venv/bin/activate
   
       pip install -r requirements.txt
   
       flask run 
   
7. Database :
   
   Configure a local PostgreSQL instance and update your connection URI in environment or config.


 # CI/CD Pipeline

1. Your GitHub Actions workflow automates:
   
     Push & PR on main: run lint, unit tests (pytest, ESLint if present), build frontend

2. Merge to main: build React → sync to S3 → invalidate CloudFront cache
   
    build and push Docker image to ECR → deploy to Lambda → smoke-test via API Gateway

The below is architecture of this application:
![from-management architecture](https://github.com/user-attachments/assets/2df41fa9-7866-4f52-bc0b-2034c74d9289)
