package com.jspstudio.ex63servicebind;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyService extends Service {

    MediaPlayer mp;



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "START COMMAND", Toast.LENGTH_SHORT).show();
        return START_STICKY;



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // bindService()를 실행할 때 자동 발동하는 메소드
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Toast.makeText(this, "ON BIND", Toast.LENGTH_SHORT).show();
        return new MyBinder(); // bind될 MainActivity쪽으로 파견나갈 객체
    }

    class MyBinder extends Binder {
        // 이 MyService 객체의 주소값을 리턴해주는 기능함수
        MyService getMyServiceAddress(){
            return MyService.this;
        }
    }


    // MediaPlayer를 제어하는 3가지 기능 메소드
    public void playMusic(){
        if(mp==null){
            mp= MediaPlayer.create(this, R.raw.kalimba);
            mp.setVolume(0.7f,0.7f);
            mp.setLooping(true);
        }
        if(!mp.isPlaying()) mp.start();

    }

    public void pauseMusic(){
        if(mp!=null && mp.isPlaying()) mp.pause();
    }

    public void stopMusic(){
        if(mp!=null){
            mp.stop();
            mp.release();
            mp=null;
        }

    }


}



































