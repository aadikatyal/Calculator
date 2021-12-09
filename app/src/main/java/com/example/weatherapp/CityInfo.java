package com.example.weatherapp;


import java.text.SimpleDateFormat;
import java.util.Date;

public class CityInfo
{
    private double latitude;
    private double longitude;
    private String cityName;
    private double temperature;
    private String mainWeather;
    private String weatherDescription;
    private Date dt;
    private SimpleDateFormat date, time;

    public CityInfo(double latitude, double longitude, String cityName, double temperature, String mainWeather, String weatherDescription, Date dt, SimpleDateFormat date, SimpleDateFormat time)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityName = cityName;
        this.mainWeather = mainWeather;
        this.weatherDescription = weatherDescription;
        this.temperature = temperature;
        this.dt = dt;
        this.date = date;
        this.time = time;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public String getCityName()
    {
        return cityName;
    }

    public double getTemperature()
    {
        return temperature;
    }

    public String getMainWeather()
    {
        return mainWeather;
    }

    public String getWeatherDescription()
    {
        return weatherDescription;
    }

    public Date getDt()
    {
        return dt;
    }

    public SimpleDateFormat getDate()
    {
        return date;
    }

    public SimpleDateFormat getTime()
    {
        return time;
    }
}