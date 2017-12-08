#include "user_thread.h"
#include "MQTT/MQTTS7G2.h"
#include "MQTT/MQTTS7G2_TLS.h"
#include "MQTT/MQTTClient.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <SimpleAPI/middleware/Configuration.h>
#include <SimpleAPI/middleware/MA/MA.h>
#include <SimpleAPI/middleware/SMA/SMA.h>
#include <unistd.h>

#include <SimpleAPI/include/Simple.h>
#include <SimpleAPI/include/ThingPlug.h>

#include "NTPClient/NTPClient.h"

#define NTP_RETRY_COUNT 10

ULONG error_counter;
UINT status;
ULONG ip0_ip_address = 0;
ULONG ip0_mask = 0;

ULONG     ip_status;
char    str[64];
extern char* deviceIpAddress;
extern char* gatewayIpAddress;

extern void setIpAddress(char *ip, char *gw);
extern int ThingPlug_Simple_SDK(void);

void dhcp_start(void)
{
    status = nx_ip_fragment_enable (&g_ip0);
    if (status != NX_SUCCESS)
        error_counter++;
    status = nx_dhcp_start (&g_dhcp_client0);
    /* Check for DHCP start error. */
    if (status)
        error_counter++;
    while (status != NX_IP_ADDRESS_RESOLVED)
    {
        /* Wait for IP address to be resolved through DHCP. */
        nx_ip_status_check (&g_ip0, NX_IP_ADDRESS_RESOLVED, (ULONG *) &status, 10);
    }
    status = nx_ip_interface_address_get (&g_ip0, 0, &ip0_ip_address, &ip0_mask);
    if (status != NX_SUCCESS)
        error_counter++;

    if(error_counter) {
        g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, "DHCP IP Error\r\n", sizeof("DHCP IP Error\r\n"), TX_NO_WAIT);
    }
    g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, "DHCP Get IP\r\n", sizeof("DHCP Get IP\r\n"), TX_NO_WAIT);
    {
        char ip[30],gw[30];
        snprintf(ip ,30, "%d.%d.%d.%d", (int)(ip0_ip_address>>24), (int)(ip0_ip_address>>16)&0xFF, (int)(ip0_ip_address>>8)&0xFF, (int)(ip0_ip_address)&0xFF );
        g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, ip, strlen(ip), TX_NO_WAIT);
        g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, "\r\n", sizeof("\r\n"), TX_NO_WAIT);
        snprintf(gw ,30, "%d.%d.%d.1" , (int)(ip0_ip_address>>24), (int)(ip0_ip_address>>16)&0xFF, (int)(ip0_ip_address>>8)&0xFF );
        g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, gw, strlen(gw), TX_NO_WAIT);
        g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, "\r\n", sizeof("\r\n"), TX_NO_WAIT);
        setIpAddress(ip,gw);
    }
}

/* User Thread entry function */
void user_thread_entry(void)
{
    g_timer0.p_api->open(g_timer0.p_ctrl, g_timer0.p_cfg);
    tx_thread_sleep(200);
    g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, "ThreadX START!!\r\n", sizeof("ThreadX START!!\r\n"), TX_NO_WAIT);
        
    dhcp_start();

    while(1) {
	int loop_cnt = NTP_RETRY_COUNT;
        while(loop_cnt--) {
	    status = NTPSetTimer();
	    if( status == 0)
	        break;
	}

        ThingPlug_Simple_SDK();
        tx_thread_sleep(500);

        status = 0;
        while( status != NX_IP_LINK_ENABLED) {
            g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, "retry connect\r\n", sizeof("retry connect\r\n"), TX_NO_WAIT);
            nx_ip_status_check( &g_ip0, NX_IP_LINK_ENABLED, (ULONG *) &status, 10);
            tx_thread_sleep(500);
        }
        nx_dhcp_stop (&g_dhcp_client0);
        nx_dhcp_reinitialize (&g_dhcp_client0);
        dhcp_start();
        g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, str, sizeof(str), TX_NO_WAIT);
 
   }
}
