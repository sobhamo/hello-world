/* generated thread header file - do not edit */
#ifndef USER_THREAD_H_
#define USER_THREAD_H_
#include "bsp_api.h"
#include "tx_api.h"
#include "hal_data.h"
#ifdef __cplusplus 
extern "C" void user_thread_entry(void);
#else 
extern void user_thread_entry(void);
#endif
#include "r_sci_i2c.h"
#include "r_i2c_api.h"
#include "sf_el_ux_comms_v2.h"
#include "sf_comms_api.h"
#include "nx_dhcp.h"
#include "r_gpt.h"
#include "r_timer_api.h"
#ifdef __cplusplus
extern "C"
{
#endif
extern const i2c_cfg_t g_i2c_cfg;
/** I2C on SCI Instance. */
extern const i2c_master_instance_t g_i2c;
#ifndef NULL
void NULL(i2c_callback_args_t * p_args);
#endif
/* USB Communication Framework Instance */
extern const sf_comms_instance_t g_sf_comms0;
/* USBX CDC-ACM Instance Activate User Callback Function */
VOID ux_cdc_device0_instance_activate(VOID *cdc_instance);
/* USBX CDC-ACM Instance Deactivate User Callback Function */
VOID ux_cdc_device0_instance_deactivate(VOID *cdc_instance);
void g_sf_comms0_err_callback(void * p_instance, void * p_data);
void sf_comms_init0(void);
extern NX_DHCP g_dhcp_client0;
void g_dhcp_client0_err_callback(void * p_instance, void * p_data);
/** Timer on GPT Instance. */
extern const timer_instance_t g_timer0;
#ifndef SysTickIntHandler
void SysTickIntHandler(timer_callback_args_t * p_args);
#endif
#ifdef __cplusplus
} /* extern "C" */
#endif
#endif /* USER_THREAD_H_ */
