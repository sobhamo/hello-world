package com.skt.thingplug_v2_0_service.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonObject;
import com.skt.thingplug_v2_0_service.Const;
import com.skt.thingplug_v2_0_service.data.UserInfo;
import com.skt.thingplug_v2_0_service.data.Utils;

import tp.skt.simple.api.Simple;
import tp.skt.simple.common.Define;
import tp.skt.simple.element.ArrayElement;
import tp.skt.simple.element.Subscribe;
import tp.skt.simple.net.mqtt.SimpleCallback;
import tp.skt.simple.net.mqtt.SimpleListener;

/**
 * oneM2M SDK handler
 *
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class SimpleWorker {

    private static final String TAG = "SIMPLE_WORKER";
    private static SimpleWorker simpleWorker;
    private Context context;
    private StateListener stateListener;

    private final boolean USE_REPORT_DELIVERED_CHECK = false;
    private boolean reportMessageDelivered = true;

    private int cmdId = 1;

    private Simple simple;
    private UserInfo userInfo;
    /**
     * working state listener
     */
    public interface StateListener {
        enum RESULT {
            FAIL,
            SUCCESS,
            SUSPEND,
        }

        void onConnected(boolean result);
        void onDisconnected(boolean result);
//        void onRegistered(boolean result);
        void onUnregistered(boolean result);
        RESULT onReceiveCommand(String message);
        void onMessageReceived(String message);
    }

    /**
     * get SimpleWorker
     * @return SimpleWorker object
     */
    public static SimpleWorker getInstance() {
        if (simpleWorker == null) {
            simpleWorker = new SimpleWorker();
        }
        return simpleWorker;
    }

    /**
     * set state listener
     *
     * @param stateListener
     */
    public void setStateListener(StateListener stateListener) {
        this.stateListener = stateListener;
    }

    /**
     * connect
     * @param context
     */
    public void connect(final Context context) {
        this.context = context.getApplicationContext();
        userInfo = UserInfo.getInstance(this.context);
        String clientId = "";
        String macAddress = Utils.getMacAddress(context);
        if (TextUtils.isEmpty(macAddress) == false) {
            clientId = userInfo.getDeviceToken() + "_" + macAddress;
            int length = clientId.length();
            if (length > Const.MAX_CLIENT_ID_LENGTH) {
                clientId = clientId.substring(0, length - (length - Const.MAX_CLIENT_ID_LENGTH));
            }
        }
        String host = userInfo.getServer();
        if(userInfo.getUseTLS()) {
            host = "ssl://" + host;
        } else {
            host = "tcp://" + host;
        }
        Log.e(TAG, "host : " + host);

        String serviceName = userInfo.getServiceName();
        String deviceName = userInfo.getDeviceName();
        String userName = userInfo.getUserName();
        String userPassword = userInfo.getUserPassword();
//        String deviceToken = userInfo.getDeviceToken();

        if (TextUtils.isEmpty(clientId) ||
                TextUtils.isEmpty(host) ||
                TextUtils.isEmpty(userName)) {
            Log.e(TAG, "Invalid info!");
            if (stateListener != null) {
                stateListener.onConnected(false);
            }
            return;
        }
        simple = new Simple(this.context, serviceName, deviceName, userName,
                new tp.skt.simple.net.mqtt.SimpleConfiguration(host, clientId, userName, userPassword),
                simpleListener, true);
        simple.tpSimpleConnect();
    }

    /**
     * disconnect
     */
    public void disconnect() {
        if (simple != null) {
            simple.tpSimpleDisconnect();
        }
    }

    /**
     * send device info
     * @param arrayElement device info list
     */
    public void sendAttribute(ArrayElement arrayElement) {
        if (simple == null || simple.tpSimpleIsConnected() == false) {
            if(reportMessageDelivered == false) {
                Log.e(TAG, "not delivered!");
            }
            return;
        }

        simple.tpSimpleAttribute(arrayElement, new tp.skt.simple.net.mqtt.SimpleCallback() {
            @Override
            public void onResponse(Object o) {
                Log.i(TAG, "sendAttribute success");
            }

            @Override
            public void onFailure(int errorCode, String message) {
                Log.e(TAG, errorCode + " : " + message);
            }
        });
    }

    /**
     * set attribute
     * @param attribute set attribute text
     */
    public void setAttribute(int cmdId, ArrayElement attribute, SimpleCallback callback) {
        if (simple == null || simple.tpSimpleIsConnected() == false) {
            if(reportMessageDelivered == false) {
                Log.e(TAG, "not delivered!");
            }
            return;
        }
        simple.tpSimpleSetAttribute(cmdId, attribute, callback);
    }

    /**
     * set activate
     *
     * @param controlParams
     * @param cmdId
     * @param callback
     */
    public void setActivate(ArrayElement controlParams, int cmdId, SimpleCallback callback) {
        if (simple == null || simple.tpSimpleIsConnected() == false) {
            if(reportMessageDelivered == false) {
                Log.e(TAG, "not delivered!");
            }
            return;
        }
        simple.tpSimpleJsonRpcReq(controlParams, "tp_user", cmdId, false, callback);
    }

    /**
     * sendTelemetry sensor infos
     * @param arrayElement contents for reporting
     */
    public void sendTelemetry(ArrayElement arrayElement) {
        if (simple == null || simple.tpSimpleIsConnected() == false) {
            if(reportMessageDelivered == false) {
                Log.e(TAG, "not delivered!");
            }
            return;
        }

        simple.tpSimpleTelemetry(arrayElement, false, new tp.skt.simple.net.mqtt.SimpleCallback() {
            @Override
            public void onResponse(Object o) {
                Log.i(TAG, "sendTelemetry success");
            }

            @Override
            public void onFailure(int errorCode, String message) {
                Log.e(TAG, errorCode + " : " + message);
            }
        });
    }

    /**
     * monitoring
     * @param subscribe
     */
    public void subscribe(Subscribe subscribe) {
        if (simple == null || simple.tpSimpleIsConnected() == false) {
            if(reportMessageDelivered == false) {
                Log.e(TAG, "not delivered!");
            }
            return;
        }

        simple.tpSimpleSubscribe(subscribe, new tp.skt.simple.net.mqtt.SimpleCallback() {
            @Override
            public void onResponse(Object o) {
                Log.i(TAG, "subscribe success");
            }

            @Override
            public void onFailure(int errorCode, String message) {
                Log.e(TAG, errorCode + " : " + message);
            }
        });
    }

    public int getCmdId() {
        if(cmdId > Integer.MAX_VALUE) {
            cmdId = 2;
        } else {
            cmdId++;
        }
        return cmdId;
    }

    SimpleListener simpleListener = new SimpleListener() {
        @Override
        public void onPush(String message) {
            Log.e(TAG, "onPush : " + message);
            if(stateListener != null) {
                stateListener.onMessageReceived(message);
//                StateListener.RESULT controlResult = stateListener.onReceiveCommand(message);
//                if (controlResult != StateListener.RESULT.SUSPEND) {
//                    simpleWorker.controlResult(control.getNm(), control.getRi(), controlResult == StateListener.RESULT.SUCCESS);
//                }
            }
        }

        @Override
        public void onConnected() {
            Log.e(TAG, "connected");
        }

        @Override
        public void onDisconnected() {
            if(stateListener != null) {
                stateListener.onDisconnected(true);
            }
        }

        @Override
        public void onSubscribed() {
            Log.e(TAG, "subscribed");
            UserInfo.getInstance(context).setRegisterState(true);
            if (stateListener != null) {
                stateListener.onConnected(true);
            }
            reportMessageDelivered = true;
        }

        @Override
        public void onSubscribeFailure() {
            Log.e(TAG, "subscribe failure!");
            if (stateListener != null) {
                stateListener.onConnected(false);
            }
        }

        @Override
        public void onConnectFailure() {
            Log.e(TAG, "connect fail!");
            if (stateListener != null) {
                stateListener.onConnected(false);
            }
        }

        @Override
        public void onDisconnectFailure() {
            if(stateListener != null) {
                stateListener.onDisconnected(false);
            }
        }

        @Override
        public void onConnectionLost() {

        }

        @Override
        public void onDelivered() {

        }
    };
}
