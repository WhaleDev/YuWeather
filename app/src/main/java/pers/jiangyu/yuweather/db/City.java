package pers.jiangyu.yuweather.db;

import org.litepal.crud.DataSupport;

public class City extends DataSupport {

    private int id;

    private String cityName;

    private int cityCode;

    public int getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }
}
