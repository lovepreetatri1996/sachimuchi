package com.example.asus.mainproject.dataModels;

/**
 * Created by Asus on 3/21/2018.
 */

public class ProfileData {

   public String name , phone,brith,email,password, address,city,state , gender;


   public   ProfileData()
    {

    }


    public ProfileData(String name, String phone, String brith, String email, String password, String address, String city, String state , String gender)

    {
        this.name = name;


        this.phone=phone;
        this .brith=brith;

        this.email=email;
        this.password=password;
        this.gender = gender;

        this.address = address;
        this.city=city;
        this.state=state;







    }

}
