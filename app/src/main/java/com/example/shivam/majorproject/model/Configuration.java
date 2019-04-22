package com.example.shivam.majorproject.model;

/**
 * Created by shivam on 9/3/19.
 */

public class Configuration {

    public boolean logged_in  = false ;
    public String login_id  = " default";
    public  String login_password = "default";
    public String contact_number = "default";
    public  String name_of_person = "default";
    public String type_of_person = "default";

    public Configuration() {
    }

    public boolean isLogged_in() {
        return logged_in;
    }

    public void setLogged_in(boolean logged_in) {
        this.logged_in = logged_in;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getLogin_password() {
        return login_password;
    }

    public void setLogin_password(String login_password) {
        this.login_password = login_password;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getName_of_person() {
        return name_of_person;
    }

    public void setName_of_person(String name_of_person) {
        this.name_of_person = name_of_person;
    }

    public String getType_of_person() {
        return type_of_person;
    }

    public void setType_of_person(String type_of_person) {
        this.type_of_person = type_of_person;
    }
}
