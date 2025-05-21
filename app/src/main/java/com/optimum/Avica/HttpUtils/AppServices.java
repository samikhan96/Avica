package com.optimum.Avica.HttpUtils;


import com.android.volley.VolleyError;
import com.optimum.Avica.Listener.ServiceListener;
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
                        UserPrefs.getInstance().saveDoctorUser(success.getJSONObject("data"));
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

}