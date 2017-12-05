package com.oss.kookmin.gps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StartActivity extends AppCompatActivity {
    //변수지정
    private EditText user_chat;
    private Button user_next;
    private ListView chat_list;
    //파이어베이스 불러옴
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        user_chat = (EditText) findViewById(R.id.user_chat);
        user_next = (Button) findViewById(R.id.user_next);
        chat_list = (ListView) findViewById(R.id.chat_list);

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        //체팅방 들어갈 때 버튼 클릭시 수행될 것으로, 체팅방 이름과 아이디를 가지고 넘어간다.
        user_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_chat.getText().toString().equals(""))
                    return;

                Intent intent = new Intent(StartActivity.this, ChatActivity.class);
                intent.putExtra("chatName", user_chat.getText().toString());
                intent.putExtra("userName", userID);
                startActivity(intent);
            }
        });
        //체팅방을 보여주는 함수 실행
        showChatList();

    }

    private void showChatList() {
        // 리스트 어댑터 생성 및 세팅
        final ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        chat_list.setAdapter(adapter);
        //아파치 라이센스 가진 심플리스트 가져오기.

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리 차일드 chat에 있는 것에 추가하는 것
        databaseReference.child("chat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("LOG", "dataSnapshot.getKey() : " + dataSnapshot.getKey());
                adapter.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}