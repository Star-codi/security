# 🛡️ DevSecOps Pipeline

A production-ready DevSecOps stack integrating **Jenkins**, **SonarQube**, **OWASP ZAP**, **Prometheus**, and **Grafana** — all containerised with Docker Compose.

---

## 📐 Architecture

```
Developer Push
      │
      ▼
  Jenkins CI/CD Pipeline
      │
      ├─► Build & Unit Tests
      │
      ├─► SAST ──────────► SonarQube (code quality + security)
      │         Quality Gate (blocks on failure)
      │
      ├─► SCA ────────────► OWASP Dependency Check (CVEs in deps)
      │
      ├─► Container Scan ─► Trivy (Docker image vulnerabilities)
      │
      ├─► Deploy to Staging
      │
      ├─► DAST ──────────► OWASP ZAP (live app scanning)
      │
      └─► Push to Registry (main branch only)
            │
            ▼
      Prometheus ◄──── scrapes Jenkins, SonarQube, ZAP metrics
            │
            ▼
        Grafana ──────► DevSecOps Dashboard + OWASP Alerts
```

---

## 🚀 Quick Start

### Prerequisites
- Docker ≥ 24 & Docker Compose v2
- 8 GB RAM (SonarQube is hungry)
- Set kernel param: `sysctl -w vm.max_map_count=524288`

### 1. Clone & configure
```bash
git clone https://github.com/your-org/devsecops-stack.git
cd devsecops-stack
cp .env.example .env        # fill in tokens/passwords
```

### 2. Start the stack
```bash
docker compose up -d
```

### 3. Access the services

| Service        | URL                        | Default Credentials |
|----------------|----------------------------|---------------------|
| Jenkins        | http://localhost:8080      | admin / admin       |
| SonarQube      | http://localhost:9000      | admin / admin       |
| Grafana        | http://localhost:3000      | admin / admin       |
| Prometheus     | http://localhost:9090      | —                   |
| OWASP ZAP      | http://localhost:8090      | API key: zap-api-key|
| Sample App     | http://localhost:8888      | —                   |

### 4. First-time setup

**SonarQube**
1. Login → Administration → Security → Generate a token
2. Copy token into Jenkins credentials (`sonar-token`)

**Jenkins**
1. Install plugins: SonarQube Scanner, OWASP Dependency-Check, Prometheus Metrics, Configuration as Code
2. Create a pipeline job pointing to `jenkins/Jenkinsfile`

**Grafana**
1. Datasource is auto-provisioned (Prometheus)
2. Import dashboard IDs: `9964` (Jenkins), `11835` (SonarQube)

---

## 📁 Project Structure

```
devsecops/
├── docker-compose.yml          # Full stack definition
├── sonar-project.properties    # SonarQube config
├── jenkins/
│   ├── Jenkinsfile             # Declarative pipeline
│   └── casc.yaml              # Jenkins Config as Code
├── prometheus/
│   ├── prometheus.yml          # Scrape configs
│   └── owasp_alerts.yml       # Security alert rules
└── grafana/
    └── provisioning/
        ├── datasources/        # Auto-provisioned datasources
        └── dashboards/         # Auto-provisioned dashboards
```

---

## 🔒 Security Gates

| Gate               | Tool                    | Blocks Pipeline? |
|--------------------|-------------------------|-----------------|
| Code Quality       | SonarQube Quality Gate  | ✅ Yes           |
| Known CVEs (deps)  | OWASP Dependency Check  | ✅ Yes           |
| Image vulns        | Trivy                   | ✅ Yes (HIGH+)   |
| Live app vulns     | OWASP ZAP               | ✅ Yes (High+)   |

---

## 📊 Prometheus Alerts

| Alert                     | OWASP Category | Severity |
|---------------------------|----------------|----------|
| HighVulnerabilityCount    | A06            | Critical |
| CriticalSecurityHotspot   | A05            | Warning  |
| ZAPHighRiskAlerts         | A03            | Critical |
| SonarQualityGateFailed    | —              | Critical |
| JenkinsBuildSecurityFailed| —              | Warning  |

---

## 🛑 Tear Down

```bash
docker compose down -v   # removes volumes too
```
