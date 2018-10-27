package com.angelmxc.test.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;
import java.util.Set;


public class MainActivity extends Activity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public static CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        //TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
        ImageButton btn=(ImageButton) findViewById(R.id.imageButton);
        //btn.setOnClickListener(new View.);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("email"));//, "public_profile", "user_friends"
                //LoginManager.getInstance().logOut();
            }
        });

        Button botonAnimales=(Button) findViewById(R.id.button2);
        botonAnimales.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Cambiar();
            }
        });
        Button botonAnimaciones=(Button) findViewById(R.id.button3);
        botonAnimaciones.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CambiarAanimaciones();
            }
        });


        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        Set<String> permisos=loginResult.getRecentlyGrantedPermissions();
                        if(permisos.contains("email")) {
                            Cambiar();
                        }else {
                            Toast.makeText(MainActivity.this, "No se aceptó el permiso de correo.", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, "Login Cancel.", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


        /*
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });*/
/*
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }*/
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void Cambiar() {
        Intent intent=new Intent(this,ActivityLista.class);
        startActivity(intent);
        finish();
    }

    public void CambiarAanimaciones() {
        Intent intent=new Intent(this,AnimacionesActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
