package com.example.quotesapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private Context context;

    //Create Constructor
    public SessionManager(Context _context){
        context = _context;
        sharedPreferences = context.getSharedPreferences("UserLoginSession",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    //Check Login status
    public boolean checkLogin(){
        if(sharedPreferences.getBoolean("IS_LOGIN",false)==false){
            return false;
        }else{
            return true;
        }
    }


    public void logout(){
        editor.clear();
        editor.commit();
    }


    //Create Login Session
    public void createSession(String id){
        editor.putBoolean("IS_LOGIN",true);
        editor.putString("KEY_ID",id);
        editor.commit();
    }

    public String getID(){
        String id = sharedPreferences.getString("KEY_ID","NO Value");
        return id;
    }

}
