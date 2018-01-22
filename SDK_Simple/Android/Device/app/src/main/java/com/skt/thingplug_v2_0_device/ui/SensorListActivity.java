package com.skt.thingplug_v2_0_device.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.skt.thingplug_v2_0_device.R;
import com.skt.thingplug_v2_0_device.controller.Buzzer;
import com.skt.thingplug_v2_0_device.controller.Camera;
import com.skt.thingplug_v2_0_device.controller.Led;
import com.skt.thingplug_v2_0_device.data.SensorInfo;
import com.skt.thingplug_v2_0_device.data.SensorType;
import com.skt.thingplug_v2_0_device.data.TTVBuilder;
import com.skt.thingplug_v2_0_device.data.UserInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tp.skt.simple.common.Define;
import tp.skt.simple.element.ArrayElement;
import tp.skt.simple.element.RPCResponse;

/**
 * activity for sensor list
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class SensorListActivity extends AppCompatActivity {

    private final static String TAG = SensorListActivity.class.getSimpleName();

    public static final String EXTRA_SENSOR_TYPE = "sensorType";

    private final int SETTING_RESULT = 100;
    public static final int SETTING_RESULT_SAVE = 1;
    public static final int SETTING_RESULT_LOGOUT = 2;

    private SensorListener sensorListener;
    private static List<SensorInfo> sensorInfos = new ArrayList<>();

    private UserInfo userInfo;
    private SimpleWorker simpleWorker;
    //    private GoogleDriveHandler googleDriveHandler;
    private Timer timer;
    private Handler transferHandler;
    private Runnable transferTask;
    private boolean sendedOffAll;

    private MediaRecorder mediaRecorder;

    private ListItemClickListener listItemClickListener = new ListItemClickListener();
    private EnableClickListener enableClickListener = new EnableClickListener();

    private boolean isFront = false;

    /**
     * get sensor info list
     *
     * @return sensor info list
     */
    public static List<SensorInfo> getSensorInfos() {
        return sensorInfos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        setContentView(R.layout.activity_sensor_list);
        sensorListener = new SensorListener(this, sensorInfos);
        userInfo = UserInfo.getInstance(this);

        ActionBar bar = getSupportActionBar();
        bar.setTitle(getString(R.string.actionbar_list) + " (" + userInfo.getDeviceName() + ")");

        simpleWorker = SimpleWorker.getInstance();
        simpleWorker.setStateListener(new SimpleListener());

//        googleDriveHandler = new GoogleDriveHandler(this, new GoogleDriveCommandListener());
//        googleDriveHandler.connect();

//        sendDeviceInfo();
        // create sensor list
        createSensorList();
    }

//    private void sendDeviceInfo() {
//        ArrayElement deviceInfo = new ArrayElement();
//        deviceInfo.addNumberElement("sysAvailableMemory", 640);
//        deviceInfo.addStringElement("sysFirmwareVersion", "1.0");
//        deviceInfo.addStringElement("sysSerialNumber", "710DJC5I10000290");
//        deviceInfo.addNumberElement("sysErrorCode", 0);
//        deviceInfo.addStringElement("sysNetworkType", "LTE");
//        deviceInfo.addStringElement("sysDeviceIpAddress", "111.111.111.111");
//        deviceInfo.addStringElement("sysThingPlugIpAddress", userInfo.getServer());
//
//        simpleWorker.sendAttribute(deviceInfo);
//    }

//    private void attribute() {
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sensor_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivityForResult(intent, SETTING_RESULT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (googleDriveHandler.onActivityResult(requestCode, resultCode, data)) {
//            return;
//        }

        if (requestCode == SETTING_RESULT) {
            switch (resultCode) {
                case SETTING_RESULT_SAVE:
                    clearSensorList();
                    createSensorList();
                    break;
                case SETTING_RESULT_LOGOUT:
                    userInfo.clear(false);
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        simpleWorker.disconnect();
        // clear sensor list
        clearSensorList();
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    /**
     * create sensor list
     */
    private void createSensorList() {
        ArrayElement deviceInfo = new ArrayElement();

        // add sensor item
        for (SensorType sensorType : SensorType.values()) {
            if (sensorType != SensorType.NONE && sensorType != SensorType.DEVICE) {
                if (setSensorListener(sensorType, true)) {
                    SensorInfo sensorInfo = new SensorInfo(sensorType);
                    if (!userInfo.getSensorStatus(sensorType)) {
                        sensorInfo.setEnable(false);
                        setSensorListener(sensorType, false);
                    }
                    sensorInfos.add(sensorInfo);
                    deviceInfo.addNumberElement(sensorType.getNickname(), 1);
                } else {
                    Log.e(TAG, sensorType.getNickname() + " is not supported!");
                    deviceInfo.addNumberElement(sensorType.getNickname(), 0);
                }
            }
        }

        // add to listview
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setSmoothScrollbarEnabled(true);
        listView.setAdapter(new SensorListAdapter(this, sensorInfos));

        timer = new Timer();
        sendedOffAll = false;

        // create timer for view
        final Handler handler = new Handler();
        TimerTask viewTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ListView listView = (ListView) findViewById(R.id.listView);
                        listView.invalidateViews();
                    }
                });
            }
        };
        timer.schedule(viewTask, 0, userInfo.getListInterval());

        simpleWorker.sendAttribute(deviceInfo);
        startReportCycle();
    }

    /**
     * start sendTelemetry cycle
     */
    private void startReportCycle() {
        if (transferHandler == null) {
            transferHandler = new Handler();

            transferTask = new Runnable() {
                @Override
                public void run() {
                    try {
                        reportSensorInfo();
                    } finally {
                        transferHandler.postDelayed(this, userInfo.getReportInterval());
                    }
                }
            };
//            transferTask.run();
            transferHandler.postDelayed(transferTask, userInfo.getReportInterval());
        }
    }

    /**
     * stop sendTelemetry cycle
     */
    private void stopReportCycle() {
        if (transferHandler != null && transferTask != null) {
            transferHandler.removeCallbacks(transferTask);
            transferTask = null;
            transferHandler = null;
        }
    }

    /**
     * clear sensor list
     */
    private void clearSensorList() {
        stopReportCycle();
        timer.cancel();
        timer.purge();

        // unregist listener
        for (SensorInfo info : sensorInfos) {
            if (info.isActivated()) {
                setSensorListener(info.getType(), false);
            }
        }

        // clear view
        sensorInfos.clear();
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.deferNotifyDataSetChanged();
    }

    /**
     * set sensor listener
     *
     * @param type        sensor type
     * @param isActivated state of activation
     * @return setting result
     */
    private boolean setSensorListener(SensorType type, boolean isActivated) {
        boolean isSetted = false;
        switch (type.getCategory()) {
            case SENSOR_MANAGER:
                SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
                for (Sensor sensor : sensorList) {
                    SensorType sensorType = SensorType.getType(SensorType.Category.SENSOR_MANAGER, sensor.getType());
                    if (sensorType == type) {
                        if (isActivated) {
                            // regist sensor listener
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                sensorManager.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_UI, userInfo.getReadInterval() * 1000);
                            } else {
                                sensorManager.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_UI);
                            }
                        } else {
                            // unregist sensor listener
                            sensorManager.unregisterListener(sensorListener, sensor);
                        }
                        isSetted = true;
                        break;
                    }
                }
                break;
            case BROADCAST:
                if (isActivated) {
                    // regist BroadcastReceiver for battery
                    registerReceiver(sensorListener, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                } else {
                    // unregist BroadcastReceiver for battery
                    unregisterReceiver(sensorListener);
                }
                isSetted = true;
                break;
            case LOCATION_MANAGER:
                if (userInfo.getAgreeTerms()) {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    boolean supportGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    boolean supportNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                    if (supportGPS || supportNetwork) {
                        if (ActivityCompat.checkSelfPermission(SensorListActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(SensorListActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return isSetted;
                        }

                        if (isActivated) {
                            // regist LocationManager listener
                            if (supportGPS) {
                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, userInfo.getReadInterval(), 0, sensorListener);
                            }
                            if (supportNetwork) {
                                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, userInfo.getReadInterval(), 0, sensorListener);
                            }
                        } else {
                            // unregist LocationManager listener
                            locationManager.removeUpdates(sensorListener);
                        }
                    }
                    isSetted = true;
                }
                break;
            case MEDIA_RECORDER:
                if (isActivated) {
                    if (mediaRecorder == null) {
                        try {
                            mediaRecorder = new MediaRecorder();
                            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                            mediaRecorder.setOutputFile("/dev/null");

                            mediaRecorder.prepare();
                            mediaRecorder.start();

                            sensorListener.setMediaRecorder(mediaRecorder);
                        } catch (IOException e) {
                            e.printStackTrace();
                            mediaRecorder = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                            mediaRecorder = null;
                        }
                    }
                } else {
                    if (mediaRecorder != null) {
                        sensorListener.setMediaRecorder(null);

                        try {
                            mediaRecorder.stop();
                            mediaRecorder.release();
                            mediaRecorder = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                            mediaRecorder = null;
                        }
                    }
                }
                isSetted = true;
                break;
            case ACTUATOR:
                isSetted = true;
                break;
        }
        return isSetted;
    }

    /**
     * sendTelemetry sensor information to ThingPlug
     */
    private void reportSensorInfo() {
        boolean isAllSensorOff = true;
        ArrayElement reportContents = null;
        final TTVBuilder builder = new TTVBuilder();
        for (SensorInfo sensorInfo : sensorInfos) {
                builder.addSensorData(sensorInfo);
                isAllSensorOff = false;
        }

        long nowMillis = System.currentTimeMillis();
        builder.addSensorTime(nowMillis);
        reportContents = builder.build();

        if (reportContents.elements.size() > 0) {
            simpleWorker.sendTelemetry(reportContents);
            sendedOffAll = isAllSensorOff;
        }
    }

    private SensorInfo getSensorInfo(SensorType sensorType) {
        for(SensorInfo sensorInfo : sensorInfos) {
            if(sensorInfo.getType() == sensorType) {
                return sensorInfo;
            }
        }
        return null;
    }

    /**
     * list adapter
     */
    private class SensorListAdapter extends ArrayAdapter<SensorInfo> {
        private final Context context;
        private final List<SensorInfo> sensorInfos;

        /**
         * constructor
         *
         * @param context     context
         * @param sensorInfos sensor info list
         */
        public SensorListAdapter(Context context, List<SensorInfo> sensorInfos) {
            super(context, R.layout.sensor_list_item, sensorInfos);

            this.context = context;
            this.sensorInfos = sensorInfos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.sensor_list_item, parent, false);
            LinearLayout itemLayout = (LinearLayout) rowView.findViewById(R.id.item_layout);
            ImageView itemImageView = (ImageView) rowView.findViewById(R.id.item_image);
            TextView itemNameView = (TextView) rowView.findViewById(R.id.item_name);
            TextView itemStatus = (TextView) rowView.findViewById(R.id.item_status);
            ToggleButton itemEnable = (ToggleButton) rowView.findViewById(R.id.item_enable);
            ToggleButton itemActivate = (ToggleButton) rowView.findViewById(R.id.item_activate);
            Button itemActuatorRun = (Button) rowView.findViewById(R.id.item_actuator_run);
            itemActuatorRun.setVisibility(View.GONE);

            final SensorInfo sensorInfo = sensorInfos.get(position);
            itemImageView.setImageResource(sensorInfo.getType().getImage());
            itemNameView.setText(sensorInfo.getType().getNickname());
            itemEnable.setChecked(sensorInfo.isEnable());
            if (sensorInfo.getType().getCategory() == SensorType.Category.ACTUATOR) {
                itemStatus.setVisibility(View.GONE);
                itemActivate.setVisibility(View.GONE);
            } else {
                itemActivate.setEnabled(false);
                itemStatus.setText(sensorInfo.toString());
                itemActivate.setChecked(sensorInfo.isActivated() && sensorInfo.isEnable());
            }

            // sensor item click
            itemLayout.setTag(sensorInfo);
            itemLayout.setOnClickListener(listItemClickListener);

            // sensor enable button click
            itemEnable.setTag(sensorInfo);
            itemEnable.setOnClickListener(enableClickListener);

            return rowView;
        }
    }

    /**
     * list item click listener
     */
    private class ListItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            final SensorInfo sensorInfo = (SensorInfo) view.getTag();

            if (sensorInfo.getType().getCategory() == SensorType.Category.ACTUATOR) {
            } else if (sensorInfo.isActivated()) {
                Intent intent = new Intent(SensorListActivity.this, SensorDetailActivity.class);
                intent.putExtra(EXTRA_SENSOR_TYPE, sensorInfo.getType());
                startActivity(intent);
            }
        }
    }

    /**
     * sensor enable button click listener
     */
    private class EnableClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            SensorInfo sensorInfo = (SensorInfo) view.getTag();
            ToggleButton button = (ToggleButton) view;

            sensorInfo.setEnable(button.isChecked());
            userInfo.setSensorStatus(sensorInfo.getType(), sensorInfo.isEnable());

            setSensorListener(sensorInfo.getType(), sensorInfo.isActivated());
        }
    }

    /**
     * oneM2M worker state listener
     */
    private class SimpleListener implements SimpleWorker.StateListener {
        @Override
        public void onConnected(boolean result) {
        }

        @Override
        public void onDisconnected(boolean result) {
        }

        @Override
        public void onUnregistered(boolean result) {
        }

        @Override
        public RESULT onReceiveCommand(String message) {
            Log.i(TAG, "onReceiveCommand");
            RESULT controlResult = RESULT.SUSPEND;
            String key = "";
            int control = 0;
            try {
                JSONObject messageObject = new JSONObject(message);
                final String cmd = messageObject.getString(Define.CMD);

                if (TextUtils.isEmpty(cmd) == false && cmd.equals(Define.SET_ATTRIBUTE)) {
                    JSONObject controlObject = messageObject.getJSONObject(Define.ATTRIBUTE);
                    key = (String) controlObject.keys().next();
                    control = controlObject.getInt(key);

                    if (key.equals("camera")) {

                        if(getSensorInfo(SensorType.CAMERA).isEnable() == false) return RESULT.FAIL;

                        // under LOLLIPOP exception handling
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && isFront == false) {
                            controlResult = RESULT.FAIL;
                        } else {
                            Camera.TYPE cameraType = Camera.TYPE.NONE;
                            switch (control) {
                                case 0:
                                    cameraType = Camera.TYPE.BACK;
                                    break;
                                case 1:
                                    cameraType = Camera.TYPE.FRONT;
                                    break;
                            }

                            Camera camera = new Camera(SensorListActivity.this, new Camera.CapturedListener() {
                                @Override
                                public void onCaptured(byte[] image) {
                                    String base64Image = Base64.encodeToString(image, Base64.NO_WRAP);
                                    Log.e(TAG, "onCaptured : " + base64Image.length());
                                    ArrayElement telemetry = new ArrayElement();
                                    telemetry.addStringElement("photo", base64Image);
                                    int nowSecond = (int) (System.currentTimeMillis()/1000);
                                    telemetry.addNumberElement("ts", nowSecond);
                                    simpleWorker.sendTelemetry(telemetry);
                                }

                                @Override
                                public void onCaptureFailed() {
                                    Log.i(TAG, "onCaptureFailed");
                                    ArrayElement telemetry = new ArrayElement();
                                    telemetry.addStringElement("photo", "");
                                    int nowSecond = (int) (System.currentTimeMillis()/1000);
                                    telemetry.addNumberElement("ts", nowSecond);
                                    simpleWorker.sendTelemetry(telemetry);
                                }
                            });
                            camera.notifyCommand(cameraType, (FrameLayout) findViewById(R.id.camera_preview));
                        }
                    } else if (key.equals("buzzer")) {
                        if(getSensorInfo(SensorType.BUZZER).isEnable() == false) return RESULT.FAIL;
                        Buzzer.TYPE type = Buzzer.TYPE.NONE;
                        switch (control) {
                            case 0:
                                type = Buzzer.TYPE.NONE;
                                break;
                            case 1:
                                type = Buzzer.TYPE.RINGTONE;
                                break;
                            case 2:
                                type = Buzzer.TYPE.NOTIFICATION;
                                break;
                            case 3:
                                type = Buzzer.TYPE.ALARM;
                                break;
                        }
                        Buzzer buzzer = new Buzzer(SensorListActivity.this);
                        if (buzzer.notifyCommand(type)) {
                            controlResult = RESULT.SUCCESS;
                        } else {
                            control = 0;
                            controlResult = RESULT.FAIL;
                        }
                    } else if (key.equals("led")) {
                        if(getSensorInfo(SensorType.LED).isEnable() == false) return RESULT.FAIL;

                        Led.COLOR color = Led.COLOR.NONE;
                        switch (control) {
                            case 0:
                                color = Led.COLOR.NONE;
                                break;
                            case 1:
                                color = Led.COLOR.RED;
                                break;
                            case 2:
                                color = Led.COLOR.GREEN;
                                break;
                            case 3:
                                color = Led.COLOR.BLUE;
                                break;
                            case 4:
                                color = Led.COLOR.MAGENTA;
                                break;
                            case 5:
                                color = Led.COLOR.CYAN;
                                break;
                            case 6:
                                color = Led.COLOR.YELLOW;
                                break;
                            case 7:
                                color = Led.COLOR.WHITE;
                                break;
                        }
                        Led led = new Led(SensorListActivity.this);
                        if (led.notifyCommand(color)) {
                            controlResult = RESULT.SUCCESS;
                        } else {
                            control = 0;
                            controlResult = RESULT.FAIL;
                        }
                    }
                } else if (TextUtils.isEmpty(cmd) == false && cmd.equals(Define.JSON_RPC)) {
                    JSONObject rpcReqObject = messageObject.getJSONObject("rpcReq");
                    String method = rpcReqObject.getString("method");
                    final String jsonRpc = rpcReqObject.getString("jsonrpc");
                    final long id = rpcReqObject.getLong("id");

                    if (method.equals("tp_user")) {
                        JSONArray params = rpcReqObject.getJSONArray("params");
                        JSONObject param = params.getJSONObject(0);
                        key = param.keys().next();
                        control = param.getInt(key);
                        Log.e(TAG, key + ":" + control);
                        String[] names;
                        String name;
//                        int length;
                        if(key.equals("camera")) {
                            if(getSensorInfo(SensorType.CAMERA).isEnable() == false) {
                                ArrayElement error = new ArrayElement();
                                error.addStringElement("errorMessage", "Camera disabled.");
                                simpleWorker.controlResult(cmd, jsonRpc, id, false, error);
                                return RESULT.FAIL;
                            }

                            // under LOLLIPOP exception handling
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && isFront == false) {
                                ArrayElement error = new ArrayElement();
                                error.addStringElement("errorMessage", "Device Application is not foreground.");
                                simpleWorker.controlResult(cmd, jsonRpc, id, false, error);
                                controlResult = RESULT.FAIL;
                            } else {
                                Camera.TYPE cameraType = Camera.TYPE.NONE;
                                switch (control) {
                                    case 0:
                                        cameraType = Camera.TYPE.BACK;
                                        break;
                                    case 1:
                                        cameraType = Camera.TYPE.FRONT;
                                        break;
                                }

                                Camera camera = new Camera(SensorListActivity.this, new Camera.CapturedListener() {
                                    @Override
                                    public void onCaptured(byte[] image) {
                                        String base64Image = Base64.encodeToString(image, Base64.NO_WRAP);
                                        Log.e(TAG, "onCaptured : " + base64Image.length());
                                        ArrayElement photo = new ArrayElement();
                                        photo.addStringElement("photo", base64Image);
//                                        int nowSecond = (int) (System.currentTimeMillis()/1000);
                                        simpleWorker.controlResult(cmd, jsonRpc, id, true, photo);
//                                        telemetry.addNumberElement("ts", nowSecond);
//                                        simpleWorker.sendTelemetry(telemetry);
                                    }

                                    @Override
                                    public void onCaptureFailed() {
                                        Log.i(TAG, "onCaptureFailed");
                                        ArrayElement error = new ArrayElement();
                                        error.addStringElement("errorMessage", "Camera capture failed.");
//                                        int nowSecond = (int) (System.currentTimeMillis()/1000);
//                                        telemetry.addNumberElement("ts", nowSecond);
//                                        simpleWorker.sendTelemetry(telemetry);
                                        simpleWorker.controlResult(cmd, jsonRpc, id, false, error);
                                    }
                                });
                                camera.notifyCommand(cameraType, (FrameLayout) findViewById(R.id.camera_preview));
                            }

                        } else {
                            for (SensorInfo sensorInfo : sensorInfos) {
                                names = sensorInfo.getType().getName();
//                            length = names.length;
                                name = names[0];
                                Log.e(TAG, name);
                                if (key.equals(name)) {
                                    sensorInfo.setActivated(control == 1 ? true : false);
                                    setSensorListener(sensorInfo.getType(), sensorInfo.isActivated());
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            if (controlResult != RESULT.SUSPEND) {
//                ArrayElement attr = new ArrayElement();
//                attr.addNumberElement(key, control);
//                simpleWorker.sendAttribute(attr);
//            }
            return controlResult;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isFront = true;
        Log.e(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        isFront = false;
        Log.e(TAG, "onPause");
    }
}
