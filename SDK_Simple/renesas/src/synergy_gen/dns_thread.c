/* generated thread source file - do not edit */
#include "dns_thread.h"

TX_THREAD dns_thread;
void dns_thread_create(void);
static void dns_thread_func(ULONG thread_input);
/** Alignment requires using pragma for IAR. GCC is done through attribute. */
#if defined(__ICCARM__)
#pragma data_alignment = BSP_STACK_ALIGNMENT
#endif
static uint8_t dns_thread_stack[1024] BSP_PLACE_IN_SECTION(".stack.dns_thread") BSP_ALIGN_VARIABLE(BSP_STACK_ALIGNMENT);
#if defined(__ICCARM__)
#define g_dns0_err_callback_WEAK_ATTRIBUTE
#pragma weak g_dns0_err_callback  = g_dns0_err_callback_internal
#elif defined(__GNUC__)
#define g_dns0_err_callback_WEAK_ATTRIBUTE   __attribute__ ((weak, alias("g_dns0_err_callback_internal")))
#endif
void g_dns0_err_callback(void * p_instance, void * p_data)
g_dns0_err_callback_WEAK_ATTRIBUTE;
NX_DNS g_dns0;
/*******************************************************************************************************************//**
 * @brief      This is a weak example initialization error function.  It should be overridden by defining a user  function 
 *             with the prototype below.
 *             - void g_dns0_err_callback(void * p_instance, void * p_data)
 *
 * @param[in]  p_instance arguments used to identify which instance caused the error and p_data Callback arguments used to identify what error caused the callback.
 **********************************************************************************************************************/
void g_dns0_err_callback_internal(void * p_instance, void * p_data);
void g_dns0_err_callback_internal(void * p_instance, void * p_data)
{
    /** Suppress compiler warning for not using parameters. */
    SSP_PARAMETER_NOT_USED (p_instance);
    SSP_PARAMETER_NOT_USED (p_data);

    /** An error has occurred. Please check function arguments for more information. */
    BSP_CFG_HANDLE_UNRECOVERABLE_ERROR (0);
}
extern bool g_ssp_common_initialized;
extern uint32_t g_ssp_common_thread_count;
extern TX_SEMAPHORE g_ssp_common_initialized_semaphore;
void g_hal_init(void);

void dns_thread_create(void)
{
    /* Increment count so we will know the number of ISDE created threads. */
    g_ssp_common_thread_count++;

    /* Initialize each kernel object. */

    tx_thread_create (&dns_thread, (CHAR *) "DNS Thread", dns_thread_func, (ULONG) NULL, &dns_thread_stack, 1024, 1, 1,
                      1, TX_AUTO_START);
}

static void dns_thread_func(ULONG thread_input)
{
    /* Not currently using thread_input. */
    SSP_PARAMETER_NOT_USED (thread_input);

    /* First thread will take care of common initialization. */
    UINT err;
    err = tx_semaphore_get (&g_ssp_common_initialized_semaphore, TX_WAIT_FOREVER);

    while (TX_SUCCESS != err)
    {
        /* Check err, problem occurred. */
        BSP_CFG_HANDLE_UNRECOVERABLE_ERROR (0);
    }

    /* Only perform common initialization if this is the first thread to execute. */
    if (false == g_ssp_common_initialized)
    {
        /* Later threads will not run this code. */
        g_ssp_common_initialized = true;

        /* Perform common module initialization. */
        g_hal_init ();

        /* Now that common initialization is done, let other threads through. */
        /* First decrement by 1 since 1 thread has already come through. */
        g_ssp_common_thread_count--;
        while (g_ssp_common_thread_count > 0)
        {
            err = tx_semaphore_put (&g_ssp_common_initialized_semaphore);

            while (TX_SUCCESS != err)
            {
                /* Check err, problem occurred. */
                BSP_CFG_HANDLE_UNRECOVERABLE_ERROR (0);
            }

            g_ssp_common_thread_count--;
        }
    }

    /* Initialize each module instance. */
    UINT g_dns0_err;

    /* Create DNS Client. */
    g_dns0_err = nx_dns_create (&g_dns0, &g_ip0, (UCHAR *) "g_dns0 DNS Client");

    if (NX_SUCCESS != g_dns0_err)
    {
        g_dns0_err_callback ((void *) &g_dns0, &g_dns0_err);
    }

    /* Enter user code for this thread. */
    dns_thread_entry ();
}
