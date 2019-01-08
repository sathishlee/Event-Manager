package com.satvatinfosole.ssss.sangam.model.localDBModel;

import io.realm.RealmObject;

/**
 * Created by SATHISH on 10/25/2018.
 */
public class SangamEvent extends RealmObject{

    private String event_status;
    private String event_image;
    private String event_program;
    private String event_venue;
    private String event_type;
    private String event_name;
    private String event_edate;
    private String event_sdate;


    private String event_edate_month;
    private String event_sdate_month;
    private String event_edate_year;
    private String event_sdate_year;
    private String event_id;

    public SangamEvent(String event_status, String event_image, String event_program, String event_venue, String event_type, String event_name, String event_edate, String event_sdate, String event_id,String event_edate_month,String event_sdate_month,String event_edate_year,String event_sdate_year) {
        this.event_status = event_status;
        this.event_image = event_image;
        this.event_program = event_program;
        this.event_venue = event_venue;
        this.event_type = event_type;
        this.event_name = event_name;
        this.event_edate = event_edate;
        this.event_sdate = event_sdate;
        this.event_id = event_id;
        this.event_edate_month=event_edate_month;
        this.event_sdate_month=event_sdate_month;
        this.event_edate_year=event_edate_year;
        this.event_sdate_year=event_sdate_year;
    }

    public SangamEvent() {

    }

    public String getEvent_status() {
        return event_status;
    }

    public void setEvent_status(String event_status) {
        this.event_status = event_status;
    }

    public String getEvent_image() {
        return event_image;
    }

    public void setEvent_image(String event_image) {
        this.event_image = event_image;
    }

    public String getEvent_program() {
        return event_program;
    }

    public void setEvent_program(String event_program) {
        this.event_program = event_program;
    }

    public String getEvent_venue() {
        return event_venue;
    }

    public void setEvent_venue(String event_venue) {
        this.event_venue = event_venue;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_edate() {
        return event_edate;
    }

    public void setEvent_edate(String event_edate) {
        this.event_edate = event_edate;
    }

    public String getEvent_sdate() {
        return event_sdate;
    }

    public void setEvent_sdate(String event_sdate) {
        this.event_sdate = event_sdate;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getEvent_edate_month() {
        return event_edate_month;
    }

    public void setEvent_edate_month(String event_edate_month) {
        this.event_edate_month = event_edate_month;
    }

    public String getEvent_sdate_month() {
        return event_sdate_month;
    }

    public void setEvent_sdate_month(String event_sdate_month) {
        this.event_sdate_month = event_sdate_month;
    }

    public String getEvent_edate_year() {
        return event_edate_year;
    }

    public void setEvent_edate_year(String event_edate_year) {
        this.event_edate_year = event_edate_year;
    }

    public String getEvent_sdate_year() {
        return event_sdate_year;
    }

    public void setEvent_sdate_year(String event_sdate_year) {
        this.event_sdate_year = event_sdate_year;
    }

}
