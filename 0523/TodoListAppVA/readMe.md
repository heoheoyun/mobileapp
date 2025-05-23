# ToDoListAppVA

Android용 할 일 관리 애플리케이션입니다. Java와 SQLite를 사용해 구현되었으며, 할 일 추가·수정·삭제, 알람 설정, 상세 보기 기능을 제공합니다.

## 주요 기능

- **할 일 추가**: 입력란에 할 일을 작성하고 알람 시간을 선택합니다.  
- **알람 설정**: Android 12+에서 정확 알람(`SCHEDULE_EXACT_ALARM`) 권한을 확인하고 스케줄링합니다.  
- **알림 전송**: Android 13+에서 알림 권한(`POST_NOTIFICATIONS`)을 요청하고, 설정한 시간에 시스템 알림을 보여줍니다.  
- **수정 및 삭제**: 리스트 항목에서 수정 버튼으로 텍스트를 변경하거나, 삭제 버튼으로 항목을 제거합니다.  
- **상세 보기**: 항목 클릭 시 등록일과 알람 시간이 함께 표시된 상세 화면으로 이동합니다.  
- **데이터 영속성**: SQLite 데이터베이스에 저장하여 앱 재실행 시에도 데이터가 유지됩니다.

## 폴더 구조

```text
app/
├─ src/main/
│  ├─ java/com/example/todolistappva/
│  │  ├─ MainActivity.java          # 메인 화면, RecyclerView 및 알람 스케줄링 처리
│  │  ├─ DetailActivity.java        # 할 일 상세 정보 화면
│  │  ├─ TodoAdapter.java           # RecyclerView 어댑터
│  │  ├─ TodoDatabaseHelper.java    # SQLite DB 헬퍼 클래스
│  │  ├─ TodoItem.java              # 할 일 데이터 모델
│  │  ├─ AlarmReceiver.java         # 알람 브로드캐스트 리시버
│  │  └─ NotificationHelper.java    # 알림 채널 및 Notification 빌더
│  └─ res/
│     ├─ layout/                    # 화면 레이아웃 XML
│     │  ├─ activity_main.xml
│     │  ├─ activity_detail.xml
│     │  └─ item_todo.xml
│     ├─ drawable/                  # 벡터 드로어블 아이콘
│     │  ├─ ic_notification.xml
│     │  ├─ ic_edit.xml
│     │  └─ ic_delete.xml
│     └─ values/
│        ├─ strings.xml            # 문자열 리소스
│        └─ themes.xml             # 앱 테마
└─ AndroidManifest.xml             # 권한, 컴포넌트 등록
