package tp.skt.simple.api;




import tp.java.util.Log;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import tp.skt.simple.common.Define;
import tp.skt.simple.common.Util;
import tp.skt.simple.element.ArrayElement;
import tp.skt.simple.element.BooleanElement;
import tp.skt.simple.element.DoubleElement;
import tp.skt.simple.element.FloatElement;
import tp.skt.simple.element.IntElement;
import tp.skt.simple.element.LongElement;
import tp.skt.simple.element.RPCResponse;
import tp.skt.simple.element.StringElement;
import tp.skt.simple.element.Subscribe;
import tp.skt.simple.net.mqtt.MQTTClient;

/**
 * Simple.java
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class Simple {
    
    /** MQTT client **/
    private MQTTClient mqttClient;
    /** MQTT listener **/
    private ConnectionListener mqttListener;
    
    /** Service name **/
    private String serviceName;
    /** Device name **/
    private String deviceName;
    
    /** added data **/
    private String addedData = "";
    
    /**
     * Simple constructor
     *
     * @param context
     * @param serviceName
     * @param deviceName
     * @param configuration
     * @param connectionListener
     * @param logEnabled
     */
    public Simple( String serviceName, String deviceName, Configuration configuration, ConnectionListener connectionListener, boolean logEnabled) {
        this.serviceName = serviceName;
        this.deviceName = deviceName;
        
        // make subscribeTopic
        Vector subscribeTopicList = new Vector();
        if(deviceName != null) {
            subscribeTopicList.addElement("v1/dev/" + serviceName + "/" + deviceName + "/down");
        }
        
        int size = subscribeTopicList.size();
        String[] subscribeTopics = new String[subscribeTopicList.size()];
        for(int index= 0; index< size; index++){
            subscribeTopics[index] =  (String) subscribeTopicList.elementAt(index);
        }
        // create mqtt client
        this.mqttClient = new MQTTClient.Builder()
                .baseUrl(configuration.getServerAddress() + ":" + configuration.getServerPort())
                .keepAlive(configuration.getKeepAlive())
                .clientId(configuration.getClientId())
                .userName(configuration.getLoginName())
                .password(configuration.getLoginPassword())
                .setSubscribeTopics(subscribeTopics)
                .setLog(logEnabled).build();
        this.mqttListener = connectionListener;
    }
    
    
    /**
     * connect server
     *
     */
    public void tpSimpleConnect() {
        if(mqttClient == null || mqttListener == null) {
            return;
        }
        mqttClient.connect(mqttListener);
    }
    
    /**
     * disconnect server
     *
     */
    public void tpSimpleDisconnect() {
        if(mqttClient != null) {
            mqttClient.disconnect();
        }
    }
    
    /**
     * destroy ThingPlug SDK
     *
     */
    public void tpSimpleDestroy() {
        if(mqttClient != null) {
            mqttClient.destroy();
        }
    }
    
    /**
     * MQTT is connected
     *
     * @return true : connected <-> false
     */
    public boolean tpSimpleIsConnected() {
        if(mqttClient != null) {
            return mqttClient.isMQTTConnected();
        }
        return false;
    }
    
    /**
     * add element by type
     *
     * @param jsonObject
     * @param element
     * @return
     */
    private boolean addElement(JSONObject jsonObject, Object element) {
        if(element == null) {
            return false;
        }
        
        // TODO : addProperty to put !!
        try {
            if(element instanceof BooleanElement){
                jsonObject.put(((BooleanElement)element).name, ((BooleanElement)element).value);
            }else  if(element instanceof DoubleElement){
                jsonObject.put(((DoubleElement)element).name, ((DoubleElement)element).value);
            }else  if(element instanceof FloatElement){
                jsonObject.put(((FloatElement)element).name, ((FloatElement)element).value);
            }else  if(element instanceof IntElement){
                jsonObject.put(((IntElement)element).name, ((IntElement)element).value);
            }else  if(element instanceof LongElement){
                jsonObject.put(((LongElement)element).name, ((LongElement)element).value);
            }else  if(element instanceof StringElement){
                jsonObject.put(((StringElement)element).name, ((StringElement)element).value);
            }else{
                return false;
            }
        } catch (JSONException ex) {
            Log.print(Simple.class.getName(), ex.toString());
        }
        return true;
        
    }
    
    /**
     * data add for telemetry
     *
     * @param data data
     */
    public void tpSimpleAddData(String data) {
        this.addedData += data;
    }
    
    /**
     * device telemetry
     *
     * @param telemetry
     * @param useAddedData
     * @param listener
     */
    public void tpSimpleTelemetry(ArrayElement telemetry, final boolean useAddedData, final ResultListener listener) {
        try {
            //String topic = String.format("v1/dev/%s/%s/telemetry", serviceName, deviceName);
            String topic = "v1/dev/"+serviceName+"/"+deviceName+"/telemetry";
            String payload = null;
            if(useAddedData) {
                payload = addedData;
                addedData = "";
            } else {
                JSONObject jsonObject = new JSONObject();
                int size = telemetry.elements.size();
                for(int index = 0; index < size ; index++){
                    Object element = telemetry.elements.elementAt(index);
                    boolean result = addElement(jsonObject, element);
                    if (result == false) {
                        throw new Exception("Bad element!");
                    }
                }
                payload = jsonObject.toString();
                Util.log("\n" + topic + "\n" + payload);
            }
            mqttClient.publish(topic, payload, listener);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFailure(Define.INTERNAL_SDK_ERROR, e.getMessage());
        }
    }
    
    public void tpSimpleRawTelemetry(String telemetry, DataFormat format, final ResultListener listener){
         try {
            String topic = "v1/dev/"+serviceName+"/"+deviceName+"/telemetry";
            if(format.value() == DataFormat.DataValue.VALUE_CSV){
                topic = topic + "/csv";
            }else if(format.value() == DataFormat.DataValue.VALUE_OFFSET){
                topic = topic + "/offset";
            }
            
            mqttClient.publish(topic, telemetry, listener);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFailure(Define.INTERNAL_SDK_ERROR, e.getMessage());
        }
    }
    
    /**
     * device attribute
     *
     * @param attribute
     * @param listener
     */
    public void tpSimpleAttribute(ArrayElement attribute, final ResultListener listener) {
        try {
            //String topic = String.format("v1/dev/%s/%s/attribute", serviceName, deviceName);
            String topic = "v1/dev/"+ serviceName +"/"+ deviceName +"/attribute";
            JSONObject jsonObject = new JSONObject();
            
            int size = attribute.elements.size();
            for(int index = 0; index < size ; index++){
                Object element = attribute.elements.elementAt(index);
                boolean result = addElement(jsonObject, element);
                if(result == false) {
                    throw new Exception("Bad element!");
                }
            }
            String payload = jsonObject.toString();
            Util.log("\n" + topic + "\n" + payload);
            mqttClient.publish(topic, payload, listener);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFailure(Define.INTERNAL_SDK_ERROR, e.getMessage());
        }
    }
    
    
    public void tpSimpleRawAttribute(String attribute, DataFormat format, final ResultListener listener){
         try {
            String topic = "v1/dev/"+ serviceName +"/"+ deviceName +"/attribute";
            if(format.value() == DataFormat.DataValue.VALUE_CSV){
                topic = topic + "/csv";
            }else if(format.value() == DataFormat.DataValue.VALUE_OFFSET){
                topic = topic + "/offset";
            }
            
            mqttClient.publish(topic, attribute, listener);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFailure(Define.INTERNAL_SDK_ERROR, e.getMessage());
        }
    }
    
    /**
     * device RPC control result
     *
     * @param response
     * @param listener
     */
    public void tpSimpleResult(RPCResponse response, final ResultListener listener) {
        try {
            //String topic = String.format("v1/dev/%s/%s/up", serviceName, deviceName);
            String topic = "v1/dev/"+serviceName+"/"+deviceName+"/up";
            JSONObject jsonObject = new JSONObject();
            JSONObject rpcRspObject = new JSONObject();
            JSONObject resultObject;
            JSONObject errorObject;
            
            addElement(jsonObject, response.getCmd());
            addElement(jsonObject, response.getCmdId());
            
            addElement(rpcRspObject, response.getJsonrpc());
            addElement(rpcRspObject, response.getId());
            
            if(response.getErrorCode() != null) {
                errorObject = new JSONObject();
                addElement(errorObject, response.getErrorCode());
                addElement(errorObject, response.getErrorMessage());
                
                addElement(jsonObject, new StringElement(Define.RESULT, Define.FAIL));
                rpcRspObject.put(Define.ERROR, errorObject);
            } else {
                resultObject = new JSONObject();
                addElement(resultObject, response.getResult());
                
                addElement(jsonObject, new StringElement(Define.RESULT, Define.SUCCESS));
                rpcRspObject.put(Define.RESULT, resultObject);
            }
            jsonObject.put(Define.RPC_RSP, rpcRspObject);
            String payload = jsonObject.toString();
            Util.log("\n" + topic + "\n" + payload);
            mqttClient.publish(topic, payload, listener);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFailure(Define.INTERNAL_SDK_ERROR, e.getMessage());
        }
    }
    
    public void tpSimpleRawResult(String   response, final ResultListener listener) {
        try {
            //String topic = String.format("v1/dev/%s/%s/up", serviceName, deviceName);
            String topic = "v1/dev/"+serviceName+"/"+deviceName+"/up";
            Util.log("\n" + topic + "\n" + response);
            mqttClient.publish(topic, response, listener);
        } catch (MqttException e) {
            e.printStackTrace();
            listener.onFailure(Define.INTERNAL_SDK_ERROR, e.getMessage());
        }
    }
    
    /**
     *
     *
     * @param subscribe
     * @param listener
     */
    public void tpSimpleSubscribe(Subscribe subscribe, final ResultListener listener) {
        try {
            //String topic = String.format("v1/dev/%s/%s/up", serviceName, deviceName);
            String topic = "v1/dev/"+serviceName+"/"+deviceName+"/up";
            JSONObject jsonObject = new JSONObject();
            JSONArray attributeArrayObject = new JSONArray();
            JSONArray telemetryArrayObject = new JSONArray();
            
            int attrSize = subscribe.getAttribute().size();
            for(int index = 0; index < attrSize; index++){
                String attribute = (String) subscribe.getAttribute().elementAt(index);
                attributeArrayObject.put(attribute);
            }
            
            int telemetrySize = subscribe.getTelemetry().size();
            for(int index = 0; index < telemetrySize; index++){
                String telemetry = (String) subscribe.getTelemetry().elementAt(index);
                telemetryArrayObject.put(telemetry);
            }
            
            addElement(jsonObject, subscribe.getCmd());
            addElement(jsonObject, subscribe.getServiceName());
            addElement(jsonObject, subscribe.getDeviceName());
            addElement(jsonObject, subscribe.getSensorNodeId());
            addElement(jsonObject, subscribe.getIsTargetAll());
            jsonObject.put(Define.ATTRIBUTE, attributeArrayObject);
            jsonObject.put(Define.TELEMETRY, telemetryArrayObject);
            addElement(jsonObject, subscribe.getCmdId());
            String payload = jsonObject.toString();
            Util.log("\n" + topic + "\n" + payload);
            mqttClient.publish(topic, payload, listener);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFailure(Define.INTERNAL_SDK_ERROR, e.getMessage());
        }
    }
}
