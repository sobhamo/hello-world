package tp.skt.simple.api;

/**
 * ResultListener.java
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public abstract class ResultListener {

    /**
     * result
     *
     * @param response
     */
    public abstract void onResponse(int response);

    /**
     * error with code & message
     *
     * @param errorCode
     * @param message
     */
    public abstract void onFailure(int errorCode, String message);
}
