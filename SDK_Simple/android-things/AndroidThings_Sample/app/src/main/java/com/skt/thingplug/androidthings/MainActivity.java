package com.skt.thingplug.androidthings;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.skt.thingplug.androidthings.SimpleMessage.RPCRequest;
import com.skt.thingplug.androidthings.sensor.DumySensor;
import com.skt.thingplug.androidthings.sensor.RpiCamera;
import com.skt.thingplug.androidthings.sensor.RpiCamera.TakePictureListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import tp.skt.simple.api.Simple;
import tp.skt.simple.common.Define.DATA_FORMAT;
import tp.skt.simple.element.ArrayElement;
import tp.skt.simple.element.RPCResponse;
import tp.skt.simple.net.mqtt.SimpleCallback;
import tp.skt.simple.net.mqtt.SimpleConfiguration;
import tp.skt.simple.net.mqtt.SimpleListener;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();


    private RpiCamera mCamera;

    private Handler mCameraHandler;
    private HandlerThread mCameraThread;

    private Simple mSimple;

    private DATA_FORMAT format = DATA_FORMAT.FORMAT_JSON;

    private TextView state, timestamp, temperature, humidity, light;

    private int connectCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "No permission");
            Toast.makeText(this, "No permission", Toast.LENGTH_LONG).show();
            return;
        }

        setContentView(R.layout.activity_main);

        state = findViewById(R.id.state);
        timestamp = findViewById(R.id.timestamp);
        temperature = findViewById(R.id.temperature);
        humidity = findViewById(R.id.humidity);
        light = findViewById(R.id.light);

        mCameraThread = new HandlerThread("CameraBackground");
        mCameraThread.start();
        mCameraHandler = new Handler(mCameraThread.getLooper());


        mCamera = RpiCamera.getInstance();
        mCamera.initializeCamera(this, mCameraHandler);


        SimpleConfiguration configuration = new SimpleConfiguration(Config.HOST_ADDRESS + ":" + Config.HOST_PORT, Config.CLIENT_ID, Config.LOGIN_NAME, Config.LOGIN_PASSWORD);
        configuration.setCleanSession(true);
        mSimple = new Simple(this, Config.SERVICE_ID, Config.DEVICE_ID, null, configuration, simpleListener, true);

        mSimple.tpSimpleConnect();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCamera.shutDown();

        mCameraThread.quitSafely();

        mTelemetryHandler.removeCallbacksAndMessages(null);
    }


    private SimpleCallback callback = new SimpleCallback() {
        @Override
        public void onResponse(Object response) {
            Log.i(TAG, "onResponse");
        }

        @Override
        public void onFailure(int errorCode, String message) {
            Log.d(TAG, "onFailure ==> errorCode " + errorCode + ", message : " + message);
        }
    };

    private int getTimeStamp() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    private void sendAttribute() {
        ArrayElement element = new ArrayElement();
        element.addBooleanElement("Temperature", true);
        element.addBooleanElement("Humidity", true);
        element.addBooleanElement("Light", true);
        element.addBooleanElement("Camera", true);
        mSimple.tpSimpleAttribute(element, callback);
    }

    private void sendTelemetry() {

        int timeStamp = getTimeStamp();
        float temperature = DumySensor.getTemperature();
        float humidity = DumySensor.getHumidity();
        float light = DumySensor.getLight();

        if (format == DATA_FORMAT.FORMAT_JSON) {
            ArrayElement element = new ArrayElement();
            element.addNumberElement("ts", timeStamp);
            element.addNumberElement("temperature", temperature);
            element.addNumberElement("humidity", humidity);
            element.addNumberElement("light", light);
            mSimple.tpSimpleTelemetry(element, false, callback);
        } else if (format == DATA_FORMAT.FORMAT_CSV) {
            String data = "" + timeStamp + "," + temperature + "," + humidity + "," + light;
            mSimple.tpSimpleRawTelemetry(data, format, callback);
        }


        Date date = new Date(timeStamp * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+9"));

        this.timestamp.setText("" + timeStamp + "\n" + sdf.format(date));
        this.temperature.setText("" + temperature);
        this.humidity.setText("" + humidity);
        this.light.setText("" + light);

    }

    @SuppressLint("HandlerLeak")
    private Handler mTelemetryHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (mSimple.tpSimpleIsConnected()) {
                sendTelemetry();
                if (!isFinishing()) {
                    mTelemetryHandler.sendEmptyMessageDelayed(0, 10 * 1000);
                }
            }
        }
    };


    private SimpleListener simpleListener = new SimpleListener() {
        @Override
        public void onPush(String message) {
            try {
                Log.i(TAG, "onMessageReceived : " + message);
                final SimpleMessage simpleMessage = SimpleMessage.parsing(message);
                if (null != simpleMessage) {
                    if (null != simpleMessage.result) {
                        Log.i(TAG, "onMessageReceived result : " + simpleMessage.result);
                        return;
                    }

                    if (null != simpleMessage.rpcReq) {
                        final RPCRequest rpcReq = simpleMessage.rpcReq;
                        if (Define.RPC_RESET.equals(rpcReq.method)) {
                            Log.i(TAG, "RPC_RESET");
                        } else if (Define.RPC_REBOOT.equals(rpcReq.method)) {
                            Log.i(TAG, "RPC_REBOOT");
                        } else if (Define.RPC_UPLOAD.equals(rpcReq.method)) {
                            Log.i(TAG, "RPC_UPLOAD");
                        } else if (Define.RPC_DOWNLOAD.equals(rpcReq.method)) {
                            Log.i(TAG, "RPC_DOWNLOAD");
                        } else if (Define.RPC_SOFTWARE_INSTALL.equals(rpcReq.method)) {
                            Log.i(TAG, "RPC_SOFTWARE_INSTALL");
                        } else if (Define.RPC_SOFTWARE_REINSTALL.equals(rpcReq.method)) {
                            Log.i(TAG, "RPC_SOFTWARE_REINSTALL");
                        } else if (Define.RPC_SOFTWARE_REUNINSTALL.equals(rpcReq.method)) {
                            Log.i(TAG, "RPC_SOFTWARE_REUNINSTALL");
                        } else if (Define.RPC_SOFTWARE_UPDATE.equals(rpcReq.method)) {
                            Log.i(TAG, "RPC_SOFTWARE_UPDATE");
                        } else if (Define.RPC_FIRMWARE_UPGRADE.equals(rpcReq.method)) {
                            Log.i(TAG, "RPC_FIRMWARE_UPGRADE");
                            // DO FIRMWARE UPGRADE here...

                        } else if (Define.RPC_CLOCK_SYNC.equals(rpcReq.method)) {
                            Log.i(TAG, "RPC_CLOCK_SYNC");
                        } else if (Define.RPC_SIGNAL_STATUS_REPORT.equals(rpcReq.method)) {
                            Log.i(TAG, "RPC_SIGNAL_STATUS_REPORT");
                        } else if (Define.RPC_REMOTE.equals(rpcReq.method)) {
                            Log.i(TAG, "RPC_REMOTE");

                            // DO REMOTE here...

                        } else if (Define.RPC_USER.equals(rpcReq.method)) {
                            Log.i(TAG, "RPC_USER");

                            JSONObject paramsJSONObject = rpcReq.params.getJSONObject(0);
                            if (paramsJSONObject.has("camera")) {


                                mCamera.takePicture(new TakePictureListener() {
                                    @Override
                                    public void onPictureTaken(byte[] imageBytes) {
                                        String base64Image = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
                                        Log.e(TAG, "onCaptured : " + base64Image.length());
                                        ArrayElement photo = new ArrayElement();
                                        photo.addStringElement("photo", base64Image);

                                        RPCResponse rpcRsp = new RPCResponse(simpleMessage.cmd, simpleMessage.cmdId, rpcReq.jsonrpc, rpcReq.id, "success", true, photo);

                                        mSimple.tpSimpleResult(rpcRsp, new tp.skt.simple.net.mqtt.SimpleCallback() {
                                            @Override
                                            public void onResponse(Object o) {
                                                Log.i(TAG, "controlResult success");
                                            }

                                            @Override
                                            public void onFailure(int errorCode, String message) {
                                                Log.e(TAG, errorCode + " : " + message);
                                            }
                                        });

                                    }
                                });


                            } else if (paramsJSONObject.has("led")) {
                                int control = rpcReq.params.getJSONObject(0).getInt("led");
                                Log.i(TAG, "rpc : " + rpcReq.jsonrpc + ", id : " + rpcReq.id + "cmd : " + control);

                                // DO CONTROL here...


                                boolean result = false;
                                if (0 <= control && control <= 10) {
                                    result = true;
                                } else {
                                    result = false;
                                }

                                RPCResponse rsp = new RPCResponse();
                                rsp.setCmd(simpleMessage.cmd);
                                rsp.setCmdId(1);
                                rsp.setJsonrpc(rpcReq.jsonrpc);
                                rsp.setId(rpcReq.id);
                                rsp.setSuccess(result);
                                rsp.setResult(result ? "success" : "fail");
                                if (result) {
                                    ArrayElement arrayElement = new ArrayElement();
                                    arrayElement.addNumberElement("led", control);
                                    rsp.setResultArray(arrayElement);
                                } else {
                                    ArrayElement arrayElement = new ArrayElement();
                                    arrayElement.addNumberElement("code", 111);
                                    arrayElement.addStringElement("message", "wrong parameters");
                                    rsp.setResultArray(arrayElement);
                                }
                                mSimple.tpSimpleResult(rsp, callback);
                            }
                        }
                    } else {
                        if ("setAttribute".equals(simpleMessage.cmd) && null != simpleMessage.attribute) {

                            if (format == DATA_FORMAT.FORMAT_JSON) {
                                int led = simpleMessage.attribute.getInt("led");
                                Log.i(TAG, "led :" + led + ", " + simpleMessage.cmdId);

                                ArrayElement arrayElement = new ArrayElement();
                                arrayElement.addNumberElement("led", led);
                                mSimple.tpSimpleAttribute(arrayElement, callback);

                            } else if (format == DATA_FORMAT.FORMAT_CSV) {
                                int led = simpleMessage.attribute.getInt("led");
                                String data = ",,,,," + led;
                                mSimple.tpSimpleRawAttribute(data, format, callback);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onConnected() {
            connectCount++;
            state.setText("connected" + " (" + connectCount + ")");
            Log.i(TAG, "onConnected");
            sendAttribute();

            mTelemetryHandler.removeMessages(0);
            mTelemetryHandler.sendEmptyMessage(0);
        }

        @Override
        public void onDisconnected() {
            state.setText("disconnected" + " (" + connectCount + ")");
            Log.i(TAG, "onDisconnected");
        }

        @Override
        public void onSubscribed() {
            Log.i(TAG, "onSubscribed");
        }

        @Override
        public void onSubscribeFailure() {
            Log.i(TAG, "onSubscribeFailure");
        }

        @Override
        public void onConnectFailure() {
            state.setText("onConnectFailure" + " (" + connectCount + ")");
            Log.i(TAG, "onConnectFailure");
        }

        @Override
        public void onDisconnectFailure() {
            Log.i(TAG, "onDisconnectFailure");
        }

        @Override
        public void onConnectionLost() {
            state.setText("onConnectionLost" + " (" + connectCount + ")");
            Log.i(TAG, "onConnectionLost");
        }

        @Override
        public void onDelivered() {
            Log.i(TAG, "onDelivered");
        }
    };

}
