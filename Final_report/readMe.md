# 📱 학습 도우미 앱 (LearningApp)

> **2025년 1학기 모바일 앱 개발 기말 프로젝트**  
> 개발자: 허윤  
> 개발환경: Android Studio (Java 기반, Kotlin DSL 사용)  
> SDK 버전: `minSdk 30`, `targetSdk 35`  
> 데이터 저장: SQLite 로컬 데이터베이스 사용

---

## ✅ 주요 기능 요약

| 기능 구분           | 설명 |
|--------------------|------|
| 학습 항목 등록      | 제목, 설명, 즐겨찾기 여부 입력 후 저장 |
| 학습 목록 조회      | 저장된 학습 항목 전체 조회 (RecyclerView) |
| 즐겨찾기 관리       | 항목을 탭하면 즐겨찾기 추가/해제 가능 |
| 학습 항목 삭제       | 항목을 **롱클릭**하면 삭제 여부 확인 후 제거 |
| 즐겨찾기 전용 보기    | 즐겨찾기로 등록한 항목만 필터링하여 보여줌 |
| 퀴즈 기능           | 설명을 보고 제목을 맞추는 OX 퀴즈 형태로 출제 |
| 통계 보기           | 등록된 항목 수, 즐겨찾기 수, 최근 등록일 표시 |

---

## 🧩 앱 화면 구성

| 화면 이름            | 레이아웃 파일              | 설명 |
|---------------------|----------------------------|------|
| 메인 화면            | `activity_main.xml`        | 기능 진입 버튼 5개 배치 |
| 학습 등록 화면       | `activity_add_learning.xml`| 제목, 설명, 즐겨찾기 체크 입력 |
| 학습 목록 화면       | `activity_learning_list.xml` | 전체 학습 항목 조회 및 즐겨찾기/삭제 |
| 즐겨찾기 화면        | `activity_favorite.xml`    | 즐겨찾기 항목만 필터링 |
| 퀴즈 화면            | `activity_quiz.xml`        | 설명 → 제목 맞추기 퀴즈 (OX 버튼) |
| 통계 화면            | `activity_stats.xml`       | 항목 수, 즐겨찾기 수, 마지막 등록일 표시 |

---

## 🛠 주요 기술 스택

- **Java (Android SDK 기반)**
- **SQLite + DBHelper 직접 구현**
- **RecyclerView & CardView**
- **ViewBinding 활성화**
- **AlertDialog (삭제 확인)**
- **Toast 메시지 안내**
- **테마, 색상, 문자열 리소스 분리 관리**

---

## 📁 폴더 구조 예시

LearningApp/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/example/learningapp/
│   │       │       ├── MainActivity.java                 # 메인 메뉴
│   │       │       ├── AddLearningActivity.java          # 학습 항목 추가
│   │       │       ├── LearningListActivity.java         # 전체 학습 항목 목록
│   │       │       ├── FavoriteActivity.java             # 즐겨찾기 목록
│   │       │       ├── QuizActivity.java                 # 퀴즈 기능
│   │       │       ├── StatsActivity.java                # 통계 화면
│   │       │       ├── LearningAdapter.java              # RecyclerView 어댑터
│   │       │       ├── DBHelper.java                     # SQLite 데이터베이스 관리
│   │       │       └── model/
│   │       │           └── LearningItem.java             # 데이터 모델 클래스
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   ├── activity_main.xml
│   │       │   │   ├── activity_add_learning.xml
│   │       │   │   ├── activity_learning_list.xml
│   │       │   │   ├── activity_favorite.xml
│   │       │   │   ├── activity_quiz.xml
│   │       │   │   ├── activity_stats.xml
│   │       │   │   └── item_learning.xml                 # RecyclerView 항목 레이아웃
│   │       │   ├── values/
│   │       │   │   ├── strings.xml                       # 다국어 문자열
│   │       │   │   ├── colors.xml                        # 색상 리소스
│   │       │   │   ├── dimens.xml                        # 크기 리소스
│   │       │   │   └── themes.xml                        # 테마 스타일
│   │       │   └── drawable/
│   │       │       └── (아이콘, 버튼 이미지 등)
│   │       └── AndroidManifest.xml
│   └── build.gradle (모듈 단위)
├── build.gradle (프로젝트 단위)
└── README.md

---

## 🎯 개발 후기 및 학습 내용

- SQLite 로컬 DB를 직접 설계하고, CRUD 로직을 Java로 구현하며 로컬 데이터 흐름을 이해하게 됨
- RecyclerView & Adapter 패턴 학습 → 효율적인 목록 처리 및 클릭 이벤트 구현 가능
- AlertDialog 및 Toast 등 사용자 인터페이스 피드백 요소를 적극 활용
- ViewBinding, 리소스 분리(strings/colors/dimens 등)를 통해 유지보수 가능한 앱 설계 구조 체험
- 간단한 로컬 퀴즈 기능 구현을 통해 사용자 입력 기반 콘텐츠 처리 실습

---

## 🔚 마무리

본 앱은 사용자가 직접 학습 데이터를 입력하고 즐겨찾기 및 퀴즈, 통계를 통해 학습을 복습할 수 있도록 설계되었습니다.  
기초적인 CRUD 구현과 UI 구성, 로컬 DB 활용의 전반을 다뤘으며,  
향후 Firebase 연동, 검색 기능, 다크모드 지원, 푸시 알림 등을 추가할 수 있습니다.

