package pers.jiangyu.yuweather.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.weather.Weather;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import pers.jiangyu.yuweather.R;
import pers.jiangyu.yuweather.databinding.ActivityWeatherBinding;
import pers.jiangyu.yuweather.util.HttpUtil;
import pers.jiangyu.yuweather.util.fullScreen;


public class WeatherActivity extends AppCompatActivity {


    public ActivityWeatherBinding binding;

    private ImageView bingPicImg;

    public SwipeRefreshLayout swipeRefresh;
    private String mWeatherId;




    private TextView titleUpdateTime;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        fullScreen.changeFullScreen(WeatherActivity.this);
        binding = DataBindingUtil.setContentView(WeatherActivity.this, R.layout.activity_weather);
        binding.swipeRefresh.setColorSchemeColors(R.color.colorPrimary);  //设置下拉刷新主题颜色
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this); //数据持久化存储相关
        String weatherString = prefs.getString("weather",null);
        bingPicImg = binding.bingPicImg;
        HeConfig.init("HE1903121837451368", "8fbf7a2a75bf41c9a06acc29b1d32b7b");
        HeConfig.switchToFreeServerNode();
        //无缓存时访问服务器查询天气情况
        String weatherId = getIntent().getStringExtra("weather_id");
        requestWeather(weatherId);
        binding.swipeRefresh.setOnRefreshListener(()->{
            requestWeather(weatherId);
        });
        binding.t.chooseCity.setOnClickListener(v->{
            binding.drawerLayout.openDrawer(GravityCompat.START);
        });


        String bingPic = prefs.getString("bing_pic",null);
        if(bingPic != null){
            Glide.with(this).load(bingPic).into(bingPicImg);
        }else {
            loadBingPic();
        }

    }

    /**
     * 根据天气id请求城市天气信息
     */

    public void requestWeather(final String weatherId){
                HeWeather.getWeather(this, weatherId, new HeWeather.
                        OnResultWeatherDataListBeansListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        runOnUiThread(()->{
                            Toast.makeText(WeatherActivity.this,"获取天气失败",
                                    Toast.LENGTH_SHORT).show();
                            binding.swipeRefresh.setRefreshing(false);
                        });
                    }

                    @Override
                    public void onSuccess(List<Weather> list) {
                         runOnUiThread(()->{
                             String status = list.get(0).getStatus();
                             if (status != null&&"ok".equals(status)){
                                    binding.t.titleCity .setText(list.get(0).getBasic().getLocation());
                                    binding.a.aqiText.setText(list.get(0).getNow().getHum());
                                    binding.a.pm25Text.setText(list.get(0).getNow().getPres());
                                    binding.n.degreeText.setText(list.get(0).getNow().getTmp()+"℃");
                                    binding.n.weatherInfoText.setText(list.get(0).getNow().getCond_txt());
                                    binding.a.conditionText.setText(list.get(0).getLifestyle().get(0).getTxt());
                                    binding.a.maxText.setText(list.get(0).getDaily_forecast().get(0).getTmp_max()+"℃");
                                    binding.a.minText.setText(list.get(0).getDaily_forecast().get(0).getTmp_min()+"℃"+" ~ ");
                             }
                             binding.swipeRefresh.setRefreshing(false);
                             Toast.makeText(WeatherActivity.this,"获取天气成功",Toast.LENGTH_SHORT).show();
                         });
                    }
                });
         loadBingPic();
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context,WeatherActivity.class);
         context.startActivity(intent);
    }

    /**
     * 加载每日一图
     */
    public void loadBingPic(){
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                        WeatherActivity.this).edit();
                editor.putString("bing_ pic",bingPic);
                editor.apply();
                runOnUiThread(()->{
                    Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                });
            }
        });
    }


}
