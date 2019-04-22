package com.example.shivam.majorproject.model;

/**
 * Created by shivam on 19/4/19.
 */

public class EmployeeListModel {

    public String name_of_person;
    public String contact_number;
    public String employee_id;

    public EmployeeListModel() {
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

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }
}


//ToDO :  Process -- >  1. Add all the available employees in list.
// 2. Retrieve  Object list for all values in list
// 3. Present Data on Adapter.

