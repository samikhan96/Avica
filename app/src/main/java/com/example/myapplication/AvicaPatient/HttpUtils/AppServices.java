package com.example.myapplication.AvicaPatient.HttpUtils;


import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.AvicaPatient.Listener.ServiceListener;
import com.example.myapplication.AvicaPatient.Models.Appointment.AppointmentData;
import com.example.myapplication.AvicaPatient.Models.Chat.ChatRoom;
import com.example.myapplication.AvicaPatient.Models.DashboardData;
import com.example.myapplication.AvicaPatient.Models.DoctorProfile.ProfileData;
import com.example.myapplication.AvicaPatient.Models.Education;
import com.example.myapplication.AvicaPatient.Models.Medication;
import com.example.myapplication.AvicaPatient.Models.Notifications;
import com.example.myapplication.AvicaPatient.Models.PatientProfile;
import com.example.myapplication.AvicaPatient.Models.Reports;
import com.example.myapplication.AvicaPatient.Models.Tutorials;
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


    public static void Dashboard(String TAG,  final ServiceListener<DashboardData, String> listener) {
        RestAPI.GetUrlEncodedRequest(TAG, ConfigConstants.patientDashboard, new ServiceListener<JSONObject, VolleyError>() {
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

    public static void gettelemedappointments(String TAG,  final ServiceListener<AppointmentData, String> listener) {
        RestAPI.GetUrlEncodedRequest(TAG, ConfigConstants.gettelemedappointments , new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    AppointmentData dashboardData = GsonUtils.fromJSON(success.getJSONObject("data"), AppointmentData.class);
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

    public static void getDoctors(String TAG,  final ServiceListener<ArrayList<ProfileData>, String> listener) {
        RestAPI.GetUrlEncodedRequest(TAG, ConfigConstants.getDoctors , new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    ArrayList<ProfileData> data = new ArrayList<>(Arrays.asList(GsonUtils.fromJSON(success.getJSONArray("data").toString(), ProfileData[].class)));
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

    public static void getTutorials(String TAG,String category,  final ServiceListener<Tutorials, String> listener) {
        RestAPI.GetUrlEncodedRequest(TAG, ConfigConstants.getTutorials+"?category="+category, new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    Tutorials dashboardData = GsonUtils.fromJSON(success.getJSONObject("data"), Tutorials.class);
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

    public static void CreateAppointment(String TAG,  JSONObject userObject, final ServiceListener<String, String> listener) {
        RestAPI.PostJsonRequest(TAG, ConfigConstants.CreateAppointment, userObject, new ServiceListener<JSONObject, VolleyError>() {
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

    public static void createOrSelectChatRoom(String TAG, JSONObject userObject, final ServiceListener<ChatRoom, String> listener) {
        RestAPI.PostJsonRequest(TAG, ConfigConstants.createOrSelectChatRoom, userObject, new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    if (success.getBoolean("success")) {
                        ChatRoom user = GsonUtils.fromJSON(success.getJSONObject("data"), ChatRoom.class);
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

    public static void agoraToken(String TAG, JSONObject userObject, final ServiceListener<String, String> listener) {
        RestAPI.PostJsonRequest(TAG, ConfigConstants.agoraToken, userObject, new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    Log.d(TAG, "Response: " + success.toString());

                    if (success.has("data")) {
                        JSONObject data = success.getJSONObject("data");

                        if (data.has("agoraToken")) {
                            String token = data.getString("agoraToken");
                            String numericUid = data.getString("numericUid");
                            listener.success(token + "//" +numericUid);
                        } else {
                            listener.error("agoraToken not found in 'data'");
                        }
                    } else {
                        listener.error("'data' object not found in response");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.error("JSON parsing error: " + e.getMessage());
                }
            }

            @Override
            public void error(VolleyError error) {
                listener.error("Volley error: " + error.getMessage());
            }
        });
    }






    public static void UploadFileRequest(String TAG, String apiEndpoint, File file,
                                         final ServiceListener<NetworkResponse, VolleyError> listener) {

        MultipartRequest multipartRequest = new MultipartRequest(
                ConfigConstants.API_BASE_URL + apiEndpoint,
                file,
                "file", // <- This must match server-side form field key
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        listener.success(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.error(error);
                    }
                }
        );

        HttpRequestHandler.getInstance().addToRequestQueue(multipartRequest, TAG);
    }




}

