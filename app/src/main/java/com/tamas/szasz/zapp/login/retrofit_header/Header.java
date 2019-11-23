package com.tamas.szasz.zapp.login.retrofit_header;

import com.tamas.szasz.zapp.credentials.User;

import java.util.HashMap;

public  class Header {

    private static HashMap<String ,String> headers;

    private Header(){

        headers = new HashMap<String, String>();
        headers.put("TEAM_KEY","SWFMDCMMZBGU8");
        headers.put("Content-Type","application/json");
        headers.put("Authorization","bearer" + " " + User.getInstance().getToken());

    }

    public static HashMap<String,String> getHeader(){
        if(headers == null)
            new Header();
        return headers;
    }


}
