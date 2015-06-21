package com.spla.timer_app;

/**
 * Created by Wastl on 09.06.2015.
 */
public class ObjectItem {

    private String cname = "";
    private String k_number ="";

    //set Methods
    public void setCname(String name){
        this.cname = name;
    }

    public void setK_number(String k_number){
        this.k_number = k_number;
    }

    //get Methods
    public String getCname(){
        return this.cname;
    }
    public String getK_number(){
        return this.k_number;
    }
}
