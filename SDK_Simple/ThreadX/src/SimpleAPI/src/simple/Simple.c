/**
 * @file Simple.c
 *
 * @brief Simple API file
 *
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <SimpleAPI/include/Simple.h>
#include <SimpleAPI/include/MQTTHandler.h>
#include <SimpleAPI/include/SKTtpDebug.h>


/** service ID **/
static char* mServiceID;
/** device ID **/
static char* mDeviceID;

/**
 * @brief add Element to cJSON object
 * @param[in] jsonObject : cJSON object
 * @param[in] element : adding Element
 * @return int : result code
 */
static int addElement(cJSON* jsonObject, Element* element) {
    if(!jsonObject || !element)  return TP_SDK_INVALID_PARAMETER;
    if(element->type == JSON_TYPE_BOOLEAN) {
        if(*(int *)element->value) {
            cJSON_AddTrueToObject(jsonObject, element->name);
        } else {
            cJSON_AddFalseToObject(jsonObject, element->name);
        }
    } else if(element->type == JSON_TYPE_LONGLONG) {
        cJSON_AddNumberToObject(jsonObject, element->name, *(long long *)element->value);
    } else if(element->type == JSON_TYPE_LONG) {
        cJSON_AddNumberToObject(jsonObject, element->name, *(long *)element->value);
    } else if(element->type == JSON_TYPE_DOUBLE) {
        cJSON_AddNumberToObject(jsonObject, element->name, *(double *)element->value);
    } else if(element->type == JSON_TYPE_RAW) {
        cJSON_AddRawToObject(jsonObject, element->name, (char *)element->value);
    } else {
        cJSON_AddStringToObject(jsonObject, element->name, (char *)element->value);
    }
    return TP_SDK_SUCCESS;
}

/**
 * @brief add content data of contentInstance
 * @param[in] data : data
 * @param[in] length : data length
 * @return int : result code
 */
int tpSimpleAddData(char* data, unsigned char length) {
    if(!data || length < 1) return TP_SDK_FAILURE;
    if(!gContent) {
        gContent = (Content *)calloc(1, sizeof(Content));
        gContent->data = (char *)calloc(1, length + 1);
        memcpy(gContent->data, data, length);
        gContent->len = length;
    } else {
        gContent->len = gContent->len + length;
        gContent->data = (char *)realloc(gContent->data, gContent->len + 1);
        gContent->data[gContent->len] = '\0';
        strncat(gContent->data, data, length);
    }

    {
        char str[64];
        snprintf(str,64,"Content data : %s, length : %d", gContent->data, gContent->len);
        SKTDebugPrint(LOG_LEVEL_INFO, str);
    }

    return TP_SDK_SUCCESS;
}

/**
 * @brief device telemetry
 * @param[in] telemetry : content
 * @param[in] useAddedData : use added data flag
 * @return int : result code
 */
int tpSimpleTelemetry(ArrayElement* telemetry, unsigned char useAddedData) {
    int rc = TP_SDK_FAILURE;
    char topic[SIZE_TOPIC] = "";
    snprintf(topic, SIZE_TOPIC, "v1/dev/%s/%s/telemetry", mServiceID, mDeviceID);

    if(useAddedData) {
        if(!gContent || !gContent->data) return TP_SDK_INVALID_PARAMETER;

        rc = handleMQTTPublishMessageWithTopic(topic, gContent->data);
        {
            char str[64];
            snprintf(str,64,"tpSimpleTelemetry\r\ntopic : %s", topic);
            SKTDebugPrint(LOG_LEVEL_INFO, str);
            SKTDebugPrint(LOG_LEVEL_INFO, gContent->data);
        }
        if(gContent) {
            if(gContent->data) {
                free(gContent->data);
                gContent->data = NULL;
            }
            free(gContent);
            gContent = NULL;
        }
    } else {
        if(!telemetry) return TP_SDK_INVALID_PARAMETER;
        int i, size;
        Element* element;

        char* jsonData;
        cJSON* jsonObject = cJSON_CreateObject();

        size = telemetry->total;
        for(i = 0; i < size; i++) {
            element = (telemetry->element + i);
            addElement(jsonObject, element);
        }
        jsonData = cJSON_Print(jsonObject);
        cJSON_Delete(jsonObject);

        {
            char str[64];
            snprintf(str,64,"tpSimpleTelemetry\r\ntopic : %s", topic);
            SKTDebugPrint(LOG_LEVEL_INFO, str);
            SKTDebugPrint(LOG_LEVEL_INFO, jsonData);
        }
        rc = handleMQTTPublishMessageWithTopic(topic, jsonData);
        if(jsonData) free(jsonData);
    }
    return rc;
}

/**
 * @brief device attribute
 * @param[in] attribute : attributes
 * @return int : result code
 */
int tpSimpleAttribute(ArrayElement* attribute) {
    if(!attribute) return TP_SDK_INVALID_PARAMETER;
    int rc = TP_SDK_FAILURE;
    int i, size;
    char topic[SIZE_TOPIC] = "";
    Element* element;
    snprintf(topic, SIZE_TOPIC, "v1/dev/%s/%s/attribute", mServiceID, mDeviceID);

    char* jsonData;
    cJSON* jsonObject = cJSON_CreateObject();

    size = attribute->total;
    for(i = 0; i < size; i++) {
        element = (attribute->element + i);
        addElement(jsonObject, element);
    }
    jsonData = cJSON_Print(jsonObject);
    cJSON_Delete(jsonObject);

    {
        char str[64];
        snprintf(str, 64,"tpSimpleAttribute\r\ntopic : %s", topic);
        SKTDebugPrint(LOG_LEVEL_INFO, str);
        SKTDebugPrint(LOG_LEVEL_INFO, jsonData);
    }
    rc = handleMQTTPublishMessageWithTopic(topic, jsonData);
    if(jsonData) free(jsonData);
    return rc;
}

/**
 * @brief device control result
 * @param[in] response : control result response
 * @return int : result code
 */
int tpSimpleResult(RPCResponse* response) {
    if(!response) return TP_SDK_INVALID_PARAMETER;
    int i, size, rc = TP_SDK_FAILURE;
    char topic[SIZE_TOPIC] = "";
    snprintf(topic, SIZE_TOPIC, "v1/dev/%s/%s/up", mServiceID, mDeviceID);

    char* jsonData;
    cJSON* jsonObject = cJSON_CreateObject();
    cJSON* rpcRspObject = cJSON_CreateObject();
    cJSON* resultObject = cJSON_CreateObject();
    Element* element;

    cJSON_AddStringToObject(jsonObject, CMD, response->cmd);
    cJSON_AddNumberToObject(jsonObject, CMD_ID, response->cmdId);
    cJSON_AddStringToObject(jsonObject, RESULT, response->result);

    cJSON_AddStringToObject(rpcRspObject, JSONRPC, response->jsonrpc);
    cJSON_AddNumberToObject(rpcRspObject, ID, response->id);
    cJSON_AddStringToObject(rpcRspObject, METHOD, response->method);
    size = response->resultArray->total;
    for(i = 0; i < size; i++) {
        element = (response->resultArray->element + i);
        addElement(resultObject, element);
    }
    if(response->fail) {
        cJSON_AddItemToObject(rpcRspObject, ERROR, resultObject);        
    } else {
        cJSON_AddItemToObject(rpcRspObject, RESULT, resultObject);
    }
    cJSON_AddItemToObject(jsonObject, RPC_RSP, rpcRspObject);
    jsonData = cJSON_Print(jsonObject);
    cJSON_Delete(jsonObject);

    {
        char str[64];
        snprintf(str,64,"tpSimpleResult\ntopic : %s", topic);
        SKTDebugPrint(LOG_LEVEL_INFO, str);
        SKTDebugPrint(LOG_LEVEL_INFO, jsonData);
    }
    rc = handleMQTTPublishMessageWithTopic(topic, jsonData);
    if(jsonData) free(jsonData);
    return rc;
}

/**
 * @brief subscribe/unsubscribe request
 * @param[in] subscribe : information
 * @return int : result code
 */
int tpSimpleSubscribe(DeviceSubscribe* subscribe) {
    if(!subscribe) return TP_SDK_INVALID_PARAMETER;
    int rc = TP_SDK_FAILURE;
    char topic[SIZE_TOPIC] = "";
    snprintf(topic, SIZE_TOPIC, "v1/dev/%s/%s/up", mServiceID, mDeviceID);

    char* jsonData;
    cJSON* jsonObject = cJSON_CreateObject();
    cJSON* attributeArray = cJSON_CreateStringArray(subscribe->attribute, subscribe->attribute_size);
    cJSON* telemetryArray = cJSON_CreateStringArray(subscribe->telemetry, subscribe->telemetry_size);

    cJSON_AddStringToObject(jsonObject, CMD, subscribe->cmd);
    cJSON_AddNumberToObject(jsonObject, CMD_ID, subscribe->cmdId);
    cJSON_AddStringToObject(jsonObject, SERVICE_NAME, mServiceID);
    cJSON_AddStringToObject(jsonObject, DEVICE_NAME, mDeviceID);
    if(subscribe->sensorNodeId) {
        cJSON_AddStringToObject(jsonObject, SENSOR_NODE_ID, subscribe->sensorNodeId);
    }
    if(subscribe->isTargetAll) {
        cJSON_AddTrueToObject(jsonObject, IS_TARGET_ALL);
    } else {
        cJSON_AddFalseToObject(jsonObject, IS_TARGET_ALL);
    }
    cJSON_AddItemToObject(jsonObject, ATTRIBUTE, attributeArray);
    cJSON_AddItemToObject(jsonObject, TELEMETRY, telemetryArray);

    jsonData = cJSON_Print(jsonObject);
    cJSON_Delete(jsonObject);

    {
        char str[64];
        snprintf(str,64,"tpSimpleSubscribe\r\ntopic : %s", topic );
        SKTDebugPrint(LOG_LEVEL_INFO, str);
        SKTDebugPrint(LOG_LEVEL_INFO, jsonData);
    }
    rc = handleMQTTPublishMessageWithTopic(topic, jsonData);
    if(jsonData) free(jsonData);
    return rc;
}

/**
 * @brief initialize Simple API
 * @param[in] serviceID : service id
 * @param[in] deviceID : device id
 * @param[in] deviceID : gateway id
 * @return int : result code
 */
int tpSimpleInitialize(char* serviceID, char* deviceID) {
    if(!serviceID) return TP_SDK_FAILURE;
    mServiceID = serviceID;
    mDeviceID = deviceID;
    {
        char str[128];
        snprintf(str,128,"tpSimpleInitialize\r\nserviceID : %s, deviceID: %s", serviceID,  deviceID);
        SKTDebugPrint(LOG_LEVEL_INFO, str);
    }
    return TP_SDK_SUCCESS;
}

/**
 * @brief send raw data to telemetry
 * @param[in] telemetry : content
 * @param[in] format : data format
 * @return int : result code
 */
int tpSimpleRawTelemetry(char* telemetry, DATA_FORMAT format) {
    if(!telemetry) return TP_SDK_INVALID_PARAMETER;
    int rc = TP_SDK_FAILURE;
    char topic[SIZE_TOPIC] = "";
    char* topicBase;

    switch(format) {
        case FORMAT_JSON:
            topicBase = TOPIC_TELEMETRY;
            break;
        case FORMAT_CSV:
            topicBase = TOPIC_TELEMETRY_CSV;
            break;
        case FORMAT_OFFSET:
            topicBase = TOPIC_TELEMETRY_OFFSET;
            break;
        default:
            return TP_SDK_INVALID_PARAMETER;
    }
    snprintf(topic, SIZE_TOPIC, topicBase, mServiceID, mDeviceID);
    {
        char str[128];
        snprintf(str,128,"tpSimpleRawTelemetry\r\ntopic : %s", topic);
        SKTDebugPrint(LOG_LEVEL_INFO, str);
        SKTDebugPrint(LOG_LEVEL_INFO, telemetry);
    }
    rc = handleMQTTPublishMessageWithTopic(topic, telemetry);
    return rc;
}

/**
 * @brief send raw data to attribute
 * @param[in] attribute : attributes
 * @param[in] format : data format
 * @return int : result code
 */
 int tpSimpleRawAttribute(char* attribute, DATA_FORMAT format) {
    if(!attribute) return TP_SDK_INVALID_PARAMETER;
    int rc = TP_SDK_FAILURE;
    char topic[SIZE_TOPIC] = "";
    char* topicBase;
    
    switch(format) {
        case FORMAT_JSON:
            topicBase = TOPIC_ATTRIBUTE;
            break;
        case FORMAT_CSV:
            topicBase = TOPIC_ATTRIBUTE_CSV;
            break;
        case FORMAT_OFFSET:
            topicBase = TOPIC_ATTRIBUTE_OFFSET;
            break;
        default:
            return TP_SDK_INVALID_PARAMETER;
    }    
    snprintf(topic, SIZE_TOPIC, topicBase, mServiceID, mDeviceID);    
    {
        char str[128];
        snprintf(str,128,"tpSimpleRawAttribute\r\ntopic : %s", topic);
        SKTDebugPrint(LOG_LEVEL_INFO, str);
        SKTDebugPrint(LOG_LEVEL_INFO, attribute);
    }
    rc = handleMQTTPublishMessageWithTopic(topic, attribute);
    return rc;
}

/**
 * @brief send raw data to result
 * @param[in] response : control result response
 * @return int : result code
 */
 int tpSimpleRawResult(RPCResponse* response) {
    if(!response) return TP_SDK_INVALID_PARAMETER;
    int rc = TP_SDK_FAILURE;
    char topic[SIZE_TOPIC] = "";
    snprintf(topic, SIZE_TOPIC, "v1/dev/%s/%s/up", mServiceID, mDeviceID);

    char* jsonData;
    cJSON* jsonObject = cJSON_CreateObject();
    cJSON* rpcRspObject = cJSON_CreateObject();
    cJSON* resultObject;

    cJSON_AddStringToObject(jsonObject, CMD, response->cmd);
    cJSON_AddNumberToObject(jsonObject, CMD_ID, response->cmdId);
    cJSON_AddStringToObject(jsonObject, RESULT, response->result);

    cJSON_AddStringToObject(rpcRspObject, JSONRPC, response->jsonrpc);
    cJSON_AddNumberToObject(rpcRspObject, ID, response->id);
    cJSON_AddStringToObject(rpcRspObject, METHOD, response->method);
    resultObject = cJSON_CreateRaw(response->resultBody);
    if(response->fail) {
        cJSON_AddItemToObject(rpcRspObject, ERROR, resultObject);        
    } else {
        cJSON_AddItemToObject(rpcRspObject, RESULT, resultObject);
    }
    cJSON_AddItemToObject(jsonObject, RPC_RSP, rpcRspObject);
    jsonData = cJSON_Print(jsonObject);
    cJSON_Delete(jsonObject);
    {
        char str[128];
        snprintf(str,128,"tpSimpleRawResult\r\ntopic : %s", topic);
        SKTDebugPrint(LOG_LEVEL_INFO, str);
        SKTDebugPrint(LOG_LEVEL_INFO, jsonData);
    }
    rc = handleMQTTPublishMessageWithTopic(topic, jsonData);
    if(jsonData) free(jsonData);
    return rc;
}
