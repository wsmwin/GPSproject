package com.oss.kookmin.gps;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //담길 내용 변수화 해줌
        TextView welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        Button managementButton = (Button) findViewById(R.id.managementButton);
        Button chattingButton = (Button) findViewById(R.id.chattingButton);
        Button mapButton = (Button) findViewById(R.id.mapButton);
        //여기로 넘어올 때 가져온 아이디를 받아서 환영한다는 메세지를 TextView에 넣어줌
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        String message = "환영합니다!!!\n" + userID + "님 ^ㅇ^";

        welcomeMessage.setText(message);
        //회원관리, 체팅, 지도 3가지의 버튼으로 갈 때를 구현, 여기서 채팅방이나 지도의 경우는 아이디가 계속 따라가야 하기에 인텐트에 넣어줌
        managementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask().execute();
            }
        });

        chattingButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), StartActivity.class);
                intent1.putExtra("userID", userID);
                startActivity(intent1);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), MapsActivity.class);
                intent2.putExtra("userID", userID);
                startActivity(intent2);
            }
        });
    }
    //회원검색 부분에서 쓰일 AsyncTask를 이용한 회원불러오기 기능
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;
        @Override
        //초기화하는 것 target을 리스트.php로
        protected void onPreExecute(){
            target = "http://kookmingps.cafe24.com/List.php";
        }
        //실질적으로 실행될 부분
        @Override
        protected String doInBackground(Void... voids){
            try{
                //웹 파싱하는 것과 같음
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) !=null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        //이것은 onProgressUpdate를 오버라이드하고 사용하진 않음
        @Override
        public void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }

        /*모든 파싱 작업이 끝난 이후에 다음 인텐트로 넘어가도록
        onPostExecute에서 파싱결과 그대로 넣어줘서 넘어가도록*/
        @Override
        public void onPostExecute(String result){
            Intent intent = new Intent(MainActivity.this, ManagementActivity.class);
            intent.putExtra("userList",result);
            MainActivity.this.startActivity(intent);
        }
    }
}