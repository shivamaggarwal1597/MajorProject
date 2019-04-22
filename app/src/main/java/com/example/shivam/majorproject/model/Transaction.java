package com.example.shivam.majorproject.model;

/**
 * Created by shivam on 9/3/19.
 */

public class Transaction {

   //String Parameters
    //Primary Key in the transaction.
  public String transaction_id = "default";

    public String commodity = "default";
    public String quantity= "default";

    public String start_date= "default";

    public boolean started = false; // when the employee checks in
    public String check_in_date= "default";

    public boolean completed  = false; //when the employee checks out
    public String check_out_date= "default";

    public String employee_id= "default";
    public String employer_id= "default";

    public String ending_address= "default";
    public String starting_address = "default";

    public String recipient_name= "default";
    public String recipient_company_name= "default";

    public String starting_location= "default"; // Store in the form Lat_lon, if available, else default
    public String ending_location= "default"; // Ditto

    public String charges= "default";
    public String recipient_remarks = "default";

    public String current_employee_location = "default";//Format - lat_lng
    public Boolean current_employee_location_set = false;

    //Boolean Parameters

    public boolean payment_done = false; //when payment is done successfully

    public boolean starting_location_set = false;
    public boolean ending_location_set = false;

    public String getCurrent_employee_location() {
        return current_employee_location;
    }

    public void setCurrent_employee_location(String current_employee_location) {
        this.current_employee_location = current_employee_location;
    }

    public Boolean getCurrent_employee_location_set() {
        return current_employee_location_set;
    }

    public void setCurrent_employee_location_set(Boolean current_employee_location_set) {
        this.current_employee_location_set = current_employee_location_set;
    }

    public String getEnding_address() {
        return ending_address;
    }

    public void setEnding_address(String ending_address) {
        this.ending_address = ending_address;
    }

    public String getRecipient_remarks() {
        return recipient_remarks;
    }

    public void setRecipient_remarks(String recipient_remarks) {
        this.recipient_remarks = recipient_remarks;
    }

    public boolean isStarting_location_set() {
        return starting_location_set;
    }

    public void setStarting_location_set(boolean starting_location_set) {
        this.starting_location_set = starting_location_set;
    }

    public boolean isEnding_location_set() {
        return ending_location_set;
    }

    public void setEnding_location_set(boolean ending_location_set) {
        this.ending_location_set = ending_location_set;
    }


    //The database model of transaction would match this and thus would be in accordance to it.

    public String getStarting_address() {
        return starting_address;
    }

    public void setStarting_address(String starting_address) {
        this.starting_address = starting_address;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getCheck_in_date() {
        return check_in_date;
    }

    public void setCheck_in_date(String check_in_date) {
        this.check_in_date = check_in_date;
    }

    public String getCheck_out_date() {
        return check_out_date;
    }

    public void setCheck_out_date(String check_out_date) {
        this.check_out_date = check_out_date;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployer_id() {
        return employer_id;
    }

    public void setEmployer_id(String employer_id) {
        this.employer_id = employer_id;
    }



    public String getRecipient_name() {
        return recipient_name;
    }

    public void setRecipient_name(String recipient_name) {
        this.recipient_name = recipient_name;
    }

    public String getRecipient_company_name() {
        return recipient_company_name;
    }

    public void setRecipient_company_name(String recipient_company_name) {
        this.recipient_company_name = recipient_company_name;
    }

    public String getStarting_location() {
        return starting_location;
    }

    public void setStarting_location(String starting_location) {
        this.starting_location = starting_location;
    }

    public String getEnding_location() {
        return ending_location;
    }

    public void setEnding_location(String ending_location) {
        this.ending_location = ending_location;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isPayment_done() {
        return payment_done;
    }

    public void setPayment_done(boolean payment_done) {
        this.payment_done = payment_done;
    }

    public Transaction() {

    }

  }
