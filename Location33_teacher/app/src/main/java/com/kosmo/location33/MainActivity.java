package com.kosmo.location33;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

//실행시 권한 요청:
//https://developer.android.com/training/permissions/requesting.html
public class MainActivity extends AppCompatActivity {
    //현재 앱에서 필요한 권한들
    private String[] permissions ={
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    //권한요청시 각 권한을 구분하기 위한 요청코드값
    public static final int MY_REQUEST_PERMISSION=1;
    //사용자 위치 정보 관련 API 들]
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Location mLocation;

    //PROVIDER활성화 여부관련 변수]
    private boolean gpsProvider,networkProvider;
    //최적의 프로바이더]
    private String bestProvider;
    private Button btnMyLastPosition;
    //허용이 안된 권한들을 저장할 컬렉션
    private List<String> deniedPermissions = new Vector<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //위젯 얻기]
        btnMyLastPosition = findViewById(R.id.btnMyLastPosition);
        //위치 관리자 얻기]
        //위치 관리자는 시스템 서비스 이므로 객체를 참조하기 위해 getSystemService()메소드 사용
        mLocationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //LocationManager의 isProviderEnabled()메소드로 프로바이더 활성화여부
        gpsProvider=mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        networkProvider =mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Toast.makeText(this,String.format("GPS:%s, NETWORK:%s",gpsProvider,networkProvider),Toast.LENGTH_SHORT).show();
        //위치 변화가 있을때마다  위치를 감지하는 리스너 객체 생성]
        mLocationListener = new LocationListener() {
            //위치가 변경될때마다 호출됨]
            @Override
            public void onLocationChanged(Location location) {
                //현재 위치의 위도/경도 얻기
                double lat=location.getLatitude();
                double lng=location.getLongitude();
                Log.i("com.kosmo.location",String.format("위도:%s, 경도:%s",lat,lng));
            }
            //프로바이더의 상태가 변할때마다 호출됨.(29에서 deprecated)
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

                switch (status){
                    case LocationProvider.OUT_OF_SERVICE:
                        Log.i("com.kosmo.location","서비스 지역이 아닙니다");break;
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        Log.i("com.kosmo.location","일시적인 통신장애");break;
                    case LocationProvider.AVAILABLE:
                        Log.i("com.kosmo.location","서비스 사용가능한 지역입니다");break;
                }
            }
            //사용자가 프로바이더를 활성화 시켰을때
            @Override
            public void onProviderEnabled(String provider) {
                Log.i("com.kosmo.location",provider+"가 활성화 되었습니다");
            }
            //사용자가 프로바이더를 비 활성화 시켰을때때
            @Override
            public void onProviderDisabled(String provider) {
                Log.i("com.kosmo.location",provider+"가 비활성화 되었습니다");
            }
        };
        //현재 앱이 필요로 하는 모든 권한을 사용자로부터 얻기
        //사용자 권한은 String형 상수로 정의 되어 있다
        //자바코드:Manifest.permission.권한
        //xml : android.permission.권한
        //마쉬멜로우 이후 버전부터 사용자에게 권한 요청보낸다
        if(Build.VERSION.SDK_INT >=23){
            requestUserPermissions();
        }

    }///onCreate
    //사용자에게 권한을 요청하는 메소드(안드로이드 6.0이상부터 추가됨)
    private boolean requestUserPermissions() {
        for(String permission : permissions){
            //권한 획득시 0,없을시 -1
            int checkSelfpermission=ActivityCompat.checkSelfPermission(this,permission);
            Log.i("com.kosmo.location",permission+":"+(checkSelfpermission== PackageManager.PERMISSION_GRANTED ? "권한 있다":"권한 없다" ));
            //권한이 없는 경우
            if(checkSelfpermission == PackageManager.PERMISSION_DENIED){

                deniedPermissions.add(permission);
            }
        }///////////////for
        //권한이 없는게 있다면
        if(deniedPermissions.size() !=0){

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setCancelable(false)
                    .setTitle("권한 요청")
                    .setMessage("권한을 허용해야만 앱을 정상적으로 사용할 수 있습니다")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //사용자에게 권한 요청 코드 작성
                            //두번째 인자:요청할 권한들의 String[]
                            ActivityCompat.requestPermissions(MainActivity.this,deniedPermissions.toArray(new String[deniedPermissions.size()]),MY_REQUEST_PERMISSION);

                        }
                    })
                    .setNegativeButton("앱 종료", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
            return false;//권한이 없는 경우
        }//////////////////

        return true;//모든 권한이 없는 경우
        //※ onRequestPermissionsResult오버라이딩 하자
    }/////////////////requestUserPermissions
    //권한 사용 동의창(시스템에서 띄움)에서 동의(allow) /비동의(deny)를 클릭시 아래 메소드 자동 호출됨.
    /*
    int requestCode: ActivityCompat.requestPermissions()메소드 호출시 보낸 요청코드
    String[] permissions: 요청한 권한들
    int[] grantResults:권한에 따른 사용자의 deny 혹은  allow결과를 담은 int형 배열
                       배열크기가 0인 경우:deny나 allow둘중의 아무것도 누르지
                                           않는 경우
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("com.kosmo.location","[사용자에게 요청한 권한들]");
        Log.i("com.kosmo.location", Arrays.toString(permissions));
        Log.i("com.kosmo.location","[사용자가 선택한 허용여부들]");
        Log.i("com.kosmo.location", Arrays.toString(grantResults));
        switch(requestCode){
            case MY_REQUEST_PERMISSION:
                //사용자가  allow(허용)나 deny를 누른 경우
                if(grantResults.length > 0){
                    for(int i=0; i < permissions.length;i++){
                        if(grantResults[i]==PackageManager.PERMISSION_GRANTED){//허용한 경우

                            if(permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)){
                                //요청권한과 관련된 기능 및 서비스 활성화
                                btnMyLastPosition.setEnabled(true);//활성화
                                //최적의 프로바이더 얻기

                            }
                        }
                        else{//사용자가  deny(거부)를 누른 경우
                            //요청권한과 관련된 기능 및 서비스 비 활성화
                            if(permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)){
                                btnMyLastPosition.setEnabled(false);
                            }
                        }
                    }
                }
        }
    }//////////////////onRequestPermissionsResult

    public void startLocation(View view){

    }/////////////startLocation
    public void stopLocation(View view){

    }/////////////stopLocation
    public void getLastLocation(View view){

    }/////////////getLastLocation
}