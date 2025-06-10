# 🎟️ CouponSite
**선착순 쿠폰 발급 시스템**

---

## 📌 Background
네고왕 선착순 쿠폰 이벤트는 **한정 수량**의 쿠폰을 먼저 신청한 사용자에게 제공하는 이벤트입니다.

---

## ✅ Requirements

- 📆 이벤트 기간 내에만 발급 가능  
  _예: `2023-11-03 13:00 ~ 2023-11-04 13:00`_
- 👤 사용자당 1회만 발급 가능
- 🎯 최대 발급 수량 설정 가능

---

## 🏗 Architecture

### ⚙️ 비동기 쿠폰 발급 요청 처리 구조
1. 유저 요청 → API Server
2. API Server → Redis에 발급 요청 저장
3. 발급 Server가 Redis에서 큐를 Pull
4. 발급 처리 후 MySQL에 트랜잭션 저장

---

## 🛠 Tech Stack

### ☁ Infra
- AWS EC2
- AWS RDS
- AWS Elastic Cache

### 🖥 Server
- Java 17
- Spring Boot 3.1
- Spring MVC
- JPA, QueryDSL

### 💾 Database
- MySQL
- Redis
- H2 (테스트용)

### 📈 Monitoring
- AWS CloudWatch
- Spring Actuator
- Prometheus
- Grafana

### 🧪 Etc
- Locust (부하 테스트)
- Gradle
- Docker

---

## 🌟 Main Features

- ✅ 쿠폰 발급 검증
    - ⏰ 발급 기한 확인
    - 📦 발급 수량 제한
    - 🚫 중복 발급 차단

- 🧮 Redis 기반 수량 관리
    - `Redis Set`으로 재고 관리
    - `Redis List` 큐 기반 비동기 발급 처리
    - 스케줄러를 이용한 `Queue Polling`

---

## 🚀 실행 방법 (옵션)

```bash
# 1. Docker 실행
$ docker-compose up

# 2. Gradle 빌드
$ ./gradlew build
