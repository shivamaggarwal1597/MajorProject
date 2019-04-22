package com.example.shivam.majorproject.model;

import com.example.shivam.majorproject.utils.StringUsed;

/**
 * Created by shivam on 20/4/19.
 */

public class CreateEmployeeModel {

    public String name_of_person;

    public String contact_number;

    public  Boolean is_active = true;

    public String login_id;

    //everyone's password is def
    public String password = "def";


    public String current_transaction_id = "default";

    public String employer_id;


    public Boolean current_transaction_complete = false;
    public Boolean availability = true;


    public CreateEmployeeModel() {
    }

    public String getName_of_person() {
        return name_of_person;
    }

    public void setName_of_person(String name_of_person) {
        this.name_of_person = name_of_person;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurrent_transaction_id() {
        return current_transaction_id;
    }

    public void setCurrent_transaction_id(String current_transaction_id) {
        this.current_transaction_id = current_transaction_id;
    }

    public String getEmployer_id() {
        return employer_id;
    }

    public void setEmployer_id(String employer_id) {
        this.employer_id = employer_id;
    }

    public Boolean getCurrent_transaction_complete() {
        return current_transaction_complete;
    }

    public void setCurrent_transaction_complete(Boolean current_transaction_complete) {
        this.current_transaction_complete = current_transaction_complete;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
}
