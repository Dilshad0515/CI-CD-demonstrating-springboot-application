# Combined Spring Boot + React Monorepo

**Purpose:** A template monorepo that combines a Spring Boot backend and a React frontend with optimized CI/CD, multi-stage Dockerfiles, and modern frontend design (Tailwind).  
**Notes:** Replace placeholders (YOUR_NAME, YOUR_EMAIL, YOUR_REGISTRY, YOUR_SECRETS) with your own information. Add project-specific docs where indicated.

## Structure
```
/ (repo root)
├─ backend/            # Spring Boot app (Maven)
├─ frontend/           # React app (Vite + React + Tailwind)
├─ .github/workflows/  # CI/CD workflows
├─ Dockerfile.backend
├─ Dockerfile.frontend
├─ README.md
├─ CONTRIBUTING.md
```

## Quick start (local)
### Backend
```bash
cd backend
./mvnw spring-boot:run
# or build: mvn -B clean package
```

### Frontend
```bash
cd frontend
npm install
npm run dev
```

## CI / CD
- GitHub Actions workflows included: `.github/workflows/ci-cd-root.yml`
- Customize secrets: `DOCKER_REGISTRY`, `DOCKER_USERNAME`, `DOCKER_PASSWORD`, etc.

## Personalization
- Update README author details.
- Replace `app.properties` placeholders with real values.
- Add your tests and production configuration.

**Author:** YOUR_NAME (replace in files)


## CI/CD - Secrets & Deployment

To enable pushing images to GitHub Container Registry (GHCR), add repository secrets:
- `GHCR_TOKEN` — Personal Access Token with `write:packages` and `read:packages` scopes.

To enable Docker Hub pushes:
- `DOCKERHUB_USERNAME`
- `DOCKERHUB_TOKEN`

Frontend environment for Vite:
- Set `VITE_API_BASE_URL` in GitHub Actions or in `.env` for production builds.

## Local development

Start both apps via Docker Compose:
```bash
docker-compose up --build
# Backend on http://localhost:8080
# Frontend on http://localhost:3000
```

## What I added for you
- OpenAPI (springdoc) dependency and config. Visit `/swagger-ui.html` after starting backend.
- Basic unit test for backend and a React testing placeholder.
- Docker Compose for local integration testing.
- CI steps to optionally push images to GHCR/Docker Hub when secrets are configured.

Replace placeholders (YOUR_NAME, YOUR_EMAIL) in files before publishing publicly.


## Deploy backend to Render (free)
1. Sign up at https://render.com (free tier works).
2. Create a new Web Service and connect your GitHub repository.
3. Choose Docker as the build type and point to `Dockerfile.backend`.
4. Set environment variables:
   - `JWT_SECRET` (secure random string)
   - (Optional) Postgres connection variables if you use Postgres
5. Render will build and deploy automatically on push to `main`.

This approach gives you full CI/CD experience with Render's free tier.

## Create a new repo with clean history (recommended)
To publish this project under a **new GitHub repository** that shows only your commits (clean history), run:

```bash
# from the local repo root (after you've customized files and replaced placeholders)
rm -rf .git
git init
git add .
git commit -m "Initial commit - personalized by YOUR_NAME"
# create repo on GitHub and then:
git branch -M main
git remote add origin git@github.com:YOUR_GITHUB_USERNAME/NEW_REPO_NAME.git
git push -u origin main
```

This will create a repo where the history starts with your commit only. Replace `YOUR_GITHUB_USERNAME` and `NEW_REPO_NAME` accordingly.



## Automated Render deployment via GitHub Actions

The repo includes a workflow `.github/workflows/deploy-to-render.yml` that triggers a Render deploy on pushes to `main`. To enable it:

1. Create a Render service for the backend (Web Service) and note its `SERVICE ID` from the Render dashboard (Settings -> Service ID).
2. Create a Render API key: Account -> API Keys -> New API Key (keep it secret).
3. Add the following repository secrets in GitHub:
   - `RENDER_SERVICE_ID` — your service id for the backend
   - `RENDER_API_KEY` — your Render API key

The workflow will call Render's API to create a deploy for the specified service. Render will then build and deploy the service using the `Dockerfile.backend` in repo root.

Note: You can also connect Render directly to GitHub (recommended) to enable auto-deploys without API tokens.
