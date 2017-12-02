package com.oss.kookmin.gps;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class MapActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;
    private Button btnsave;
    private Button btnproceed;
    private EditText editTextName;
    private EditText editTextLatitude;
    private EditText editTextLongitude;
    private EditText et_GeoInput;
    private TextView tv_GeopText;
    private Button btn_GeoStart;
//지오코더를 불러 주소를 좌표로 만들 수 있도록!
    Geocoder mGeocoder;
    List<Address> mListAddress;
    Address mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Init();
        btnproceed=(Button)findViewById(R.id.btnproceed);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        editTextName=(EditText)findViewById(R.id.editTextName);
        editTextLatitude=(EditText)findViewById(R.id.editTextLatitude);
        editTextLongitude=(EditText)findViewById(R.id.editTextLongitude);
        btnsave=(Button)findViewById(R.id.btnsave);
        btnsave.setOnClickListener(this);
        btnproceed.setOnClickListener(new View.OnClickListener() {

            Intent intent = getIntent();
            final String userID = intent.getStringExtra("userID");

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, MapsActivity.class);
                i.putExtra("userID", userID);
                startActivity(i);
            }
        });
    }
    //주소를 좌표로 바꿔주는 것 변수 생성
    public void Init()
    {
        et_GeoInput = (EditText)findViewById(R.id.et_GeoInput);
        tv_GeopText = (TextView)findViewById(R.id.tv_GeoTextView);
        btn_GeoStart = (Button)findViewById(R.id.btn_GeoStart);
        btn_GeoStart.setOnClickListener(this);
        mGeocoder = new Geocoder(this);
    }
    //주소를 좌표로 바꿔보기
    public String SearchLocation(String location)
    {
        String result = "";
        try{
            mListAddress = mGeocoder.getFromLocationName(location, 5); //5개의 위치를 일단 받지만 밑에서 0번째만 사용
            if(mListAddress.size() > 0)
            {
                mAddress = mListAddress.get(0); // 0 번째 주소값,
                result = "lat : " + mAddress.getLatitude() + "\r\n" +
                "lon : " + mAddress.getLongitude()+ "\r\n" +
                        "Address : " + mAddress.getAddressLine(0);
            }else                Toast.makeText(this, "위치 검색 실패", Toast.LENGTH_SHORT).show();
        }catch(IOException e)
        {
            e.printStackTrace();
        }

        return result;
    }
    //와드 박기
    private void saveUserInformation(){
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        String name = editTextName.getText().toString().trim()+"_"+userID;
        double latitude=Double.parseDouble(editTextLatitude.getText().toString().trim());
        double longitude=Double.parseDouble(editTextLongitude.getText().toString().trim());
        UserInformation userInformation=new UserInformation(name,latitude,longitude);
        mDatabase.child("Users").child(userID).setValue(userInformation);
        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
    }
    //3가지 버튼 클릭 경우 수행될 것 지정
    @Override
    public void onClick(View view) {
            if (view == btnproceed) {
                finish();
            }
            if (view == btnsave) {
                saveUserInformation();
                editTextName.getText().clear();
                editTextLatitude.getText().clear();
                editTextLongitude.getText().clear();
            }
            if (view == btn_GeoStart) {
                //주소를 좌표로 찾기 버튼 클릭 시 입력창도 바뀌도록
                switch (view.getId()) {
                case R.id.btn_GeoStart:
                    String result = SearchLocation(String.valueOf(et_GeoInput.getText()));
                    tv_GeopText.setText(result);
                    et_GeoInput.setText(mAddress.getAddressLine(0));
            }
        }
    }
}