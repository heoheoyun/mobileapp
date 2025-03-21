# 모바일 앱 프로그래밍 두 번째 수업
## 위젯, 컨테이너
## hello world 프로그래밍
# 화면 설계

## 1. 뷰그룹 생성

뷰그룹은 여러 뷰를 포함하고 관리하는 레이아웃 요소입니다. Android에서는 `LinearLayout`, `RelativeLayout`, `ConstraintLayout` 등 다양한 뷰그룹을 사용할 수 있습니다.

예를 들어, `LinearLayout`을 사용하여 세로로 배치된 뷰를 만드는 방법은 다음과 같습니다:

```xml
<!-- res/layout/activity_main.xml -->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">
    
    <!-- 여기에 필요한 뷰를 추가할 것입니다 -->
</LinearLayout>
