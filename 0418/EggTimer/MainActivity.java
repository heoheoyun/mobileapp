package com.example.eggtimernew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button startButton;
    private MediaPlayer mediaPlayer;
    private boolean isTimerRunning = false;
    private CountDownTimer countDownTimer;
    private AlertDialog extendDialog;

    String[] requestPermission = {"android.permission.POST_NOTIFICATIONS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화
        mEditText = findViewById(R.id.edit);
        startButton = findViewById(R.id.button);

        // 알림 채널 생성 및 권한 요청
        createNotificationChannel();
        ActivityCompat.requestPermissions(this, requestPermission, 1000);

        // 소리 재생을 위한 MediaPlayer 초기화 - 시스템 기본 알림음 사용
        mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_NOTIFICATION_URI);

        // 연장 다이얼로그 생성
        createExtendDialog();
    }

    private void createExtendDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("추가 시간");
        builder.setMessage("계란을 10초 더 익히시겠습니까?");
        builder.setPositiveButton("예", (dialog, which) -> {
            // 10초 더 타이머 추가
            extendTimer();
        });
        builder.setNegativeButton("아니오", (dialog, which) -> finishTimer());
        builder.setCancelable(false); // 뒤로가기 버튼으로 다이얼로그를 취소할 수 없게 설정
        extendDialog = builder.create();
    }

    @Override
    protected void onDestroy() {
        // 리소스 정리
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (extendDialog != null && extendDialog.isShowing()) {
            extendDialog.dismiss();
        }
        super.onDestroy();
    }

    String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "계란 타이머 알림",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationChannel.setDescription("계란 타이머 완료 알림");
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void sendNotification() {
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        // 알림이 클릭되면 앱으로 돌아옴
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("계란 타이머")
                .setContentText("계란 삶기가 완료되었습니다.")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

    // 시간을 MM:SS 형식으로 변환하는 메소드
    private String formatTime(long seconds) {
        int minutes = (int) (seconds / 60);
        int remainingSeconds = (int) (seconds % 60);
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, remainingSeconds);
    }

    // 소리를 재생하는 메소드
    private void playSound() {
        if (mediaPlayer != null) {
            // 이미 재생 중이면 처음부터 다시 재생
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                try {
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    // 오류 발생 시 새로운 MediaPlayer 생성
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_NOTIFICATION_URI);
                }
            }
            mediaPlayer.start();
        } else {
            // mediaPlayer가 없는 경우 새로 생성
            mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_NOTIFICATION_URI);
            mediaPlayer.start();
        }
    }

    // 타이머 연장 메소드
    private void extendTimer() {
        long milliseconds = 10 * 1000L;

        countDownTimer = new CountDownTimer(milliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsUntilFinished = millisUntilFinished / 1000;
                mEditText.setText(formatTime(secondsUntilFinished));

                // 매 초마다 알림음 재생 (연장된 10초는 중요하므로)
                playSound();
            }

            @Override
            public void onFinish() {
                // 추가 시간 종료 후 완전히 종료
                finishTimer();
            }
        };

        countDownTimer.start();
        isTimerRunning = true;
        Toast.makeText(this, 10 + "초 연장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    // 타이머 완전 종료 메소드
    @SuppressLint("SetTextI18n")
    private void finishTimer() {
        mEditText.setText("완료!");
        playSound(); // 완료 시 소리 재생
        sendNotification(); // 알림 보내기

        // 버튼 텍스트 원래대로
        startButton.setText("계란 삶기 시작");
        isTimerRunning = false;

        // 3초 후에 기본 시간으로 복원
        new android.os.Handler().postDelayed(
                () -> mEditText.setText("01:00"),
                3000
        );
    }

    @SuppressLint("SetTextI18n")
    public void startTimer(View view) {
        // 이미 타이머가 실행 중이면 취소하고 새로운 타이머 시작
        if (isTimerRunning && countDownTimer != null) {
            countDownTimer.cancel();
            startButton.setText("계란 삶기 시작");
            isTimerRunning = false;
            return;
        }

        try {
            String timeText = mEditText.getText().toString();
            // 입력 형식 검증 (MM:SS)
            if (!timeText.matches("\\d{2}:\\d{2}")) {
                Toast.makeText(this, "시간을 MM:SS 형식으로 입력해주세요. (예: 01:30)", Toast.LENGTH_SHORT).show();
                return;
            }

            int min = Integer.parseInt(timeText.substring(0, 2));
            int sec = Integer.parseInt(timeText.substring(3, 5));
            final long totalTimeInSeconds = (long) min * 60 + sec;
            final long totalTimeInMillis = totalTimeInSeconds * 1000;

            // 버튼 텍스트 변경
            startButton.setText("타이머 중지");

            countDownTimer = new CountDownTimer(totalTimeInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long secondsUntilFinished = millisUntilFinished / 1000;
                    mEditText.setText(formatTime(secondsUntilFinished));

                    // 10초마다 소리 재생 (10으로 나누어 떨어지는 초일 때)
                    if (secondsUntilFinished % 10 == 0 && secondsUntilFinished > 0) {
                        playSound();
                        Toast.makeText(MainActivity.this,
                                "남은 시간: " + formatTime(secondsUntilFinished),
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFinish() {
                    // 타이머 완료 시 연장 여부 묻기
                    if (!isFinishing() && !extendDialog.isShowing()) {
                        playSound(); // 완료 알림음
                        extendDialog.show();
                    }
                }
            };

            countDownTimer.start();
            isTimerRunning = true;
            Toast.makeText(this, "계란 삶기가 시작되었습니다.", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "올바른 형식(MM:SS)으로 시간을 입력해주세요.", Toast.LENGTH_SHORT).show();
            mEditText.setText("01:00");
        }
    }
}