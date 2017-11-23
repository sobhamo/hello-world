/* generated common header file - do not edit */
#ifndef COMMON_DATA_H_
#define COMMON_DATA_H_
#include <stdint.h>
#include "bsp_api.h"
#include "nx_api.h"

#ifdef __cplusplus
extern "C"
{
#endif

#include "ux_api.h"

/* USBX Host Stack initialization error callback function. User can override the function if needed. */
void ux_v2_err_callback(void * p_instance, void * p_data);

#if !defined(NULL)
/* User Callback for Host Event Notification (Only valid for USB Host). */
extern UINT NULL(ULONG event, UX_HOST_CLASS * host_class, VOID * instance);
#endif

#ifdef UX_HOST_CLASS_STORAGE_H
/* Utility function to get the pointer to a FileX Media Control Block for a USB Mass Storage device. */
UINT ux_system_host_storage_fx_media_get(UX_HOST_CLASS_STORAGE * instance, UX_HOST_CLASS_STORAGE_MEDIA ** p_storage_media, FX_MEDIA ** p_fx_media);
#endif
#include "ux_api.h"
#include "ux_dcd_synergy.h"
#include "sf_el_ux_dcd_fs_cfg.h"
void g_sf_el_ux_dcd_fs_0_err_callback(void * p_instance, void * p_data);
#include "ux_api.h"
#include "ux_dcd_synergy.h"

/* USBX Device Stack initialization error callback function. User can override the function if needed. */
void ux_device_err_callback(void * p_instance, void * p_data);
/* Header section starts for g_ux_device_class_cdc_acm0 */
#include "ux_api.h"
#include "ux_device_class_cdc_acm.h"
/* USBX CDC-ACM Instance Activate User Callback Function */
extern VOID ux_cdc_device0_instance_activate(VOID * cdc_instance);
/* USBX CDC-ACM Instance Deactivate User Callback Function */
extern VOID ux_cdc_device0_instance_deactivate(VOID * cdc_instance);
/* Header section ends for g_ux_device_class_cdc_acm0 */
#ifndef NX_INCLUDE
#define NX_INCLUDE
#include "nx_api.h"
#include "sf_el_nx_cfg.h"
#include "../src/framework/sf_el_nx/nx_renesas_synergy.h"
#endif

VOID nx_ether_driver_wrapper1(NX_IP_DRIVER *driver_req_ptr);
extern VOID (*g_sf_el_nx)(NX_IP_DRIVER * driver_req_ptr);
extern NX_PACKET_POOL g_packet_pool0;
void g_packet_pool0_err_callback(void * p_instance, void * p_data);
extern NX_IP g_ip0;
void g_ip0_err_callback(void * p_instance, void * p_data);
void g_common_init(void);
#ifdef __cplusplus
} /* extern "C" */
#endif
#endif /* COMMON_DATA_H_ */
