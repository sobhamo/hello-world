package com.skt.thingplug_v2_0_device.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skt.thingplug_v2_0_device.Const;
import com.skt.thingplug_v2_0_device.R;
import com.skt.thingplug_v2_0_device.data.DeviceDescriptor;
import com.skt.thingplug_v2_0_device.data.UserInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AppendDeviceActivity extends AppCompatActivity {
    private static final String TAG = "AppendDevice";
    public static final String EXTRA_SERVICE_NAME = "ServiceName";
    public static final String EXTRA_ACCESS_TOKEN = "AccessToken";


    public static final String RESULT_DEVICE_NAME = "DeviceName";
    public static final String RESULT_SERVICE_ID = "ServiceId";
    public static final String RESULT_DEVICE_TOKEN = "DEVICE_TOKEN";

    private UserInfo  userInfo;

    private TextView mServiceName;

    private String serviceName, accessToken;
    private ArrayList<DeviceDescriptor> deviceDescriptors = new ArrayList<>();
    private Spinner deviceDescriptorList;
    private AutoCompleteTextView deviceIdTextView, deviceNameTextView, deviceDescriptorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_append_device);

        setResult(RESULT_CANCELED);

        Intent intent = getIntent();
        serviceName = intent.getStringExtra(EXTRA_SERVICE_NAME);
        accessToken = intent.getStringExtra(EXTRA_ACCESS_TOKEN);

        mServiceName = (TextView) findViewById(R.id.service_name);
        mServiceName.setText(serviceName);

        deviceIdTextView = (AutoCompleteTextView) findViewById(R.id.device_id);
        deviceNameTextView = (AutoCompleteTextView) findViewById(R.id.device_name);

        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reqDeviceId = deviceIdTextView.getText().toString();
                String reqDeviceName = deviceNameTextView.getText().toString();
                String reqDeviceDescriptorName = deviceDescriptors.get(deviceDescriptorList.getSelectedItemPosition()).getDeviceDescriptorName();

                new RegistDeviceTask().execute(serviceName, accessToken, reqDeviceId, reqDeviceName, reqDeviceDescriptorName);
            }
        });

        deviceDescriptorList = (Spinner) findViewById(R.id.device_descriptor_list);

        userInfo = UserInfo.getInstance(AppendDeviceActivity.this);

        new GetDeviceDescriptionsTask().execute(serviceName, accessToken);
    }

    private ArrayList<String> getDeviceDescriptorNames(){
        ArrayList<String> names = new ArrayList<>();
        for(DeviceDescriptor dd : deviceDescriptors){
            names.add(dd.getDisplayName());
        }
        return names;
    }

    class RegistDeviceTask extends  AsyncTask<String, Void, Boolean>{

        private String deviceName;
        private String serviceId;
        private String deviceToken;

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                Intent intent = getIntent();
                intent.putExtra(RESULT_DEVICE_NAME, deviceName);
                intent.putExtra(RESULT_SERVICE_ID, serviceId);
                intent.putExtra(RESULT_DEVICE_TOKEN, deviceToken);
                setResult(RESULT_OK, intent);
                finish();

            }else{
                Toast.makeText(AppendDeviceActivity.this, R.string.append_device_regist_fail, Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(aBoolean);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String serviceName = strings[0];
            String accessToken = strings[1];
            String reqDeviceId = strings[2];
            String reqDeviceName = strings[3];
            String reqDeviceDescriptorName = strings[4];


            try {
                // login
                URL url = new URL(String.format(Const.URL_REGIST_DEVICE, userInfo.getPortal(), serviceName));
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                request.setRequestMethod("POST");
                request.setRequestProperty("Content-Type", "application/json");
                request.setRequestProperty("X-Authorization", accessToken);

                JSONObject loginRequest = new JSONObject();
                loginRequest.put("deviceName", reqDeviceId);
                loginRequest.put("displayName", reqDeviceName);
                loginRequest.put("deviceDescriptorName", reqDeviceDescriptorName);

                OutputStreamWriter wr = new OutputStreamWriter(request.getOutputStream());
                wr.write(loginRequest.toString());
                wr.flush();

                int responseCode = request.getResponseCode();
                Log.i(TAG, "[" + url.toString() + "]" + "responseCode : " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_CREATED) {
                    InputStream is = request.getInputStream();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] byteBuffer = new byte[1024];
                    int nLength = 0;
                    while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                        baos.write(byteBuffer, 0, nLength);
                    }
                    Log.i(TAG, "response : " + baos.toString());
                    JSONObject registInfoObj = new JSONObject(baos.toString());
                    baos.reset();

                    deviceName = registInfoObj.getString("deviceName");
                    serviceId = registInfoObj.getString("serviceId");
                    deviceToken = registInfoObj.getString("deviceToken");

                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return false;
        }
    };

    class GetDeviceDescriptionsTask extends AsyncTask<String, Void, Void>{


        @Override
        protected void onPostExecute(Void aVoid) {
            if(deviceDescriptors == null || deviceDescriptors.size() <= 0){
                Toast.makeText(AppendDeviceActivity.this, R.string.append_device_not_exist_dd, Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            ArrayAdapter serviceAdapter = new ArrayAdapter(AppendDeviceActivity.this, R.layout.support_simple_spinner_dropdown_item, getDeviceDescriptorNames());
            deviceDescriptorList.setAdapter(serviceAdapter);

            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... strings) {

            String serviceName = strings[0];
            String accessToken = strings[1];
            try {
                // login
                URL url = new URL(String.format(Const.URL_GET_DEVICE_DESCRIPTIONS, userInfo.getPortal(), serviceName));
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                request.setRequestMethod("GET");
                request.setRequestProperty("Content-Type", "application/json");
                request.setRequestProperty("X-Authorization", accessToken);
//

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
                    JSONObject ddResponseJsonObj = new JSONObject(baos.toString());
                    baos.reset();

                    JSONArray ddArrayObj = ddResponseJsonObj.getJSONArray("rows");
                    int ddArraySize = ddArrayObj.length();
                    for(int index = 0; index < ddArraySize; index++){
                        JSONObject ddObj = (JSONObject) ddArrayObj.get(index);
                        String deviceDescriptorId = ddObj.getString("deviceDescriptorId");
                        JSONObject descriptorObj = ddObj.getJSONObject("descriptor");
                        String deviceDescriptorName = descriptorObj.getString("deviceDescriptorName");
                        String displayName = descriptorObj.getString("displayName");

                        deviceDescriptors.add(new DeviceDescriptor(displayName, deviceDescriptorName, deviceDescriptorId));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
