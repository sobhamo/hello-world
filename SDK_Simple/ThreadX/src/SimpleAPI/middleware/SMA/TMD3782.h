/*
 * TMD3782.h
 *
 *  Created on: Dec 12, 2016
 *      Author: lycan
 */

#ifndef TMD3782_H_
#define TMD3782_H_

#include "user_thread.h"

typedef struct _TMD3782Data
{
    uint16_t Clear;
    uint16_t Red;
    uint16_t Green;
    uint16_t Blue;
    uint16_t Proximity;
} TMD3782Data;

/* Constant Structure to register addresses */
struct TMD3782RGBCIntegrationTimes
{
    uint8_t Cycles1;
    uint8_t Cycles10;
    uint8_t Cycles42;
    uint8_t Cycles64;
    uint8_t Cycles256;
};



ssp_err_t TMD3782Initialize(void);
ssp_err_t TMD3782Open(i2c_master_instance_t * const i2c, uint8_t address);
ssp_err_t TMD3782SetEnableRegister(i2c_master_instance_t * const i2c, uint8_t address, bool proximityInterruptEnabled, bool ambientLightSensingInterruptEnabled, bool waitEnabled, bool proxmityEnabled, bool adcEnabled, bool powerOn);
ssp_err_t TMD3782GetEnableRegister(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const enableValue);
ssp_err_t TMD3782SetRGBCIntegrationTimeRegister(i2c_master_instance_t * const i2c, uint8_t address, uint8_t rgbcIntegrationTimeValue);
ssp_err_t TMD3782GetRGBCIntegrationTimeRegister(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const rgbcIntegrationTimeValue);
ssp_err_t TMD3782ChipId(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const chipId);
ssp_err_t TMD3782Status(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const status);
ssp_err_t TMD3782UpdateSensors(i2c_master_instance_t * const i2c, uint8_t address, TMD3782Data * const data);
ssp_err_t TMD3782ProximityInterruptClear(i2c_master_instance_t * const i2c, uint8_t address);
ssp_err_t TMD3782ClearInterruptClear(i2c_master_instance_t * const i2c, uint8_t address);
ssp_err_t TMD3782ProximityAndInterruptClear(i2c_master_instance_t * const i2c, uint8_t address);


#endif /* TMD3782_H_ */
