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
// #define SIZE_TOPIC                          128
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

static void attribute(void);
static int telemetry(void);

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
        int control, rc = -1;
        cJSON* cmdObject = cJSON_GetObjectItemCaseSensitive(root, "cmd");
        cJSON* rpcObject = cJSON_GetObjectItemCaseSensitive(rpcReqObject, "jsonrpc");
        cJSON* idObject = cJSON_GetObjectItemCaseSensitive(rpcReqObject, "id");
        cJSON* paramsObject = cJSON_GetObjectItemCaseSensitive(rpcReqObject, "params");
        cJSON* methodObject = cJSON_GetObjectItemCaseSensitive(rpcReqObject, "method");
        cJSON* controlObject;
        if(!cmdObject || !idObject || !methodObject) return;
        char* cmd = cmdObject->valuestring;
        char* rpc = rpcObject->valuestring;
        int id = idObject->valueint;
        char* method = methodObject->valuestring;
        if(!cmd || !method) return;

        // Reserved Procedure for ThingPlug
        if(strncmp(method, RPC_RESET, strlen(RPC_RESET)) == 0) {
            // TODO RESET
            SKTDebugPrint(LOG_LEVEL_INFO, "RPC_RESET");

        } else if(strncmp(method, RPC_REBOOT, strlen(RPC_REBOOT)) == 0) {
            // TODO REBOOT
            SKTDebugPrint(LOG_LEVEL_INFO, "RPC_REBOOT");
            
        } else if(strncmp(method, RPC_UPLOAD, strlen(RPC_UPLOAD)) == 0) {
            // TODO UPLOAD
            SKTDebugPrint(LOG_LEVEL_INFO, "RPC_UPLOAD");
            
        } else if(strncmp(method, RPC_DOWNLOAD, strlen(RPC_DOWNLOAD)) == 0) {
            // TODO DOWNLOAD
            SKTDebugPrint(LOG_LEVEL_INFO, "RPC_DOWNLOAD");
            
        } else if(strncmp(method, RPC_SOFTWARE_INSTALL, strlen(RPC_SOFTWARE_INSTALL)) == 0) {
            // TODO SOFTWARE INSTALL
            SKTDebugPrint(LOG_LEVEL_INFO, "RPC_SOFTWARE_INSTALL");
            
        } else if(strncmp(method, RPC_SOFTWARE_REINSTALL, strlen(RPC_SOFTWARE_REINSTALL)) == 0) {
            // TODO SOFTWARE REINSTALL
            SKTDebugPrint(LOG_LEVEL_INFO, "RPC_SOFTWARE_REINSTALL");
            
        } else if(strncmp(method, RPC_SOFTWARE_UNINSTALL, strlen(RPC_SOFTWARE_UNINSTALL)) == 0) {
            // TODO SOFTWARE UNINSTALL
            SKTDebugPrint(LOG_LEVEL_INFO, "RPC_SOFTWARE_UNINSTALL");
            
        } else if(strncmp(method, RPC_SOFTWARE_UPDATE, strlen(RPC_SOFTWARE_UPDATE)) == 0) {
            // TODO SOFTWARE UPDATE
            SKTDebugPrint(LOG_LEVEL_INFO, "RPC_SOFTWARE_UPDATE");
            
        } else if(strncmp(method, RPC_FIRMWARE_UPGRADE, strlen(RPC_FIRMWARE_UPGRADE)) == 0) {
            // TODO FIRMWARE UPGRADE
            SKTDebugPrint(LOG_LEVEL_INFO, "RPC_FIRMWARE_UPGRADE");
            
        } else if(strncmp(method, RPC_CLOCK_SYNC, strlen(RPC_CLOCK_SYNC)) == 0) {
            // TODO CLOCK SYNC
            SKTDebugPrint(LOG_LEVEL_INFO, "RPC_CLOCK_SYNC");

        } else if(strncmp(method, RPC_SIGNAL_STATUS_REPORT, strlen(RPC_SIGNAL_STATUS_REPORT)) == 0) {
            // TODO SIGNAL STASTUS REPORT
            SKTDebugPrint(LOG_LEVEL_INFO, "RPC_SIGNAL_STATUS_REPORT");

        } else if(strncmp(method, RPC_USER, strlen(RPC_USER)) == 0) {
            // USER
            SKTDebugPrint(LOG_LEVEL_INFO, "RPC_USER");
            if(!paramsObject) return;
            cJSON* paramObject = cJSON_GetArrayItem(paramsObject, 0);
            controlObject = cJSON_GetObjectItemCaseSensitive(paramObject, "act7colorLed");
            if(!controlObject) return;
            control = controlObject->valueint;
            {
                char str[128];
                snprintf(str,128,"\r\nrpc : %s,\r\nid : %d,\r\ncmd : %d", rpc, id, control);
                SKTDebugPrint(LOG_LEVEL_INFO, str);
            }
            rc = RGB_LEDControl(control);
        }
        RPCResponse rsp;
        memset(&rsp, 0, sizeof(RPCResponse));
        rsp.cmd = cmd;
        rsp.cmdId = 1;
        rsp.jsonrpc = rpc;
        rsp.id = id;
        rsp.method = method;
        rsp.fail = rc;
        // control success
        if(rc == 0) {
            char body[128] = "";
            snprintf(body, sizeof(body), "{\"%s\":%d}", controlObject->string, control);
            rsp.resultBody = body;
            rsp.result = "success";
            tpSimpleRawResult(&rsp);
        }
        // control fail
        else {
            ArrayElement* resultArray = calloc(1, sizeof(ArrayElement));
            resultArray->capacity = 2;
            resultArray->element = calloc(1, sizeof(Element) * resultArray->capacity);
            int errorCode = 106;

            Element* item = resultArray->element + resultArray->total;
            item->type = JSON_TYPE_LONG;
            item->name = "code";
            item->value = &errorCode;
            resultArray->total++;

            item = resultArray->element + resultArray->total;
            item->type = JSON_TYPE_STRING;
            item->name = "message";
            item->value = "FAIL";
            resultArray->total++;
            
            rsp.resultArray = resultArray;
            rsp.result = "fail";

            tpSimpleResult(&rsp);
            free(resultArray->element);
            free(resultArray);
        }
        
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
    timer = ntp_time() + get_npt_offset();
    return timer;
}

char *sensor_list[] = { "temp1", "humi1", "light1" };

static int telemetry(void) {

#ifdef JSON_TELEMETRY
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

    int rc = tpSimpleTelemetry(arrayElement, 0);
    free(arrayElement->element);
    free(arrayElement);
    free(temp);
    free(humi);
    free(light);

    return rc;
#endif
#ifdef CSV_TELEMETRY
    char *temp, *humi, *light;
    char time[16];
    int len,total_len,rc;

    unsigned long curr_time = current_timestamp();
    snprintf(time, 16, "%lu", curr_time);
    total_len = (strlen(time) + 1);
    SMAGetData(sensor_list[0], &temp, &len);
    total_len += (strlen(temp) + 1);
    SMAGetData(sensor_list[1], &humi, &len);
    total_len += (strlen(humi) + 1);
    SMAGetData(sensor_list[2], &light, &len);
    total_len += (strlen(light) + 1);

    char* csv_data = (char *) calloc(total_len + 1, sizeof(char));
    SRAConvertCSVData( csv_data, time);
    SRAConvertCSVData( csv_data, temp);
    SRAConvertCSVData( csv_data, humi);
    SRAConvertCSVData( csv_data, light);
    rc = tpSimpleRawTelemetry(csv_data, FORMAT_CSV);
    free(csv_data);
    free(temp);
    free(humi);
    free(light);

    return rc;
#endif
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

static void attribute(void) {

#ifdef JSON_TELEMETRY
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
#endif
#ifdef CSV_TELEMETRY
    char csv_attr[256] = "";
    unsigned long availableMemory = getAvailableMemory();
    char tmp[64];
    snprintf( tmp, 64, "%lu", availableMemory );
    // Memory 
    SRAConvertCSVData( csv_attr, tmp);
    // SW Version
    SRAConvertCSVData( csv_attr, "2.0.0");
    // HW Version
    SRAConvertCSVData( csv_attr, "1.0");
    //Serial
    SRAConvertCSVData( csv_attr, "710DJC5I10000290");
    int errorCode = 0;
    snprintf( tmp, 64, "%d", errorCode);
    //Error code
    SRAConvertCSVData( csv_attr, tmp);
    //NetworkType
    SRAConvertCSVData( csv_attr, "ethernet");
    NetworkInfo info;
    memset(&info, 0, sizeof(NetworkInfo));
    getNetworkInfo(&info, "eth0");
    //IPAddr
    SRAConvertCSVData( csv_attr, info.deviceIpAddress);
    //ServerIPAddr
    SRAConvertCSVData( csv_attr, MQTT_HOST); 
    //Latitude
    SRAConvertCSVData( csv_attr, "35.1689766"); 
    //Longitude
    SRAConvertCSVData( csv_attr, "129.1338524"); 
    //Led
    int act7colorLed = 0;
    snprintf( tmp, 64, "%d", act7colorLed );
    SRAConvertCSVData( csv_attr, tmp);
    tpSimpleRawAttribute(csv_attr, FORMAT_CSV);
    
    mStep = PROCESS_TELEMETRY;
#endif
}

/**
 * @brief get Device MAC Address without Colon.
 * @return mac address
 */
char* GetMacAddressWithoutColon() {
    return strdup("A1:B2:C3:D4:E5:F6");
}


int start() {
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
    return rc;
}

int MARun() {
    int rc;
    SKTDebugInit(1, LOG_LEVEL_INFO);
    SKTDebugPrint(LOG_LEVEL_VERBOSE, "ThingPlug_Simple_SDK");
    rc = start();

    while (rc == 0 && mStep < PROCESS_END) {
        if(tpMQTTIsConnected() && mStep == PROCESS_TELEMETRY) {
            int rc = telemetry();
	    if(rc != 0) {
		    char str[64];
		    snprintf(str,64,"send telemetry fail: %d", rc);
		    SKTDebugPrint(LOG_LEVEL_INFO, str);
		    break;
	    }      
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
