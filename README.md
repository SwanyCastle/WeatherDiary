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
      <td>Json Simple (Google Code)</td>
      <td>1.1.1</td>
    </tr>
    <tr>
      <td>SpringDoc OpenAPI Starter WebMVC UI</td>
      <td>2.0.2</td>
    </tr>
  </tbody>
</table>

### 📌 Spring 3.x.x 및 Java 17 이상 에서 Springfox 라이브러리는 호환되지 않아서 <br> &nbsp;&nbsp;&nbsp;&nbsp; 호환되는 SpringDoc OpenAPI Starter WebMVC UI 라이브러리 사용
<br>
<br>

# 🗓️ 기능
#### 날씨
  - 날씨 데이터를 매일 새벽 1시에 자동적으로 OpenWeatherMap API 에서 받아와 <br>
    DB에 저장

#### 일기
  - **일기 생성** <br>
    일기를 쓰고 싶은 날짜에 날씨 데이터가 없는 경우에는 OpenWeatherMap API 를 호출해 <br>
    해당 날짜의 날씨 데이터를 받아와 일기 생성
    Request Param 에 맞지 않는 데이터가 들어오면 500 INTERNAL_SERVER_ERROR
    
  - **일기 조회** <br>
    조회하고 싶은 날짜의 일기를 조회 해당하는 날짜에 여러개의 일기가 저장될 수도 있어 List 로 반환
    Request Param 에 맞지 않는 데이터가 들어오면 500 INTERNAL_SERVER_ERROR
    
  - **일기 구간 조회** <br>
    조회하고 싶은 구간의 시작 날짜, 끝 날짜로 일기를 조회
    Request Param 에 맞지 않는 데이터가 들어오면 500 INTERNAL_SERVER_ERROR
    
  - **일기 수정** <br>
    수정하고싶은 날짜의 일기 데이터 수정
    Request Param 에 맞지 않는 데이터가 들어오면 500 INTERNAL_SERVER_ERROR

  - **일기 삭제** <br>
    삭제하고 싶은 날짜의 일기 데이터 삭제
    Request Param 에 맞지 않는 데이터가 들어오면 500 INTERNAL_SERVER_ERROR
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
      <td rowspan="5">일기</td>
      <td>일기 생성</td>
      <td>/diary/create?date={date}</td>
      <td>POST</td>
      <td>200</td>
    </tr>
    <tr>
      <td>일기 조회</td>
      <td>/diary/read?date={date}</td>
      <td>GET</td>
      <td>200</td>
    </tr>
    <tr>
      <td>일기 구간 조회</td>
      <td>/diaries/read?date={date}</td>
      <td>GET</td>
      <td>200</td>
    </tr>
    <tr>
      <td>일기 수정</td>
      <td>/diary/update?date={date}</td>
      <td>PUT</td>
      <td>200</td>
    </tr>
    <tr>
      <td>일기 삭제</td>
      <td>/diary/delete?date={date}</td>
      <td>DELETE</td>
      <td>200</td>
    </tr>
  </tbody>
</table>

<br>
<br>

# Swagger UI
- 날씨 일기 생성 API
<img width="1315" alt="날씨일기전체API" src="https://github.com/user-attachments/assets/29077498-babd-4d48-844e-7e94a75bc65f">
<br>
<br>

- 날씨 일기 조회 API
<img width="1290" alt="날씨일기조회" src="https://github.com/user-attachments/assets/d248570a-832f-4295-a6c3-03d1f9d91feb">
<br>
<br>

- 날씨 일기 구간 조회 API
<img width="1288" alt="날씨일기구간조회" src="https://github.com/user-attachments/assets/e71a37b9-dffb-4937-97d8-d66fe0027fd0">
<br>
<br>

- 날씨 일기 수정 API
<img width="1297" alt="날씨일기수정" src="https://github.com/user-attachments/assets/9a6cbd76-0961-4bd1-b40a-0bf5bbd0621b">
<br>
<br>

- 날씨 일기 삭제 API
<img width="1289" alt="날씨일기삭제" src="https://github.com/user-attachments/assets/47e3d90f-39c2-4acd-a868-eb3b88e17a92">

