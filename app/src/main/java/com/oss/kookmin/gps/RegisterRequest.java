package com.oss.kookmin.gps;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Woo on 2017-11-15.
 */

public class RegisterRequest extends StringRequest{

    final static private String URL = "http://kookmingps.cafe24.com/Register.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userName, int userAge, String userLanguage, String userResidence, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userAge", userAge + "");
        parameters.put("userLanguage", userLanguage);
        parameters.put("userResidence", userResidence);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
