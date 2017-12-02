package com.oss.kookmin.gps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManagementActivity extends AppCompatActivity {
    //변수 생성
    private ListView listView;
    private UserListAdapter adapter;
    private List<User> userList;
    private List<User> saveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        Intent intent = getIntent();
        //adapter초기화해줘서 userList로 만들어서 listView세팅해주기
        listView = (ListView) findViewById(R.id.listView);
        userList = new ArrayList<User>();
        saveList = new ArrayList<User>();
        adapter = new UserListAdapter(getApplicationContext(), userList, saveList);
        listView.setAdapter(adapter);

        try{
            //userList에 들어간 값을 JSONObject로 받아서 처리할 수 있도록
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            /*
            이제 이것을 JsonObject파싱할 수 있도록, 배열 이름 레스폰스 변수 개수는 카운트로 관리
            카운트 늘려가면서 받아오기 각 변수에 들어갈 수 있도록. 유저 객체에 생성자로 넣고 그것을 add해주면 됨*/
            int count = 0;
            String userID, userName, userAge, userLanguage, userResidence;
            while (count < jsonArray.length())
            {
                JSONObject object = jsonArray.getJSONObject(count);
                userID = object.getString("userID");
                userName = object.getString("userName");
                userAge = object.getString("userAge");
                userLanguage = object.getString("userLanguage");
                userResidence = object.getString("userResidence");
                User user = new User(userID, userName, userAge, userLanguage, userResidence);
                userList.add(user);
                saveList.add(user);
                count++;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        //사용가능언어, 주요활동지 이렇게 두개의 기능 구현
        EditText search = (EditText) findViewById(R.id.search);
        EditText search0 = (EditText) findViewById(R.id.search0);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            //onTextChanged는 바뀔때마다 실행하는 함수로, 여기서 searchUser를 실행해준다.
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUser(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        search0.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            //여기도 뭐 기능은 같다.
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUser0(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
/*
    searchUser함수는 유저리스트 내용은 지워주고 save리스트만큼
    search를 포함하는 경우에만 해당 유저를 추가, 값이 변경되었다는 함수 추가해주면 됨
*/
    public void searchUser(String search) {
        userList.clear();
        for(int i = 0; i< saveList.size(); i++) {
            if(saveList.get(i).getUserResidence().contains(search))
            {
                userList.add(saveList.get(i));
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void searchUser0(String search) {
        userList.clear();
        for(int i = 0; i< saveList.size(); i++) {
            if(saveList.get(i).getUserLanguage().contains(search))
            {
                userList.add(saveList.get(i));
            }
            adapter.notifyDataSetChanged();
        }
    }
}