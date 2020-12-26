package com.kosmo.location33;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.os.health.PackageHealthStats;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;


/*
manifest 파일에
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.CAMERA"/>
추가
 */


//실행시 권한 요청:
//https://developer.android.com/training/permissions/requesting.html
public class MainActivity extends AppCompatActivity {

    //현재 앱에서 필요한 권한들
    private String[] permissions = {
            Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION
    };
    //권한 요청시 각 권한을 구분하기 위한 요청 코드값
    public static  final int MY_REQUEST_PERMISSION = 1;
    //사용자 위치정보 관련 API 들
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;//위치 변화 감지
    private Location mLocation;//위도 경도 좌표 들어있다.

    //Provider 활성화 여부관련 변수
    private boolean gpsProvider,networkProvider;
    //최적의 provider
    private String bestProvider;
    private Button btnMyLastPosition;
    //허용이 안된 권한들을 저장할 컬렉션
    private List<String> deniedPermissions = new Vector<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMyLastPosition = findViewById(R.id.btnMyLastPosition);
        //위치 관리자 얻기
        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //LocationManager의 isProviderEnabled()로 프로바이더 활성화 여부
        gpsProvider = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        networkProvider = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Toast.makeText(this,String.format("GPS: %s, NETWORK: %s",gpsProvider,networkProvider)
                ,Toast.LENGTH_LONG).show();//둘다 true
        //위치변화 감지하는 리스너 생성
        mLocationListener = new LocationListener() {
            //위치변화시 호출된다.
            @Override
            public void onLocationChanged(@NonNull Location location) {
                //현재 위도 경도 얻기
                double lat = location.getLatitude();//위도
                double lng = location.getLongitude();//경도
                Log.i("com.kosmo.location",String.format("위도: %s, 경도:%s",lat,lng));
            }

            //29버젼에서 deprecated
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                switch (status){
                    case LocationProvider.OUT_OF_SERVICE:
                        Log.i("com.kosmo.location","서비스 지역이 아닙니다."); break;
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        Log.i("com.kosmo.location","일시적 오류가 있습니다."); break;
                    case LocationProvider.AVAILABLE:
                        Log.i("com.kosmo.location","서비스 사용가능한 지역입니다."); break;
                }
            }

            //provider 활성화시
            @Override
            public void onProviderEnabled(@NonNull String provider) {
                Log.i("com.kosmo.location",provider+"가 활성화 되었습니다.");
            }

            //사용자가 프로바이더를 비활성화 시켰을 때
            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Log.i("com.kosmo.location",provider+"가 비활성화 되었습니다.");
            }
        };///////LocationListener


        //현재 앱이 필요로 하는 모든 권한을 사용자로부터 얻기
        //사용자 권한은 String형 상수로 정의 되어 있다
        //자바코드:Manifest.permission.권한
        //xml : android.permission.권한
        //마쉬멜로우 이후 버전부터 사용자에게 권한 요청보낸다
        //버젼확인
        if(Build.VERSION.SDK_INT >=23){
            requestUserPermissions();
        }


    }///////onCreate

    //사용자에게 권한을 요청하는 메서드(안드로이드 6.0이상부터 추가됨)
    private boolean requestUserPermissions() {
        for(String permission : permissions){
            //권한 있는지 판단
            //권한 있는경우 0, 없으면 -1
            int checkSelfPermission = ActivityCompat.checkSelfPermission(this,permission);
            Log.i("com.kosmo.location",permission+" : "+
                    (checkSelfPermission== PackageManager.PERMISSION_GRANTED ? "권한 존재":"권한 없음"));

            //권한이 없는 경우
            if(checkSelfPermission == PackageManager.PERMISSION_DENIED){
                deniedPermissions.add(permission);
            }
        }//////for
        //권한이 없는게 있다면
        if(deniedPermissions.size()!=0){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setCancelable(false)
                    .setTitle("권한 요청")
                    .setMessage("권한을 허용해야만 앱을 정상적으로 사용할 수 있습니다.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //권한 요청코드 작성  ActivityCompat.requestPermissions() 이용
                            //두번째 인자는 요청할 권한들의 String[]
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    deniedPermissions.toArray(new String[deniedPermissions.size()]),
                                    MY_REQUEST_PERMISSION);
                        }
                    })
                    .setNegativeButton("앱 종료", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
            return false;
        }/////////////권한이 없는 경우
        //onRequestPermissionResult 오버라이딩 하자
        return true;//권한이 있는 경우
    }//////////////requestUserPermissions


    /*
   int requestCode: ActivityCompat.requestPermissions()메소드 호출시 보낸 요청코드
   String[] permissions: 요청한 권한들
   int[] grantResults:권한에 따른 사용자의 deny 혹은  allow결과를 담은 int형 배열
                      배열크기가 0인 경우:deny나 allow둘중의 아무것도 누르지 않는 경우
    */
    //허용하면 grantResults에 0, deny 하면 -1
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("com.kosmo.location","[사용자에게 요청한 권한배열]");
        Log.i("com.kosmo.location", Arrays.toString(permissions));
        Log.i("com.kosmo.location","[사용자의 허용 여부 배열]");
        Log.i("com.kosmo.location",Arrays.toString(grantResults));
        switch (requestCode){
            case MY_REQUEST_PERMISSION:
                if(grantResults.length > 0){//사용자가 allow나 deny를 누른 경우
                    for(int i=0;i<permissions.length;i++){
                        if(grantResults[i]== PackageManager.PERMISSION_GRANTED){//허용한 경우
                            //요청권한과 관련된 기능 및 서비스 활성화
                            if(permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)){
                                btnMyLastPosition.setEnabled(true);
                                Log.i("com.kosmo.location","위치 활성화");
                                //최적의 프로바이더 얻기
                                //true: 활성화된 위치제공자에서 찾기, false는 모든 위치제공자에서 찾기
                                bestProvider = mLocationManager.getBestProvider(getCriteria(),true);
                                Log.i("com.kosmo.location","최적의 위치제공자: "+bestProvider);//gps

                            }
                        }
                        else{//권한요청을 거부한 경우
                            //요청권한과 관련된 기능 및 서비스 비활성화
                            if(permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)){
                                btnMyLastPosition.setEnabled(false);
                                Log.i("com.kosmo.location","위치 비활성화");
                            }
                        }
                    }
                }
        }
    }///////////////onRequestPermissionsResult


    //최적의 Provider(위치 서비스 제공자)를 얻기 위한 기준
    private Criteria getCriteria(){
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setSpeedRequired(false);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);

        return criteria;
    }//////////////getCriteria


    ///위치감지 시작 - onResume()에서 호출해도 됨(requestLocationUpdates()메서드)
    public void startLocation(View view){
        try {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, mLocationListener);
        }catch (SecurityException e){e.printStackTrace();}
    }////////////////////startLocation

    //위치감지 중지 - onPause()에서 호출해도 된다.
    public void stopLocation(View view){
        mLocationManager.removeUpdates(mLocationListener);

    }////////////////////startLocation


    //나의 최근 위치 얻기
    public void getLastLocation(View view){
        try {
            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location != null){
                Log.i("com.kosmo.location",String.format(
                        "최근 나의 위치: 위도는 %s, 경도는 %s",location.getLatitude(),location.getLongitude()));
            }
        }catch (SecurityException e){e.printStackTrace();}
    }////////////////////getLastLocation
}//////////////////class