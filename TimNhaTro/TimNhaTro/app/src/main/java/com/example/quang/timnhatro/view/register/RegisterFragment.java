package com.example.quang.timnhatro.view.register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quang.timnhatro.AccountActivity;
import com.example.quang.timnhatro.R;
import com.example.quang.timnhatro.presenter.register.PresenterLogicRequestRegister;
import com.example.quang.timnhatro.view.login.LoginFragment;

public class RegisterFragment extends Fragment implements View.OnClickListener, ViewRequestRegister {

    private EditText edtUser;
    private EditText edtPass;
    private EditText edtName;
    private EditText edtPhone;
    private Button btnRegister;
    private Button btnBack;

    private PresenterLogicRequestRegister presenterLogicRequestRegister;

    private AccountActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();

        findID();
        initViews();
    }

    private void findID() {
        edtUser = getActivity().findViewById(R.id.edtUserRegister);
        edtPass = getActivity().findViewById(R.id.edtPassRegister);
        edtName = getActivity().findViewById(R.id.edtNameRegister);
        edtPhone = getActivity().findViewById(R.id.edtPhoneRegister);
        btnRegister = getActivity().findViewById(R.id.btnRegister);
        btnBack = getActivity().findViewById(R.id.btnBackRegister);
    }

    private void initViews() {
        activity = (AccountActivity) getActivity();

        presenterLogicRequestRegister = new PresenterLogicRequestRegister(this);

        btnBack.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBackRegister:
                activity.loadFragment(new LoginFragment());
                break;
            case R.id.btnRegister:
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                String name = edtName.getText().toString();
                String phone = edtPhone.getText().toString();
                if (user.isEmpty() || pass.isEmpty() || name.isEmpty() || phone.isEmpty()){
                    Toast.makeText(activity, "Bạn cần nhập đủ thông tin.", Toast.LENGTH_SHORT).show();
                    return;
                }

                presenterLogicRequestRegister.checkUserExists(user,activity);

                break;
        }
    }

    @Override
    public void successExists() {
        String user = edtUser.getText().toString();
        String pass = edtPass.getText().toString();
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        presenterLogicRequestRegister.register(user,pass,name,phone,activity);
    }

    @Override
    public void failExists() {
        Toast.makeText(activity, "Tên tài khoản của bạn đã tồn tại.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void error(String str) {
        Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successRegister() {
        Toast.makeText(activity, "Đăng ký thành công.", Toast.LENGTH_SHORT).show();
        activity.loadFragment(new LoginFragment());
    }

    @Override
    public void failRegister() {
        Toast.makeText(activity, "Đăng ký thất bại.", Toast.LENGTH_SHORT).show();
    }
}
