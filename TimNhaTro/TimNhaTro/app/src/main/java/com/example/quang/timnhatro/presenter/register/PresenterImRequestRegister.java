package com.example.quang.timnhatro.presenter.register;

import android.content.Context;

public interface PresenterImRequestRegister {
    void checkUserExists(String user, Context context);

    void register(String user, String pass, String name, String phone, Context context);
}
