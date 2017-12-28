package tp.skt.simple.net.mqtt;

import android.content.Context;
import android.text.TextUtils;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Arrays;

import tp.skt.simple.common.Define;
import tp.skt.simple.common.Util;

/**
 * MQTT client
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class MQTTClient {
    /**
     * Context
     **/
    private Context context;
    private String baseUrl;
    private String clientID;
    private String userName;
    private String password;
    private String version;

    /**
     * MqttAndroidClient
     **/
    private MqttAndroidClient mqttAndroidClient;
    /**
     * Subscribe Topics
     **/
    private String[] subscribeTopics;
    /**
     * SimpleListener
     **/
    private SimpleListener simpleListener;

    /**
     * MQTTClient constructor
     *
     * @param context
     * @param baseUrl
     * @param clientID
     * @param userName
     * @param password
     * @param version  if value is null, default value is 1.0
     */
    MQTTClient(Context context, String baseUrl, String clientID, String userName, String password, String version, String[] subscribeTopics) {
        this.context = context.getApplicationContext();
        this.baseUrl = baseUrl;
        this.clientID = clientID;
        this.userName = userName;
        this.password = password;
        this.version = (version == null ? Define.VERSION : version);
        this.subscribeTopics = subscribeTopics;
    }

    /**
     * disconnect
     */
    public void disconnect() {
        if (mqttAndroidClient != null && mqttAndroidClient.isConnected()) {
            try {
                mqttAndroidClient.disconnect(null, null);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     *
     */
    public void destroy() {
        if (mqttAndroidClient != null) {
            mqttAndroidClient.unregisterResources();
            mqttAndroidClient.close();
            Util.log("destroy");
        }
    }

    /**
     * connect server
     *
     * @return
     */
    public void connect(final SimpleListener simpleListener) {
        this.simpleListener = simpleListener;
        // MQTT Client 생성및 설정.
        this.mqttAndroidClient = new MqttAndroidClient(context, baseUrl, clientID);
        this.mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (reconnect == true) {
                    Util.log("Reconnected to : " + serverURI);
                    // Because Clean Session is true, we need to re-subscribe
                    // subscribeTopic();
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                if (cause != null) {
                    Util.log(cause.getMessage());
                }
                if (simpleListener != null) {
                    simpleListener.onConnectionLost();
                }
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if (message.isDuplicate() == true) {
                    Util.log("message duplicated!");
                    return;
                }
                String receivedMessage = message.toString();
                Util.log("messageArrived : " + topic + " - " + receivedMessage + this.toString());
                simpleListener.onPush(receivedMessage);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Util.log("message delivered");
                simpleListener.onDelivered();
            }
        });


        final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setAutomaticReconnect(true);

        if (this.userName != null) {
            Util.log("userName : " + this.userName);
            mqttConnectOptions.setUserName(this.userName);
        }
        if (this.password != null) {
            Util.log("password : " + this.password);
            mqttConnectOptions.setPassword(this.password.toCharArray());
        }

        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    simpleListener.onConnected();
                    subscribeTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    if (exception != null) {
                        exception.printStackTrace();
                    }
                    simpleListener.onConnectFailure();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            simpleListener.onConnectFailure();
        }
    }

    /**
     * subscribe topic
     */
    private void subscribeTopic() {
        if (mqttAndroidClient == null || mqttAndroidClient.isConnected() == false || subscribeTopics == null) {
            simpleListener.onSubscribeFailure();
            return;
        }
        try {
            int[] qosArray = new int[subscribeTopics.length];
            for (int qos : qosArray) {
                qos = 1;
            }
            mqttAndroidClient.subscribe(subscribeTopics, qosArray, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Util.log(Arrays.toString(asyncActionToken.getTopics()));
                    simpleListener.onSubscribed();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Util.log(Arrays.toString(asyncActionToken.getTopics()));
                    simpleListener.onSubscribeFailure();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            simpleListener.onSubscribeFailure();
        }
    }

    /**
     * publish message
     *
     * @param topic
     * @param payload
     * @param callBack
     * @throws MqttException
     */
    public void publish(String topic, String payload, final SimpleCallback callBack) throws MqttException {
        MqttMessage message = new MqttMessage();
        message.setPayload(payload.getBytes());
        Util.log("publishTopic : " + topic);
//        Util.log("publishMessage : " + payload);
        while (payload.length() > 0) {
            if (payload.length() > 4000) {
                Util.log(payload.substring(0, 4000));
                payload = payload.substring(4000);
            } else {
                Util.log(payload);
                break;
            }
        }

        mqttAndroidClient.publish(topic, message, context, new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Util.log("message publish success");
                callBack.onResponse(asyncActionToken.getMessageId());
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                String message = "";
                if (exception != null) {
                    message = exception.getMessage();
                    Util.log(message);
                }
                callBack.onFailure(asyncActionToken.getMessageId(), message);
            }
        });
    }

    /**
     * MQTT is connected
     *
     * @return true : connected <-> false
     */
    public boolean isMQTTConnected() {
        if (mqttAndroidClient == null) {
            return false;
        }
        return mqttAndroidClient.isConnected();
    }

    /**
     * MQTTClient builder
     */
    public static class Builder {
        private String baseUrl;
        private String clientId;
        private String userName;
        private String password;
        private String version;
        private String[] subscribeTopics;
        private Context context;


        /**
         * @param context
         */
        public Builder(Context context) {
            this.context = context.getApplicationContext();
        }

        /**
         * @param baseUrl
         * @return
         */
        public Builder baseUrl(String baseUrl) {
            Util.checkNull(baseUrl, "baseUrl = null");
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * @param clientId
         * @return
         */
        public Builder clientId(String clientId) {
            Util.checkNull(clientId, "clientID = null");
            this.clientId = clientId;
            return this;
        }

        /**
         * @param userName
         * @return
         */
        public Builder userName(String userName) {
            Util.checkNull(userName, "userName = null");
            this.userName = userName;
            return this;
        }

        /**
         * @param password
         * @return
         */
        public Builder password(String password) {
//            Util.checkNull(password, "password = null");
            this.password = password;
            return this;
        }

        /**
         * @param enabled
         * @return
         */
        public Builder setLog(boolean enabled) {
            Util.setLogEnabled(enabled);
            return this;
        }

        /**
         * set API version info
         *
         * @param version
         * @return Builder
         */
        public Builder setVersion(String version) {
            if (TextUtils.isEmpty(version) == false) {
                this.version = version;
            } else {
                throw new IllegalArgumentException("Invalid version value!");
            }
            return this;
        }

        /**
         * set subscribe topics
         *
         * @param subscribeTopics
         * @return Builder
         */
        public Builder setSubscribeTopics(String[] subscribeTopics) {
            if (subscribeTopics != null) {
                this.subscribeTopics = subscribeTopics;
            }
            return this;
        }

        /**
         * @return
         */
        public MQTTClient build() {
            Util.checkNull(baseUrl, "baseUrl = null");
            return new MQTTClient(context, baseUrl, clientId, userName, password, version, subscribeTopics);
        }
    }
}
