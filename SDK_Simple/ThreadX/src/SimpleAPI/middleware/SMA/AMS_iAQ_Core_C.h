/*
 * AMS_iAQ_Core_C.h
 *
 *  Created on: Dec 6, 2016
 *      Author: lycan
 */

#ifndef AMS_IAQ_CORE_C_H_
#define AMS_IAQ_CORE_C_H_

#include "user_thread.h"

typedef struct _AMSiAQCoreData
{
    uint16_t Prediction;
    uint8_t Status;
    int32_t Resistance;
    uint16_t TVOC;
} AMSiAQCoreData;
ssp_err_t AMSiAQCoreInitialize(void);
ssp_err_t AMSiAQCoreUpdateSensors(i2c_master_instance_t * const i2c, uint8_t address, AMSiAQCoreData * const data, bool retryIfBusy);

#endif /* AMS_IAQ_CORE_C_H_ */
