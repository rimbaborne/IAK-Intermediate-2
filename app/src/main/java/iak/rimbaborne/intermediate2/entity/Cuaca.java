package iak.rimbaborne.intermediate2.entity;

/**
 * Created by github.com/rimbaborne on 2/1/2018.
 */

public class Cuaca {
    private String dt;
    private String temp;
    private String weather;

    //constructor untuk inisialisasi jika tidak ada data yang akan diproses
    public Cuaca() {

    }

    //constructor untuk inisialisasi jika ada data yang akan diproses
    public Cuaca(String dt, String temp, String weather) {
        this.dt = dt;
        this.temp = temp;
        this.weather = weather;

    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
