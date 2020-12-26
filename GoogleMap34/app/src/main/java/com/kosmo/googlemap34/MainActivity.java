package com.kosmo.googlemap34;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

/*
    Geocoder클래스]

    순방향 지오코딩(Forward Geocoding):주소정보를 위도/경도로 변환
                                     getFromLocationName(String 주소명,int 반환활 주소 최대개수,
                                     double 박스영역의 좌측 아래위도,
                                     double 박스영역의 좌측 아래경도,
                                     double 박스영역의 우측 상단위도,
                                     double 박스영역의 우측 상단경도
                                     )사용  -- 잘 사용하지 않음
                                     혹은
                                     getFromLocationName(String 주소명,int 반환활 주소 최대개수)사용

     역방향 지오코딩(Reverse Geocoding):위도/경도를 주소정보로 변환
                                     getFromLocation(double 위도,double 경도,int 반환활 주소 최대개수)사용
                                     최대객수는 1~5개 권장


    지오코딩에 대한 정보는 구글 데이타베이스에 저장되어 잇음.그래서 매너페스트 파일에 반드시 인터넷 권한 추가
    즉 인터넷을 통해 정보를 조회하기때문에 조회시간이 오래걸릴 수 있다.
    그러므로 지오코딩 관련처리는 동기적으로 처리하기보다는 비동기적으로 처리하여
    사용자 화면이 멈추지 않도록 해야한다(서비스나 스레드 사용)


    Address클래스의 주요 메소드]
    getAddressLine(int index):주소 정보를 반환.
	주소정보는 여러 라인으로 저장되어 있으며 ,
	index는 0부터시작
    getMaxAddresLineIndex():주소정보를 표현하는 라인 개수 반환.
	            없다면 -1반환.getAddresLine(int index)와 함께 사용
    getPostalCode():우편번호 반환
    getLatitude():위도반환
    getLongitude():경도 반환

    ※구글 맵 애플리리케이션과는 달리
      지오코딩 조회는 만족할 만한 수준은 아니다

*/


//API 키 : AIzaSyC7QdrShMPB7200u6BAVmTe5DwFbLLKVME

/*
최초 앱 실행시
onCreate()->onStart()->onResume()->onMapReady() invoked
최초 허용 버튼 클릭시
onRequestPermissionsResult()->onResume()->onMapReady()
거부 버튼 클릭시
onRequestPermissionsResult()->finish()
 */
//FragmentActivity 상속으로 바꾸기
//deprecated된 getMap()대신 getMapAsync(OnMapRaedyCallBack) 사용시
//OnMapReadyCallback를 implements한다.
//onMapReady(GoogleMap)을 오버라이딩
public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private EditText editLatitude;
    private EditText editLongitude;
    private EditText editAddrname;

    //권한요철시 구분하기 위한 요청 코드
    private static final int MY_REQUEST_PERMISSION_LOCATION =1;
    //구글 지도 표시용 구글맵
    private GoogleMap map;
    //주소명으로 위치 찾을 때, 찾은 주소들의 주소명/위도/경도를 저장할 컬렉션
    List<Map> searchLatLng = new Vector<Map>();
    //위치서비스를사용하기 위한 변수
    private LocationManager locationManager;
    private LocationListener locationListener;
    //현재 앱에서 필요한 권한들
    private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
    //허용이 안도니 권한들을 저장할 컬렉션
    List<String> denyPermissions = new Vector<String>();

    private SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        preferences = getSharedPreferences("checkDeny",MODE_PRIVATE);
        //LocationManger 생성
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //리스너 생성
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                showCurrentPosition(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }
            @Override
            public void onProviderEnabled(@NonNull String provider) {  }
            @Override
            public void onProviderDisabled(@NonNull String provider) { }
        };
        //3) 권한 요청하기
        if(Build.VERSION.SDK_INT>=23){//버젼 확인, 23버젼 이후부터 보안강화로 권한 요청필요
            boolean isCheck = preferences.getBoolean("AGAIN",false);
            if(isCheck){//다시 묻지 않음 체크시 - 권한 요청창이 다시 뜨지 않는다.
                new AlertDialog.Builder(this)
                        .setTitle("앱권한 설정")
                        .setMessage("권한을 설정해야 앱을 사용하실 수 있습니다.\r\n설정하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //권한 설정화면으로 이동시키기(화면 전환)
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",getPackageName(),null);
                                intent.setData(uri);
                                startActivity(intent);
                                //다시 false로 초기화
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("AGAIN",false);
                                editor.commit();

                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();
            }///////if(isCheck)
            else//권한 요청 보내기
                requestUserPermissions();
        }//////if(SDK_INT)
    }//////////////onCreate





    private boolean requestUserPermissions(){
        for(String permission : permissions){
            //권한 있는지 판단
            //권한 있는경우 0, 없으면 -1
            int checkSelfPermission = ActivityCompat.checkSelfPermission(this,permission);
            Log.i("com.kosmo.location",permission+" : "+
                    (checkSelfPermission== PackageManager.PERMISSION_GRANTED ? "권한 존재":"권한 없음"));

            //권한이 없는 경우
            if(checkSelfPermission == PackageManager.PERMISSION_DENIED){
                denyPermissions.add(permission);
            }
        }//////for
        //권한이 없는게 있다면
        if(denyPermissions.size()!=0){
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
                                    denyPermissions.toArray(new String[denyPermissions.size()]),
                                    MY_REQUEST_PERMISSION_LOCATION);
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
    }//////////////////requestUserPermissions


    //사용자가 deny 혹은 allow클릭했을때 자동으로 호출되는 콜백 메소드
    //이전에 거부(다시 묻지 않음)를 클릭후 다시 앱을 실행해도 아래 메소드 자동호출됨
    //단,모두 허용한 경우에는 앱을 다시 실행해도 아래는 호출되지 않음
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_REQUEST_PERMISSION_LOCATION:
                if(grantResults.length>0){//사용자가 allow, deny를 불렀을 경우
                    for(int i=0; i< permissions.length ;i++){
                        if(grantResults[i]==PackageManager.PERMISSION_GRANTED){//허용

                        }
                        else{//거절
                            //boolean shouldShowRequestPermissionRationale(context,권한명)
                            //사용자가 이전에 권한 요청을 거부(deny)한 이력이 있는 경우에 true반환.
                            //다시 앱 실행시 권한 요청 대화창에는 '다시 묻지 않기' 표시됨.
                            //사용자가 '다시 묻지 않기'를 클릭하면 이후에 앱이  ActivityCompat.requestPermissions()메서드를
                            //호출해도 권한 요청 대화창이 뜨지 않는다
                            //단,onRequestPermissionsResult()콜백함수는 호출된다.
                           if(!ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                                //취소한적이 없는 경우
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("AGAIN",true);
                                editor.commit();
                            }
                            Toast.makeText(this,"권한을 허용해야만 앱을 사용할 수 있어요\r\n앱을 종료합니다.",1).show();
                            finish();
                        }
                    }//////////for

                }//////////if -누른지 확인
        }

    }//////////////////////////////////onRequestPermissionsResult



    //위치가 변할때마다 해당 위도/경도로 카메라를 이동후 마커표시하기
    private void showCurrentPosition(Location location){
        //현재 위치를 이용해 LatLng 객체 생성
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        //카메라 중심을 현재 위치로 이동
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
        //이동한 현재위치에 마커 표시하기
        addMarker(location);

    }///////////////////showCurrentPosition


    private void addMarker(Location location){
        //1)MarkerOptions객체 생성
        MarkerOptions options = new MarkerOptions();
        //2) 마커 위치 설정
        options.position(new LatLng(location.getLatitude(),location.getLongitude()));
        //마커 제목 표시 , 마커 글씨, 움직일 수 있게, icon 설정
        options.title("내가 찾는 주소");
        options.snippet(editAddrname.getText().toString());
        options.draggable(true);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.c));
        //기존 마커 지우기
        map.clear();
        //3) GoogleMap 객체의 addMarker() 로 마커 표시
        map.addMarker(options);

    }///////////////addMarker



    //버튼에 이벤트 처리
    //위도와 경도로 위치 찾기
    public void moveLocationByLatLng(View view) {
        //사용자 입력 위도 경도 입력값 얻기
        double lat = Double.parseDouble(editLatitude.getText().toString());
        double lng = Double.parseDouble(editLongitude.getText().toString());
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(lat);
        location.setLongitude(lng);
        showCurrentPosition(location);
    }//////////////////////////moveLocationByLatLng


    public void moveLocationByAddress(View view) {
        //컬렉션 초기화
        searchLatLng.clear();
        //주소명으로  위치 찾기 메서드
        getLocationByAddress();
        Log.i("com.kosmo.location",searchLatLng.toString());//주소로 찾는지 확인
        //찾은 주소목록을 대화상자로 보여주기
        //주소명만 가지고 배열로 변환
        String [] addressNames = new String[searchLatLng.size()];
        for(int i =0; i<searchLatLng.size();i++){
            addressNames[i]=searchLatLng.get(i).get("address").toString();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_menu_compass).setCancelable(false);
        if(addressNames.length>0){//찾은 주소가 있는 경우
            builder.setTitle("주소를 선택하세요")
                    .setSingleChoiceItems(addressNames, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            double lat = Double.parseDouble(searchLatLng.get(which).get("lat").toString());
                            double lng = Double.parseDouble(searchLatLng.get(which).get("lng").toString());
                            Location loc = new Location(LocationManager.GPS_PROVIDER);
                            loc.setLatitude(lat);
                            loc.setLongitude(lng);
                            showCurrentPosition(loc);
                        }
                    }).setPositiveButton("확인",null)
                    .setNegativeButton("취소",null).show();
            return;
        }
        //찾은 주소가 없는 경우
        builder.setTitle(editAddrname.getText()+"로(으로) 검색결과")
                .setMessage("찾는 주소가 없습니다.")
                .setPositiveButton("확인",null).show();

    }//////////////////////////moveLocationByAddress

    private void getLocationByAddress(){
        //Geocoder생성]
        Geocoder geocoder = new Geocoder(this, Locale.KOREA);
        //사용자 입력 주소 얻기]
        String address = editAddrname.getText().toString().trim();
        //반환활 주소 최대개수]
        int maxResult = 5;
        List<Address> addresses = new Vector<Address>();
        try{
            addresses = geocoder.getFromLocationName(address,maxResult);
            Log.i("com.kosmo.location",addresses.toString());//입력값으로 주소를 찾는다.
            for(int i=0;i<addresses.size();i++){
                Map map = new HashMap();
                map.put("lat",addresses.get(i).getLatitude());
                map.put("lng",addresses.get(i).getLongitude());
                map.put("address",addresses.get(i).getAddressLine(i));
                searchLatLng.add(map);
            }
        }
        catch (IOException e){e.printStackTrace();}
    }



    //위치 서비스 시작
    @Override
    protected void onResume() {
        super.onResume();
        //구글맵 얻기 위한 getMapAsync() 호출
        ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        try {
            if(map !=null){
                map.setMyLocationEnabled(true);//내위치 활성화
            }
        }catch(SecurityException e){e.printStackTrace();}
        //위치 변화시마다 감지하기 위해서 로케이션 매니저에 리스너 등록 설정하기
        requestLocation();


    }////////////onResume

    //내 현재위치 파악을 위해 로케이션 매니저에 리스너 등록
    private void requestLocation(){
        if(locationManager != null){
            try {
                //위치 서비스 시작
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, locationListener);
            }catch(SecurityException e){e.printStackTrace();}
        }
    }/////////////requestLocation


    //위치 서비스 중지
    @Override
    protected void onPause() {
        super.onPause();
        //내 위치 표시기능 비활성화
        try {
            if(map!=null){
                map.setMyLocationEnabled(false);
            }
        }catch (SecurityException e){e.printStackTrace();}

        if(locationManager != null && locationListener!= null){
            locationManager.removeUpdates(locationListener);
        }
    }///////////onPause


    //OnMapReadyCallback구현시 아래 메소드 오버라이딩
    // onResume()메소드 안의 getMapAsync(this)호출을 통해서
    //GoogleMap 을 얻게 되면 아래 메소드가 자동으로 호출된다
    //2]onMapReady 오버라이딩

    //맵을 준비하는 메서드
    //onResume 다음에 호출된다.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //1) GoogleMap 객체 얻기
        map = googleMap;
        //1-1]자바코드로 특정위치로 카메라 이동시키기
        //37.4796155, 126.8798342
        //현재 나의 위치의 위도/경도 로 카메라 이동]-실제 폰으로 테스트
        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location != null){
                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                //지도 유형
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        }catch(SecurityException e){e.printStackTrace();}
    }/////////////onMapReady





    private void initView() {
        editLatitude = (EditText) findViewById(R.id.edit_latitude);
        editLongitude = (EditText) findViewById(R.id.edit_longitude);
        editAddrname = (EditText) findViewById(R.id.edit_addrname);
    }
}///////////class