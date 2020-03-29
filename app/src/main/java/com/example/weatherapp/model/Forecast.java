
package com.example.weatherapp.model;


public class Forecast {

    private String cod;
    private Integer message;
    private Integer cnt;
    private java.util.List<ForeCastList> list = null;
    private City city;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public java.util.List<ForeCastList> getList() {
        return list;
    }

    public void setList(java.util.List<ForeCastList> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
