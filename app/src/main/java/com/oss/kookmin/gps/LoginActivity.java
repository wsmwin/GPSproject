package com.oss.kookmin.gps;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //각각 담길 내용을 변수 할당해줌
        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final TextView registerButton = (TextView) findViewById(R.id.registerButton);
        //회원가입 버튼을 눌렀을 때 회원가입 페이지로 가도록 해줌
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
        //로그인 버튼을 눌렀을 때 일어날 일이다.
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String userID = idText.getText().toString();
                final String userPassword = passwordText.getText().toString();
                //리스폰스 내부 클레스로, 인터넷 접속 뒤 레스폰스 건너오면 그 레스폰스 저장해주는 것
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            //onResponse를 override해주는 것으로, json 파싱을 통햏 success값이 T/F인지 success boolean변수에 넣어줌
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //로그인 성공 시 아이디 데이터를 파싱해서 인텐트에 값을 넣어서 그것을 가지고 메인 화면으로 가도록 해줌
                            if (success) {
                                String userID = jsonResponse.getString("userID");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userID", userID);

                                LoginActivity.this.startActivity(intent);
                            }
                            //로그인 실패 시 알림을 띄워줌
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 실패하였습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        //로그인 과정을 수행하다가 오류가 날 경우
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                //LoginRequest클레스를 생성자로 만들어서 큐에 에드한다.
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                //RequestQueue 여기서 리퀘스트 전송하고 레스폰스 가져오는 것
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                //레스폰스에 로그인 리퀘스트로 넣은 것을 추가해준다.
                queue.add(loginRequest);
            }
        });
    }
}