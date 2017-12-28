package com.skt.thingplug_v2_0_service.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.skt.thingplug_v2_0_service.Const;
import com.skt.thingplug_v2_0_service.data.SensorType;

/**
 * user information
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class UserInfo {
    private static final String SHARED_PREFERENCE_NAME = "Simple";

    private static final String KEY_REGISTER_STATE = "register";

    private static final String KEY_SERVICE = "service";
    private static final String KEY_DEVICE = "device";
    private static final String KEY_DEVICE_TOKEN = "deviceToken";

    private static final String KEY_PORTAL = "portal";
    private static final String KEY_SERVER = "server";

    private static final String KEY_SETTING_READ = "readInterval";
    private static final String KEY_SETTING_REPORT = "reportInterval";
    private static final String KEY_SETTING_LIST = "listInterval";
    private static final String KEY_SETTING_GRAPH = "graphInterval";

    private static final String KEY_SETTING_USE_TLS = "useTLS";

    private static final String KEY_AGREE_TERMS = "agreeTerms";

    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_PASSWORD = "userPassword";

    private static SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;

    private static UserInfo userInfo;

    private boolean showContent;

    /**
     * get UserInfo
     *
     * @param context
     * @return
     */
    public static UserInfo getInstance(Context context) {
        if (userInfo == null) {
            userInfo = new UserInfo(context);
        }
        return userInfo;
    }

    /**
     *
     *
     * @return
     */
    public boolean isShowContent() {
        return showContent;
    }

    /**
     *
     *
     * @param showContent
     */
    public void setShowContent(boolean showContent) {
        this.showContent = showContent;
    }

    /**
     * set register state
     *
     * @param isRegistered is registered ?
     */
    public void setRegisterState(boolean isRegistered) {
        editor.putBoolean(KEY_REGISTER_STATE, isRegistered);
        editor.commit();
    }

    /**
     * get register state
     *
     * @return true : registered <-> false
     */
    public boolean getRegisterState() {
        return sharedPreference.getBoolean(KEY_REGISTER_STATE, false);
    }

    /**
     * constructor
     *
     * @param context context
     */
    public UserInfo(Context context) {
        sharedPreference = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
    }

    /**
     * get service
     *
     * @return service
     */
    public String getServiceName() {
        return sharedPreference.getString(KEY_SERVICE, "");
    }

    /**
     * save service
     *
     * @param service
     */
    public void setServiceName(String service) {
        editor.putString(KEY_SERVICE, service);
        editor.commit();
    }

    /**
     * get device
     *
     * @return device
     */
    public String getDeviceName() {
        return sharedPreference.getString(KEY_DEVICE, "");
    }

    /**
     * save device
     *
     * @param device
     */
    public void setDeviceName(String device) {
        editor.putString(KEY_DEVICE, device);
        editor.commit();
    }

    /**
     * get device token
     *
     * @return device
     */
    public static String getDeviceToken() {
        return sharedPreference.getString(KEY_DEVICE_TOKEN, "");
    }

    /**
     * save device token
     *
     * @param deviceToken
     */
    public void setDeviceToken(String deviceToken) {
        editor.putString(KEY_DEVICE_TOKEN, deviceToken);
        editor.commit();
    }

    /**
     * get portal
     *
     * @return portal
     */
    public static String getPortal() {
        return sharedPreference.getString(KEY_PORTAL, Const.HOST_THINGPLUG_PORTAL_DEFAULT);
    }

    /**
     * save portal host
     *
     * @param portal
     */
    public void setPortal(String portal) {
        editor.putString(KEY_PORTAL, portal);
        editor.commit();
    }

    /**
     * get server
     *
     * @return server
     */
    public String getServer() {
        return sharedPreference.getString(KEY_SERVER, Const.HOST_THINGPLUG_SERVER_DEFAULT);
    }

    /**
     * save server host
     *
     * @param server
     */
    public void setServer(String server) {
        editor.putString(KEY_SERVER, server);
        editor.commit();
    }

    /**
     * get read interval
     *
     * @return read interval
     */
    public int getReadInterval() {
        return sharedPreference.getInt(KEY_SETTING_READ, Const.SENSOR_DEFAULT_READ_PERIOD);
    }

    /**
     * save read interval
     *
     * @param interval read interval
     */
    public void setReadInterval(int interval) {
        editor.putInt(KEY_SETTING_READ, interval);
        editor.commit();
    }

    /**
     * get sendTelemetry interval
     *
     * @return sendTelemetry interval
     */
    public int getReportInterval() {
        return sharedPreference.getInt(KEY_SETTING_REPORT, Const.SENSOR_DEFAULT_REPORT_INTERVAL);
    }

    /**
     * save sendTelemetry interval
     *
     * @param interval sendTelemetry interval
     */
    public void setReportInterval(int interval) {
        editor.putInt(KEY_SETTING_REPORT, interval);
        editor.commit();
    }

    /**
     * get list interval
     *
     * @return list interval
     */
    public int getListInterval() {
        return sharedPreference.getInt(KEY_SETTING_LIST, Const.SENSOR_DEFAULT_LIST_UPDATE_INTERVAL);
    }

    /**
     * save list update interval
     *
     * @param interval list update interval
     */
    public void setListInterval(int interval) {
        editor.putInt(KEY_SETTING_LIST, interval);
        editor.commit();
    }

    /**
     * get graph interval
     *
     * @return graph interval
     */
    public int getGraphInterval() {
        return sharedPreference.getInt(KEY_SETTING_GRAPH, Const.SENSOR_DEFAULT_GRAPH_UPDATE_INTERVAL);
    }

    /**
     * save graph update interval
     *
     * @param interval graph update interval
     */
    public void setGraphInterval(int interval) {
        editor.putInt(KEY_SETTING_GRAPH, interval);
        editor.commit();
    }

    /**
     * save useTLS
     *
     * @param useTLS useTLS
     */
    public void setUseTLS(boolean useTLS) {
        editor.putBoolean(KEY_SETTING_USE_TLS, useTLS);
        editor.commit();
    }

    /**
     * load useTLS
     *
     * @return useTLS
     */
    public boolean getUseTLS() {
        return sharedPreference.getBoolean(KEY_SETTING_USE_TLS, Const.USE_TLS_DEFAULT);
    }

    /**
     * get agree terms
     *
     * @return agree terms
     */
    public boolean getAgreeTerms() {
        return sharedPreference.getBoolean(KEY_AGREE_TERMS, false);
    }

    /**
     * save agreeTerms
     *
     * @param agreeTerms agreeTerms
     */
    public void setAgreeTerms(boolean agreeTerms) {
        editor.putBoolean(KEY_AGREE_TERMS, agreeTerms);
        editor.commit();
    }

    /**
     * load sensor status
     *
     * @param type sensor type
     * @return sensor status
     */
    public boolean getSensorStatus(SensorType type) {
        return sharedPreference.getBoolean(type.name(), true);
    }

    /**
     * save sensor status
     *
     * @param type   sensor type
     * @param enable status
     */
    public void setSensorStatus(SensorType type, boolean enable) {
        editor.putBoolean(type.name(), enable);
        editor.commit();
    }

    /**
     * get user name
     *
     * @return user name
     */
    public String getUserName() {
        return sharedPreference.getString(KEY_USER_NAME, "");
    }

    /**
     * save user name
     *
     * @param userName user name
     */
    public void setUserName(String userName) {
        editor.putString(KEY_USER_NAME, userName);
        editor.commit();
    }

    /**
     * get user password
     *
     * @return user password
     */
    public String getUserPassword() {
        return sharedPreference.getString(KEY_USER_PASSWORD, "");
    }

    /**
     * save user password
     *
     * @param userPassword user password
     */
    public void setUserPassword(String userPassword) {
        editor.putString(KEY_USER_PASSWORD, userPassword);
        editor.commit();
    }

    /**
     * clear
     *
     * @param withServerInfo
     */
    public void clear(boolean withServerInfo) {
        editor.remove(KEY_REGISTER_STATE);
        editor.remove(KEY_SERVICE);
        editor.remove(KEY_DEVICE);
        editor.remove(KEY_DEVICE_TOKEN);

        editor.remove(KEY_SETTING_READ);
        editor.remove(KEY_SETTING_REPORT);
        editor.remove(KEY_SETTING_LIST);
        editor.remove(KEY_SETTING_GRAPH);

        editor.remove(KEY_AGREE_TERMS);
        editor.remove(KEY_USER_NAME);
        editor.remove(KEY_USER_PASSWORD);

        if (withServerInfo == true) {
            editor.remove(KEY_PORTAL);
            editor.remove(KEY_SERVER);
            editor.remove(KEY_SETTING_USE_TLS);
        }

        for (SensorType sensorType : SensorType.values()) {
            if (sensorType != SensorType.NONE) {
                editor.remove(sensorType.name());
            }
        }
        editor.commit();
    }
}