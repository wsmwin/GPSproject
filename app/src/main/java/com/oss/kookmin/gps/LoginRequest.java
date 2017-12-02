package com.oss.kookmin.gps;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest{

    final static private String URL = "http://kookmingps.cafe24.com/Login.php";
    private Map<String, String> parameters;
    //LoginRequest 생성자를 만들어줌(아이디와 비밀번호를 가져가서 비교할 것이므로..,스트형식으로 해줄 것이기에 이와 같음.
    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
