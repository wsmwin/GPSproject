package com.oss.kookmin.gps;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        TextView welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        Button managementButton = (Button) findViewById(R.id.managementButton);
        Button chattingButton = (Button) findViewById(R.id.chattingButton);
        Button mapButton = (Button) findViewById(R.id.mapButton);

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        String message = "환영합니다!!!\n" + userID + "님 ^ㅇ^";

        welcomeMessage.setText(message);

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
                Intent intent2 = new Intent(getApplicationContext(), MapActivity.class);
                intent2.putExtra("userID", userID);
                startActivity(intent2);
            }
        });
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            target = "http://kookmingps.cafe24.com/List.php";
        }
        @Override
        protected String doInBackground(Void... voids){
            try{
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
        @Override
        public void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result){
            Intent intent = new Intent(MainActivity.this, ManagementActivity.class);
            intent.putExtra("userList",result);
            MainActivity.this.startActivity(intent);
        }
    }
}