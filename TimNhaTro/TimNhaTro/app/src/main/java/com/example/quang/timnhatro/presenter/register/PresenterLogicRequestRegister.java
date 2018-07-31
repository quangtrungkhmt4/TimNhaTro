package com.example.quang.timnhatro.presenter.register;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quang.timnhatro.constants.Constants;
import com.example.quang.timnhatro.view.register.ViewRequestRegister;

import java.util.HashMap;
import java.util.Map;

public class PresenterLogicRequestRegister implements PresenterImRequestRegister {

    private ViewRequestRegister viewRequestRegister;

    public PresenterLogicRequestRegister(ViewRequestRegister viewRequestRegister) {
        this.viewRequestRegister = viewRequestRegister;
    }

    @Override
    public void checkUserExists(final String user, Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.CHECK_USER_EXISTS
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equalsIgnoreCase("fail")){
                    viewRequestRegister.failExists();
                }else {
                    viewRequestRegister.successExists();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                viewRequestRegister.error(error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("userREGISTER",user.trim());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public void register(final String user, final String pass, final String name, final String phone, Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTER
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equalsIgnoreCase("fail")){
                    viewRequestRegister.failRegister();
                }else {
                    viewRequestRegister.successRegister();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                viewRequestRegister.error(error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("userREGISTER",user.trim());
                params.put("passREGISTER",pass.trim());
                params.put("nameREGISTER",name.trim());
                params.put("phoneREGISTER",phone.trim());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
