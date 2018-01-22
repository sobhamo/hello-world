package com.skt.thingplug_v2_0_service.ui;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skt.thingplug_v2_0_service.Const;
import com.skt.thingplug_v2_0_service.R;
import com.skt.thingplug_v2_0_service.data.UserInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * activity for login
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class LoginActivity extends AppCompatActivity {

    private final static String TAG = LoginActivity.class.getSimpleName();

    private final int PERMISSION_POPUP = 1000;

    // UI references.
    private AutoCompleteTextView textviewId;
    private EditText textviewPassword;

    private Spinner serviceList;
    private Spinner deviceList;

    private SimpleWorker simpleWorker;
    private UserInfo userInfo;

    private String accessToken;

    private boolean isNextActivity = false;

    private final String STEP_LOGIN_AND_GET_SERVICES = "1";
    private final String STEP_GET_DEVICES = "2";
    private final String STEP_GET_DEVICE_TOKEN = "3";
    private final String STEP_GET_ATTRIBUTE_STATUS = "4";

    private String serviceName = "";
    private String deviceName = "";
    private String deviceToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        simpleWorker = SimpleWorker.getInstance();
        simpleWorker.setStateListener(new SimpleWorkerListener());
        userInfo = UserInfo.getInstance(this);

        ActionBar bar = getSupportActionBar();
        bar.setTitle(R.string.actionbar_register);

        // Set up the login form.
        textviewId = (AutoCompleteTextView) findViewById(R.id.id);
        textviewPassword = (EditText) findViewById(R.id.password);
        textviewPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleWorker.disconnect();
                attemptLogin();
            }
        });

        serviceList = (Spinner) findViewById(R.id.serviceList);
        serviceList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String service = (String) parent.getItemAtPosition(position);
                new RestAPI().execute(STEP_GET_DEVICES, service);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        deviceList = (Spinner) findViewById(R.id.deviceList);
        deviceList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String device = (String) parent.getItemAtPosition(position);
                deviceToken = "";
                new RestAPI().execute(STEP_GET_DEVICE_TOKEN, device);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        View changeServerInfoView = findViewById(R.id.change_server_info);
        changeServerInfoView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SettingServerInfoActivity.class);
                startActivity(intent);
            }
        });

        View joinThingPlugView = findViewById(R.id.join_thingplug);
        joinThingPlugView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Const.URL_JOIN_THINGPLUG));
                startActivity(intent);
            }
        });

        Button registerButton = (Button) findViewById(R.id.register);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(serviceName) ||
                        TextUtils.isEmpty(deviceName) ||
                        TextUtils.isEmpty(deviceToken)) {
                    showToast(getResources().getString(R.string.fail_register));
                } else {
                    new RestAPI().execute(STEP_GET_ATTRIBUTE_STATUS);
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            checkAutoLogin();
        }
    }

    @Override
    protected void onDestroy() {
        if (isNextActivity == false) {
            simpleWorker.disconnect();
        }
        super.onDestroy();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, PERMISSION_POPUP);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_POPUP) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    finish();
                }
            }
            checkAutoLogin();
        }
    }

    private void moveToNext() {
        isNextActivity = true;
        Intent intent = new Intent(this, SensorListActivity.class);
//        if (userInfo.getAgreeTerms() == true) {
//            intent = new Intent(this, SensorListActivity.class);
//        } else {
//            intent = new Intent(this, TermsActivity.class);
//        }
        startActivity(intent);
        finish();
    }

    private void checkAutoLogin() {
        if(userInfo.getRegisterState() == true) {
            showProgress(true);
            simpleWorker.connect(this);
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        textviewId.setError(null);
        textviewPassword.setError(null);

        // Store values at the time of the login attempt.
        String id = textviewId.getText().toString().trim();
        String password = textviewPassword.getText().toString().trim();

        // Check for a valid id
        if (TextUtils.isEmpty(id)) {

            textviewId.setError(getString(R.string.error_field_required));
            textviewId.requestFocus();
            return;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            textviewPassword.setError(getString(R.string.error_field_required));
            textviewPassword.requestFocus();
            return;
        }

        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(password)) {
            textviewPassword.setError(getString(R.string.error_invalid_password));
            textviewPassword.requestFocus();
            return;
        }

        showProgress(true);
        // disconnect server
        simpleWorker.disconnect();
        new RestAPI().execute(STEP_LOGIN_AND_GET_SERVICES, id, password);
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final View loginFormView = findViewById(R.id.login_form);
                final View progressView = findViewById(R.id.login_progress);

                // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
                // for very easy animations. If available, use these APIs to fade-in
                // the progress spinner.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                    int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                    loginFormView.animate().setDuration(shortAnimTime).alpha(
                            show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                        }
                    });

                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                    progressView.animate().setDuration(shortAnimTime).alpha(
                            show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });
                } else {
                    // The ViewPropertyAnimator APIs are not available, so simply show
                    // and hide the relevant UI components.
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    private class RestAPI extends AsyncTask<String, Void, List<String>> {

        private String mode = "";
        private String userName;
        private String userPassword;

        @Override
        protected List<String> doInBackground(String... params) {
            mode = params[0];
            List<String> result = null;

            if (mode.equals(STEP_LOGIN_AND_GET_SERVICES)) {
//                String id = params[1];
                userName = params[1];
                userPassword = params[2];
                result = login(userName, userPassword);
            } else if (mode.equals(STEP_GET_DEVICES)) {
                serviceName = params[1];
                result = getDevices(serviceName, accessToken);
            } else if (mode.equals(STEP_GET_DEVICE_TOKEN)) {
                deviceName = params[1];
                result = getDeviceToken(serviceName, deviceName, accessToken);
            } else if (mode.equals(STEP_GET_ATTRIBUTE_STATUS)) {
                result = getAtrributeStatus(serviceName, deviceName, accessToken);
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);
            if (mode.equals(STEP_LOGIN_AND_GET_SERVICES)) {
                if (result == null) {
                    showToast(getResources().getString(R.string.fail_login));
                } else {
                    ArrayAdapter serviceAdapter = new ArrayAdapter(LoginActivity.this, R.layout.support_simple_spinner_dropdown_item, result);
                    serviceList.setAdapter(serviceAdapter);
                    userInfo.setUserName(userName);
                    userInfo.setUserPassword(userPassword);
                    findViewById(R.id.register_form).setVisibility(View.VISIBLE);
                }
            } else if (mode.equals(STEP_GET_DEVICES)) {
                if (result == null) {
                    showToast(getResources().getString(R.string.fail_device));
                } else {
                    ArrayAdapter serviceAdapter = new ArrayAdapter(LoginActivity.this, R.layout.support_simple_spinner_dropdown_item, result);
                    deviceList.setAdapter(serviceAdapter);
                }
            } else if (mode.equals(STEP_GET_DEVICE_TOKEN)) {
                if (result != null) {
                    deviceToken = result.get(0);
                }
                if (TextUtils.isEmpty(deviceToken) == true) {
                    showToast(getResources().getString(R.string.fail_token));
                }
            } else if (mode.equals(STEP_GET_ATTRIBUTE_STATUS)) {
                if(result != null) {
                    userInfo.setSupportSensor(result.get(0));
                    userInfo.setServiceName(serviceName);
                    userInfo.setDeviceName(deviceName);
                    userInfo.setDeviceToken(deviceToken);
                    simpleWorker.connect(LoginActivity.this);
                    return;
                } else {
                    showToast(getResources().getString(R.string.fail_support_sensor));
                }
            }
            showProgress(false);
        }
    }

    private List<String> login(String userName, String password) {
        List<String> services = null;
        try {
            // login
            URL url = new URL(String.format(Const.URL_LOGIN_DEFAULT, userInfo.getPortal()));
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("POST");
            request.setRequestProperty("Content-Type", "application/json");

            JSONObject loginRequest = new JSONObject();
            loginRequest.put("username", userName);
            loginRequest.put("password", password);

            OutputStreamWriter wr = new OutputStreamWriter(request.getOutputStream());
            wr.write(loginRequest.toString());
            wr.flush();

            int responseCode = request.getResponseCode();
            Log.i(TAG, "[" + url.toString() + "]" + "responseCode : " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = request.getInputStream();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                int nLength = 0;
                while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                Log.i(TAG, "response : " + baos.toString());
                JSONObject loginResponse = new JSONObject(baos.toString());
                baos.reset();
                accessToken = loginResponse.getString("accessToken");
                if (TextUtils.isEmpty(accessToken) == false) {
                    // get serviceName list
                    url = new URL(String.format(Const.URL_GET_SERVICE_LIST_DEFAULT, userInfo.getPortal(), userName));
                    request = (HttpURLConnection) url.openConnection();
                    request.setRequestMethod("GET");
                    request.setRequestProperty("Content-Type", "application/json");
                    request.setRequestProperty("X-Authorization", accessToken);

                    responseCode = request.getResponseCode();
                    Log.i(TAG, "[" + url.toString() + "]" + "responseCode : " + responseCode);

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        is = request.getInputStream();

                        while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                            baos.write(byteBuffer, 0, nLength);
                        }
                        Log.i(TAG, "response : " + baos.toString());
                        JSONObject serviceResponse = new JSONObject(baos.toString());
                        baos.reset();
                        JSONArray serviceList = new JSONArray(serviceResponse.getString("rows"));
                        JSONObject service;
                        for (int i = 0; i < serviceList.length(); i++) {
                            service = serviceList.getJSONObject(i);
                            if (services == null) services = new ArrayList<>();
                            services.add(service.getString("serviceName"));
                            Log.i(TAG, "serviceName : " + service.getString("serviceName"));
                        }
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return services;
    }

    private List<String> getDevices(String service, String accessToken) {
        List<String> devices = null;
        try {
            // get evice list
            URL url = new URL(String.format(Const.URL_GET_DEVICE_LIST_DEFAULT, userInfo.getPortal(), service));
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET");
            request.setRequestProperty("Content-Type", "application/json");
            request.setRequestProperty("X-Authorization", accessToken);

            int responseCode = request.getResponseCode();
            Log.i(TAG, "[" + url.toString() + "]" + "responseCode : " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = request.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                int nLength = 0;
                while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                Log.i(TAG, "response : " + baos.toString());
                JSONObject deviceResponse = new JSONObject(baos.toString());
                JSONArray deviceList = new JSONArray(deviceResponse.getString("rows"));
                JSONObject device;
                for (int i = 0; i < deviceList.length(); i++) {
                    device = deviceList.getJSONObject(i);
                    if (devices == null) devices = new ArrayList<>();
                    devices.add(device.getString("deviceName"));
                    Log.i(TAG, "deviceName : " + device.getString("deviceName"));
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return devices;
    }

    private List<String> getDeviceToken(String service, String device, String accessToken) {
        List<String> token = null;
        try {
            // get evice list
            URL url = new URL(String.format(Const.URL_GET_DEVICE_TOKEN_DEFAULT, userInfo.getPortal(), service, device));
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET");
            request.setRequestProperty("Content-Type", "application/json");
            request.setRequestProperty("X-Authorization", accessToken);

            int responseCode = request.getResponseCode();
            Log.i(TAG, "[" + url.toString() + "]" + "responseCode : " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = request.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                int nLength = 0;
                while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                Log.i(TAG, "response : " + baos.toString());
                JSONObject tokenResponse = new JSONObject(baos.toString());
                token = new ArrayList<>();
                token.add(tokenResponse.getString("deviceToken"));
                Log.i(TAG, "deviceName token: " + tokenResponse.getString("deviceToken"));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    private List<String> getAtrributeStatus(String service, String device, String accessToken) {
        List<String> result = null;
        try {
            // get evice list
            URL url = new URL(String.format(Const.URL_GET_ATTRIBUTE_CHECK_DEFAULT, userInfo.getPortal(), service, device));
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET");
            request.setRequestProperty("X-Authorization", accessToken);

            int responseCode = request.getResponseCode();
            Log.i(TAG, "[" + url.toString() + "]" + "responseCode : " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = request.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                int nLength = 0;
                while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                Log.i(TAG, "response : " + baos.toString());
                JSONObject tokenResponse = new JSONObject(baos.toString());
                JSONObject rowsObject = tokenResponse.getJSONObject("rows");
                String batteryStatus = rowsObject.getString("Battery");
                if(TextUtils.isEmpty(batteryStatus) == false) {
                    Log.i(TAG, "batteryStatus : " + batteryStatus);
                    result = new ArrayList<>();
                    result.add(rowsObject.toString());
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * oneM2M worker state listener
     */
    private class SimpleWorkerListener implements SimpleWorker.StateListener {

        @Override
        public void onConnected(boolean result) {
            if (result) {
                if (userInfo.getRegisterState()) {
                    moveToNext();
                } else {
                    showToast(getResources().getString(R.string.fail_register));
                }
            } else {
                showToast(getResources().getString(R.string.fail_register));
                showProgress(false);
            }
        }

        @Override
        public void onDisconnected(boolean result) {
        }

        @Override
        public void onUnregistered(boolean result) {
        }

        @Override
        public RESULT onReceiveCommand(String message) {
            return RESULT.SUSPEND;
        }

        @Override
        public void onMessageReceived(String message) {

        }
    }
}
