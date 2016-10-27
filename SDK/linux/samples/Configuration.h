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

#define MQTT_HOST                           "218.234.164.84" // pusan smartcity
#define MQTT_PORT                           1883
#define MQTT_SECURE_PORT                    8883
#define MQTT_KEEP_ALIVE                     120
#define MQTT_ENABLE_SERVER_CERT_AUTH        0

#define ONEM2M_NODEID                       ""
#define ONEM2M_AE_RESOURCENAME              "ae-middleware"
#define ONEM2M_SERVICENAME                  "middleware"
#define ONEM2M_CB                           "CB00073"
#define ONEM2M_TO                           "/CB00073/middleware"
#define ONEM2M_RI                           "12345"

#define ACCOUNT_USER                        "sptektest"
#define ACCOUNT_PASSWORD                    "Baek3883!"

#define APP_AEID                            "S000000000020284232178"

#else // oneM2M V1

#define MQTT_HOST                           "thingplugtest.sktiot.com"
//efine MQTT_HOST                           "ssl://thingplugtest.sktiot.com" // TLS

#define MQTT_PORT                           1883
#define MQTT_SECURE_PORT                    8883
#define MQTT_KEEP_ALIVE                     300
#define MQTT_ENABLE_SERVER_CERT_AUTH        0

#define ACCOUNT_USER						""
#define ACCOUNT_PASSWORD					""

#define ONEM2M_NODEID                       "1.2.481.1.999.198.000101" //1.2.481.1.999.130.3000906" // Demoday
//#define ONEM2M_TO                           "/ThingPlug" // onem2m.sktiot.com
#define ONEM2M_TO                           "/ThingPlug/v1_0" // thingplugtest.sktiot.com
#define ONEM2M_PASSCODE                     "123456"
#define ONEM2M_RI                           "1234"

#endif // ONEM2M_V1_12

#endif // _CONFIGURATION_H_

