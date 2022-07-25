package com.example.quiz;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private SharedPreferences sharedPreferences;

    public SharedPref(Context context) {
      sharedPreferences = context.getSharedPreferences("file",Context.MODE_PRIVATE);
    }
    public void setDarkModeState(Boolean state){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("darkTheme",state);
        editor.apply();
    }
    public Boolean loadDarkModeState(){
        return  sharedPreferences.getBoolean("darkTheme",false);
    }
}
