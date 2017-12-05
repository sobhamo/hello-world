
#include "I2C.h"


ssp_err_t I2CInitialize(void)
{
    return SSP_SUCCESS;
}

ssp_err_t I2COpen(i2c_master_instance_t * const i2c)
{
    ssp_err_t error;

    // Open I2C
    error = i2c->p_api->open (i2c->p_ctrl, i2c->p_cfg);
    if (error != SSP_SUCCESS)
        return error;

    return SSP_SUCCESS;
}

ssp_err_t I2CClose(i2c_master_instance_t * const i2c)
{
    ssp_err_t error;

    // Close I2C
    error = i2c->p_api->close (i2c->p_ctrl);
    if (error != SSP_SUCCESS)
        return error;

    return SSP_SUCCESS;
}

ssp_err_t I2CWrite(i2c_master_instance_t * const i2c, uint8_t slaveAddress, uint8_t * const data, uint32_t const length, bool const restart)
{
    // Set the slave address to the device we want to talk to.
    i2c->p_api->slaveAddressSet(i2c->p_ctrl, slaveAddress, I2C_ADDR_MODE_7BIT);

    // Write data to the slave
    return i2c->p_api->write (i2c->p_ctrl, data, length, restart);
}

ssp_err_t I2CWriteRegister(i2c_master_instance_t * const i2c, uint8_t slaveAddress, uint8_t registerAddress, uint8_t data, bool const restart)
{
    uint8_t slaveData[2] = {registerAddress, data};
    // Write single byte to slave
    return I2CWrite (i2c, slaveAddress, (uint8_t * const ) slaveData, 2, restart);
}

ssp_err_t I2CWriteByte(i2c_master_instance_t * const i2c, uint8_t slaveAddress, uint8_t const data, bool const restart)
{
    // Write single byte to slave
    return I2CWrite (i2c, slaveAddress, (uint8_t * const ) &data, 1, restart);
}

ssp_err_t I2CRead(i2c_master_instance_t * const i2c, uint8_t slaveAddress, uint8_t * const data, uint32_t const length, bool const restart)
{
    // Set the slave address to the device we want to talk to.
    i2c->p_api->slaveAddressSet(i2c->p_ctrl, slaveAddress, I2C_ADDR_MODE_7BIT);

    // Read data from the slave
    return i2c->p_api->read (i2c->p_ctrl, data, length, restart);
}

ssp_err_t I2CReadByte(i2c_master_instance_t * const i2c, uint8_t slaveAddress, uint8_t * const data, bool const restart)
{
    // Read single byte from slave
    return I2CRead (i2c, slaveAddress, data, 1, restart);
}

ssp_err_t I2CReadRegister(i2c_master_instance_t * const i2c, uint8_t slaveAddress, uint8_t registerAddress, uint8_t * const data, uint32_t const length, bool const restart)
{
    ssp_err_t error;
    error = I2CWriteByte(i2c, slaveAddress, registerAddress, true);
    if (error != SSP_SUCCESS)
         return error;

    error = I2CRead(i2c, slaveAddress, data, length, restart);
    if (error != SSP_SUCCESS)
        return error;

     return SSP_SUCCESS;
}
