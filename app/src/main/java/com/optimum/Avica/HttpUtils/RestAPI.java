package com.optimum.Avica.HttpUtils;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.optimum.Avica.Listener.ServiceListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
                headers.put("Content-Type", "application/json");
                headers.put("isMobile", "true");
                headers.put("Authorization", "Bearer " + ConfigConstants.token);
                return headers;

            }
        };

        HttpRequestHandler.getInstance().addToRequestQueue(objectRequest, TAG);
    }


    public static void PostJsonRequest(String TAG, String apiEndpoint, final JSONObject obj, final ServiceListener<JSONObject, VolleyError> listener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                ConfigConstants.API_BASE_URL + apiEndpoint,
                obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.success(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.error(error);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("isMobile", "true");
                headers.put("Authorization", "Bearer " + ConfigConstants.token);
                return headers;
            }
        };

        HttpRequestHandler.getInstance().addToRequestQueue(jsonObjectRequest, TAG);
    }


    public static void UploadFileRequest(String TAG, String apiEndpoint, File file, final ServiceListener<NetworkResponse, VolleyError> listener) {
        MultipartRequest multipartRequest = new MultipartRequest(
                ConfigConstants.API_BASE_URL + apiEndpoint,
                file,
                "file", // <-- This is the key expected by the server
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
