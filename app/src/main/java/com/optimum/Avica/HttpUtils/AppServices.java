package com.optimum.Avica.HttpUtils;


import com.android.volley.VolleyError;
import com.optimum.Avica.Listener.ServiceListener;
import com.optimum.Avica.Models.Dashboard_BG;
import com.optimum.Avica.Models.Dashboard_BP;
import com.optimum.Avica.Models.Dashboard_ECG;
import com.optimum.Avica.Models.Dashboard_Spo2;
import com.optimum.Avica.Models.Dashboard_Temp;
import com.optimum.Avica.Models.DoctorProfile;
import com.optimum.Avica.Models.PatientProfile;
import com.optimum.Avica.Models.User;
import com.optimum.Avica.Utils.UserPrefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppServices {

    public static void Login(String TAG, JSONObject userObject, final ServiceListener<User, String> listener) {
        RestAPI.PostJsonRequest(TAG, ConfigConstants.Login, userObject, new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    if (success.getBoolean("success")) {
                        UserPrefs.getInstance().saveUser(success.getJSONObject("data"));
                        User user = GsonUtils.fromJSON(success.getJSONObject("data"), User.class);
                        listener.success(user);
                    } else listener.error(success.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                listener.error(error.getMessage());
            }
        });

    }

    public static void ForgetPassword(String TAG, JSONObject userObject, final ServiceListener<String, String> listener) {
        RestAPI.PostJsonRequest(TAG, ConfigConstants.ForgetPassword, userObject, new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    if (success.getBoolean("success")) {
                        listener.success(success.getJSONObject("data").getString("message"));
                    } else listener.error(success.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                listener.error(error.getMessage());
            }
        });

    }


    public static void TechnicalSupport(String TAG, JSONObject userObject, final ServiceListener<String, String> listener) {
        RestAPI.PostJsonRequest(TAG, ConfigConstants.TechnicalSupport, userObject, new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    if (success.getBoolean("success")) {
                        listener.success(success.getJSONObject("data").toString());
                    } else listener.error(success.getJSONObject("data").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                listener.error(error.getMessage());
            }
        });

    }

    public static void ResetPass(String TAG, JSONObject userObject, final ServiceListener<String, String> listener) {
        RestAPI.PostJsonRequest_new(TAG, ConfigConstants.ResetPass, userObject, new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    if (success.getBoolean("success")) {
                        listener.success(success.getJSONObject("data").toString());
                    } else listener.error(success.getJSONObject("data").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                listener.error(error.getMessage());
            }
        });

    }

    public static void patientProfile(String TAG,  final ServiceListener<PatientProfile, String> listener) {
        RestAPI.GetUrlEncodedRequest(TAG, ConfigConstants.patientprofile, new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    PatientProfile patientProfile = GsonUtils.fromJSON(success.getJSONObject("data"), PatientProfile.class);
                    listener.success(patientProfile);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                listener.error(error.getMessage());
            }
        });
    }
    public static void DoctorProfile(String TAG,  final ServiceListener<DoctorProfile, String> listener) {
        RestAPI.GetUrlEncodedRequest(TAG, ConfigConstants.doctorprofile, new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    DoctorProfile doctorProfile = GsonUtils.fromJSON(success.getJSONObject("data"), DoctorProfile.class);
                    listener.success(doctorProfile);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                listener.error(error.getMessage());
            }
        });
    }


    public static void Dashboard(String TAG,String id,  final ServiceListener<String, String> listener) {
        RestAPI.GetUrlEncodedRequest(TAG, ConfigConstants.patientDashboard+id, new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    Dashboard_ECG dashboardEcg = GsonUtils.fromJSON(success.getJSONObject("data").getJSONObject("ecg"), Dashboard_ECG.class);
                    Dashboard_Spo2 dashboardSpo2 = GsonUtils.fromJSON(success.getJSONObject("data").getJSONObject("spo2"), Dashboard_Spo2.class);
                    Dashboard_BP dashboardBp = GsonUtils.fromJSON(success.getJSONObject("data").getJSONObject("bloodpressure"), Dashboard_BP.class);
                    Dashboard_BG dashboardBg = GsonUtils.fromJSON(success.getJSONObject("data").getJSONObject("bloodglucose"), Dashboard_BG.class);
                    Dashboard_Temp dashboardTemp = GsonUtils.fromJSON(success.getJSONObject("data").getJSONObject("temperature"), Dashboard_Temp.class);
                    listener.success(success.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                listener.error(error.getMessage());
            }
        });
    }


}