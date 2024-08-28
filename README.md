# ⛅️ 날씨 일기 프로젝트
  > 날씨 일기 프로젝트는 오늘 하루 날씨 정보를 OpenWeatherMap API 에서 받아와 DB에 저장하고 <br>
    하루 날씨와 하루 일기를 같이 작성해 저장하는 프로젝트
<br>
<br>

# 🛠️ 사용 기술
<table>
  <thead>
    <th>Name</th>
    <th>Version</th>
  </thead>
  <tbody>
    <tr>
      <td>Spring Boot</td>
      <td>3.3.3</td>
    </tr>
    <tr>
      <td>Gradle</td>
      <td>8.8</td>
    </tr>
    <tr>
      <td>Java</td>
      <td>17</td>
    </tr>
    <tr>
      <td>MySQL</td>
      <td>9.0.1</td>
    </tr>    
    <tr>
      <td>Spring Data JPA</td>
      <td>3.3.3</td>
    </tr>  
    <tr>
      <td>Redis</td>
      <td>7.4.0</td>
    </tr>
    <tr>
      <td>Redisson</td>
      <td>3.32.0</td>
    </tr>
  </tbody>
</table>

### 📌 Spring 3.x.x 및 Java 17 이상 에서 호환되는 Embedded Redis 라이브러리가 없어 Docker 로 Redis 서버 띄워서 진행
<br>
<br>

# 🗓️ 기능
#### 사용자
  - 사용자 CRUD 는 따로 생성하지 않고 샘플로 Spring Application 이 실행 될때 <br>
    H2 데이터 베이스에 사용자 1, 2, 3을 자동으로 저장

#### 계좌
  - **계좌 생성** <br>
    사용자가 없는 경우 <br>
    사용자가 있지만 해당 사용자의 계좌가 이미 10개인 경우, 위와 같은 경우에 **실패응답**을 반환 <br>
    *(하나의 사용자당 최대 생성할 수 있는 계좌 10개)* <br>
    그 외의 경우에는 **성공응답**을 반환 
    
  - **계좌 해지** <br>
    사용자 또는 계좌가 없는 경우 <br>
    사용자의 정보와 계좌의 소유주 정보가 다른 경우 <br>
    계좌가 이미 해지 상태인 경우 <br>
    해당 계좌에 잔액이 남아 있는 경우, 위와 같은 경우에 **실패응답**을 반환<br>
    그 외의 경우에는 **성공응답**을 반환
    
  - **계좌 확인** <br>
    사용자가 없는 경우에 **실패응답**을 반환하고 그 외의 경우에 **성공응답**을 반환

#### 거래
  - 거래 생성 (잔액 사용)
    사용자가 없는경우 <br>
    사용자 정보와 계좌 소유주가 다른 경우 <br>
    계좌가 이미 해지되어 있는 경우 <br>
    거래 금액이 계좌의 잔액 보다 큰 경우 <br>
    거래 금액이 너무 작거나 큰 경우, 위와 같은 경우에 **실패응답**을 반환<br>
    그외의 경우에는 **성공응답**을 반환
    
  - 거래 취소 (잔액 사용 취소)
    삭제하고 싶은 거래 id 에 해당하는 거래가 없는 경우 <br>
    거래 금액과 거래 취소 금액이 다른 경우 <br>
    거래일로부터 1년이 넘은 거래는 취소가 불가능해서 위와 같은 경우 **실패응답**을 반환 <br>
    그 외의 경우에는 **성공응답**을 반환
    
  - 거래 확인
    조회하고 싶은 거래 id에 해당하는 거래가 없는경우 **실패응답**을 반환하고 그 외의 경우 **성공응답**을 반환
<br>
<br>

# API 스펙
<table>
  <thead>
    <th>분류</th>
    <th>기능</th>
    <th>URI</th>
    <th>Method</th>
    <th>Status Code</th>
  </thead>
  <tbody>
    <tr>
      <td rowspan="3">계좌</td>
      <td>계좌 생성</td>
      <td>/account</td>
      <td>POST</td>
      <td>200</td>
    </tr>
    <tr>
      <td>계좌 삭제</td>
      <td>/account</td>
      <td>DELETE</td>
      <td>200</td>
    </tr>
    <tr>
      <td>계좌 확인</td>
      <td>/account?user_id={userId}</td>
      <td>GET</td>
      <td>200</td>
    </tr>
    <tr>
      <td rowspan="3">거래</td>
      <td>거래 생성</td>
      <td>/transaction/use</td>
      <td>POST</td>
      <td>200</td>
    </tr>
    <tr>
      <td>거래 취소</td>
      <td>/transaction/cancel</td>
      <td>POST</td>
      <td>200</td>
    </tr>
    <tr>
      <td>거래 확인</td>
      <td>/transaction/{transactionId}</td>
      <td>GET</td>
      <td>200</td>
    </tr>
  </tbody>
</table>
