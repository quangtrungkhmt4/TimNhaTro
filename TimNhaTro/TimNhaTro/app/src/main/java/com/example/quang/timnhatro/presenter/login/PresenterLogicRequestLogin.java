package com.example.quang.timnhatro.presenter.login;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quang.timnhatro.constants.Constants;
import com.example.quang.timnhatro.view.login.ViewRequestLogin;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class PresenterLogicRequestLogin implements PresenterImRequestLogin{
    private ViewRequestLogin viewRequestLogin;

    public PresenterLogicRequestLogin(ViewRequestLogin viewRequestLogin) {
        this.viewRequestLogin = viewRequestLogin;
    }

    @Override
    public void checkLogin(final String user, final String pass, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equalsIgnoreCase("fail")){
                    viewRequestLogin.fail();
                }else {
                    viewRequestLogin.success(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                viewRequestLogin.error(error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("userLOGIN",user.trim());
                params.put("passLOGIN",pass.trim());
                return params;
            }
        }
        ;

        requestQueue.add(stringRequest);



//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String>  params = new HashMap<>();
//                params.put("userLOGIN",user);
//                params.put("passLOGIN",pass);
//                return params;
//            }
//        };


    }




}
