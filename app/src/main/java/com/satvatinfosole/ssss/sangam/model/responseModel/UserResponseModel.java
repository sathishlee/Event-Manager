package com.satvatinfosole.ssss.sangam.model.responseModel;

/**
 * Created by SATHISH on 10/23/2018.
 */
public class UserResponseModel {

    private String reg_date;
    private String videostatus;
    private String fathername;
    private String additionalinfo;
    private String howdouknow;
    private String email;
    private String sex;
    private String mobile;
    private String pincode;
    private String contactphone;
    private String state;
    private String city;
    private String address;
    private String activation;
    private String dob;
    private String suppose_name;
    private String lname;
    private String fname;
    private String pword;
    private String uname;
    private String devoteeid;
    private String country;

    public String getDevotee_cover_image() {
        return devotee_cover_image;
    }

    public void setDevotee_cover_image(String devotee_cover_image) {
        this.devotee_cover_image = devotee_cover_image;
    }

    private String devotee_cover_image;


    /**
     *
     * @param reg_date
     * @param videostatus
     * @param fathername
     * @param additionalinfo
     * @param howdouknow
     * @param email
     * @param sex
     * @param mobile
     * @param pincode
     * @param contactphone
     * @param state
     * @param city
     * @param address
     * @param activation
     * @param dob
     * @param suppose_name
     * @param lname
     * @param fname
     * @param pword
     * @param uname
     * @param devoteeid
     * @param country
     * @param devotee_cover_image
     */
    public UserResponseModel(String reg_date, String videostatus, String fathername, String additionalinfo, String howdouknow, String email, String sex, String mobile, String pincode, String contactphone, String state, String city, String address, String activation, String dob, String suppose_name, String lname, String fname, String pword, String uname, String devoteeid,String country,String devotee_cover_image) {
        this.reg_date = reg_date;
        this.videostatus = videostatus;
        this.fathername = fathername;
        this.additionalinfo = additionalinfo;
        this.howdouknow = howdouknow;
        this.email = email;
        this.sex = sex;
        this.mobile = mobile;
        this.pincode = pincode;
        this.contactphone = contactphone;
        this.state = state;
        this.city = city;
        this.address = address;
        this.activation = activation;
        this.dob = dob;
        this.suppose_name = suppose_name;
        this.lname = lname;
        this.fname = fname;
        this.pword = pword;
        this.uname = uname;
        this.devoteeid = devoteeid;
        this.country = country;
        this.devotee_cover_image = devotee_cover_image;
    }

    public UserResponseModel() {

    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getVideostatus() {
        return videostatus;
    }

    public void setVideostatus(String videostatus) {
        this.videostatus = videostatus;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getAdditionalinfo() {
        return additionalinfo;
    }

    public void setAdditionalinfo(String additionalinfo) {
        this.additionalinfo = additionalinfo;
    }

    public String getHowdouknow() {
        return howdouknow;
    }

    public void setHowdouknow(String howdouknow) {
        this.howdouknow = howdouknow;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getContactphone() {
        return contactphone;
    }

    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSuppose_name() {
        return suppose_name;
    }

    public void setSuppose_name(String suppose_name) {
        this.suppose_name = suppose_name;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getPword() {
        return pword;
    }

    public void setPword(String pword) {
        this.pword = pword;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getDevoteeid() {
        return devoteeid;
    }

    public void setDevoteeid(String devoteeid) {
        this.devoteeid = devoteeid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}
