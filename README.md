# Team25_Android

### ❗ 테스트 안내

> 테스트 시 관리자 페이지에서 매니저 승인이 가능합니다. <br>
[admin 페이지 주소](https://meditogetherapp.com/admin) <br>
비밀번호: admin (평가 기간 후 변경 예정입니다) <br>
매니저 등록 후 매니저 앱의 프로필 페이지에서 근무 지역과 근무 시간을 설정해야 그에 맞게 이용자 앱에서 검색됩니다.
자세한 앱 사용법은 [테스트 시나리오](https://quickest-asterisk-75d.notion.site/bf50e3dcdb444b298734142ab6bcde29)에서 확인 가능합니다.

<br>

![image](https://github.com/user-attachments/assets/121ff619-5adf-49ca-8128-5d677da23bc6)

> 목차
> - [📌 프로젝트 소개](#프로젝트-소개)
> - [👩‍👩‍👧‍👧 팀원 소개](#팀원-소개)
> - [✏️ 주요 기능](#주요-기능)
> - [🔗 링크 모음](#링크-모음)
> - [📜 ERD](#erd)
> - [📄 API 모아보기](#api-모아보기)
> - [🖥️ 기술 스택](#기술-스택)

<br>

## 프로젝트 소개

### 개발 동기 및 목적

저희 칠전팔기 팀은 **“병원 동행 서비스 매칭 플랫폼”** 을 주제로 선정했습니다. 고령화 사회로 접어들면서 의료 이용에 어려움을 겪는 고령층이 늘어나고 있습니다. 특히 무인 접수기 등 디지털 도구의 도입으로 인해 익숙하지 않은 어르신들은 병원 이용에 불편함을 겪고 있습니다.

이 문제를 해결하기 위해 저희는 **도움이 필요한 환자와 전문 매니저를 매칭**하여 환자가 안전하게 의료 서비스를 이용할 수 있는 플랫폼을 제안합니다. 병원 동행 서비스의 수요는 고령화와 1인 가구 증가로 인해 지속적으로 증가하고 있으며, 병원 동행 매니저 업무는 비교적 낮은 노동 강도로 중장년층의 새로운 직업으로 주목받고 있습니다.

저희 플랫폼은 **환자와 전문 매니저 간의 매칭**을 통해 병원 이용을 지원하며, **실시간 조회 및 리포트 기능**을 통해 자녀들도 안심하고 서비스를 이용할 수 있도록 돕겠습니다.

<br>

### 서비스 소개

> 투명한 서비스로 환자와 병원 동행 매니저를 매칭하다, '메디투게더'

1. ✏️ **로그인 및 회원가입**

    - SNS 간편 로그인 (카카오)으로 회원가입이 가능해요.
    - 로그아웃 및 탈퇴 기능도 제공하고 있어요.

2. 🗂️ 환자의 **프로필 관리**

    - 환자 개인 정보를 입력하고 의료 관련 정보를 관리할 수 있어요 (이름, 나이, 성별, 병력 등).
    - 주치의 정보도 등록해두면 서비스 이용에 도움이 돼요.

3. 📅 **서비스 예약**으로 병원 동행을 미리 준비해요

    - 동행 서비스 예약을 통해 병원 정보와 예약 시간을 쉽게 설정할 수 있어요.
    - 예약 후에도 수정이나 관리가 가능해요.

4. 📍 실시간 동행 현황 알림

    - 동행 서비스 이용 중 실시간으로 환자와 동행자의 위치를 확인할 수 있어요.
    - 보호자나 환자 본인이 어플에서 이동 경로와 위치를 실시간으로 확인할 수 있어요.

5. 📝 **진료 리포트 제공**으로 진료 정보를 한눈에

    - 진료가 끝나면 리포트를 자동으로 생성해줘요 (진료 정보, 처방 내역 등 포함).

<br>

### 개발 기간

2024.09 ~ 2024.11 (카카오 테크 캠퍼스 2기 - STEP3)

## 팀원 소개
|      | **노신**                 | **박정훈**                  | **이창욱**                    |
|:----:|:--------------------------:|:---------------------------:|:-----------------------------:|
|E-Mail| normengdie@pusan.ac.kr     | hoondb@naver.com          | ckddnr5527@gmail.com                |
|GitHub| [Normenghub](https://github.com/Normenghub) | [Pjhn](https://github.com/Pjhn) | [ichanguk](https://github.com/ichanguk) |
|      | <img src="https://github.com/Normenghub.png" width=100px> | <img src="https://github.com/Pjhn.png" width=100px> | <img src="https://github.com/ichanguk.png" width=100px> |

<br>

## 주요 기능
<table>
  <tr>
    <th>기능</th>
    <th>설명</th>
  </tr>
  <tr>
    <td><b>로그인/회원가입</b></td>
    <td>카카오 OAuth 로그인 기능을 제공하며, 카카오 로그인 후 발급된 access token과 refresh token을 통해 JWT 관리를 수행합니다.<br> 
        refresh token의 만료 기간이 7일 이상 남아 있을 경우 자동 로그인도 지원합니다.</td>
  </tr>
  <tr>
    <td><b>예약</b></td>
    <td>사용자 정보를 입력(이름, 생년월일, 성별, 연락처, 출발지, 도착지 등) 받아 개인정보 수집 동의 후 예약을 완료합니다.</td>
  </tr>
  <tr>
    <td><b>실시간 동행 현황</b></td>
    <td>매니저 앱에서 매니저가 입력한 동행 상황 정보(예: 24.11.20 21:04 부산대 병원 도착)를 실시간으로 확인할 수 있습니다.</td>
  </tr>
  <tr>
    <td><b>예약 현황</b></td>
    <td>현재 예약 상태와 지난 예약 내역을 조회할 수 있으며, 진행 중인 예약에 대해서는 취소 요청이 가능합니다.<br>
        또한, 지난 예약에 대한 리포트도 확인할 수 있습니다.</td>
  </tr>
  <tr>
    <td><b>매니저 리스트 조회</b></td>
    <td>예약 시 출발지를 도로명 주소 검색 API를 통해 선택하면, 해당 출발지의 시/도 정보를 저장하여 해당 지역에 맞는 매니저 리스트를 조회할 수 있습니다.</td>
  </tr>
  <tr>
    <td><b>매니저 프로필 조회</b></td>
    <td>매니저 리스트에서 항목을 선택하면 매니저 앱에 등록된 매니저들의 프로필을 조회할 수 있습니다.<br>
        특히, 매니저 프로필 이미지는 Amazon S3 서비스를 통해 다운로드 받아 표시됩니다.</td>
  </tr>
</table>

<br>

## Android 개발 주안점

### 📌 MVVM 패턴 적용

> 각 구성 요소(data, domain, ui)의 역할이 분리되어 코드 수정과 확장이 용이
>
> 비즈니스 로직이 UI와 분리되어 있어 단위 테스트가 간편

### 📌 DataStore 사용

> JWT 토큰 저장시 SharedPreferences에 비해 데이터 무결성과 보안성이 높음
>
> 코루틴을 사용하여 비동기적으로 데이터를 저장하고 불러올 수 있어 효율적

### 📌 Authenticator와 Interceptor 사용

> 토큰이 만료된 경우 refresh 토큰을 사용해 자동으로 갱신된 액세스 토큰을 받아 사용자가 로그아웃하지 않고도 앱 내에서 계속해서 작업을 수행할 수 있게 함.
>
> 모든 API 요청 헤더에 JWT를 자동으로 포함하여 각 요청마다 사용자 인증을 처리

### 📌 DI 사용

> 객체의 생성과 관리를 Hilt가 담당해 원하는 인터페이스를 주입받을 수 있도록 함
>
> 클래스 간의 결합도가 낮아지고 의존 관계를 모듈 단위로 분리하여 코드의 가독성과 유지 보수성 향상

### 📌 Fragment와 ViewModel 결합 사용

> ViewModel을 통해 데이터를 관리하고 Fragment에서는 UI에 집중
>
> StateFlow를 사용하여 데이터가 변경될 때마다 자동으로 UI가 업데이트되도록 구성

<br>

## 링크 모음

|                                               기획                                                |                                                                                          디자인                                                                                           |                                     개발                                     |                                                                      배포                                                                       |
|:-----------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------------------------:|
| [노션](https://www.notion.so/28ef94a6ccd4459280e549b658e3e3ab?v=79a3e414d3ad44a5ac32a21a64aea358) | [와이어프레임](https://www.figma.com/design/MOl5hc5iBjWT8XPTGfsdkm/%EC%99%80%EC%9D%B4%EC%96%B4%ED%94%84%EB%A0%88%EC%9E%84-%EC%B9%A0%EC%A0%84%ED%8C%94%EA%B8%B0?node-id=0-1&node-type=canvas) |    [백엔드 깃허브](https://github.com/kakao-tech-campus-2nd-step3/Team25_BE)     |                                                   [백엔드 배포 주소](https://meditogetherapp.com)                                                    |
|                [최종 기획안](https://www.notion.so/6618de8cc3e14655b28816d3adb80607)                 |                                                                                                                                                                                        | [안드로이드(이용자 앱) 깃허브](https://github.com/kakao-tech-campus-2nd-step3/Team25_Android) |                              [메디투게더 원스토어](https://m.onestore.co.kr/ko-kr/apps/appsDetail.omp?prodId=0000779535)                               |
|                                                                                                 |                                                                                                                                                                                        |          [안드로이드(매니저 앱) 깃허브](https://github.com/kakao-tech-campus-2nd-step3/Team25_Android_2)           |                            [메디투게더 매니저앱 원스토어](https://m.onestore.co.kr/ko-kr/apps/appsDetail.omp?prodId=0000779536)                            |
|                                                                                                 |                                                                                                                                                                                        |    [API 문서](https://www.notion.so/API-5f451248315e4bca9f6de224fa1215a1)    | [Vault 서버 배포 주소](http://ec2-13-125-34-52.ap-northeast-2.compute.amazonaws.com:8200/ui/vault/auth?redirect_to=%2Fvault%2Fdashboard&with=token) |
|                                                                                                 |                                                                                                                                                                                        |     [테스트 시나리오](https://www.notion.so/bf50e3dcdb444b298734142ab6bcde29)     |                                              [메디투게더 관리자 페이지](https://meditogetherapp.com/admin)                                               |
|                                                                                                 |                                                                                                                                                                                        |    [테스트 결과보고서](https://www.notion.so/4b60db4392514af4ba1d8ddd3970f1fa)     |                                                                                                                                               |

<br>

## ERD

![ERD](https://github.com/user-attachments/assets/34927992-2ab6-464b-965c-63f3d359fe6d)

<br>

## API 모아보기

📝 [API 문서](https://quickest-asterisk-75d.notion.site/API-5f451248315e4bca9f6de224fa1215a1)
![API-문서](https://github.com/user-attachments/assets/70cf3abe-67d9-49fc-98ae-8016c8f2a22d)

<br>

## 기술 스택
![image](https://github.com/user-attachments/assets/9e96dfa1-fd50-4f0e-b86e-1803e080ae61)

<br>

## 구현 화면
<img width="691" alt="이용자앱2x" src="https://github.com/user-attachments/assets/d44f4e25-e9ee-40b5-b872-beb3f7d043e9">
