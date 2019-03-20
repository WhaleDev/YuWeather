package pers.jiangyu.yuweather.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pers.jiangyu.yuweather.R;

public class FlashActivity extends AppCompatActivity {

    private static final String SHARE_APP_TAG ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        SharedPreferences setting = getSharedPreferences(SHARE_APP_TAG, 0);
        Boolean user_first = setting.getBoolean("FIRST",true);
        if(user_first){//第一次
            setting.edit().putBoolean("FIRST", false).commit();
            MainActivity.actionStart(FlashActivity.this);

        }else{
          WeatherActivity.actionStart(FlashActivity.this);
        }

    }
}
