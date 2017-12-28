package tp.skt.simple.net.mqtt;

/**
 * SimpleListener.java
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public abstract class SimpleListener {
    /**
     * 서버에서 전달된 메시지 수신
     *
     * @param message
     */
    public abstract void onPush(String message);

    /**
     * 서버와 연결됨
     */
    public abstract void onConnected();

    /**
     * 서버와 연결 해제됨
     */
    public abstract void onDisconnected();

    /**
     * subscribe 성공
     */
    public abstract void onSubscribed();

    /**
     * subscribe 실패
     */
    public abstract void onSubscribeFailure();

    /**
     * 서버와 연결 실패
     */
    public abstract void onConnectFailure();

    /**
     * 서버와 연결 해제 실패
     */
    public abstract void onDisconnectFailure();

    /**
     * 서버와 연결을 잃음
     */
    public abstract void onConnectionLost();

    /**
     * 메시지 전달됨
     */
    public abstract void onDelivered();
}
