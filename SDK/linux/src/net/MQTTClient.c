/**
 * @file MQTTClient.c
 *
 * @brief MQTT Client for Linux
 *
 * Copyright (C) 2016. SK Telecom, All Rights Reserved.
 * Written 2016, by SK Telecom
 */

#include "stdio.h"
#include "stdlib.h"
#include "string.h"
#include "MQTTAsync.h"

#include "MQTT.h"
#ifdef SPT_DEBUG_ENABLE
#include "SPTekDebug.h"
#else
#include "SKTtpDebug.h"
#endif

static MQTTAsync mClient;

static char mUserName[32];
static char mUserPass[90];

static char mPublishTopic[128];
static char mSubscribeTopic[5][128];
static int mSubscribeTopicsize;

static tpMQTTConnectedCallback* mConnectedCallback;
static tpMQTTSubscribedCallback* mSubscribedCallback;
static tpMQTTDisconnectedCallback* mDisconnectedCallback;
static tpMQTTConnectionLostCallback* mConnectionLostCallback;
static tpMQTTMessageDeliveredCallback* mMessageDeliveredCallback;
static tpMQTTMessageArrivedCallback* mMessageArrivedCallback;

Content* gContent = NULL;


volatile MQTTAsync_token deliveredtoken;

void OnConnect(void* context, MQTTAsync_successData* response){
    if(mConnectedCallback) mConnectedCallback(MQTTASYNC_SUCCESS);
    int i, rc;
    for(i = 0; i < mSubscribeTopicsize; i++) {
        rc = MQTTAsyncSubscribe(mSubscribeTopic[i], 2);
#ifdef SPT_DEBUG_ENABLE
	   	SPTekDebugLog(LOG_LEVEL_INFO, "subscribed topic : %s, result : %d", mSubscribeTopic[i], rc);
#else
        SKTDebugPrint(LOG_LEVEL_INFO, "subscribed topic : %s, result : %d", mSubscribeTopic[i], rc);
#endif

        if(rc != MQTTASYNC_SUCCESS) {
            MQTTAsyncDestroy();
        }
    }
}

void OnConnectFailure(void* context, MQTTAsync_failureData* response){
    if(mConnectedCallback) mConnectedCallback((response ? response->code : MQTTASYNC_FAILURE));
}

void OnSubscribe(void* context, MQTTAsync_successData* response) {
    mSubscribeTopicsize--;    
    if(mSubscribedCallback && mSubscribeTopicsize == 0) mSubscribedCallback(MQTTASYNC_SUCCESS);
}

void OnSubscribeFailure(void* context, MQTTAsync_failureData* response) {
    if(mSubscribedCallback) mSubscribedCallback((response ? response->code : MQTTASYNC_FAILURE));
}

void OnDisconnect(void* context, MQTTAsync_successData* response){
    if(mDisconnectedCallback) mDisconnectedCallback(MQTTASYNC_SUCCESS);
}

void ConnectionLostCallback(void *context, char *cause) {
    if(mConnectionLostCallback) mConnectionLostCallback(cause);    
}

int MessageArrivedCallback(void *context, char *topicName, int topicLen, MQTTAsync_message *message) {
    if(mMessageArrivedCallback) {
        mMessageArrivedCallback(topicName, message->payload, message->payloadlen);
    }

    MQTTAsync_freeMessage(&message);
    MQTTAsync_free(topicName);
    
    return 1;
}

void MessageDeliveredCallback(void *context, MQTTAsync_token dt) {
    if(mMessageDeliveredCallback) mMessageDeliveredCallback((int)dt);
    deliveredtoken = dt;
}

/**
 * @brief set callback function
 * @param[in] cc connect callback
 * @param[in] sc subscribe callback
 * @param[in] dc disconnect callback
 * @param[in] clc connection lost callback
 * @param[in] mdc message delivered callback
 * @param[in] mac message arrived callback
 * @return the return code of the set callbacks result
 */
int MQTTSetCallbacks(tpMQTTConnectedCallback* cc, tpMQTTSubscribedCallback* sc, tpMQTTDisconnectedCallback* dc,
        tpMQTTConnectionLostCallback* clc, tpMQTTMessageDeliveredCallback* mdc, tpMQTTMessageArrivedCallback* mac) {

    if(mac == NULL) {
        return -1;
    }
    
    mConnectedCallback = cc;
    mSubscribedCallback = sc;
    mDisconnectedCallback = dc;
    mConnectionLostCallback = clc;
    mMessageDeliveredCallback = mdc;
    mMessageArrivedCallback = mac;

    return 0;
}

/**
 * @brief create mqtt
 * @param[in] host the hostname or ip address of the broker to connect to.
 * @param[in] port the network port to connect to.  Usually 1883.
 * @param[in] keepalive the number of seconds after which the broker should send a PING message to the client if no other messages have been exchanged in that time. 
 * @param[in] userName authentication user name
 * @param[in] password authentication password
 * @param[in] enableServerCertAuth True/False option to enable verification of the server certificate
 * @param[in] subscribeTopic mqtt topic
 * @param[in] subscribeTopicSize mqtt topic size
 * @param[in] publishTopic publish topic
 * @param[in] enabledCipherSuites cipher format. If this setting is ommitted, its default value will be "ALL".
 * @param[in] cleanSession if cleanSession=true, then the previous session information is cleared.
 * @param[in] clientID The client identifier(no longer 23 characters).
 * @return the return code of the connection response
 */
int MQTTAsyncCreate(char* host, int port, int keepalive, char* userName, char* password, int enableServerCertAuth, 
         char* subscribeTopic[], int subscribeTopicSize, char* publishTopic, char* enabledCipherSuites, int cleanSession, char* clientID) {
#ifdef SPT_DEBUG_ENABLE
	SPTekDebugLog(LOG_LEVEL_INFO, "MQTTAsyncCreate()");
#else
	SKTDebugPrint(LOG_LEVEL_INFO, "MQTTAsyncCreate()");
#endif
    MQTTAsync_connectOptions conn_opts = MQTTAsync_connectOptions_initializer;
    MQTTAsync_SSLOptions ssl_opts = MQTTAsync_SSLOptions_initializer;

    int rc;
    int i = 0;
    int hostLength = strlen(host);
    int serverLength = hostLength + 10;
    int portLength = 5;
    char server[serverLength];
    char pt[portLength];

    memset(server, 0, serverLength);
    memset(pt, 0, portLength);
    snprintf(pt, portLength, "%d", port);
    memcpy(server, host, strlen(host));

    if(port > 0) {
        memcpy(server + hostLength, ":", 1);
        memcpy(server + hostLength + 1, pt, strlen(pt));
    }

    MQTTAsync_create(&mClient, server, clientID, MQTTCLIENT_PERSISTENCE_NONE, NULL);
    conn_opts.keepAliveInterval = keepalive;
    conn_opts.cleansession = cleanSession;
    conn_opts.automaticReconnect = 1;
	conn_opts.onSuccess = OnConnect;
    conn_opts.onFailure = OnConnectFailure;
    conn_opts.context = mClient;
    conn_opts.ssl = &ssl_opts;	

    if(userName) {
		memset(mUserName, 0, sizeof(mUserName));
		memcpy(mUserName, userName, strlen(userName));
        conn_opts.username = mUserName;
   	}
    if(password) {
		memset(mUserPass, 0, sizeof(mUserPass));
		memcpy(mUserPass, password, strlen(password));
        conn_opts.password = mUserPass;
   	}
    ssl_opts.enableServerCertAuth = enableServerCertAuth;
    
    mSubscribeTopicsize = subscribeTopicSize;
    for(i = 0; i < subscribeTopicSize; i++) {
        memset(mSubscribeTopic[i], 0, sizeof(mSubscribeTopic[i]));
        strncpy(mSubscribeTopic[i], subscribeTopic[i], strlen(subscribeTopic[i]));
    }

    memset(mPublishTopic, 0, sizeof(mPublishTopic));
    memcpy(mPublishTopic, publishTopic, strlen(publishTopic));
#ifdef SPT_DEBUG_ENABLE
	SPTekDebugLog(LOG_LEVEL_INFO, "MQTTAsyncCreate() publish topic : %s", publishTopic);
#else
	SKTDebugPrint(LOG_LEVEL_INFO, "MQTTAsyncCreate() publish topic : %s", publishTopic);
#endif
    MQTTAsync_setCallbacks(mClient, NULL, ConnectionLostCallback, MessageArrivedCallback, MessageDeliveredCallback);

    if ((rc = MQTTAsync_connect(mClient, &conn_opts)) != MQTTASYNC_SUCCESS){
        MQTTAsyncDestroy();
        return rc;
    }

    return rc;
}

/**
 * @brief async subscribe
 * @param[in] topic The subscription topic, which may include wildcards.
 * @param[in] qos The requested quality of service for the subscription.
 * @return MQTTASYNC_SUCCESS if the subscription request is successful.
 */
int MQTTAsyncSubscribe(char* topic, int qos) {
    MQTTAsync_responseOptions opts = MQTTAsync_responseOptions_initializer;
    opts.onSuccess = OnSubscribe;
    opts.onFailure = OnSubscribeFailure;
    opts.context = mClient;
    int rc = MQTTAsync_subscribe(mClient, topic, qos, &opts);
    return rc;
}

/**
 * @brief publish message
 * @param[in] payload A pointer to the payload of the MQTT message.
 * @return MQTTASYNC_SUCCESS if the message is accepted for publication.
 */
int MQTTAsyncPublishMessage(char* payload) {
    if(mClient == NULL || mPublishTopic == NULL || payload == NULL) {
        return MQTTASYNC_FAILURE;
    }
    MQTTAsync_message pubmsg = MQTTAsync_message_initializer;
    MQTTAsync_responseOptions opts = MQTTAsync_responseOptions_initializer;

    pubmsg.payload = payload;
    pubmsg.payloadlen = strlen(payload);
    pubmsg.qos = 2;
    pubmsg.retained = 0;
    
    int rc = MQTTAsync_sendMessage(mClient, mPublishTopic, &pubmsg, &opts);
    return rc;
}

/**
 * @brief disconnect mqtt
 */
int MQTTAsyncDisconnect() {
#ifdef SPT_DEBUG_ENABLE
	SPTekDebugLog(LOG_LEVEL_INFO, "MQTTAsyncDisconnect()");
#else
	SKTDebugPrint(LOG_LEVEL_INFO, "MQTTAsyncDisconnect()");
#endif
    MQTTAsync_disconnectOptions disc_opts = MQTTAsync_disconnectOptions_initializer;
    disc_opts.onSuccess = OnDisconnect;
    int rc;
	if(mClient != NULL) {
		rc = MQTTAsync_disconnect(mClient, &disc_opts);
	}
    return rc;
}

/**
 * @brief destroy mqtt
 */
void MQTTAsyncDestroy() {
#ifdef SPT_DEBUG_ENABLE
	SPTekDebugLog(LOG_LEVEL_INFO, "MQTTAsyncDestroy()");
#else
    SKTDebugPrint(LOG_LEVEL_INFO, "MQTTAsyncDestroy()");
#endif
	if(gContent) {
		if(gContent->data) {
			free(gContent->data);
			gContent->data = NULL;
		}
		free(gContent);
		gContent = NULL;
	}
	if(mClient != NULL) {
        // disconnect when connected.
        if(MQTTAsync_isConnected(mClient)) {
            MQTTAsyncDisconnect();
        }
        MQTTAsync_destroy(&mClient);
		mClient = NULL;
	}
}

/**
 * @brief is connected
 * @return true : connected <-> false
 */
int MQTTAsyncIsConnected() {
    int rc = MQTTAsync_isConnected(mClient);
    return rc;
}
