package tp.skt.simple.net.mqtt;

/**
 * MQTT request callback
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public abstract class SimpleCallback<T> {

    /**
     * result
     *
     * @param response
     */
    public abstract void onResponse(T response);

    /**
     * error with code & message
     *
     * @param errorCode
     * @param message
     */
    public abstract void onFailure(int errorCode, String message);
}
