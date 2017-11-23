/**
 * @file Simple.h
 *
 * @brief The header file for Simple API
 *
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
#ifndef _SIMPLE_H_
#define _SIMPLE_H_

#include <SimpleAPI/include/cJSON.h>
#include <SimpleAPI/include/Define.h>

/*
 ****************************************
 * Enumerations
 ****************************************
 */
typedef enum data_type {
    JSON_TYPE_STRING = 0,     // char pointer
    JSON_TYPE_RAW,            // deal with words numerically
    JSON_TYPE_LONG,           // int, long type
    JSON_TYPE_LONGLONG,       // long long type
    JSON_TYPE_DOUBLE,         // double type
    JSON_TYPE_BOOLEAN         // int 0(false) or other(true)
} DATA_TYPE;

/*
 ****************************************
 * Definition
 ****************************************
 */
/** subscribe **/
#define SUBSCRIBE                   "subscribe"
/** unsubscribe **/
#define UNSUBSCRIBE                 "unsubscribe"
/** device **/
#define DEVICE                      "device"

/** devicesOnline(unsigned char) **/
#define DEVICES_ONLINE              "devicesOnline"
/** attributesUploaded(unsigned char) **/
#define ATTRIBUTES_UPLOADED         "attributesUploaded"
/** telemetryUploaded(unsigned char) **/
#define TELEMETRY_UPLOADED          "telemetryUploaded"
/** timestamp(unsigned long) **/
#define TIMESTAMP                   "ts"

/** device descriptor(string) **/
#define DEVICE_DESCRIPTOR           "deviceDescriptor"

/** command(string) **/
#define CMD                         "cmd"
/** command ID(int) **/
#define CMD_ID                      "cmdId"
/** device ID(string) **/
#define DEVICE_ID                   "deviceId"
/** telemetry(string array) **/
#define TELEMETRY                   "telemetry"
/** attribute(string array) **/
#define ATTRIBUTE                   "attribute"

/** sensor node ID(string) **/
#define SENSOR_NODE_ID              "sensorNodeId"
/** is target all(boolean) **/
#define IS_TARGET_ALL               "isTargetAll"

/** RPC response(JSON) **/
#define RPC_RSP                     "rpcRsp"
/** JSON RPC version(string) **/
#define JSONRPC                     "jsonrpc"
/** Identifier(int) **/
#define ID                          "id"
/** method(string) **/
#define METHOD                      "method"
/** control result **/
#define RESULT                      "result"
/** error(JSON) **/
#define ERROR                       "error"
/** error code(int) **/
#define CODE                        "code"
/** error message(string) **/
#define MESSAGE                     "message"
/** result status(string) **/
#define STATUS                      "status"
/** result success(string) **/
#define SUCCESS                     "success"
/** result fail(string) **/
#define FAIL                        "fail"
/*
 ****************************************
 * Structure Definition
 ****************************************
 */
typedef struct
{
    /** data type **/
    DATA_TYPE type;
    /** JSon name **/
    char* name;
    /** JSon value **/
    void* value;
} Element;

typedef struct
{
    /** Element real count **/
    int total;
    /** Element capacity **/
    int capacity;
    /** Element list **/
    Element* element;
} ArrayElement;

typedef struct
{
    /** command(string) **/
    char* cmd;
    /** command ID(int) **/
    int cmdId;
    /** JSON RPC version(string) **/
    char* jsonrpc;
    /** request ID from server(int) **/
    int id;
    /** method(string) **/
    char* method;
    /** control result(string) **/
    char* result;
    /** error code(int) **/
    int errorCode;
    /** error message(string) **/
    char* errorMessage;
} RPCResponse;

typedef struct
{
    /** command **/
    char* cmd;
    /** device ID(UUID) **/
    char* deviceId;
    /** sensor node ID(optional) **/
    char* sensorNodeId;
    /** all sensor node flag **/
    int isTargetAll;
    /** attribute array **/
    const char **attribute;
    int attribute_size;
    /** telemetry array **/
    const char **telemetry;
    int telemetry_size;
    /** command ID **/
    unsigned int cmdId;
} DeviceSubscribe;

/*
 ****************************************
 * Major Function
 ****************************************
 */
int tpSimpleAddData(char* data, unsigned char length);
 
int tpSimpleInitialize(char* serviceID, char* deviceID);

int tpSimpleTelemetry(ArrayElement* telemetry, unsigned char useAddedData);

int tpSimpleAttribute(ArrayElement* attribute);

int tpSimpleResult(RPCResponse* response);

int tpSimpleSubscribe(DeviceSubscribe* subscribe);
#endif
