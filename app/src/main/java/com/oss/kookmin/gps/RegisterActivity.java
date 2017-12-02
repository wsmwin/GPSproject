package com.oss.kookmin.gps;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //각각 담길 내용을 변수 할당해줌
        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final EditText ageText = (EditText) findViewById(R.id.ageText);
        final EditText languageText = (EditText) findViewById(R.id.languageText);
        final EditText residenceText = (EditText) findViewById(R.id.residenceText);

        Button registerButton = (Button) findViewById(R.id.registerButton);
        //등록버튼을 눌렀을 때 일어날 이벤트를 이 안에 넣어준다.
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userName = nameText.getText().toString();
                int userAge = Integer.parseInt(ageText.getText().toString());
                String userLanguage = languageText.getText().toString();
                String userResidence = residenceText.getText().toString();
                //특정요청을 한 이후 리스너에서 원하는 결과값을 다루도록 함.
                Response.Listener<String> responListener = new Response.Listener<String>() {
                //특정 레스폰스 실행했을 때 결과가 담길 수 있도록 한다.
                    @Override
                    public void onResponse(String response) {
                        try {
                            //onResponse를 override해주는 것으로, json을 통해 값을 success해서 트루값으로 변경해줌
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //성공했을 경우
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            //실패했을 경우
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 실패했습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        //json 수행과정에 오류가 있을 경우
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
/*
    RegisterRequest 회원가입 신청. 레스폰스 리스너까지 매개변수로 넘겨서 함수 실행
    RequestQueue 인터넷 접속하는 방식으로 버튼 클릭했을 때 레지스터 리퀘스트 실행되도록
*/
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userAge, userLanguage, userResidence, responListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}