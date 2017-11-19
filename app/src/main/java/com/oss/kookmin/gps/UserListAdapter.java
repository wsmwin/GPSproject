package com.oss.kookmin.gps;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Woo on 2017-11-16.
 */

public class UserListAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;
    private List<User> saveList;

    public UserListAdapter(Context context, List<User> userList, List<User> saveList) {
        this.context = context;
        this.userList = userList;
        this.saveList = saveList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

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

        v.setTag(userList.get(i).getUserID());
        return v;
    }
}
