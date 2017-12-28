package tp.skt.simple.common;

/**
 * constant values
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class Define {

    /**
     * subscribe
     **/
    public static final String ENLIST = "enlist";
    /**
     * unsubscribe
     **/
    public static final String DELIST = "declist";

    /**
     * command(string)
     **/
    public static final String CMD = "cmd";
    /**
     * command ID(int)
     **/
    public static final String CMD_ID = "cmdId";
    /**
     * device ID(string)
     **/
    public static final String DEVICE_ID = "deviceId";
    /**
     * telemetry(string array)
     **/
    public static final String TELEMETRY = "telemetry";
    /**
     * attribute(string array)
     **/
    public static final String ATTRIBUTE = "attribute";
    /**
     * RPC response(JSON)
     **/
    public static final String RPC_RSP = "rpcRsp";
    /**
     * RPC request(JSON)
     **/
    public static final String RPC_REQ = "rpcReq";
    /**
     * rpcMode(String)
     **/
    public static final String RPC_MODE = "rpcMode";
    /**
     * rpc_json(String)
     **/
    public static final String JSON_RPC = "jsonRpc";
    /**
     * set_attr(String)
     **/
    public static final String SET_ATTRIBUTE = "setAttribute";
    /**
     * oneway
     **/
    public static final String ONEWAY = "oneway";
    /**
     * twoway
     **/
    public static final String TWOWAY = "twoway";
    /**
     * params(JSONArray)
     **/
    public static final String PARAMS = "params";
    /**
     * JSON RPC version(string)
     **/
    public static final String JSONRPC = "jsonrpc";
    /**
     * Identifier(int)
     **/
    public static final String ID = "id";
    /**
     * method(string)
     **/
    public static final String METHOD = "method";
    /**
     * control result
     **/
    public static final String RESULT = "result";
    /**
     * error(JSON)
     **/
    public static final String ERROR = "error";
    /**
     * error code(int)
     **/
    public static final String CODE = "code";
    /**
     * error message(string)
     **/
    public static final String MESSAGE = "message";
    /**
     * result status(string)
     **/
    public static final String STATUS = "status";
    /**
     * result success(string)
     **/
    public static final String SUCCESS = "success";
    /**
     * result fail(string)
     **/
    public static final String FAIL = "fail";
    /**
     * SDK version
     **/
    public static final String VERSION = "1.0";
    /**
     * device name(string)
     **/
    public static final String DEVICE_NAME = "deviceName";
    /**
     * service name(string)
     **/
    public static final String SERVICE_NAME = "serviceName";
    /**
     * sensor node ID(string)
     **/
    public static final String SENSOR_NODE_ID = "sensorNodeId";
    /**
     * is target all(boolean)
     **/
    public static final String IS_TARGET_ALL = "isTargetAll";

    /**
     * Error code
     **/
    public static final int INTERNAL_SDK_ERROR = 9999;

    /**
     * telemetry topic for JSON format
     **/
    public static final String TOPIC_TELEMETRY = "v1/dev/%s/%s/telemetry";
    /**
     * telemetry topic for CSV format
     **/
    public static final String TOPIC_TELEMETRY_CSV = "v1/dev/%s/%s/telemetry/csv";
    /**
     * telemetry topic for OFFSET format
     **/
    public static final String TOPIC_TELEMETRY_OFFSET = "v1/dev/%s/%s/telemetry/offset";
    /**
     * attribute topic for JSON format
     **/
    public static final String TOPIC_ATTRIBUTE = "v1/dev/%s/%s/attribute";
    /**
     * attribute topic for CSV format
     **/
    public static final String TOPIC_ATTRIBUTE_CSV = "v1/dev/%s/%s/attribute/csv";
    /**
     * attribute topic for OFFSET format
     **/
    public static final String TOPIC_ATTRIBUTE_OFFSET = "v1/dev/%s/%s/attribute/offset";
    /**
     * up topic
     **/
    public static final String TOPIC_UP = "v1/dev/%s/%s/up";
    /**
     * down topic
     **/
    public static final String TOPIC_DOWN = "v1/dev/%s/%s/down";
    /**
     * up user topic
     **/
    public static final String TOPIC_USER_UP = "v1/usr/%s/up";
    /**
     * down user topic
     **/
    public static final String TOPIC_USER_DOWN = "v1/usr/%s/down";

    public static enum DATA_FORMAT {
        FORMAT_JSON,
        FORMAT_CSV,
        FORMAT_OFFSET
    };
}
