package com.example.quang.timnhatro.view.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quang.timnhatro.AccountActivity;
import com.example.quang.timnhatro.MainActivity;
import com.example.quang.timnhatro.R;
import com.example.quang.timnhatro.presenter.register.PresenterLogicRequestRegister;
import com.example.quang.timnhatro.view.register.RegisterFragment;
import com.example.quang.timnhatro.presenter.login.PresenterLogicRequestLogin;
import com.example.quang.timnhatro.view.register.ViewRequestRegister;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment implements View.OnClickListener, ViewRequestLogin , ViewRequestRegister{

    private EditText edtUser;
    private EditText edtPass;
    private Button btnLogin;
    private AccountActivity activity;
    private TextView tvRegister;

    private PresenterLogicRequestLogin presenterLogicRequestLogin;
    private PresenterLogicRequestRegister presenterLogicRequestRegister;


    // Request sing in code. Could be anything as you required.
    public static final int RequestSignInCode = 7;
    private SignInButton btnSignIn;

    private FirebaseAuth firebaseAuth;

    // Google API Client object.
    public GoogleApiClient googleApiClient;



    private LoginButton loginButton;

    private CallbackManager callbackManager;

    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        FacebookSdk.sdkInitialize(getContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logOut();
        findID();
        initViews();


    }

    private void findID() {
        edtUser = getActivity().findViewById(R.id.edtUserLogin);
        edtPass = getActivity().findViewById(R.id.edtPassLogin);
        btnLogin = getActivity().findViewById(R.id.btnLogin);
        tvRegister = getActivity().findViewById(R.id.tvRegister);
        btnSignIn = getActivity().findViewById(R.id.btnLoginGoogle);

        loginButton = getActivity().findViewById(R.id.login_button);
    }

    private void initViews() {
        activity = (AccountActivity) getActivity();

        tvRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        presenterLogicRequestLogin = new PresenterLogicRequestLogin(this);
        presenterLogicRequestRegister = new PresenterLogicRequestRegister(this);

        TextView textView = (TextView) btnSignIn.getChildAt(0);
        textView.setText("Đăng nhập với Google");

        // Getting Firebase Auth Instance into firebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance();

        // Creating and Configuring Google Sign In object.
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Creating and Configuring Google Api Client.
        googleApiClient = new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity , new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        // Adding Click listener to User Sign in Google button.
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserSignInMethod();

            }
        });


        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                request();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void request() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken()
                , new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.e("JSON",response.getJSONObject().toString());
                        try {
//                            tv.setText(object.getString("name"));
//                            tvMail.setText(object.getString("email"));
//                            profilePictureView.setProfileId(Profile.getCurrentProfile().getId());
                            email = object.getString("id");
                            name = object.getString("name");
                            presenterLogicRequestRegister.checkUserExists(email,activity);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    // Sign In function Starts From Here.
    public void UserSignInMethod(){

        // Passing Google Api Client into Intent.
        Intent AuthIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);

        startActivityForResult(AuthIntent, RequestSignInCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestSignInCode){

            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (googleSignInResult.isSuccess()){

                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();

                FirebaseUserAuth(googleSignInAccount);
            }

        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private String email;
    private String name;


    public void FirebaseUserAuth(GoogleSignInAccount googleSignInAccount) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

        Toast.makeText(activity,""+ authCredential.getProvider(),Toast.LENGTH_LONG).show();

        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(activity, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task AuthResultTask) {

                        if (AuthResultTask.isSuccessful()){

                            // Getting Current Login user details.
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


                            //Toast.makeText(activity, firebaseUser.getDisplayName().toString(), Toast.LENGTH_SHORT).show();
                            // Setting up name into TextView.
                            name =  firebaseUser.getDisplayName().toString();
                            email = firebaseUser.getEmail().toString();
//                            // Setting up Email into TextView.
//                            tvMail.setText("Email =  "+ firebaseUser.getEmail().toString());
                            presenterLogicRequestRegister.checkUserExists(firebaseUser.getEmail().toString(),activity);


                        }else {
                            Toast.makeText(activity,"Something Went Wrong",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvRegister:
                activity.loadFragment(new RegisterFragment());
                break;
            case R.id.btnLogin:
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();

                if (user.isEmpty() || pass.isEmpty()){
                    Toast.makeText(activity, "Bạn phải nhập đủ thông tin.", Toast.LENGTH_SHORT).show();
                    return;
                }

                presenterLogicRequestLogin.checkLogin(user,pass,activity);

                break;
        }
    }

    @Override
    public void success(String array) {
        String[] arr = array.split("-");

        Toast.makeText(activity, array, Toast.LENGTH_SHORT).show();

        SharedPreferences pre=getContext().getSharedPreferences("data_timnhatro", MODE_PRIVATE);
        SharedPreferences.Editor edit=pre.edit();
        edit.putString("user",array);
        edit.commit();

        activity.switchActivity();
    }

    @Override
    public void fail() {
        Toast.makeText(activity, "Thất bại", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successExists() {
        presenterLogicRequestRegister.register(email,email,name,"",activity);
    }

    @Override
    public void failExists() {
        presenterLogicRequestLogin.checkLogin(email,email,activity);
    }

    @Override
    public void error(String str) {
        Toast.makeText(activity, str, Toast.LENGTH_LONG).show();
    }

    @Override
    public void successRegister() {
        presenterLogicRequestLogin.checkLogin(email,email,activity);
    }

    @Override
    public void failRegister() {

    }


}
