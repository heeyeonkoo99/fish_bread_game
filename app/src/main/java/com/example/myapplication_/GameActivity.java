package com.example.myapplication_;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

private static MediaPlayer mp;

    TextView time;
    TextView count;
    Button start;

    ImageView[] img_array = new ImageView[9];
    int[] imageID = {R.id.imageViewnew1, R.id.imageViewnew2, R.id.imageViewnew3, R.id.imageViewnew4, R.id.imageViewnew5, R.id.imageViewnew6, R.id.imageViewnew7, R.id.imageViewnew8, R.id.imageViewnew9};

    final String TAG_CHANGE1 = "change1"; //태그용
    final String TAG_CHANGE2 = "change2";
    final String TAG_CHANGE3  ="change3";
    final String TAG_CHANGE4  ="change4";
    final String TAG_CHANGE5  ="change5";
    final String TAG_FINISH="finish";
    final String TAG_NOTHING=  "change nothing";

    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mp= MediaPlayer.create(this,R.raw.main_bgm);
        mp.setLooping(true);
        mp.start();
        time = (TextView)findViewById(R.id.time);
        count = (TextView)findViewById(R.id.count);
        start = (Button)findViewById(R.id.start);

        for(int i = 0; i<img_array.length; i++){
            /*int img_id = getResources().getIdentifier("imageView"+i+1, "id", "com.example.pc_20.molegame");*/
            img_array[i] = (ImageView)findViewById(imageID[i]);
            img_array[i].setTag(TAG_NOTHING);

            img_array[i].setOnClickListener(new View.OnClickListener() { //두더지이미지에 온클릭리스너
                @Override
                public void onClick(View v) {
                    if(((ImageView)v).getTag().toString().equals(TAG_NOTHING)){
                        ((ImageView) v).setImageResource(R.drawable.a1);
                        v.setTag(TAG_CHANGE1);
                    }

                    else if (((ImageView) v).getTag().toString().equals(TAG_CHANGE1)) {
                        Toast.makeText(getApplicationContext(), "붕어빵 만들기 시작하셨군요!", Toast.LENGTH_SHORT).show();

                        ((ImageView) v).setImageResource(R.drawable.a2);
                        v.setTag(TAG_CHANGE2);
                    } else if (((ImageView) v).getTag().toString().equals(TAG_CHANGE2)) {

                        ((ImageView) v).setImageResource(R.drawable.a3);
                        v.setTag(TAG_CHANGE3);
                    } else if (((ImageView) v).getTag().toString().equals(TAG_CHANGE3)) {

                        ((ImageView) v).setImageResource(R.drawable.a4);
                        v.setTag(TAG_CHANGE4);
                    } else if (((ImageView) v).getTag().toString().equals(TAG_CHANGE4)) {

                        ((ImageView) v).setImageResource(R.drawable.a5);
                        v.setTag(TAG_CHANGE5);
                    } else if (((ImageView) v).getTag().toString().equals(TAG_CHANGE5)) {
                        count.setText(String.valueOf(score++));
                        v.setTag(TAG_FINISH);
                        ((ImageView) v).setImageResource(R.drawable.z);
                    }
                    else if (((ImageView) v).getTag().toString().equals(TAG_FINISH)) {

                        ((ImageView) v).setImageResource(R.drawable.a1);
                        v.setTag(TAG_NOTHING);

                    }
                            count.setText(String.valueOf(score));



                }
            });
        }

        time.setText("30초");
        count.setText("0개 완성");

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start.setVisibility(View.GONE);
                count.setVisibility(View.VISIBLE);

                new Thread(new timeCheck()).start();

                for(int i = 0; i<img_array.length; i++){
                    if(img_array[i].getTag().toString().equals(TAG_CHANGE1)) {
                        new Thread(new DThread(i)).start();
                    }
                }
            }
        });




    }
@Override
        public void onDestroy(){
        mp.stop();
        super.onDestroy();
}


    //손님이 왔을 경우
    Handler onHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            //랜덤 손님

            img_array[msg.arg1].setImageResource(R.drawable.b1);

        }
    };

    //테이블 쓰레드 정의
    public class DThread implements Runnable{
        int index = 0;

        DThread(int index){
            this.index=index;
        }

        @Override
        public void run() {
            while(true){
                try {

                    Thread.sleep(50000);
                    //테이블 번호 메세지에 담아 handler로 보냄
                    Message msg1 = new Message();
                    msg1.arg1 = index;
                    onHandler.sendMessage(msg1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            time.setText(msg.arg1 + "초");
        }
    };

    public class timeCheck implements Runnable {
        final int MAXTIME = 30;

        @Override
        public void run() {
            for (int i = MAXTIME; i >= 0; i--) {
                Message msg = new Message();
                msg.arg1 = i;
                handler.sendMessage(msg);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Intent intent = new Intent(GameActivity.this, ResultActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
            finish();
        }
    }
}