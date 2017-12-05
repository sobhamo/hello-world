/*
 * ENS210.h
 *
 *  Created on: Dec 6, 2016
 *      Author: lycan
 */

#ifndef ENS210_H_
#define ENS210_H_

#include "user_thread.h"

typedef struct _ENS210Data
{
    float Temperature;
    float Humidity;
} ENS210Data;

ssp_err_t ENS210Initialize(void);
ssp_err_t ENS210Open(i2c_master_instance_t * const i2c, uint8_t address);
ssp_err_t ENS210ChipId(i2c_master_instance_t * const i2c, uint8_t address, uint16_t * const chipId);
ssp_err_t ENS210UniqueId(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const uniqueId);
ssp_err_t ENS210UpdateSensors(i2c_master_instance_t * const i2c, uint8_t address, ENS210Data * const data);
ssp_err_t ENS210SetSystemControl(i2c_master_instance_t * const i2c, uint8_t address, uint8_t systemControl);
ssp_err_t ENS210GetSystemControl(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const systemControl);
ssp_err_t ENS210GetSystemStatus(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const systemStatus);
ssp_err_t ENS210SetSensorRunMode(i2c_master_instance_t * const i2c, uint8_t address, uint8_t const sensorRunMode);
ssp_err_t ENS210GetSensorRunMode(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const sensorRunMode);
ssp_err_t ENS210SetSensorStart(i2c_master_instance_t * const i2c, uint8_t address, uint8_t const sensorStart);
ssp_err_t ENS210GetSensorStart(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const sensorStart);
ssp_err_t ENS210SetSensorStop(i2c_master_instance_t * const i2c, uint8_t address, uint8_t const sensorStop);
ssp_err_t ENS210GetSensorStop(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const sensorStop);
ssp_err_t ENS210GetSensorStatus(i2c_master_instance_t * const i2c, uint8_t address, uint8_t * const sensorStatus);

#endif /* ENS210_H_ */
