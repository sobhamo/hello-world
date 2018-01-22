package tp.skt.simple.api;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import tp.skt.simple.common.Define;
import tp.skt.simple.element.ArrayElement;
import tp.skt.simple.element.BooleanElement;
import tp.skt.simple.element.NumberElement;
import tp.skt.simple.element.RPCResponse;
import tp.skt.simple.element.StringElement;
import tp.skt.simple.element.Subscribe;
import tp.skt.simple.net.mqtt.MQTTClient;
import tp.skt.simple.net.mqtt.SimpleCallback;
import tp.skt.simple.net.mqtt.SimpleConfiguration;
import tp.skt.simple.net.mqtt.SimpleListener;

/**
 * Simple.java
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class Simple {

    /**
     * MQTT client
     **/
    private MQTTClient mqttClient;
    /**
     * MQTT listener
     **/
    private SimpleListener simpleListener;

    /**
     * Service name
     **/
    private String serviceName;
    /**
     * Device name
     **/
    private String deviceName;
    /**
     * User name
     **/
    private String userName;

    /**
     * added data
     **/
    private String addedData = "";

    /**
     * Simple constructor
     *
     * @param context
     * @param serviceName
     * @param deviceName
     * @param simpleConfiguration
     * @param simpleListener
     * @param logEnabled
     */
    public Simple(Context context, String serviceName, String deviceName, String userName, SimpleConfiguration simpleConfiguration, SimpleListener simpleListener, boolean logEnabled) {
        this.tpSimpleInitialize(serviceName, deviceName, userName);

        // make subscribeTopic
        List<String> subscribeTopicList = new ArrayList<String>();
        if (userName != null) {
            subscribeTopicList.add(String.format(Define.TOPIC_USER_DOWN, userName));
        } else {
            subscribeTopicList.add(String.format(Define.TOPIC_DOWN, serviceName, deviceName));
        }

        String[] subscribeTopics = subscribeTopicList.toArray(new String[subscribeTopicList.size()]);
        // create mqtt client
        this.mqttClient = new MQTTClient.Builder(context)
                .baseUrl(simpleConfiguration.getMqttServerAddress())
                .clientId(simpleConfiguration.getClientID())
                .userName(simpleConfiguration.getLoginName())
                .password(simpleConfiguration.getLoginPassword())
                .setSubscribeTopics(subscribeTopics)
                .setEnableSecure(simpleConfiguration.isEnableSecure())
                .setLog(logEnabled).build();
        this.simpleListener = simpleListener;
    }

    /**
     * Simple initialize
     *
     * @param serviceName
     * @param deviceName
     */
    public void tpSimpleInitialize(String serviceName, String deviceName, String userName) {
        this.serviceName = serviceName;
        this.deviceName = deviceName;
        this.userName = userName;
    }

    /**
     * connect server
     */
    public void tpSimpleConnect() {
        if (mqttClient == null || simpleListener == null) {
            return;
        }
        mqttClient.connect(simpleListener);
    }

    /**
     * disconnect server
     */
    public void tpSimpleDisconnect() {
        if (mqttClient != null) {
            mqttClient.disconnect();
        }
    }

    /**
     * destroy ThingPlug SDK
     */
    public void tpSimpleDestroy() {
        if (mqttClient != null) {
            mqttClient.destroy();
        }
    }

    /**
     * MQTT is connected
     *
     * @return true : connected <-> false
     */
    public boolean tpSimpleIsConnected() {
        if (mqttClient != null) {
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
    private boolean addElement(JsonObject jsonObject, Object element) {
        if (element == null) {
            return false;
        }
        if (element instanceof StringElement) {
            StringElement se = (StringElement) element;
            jsonObject.addProperty(se.name, se.value);
        } else if (element instanceof BooleanElement) {
            BooleanElement be = (BooleanElement) element;
            jsonObject.addProperty(be.name, be.value);
        } else if (element instanceof NumberElement) {
            NumberElement de = (NumberElement) element;
            jsonObject.addProperty(de.name, de.value);
        } else {
            return false;
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
     * @param callback
     */
    public void tpSimpleTelemetry(ArrayElement telemetry, final boolean useAddedData, final SimpleCallback callback) {
        try {
            String topic = String.format(Define.TOPIC_TELEMETRY, serviceName, deviceName);
            String payload = null;
            if (useAddedData) {
                payload = addedData;
                addedData = "";
            } else {
                JsonObject jsonObject = new JsonObject();
                for (Object element : telemetry.elements) {
                    boolean result = addElement(jsonObject, element);
                    if (result == false) {
                        throw new Exception("Bad element!");
                    }
                }
                payload = jsonObject.toString();
            }
            mqttClient.publish(topic, payload, callback);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(Define.INTERNAL_SDK_ERROR, e.getMessage());
        }
    }

    /**
     * device attribute
     *
     * @param attribute
     * @param callback
     */
    public void tpSimpleAttribute(ArrayElement attribute, final SimpleCallback callback) {
        try {
            String topic = String.format(Define.TOPIC_ATTRIBUTE, serviceName, deviceName);
            JsonObject jsonObject = new JsonObject();
            for (Object element : attribute.elements) {
                boolean result = addElement(jsonObject, element);
                if (result == false) {
                    throw new Exception("Bad element!");
                }
            }
            String payload = jsonObject.toString();
            mqttClient.publish(topic, payload, callback);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(Define.INTERNAL_SDK_ERROR, e.getMessage());
        }
    }

    /**
     * device RPC control result
     *
     * @param response
     * @param callback
     */
    public void tpSimpleResult(RPCResponse response, final SimpleCallback callback) {
        try {
            String topic = String.format(Define.TOPIC_UP, serviceName, deviceName);
            JsonObject jsonObject = new JsonObject();
            JsonObject rpcRspObject = new JsonObject();
            JsonObject resultObject = new JsonObject();

            addElement(jsonObject, response.getCmd());
            addElement(jsonObject, response.getCmdId());
            addElement(jsonObject, response.getResult());

            addElement(rpcRspObject, response.getJsonrpc());
            addElement(rpcRspObject, response.getId());

            for (Object element : response.getResultArray().elements) {
                boolean result = addElement(resultObject, element);
                if (result == false) {
                    throw new Exception("Bad element!");
                }
            }
            if (response.isSuccess() == true) {
                rpcRspObject.add(Define.RESULT, resultObject);
            } else {
                rpcRspObject.add(Define.ERROR, resultObject);
            }
            jsonObject.add(Define.RPC_RSP, rpcRspObject);
            String payload = jsonObject.toString();
            mqttClient.publish(topic, payload, callback);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(Define.INTERNAL_SDK_ERROR, e.getMessage());
        }
    }

    /**
     * @param subscribe
     * @param callback
     */
    public void tpSimpleSubscribe(Subscribe subscribe, final SimpleCallback callback) {
        try {
            String topic = String.format(Define.TOPIC_USER_UP, userName);
            JsonObject jsonObject = new JsonObject();
            JsonArray attributeArrayObject = new JsonArray();
            JsonArray telemetryArrayObject = new JsonArray();

            for (String attribute : subscribe.getAttribute()) {
                attributeArrayObject.add(attribute);
            }
            for (String telemetry : subscribe.getTelemetry()) {
                telemetryArrayObject.add(telemetry);
            }
            addElement(jsonObject, subscribe.getCmd());
            addElement(jsonObject, subscribe.getServiceName());
            addElement(jsonObject, subscribe.getDeviceName());
            addElement(jsonObject, subscribe.getSensorNodeId());
            addElement(jsonObject, subscribe.getIsTargetAll());
            jsonObject.add(Define.ATTRIBUTE, attributeArrayObject);
            jsonObject.add(Define.TELEMETRY, telemetryArrayObject);
            addElement(jsonObject, subscribe.getCmdId());
            String payload = jsonObject.toString();
            mqttClient.publish(topic, payload, callback);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(Define.INTERNAL_SDK_ERROR, e.getMessage());
        }
    }

    /**
     * @param telemetry
     * @param callback
     */
    public void tpSimpleRawTelemetry(String telemetry, Define.DATA_FORMAT format, final SimpleCallback callback) {
        try {
            String topic = "";
            switch (format) {
                case FORMAT_JSON:
                    topic = String.format(Define.TOPIC_TELEMETRY, serviceName, deviceName);
                    break;
                case FORMAT_CSV:
                    topic = String.format(Define.TOPIC_TELEMETRY_CSV, serviceName, deviceName);
                    break;
                case FORMAT_OFFSET:
                    topic = String.format(Define.TOPIC_TELEMETRY_OFFSET, serviceName, deviceName);
                    break;
                default:
                    callback.onFailure(Define.INTERNAL_SDK_ERROR, "Invalid Parameter about format.");
                    return;
            }
            mqttClient.publish(topic, telemetry, callback);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(Define.INTERNAL_SDK_ERROR, e.getMessage());
        }
    }

    /**
     * @param attribute
     * @param callback
     */
    public void tpSimpleRawAttribute(String attribute, Define.DATA_FORMAT format, final SimpleCallback callback) {
        try {
            String topic = "";
            switch (format) {
                case FORMAT_JSON:
                    topic = String.format(Define.TOPIC_ATTRIBUTE, serviceName, deviceName);
                    break;
                case FORMAT_CSV:
                    topic = String.format(Define.TOPIC_ATTRIBUTE_CSV, serviceName, deviceName);
                    break;
                case FORMAT_OFFSET:
                    topic = String.format(Define.TOPIC_ATTRIBUTE_OFFSET, serviceName, deviceName);
                    break;
                default:
                    callback.onFailure(Define.INTERNAL_SDK_ERROR, "Invalid Parameter about format.");
                    return;
            }
            mqttClient.publish(topic, attribute, callback);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(Define.INTERNAL_SDK_ERROR, e.getMessage());
        }
    }

    /**
     * send RPC control result using all raw data
     *
     * @param result
     * @param callback
     */
    public void tpSimpleRawResult(String result, final SimpleCallback callback) {
        try {
            String topic = String.format(Define.TOPIC_UP, serviceName, deviceName);
            mqttClient.publish(topic, result, callback);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(Define.INTERNAL_SDK_ERROR, e.getMessage());
        }
    }

    /**
     * set attribute
     *
     * @param cmdId     command id
     * @param attribute set attribute
     * @param callback  result callback
     */
    public void tpSimpleSetAttribute(int cmdId, ArrayElement attribute, final SimpleCallback callback) {
        try {
            String topic = String.format(Define.TOPIC_USER_UP, userName);
            JsonObject jsonObject = new JsonObject();
            JsonObject attributeObject = new JsonObject();

            addElement(jsonObject, new StringElement(Define.SERVICE_NAME, serviceName));
            addElement(jsonObject, new StringElement(Define.DEVICE_NAME, deviceName));
            addElement(jsonObject, new NumberElement(Define.CMD_ID, cmdId));
            addElement(jsonObject, new StringElement(Define.CMD, Define.SET_ATTRIBUTE));

            for (Object element : attribute.elements) {
                boolean result = addElement(attributeObject, element);
                if (result == false) {
                    throw new Exception("Bad element!");
                }
            }
            jsonObject.add(Define.ATTRIBUTE, attributeObject);
            String payload = jsonObject.toString();
            mqttClient.publish(topic, payload, callback);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(Define.INTERNAL_SDK_ERROR, e.getMessage());
        }
    }

    /**
     * req jsonRpc
     *
     * @param params
     * @param method
     * @param id
     * @param isTwoWay
     * @param callback
     */
    public void tpSimpleJsonRpcReq(ArrayElement params, String method, int id, boolean isTwoWay, final SimpleCallback callback) {
        try {
            String topic = String.format(Define.TOPIC_USER_UP, userName);
            JsonObject jsonObject = new JsonObject();
            JsonObject rpcReqObject = new JsonObject();
            JsonArray paramsObject = new JsonArray();
            JsonObject paramObject;

            addElement(jsonObject, new NumberElement(Define.CMD_ID, id));
            addElement(jsonObject, new StringElement(Define.CMD, Define.JSON_RPC));
            addElement(jsonObject, new StringElement(Define.SERVICE_NAME, serviceName));
            addElement(jsonObject, new StringElement(Define.DEVICE_NAME, deviceName));

            addElement(rpcReqObject, new StringElement(Define.JSONRPC, "2.0"));
            addElement(rpcReqObject, new StringElement(Define.METHOD, "tp_user"));

            for (Object element : params.elements) {
                paramObject = new JsonObject();
                boolean result = addElement(paramObject, element);
                paramsObject.add(paramObject);
                if (result == false) {
                    throw new Exception("Bad element!");
                }
            }
            rpcReqObject.add(Define.PARAMS, paramsObject);
            addElement(rpcReqObject, new NumberElement(Define.ID, id));
            jsonObject.add(Define.RPC_REQ, rpcReqObject);

            if (isTwoWay == true) {
                addElement(jsonObject, new StringElement(Define.RPC_MODE, Define.TWOWAY));
            } else {
                addElement(jsonObject, new StringElement(Define.RPC_MODE, Define.ONEWAY));
            }
            String payload = jsonObject.toString();
            mqttClient.publish(topic, payload, callback);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(Define.INTERNAL_SDK_ERROR, e.getMessage());
        }
    }
}
