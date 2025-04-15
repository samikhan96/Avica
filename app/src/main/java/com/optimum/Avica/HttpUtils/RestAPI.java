package com.optimum.Avica.HttpUtils;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.optimum.Avica.Listener.ServiceListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RestAPI {

    public static void GetUrlEncodedRequest(String TAG, String apiEndpoint, final ServiceListener<JSONObject, VolleyError> listener) {
        StringRequest objectRequest = new StringRequest(Request.Method.GET,
                ConfigConstants.API_BASE_URL + apiEndpoint
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    listener.success(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.error(error);
            }
        }) {
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
//                headers.put("accessToken", Appconstant.token);
                return headers;

            }
        };

        HttpRequestHandler.getInstance().addToRequestQueue(objectRequest, TAG);
    }


    public static void PostUrlEncodedRequest(String TAG, String apiEndpoint, final JSONObject obj, final ServiceListener<JSONObject, VolleyError> listener) {
        StringRequest objectRequest = new StringRequest(Request.Method.POST,
                ConfigConstants.API_BASE_URL + apiEndpoint
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    listener.success(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.error(error);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //noinspection unchecked
                return GsonUtils.fromJSON(obj, HashMap.class);
            }

            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
//                headers.put("accessToken", Appconstant.token);
                return headers;

            }
        };
        HttpRequestHandler.getInstance().addToRequestQueue(objectRequest, TAG);
    }

    public static void PostRequestarray(String TAG, String apiEndpoint, JSONArray jsonObj, final ServiceListener<JSONArray, VolleyError> listener) {
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.POST, ConfigConstants.API_BASE_URL + apiEndpoint,
                jsonObj, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                listener.success(response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.error(error);
            }
        }) {

            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
//                headers.put("accessToken", Appconstant.token);

                return headers;


            }

        };
        HttpRequestHandler.getInstance().addToRequestQueue(objectRequest, TAG);
    }
}
