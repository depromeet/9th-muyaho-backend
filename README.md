# 9th_muyaho_backend (영차)
![Generic badge](https://img.shields.io/badge/version-0.0.3-orange.svg)
## Introduction
### 주식 수익률 계산기, 주린이 눈물

- 자신의 주식 평단을 입력하고 목표 주가에 도달했을 때 수익율을 계산해줘요
- 수익율에 따른 주식밈을 활용해 수익율 구간별로 밈과 상승금액을 보여줘요

### 로그인
- 애플 로그인 (IOS)
- 카카오 로그인 (AOS, IOS)

### 현재 거래중인 종목 코드와 종목명 조회
매일 거래되는 종목 정보 갱신
- 국내주식 (KRX):  약 3000개의 종목
- 해외주식 (NASDAQ): 약 4000개의 종목
- 비트코인 (업비트): 약 100개의 종목

### 보유 주식/코인 관리
- 국내 주식, 비트코인: 원화를 통한 보유 주식 등록 및 관리.
- 해외 주식: 달러를 통한 보유 주식 등록 및 관리

### 보유 주식/코인에 대한 현재가 조회 및 수익률 계산
- 현재가를 원화와 달러로 모두 조회.
- 현재가는 실시간으로 가져온다.
- 통화 변환시 실시간 환율 정보를 이용해서 변환.
- 각 주식/코인 별 실시간 수익률 계산

### 사용자의 모든 주식/코인 통계 정보
- 모든 주식/코인의 총 보유량을 원화로 조회
- 모든 주식/코인의 총 시드값을 원화로 조회 (해외 주식의 경우, 시드 값을 원화로 별도로 보관 - 환율의 변동으로 인한)

### 사용자의 어제 대비 오늘의 수익금 계산 (배치 작업) - TODO
- 어제 대비 오늘의 수익금 계산
- 어제 대비 오늘의 수익률 계산


## Installation

### with gradlew

```bash
./gradlew clean build
java -jar muyaho-api/build/libs/muyaho-api-0.0.1-SNAPSHOT.jar 
```

### with docker-compose

```bash
docker-compose up --build
```

## Tech Stacks
### Language & Framework
- Language: Java 11
- Framework: Spring Boot 2.3
- Web Framework: Spring Web MVC
- Batch: Spring Batch
- Data Access: Spring Data JPA + QueryDSL 4.3
- Build: Gradle 6.8
- Unit/Integration Testing: Junit 5

### Infra (Production Server)
- TODO

### Infra (Development Server)
- AWS ECS Fargate
- AWS RDS (MariaDB 10.4)
- Application Load Balancer

### CI/CD
- GitHub Action CI/CD
