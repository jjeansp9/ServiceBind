package com.jspstudio.ex63servicebind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyService myService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_start).setOnClickListener(view -> clickStart());
        findViewById(R.id.btn_pause).setOnClickListener(view -> clickPause());
        findViewById(R.id.btn_stop).setOnClickListener(view -> clickStop());

    }

    // 액티비티가 화면에 보여질 때 자동 실행되는 라이프사이클 콜백메소드
    @Override
    protected void onResume() {
        super.onResume();

        if(myService==null){
            // MyService를 시작하기
            Intent intent= new Intent(this, MyService.class); // 해당코드 입력하면 MyService.java에 onStartCommand() 실행
            startService(intent);

            // MyService와 연결(Bind)
            bindService(intent, connection, 0); // 해당코드 입력시 MyService.java에 onBind() 실행

        }

    }

    // MyService와 연결되는 통로 객체 생성(멤버변수)
    ServiceConnection connection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // 두번째 파라미터 iBinder : MyService로부터 통로를 통해 넘어온 파견직원
            MyService.MyBinder myBinder= (MyService.MyBinder) iBinder;
            myService= myBinder.getMyServiceAddress(); // MyService 객체의 참조값을 얻어옴

            Toast.makeText(myService, "서비스와 연결 성공!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };



    void clickStart(){

        if(myService!=null) myService.playMusic();

    }

    void clickPause(){

        if(myService!=null) myService.pauseMusic();

    }

    void clickStop(){

        if(myService!=null){
            myService.stopMusic();
            // bindService()에 의해 연결된 서비스와의 연결을 종료
            unbindService(connection);
            myService= null;

        }

        Intent intent= new Intent(this, MyService.class);
        stopService(intent);

        finish();

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();;
    }
}





























