// MainActivity.java
package com.example.aicontextmenu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mainLayout;
    private TextView infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.main_layout);
        infoText = findViewById(R.id.info_text);

        // ContextMenu를 등록할 뷰 지정
        registerForContextMenu(mainLayout);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // 메뉴 제목 설정
        menu.setHeaderTitle("배경색 선택");

        // 메뉴 아이템 추가
        menu.add(0, 1, 0, "빨간색");
        menu.add(0, 2, 0, "녹색");
        menu.add(0, 3, 0, "파란색");
        menu.add(0, 4, 0, "노란색");
        menu.add(0, 5, 0, "흰색");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        // 선택된 메뉴 아이템에 따라 배경색 변경
        switch (item.getItemId()) {
            case 1: // 빨간색
                mainLayout.setBackgroundColor(Color.RED);
                infoText.setText("배경색: 빨간색");
                Toast.makeText(this, "빨간색으로 변경되었습니다", Toast.LENGTH_SHORT).show();
                return true;
            case 2: // 녹색
                mainLayout.setBackgroundColor(Color.GREEN);
                infoText.setText("배경색: 녹색");
                Toast.makeText(this, "녹색으로 변경되었습니다", Toast.LENGTH_SHORT).show();
                return true;
            case 3: // 파란색
                mainLayout.setBackgroundColor(Color.BLUE);
                infoText.setText("배경색: 파란색");
                Toast.makeText(this, "파란색으로 변경되었습니다", Toast.LENGTH_SHORT).show();
                return true;
            case 4: // 노란색
                mainLayout.setBackgroundColor(Color.YELLOW);
                infoText.setText("배경색: 노란색");
                Toast.makeText(this, "노란색으로 변경되었습니다", Toast.LENGTH_SHORT).show();
                return true;
            case 5: // 흰색
                mainLayout.setBackgroundColor(Color.WHITE);
                infoText.setText("배경색: 흰색");
                Toast.makeText(this, "흰색으로 변경되었습니다", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}