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
        RestAPI.PostUrlEncodedRequest(TAG, ConfigConstants.Login, userObject, new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    if (success.getBoolean("Success")) {
                        UserPrefs.getInstance().saveDoctorUser(success.getJSONObject("data"));
                        User user = GsonUtils.fromJSON(success.getJSONObject("data"), User.class);
                        listener.success(user);
                    } else listener.error(success.getString("Message"));
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
        RestAPI.PostUrlEncodedRequest(TAG, ConfigConstants.ForgetPassword, userObject, new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    if (success.getBoolean("Success")) {
                        listener.success(success.getString("Message"));
                    } else listener.error(success.getString("Message"));
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
        RestAPI.PostUrlEncodedRequest(TAG, ConfigConstants.TechnicalSupport, userObject, new ServiceListener<JSONObject, VolleyError>() {
            @Override
            public void success(JSONObject success) {
                try {
                    if (success.getBoolean("Success")) {
                        listener.success(success.getString("Message"));
                    } else listener.error(success.getString("Message"));
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


//    public static void getAER(String TAG, final ServiceListener<ArrayList<Data>, String> listener) {
//        RestAPI.GetUrlEncodedRequest(TAG, ConfigConstants.getAER, new ServiceListener<JSONObject, VolleyError>() {
//            @Override
//            public void success(JSONObject success) {
//                try {
//                    ArrayList<Data> data = new ArrayList<>(Arrays.asList(GsonUtils.fromJSON(success.getJSONArray("Data").toString(), Data[].class)));
//                    listener.success(data);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void error(VolleyError error) {
//                listener.error(error.getMessage());
//            }
//        });
//    }
//
//    public static void GetCounselingCardDetail(String TAG, int id, final ServiceListener<CounselingCards, String> listener) {
//        RestAPI.GetUrlEncodedRequest(TAG, ConfigConstants.GetCounselingCardById + "?id=" + id, new ServiceListener<JSONObject, VolleyError>() {
//            @Override
//            public void success(JSONObject success) {
//                try {
//                    CounselingCards counselingCards = GsonUtils.fromJSON(success.getJSONObject("Data").getJSONObject("CounselingCards"), CounselingCards.class);
//                    listener.success(counselingCards);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void error(VolleyError error) {
//                listener.error(error.getMessage());
//            }
//        });
//    }

}