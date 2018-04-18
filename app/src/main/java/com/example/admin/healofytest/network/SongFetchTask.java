package com.example.admin.healofytest.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by admin on 18/04/18.
 */

public class SongFetchTask {

    // The string parameter
    private RequestQueue requestQueue;

    private String url;
    private Context context;
    private ResponseListener responseListener;
    private static final String TAG = SongFetchTask.class.getSimpleName();


    public interface ResponseListener{
        public void onSuccess(String jsonObject);
        public void onFailure(String errorMessage);
    }



    public SongFetchTask(String url, Context context,ResponseListener responseListener) {
        this.context = context;
        this.url = url;
        this.responseListener = responseListener;
    }


    public void execute() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"The response is " + response);
                responseListener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"The error message is " + error.getMessage());
                responseListener.onFailure(error.getMessage());
            }
        });
        requestQueue.add(request);
    }

}
