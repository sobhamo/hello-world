/* generated thread header file - do not edit */
#ifndef DNS_THREAD_H_
#define DNS_THREAD_H_
#include "bsp_api.h"
#include "tx_api.h"
#include "hal_data.h"
#ifdef __cplusplus 
extern "C" void dns_thread_entry(void);
#else 
extern void dns_thread_entry(void);
#endif
#include "nx_dns.h"
#ifdef __cplusplus
extern "C"
{
#endif
extern NX_DNS g_dns0;
void g_dns0_err_callback(void * p_instance, void * p_data);
#ifdef __cplusplus
} /* extern "C" */
#endif
#endif /* DNS_THREAD_H_ */
