package com.example.lvproject;

import java.io.Serializable;

public class Album implements Serializable
{
    private String name;
    private int ivNum;
    private String description;
    private String year;
    private String length;

    public Album(String name, int ivNum, String description, String year, String length)
    {
        this.name = name;
        this.ivNum = ivNum;
        this.description = description;
        this.year = year;
        this.length = length;
    }

    public String getName()
    {
        return name;
    }

    public int getIv()
    {
        return ivNum;
    }

    public String getDescription()
    {
        return description;
    }

    public String getYear()
    {
        return year;
    }

    public String getLength()
    {
        return length;
    }
}