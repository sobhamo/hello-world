#ifndef SPT_I2C_H_
#define SPT_I2C_H_

#include "user_thread.h"

#include <stdbool.h>


ssp_err_t I2CInitialize(void);
ssp_err_t I2COpen(i2c_master_instance_t * const i2c);
ssp_err_t I2CClose(i2c_master_instance_t * const i2c);
ssp_err_t I2CWrite(i2c_master_instance_t * const i2c, uint8_t slaveAddress, uint8_t * const data, uint32_t const length, bool const restart);
ssp_err_t I2CWriteRegister(i2c_master_instance_t * const i2c, uint8_t slaveAddress, uint8_t registerAddress, uint8_t data, bool const restart);
ssp_err_t I2CWriteByte(i2c_master_instance_t * const i2c, uint8_t slaveAddress, uint8_t const data, bool const restart);
ssp_err_t I2CRead(i2c_master_instance_t * const i2c, uint8_t slaveAddress, uint8_t * const data, uint32_t const length, bool const restart);
ssp_err_t I2CReadByte(i2c_master_instance_t * const i2c, uint8_t slaveAddress, uint8_t * const data, bool const restart);
ssp_err_t I2CReadRegister(i2c_master_instance_t * const i2c, uint8_t slaveAddress, uint8_t registerAddress, uint8_t * const data, uint32_t const length, bool const restart);


#endif /* SPT_I2C_H_ */
