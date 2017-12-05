package com.oss.kookmin.gps;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mUsers;
    Marker marker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //지도 프래그먼트의 핸들 가져오고, getMapAsync()를 사용하여 지도 콜백을 등록함.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        ChildEventListener mChildEventListener;
        mUsers= FirebaseDatabase.getInstance().getReference("Users").child("Users");
        mUsers.push().setValue(marker);

        Button searchingButton = (Button) findViewById(R.id.searchingButton);
        Button wardingButton = (Button) findViewById(R.id.wardingButton);
        Button chattingButton = (Button) findViewById(R.id.chattingButton);

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");

        searchingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });

        wardingButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        chattingButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }
    //구글맵 생성과 함께 firebase child에 있는 데이터 와드 모두 박아주기
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setOnMarkerClickListener(this);
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()){  //DataSnapshot은 특정 데이터베이스에 있던 데이터를 촬영한 사진과 같음
                    UserInformation user = s.getValue(UserInformation.class);
                    LatLng location=new LatLng(user.latitude,user.longitude);
                    mMap.addMarker(new MarkerOptions().position(location).title(user.name)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
    //이 밑부분은 회원목록 검색 부분에서 사용될 것 여기서도 써줬음
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
            Intent intent = new Intent(MapsActivity.this, ManagementActivity.class);
            intent.putExtra("userList",result);
            MapsActivity.this.startActivity(intent);
        }
    }
}