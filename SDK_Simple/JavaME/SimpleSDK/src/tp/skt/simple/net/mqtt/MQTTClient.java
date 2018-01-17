package tp.skt.simple.net.mqtt;



import tp.skt.simple.api.ConnectionListener;
import tp.skt.simple.api.ResultListener;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import tp.java.util.Arrays;
import tp.java.util.TextUtils;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import tp.java.util.Log;

import tp.skt.simple.common.Define;
import tp.skt.simple.common.Util;

/**
 * MQTT client
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class MQTTClient {
    private String baseUrl;
    private String clientID;
    private String userName;
    private String password;
    private String version;
    private int keepAlive;
    
    /** MqttAndroidClient **/
    private  MqttClient mqttClient;
    /** Subscribe Topics **/
    private String[] subscribeTopics;
    /** MQTTListener **/
    private ConnectionListener mqttListener;
    
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
    MQTTClient(String baseUrl, String clientID, String userName, String password, String version, String[] subscribeTopics, int keepAlive) {
        this.baseUrl = baseUrl;
        this.clientID = clientID;
        this.userName = userName;
        this.password = password;
        this.version = (version == null ? Define.VERSION : version);
        this.subscribeTopics = subscribeTopics;
        this.keepAlive = keepAlive;
    }
    
    /**
     * disconnect
     *
     */
    public void disconnect() {
        if (mqttClient != null && mqttClient.isConnected()) {
            // TODO : change code
            // mqttClient.disconnect(null, null);
            try {
                mqttClient.disconnect();
            } catch (MqttException ex) {
                Log.print(MQTTClient.class.getName(), ex.toString());
                //Logger.getLogger(MQTTClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     *
     *
     */
    public void destroy() {
        if(mqttClient != null) {
            try {
                // TODO : Check, code
//            mqttClient.unregisterResources();
mqttClient.close();
            } catch (MqttException ex) {
                Log.print(MQTTClient.class.getName(), ex.toString());
                //Logger.getLogger(MQTTClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            Util.log("destroy");
        }
    }
    
    /**
     * connect server
     *
     * @return
     */
    public void connect(final ConnectionListener mqttListener) {
        try {
            this.mqttListener = mqttListener;
            // MQTT Client 생성및 설정.
            this.mqttClient = new MqttClient(baseUrl, clientID);
            
            this.mqttClient.setCallback(new MqttCallbackExtended() {

                public void connectComplete(boolean reconnect, String serverURI) {
                    if (reconnect == true) {
                        Util.log("Reconnected to : " + serverURI);
                        // Because Clean Session is true, we need to re-subscribe
                         subscribeTopic();
                    }
                }
                

                public void connectionLost(Throwable cause) {
                    if (cause != null) {
                        Util.log(cause.getMessage());
                    }
                    if (mqttListener != null) {
                        mqttListener.onConnectionLost();
                    }
                }
                

                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    if (message.isDuplicate() == true) {
                        Util.log("message duplicated!");
                        return;
                    }
                    String receivedMessage = message.toString();
                    Util.log("messageArrived : " + topic + " - " + receivedMessage );
                    
                     if (mqttListener != null) {
                        mqttListener.onMessageReceived(topic, receivedMessage);
                    }
                }
                

                public void deliveryComplete(IMqttDeliveryToken token) {
                    Util.log("message delivered");
                    mqttListener.onDelivered();
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
            
            mqttConnectOptions.setKeepAliveInterval(this.keepAlive);
            
//            mqttClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
//                @Override
//                public void onSuccess(IMqttToken asyncActionToken) {
//                    mqttListener.onConnected();
//                    subscribeTopic();
//                }
//
//                @Override
//                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//                    if (exception != null) {
//                        exception.printStackTrace();
//                    }
//                    mqttListener.onConnectFailure();
//                }
//            });

            mqttClient.connect(mqttConnectOptions, new IMqttActionListener() {

                public void onSuccess(IMqttToken asyncActionToken) {
                    mqttListener.onConnected();
                    subscribeTopic();
                }


                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    if (exception != null) {
                        exception.printStackTrace();
                    }
                    mqttListener.onConnectFailure();
                }
            });
        } catch (MqttException ex) {
            Log.print(MQTTClient.class.getName(), ex.toString());
            //Logger.getLogger(MQTTClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * subscribe topic
     *
     */
    private void subscribeTopic() {
        if (mqttClient == null || mqttClient.isConnected() == false || subscribeTopics == null) {
            mqttListener.onSubscribeFailure();
            return;
        }
        try {
            int size = subscribeTopics.length;
            int[] qosArray = new int[subscribeTopics.length];
            for(int index = 0; index < size; index++){
                qosArray[index] = 1;
            }            
            mqttClient.subscribe(subscribeTopics, qosArray, new IMqttActionListener() {

                public void onSuccess(IMqttToken asyncActionToken) {
                     // TODO : Exception 발생 
//                    Util.log(Arrays.toString(asyncActionToken.getTopics())); 
                    mqttListener.onSubscribed();
                }
                

                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Util.log(Arrays.toString(asyncActionToken.getTopics()));
                    mqttListener.onSubscribeFailure();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            mqttListener.onSubscribeFailure();
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
    public void publish(String topic, String payload, final ResultListener callBack) throws MqttException {
        MqttMessage message = new MqttMessage();
        message.setPayload(payload.getBytes());
        Util.log("publishTopic : " + topic);
        Util.log("publishMessage : " + payload);
        
        
        mqttClient.publish(topic, message, new IMqttActionListener() {

            public void onSuccess(IMqttToken asyncActionToken) {
                Util.log("message publish success");
                callBack.onResponse(asyncActionToken.getMessageId());
            }
            

            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                String message = "";
                if(exception != null) {
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
        if (mqttClient == null) {
            return false;
        }
        return mqttClient.isConnected();
    }
    
    /**
     * MQTTClient builder
     *
     */
    public static class Builder {
        private String baseUrl;
        private int keepAlive;
        private String clientId;
        private String userName;
        private String password;
        private String version;
        private String[] subscribeTopics;
        
        
        /**
         *
         *
         * @param context
         */
        public Builder() {
        }
        
        /**
         *
         *
         * @param baseUrl
         * @return
         */
        public Builder baseUrl(String baseUrl) {
            Util.checkNull(baseUrl, "baseUrl = null");
            this.baseUrl = baseUrl;
            return this;
        }
        
         /**
         *
         *
         * @param keepAlive
         * @return
         */
        public Builder keepAlive(int keepAlive) {
            this.keepAlive = keepAlive;
            return this;
        }
        
        /**
         *
         *
         * @param clientId
         * @return
         */
        public Builder clientId(String clientId) {
            Util.checkNull(clientId, "clientID = null");
            this.clientId = clientId;
            return this;
        }
        
        /**
         *
         *
         * @param userName
         * @return
         */
        public Builder userName(String userName) {
            Util.checkNull(userName, "userName = null");
            this.userName = userName;
            return this;
        }
        
        /**
         *
         *
         * @param password
         * @return
         */
        public Builder password(String password) {
//            Util.checkNull(password, "password = null");
this.password = password;
return this;
        }
        
        /**
         *
         *
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
         *
         *
         * @return
         */
        public MQTTClient build() {
            Util.checkNull(baseUrl, "baseUrl = null");
            return new MQTTClient( baseUrl, clientId, userName, password, version, subscribeTopics, keepAlive);
        }
    }
}
