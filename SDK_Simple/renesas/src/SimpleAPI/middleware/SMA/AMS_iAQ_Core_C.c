/*
 * AMS_iAQ_Core_C.c
 *
 *  Created on: Dec 6, 2016
 *      Author: lycan
 */

#include "I2C.h"
#include "AMS_iAQ_Core_C.h"

ssp_err_t AMSiAQCoreInitialize(void)
{
    return SSP_SUCCESS;
}

ssp_err_t AMSiAQCoreUpdateSensors(i2c_master_instance_t * const i2c, uint8_t address, AMSiAQCoreData * const data,
        bool retryIfBusy)
{
    ssp_err_t error;
    int attempts = 0;

    // Create buffer for iAQ data
    uint8_t amsData[9] =
    { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

    // Reset structure values
    data->Prediction = 0;
    data->Status = 0;
    data->Resistance = 0;
    data->TVOC = 0;

    do
    {
        // Increment attempts
        attempts++;

        // Read sensor values
        error = I2CRead (i2c, address, amsData, 9, false);
        if (error != SSP_SUCCESS)
            return error;

        // Check if we've hit our attempt limit
        if (attempts >= 10)
        {
            return SSP_ERR_TIMEOUT;
        }
    } // Continue loop if the sensor was busy AND we want to retry
    while ((retryIfBusy == true) && (amsData[2] == 0x01));

    // Update data structure
    data->Prediction = (uint16_t) ((amsData[0] << 8) + amsData[1]);
    data->Status = amsData[2];
    data->Resistance = (int32_t) ((amsData[3] << 24) | (amsData[4] << 16) | (amsData[5] << 8) | (amsData[6]));
    data->TVOC = (uint16_t) ((amsData[7] << 8) + amsData[8]);

    return SSP_SUCCESS;
}
