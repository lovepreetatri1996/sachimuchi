package com.example.asus.mainproject.dataModels;

/**
 * Created by Asus on 4/26/2018.
 */

public class EventData {

    public String name,date,time,description,activity_one, activity_two,activity_three,activity_four,activity_five,e_male,e_female , location;

    public String key;

    public   EventData()
    {

    }
    public EventData(String name, String date ,String time,String description, String activity_one, String activity_two, String activity_three, String activity_four, String activity_five , String e_male,String e_female , String location)

    {
        this.name = name;
        this.date=date;
        this .time=time;
        this.description=description;
        this.activity_one=activity_one;
        this.activity_two=activity_two;
        this.activity_three=activity_three;
        this.activity_four=activity_four;
        this.activity_five=activity_five;
        this.e_male=e_male;
        this.e_female=e_female;

        this.location = location ;

    }

    public EventData(String name, String date ,String time,String description, String activity_one, String activity_two, String activity_three, String activity_four, String activity_five , String e_male,String e_female , String location , String key)

    {
        this.name = name;
        this.date=date;
        this .time=time;
        this.description=description;
        this.activity_one=activity_one;
        this.activity_two=activity_two;
        this.activity_three=activity_three;
        this.activity_four=activity_four;
        this.activity_five=activity_five;
        this.e_male=e_male;
        this.e_female=e_female;

        this.location = location ;

        this.key = key;

    }

}
