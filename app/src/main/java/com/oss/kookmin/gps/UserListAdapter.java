package com.oss.kookmin.gps;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
//회원목록 가져올 때 내부클레스인 BaseAdapter를 쓰는 과정, getCount같은 것들은 오버라이드 해서 기능을 바꿔주었다.
public class UserListAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;
    private List<User> saveList;

    public UserListAdapter(Context context, List<User> userList, List<User> saveList) {
        this.context = context;
        this.userList = userList;
        this.saveList = saveList;
    }
    //getcount 현재 사용자 크기
    @Override
    public int getCount() {
        return userList.size();
    }
    //특정 사용자 받아주기
    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }
    //아이템은 그대로 받고
    @Override
    public long getItemId(int i) {
        return i;
    }
    //특정 사용자의 뷰가 만들어지도록 한다. getView에서
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.user, null);
        TextView userID = (TextView) v.findViewById(R.id.userID);
        TextView userName = (TextView) v.findViewById(R.id.userName);
        TextView userAge = (TextView) v.findViewById(R.id.userAge);
        TextView userLanguage = (TextView) v.findViewById(R.id.userLanguage);
        TextView userResidence = (TextView) v.findViewById(R.id.userResidence);

        userID.setText(userList.get(i).getUserID());
        userName.setText(userList.get(i).getUserName());
        userAge.setText(userList.get(i).getUserAge());
        userLanguage.setText(userList.get(i).getUserLanguage());
        userResidence.setText(userList.get(i).getUserResidence());
        //특정 유저 값 반환해주는 것
        v.setTag(userList.get(i).getUserID());
        return v;
    }
}
