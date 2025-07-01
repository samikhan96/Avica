package com.example.myapplication.AvicaPatient.HttpUtils;

public class ConfigConstants {

    public static final String API_BASE_URL = "https://avica.up.railway.app/api/v1";
    public static String token = "";
    public static final String Login = "/web/auth/login";
    public static final String ForgetPassword = "/web/auth/forgot-password";
    public static final String TechnicalSupport = "/web/support";
    public static final String ResetPass = "/users/reset-password";
    public static final String patientprofile = "/users/profile/";
    public static final String patientDashboard = "/app/rag/measurement-analytic";
    public static final String Education = "/web/education";
    public static final String Medication = "/app/medications/patients/";
    public static final String PHR = "/web/emr";
    public static final String CreateAppointment = "/web/telemed";
    public static final String uploader = "/web/uploader";
    public static final String notifications = "/web/notifications";
    public static final String AddMeasurment = "/users/single-value-measurement/";
    public static final String gettelemedappointments = "/app/telemed/appointments";
    public static final String getDoctors = "/users?role=DOCTOR";
    public static final String createOrSelectChatRoom = "/web/chat/room";
    public static final String getTutorials = "/web/videos";
    public static final String agoraToken = "/agora/token";

}