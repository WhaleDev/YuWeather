package pers.jiangyu.yuweather.db;

import org.litepal.crud.DataSupport;

public class County extends DataSupport {

    private int id;

    private String countyName;

    private int countyCode;

    public int getId() {
        return id;
    }

    public String getCountyName() {
        return countyName;
    }

    public int getCountyCode() {
        return countyCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public void setCountyCode(int countyCode) {
        this.countyCode = countyCode;
    }
}
