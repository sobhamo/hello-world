package com.skt.thingplug_v2_0_device;

import android.hardware.Sensor;

/**
 * default values for application
 *
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class Const {
    // ThingPlug URLs
    private static final int GET_LENGTH_MAX = 10;
    public static final int MAX_CLIENT_ID_LENGTH = 24;

    public static final String HOST_THINGPLUG_PORTAL_DEFAULT = "http://(TBD):9000";
    public static final String HOST_THINGPLUG_SERVER_DEFAULT = "(TBD)";

    public static final String URL_JOIN_THINGPLUG = "%s/join";
    public static final String URL_LOGIN_DEFAULT = "%s/api/v1/login";
    public static final String URL_GET_SERVICE_LIST_DEFAULT = "%s/api/v1/users/%s/services?limit=" + GET_LENGTH_MAX;
    public static final String URL_GET_DEVICE_LIST_DEFAULT = "%s/api/v1/services/%s/devices?limit=" + GET_LENGTH_MAX;
    public static final String URL_GET_DEVICE_TOKEN_DEFAULT = "%s/api/v1/services/%s/devices/%s/credential";

    public static final boolean USE_TLS_DEFAULT = false;
    // read time delay (msec)
    public static final int SENSOR_DEFAULT_READ_PERIOD = 1000;
    public static final int SENSOR_DEFAULT_REPORT_INTERVAL = 10000;
    public static final int SENSOR_DEFAULT_LIST_UPDATE_INTERVAL = 1000;
    public static final int SENSOR_DEFAULT_GRAPH_UPDATE_INTERVAL = 1000;
    public static final int SENSOR_MIN_READ_PERIOD = 100;
    public static final int SENSOR_MIN_TRANSFER_INTERVAL = 1000;
    public static final int SENSOR_MIN_LIST_UPDATE_INTERVAL = 1000;
    public static final int SENSOR_MIN_GRAPH_UPDATE_INTERVAL = 100;

    // sensor type definition
    public static final int SENSOR_TYPE_BATTERY = Sensor.TYPE_DEVICE_PRIVATE_BASE + 1;
    public static final int SENSOR_TYPE_GPS = Sensor.TYPE_DEVICE_PRIVATE_BASE + 2;
    public static final int SENSOR_TYPE_BUZZER = Sensor.TYPE_DEVICE_PRIVATE_BASE + 3;
    public static final int SENSOR_TYPE_LED = Sensor.TYPE_DEVICE_PRIVATE_BASE + 4;
    public static final int SENSOR_TYPE_CAMERA = Sensor.TYPE_DEVICE_PRIVATE_BASE + 5;
    public static final int SENSOR_TYPE_NOISE = Sensor.TYPE_DEVICE_PRIVATE_BASE + 6;
}
