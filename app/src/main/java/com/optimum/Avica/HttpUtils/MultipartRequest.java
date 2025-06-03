package com.optimum.Avica.HttpUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MultipartRequest extends Request<NetworkResponse> {

    private final Response.Listener<NetworkResponse> mListener;
    private final Map<String, String> mHeaders;
    private final File mFile;
    private final String fileParamName;

    public MultipartRequest(
            String url,
            File file,
            String fileParamName,
            Response.Listener<NetworkResponse> listener,
            Response.ErrorListener errorListener
    ) {
        super(Method.POST, url, errorListener);
        this.mListener = listener;
        this.mHeaders = new HashMap<>();
        this.mFile = file;
        this.fileParamName = fileParamName;

        // Add headers (modify as needed)
        mHeaders.put("Authorization", "Bearer " + ConfigConstants.token);
        mHeaders.put("isMobile", "true");
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data;boundary=" + boundary;
    }

    private final String boundary = "----MultipartBoundary" + System.currentTimeMillis();

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            // File part
            String fileName = mFile.getName();
            bos.write(("--" + boundary + "\r\n").getBytes());
            bos.write(("Content-Disposition: form-data; name=\"" + fileParamName + "\"; filename=\"" + fileName + "\"\r\n").getBytes());
            bos.write(("Content-Type: application/octet-stream\r\n\r\n").getBytes());

            FileInputStream fis = new FileInputStream(mFile);
            byte[] buffer = new byte[1024];
            int count;
            while ((count = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, count);
            }
            fis.close();
            bos.write(("\r\n").getBytes());

            // Ending boundary
            bos.write(("--" + boundary + "--\r\n").getBytes());
        } catch (IOException e) {
            VolleyLog.e("Multipart error: " + e.getMessage());
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response, null);
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        mListener.onResponse(response);
    }
}
