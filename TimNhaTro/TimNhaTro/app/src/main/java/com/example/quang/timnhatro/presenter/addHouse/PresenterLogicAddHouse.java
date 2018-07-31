package com.example.quang.timnhatro.presenter.addHouse;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quang.timnhatro.constants.Constants;
import com.example.quang.timnhatro.view.addHouse.ViewAddHouse;

import java.util.HashMap;
import java.util.Map;

public class PresenterLogicAddHouse implements PresenterImAddHouse {

    private ViewAddHouse viewAddHouse;

    public PresenterLogicAddHouse(ViewAddHouse viewAddHouse) {
        this.viewAddHouse = viewAddHouse;
    }

    @Override
    public void updateImage(final String base64, Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UP_IMAGE
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equalsIgnoreCase("fail")){
                    viewAddHouse.failUpImage();
                }else {
                    viewAddHouse.successUpImage(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("StringBase",base64);
                return params;
            }
        }
                ;

        requestQueue.add(stringRequest);
    }

    @Override
    public void addHouse(final String title, final String address, final String image, final String desc
            , final String contact, final float acreage, final float price, final int idUser, Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ADD_HOUSE
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equalsIgnoreCase("fail")){
                    viewAddHouse.failAddHouse();
                }else {
                    viewAddHouse.successAddHouse();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("TITLE",title);
                params.put("ADDRESS",address);
                params.put("IMAGE",image);
                params.put("DESC",desc);
                params.put("CONTACT",contact);
                params.put("ACREAGE",acreage+"");
                params.put("PRICE",price+"");
                params.put("IDUSER",idUser+"");
                return params;
            }
        }
                ;

        requestQueue.add(stringRequest);

    }

}
