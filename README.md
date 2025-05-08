# 🍽️ WaitLess : 대규모 핫플 웨이팅 서비스
<div>
  <img src="https://github.com/user-attachments/assets/3101f745-0639-49d1-8fb9-7fd9aa093b3c" style="height: 450px;">
</div>

## 🗣️ 프로젝트 소개

- 대규모 트래픽에도 안정적인 성능을 유지하고 실시간 예약순번을 보장하는 식당 웨이팅 플랫폼
- 실시간으로 웨이팅 현황을 확인하고 어디서든 간편하게 웨이팅을 등록할 수 있는 시스템

## 🥅 프로젝트 목표

- MSA 기반 서비스 분리 및 **도메인 중심 설계(DDD) + 계층형&헥사고날 아키텍처** 적용
- **Redis** 기반 캐싱 전략 적용 및 성능 최적화 
- **Lua Script + Reddision** 분산 락을 이용한 동시성 제어
- **Kafka** 이벤트 기반 아키텍처 및 트랜잭션 관리: 이벤트 비동기 처리 및 **Saga 패턴**을 적용한 보상 트랜잭션 + **Outbox 패턴**
- **Prometheus + Grafana + Zipkin** 을 활용한 실시간 장애 감지 및 성능 모니터링
- **Resilience4j** 를 적용하여 메시지 유실 방지 및 장애 복구 기능 강화
- **Kubernetes** 기반 무중단 배포로 서비스 가용성 극대화

## 📌 팀원 역할분담
| <img src="https://avatars.githubusercontent.com/u/55836020?v=4" width="120px;" alt=""/> | <img src="https://avatars.githubusercontent.com/u/131944234?v=4" width="120px;" alt=""/> | <img src="https://avatars.githubusercontent.com/u/107541923?v=4" width="120px;" alt=""/> | <img src="https://avatars.githubusercontent.com/u/111412215?v=4" width="120px;" alt=""/> | <img src="https://avatars.githubusercontent.com/u/86669962?v=4" width="120px;" alt=""/> |
|:----------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------:|
|                           [박주희](https://github.com/juhee99)                           |                           [이성민](https://github.com/2sminn)                            |                           [이원희](https://github.com/Leewon2)                           |                           [전인종](https://github.com/jnjongjeon)                           |                           [한지해](https://github.com/hanjihae)                            |
|                                            식당                                            |                                         리뷰, 포인트                                         |                                       메뉴, 메시지, 배포                                        |                                            예약                                            |                                          유저, 쿠폰                                          |

## 🛠️ 기술 스택
**주요 언어**: Java 17  
**빌드 도구**: Gradle 8.10.2
<table>
  <tr>
    <td style="background-color: #e0f2fe;"><strong>Database / Caching</strong></td>
    <td>
      <strong>MariaDB</strong> 10.11<br>
      <strong>Redis</strong> 7.2
    </td>
    <td style="text-align: center;">
      <img src="https://github-production-user-asset-6210df.s3.amazonaws.com/86669962/441075431-374ff578-6a31-49e2-9b8c-a82a093beba4.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250507%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250507T053228Z&X-Amz-Expires=300&X-Amz-Signature=7f7c5fb862e573b6176815efdbd93606d7429519db43a15e4c32332e57ee0808&X-Amz-SignedHeaders=host" width="50"/>
      <img src="https://github.com/user-attachments/assets/2a90d034-1542-47e0-a524-b4febc5f170e" width="50"/> 
    </td>
  </tr>
  <tr>
    <td style="background-color: #f1f5f9;"><strong>Backend</strong></td>
    <td width="250px">
      <strong>Spring Boot</strong> 3.4.4<br>
      <strong>Spring Cloud</strong> 2024.0.1<br>
      <strong>Spring Cloud Eureka</strong><br>
      <strong>Spring Cloud Gateway</strong><br>
      <strong>Spring Cloud OpenFeign</strong><br>
      <strong>Redisson</strong> 3.17.6<br>
      <strong>Swagger</strong> 2.7.0<br>
      <strong>QueryDSL</strong> 5.0.0<br>
    </td>
    <td style="text-align: center;">
      <img src="https://spring.io/img/projects/spring-boot.svg" width="50" />
      <img src="https://docs.spring.io/spring-cloud-gateway/docs/2.2.10.BUILD-SNAPSHOT/reference/htmlsingle/favicon.ico" width="50" />
      <img src="https://avatars.githubusercontent.com/u/16851431?s=200&v=4" width="50"/>
      <img src="https://static1.smartbear.co/swagger/media/assets/swagger_fav.png" width="50" />
      <img src="https://raw.githubusercontent.com/querydsl/querydsl.github.io/refs/heads/master/ico/favicon.ico" width="50" /> 
    </td>
  </tr>
  <tr>
    <td style="background-color: #ecfdf5;"><strong>Cloud / DevOps</strong></td>
    <td>
      <strong>AWS EC2</strong><br>
      <strong>Docker</strong><br>
      <strong>Docker-Compose</strong><br>
      <strong>GitHub Actions</strong><br>
      <strong>Kubernetes</strong><br>   
    </td> 
    <td style="text-align: center;">
      <img src="https://upload.wikimedia.org/wikipedia/commons/9/93/Amazon_Web_Services_Logo.svg" width="50" />
      <img src="https://github.com/user-attachments/assets/6e4ecc9f-2f9e-44cb-8088-8f780665c693" width="50"/>
      <img src="https://github.com/user-attachments/assets/ecc34476-f5be-4275-bd97-fa247dbc6fa2" width="50" />
      <img src="https://github.com/user-attachments/assets/2f9ab1ca-6a02-4dcb-b15a-e4dec9f803c7" width="50" />
      <img src="https://github-production-user-asset-6210df.s3.amazonaws.com/107541923/441079559-80fc6690-7bbf-45e1-b41a-6aa49a205599.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250507%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250507T054937Z&X-Amz-Expires=300&X-Amz-Signature=7081a013bb560e523c91ca01555714e427b413acf27fae9f8249eb327e05993e&X-Amz-SignedHeaders=host" width="100">
    </td>
  </tr>
  <tr>
    <td style="background-color: #fefce8;"><strong>Monitoring / Observability</strong></td>
    <td>
      <strong>Prometheus</strong><br>
      <strong>Grafana</strong><br>
      <strong>Zipkin</strong><br>
    </td>
    <td style="text-align: center;">
      <img src="https://github.com/user-attachments/assets/ea790f84-26fa-47fa-8692-336fe1cb215c" width="50" />
      <img src="https://github.com/user-attachments/assets/ca6ca0bf-dbd0-4259-969c-12fc4c3ba110" width="50" />
      <img src="https://github.com/user-attachments/assets/98dfbfd6-1735-4f9a-a2d0-263939832c14" width="50" />
    </td>
  </tr>
  <tr>
    <td style="background-color: #fef2f2;"><strong>Collaboration</strong></td>
    <td>
      <strong>GitHub</strong><br>
      <strong>Notion</strong><br>
      <strong>Postman</strong><br>
      <strong>Slack</strong>
    </td>
    <td style="text-align: center;">
      <img src="https://github-production-user-asset-6210df.s3.amazonaws.com/86669962/441091016-a881b9fe-5d5d-47d9-890a-bf03e7352c26.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250507%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250507T062840Z&X-Amz-Expires=300&X-Amz-Signature=d8d1938e5055f6b25412f55046eaaa9be15897f265000dbd2ce8fc42c9d17421&X-Amz-SignedHeaders=host" width="50" />
      <img src="https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1570106347/noticon/hx52ypkqqdzjdvd8iaid.svg" width="50"/>
      <img src="https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1566914838/noticon/qlfe77nbcvdscm762prm.png" width="50" />
      <img src="https://github-production-user-asset-6210df.s3.amazonaws.com/86669962/441081145-69cc8054-d151-4339-8819-79500cd4bf9e.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250507%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250507T055434Z&X-Amz-Expires=300&X-Amz-Signature=e31f84ba592c06abca5b3cd81adde3912b93ab11b34c0d02480a5131cb06e85c&X-Amz-SignedHeaders=host" width="50" />
    </td>
  </tr>
  <tr>
    <td style="background-color: #ede9fe;"><strong>Event Streaming</strong></td>
    <td>
      <strong>Apache Kafka</strong>
    </td> 
    <td style="text-align: center;">
      <img src="https://github.com/user-attachments/assets/80ca122c-e8f6-45f0-86aa-56f30eaf8a81" width="50" />
    </td>
  </tr>
  <tr>
    <td style="background-color: #fff7ed;"><strong>Load Testing</strong></td>
    <td>
      <strong>JMeter</strong>
    </td>
    <td style="text-align: center;">
      <img src="https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1666058624/noticon/zppnxgsegyfrhrl42q2p.png" width="50" />
    </td>
  </tr>
</table>

## 📖 인프라 아키텍처
<img src="https://github-production-user-asset-6210df.s3.amazonaws.com/107541923/441082014-95c18511-d9ab-46b9-a4a4-d2c76dc1d43e.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250507%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250507T055708Z&X-Amz-Expires=300&X-Amz-Signature=e663ec6dfe7806914731eb82d0f864d4dd0ad414ea6eb6befa88285ac0e10a64&X-Amz-SignedHeaders=host" >

* 유저 : 브라우저에서 서비스 접속 → 요청이 k8s Cluster로 전달 → Gateway가 Eureka에 등록된 서비스 목록 조회 후 해당 서비스로 라우팅 → 요청을 받은 서버 내부에서 작업 수행
* 개발자 : Github에 Push → Github Actions CI/CD 파이프라인, Docker 이미지 Build & Push → k8s 배포

## ✏️ 이벤트 흐름
* 사용자 플로우 1 ⇒ 회원가입/로그인 → 예약 생성 신청 → 식당/메뉴 확인 → 메뉴 재고 확인 → 예약 성공 → 예약 성공 알람 전송 → 방문 완료 → 리뷰 작성 요청 알람 발송 → 리뷰 작성 성공 → 포인트 발급 성공
* 사용자 플로우 2 ⇒ 회원가입/로그인 → 쿠폰 발급
<img src="https://github-production-user-asset-6210df.s3.amazonaws.com/86669962/441081917-76b43503-d2e7-4859-9177-467803ad5e9b.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250507%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250507T055649Z&X-Amz-Expires=300&X-Amz-Signature=259769a3514df3730a953b62c8854c376ae979c3e1b2af9d1fca917da245926b&X-Amz-SignedHeaders=host">


## 📋 API 문서
**Swagger-UI**: `http://localhost:19091/swagger-ui.html`


## 🖥️ 모니터링 & 관측

- **Prometheus**: `/actuator/prometheus`
- **Grafana**: DashboardID 
- **Zipkin**: 리뷰, 포인트 이벤트 흐름을 TraceID 로 트레이싱


## ⚙️ CI/CD 파이프라인

1. **GitHub Actions** : PR검증 (Test · Lint · Build) & Docker 이미지 Build & Push
2. **k8s 배포** : Self-hosted Runner에서 kubectl 실행
3. **kube-scheduler** : 적절한 worker 노드 선택 후 kube-apiserver에 반환
4. **pod 생성** : containerd가 Dockerhub에서 이미지 pull



## 🛎️ 기술적 의사결정

### 1️⃣ [인증-인가-유저 관리 서비스 나누어 책임 분리](https://github.com/teamsparta-hangry/hangry/wiki/인증%E2%80%90인가%E2%80%90유저-관리-서비스-나누어-책임-분리)

### 2️⃣ [Redis Lua Script를 활용한 동시성 제어](https://github.com/teamsparta-hangry/hangry/wiki/Redis-LuaScript-%EB%8F%84%EC%9E%85-%EC%9D%B4%EC%9C%A0)

### 3️⃣ [보상 트랜잭션 도입 이유](https://github.com/teamsparta-hangry/hangry/wiki/보상-트랜잭션-도입-이유)

### 4️⃣ [포인트 총합/랭킹에 Redis ZSET 사용](https://github.com/teamsparta-hangry/hangry/wiki/포인트-총합-랭킹에-Redis-ZSET-도입-이유)

### 5️⃣ [Kubernetes VS Blue-Green 무중단 배포 전략](https://github.com/teamsparta-hangry/hangry/wiki/Kubernetes-vs-Blue%E2%80%90Green-무중단-배포-전략)


## 🔫 트러블슈팅

### 1️⃣ [Resilience4j를 이용한 메세지 유실 방지](https://github.com/teamsparta-hangry/hangry/wiki/%5B%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85%5D-Resilience4j%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-Slack-%EB%A9%94%EC%8B%9C%EC%A7%80-%EC%9C%A0%EC%8B%A4-%EB%B0%A9%EC%A7%80)

### 2️⃣ [쿠폰 발급시 분산 락으로 동시성 제어](https://github.com/teamsparta-hangry/hangry/wiki/%5B%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85%5D-%EC%BF%A0%ED%8F%B0-%EB%B0%9C%EA%B8%89%EC%8B%9C-%EB%B6%84%EC%82%B0-%EB%9D%BD%EC%9C%BC%EB%A1%9C-%EB%8F%99%EC%8B%9C%EC%84%B1-%EC%A0%9C%EC%96%B4)

### 3️⃣ [단건형 → 배치형 API 전환 + Redis MGET, pipelined](https://github.com/teamsparta-hangry/hangry/wiki/%5B%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85%5D-%EB%8B%A8%EA%B1%B4%ED%98%95-%E2%86%92-%EB%B0%B0%EC%B9%98%ED%98%95-API-%EC%A0%84%ED%99%98---Redis-MGET,-Pipelining)

### 4️⃣ [Kafka 이벤트 로깅 및 DLQ 설정](https://github.com/teamsparta-hangry/hangry/wiki/%5B%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85%5D-Kafka-%EC%9D%B4%EB%B2%A4%ED%8A%B8-%EB%A1%9C%EA%B9%85-%EB%B0%8F-DLQ-%EC%84%A4%EC%A0%95)

### 5️⃣ [Helm을 이용한 Kafka 컨테이너 실행](https://github.com/teamsparta-hangry/hangry/wiki/%5B%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85%5D-helm%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-kafka-%EC%BB%A8%ED%85%8C%EC%9D%B4%EB%84%88-%EC%8B%A4%ED%96%89)

