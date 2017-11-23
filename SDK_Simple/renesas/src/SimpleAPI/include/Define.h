/**
 * @file Simple.h
 *
 * @brief The header file for Defines
 *
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
#ifndef _DEFINE_H_
#define _DEFINE_H_

/* CONNACK : 0 Connection Accepted */
#define CONNECTION_ACCEPTED 0
/* CONNACK : 1 Connection Refused, unacceptable protocol version */
#define CONNECTION_REFUSED_UNACCEPTABLE_PROTOCOL_VERSION 1
/* CONNACK : 2 Connection Refused, identifier rejected */
#define CONNECTION_REFUSED_IDENTIFIER_REJECTED 2
/* CONNACK : 3 Connection Refused, Server unavailable */
#define CONNECTION_REFUSED_SERVER_UNAVAILABLE 3
/* CONNACK : 4 Connection Refused, bad user name or password */
#define CONNECTION_REFUSED_BAD_USERNAME_OR_PASSWORD 4
/* CONNACK : 5 Connection Refused, not authorized */
#define CONNECTION_REFUSED_NOT_AUTHORIZED 5

/* Return code: No error. Indicates successful completion */
#define TP_SDK_SUCCESS 0
/* Return code: A generic error code indicating the failure */
#define TP_SDK_FAILURE -1
/* error code -2 is MQTTAsync_PERSISTENCE_ERROR */
#define TP_SDK_MQTT_PERSISTENCE_ERROR -2
/* Return code: The client is disconnected. */
#define TP_SDK_MQTT_DISCONNECTED -3
/* Return code: The maximum number of messages allowed to be simultaneously in-flight has been reached. */
#define TP_SDK_MQTT_MAX_MESSAGES_INFLIGHT -4
/* Return code: An invalid UTF-8 string has been detected. */
#define TP_SDK_MQTT_BAD_UTF8_STRING -5
/* Return code: A NULL parameter has been supplied when this is invalid. */
#define TP_SDK_MQTT_NULL_PARAMETER -6
/**
 * Return code: The topic has been truncated (the topic string includes
 * embedded NULL characters). String functions will not access the full topic.
 * Use the topic length value to access the full topic.
 */
#define TP_SDK_MQTT_TOPICNAME_TRUNCATED -7
/* Return code: A structure parameter does not have the correct eyecatcher and version number. */
#define TP_SDK_MQTT_BAD_STRUCTURE -8
/* Return code: A qos parameter is not 0, 1 or 2 */
#define TP_SDK_MQTT_BAD_QOS -9
/* Return code: All 65535 MQTT msgids are being used */
#define TP_SDK_MQTT_NO_MORE_MSGIDS -10
/* Return code: the request is being discarded when not complete */
#define TP_SDK_MQTT_OPERATION_INCOMPLETE -11
/* Return code: no more messages can be buffered */
#define TP_SDK_MQTT_MAX_BUFFERED_MESSAGES -12
/* Return code: Resource type or Operation is not supported */
#define TP_SDK_NOT_SUPPORTED -13
/* Return code: Parameter is invalid */
#define TP_SDK_INVALID_PARAMETER -14
 
/* service id & device id size */
#define SIZE_TOPIC              128

#endif
