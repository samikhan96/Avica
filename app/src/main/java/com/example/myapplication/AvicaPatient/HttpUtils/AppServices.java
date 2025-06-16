package com.example.myapplication.AvicaPatient.HttpUtils;


import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.example.myapplication.AvicaPatient.Listener.ServiceListener;
import com.example.myapplication.AvicaPatient.Models.DashboardData;
import com.example.myapplication.AvicaPatient.Models.Education;
import com.example.myapplication.AvicaPatient.Models.Medication;
import com.example.myapplication.AvicaPatient.Models.Notifications;
import com.example.myapplication.AvicaPatient.Models.PatientProfile;
import com.example.myapplication.AvicaPatient.Models.Reports;
import com.example.myapplication.AvicaPatient.Models.User;
import com.example.myapplication.AvicaPatient.Utils.UserPrefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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
        RestAPI.PostJsonRequest(TAG, ConfigConstants.ResetPass, userObject, new ServiceListener<JSONObject, VolleyError>() {
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

    public static void AddMeasurment(String TAG, String id, JSONObject userObject, final ServiceListener<String, String> listener) {
        RestAPI.PostJsonRequest(TAG, ConfigConstants.AddMeasurment + id, userObject, new ServiceListener<JSONObject, VolleyError>() {
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

    public static void patientProfile(String TAG, String id, final ServiceListener<PatientProfile, String> listener) {
        RestAPI.GetUrlEncodedRequest(TAG, ConfigConstants.patientprofile + id, new ServiceListener<JSONObject, VolleyError>() {
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


    public static void Dashboard(String TAG, String id, final ServiceListener<DashboardData, String> listener) {
        RestAPI.GetUrlEncodedRequest(TAG, ConfigConstants.patientDashboard + id, new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    DashboardData dashboardData = GsonUtils.fromJSON(success.getJSONObject("data"), DashboardData.class);
                    listener.success(dashboardData);
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

    public static void getNotificiation(String TAG, final ServiceListener<ArrayList<Notifications>, String> listener) {
        RestAPI.GetUrlEncodedRequest(TAG, ConfigConstants.notifications, new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    ArrayList<Notifications> data = new ArrayList<>(Arrays.asList(GsonUtils.fromJSON(success.getJSONArray("data").toString(), Notifications[].class)));
                    listener.success(data);
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

    public static void getEducation(String TAG, final ServiceListener<ArrayList<Education>, String> listener) {
        RestAPI.GetUrlEncodedRequest(TAG, ConfigConstants.Education, new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    ArrayList<Education> data = new ArrayList<>(Arrays.asList(GsonUtils.fromJSON(success.getJSONArray("data").toString(), Education[].class)));
                    listener.success(data);
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

    public static void getMedication(String TAG, String id, final ServiceListener<ArrayList<Medication>, String> listener) {
        RestAPI.GetUrlEncodedRequest(TAG, ConfigConstants.Medication + id, new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    ArrayList<Medication> data = new ArrayList<>(Arrays.asList(GsonUtils.fromJSON(success.getJSONArray("data").toString(), Medication[].class)));
                    listener.success(data);
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

    public static void getReports(String TAG, String id, final ServiceListener<ArrayList<Reports>, String> listener) {
        RestAPI.GetUrlEncodedRequest(TAG, ConfigConstants.PHR + "?patient_id=" + id, new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    ArrayList<Reports> data = new ArrayList<>(Arrays.asList(GsonUtils.fromJSON(success.getJSONArray("data").toString(), Reports[].class)));
                    listener.success(data);
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

    public static void AddPHR(String TAG, JSONObject userObject, final ServiceListener<String, String> listener) {
        RestAPI.PostJsonRequest(TAG, ConfigConstants.PHR, userObject, new ServiceListener<JSONObject, VolleyError>() {
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

    public static void Uploader(String TAG, JSONObject userObject, final ServiceListener<String, String> listener) {
        try {
            String filePath = userObject.getString("filePath"); // e.g., {"filePath": "/storage/emulated/0/DCIM/sample.jpg"}
            File file = new File(filePath);

            if (!file.exists()) {
                listener.error("File does not exist");
                return;
            }

            RestAPI.UploadFileRequest(TAG, ConfigConstants.uploader, file, new ServiceListener<NetworkResponse, VolleyError>() {
                @Override
                public void success(NetworkResponse response) {
                    try {
                        String jsonString = new String(response.data);
                        JSONObject jsonResponse = new JSONObject(jsonString);

                        if (jsonResponse.getBoolean("success")) {
                            listener.success(jsonResponse.getJSONObject("data").toString());
                        } else {
                            listener.error(jsonResponse.getJSONObject("data").toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.error("JSON parse error");
                    }
                }

                @Override
                public void error(VolleyError error) {
                    listener.error(error.getMessage());
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            listener.error("Invalid JSON input");
        }
    }
}

