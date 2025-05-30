# 📚 학습 도우미 앱

> 모바일 앱 개발 기말 과제  
> 202241408 허윤

## 🧩 개발 사유

기존의 노트 필기나 메모 앱은 학습에 최적화되어 있지 않아 효율적인 복습 및 점검이 어려웠습니다.  
따라서 스스로 입력한 정보를 바탕으로 **퀴즈를 자동 생성**하고, **학습 통계까지 제공**하는 앱이 있다면  
학습 효율을 크게 높일 수 있다고 판단했습니다.

본 앱은 사용자의 자발적인 학습과 목표 입력을 중심으로  
**자기주도 학습을 유도하고 동기를 부여**하는 것을 목표로 기획되었습니다.

---

## 📱 앱 개요

**간단한 학습을 돕는 안드로이드 앱**입니다.

### ✅ 주요 기능
- 오늘의 학습 목표 표시
- 사용자 입력 기반 학습 정리 (용어, 예제 등)
- 퀴즈 제공
- 즐겨찾기 기능
- 학습 통계 제공

---

## 🧭 주요 기능 및 화면 구성

| 화면 이름             | 설명                                               |
|----------------------|----------------------------------------------------|
| `MainActivity`       | 전체 메뉴 진입 화면                                |
| `AddLearningActivity`| 학습 등록 입력 폼                                   |
| `LearningListActivity` | 등록된 학습 목록 표시                            |
| `QuizActivity`       | 사용자가 입력한 예제로 퀴즈 제공                   |
| `FavoriteActivity`   | 즐겨찾기한 학습 내용 보기                           |
| `StatsActivity`      | 학습 통계를 시각적으로 제공                        |

---

## 🔧 사용 기술 및 저장 방식

- **Android Platform**: 앱 전반 개발
- **SQLite**: 데이터 저장
- **Intent**: 화면 간 전환

---

## 🗂️ 프로젝트 구조 예시

```plaintext
📁 app/
 ┣ 📁 src/
 ┃ ┣ 📁 main/
 ┃ ┃ ┣ 📁 java/
 ┃ ┃ ┃ ┗ com.example.studyhelper/
 ┃ ┃ ┃   ┣ MainActivity.java
 ┃ ┃ ┃   ┣ AddLearningActivity.java
 ┃ ┃ ┃   ┣ LearningListActivity.java
 ┃ ┃ ┃   ┣ QuizActivity.java
 ┃ ┃ ┃   ┣ FavoriteActivity.java
 ┃ ┃ ┃   ┗ StatsActivity.java
 ┃ ┃ ┗ 📁 res/
 ┃ ┃   ┗ 📁 layout/
 ┃ ┃     ┣ activity_main.xml
 ┃ ┃     ┣ activity_add_learning.xml
 ┃ ┃     ┣ activity_learning_list.xml
 ┃ ┃     ┣ activity_quiz.xml
 ┃ ┃     ┣ activity_favorite.xml
 ┃ ┃     ┗ activity_stats.xml
 ┗ README.md
