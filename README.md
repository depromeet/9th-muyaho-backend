# 영차

![Generic badge](https://img.shields.io/badge/version-1.0.4-green.svg)
[![codecov](https://codecov.io/gh/depromeet/9th_muyaho_backend/branch/develop/graph/badge.svg?token=2MPw967BnL)](https://codecov.io/gh/depromeet/9th_muyaho_backend)

<img src="/_images/image.png" title="영차" alt="영차" width="22%"></img>

## Introduction
### 주식 및 비트코인 투자 현황 통합 관리 앱

- 보유한 국내 주식, 해외 주식, 비트코인을 통합적으로 관리하고 실시간 현재가를 확인할 수 있어요!
- 보유한 전체 주식과 비트코인에 대한 나의 최종 자산, 수익률 등 투자 현황을 확인할 수 있어요!
- 어제 대비 오늘의 수익금을 확인할 수 있어요!

## Download
[AppStore(IOS)](https://apps.apple.com/kr/app/%EC%98%81%EC%B0%A8/id1571507288)

## Features
### 로그인
- 애플 로그인 (IOS)
- 카카오 로그인 (AOS, IOS)

### 현재 등록할 수 있는 종목 조회
매일 거래할 수 있는 종목을 갱신합니다.
- 국내 주식 (KOSPI, KOSDAQ, KONEX): 약 3000개의 종목 존재.
- 해외주식 (NASDAQ, NYSE): 약 7000개의 종목 존재.
- 비트코인 (업비트): 약 100개의 종목 존재.

### 보유한 주식/비트코인 통합 관리
- 국내 주식, 비트코인: 원화를 통한 보유 주식 등록 및 관리할 수 있습니다..
- 해외 주식: 달러를 통한 보유 주식 등록 및 관리할 수 있습니다.
- 현재가를 원화와 달러로 모두 조회할 수 있습니다. (통화 변환 시 실시간 환율 정보를 이용합니다.)
- 현재가를 실시간으로 가져오고, 수익률을 계산할 수 있습니다.

### 보유한 최종 자산, 수익률 계산
- 모든 주식/비트 코인의 시드 값과 총자산을 원화로 조회할 수 있습니다.
- 모든 주식/비트 코인의 수익률을 계산할 수 있습니다.

### 어제 대비 오늘의 수익금 계산
- 어제 대비 오늘의 수익금 및 수익률 계산할 수 있습니다.


## Installation

### with gradlew

```bash
./gradlew clean build
java -jar muyaho-api/build/libs/muyaho-api.jar 
```

### with docker-compose

```bash
docker-compose up --build
```

## Tech Stacks
### Language & Framework
- Java 11
- Spring Boot 2.3 + Spring MVC + Spring Batch
- Spring Data JPA + QueryDSL 4.3
- ehcache 2.10
- Gradle 6.8
- Junit 5

### Infra
- AWS ECS Fargate
- AWS RDS (MariaDB 10.4), flyway
- Application Load Balancer

### CI/CD
- GitHub Action CI/CD


## Server Architecture

<img src="/_images/muyaho-20210606-1.png" title="영차 서버 구성도" alt="영차 서버 구성도"></img>

### Description

### 메인 서버 및 주식 실시간 조회 서버

- AWS ECS Fargate Service
- Public Subnet에 위치한 ALB를 통해서 분산 배치된 API 서버로 트래픽 분배.
- Public Subnet의 NAT Gateway를 통해서 외부 API 호출.

### 매일 거래 중인 종목 정보 갱신 및 매일 사용자의 투자 현황 계산 (Batch)

- AWS ECS Fargate Tasks + AWS CloudWatch Events
- AWS CloudWatch에서 이벤트 트리거를 통해서 ECS Fargate CronJob 테스크 실행.

### RDS

- AWS RDS MariaDB
- Private Subnet에 위치시켜서 외부로부터의 접근 제한하고, Bastion Server을 두어, 로컬에서 SSH 터널링을 통해서 관리 가능하도록 구성.

### CI/CD

- 특정 이벤트 시 Github Actions workflows가 작동해서 도커 이미지를 빌드 후 AWS ECR에 푸시.
- 푸시된 이미지로 AWS ECS Task를 생성한 후, Service에서 새로운 Task를 배포하도록 구성.
- 배포 전략은 롤링 업데이트.

## Contacts
- 강승호 (will.seungho@gmail.com)
