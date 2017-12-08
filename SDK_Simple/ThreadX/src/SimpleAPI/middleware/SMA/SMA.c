
/**
 * @file SMA.c
 *
 * @brief SensorManagementAgent Process
 *
 * Copyright (C) 2016. SKT, All Rights Reserved.
 * Written 2016,by SKT
 */

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define __DUMMY_DATA_
#include <SimpleAPI/middleware/SMA/SMA.h>
#ifndef __DUMMY_DATA_
#include <SimpleAPI/middleware/SMA/I2C.h>
#include <SimpleAPI/middleware/SMA/AMS_iAQ_Core_C.h>
#include <SimpleAPI/middleware/SMA/TMD3782.h>
#include <SimpleAPI/middleware/SMA/ENS210.h>
#endif

#define SMA_STRCMP(x,y) (strncmp(x,y,strlen(y)) == 0)&&(strlen(x) == strlen(y))

#ifndef __DUMMY_DATA_
static uint8_t const iAQCoreCAddress = 0x5A;
static uint8_t const tmd3782Address = 0x39;
static uint8_t const ens210Address = 0x43;
static AMSiAQCoreData aMSiAQCoreData;
static ENS210Data ens210Data;
static TMD3782Data tmd3782Data;

extern unsigned int ntp_time(void);
#endif


void SMAGetData(char *sensorType, char** output, int *len)
{
#ifndef __DUMMY_DATA_
    char str[128];
#else
    unsigned int random_num = ntp_time() * ntp_time() % 100000000;
#endif

    if( SMA_STRCMP(sensorType,"batterystate") ) {
        *output = strdup("1");
        *len = strlen(*output);
    } else if( SMA_STRCMP(sensorType, "temp1") ) {
#ifndef __DUMMY_DATA_
        ENS210UpdateSensors((i2c_master_instance_t * const)&g_i2c, ens210Address, &ens210Data);
        sprintf(str,"%4.2f", ens210Data.Temperature);
        *output = strdup(str);
        *len = strlen(*output);
#else
        *output = strdup("XX.XX");
        sprintf(*output, "%.2f", (random_num%5000+2000)/100.0);
        *len = strlen(*output);
#endif
    } else if( SMA_STRCMP(sensorType, "humi1") ) {
#ifndef __DUMMY_DATA_
        ENS210UpdateSensors((i2c_master_instance_t * const)&g_i2c, ens210Address, &ens210Data);
        sprintf(str,"%4.2f", ens210Data.Humidity);
        *output = strdup(str);
        *len = strlen(*output);
#else
        *output = strdup("XX");
        sprintf(*output, "%2d",(random_num%10+40));
        *len = strlen(*output);
#endif
    } else if( SMA_STRCMP(sensorType, "light1") ) {
#ifndef __DUMMY_DATA_
        TMD3782UpdateSensors((i2c_master_instance_t * const)&g_i2c, tmd3782Address, &tmd3782Data);
        sprintf(str, "%d", tmd3782Data.Clear);
        *output = strdup(str);
        *len = strlen(*output);
#else
        *output = strdup("XXX");
        sprintf(*output, "%3d", (random_num%50+200));
        *len = strlen(*output);
#endif
    } else {
        *output = NULL;
    }
}

#ifndef __DUMMY_DATA_
ioport_level_t level = IOPORT_LEVEL_HIGH;
static int ledStatus = 0;
int RGB_LEDControl(int act7colorLed)
{
    bsp_leds_t leds;
    if(0 == leds.led_count)
        return 0;
    /* Get LED information for this board */
    R_BSP_LedsGet(&leds);

    ioport_level_t level;

    if( act7colorLed == 0 ) {
        level = IOPORT_LEVEL_HIGH;
    } else{
        level = IOPORT_LEVEL_LOW;
    }

    /* Update all board LEDs */
    for(uint32_t i = 0; i < leds.led_count; i++)
    {
        g_ioport.p_api->pinWrite(leds.p_leds[i], level);
    }
    return level;
}

int RGB_LEDStatus(void)
{
    return ledStatus;
}
#else
static int ledStatus = 0;

int RGB_LEDControl(int color)
{
    // r=1,g=2,b=3,c=5,m=4,y=6,w=7,off=0
    if( color == 1 ) {
        ledStatus = 1;
    } else if( color == 2 ) {
        ledStatus = 2;
    } else if( color == 3 ) {
        ledStatus = 3;
    } else if( color == 4 ) {
        ledStatus = 4;
    } else if( color == 5 ) {
        ledStatus = 5;
    } else if( color == 6 ) {
        ledStatus = 6;
    } else if( color == 7 ) {
        ledStatus = 7;
    } else if( color == 0 ) {
        ledStatus = 0;
    } else {
        return -1;
    }
    return 0;
}


int RGB_LEDStatus() 
{
    return ledStatus;
}
#endif


int SensorReady(void)
{
#ifndef __DUMMY_DATA_
    int error_count = 0;
    ssp_err_t error;


    uint8_t ens210systemControl;
    uint16_t ens210ChipId;
    uint8_t ens210UniqueId[8];
    uint8_t ens210SensorRunMode;
    uint8_t ens210systemStatus;
    uint8_t ens210SensorStarted;
    uint8_t ens210SensorStatus;

    uint8_t tmd3782ChipId;
    uint8_t tmd3782Status;

    ENS210Initialize();
    TMD3782Initialize();
    AMSiAQCoreInitialize();

    error = I2COpen ((i2c_master_instance_t * const)&g_i2c);
    if(error != SSP_SUCCESS)
        error_count++;

    error = ENS210Open((i2c_master_instance_t * const)&g_i2c, ens210Address);
    if(error != SSP_SUCCESS)
        error_count++;

    error = TMD3782Open((i2c_master_instance_t * const)&g_i2c, tmd3782Address);
    if(error != SSP_SUCCESS)
        error_count++;
    error = ENS210ChipId((i2c_master_instance_t * const)&g_i2c, ens210Address, &ens210ChipId);
    if (error != SSP_SUCCESS)
        error_count++;

    error = ENS210UniqueId((i2c_master_instance_t * const)&g_i2c, ens210Address, ens210UniqueId);
    if (error != SSP_SUCCESS)
        error_count++;
    error = ENS210GetSystemControl((i2c_master_instance_t * const)&g_i2c, ens210Address, &ens210systemControl);
    if (error != SSP_SUCCESS)
        error_count++;
    error = ENS210GetSystemControl((i2c_master_instance_t * const)&g_i2c, ens210Address, &ens210systemStatus);
    if (error != SSP_SUCCESS)
        error_count++;
    error = ENS210GetSensorRunMode((i2c_master_instance_t * const)&g_i2c, ens210Address, &ens210SensorRunMode);
    if (error != SSP_SUCCESS)
        error_count++;
    error = ENS210GetSensorStart((i2c_master_instance_t * const)&g_i2c, ens210Address, &ens210SensorStarted);
    if (error != SSP_SUCCESS)
        error_count++;
    error = ENS210GetSensorStatus((i2c_master_instance_t * const)&g_i2c, ens210Address, &ens210SensorStatus);
    if (error != SSP_SUCCESS)
        error_count++;
    error = TMD3782ChipId((i2c_master_instance_t * const)&g_i2c, tmd3782Address, &tmd3782ChipId);
    if (error != SSP_SUCCESS)
        error_count++;
    error = TMD3782Status((i2c_master_instance_t * const)&g_i2c, tmd3782Address, &tmd3782Status);
    if (error != SSP_SUCCESS)
        error_count++;

    if( error_count )
        return -1;
#endif
    return 0;
}

#define __SENSOR_DEBUG__

int SensorUpdate(void)
{
#ifndef __DUMMY_DATA_
    char str[128];
    AMSiAQCoreUpdateSensors((i2c_master_instance_t * const)&g_i2c, iAQCoreCAddress, &aMSiAQCoreData, true);
    ENS210UpdateSensors((i2c_master_instance_t * const)&g_i2c, ens210Address, &ens210Data);
    TMD3782UpdateSensors((i2c_master_instance_t * const)&g_i2c, tmd3782Address, &tmd3782Data);
#ifdef __SENSOR_DEBUG__
    memset(str,0,128);
    sprintf(str,"Prediction: %d ppm\r\n", aMSiAQCoreData.Prediction);
    g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, str, sizeof(str), TX_NO_WAIT);
    memset(str,0,128);
    sprintf(str,"Resistance: %lu Ohms\r\n", aMSiAQCoreData.Resistance);
    g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, str, sizeof(str), TX_NO_WAIT);
    memset(str,0,128);
    sprintf(str,"TVOC: %d ppb\r\n", aMSiAQCoreData.TVOC);
    g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, str, sizeof(str), TX_NO_WAIT);

    memset(str,0,128);
    sprintf(str," T: %4.1fC\r\n", ens210Data.Temperature);
    g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, str, sizeof(str), TX_NO_WAIT);
    memset(str,0,128);
    sprintf(str,"RH: %4.1f%%\r\n", ens210Data.Humidity);
    g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, str, sizeof(str), TX_NO_WAIT);
    sprintf(str, "    Clear: %d\r\n", tmd3782Data.Clear);
    g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, str, sizeof(str), TX_NO_WAIT);
    sprintf(str, "      Red: %d\r\n", tmd3782Data.Red);
    g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, str, sizeof(str), TX_NO_WAIT);
    sprintf(str, "    Green: %d\r\n", tmd3782Data.Green);
    g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, str, sizeof(str), TX_NO_WAIT);
    sprintf(str, "     Blue: %d\r\n", tmd3782Data.Blue);
    g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, str, sizeof(str), TX_NO_WAIT);
    sprintf(str, "Proximity: %d\r\n", tmd3782Data.Proximity);
    g_sf_comms0.p_api->write(g_sf_comms0.p_ctrl, str, sizeof(str), TX_NO_WAIT);

    RGB_LEDControl(1);
    tx_thread_sleep(300);
    RGB_LEDControl(0);
    tx_thread_sleep(300);
    RGB_LEDControl(1);
#endif
#endif
    return 0;

}


