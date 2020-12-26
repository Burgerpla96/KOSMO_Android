package com.kosmo.notification22_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        //notificationChannel 에 넣을 중요도
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel notificationChannel = new NotificationChannel("CHANNEL_ID","CHANNEL_NAME",importance);
        //notificationChannel 설정
        notificationChannel.enableLights(true);// noti가 왔을때 스마트폰에 빛을 표시할지 설정
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);//noti 도착시 진동 설정
        notificationChannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,100});//진동시간
        //notificationManager와 연결
        notificationManager.createNotificationChannel(notificationChannel);
    }////////////onCreate



    public void basicNotification(View view){
        //NotificationCompat.Builder 객체 생성
        NotificationCompat.Builder builder = createNotificationBuilder();
        //실행할 팬딩 인텐트 설정
        builder.setContentIntent(createPendingIntent());
        //Notification 객체 생성
        Notification notification = builder.build();
        //통지하기
        notificationManager.notify(1,notification);
    }////////basicNotification

    public void extendNotification(View view){
        //NotificationCompat.Builder 객체 생성
        NotificationCompat.Builder builder = createNotificationBuilder();

        //NotificationCompat.InboxStyle 을 이용해 꾸미기
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] contents = {"Java","Spring","Android"};//내용 데이터
        inboxStyle.setBigContentTitle("java과정 커리큘럼");//제목이 된다.
        inboxStyle.setSummaryText("Curriculum");//noti small icon 옆에 표시된다.
        //내용은 addLine()으로 추가
        for(String content: contents) inboxStyle.addLine(content);
        builder.setStyle(inboxStyle);//스타일 적용

        //실행할 팬딩 인텐트 설정
        builder.setContentIntent(createPendingIntent());
        //Notification 객체 생성
        Notification notification = builder.build();
        //통지하기
        notificationManager.notify(2,notification);

    }/////////extendNotification


    //제목과 내용이 커스텀 레이아웃으로 모두 대체되서 보이게 하자
    public void customNotification(View view){
        //NotificationCompat.Builder 객체 생성
        NotificationCompat.Builder builder = createNotificationBuilder();

        //custom 화면 만들기
        //getPackageName();   // package 이름을 반환하는 메서드
        //첫번째 인자는 packageName, 두 번째 인자는 LayoutID
        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.custom_layout);
        //이미지 아이디로 가져오기
        Icon icon = Icon.createWithResource(this, android.R.drawable.ic_menu_save);
        //ImageView, TextView에 이미지,글 저장
        remoteViews.setImageViewIcon(R.id.img,icon);
        remoteViews.setTextViewText(R.id.title,"제목입니다.");
        remoteViews.setTextViewText(R.id.message,"내용입니다\r\n뿜뿌~\r\n짜잔~");
        //remoteView 를 builder에 설정
        builder.setContent(remoteViews);



        //실행할 팬딩 인텐트 설정
        builder.setContentIntent(createPendingIntent());

        //통지하기
        notificationManager.notify(3,builder.build());

    }


    //버튼 이벤트에 공통으로 적용할 메서드: 펜딩 인텐트 생성용
    public PendingIntent createPendingIntent(){
        //1) noti 클릭시 Notification 객체에 등록할 인텐트 생성
        //new Intent()의 두 번째 인자는 noti 클릭시 전환할 화면(즉 액티비티)
        //<activity android:name=".KosmoNotificationActivity"/> 등록하기
        Intent intent = new Intent(this, KosmoNotificationActivity.class);
        //1-2) 인텐트에 메시지 저장
        intent.putExtra("kosmo","코코코 코스모~");
        //추가 데이터만 업데이트 되도록 설정 -- PendingIntent.FLAG_UPDATE_CURRENT
        return PendingIntent.getActivity(this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }//////////////////////createPendingIntent

    
    //버튼 클릭시 Notification 객체를 생성하기위한 builder
    private NotificationCompat.Builder createNotificationBuilder(){
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        //두번째 인자는 채널 아이디
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setLargeIcon(largeIcon)
                .setContentTitle("한국 소프트웨어 인재 개발원")//noti 클릭시 보이는 제목
                .setContentText("가산역에 위치한 학원")
                .setTicker("KOSMO")//상태바에 표시되는 ticker
                .setAutoCancel(true)//noti 클릭시 상태바에서 사라진다.
                .setWhen(System.currentTimeMillis())//노티 전달시간
                .setDefaults(Notification.DEFAULT_VIBRATE);//noti시 알림 방법
        return builder;
    }/////////////createNotificationBuilder




}/////////////class