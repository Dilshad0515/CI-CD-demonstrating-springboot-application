# Full Stack CI/CD Pipeline: Spring Boot + React + GitHub Actions + Render

This repository demonstrates a complete end-to-end CI/CD pipeline for a full-stack application.  
The backend is deployed as a containerized service on Render, and the frontend is deployed to GitHub Pages.

The project is structured as a monorepo and includes automated builds, testing, and deployment workflows.

## 1. Features

- Spring Boot backend with JWT authentication
- React frontend built using Vite and Tailwind CSS
- Automated CI/CD using GitHub Actions
- Backend deployment through Render Web Service
- Frontend deployment to GitHub Pages
- Dockerized backend using production-ready Dockerfile
- Live URLs for instant testing

## 2. Repository Layout

backend/ Spring Boot API
frontend/ React + Vite UI
.github/workflows/ CI/CD pipelines
Dockerfile.backend Backend build for Render


## 3. Live Deployments

Frontend  
https://dilshad0515.github.io/CI-CD-demonstrating-springboot-application/

Backend  
Hosted on Render  
(Accessible through the API URL configured in the frontend)

## 4. Local Development Instructions

### Backend

cd backend
mvn spring-boot:run


### Frontend

cd frontend
npm install
npm run dev


## 5. CI/CD Workflows

### Frontend Workflow
- Installs Node dependencies
- Builds frontend using Vite
- Deploys output to `gh-pages` branch
- GitHub Pages serves static files

### Backend Workflow
- Triggered on each push to main
- Calls Render API to start a new deploy
- Render builds using Dockerfile.backend
- Service auto-updates

## 6. Required GitHub Secrets

RENDER_SERVICE_ID (from Render service dashboard)
RENDER_API_KEY (from Render account settings)
JWT_SECRET (backend token signing secret)


## 7. Technologies Used

Backend  
Spring Boot, Spring Security, JWT, Spring Data JPA, PostgreSQL, Maven

Frontend  
React, Vite, Tailwind CSS, Axios, React Router

DevOps  
GitHub Actions, GitHub Pages, Render, Docker

## 8. Author  
Dilshad Shaik
