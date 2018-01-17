/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package sample;

import javax.microedition.midlet.*;
import org.json.me.JSONException;
import org.json.me.JSONObject;
import sample.SimpleMessage.RPCRequest;
import tp.skt.simple.api.DataFormat;
import tp.java.util.Log;
import tp.skt.simple.api.Simple;
import tp.skt.simple.element.ArrayElement;
import tp.skt.simple.api.ResultListener;
import tp.skt.simple.api.Configuration;
import tp.skt.simple.api.ConnectionListener;
import tp.skt.simple.element.RPCResponse;

/**
 * SampleApp.java
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class SampleApp extends MIDlet {
    private final static String TAG_INFO = "[INFO] :";
    private Simple simple;
    
    private final DataFormat format = DataFormat.FORMAT_CSV;
    
    public void startApp() {
        logInfo("ThingPlug_Simple_SDK");
        
        String hostAddress = Config.SIMPLE_SECURE_HOST;
        int hostPort = Config.SIMPLE_SECURE_PORT;
        
        simple = new Simple( Config.SIMPLE_SERVICE_NAME, Config.SIMPLE_DEVICE_NAME,
                new Configuration(hostAddress, hostPort, Config.SIMPLE_KEEP_ALIVE, Config.DEVICE_TOKEN, Config.DEVICE_TOKEN, null),
                connectionListener, true);
        
        simple.tpSimpleConnect();
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
        if(simple != null) {
            simple.tpSimpleDestroy();
        }
    }
    
    
    private void attributeRaw(){
        String raw = "{\"rawData\":\"true\",\"sysLocationLatitude\":37.380257,\"sysHardwareVersion\":\"1.0\",\"sysLocationLongitude\":127.115479,\"sysErrorCode\":0,\"sysSerialNumber\":\"710DJC5I10000290\",\"led\":0}";
        simple.tpSimpleRawAttribute(raw, DataFormat.FORMAT_JSON, callback);
    }
    
    
    private void attribute() {
        
        if(format == DataFormat.FORMAT_CSV){
            String data = "1.0,710DJC5I10000290,0,127.115479,37.380257,7";
            
            simple.tpSimpleRawAttribute(data, format, callback);
        } else { // if(format == DataFormat.FORMAT_JSON)
            // {"sysHardwareVersion":"1.0", "sysSerialNumber":"710DJC5I10000290", "sysErrorCode":0, "sysLocationLongitude":127.115479, "sysLocationLatitude": 37.380257, "led":7}
            String data = "{\"sysHardwareVersion\":\"1.0\", \"sysSerialNumber\":\"710DJC5I10000290\", \"sysErrorCode\":0, \"sysLocationLongitude\":127.115479, \"sysLocationLatitude\": 37.380257, \"led\":7}";
            
            simple.tpSimpleRawAttribute(data, format, callback);
        }
        
//        ArrayElement element = new ArrayElement();
//        element.addStringElement("sysHardwareVersion", "1.0");
//        element.addStringElement("sysSerialNumber", "710DJC5I10000290");
//        element.addNumberElement("sysErrorCode", 0);
//        element.addNumberElement("sysLocationLongitude", 127.115479);
//        element.addNumberElement("sysLocationLatitude", 37.380257);
//        simple.tpSimpleAttribute(element, callback);
    }
    
    private void telemetry() {
        if(format == DataFormat.FORMAT_CSV){
            String data = "true,267,48,26.26";
            
            simple.tpSimpleRawTelemetry(data, format, callback);
        } else { // if(format == DataFormat.FORMAT_JSON)
            String data = "{\"rawData\":\"true\",\"light1\":267,\"humi1\":48,\"temp1\":26.26}";
            
            simple.tpSimpleRawTelemetry(data, format, callback);
        }       
        
//        ArrayElement element = new ArrayElement();
//        element.addNumberElement("temp1", 26.26);
//        element.addNumberElement("humi1", 48);
//        element.addNumberElement("light1", 267);
//        simple.tpSimpleTelemetry(element, false, callback);
    }
    
    private int errorCode = 0;
    ResultListener callback = new ResultListener() {
        
        public void onResponse(int response) {
            errorCode = response;
        }
        
        
        public void onFailure(int errorCode, String message) {
            errorCode = errorCode;
        }
    };
    
    
    ConnectionListener connectionListener = new ConnectionListener() {
        
        public void onConnected() {
            logInfo("onConnected");
        }
        
        
        public void onDisconnected() {
            logInfo("onDisconnected");
        }
        
        
        public void onSubscribed() {
            logInfo("onSubscribed");
            
            logInfo("send Attribute");
            attribute();
            logInfo("send Telemetry");
            telemetry();
//            logInfo("send Attribute Raw");
//            attributeRaw();
        }
        
        
        public void onSubscribeFailure() {
            logInfo("onSubscribFailure");
        }
        
        public void onDisconnectFailure() {
            logInfo("onDisconnectFailure");
        }
        
        public void onConnectFailure() {
            logInfo("onConnectFailure");
        }
        
        public void onConnectionLost() {
            logInfo("onConnectionLost");
        }
        
        
        public void onDelivered() {
            logInfo("onDelivered");
        }
        
        public void onMessageReceived(String topic, String payload) {
            logInfo("onMessageReceived topic : " + topic);
            logInfo("onMessageReceived payload : " + payload);
            
            try {
                SimpleMessage simpleMessage = SimpleMessage.parsing(payload);
                if(null != simpleMessage){
                    if(null != simpleMessage.result){
                        logInfo("onMessageReceived result : " + simpleMessage.result);
                        return;
                    }
                    
                    boolean isTwoWay = "twoway".equals(simpleMessage.mode)?true:false;
                    
                    if(null != simpleMessage.rpcReq){
                        RPCRequest rpcReq = simpleMessage.rpcReq;
                        if(Define.RPC_RESET.equals(rpcReq.method)){
                            logInfo("RPC_RESET");
                        }else if(Define.RPC_REBOOT.equals(rpcReq.method)){
                            logInfo("RPC_REBOOT");
                        }else if(Define.RPC_UPLOAD.equals(rpcReq.method)){
                            logInfo("RPC_UPLOAD");
                        }else if(Define.RPC_DOWNLOAD.equals(rpcReq.method)){
                            logInfo("RPC_DOWNLOAD");
                        }else if(Define.RPC_SOFTWARE_INSTALL.equals(rpcReq.method)){
                            logInfo("RPC_SOFTWARE_INSTALL");
                        }else if(Define.RPC_SOFTWARE_REINSTALL.equals(rpcReq.method)){
                            logInfo("RPC_SOFTWARE_REINSTALL");
                        }else if(Define.RPC_SOFTWARE_REUNINSTALL.equals(rpcReq.method)){
                            logInfo("RPC_SOFTWARE_REUNINSTALL");
                        }else if(Define.RPC_SOFTWARE_UPDATE.equals(rpcReq.method)){
                            logInfo("RPC_SOFTWARE_UPDATE");
                        }else if(Define.RPC_FIRMWARE_UPGRADE.equals(rpcReq.method)){
                            logInfo("RPC_FIRMWARE_UPGRADE");
                            // DO FIRMWARE UPGRADE here...
                            
                        }else if(Define.RPC_CLOCK_SYNC.equals(rpcReq.method)){
                            logInfo("RPC_CLOCK_SYNC");
                        }else if(Define.RPC_SIGNAL_STATUS_REPORT.equals(rpcReq.method)){
                            logInfo("RPC_SIGNAL_STATUS_REPORT");
                        }else if(Define.RPC_REMOTE.equals(rpcReq.method)){
                            logInfo("RPC_REMOTE");
                            
                            // DO REMOTE here...

                        }else if(Define.RPC_USER.equals(rpcReq.method)){
                            logInfo("RPC_USER");
                            
                            if(rpcReq.params.getJSONObject(0).has("led")){
                                int control = rpcReq.params.getJSONObject(0).getInt("led");
                                logInfo("rpc : " + rpcReq.jsonrpc+", id : "+rpcReq.id+"cmd : "+  control);
                                
                                // DO CONTROL here...
                                
                                if(isTwoWay){
                                    boolean result = true;
                                    if(result){
                                        // control success
                                        String successBody = "{\"led\":7}";
                                    }else{
                                        // control fail
                                        String errorBody = "{\"message\":\"wrong parameters\"}";
                                    }
                                }
                                
                                
                                RPCResponse rsp = new RPCResponse();
                                rsp.setCmd(simpleMessage.cmd);
                                rsp.setCmdId(1);
                                rsp.setJsonrpc(rpcReq.jsonrpc);
                                rsp.setCmdId(1);
                                rsp.setId(rpcReq.id);
                                rsp.setResult("success");
                                simple.tpSimpleResult(rsp, callback);
                            }
                        }
                    }else{
                        if("setAttribute".equals(simpleMessage.cmd) && null != simpleMessage.attribute){
                            
                            if(format == DataFormat.FORMAT_JSON){
                                int led = simpleMessage.attribute.getInt("led");
                                logInfo("led :" +  led + ", " + simpleMessage.cmdId);
                                
                                ArrayElement arrayElement = new ArrayElement();
                                arrayElement.addNumberElement("led", led);
                                simple.tpSimpleAttribute(arrayElement, callback);
                                
                            }else{ //  if(format == DataFormat.FORMAT_CSV)
                                int led = simpleMessage.attribute.getInt("led");
                                String data = ",,,,," + led;
                                simple.tpSimpleRawAttribute(data, format, callback);
                            }
                        }
                    }
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    };
    
    
    private void logInfo(String message){
        Log.print(TAG_INFO, message);
    }
}
