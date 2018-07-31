package com.example.quang.timnhatro.presenter.addHouse;

import android.content.Context;

public interface PresenterImAddHouse {
    void updateImage(String base64, Context context);
    void addHouse( String title, String address, String image
            , String desc, String contact, float acreage, float price, int idUser, Context context);
}
