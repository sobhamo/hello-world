/**
 * @file MA.c
 *
 * @brief MangementAgent
 *
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include "tx_api.h"

#include <SimpleAPI/include/Simple.h>
#include <SimpleAPI/include/SKTtpDebug.h>
#include <SimpleAPI/include/ThingPlug.h>
#include <SimpleAPI/middleware/Configuration.h>
#include <SimpleAPI/middleware/MA/MA.h>
#include <SimpleAPI/middleware/SMA/SMA.h>
#include <SimpleAPI/middleware/SRA/SRA.h>

#define MQTT_TOPIC_CONTROL_DOWN             "v1/dev/%s/%s/down"
#define TOPIC_SUBSCRIBE_SIZE                1
#define SIZE_RESPONSE_CODE                  10
#define SIZE_RESPONSE_MESSAGE               128
#define SIZE_TOPIC                          128
#define SIZE_PAYLOAD                        2048
#define SIZE_CLIENT_ID                      24

static enum PROCESS_STEP
{
    PROCESS_START = 0,
    PROCESS_ATTRIBUTE,
    PROCESS_TELEMETRY,
    PROCESS_END
} mStep;

static enum CONNECTION_STATUS
{
    DISCONNECTED,
    CONNECTING,
    CONNECTED
} mConnectionStatus;

typedef struct
{
    /** device IP address **/
    char deviceIpAddress[30];
    /** gateway IP address **/
    char gatewayIpAddress[30];
} NetworkInfo;

static char mTopicControlDown[SIZE_TOPIC] = "";
static char mClientID[SIZE_CLIENT_ID] = "";

static void attribute();
static void telemetry();

void MQTTConnected(int result) {
    {
        char str[64];
        snprintf(str,64,"MQTTConnected result : %d", result);
        SKTDebugPrint(LOG_LEVEL_INFO, str);
    }

    // if connection failed
    if(result) {
        mConnectionStatus = DISCONNECTED;
    } else {
        mConnectionStatus = CONNECTED;
    }
    {
        char str[64];
        snprintf(str,64,"CONNECTION_STATUS : %d", mConnectionStatus);
        SKTDebugPrint(LOG_LEVEL_INFO, str);
    }
}

void MQTTSubscribed(int result) {
    {
        char str[64];
        snprintf(str,64,"MQTTSubscribed result : %d", result);
        SKTDebugPrint(LOG_LEVEL_INFO, str);
    }
    attribute();
}

void MQTTDisconnected(int result) {
    {
        char str[64];
        snprintf(str,64,"MQTTDisconnected result : %d", result);
        SKTDebugPrint(LOG_LEVEL_INFO, str);
    }

}

void MQTTConnectionLost(char* cause) {
    {
        char str[64];
        snprintf(str,64,"MQTTConnectionLost result : %s", cause);
        SKTDebugPrint(LOG_LEVEL_INFO, str);
    }
    mConnectionStatus = DISCONNECTED;
}

void MQTTMessageDelivered(int token) {
    {
        char str[128];
        snprintf(str,64,"MQTTMessageDelivered token : %d, step : %d", token, mStep);
        SKTDebugPrint(LOG_LEVEL_INFO, str);
    }
}

void MQTTMessageArrived(char* topic, char* msg, int msgLen) {
    {
        char str[64];
        snprintf(str,64,"MQTTMessageArrived topic : %s, step : %d", topic, mStep);
        SKTDebugPrint(LOG_LEVEL_INFO, str);
    }

    if(msg == NULL || msgLen < 1) {
        return;
    }
    char payload[SIZE_PAYLOAD] = "";
    memcpy(payload, msg, msgLen);
    {
        SKTDebugPrint(LOG_LEVEL_INFO, "paylaod->");
        SKTDebugPrint(LOG_LEVEL_INFO, payload);
    }

    cJSON* root = cJSON_Parse(payload);
    if(!root) return;

    cJSON* rpcReqObject = cJSON_GetObjectItemCaseSensitive(root, "rpcReq");
    // if RPC control
    if(rpcReqObject) {
        cJSON* rpcObject = cJSON_GetObjectItemCaseSensitive(rpcReqObject, "jsonrpc");
        cJSON* idObject = cJSON_GetObjectItemCaseSensitive(rpcReqObject, "id");
        cJSON* paramsObject = cJSON_GetObjectItemCaseSensitive(rpcReqObject, "params");
        cJSON* methodObject = cJSON_GetObjectItemCaseSensitive(rpcReqObject, "method");
        if(!idObject || !paramsObject || !methodObject) return;
        char* rpc = rpcObject->valuestring;        
        int id = idObject->valueint;
        char* method = methodObject->valuestring;
        cJSON* paramObject = cJSON_GetArrayItem(paramsObject, 0);
        cJSON* cmdObject = cJSON_GetObjectItemCaseSensitive(paramObject, "act7colorLed");
        if(!cmdObject) return;
        int cmd = cmdObject->valueint;
        {
            char str[64];
            snprintf(str,64,"\nrpc : %s,\nid : %d,\ncmd : %d", rpc, id, cmd);
            SKTDebugPrint(LOG_LEVEL_INFO, str);
        }
        int rc = RGB_LEDControl(cmd);
        RPCResponse rsp;
        memset(&rsp, 0, sizeof(RPCResponse));
        rsp.cmd = "rpc_json";
        rsp.cmdId = 1;
        rsp.jsonrpc = rpc;
        rsp.id = id;
        rsp.method = method;
        if(rc == 0) {
            rsp.result = "SUCCESS";
        } else {
            rsp.errorCode = 106;
            rsp.errorMessage = "FAIL";
        }
        tpSimpleResult(&rsp);
    } else {
        cJSON* cmdObject = cJSON_GetObjectItemCaseSensitive(root, "cmd");
        cJSON* cmdIdObject = cJSON_GetObjectItemCaseSensitive(root, "cmdId");
        if(!cmdObject || !cmdIdObject) return;
        char* cmd = cmdObject->valuestring;
        int cmdId = cmdIdObject->valueint;
        if(!cmd) return;
        // if attribute control
        if(strncmp(cmd, "set_attr", strlen("set_attr")) == 0) {
            cJSON* attribute = cJSON_GetObjectItemCaseSensitive(root, "attribute");
            if(!attribute) return;
            cJSON* act7colorLedObject = cJSON_GetObjectItemCaseSensitive(attribute, "act7colorLed");
            if(!act7colorLedObject) return;
            int act7colorLed = act7colorLedObject->valueint;
            {
                char str[64];
                snprintf(str,64,"act7colorLed : %d, %d", act7colorLed, cmdId);
                SKTDebugPrint(LOG_LEVEL_INFO, str);
            }
            int rc = RGB_LEDControl(act7colorLed);

            ArrayElement* arrayElement = calloc(1, sizeof(ArrayElement));            
            arrayElement->capacity = 1;
            arrayElement->element = calloc(1, sizeof(Element) * arrayElement->capacity);            
            Element* item = arrayElement->element + arrayElement->total;
            item->type = JSON_TYPE_LONG;
            item->name = "act7colorLed";
            if(rc != 0) {
                act7colorLed = RGB_LEDStatus();
            }
            item->value = &act7colorLed;
            arrayElement->total++;
            tpSimpleAttribute(arrayElement);
            free(arrayElement->element);
            free(arrayElement);
        }
    }
    cJSON_Delete(root);
}

long long current_timestamp() {
    time_t timer;
    timer = ntp_time();
    timer = timer + get_npt_offset();
    return timer;
}

char *sensor_list[] = { "temp1", "humi1", "light1" };

static void telemetry() {
    mStep = PROCESS_TELEMETRY;
    // TODO make data
    // int i;
    char *temp, *humi, *light;
    int len;
    ArrayElement* arrayElement = calloc(1, sizeof(ArrayElement));

    arrayElement->capacity = 4;
    arrayElement->element = calloc(1, sizeof(Element) * arrayElement->capacity);

    SMAGetData(sensor_list[arrayElement->total], &temp, &len);
    temp = SRAConvertRawData(temp);
    Element* item = arrayElement->element + arrayElement->total;
    item->type = JSON_TYPE_RAW;
    item->name = sensor_list[arrayElement->total];
    item->value = temp;
    arrayElement->total++;

    SMAGetData(sensor_list[arrayElement->total], &humi, &len);
    humi = SRAConvertRawData(humi);
    item = arrayElement->element + arrayElement->total;
    item->type = JSON_TYPE_RAW;
    item->name = sensor_list[arrayElement->total];
    item->value = humi;
    arrayElement->total++;

    SMAGetData(sensor_list[arrayElement->total], &light, &len);
    light = SRAConvertRawData(light);
    item = arrayElement->element + arrayElement->total;
    item->type = JSON_TYPE_RAW;
    item->name = sensor_list[arrayElement->total];
    item->value = light;
    arrayElement->total++;

    item = arrayElement->element + arrayElement->total;
    item->type = JSON_TYPE_LONGLONG;
    item->name = TIMESTAMP;
    long long time = current_timestamp();
    item->value = (void *)&time;
    arrayElement->total++;

    tpSimpleTelemetry(arrayElement, 0);
    free(arrayElement->element);
    free(arrayElement);
    free(temp);
    free(humi);
    free(light);
}

static unsigned long getAvailableMemory() {
    //https://synergygallery.renesas.com/media/products/1/189/en-US/r01ds0272eu0141-synergy-ssp-120-datasheet.pdf
    //10.2.3 Estimated Memory Requirements (table)
    return 3263;

}

static char deviceIpAddress[30];
static char gatewayIpAddress[30];

void setIpAddress(char *ip, char *gw)
{
    memcpy(deviceIpAddress, ip, strlen(ip));
    memcpy(gatewayIpAddress, gw, strlen(gw));
}

static int getNetworkInfo(NetworkInfo* info, char* interface) {
    memcpy(info->deviceIpAddress, deviceIpAddress, strlen(deviceIpAddress));
    memcpy(info->gatewayIpAddress, gatewayIpAddress, strlen(gatewayIpAddress));

    return 0;
}

static void attribute() {

    ArrayElement* arrayElement = calloc(1, sizeof(ArrayElement));

    arrayElement->capacity = 13;
    arrayElement->element = calloc(1, sizeof(Element) * arrayElement->capacity);

    Element* item;
    item = &arrayElement->element[arrayElement->total];
    item->type = JSON_TYPE_LONG;
    item->name = "sysAvailableMemory";
    unsigned long availableMemory = getAvailableMemory();
    item->value = &availableMemory;
    arrayElement->total++;

    item = &arrayElement->element[arrayElement->total];
    item->type = JSON_TYPE_LONG;
    item->name = "sysBatteryLevel";
    int batteryLevel = 100;
    item->value = &batteryLevel;
    arrayElement->total++;

    // item = arrayElement->element + arrayElement->total;
    // item->type = JSON_TYPE_STRING;
    // item->name = "sysBatteryStatus";
    // item->value = "charging";
    // arrayElement->total++;

    item = &arrayElement->element[arrayElement->total];
    item->type = JSON_TYPE_STRING;
    item->name = "sysFirmwareVersion";
    item->value = "2.0.0";
    arrayElement->total++;

    item = &arrayElement->element[arrayElement->total];
    item->type = JSON_TYPE_STRING;
    item->name = "sysHardwareVersion";
    item->value = "1.0";
    arrayElement->total++;

    item = &arrayElement->element[arrayElement->total];
    item->type = JSON_TYPE_STRING;
    item->name = "sysSerialNumber";
    item->value = "400715-1703-003321-1641";
    arrayElement->total++;

    item = &arrayElement->element[arrayElement->total];
    item->type = JSON_TYPE_LONG;
    item->name = "sysErrorCode";
    int errorCode = 0;
    item->value = &errorCode;
    arrayElement->total++;

    item = &arrayElement->element[arrayElement->total];
    item->type = JSON_TYPE_STRING;
    item->name = "sysNetworkType";
    item->value = "ethernet";
    arrayElement->total++;

    NetworkInfo info;
    memset(&info, 0, sizeof(NetworkInfo));
    getNetworkInfo(&info, "eth0");
    item = &arrayElement->element[arrayElement->total];
    item->type = JSON_TYPE_STRING;
    item->name = "sysDeviceIpAddress";
    item->value = info.deviceIpAddress;
    arrayElement->total++;

    item = arrayElement->element + arrayElement->total;
    item->type = JSON_TYPE_STRING;
    item->name = "sysGatewayIpAddress";
    item->value = info.gatewayIpAddress;
    arrayElement->total++;

    // item = arrayElement->element + arrayElement->total;
    // item->type = JSON_TYPE_STRING;
    // item->name = "sysNtpSvrIpAddress";
    // item->value = "time.bora.net";
    // arrayElement->total++;

    item = &arrayElement->element[arrayElement->total];
    item->type = JSON_TYPE_STRING;
    item->name = "sysThingPlugIpAddress";
    item->value = MQTT_HOST;
    arrayElement->total++;

    item = &arrayElement->element[arrayElement->total];
    item->type = JSON_TYPE_RAW;
    item->name = "sysLocationLatitude";
    item->value = "35.1689766";
    arrayElement->total++;

    item = &arrayElement->element[arrayElement->total];
    item->type = JSON_TYPE_RAW;
    item->name = "sysLocationLongitude";
    item->value = "129.1338524";
    arrayElement->total++;

    item = &arrayElement->element[arrayElement->total];
    item->type = JSON_TYPE_LONG;
    item->name = "act7colorLed";
    int act7colorLed = 0;
    item->value = &act7colorLed;
    arrayElement->total++;

    tpSimpleAttribute(arrayElement);
    free(arrayElement->element);
    free(arrayElement);

    mStep = PROCESS_TELEMETRY;
}

/**
 * @brief get Device MAC Address without Colon.
 * @return mac address
 */
char* GetMacAddressWithoutColon() {
    return strdup("A1:B2:C3:D4:E5:F6");
}


void start() {
    int rc;

    mConnectionStatus = CONNECTING;

    RGB_LEDControl(0);

    // set callbacks
    rc = tpMQTTSetCallbacks(MQTTConnected, MQTTSubscribed, MQTTDisconnected, MQTTConnectionLost, MQTTMessageDelivered, MQTTMessageArrived);
    {
        char str[64];
        snprintf(str,64,"tpMQTTSetCallbacks result : %d", rc);
        SKTDebugPrint(LOG_LEVEL_INFO, str);
    }
    // Simple SDK initialize
    rc = tpSimpleInitialize(SIMPLE_SERVICE_NAME, SIMPLE_DEVICE_NAME);
    {
        char str[64];
        snprintf(str,64,"tpSimpleInitialize : %d", rc);
        SKTDebugPrint(LOG_LEVEL_INFO, str);
    }
    // create clientID - MAC address
    char* clientID = GetMacAddressWithoutColon();
    memcpy(mClientID, clientID, strlen(clientID));
    free(clientID);
    {
        char str[64];
        snprintf(str,64,"client id : %s", mClientID);
        SKTDebugPrint(LOG_LEVEL_INFO, str);
    }
    // create Topics
    snprintf(mTopicControlDown, SIZE_TOPIC, MQTT_TOPIC_CONTROL_DOWN, SIMPLE_SERVICE_NAME, SIMPLE_DEVICE_NAME);    
    char* subscribeTopics[] = { mTopicControlDown };

#if(MQTT_ENABLE_SERVER_CERT_AUTH)
    char host[] = MQTT_SECURE_HOST;
    int port = MQTT_SECURE_PORT;
#else
    char host[] = MQTT_HOST;
    int port = MQTT_PORT;
#endif
    rc = tpSDKCreate(host, port, MQTT_KEEP_ALIVE, LOGIN_NAME, NULL,
                     MQTT_ENABLE_SERVER_CERT_AUTH, subscribeTopics, TOPIC_SUBSCRIBE_SIZE, NULL, mClientID);
    {
        char str[64];
        snprintf(str,64,"tpSDKCreate result : %d", rc);
        SKTDebugPrint(LOG_LEVEL_INFO, str);
    }
}

int MARun() {
    SKTDebugInit(1, LOG_LEVEL_INFO);
    SKTDebugPrint(LOG_LEVEL_VERBOSE, "ThingPlug_Simple_SDK");
    start();

    while (mStep < PROCESS_END) {
        if(tpMQTTIsConnected() && mStep == PROCESS_TELEMETRY) {
            telemetry();
        } 
        // reconnect when disconnected
        else if(mConnectionStatus == DISCONNECTED) {
            tpSDKDestroy();
            start();
        }
        tpMQTTYield(1000);
    }
    tpSDKDestroy();
    return 0;
}
