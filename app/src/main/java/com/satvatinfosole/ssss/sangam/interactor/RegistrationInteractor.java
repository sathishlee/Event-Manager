package com.satvatinfosole.ssss.sangam.interactor;


public interface RegistrationInteractor {

    void registration(
            String txt_first_name,
            String txt_last_name,
            String txt_father_name,
            String txt_gender,
            String txt_spouse_name,
            String txt_dob,
            String txt_address,
            String txt_city,
            String txt_state,
            String txt_country,
            String txt_pincode,
            String txt_mobile,
            String txt_landline,
            String txt_reference,
            String txt_reference_info,
            String txt_userid,
            String txt_password, String requestMailId,
            String ActivationKey
    );

    void updateProfile(
            String devoteeid, String txt_first_name,
            String txt_last_name,
            String txt_father_name,
            String txt_gender,
            String txt_spouse_name,
            String txt_dob,
            String txt_address,
            String txt_city,
            String txt_state,
            String txt_country,
            String txt_pincode,
            String txt_mobile,
            String txt_landline,
            String txt_reference,
            String txt_reference_info
    );


    void postEmailAddress(String strEmailId);

    void changePassword(String strDevoteeId, String strPassword);
}
