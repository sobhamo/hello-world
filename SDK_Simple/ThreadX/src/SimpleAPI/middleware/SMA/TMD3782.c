/*
 * TMD3782.c
 *
 *  Created on: Dec 12, 2016
 *      Author: lycan
 */

#include "I2C.h"
#include "TMD3782.h"

const struct TMD3782RGBCIntegrationTimes TMD3782RGBCIntegrationTimes =
{ 0xFF, // Cycles1
  0xF6, // Cycles10
  0xD6, // Cycles42
  0xC0, // Cycles64
  0x00, // Cycles256
        };

/* Constant Structure to command values */
const struct
{
    uint8_t Command;
    uint8_t RepeatedByte;
    uint8_t AutoIncrementAddress;
    uint8_t SpecialFunction;
    uint8_t Normal;
    uint8_t ProximityInterruptClear;
    uint8_t ClearChannelInterruptClear;
    uint8_t ProximityAndClearInterruptClear;
} TMD3782Commands =
{ 0x80, // Command
  0x00, // RepeatedByte
  0x10, // AutoIncrementAddress
  0x60, // SpecialFunction
  0x00, // Normal
  0x05, // ProximityInterruptClear
  0x06, // ClearChannelInterruptClear
  0x07 // ProximityAndClearInterruptClear
        };

/* Constant Structure to register addresses */
const struct
{
    uint8_t ENABLE;
    uint8_t ATIME;
    uint8_t WTIME;
    uint8_t AILT;
    uint8_t AIHT;
    uint8_t PILT;
    uint8_t PIHT;
    uint8_t PERS;
    uint8_t CONFIG;
    uint8_t PPULSE;
    uint8_t CONTROL;
    uint8_t REVISION;
    uint8_t ID;
    uint8_t STATUS;
    uint8_t CDATA;
    uint8_t RDATA;
    uint8_t GDATA;
    uint8_t BDATA;
    uint8_t PDATA;
} TMD3782RegisterAddresses =
{ 0x00, // ENABLE
  0x01, // ATIME
  0x03, // WTIME
  0x04, // AILT
  0x06, // AIHT
  0x08, // PILT
  0x0A, // PIHT
  0x0C, // PERS
  0x0D, // CONFIG
  0x0E, // PPULSE
  0x0F, // CONTROL
  0x11, // REVISION
  0x12, // ID
  0x13, // STATUS
  0x14, // CDATA
  0x16, // RDATA
  0x18, // GDATA
  0x1A, // BDATA
  0x1C, // PDATA
        };

ssp_err_t TMD3782Initialize(void)
{
    return SSP_SUCCESS;
}

ssp_err_t TMD3782SetEnableRegister(i2c_master_instance_t * const i2c, uint8_t address, bool proximityInterruptEnabled,
        bool ambientLightSensingInterruptEnabled, bool waitEnabled, bool proxmityEnabled, bool adcEnabled, bool powerOn)
{
    // Create Command Byte
    uint8_t commandByte = 0x00;

    commandByte |= TMD3782Commands.Command;
    commandByte |= proximityInterruptEnabled ? (1 << 5) : 0x00;
    commandByte |= ambientLightSensingInterruptEnabled ? (1 << 4) : 0x00;
    commandByte |= waitEnabled ? (1 << 3) : 0x00;
    commandByte |= proxmityEnabled ? (1 << 2) : 0x00;
    commandByte |= adcEnabled ? (1 << 1) : 0x00;
    commandByte |= powerOn ? (1 << 0) : 0x00;

    return I2CWriteRegister (i2c, address, TMD3782RegisterAddresses.ENABLE, commandByte, false);
}

ssp_err_t TMD3782GetEnableRegister(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const enableValue)
{
    ssp_err_t error;

    // Read the enable register.
    error = I2CReadRegister (i2c, address, TMD3782Commands.Command | TMD3782RegisterAddresses.ENABLE, enableValue, 1,
                             false);
    if (error != SSP_SUCCESS)
        return error;

    return SSP_SUCCESS;

}

ssp_err_t TMD3782SetRGBCIntegrationTimeRegister(i2c_master_instance_t * const i2c, uint8_t address,
        uint8_t rgbcIntegrationTimeValue)
{
    return I2CWriteRegister (i2c, address, TMD3782Commands.Command | TMD3782RegisterAddresses.ATIME,
                             rgbcIntegrationTimeValue, false);
}

ssp_err_t TMD3782GetRGBCIntegrationTimeRegister(i2c_master_instance_t * const i2c, uint8_t address,
        uint8_t * const rgbcIntegrationTimeValue)
{
    return I2CReadRegister (i2c, address, TMD3782Commands.Command | TMD3782RegisterAddresses.ATIME,
                            rgbcIntegrationTimeValue, 1, false);
}

ssp_err_t TMD3782ChipId(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const chipId)
{
    ssp_err_t error;

    // Read the chip ID.
    error = I2CReadRegister (i2c, address, TMD3782Commands.Command | TMD3782RegisterAddresses.ID, chipId, 1, false);
    if (error != SSP_SUCCESS)
        return error;

    return SSP_SUCCESS;
}

ssp_err_t TMD3782Open(i2c_master_instance_t * const i2c, uint8_t address)
{
    ssp_err_t error;

    // Write WEN, PEN, AEN, PON = 1 to ENABLE register
    error = TMD3782SetEnableRegister (i2c, address, false, false, true, true, true, true);
    if (error != SSP_SUCCESS)
        return error;

    // Configure for 64 cycle integration time
    error = I2CWriteRegister (i2c, address, TMD3782Commands.Command | TMD3782RegisterAddresses.ATIME, 0xC0, false);
    if (error != SSP_SUCCESS)
        return error;

    // Set distance pulses to 30 pules per second
    error = I2CWriteRegister (i2c, address, TMD3782Commands.Command | TMD3782RegisterAddresses.PPULSE, 30, false);
    if (error != SSP_SUCCESS)
        return error;

    return SSP_SUCCESS;
}

ssp_err_t TMD3782Status(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const status)
{
    // Read the status register
    return I2CReadRegister (i2c, address, TMD3782Commands.Command | TMD3782RegisterAddresses.STATUS, status, 1, false);
}

ssp_err_t TMD3782UpdateSensors(i2c_master_instance_t * const i2c, uint8_t address, TMD3782Data * const data)
{
    ssp_err_t error;
    uint8_t rawData[10] =
    { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

    // Read 10 bytes starting at 0x14 (using address auto-increment)
    error = I2CReadRegister (
            i2c, address,
            TMD3782Commands.Command | TMD3782Commands.AutoIncrementAddress | TMD3782RegisterAddresses.CDATA, rawData,
            10, false);
    if (error != SSP_SUCCESS)
        return error;

    // Map data to sensor values
    data->Clear = (uint16_t) ((rawData[1] << 8) | rawData[0]);
    data->Red = (uint16_t) ((rawData[3] << 8) | rawData[2]);
    data->Green = (uint16_t) ((rawData[5] << 8) | rawData[4]);
    data->Blue = (uint16_t) ((rawData[7] << 8) | rawData[6]);
    data->Proximity = (uint16_t) ((rawData[9] << 8) | rawData[8]);

    return SSP_SUCCESS;
}

ssp_err_t TMD3782ProximityInterruptClear(i2c_master_instance_t * const i2c, uint8_t address)
{
    ssp_err_t error;

    // Clear the Proximity Interrupt
    error = I2CWriteByte (
            i2c, address,
            TMD3782Commands.Command | TMD3782Commands.SpecialFunction | TMD3782Commands.ProximityInterruptClear, false);
    if (error != SSP_SUCCESS)
        return error;

    return SSP_SUCCESS;
}

ssp_err_t TMD3782ClearInterruptClear(i2c_master_instance_t * const i2c, uint8_t address)
{
    ssp_err_t error;

    // Clear the Clear Interrupt
    error = I2CWriteByte (
            i2c, address,
            TMD3782Commands.Command | TMD3782Commands.SpecialFunction | TMD3782Commands.ClearChannelInterruptClear,
            false);
    if (error != SSP_SUCCESS)
        return error;

    return SSP_SUCCESS;
}

ssp_err_t TMD3782ProximityAndInterruptClear(i2c_master_instance_t * const i2c, uint8_t address)
{
    ssp_err_t error;

    // Clear the Proximity and Clear Interrupt
    error = I2CWriteByte (
            i2c, address,
            TMD3782Commands.Command | TMD3782Commands.SpecialFunction | TMD3782Commands.ProximityAndClearInterruptClear,
            false);
    if (error != SSP_SUCCESS)
        return error;

    return SSP_SUCCESS;
}

