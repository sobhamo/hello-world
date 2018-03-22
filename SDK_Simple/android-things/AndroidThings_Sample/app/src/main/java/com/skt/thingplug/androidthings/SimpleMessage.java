package com.skt.thingplug.androidthings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SimpleMessage {
    String cmd;
    int cmdId;
    RPCRequest rpcReq;
    JSONObject attribute;
    
    String result;
    
    static public class RPCRequest{
        String jsonrpc;
        int id;
        String method;
        JSONArray params;
    }
    
    public static SimpleMessage parsing(String jsonMessage) throws JSONException {
        SimpleMessage simpleMessage = new SimpleMessage();
        
        JSONObject rootObject = new JSONObject(jsonMessage);
        if(rootObject.has("result")){
            simpleMessage.result = rootObject.getString("result");
            return simpleMessage;
        }
        simpleMessage.cmd = rootObject.getString("cmd");
        simpleMessage.cmdId = rootObject.getInt("cmdId");
        
        if(rootObject.has("rpcReq")){
            
            JSONObject rpcReq =  rootObject.getJSONObject("rpcReq");
            simpleMessage.rpcReq = new RPCRequest();
            simpleMessage.rpcReq.jsonrpc = rpcReq.getString("jsonrpc");
            simpleMessage.rpcReq.method =  rpcReq.getString("method");
            simpleMessage.rpcReq.id = rpcReq.getInt("id");
            simpleMessage.rpcReq.params = rpcReq.getJSONArray("params");
        }else{
            if("setAttribute".equals(simpleMessage.cmd)){
                simpleMessage.attribute = rootObject.getJSONObject("attribute");
            }
        }
        return simpleMessage;
    }
}