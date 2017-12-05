/*
 * ENS210.c
 *
 *  Created on: Dec 6, 2016
 *      Author: lycan
 */


#include "ENS210.h"
#include "I2C.h"
#include <stdio.h>

/* Constant Structure to hold the addresses for the various registers */
const struct
{
    uint8_t PART_ID;
    uint8_t UID;
    uint8_t SYS_CTRL;
    uint8_t SYS_STAT;
    uint8_t SENS_RUN;
    uint8_t SENS_START;
    uint8_t SENS_STOP;
    uint8_t SENS_STAT;
    uint8_t T_VAL;
    uint8_t H_VAL;
} ENS210RegisterAddresses =
{ 0x00, // PART_ID
  0x04, // UID
  0x10, // SYS_CTRL
  0x11, // SYS_STAT
  0x21, // SENS_RUN
  0x22, // SENS_START
  0x23, // SENS_STOP
  0x24, // SENS_STAT
  0x30, // T_VAL
  0x33  // H_VAL
        };

ssp_err_t ENS210Initialize(void)
{
    return SSP_SUCCESS;
}

ssp_err_t ENS210Open(i2c_master_instance_t * const i2c, uint8_t address)
{
    ssp_err_t error;

    // Disable Low Power Mode
    error = ENS210SetSystemControl (i2c, address, 0x00);
    if (error != SSP_SUCCESS)
        return error;

    // Wait for sensor to be active
    uint8_t status = 0;
    tx_thread_sleep (50);
    error = ENS210GetSystemStatus (i2c, address, &status);
    while ((status & 0x01) == 0)
    {
        tx_thread_sleep (50);
        error = ENS210GetSystemStatus (i2c, address, &status);
        if (error != SSP_SUCCESS)
            return error;
    }

    // Enable Continuous Measurements
    error = ENS210SetSensorRunMode (i2c, address, 0x03);
    if (error != SSP_SUCCESS)
        return error;

    // Start R & H Measurements
    error = ENS210SetSensorStart (i2c, address, 0x03);
    if (error != SSP_SUCCESS)
        return error;

    return SSP_SUCCESS;
}

ssp_err_t ENS210SetSystemControl(i2c_master_instance_t * const i2c, uint8_t address, uint8_t const systemControl)
{
    return I2CWriteRegister (i2c, address, ENS210RegisterAddresses.SYS_CTRL, systemControl, false);
}

ssp_err_t ENS210GetSystemControl(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const systemControl)
{
    return I2CReadRegister (i2c, address, ENS210RegisterAddresses.SYS_CTRL, systemControl, 1, false);
}

ssp_err_t ENS210GetSystemStatus(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const systemStatus)
{
    return I2CReadRegister (i2c, address, ENS210RegisterAddresses.SYS_STAT, systemStatus, 1, false);
}

ssp_err_t ENS210SetSensorRunMode(i2c_master_instance_t * const i2c, uint8_t address, uint8_t const sensorRunMode)
{
    return I2CWriteRegister (i2c, address, ENS210RegisterAddresses.SENS_RUN, *((uint8_t *) &sensorRunMode), false);
}

ssp_err_t ENS210GetSensorRunMode(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const sensorRunMode)
{
    return I2CReadRegister (i2c, address, ENS210RegisterAddresses.SENS_RUN, sensorRunMode, 1, false);
}

ssp_err_t ENS210SetSensorStart(i2c_master_instance_t * const i2c, uint8_t address, uint8_t const sensorStart)
{
    return I2CWriteRegister (i2c, address, ENS210RegisterAddresses.SENS_START, *((uint8_t *) &sensorStart), false);
}
ssp_err_t ENS210GetSensorStart(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const sensorStart)
{
    return I2CReadRegister (i2c, address, ENS210RegisterAddresses.SENS_START, sensorStart, 1, false);
}

ssp_err_t ENS210SetSensorStop(i2c_master_instance_t * const i2c, uint8_t address, uint8_t const sensorStop)
{
    return I2CWriteRegister (i2c, address, ENS210RegisterAddresses.SENS_STOP, *((uint8_t *) &sensorStop), false);
}

ssp_err_t ENS210GetSensorStop(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const sensorStop)
{
    return I2CReadRegister (i2c, address, ENS210RegisterAddresses.SENS_STOP, sensorStop, 1, false);
}

ssp_err_t ENS210GetSensorStatus(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const sensorStatus)
{
    return I2CReadRegister (i2c, address, ENS210RegisterAddresses.SENS_STOP, sensorStatus, 1, false);
}

ssp_err_t ENS210ChipId(i2c_master_instance_t * const i2c, uint8_t address, uint16_t * const chipId)
{
    return I2CReadRegister (i2c, address, ENS210RegisterAddresses.PART_ID, (uint8_t *) chipId, 2, false);
}

ssp_err_t ENS210UniqueId(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const uniqueId)
{
    return I2CReadRegister (i2c, address, ENS210RegisterAddresses.UID, uniqueId, 8, false);
}

ssp_err_t ENS210UpdateSensors(i2c_master_instance_t * const i2c, uint8_t address, ENS210Data * const data)
{
    ssp_err_t error;

    uint8_t buffer[6] =
    { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
    uint16_t rawTemp;
    uint16_t rawHumi;

    error = I2CReadRegister(i2c, address, ENS210RegisterAddresses.T_VAL, buffer, 6, false);
    if (error != SSP_SUCCESS)
        return error;

    // Cast Data
    rawTemp = (uint16_t) ((buffer[1] << 8) | buffer[2]);
    rawHumi = (uint16_t) ((buffer[4] << 8) | buffer[5]);

    data->Temperature = (float) ((rawTemp / 64.0) - 273.15);
    data->Humidity = (float) ((rawHumi / 512.0));

    return SSP_SUCCESS;
}
