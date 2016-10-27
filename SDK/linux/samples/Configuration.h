/**
 * @file Configuration.h
 *
 * @brief Configuration header for The Samples
 *
 * Copyright (C) 2016. SK Telecom, All Rights Reserved.
 * Written 2016, by SK Telecom
 */

#ifndef _CONFIGURATION_H_
#define _CONFIGURATION_H_

//#define ONEM2M_V1_12

#ifdef ONEM2M_V1_12

#define MQTT_HOST                           "(TBD.)"
#define MQTT_PORT                           1883
#define MQTT_SECURE_PORT                    8883
#define MQTT_KEEP_ALIVE                     120
#define MQTT_ENABLE_SERVER_CERT_AUTH        0

#define ONEM2M_AE_RESOURCENAME              "(TBD.)"
#define ONEM2M_SERVICENAME                  "(TBD.)"
#define ONEM2M_CB                           "(TBD.)"
#define ONEM2M_TO                           "(TBD.)"
#define ONEM2M_RI                           "12345"

#define ACCOUNT_USER                        "(TBD.)"
#define ACCOUNT_PASSWORD                    "(TBD.)"

#define APP_AEID                            "(TBD.)"

#else // oneM2M V1

#define MQTT_HOST                           "thingplugtest.sktiot.com"
//efine MQTT_HOST                           "ssl://thingplugtest.sktiot.com" // TLS

#define MQTT_PORT                           1883
#define MQTT_SECURE_PORT                    8883
#define MQTT_KEEP_ALIVE                     300
#define MQTT_ENABLE_SERVER_CERT_AUTH        0

#define ACCOUNT_USER						"(TBD.)"
#define ACCOUNT_PASSWORD					"(TBD.)"

#define ONEM2M_NODEID                       "(TBD.)"
#define ONEM2M_TO                           "/ThingPlug/v1_0"
#define ONEM2M_PASSCODE                     "(TBD.)"
#define ONEM2M_RI                           "1234"

#endif // ONEM2M_V1_12

#endif // _CONFIGURATION_H_
