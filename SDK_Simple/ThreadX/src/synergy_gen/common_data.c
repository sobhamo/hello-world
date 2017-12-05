/* generated common source file - do not edit */
#include "common_data.h"

/***********************************************************************************************************************
 * USB CDC-ACM Interface Descriptor for FS mode g_usb_interface_desc_cdcacm_0
 **********************************************************************************************************************/
#if defined(__GNUC__)
static volatile const unsigned char g_usb_interface_desc_cdcacm_0_full_speed[] BSP_PLACE_IN_SECTION(".usb_interface_desc_fs") BSP_ALIGN_VARIABLE(1)
#else /* __ICCARM__ */
#pragma section = ".usb_interface_desc_fs" 1
__root static const unsigned char g_usb_interface_desc_cdcacm_0_full_speed[] BSP_PLACE_IN_SECTION(".usb_interface_desc_fs")
#endif
=
{
    /****************************************************************
     * Interface Association Descriptor(IAD)                        *
     ****************************************************************/
    0x08, /* 0 bLength */
    UX_INTERFACE_ASSOCIATION_DESCRIPTOR_ITEM, /* 1 bDescriptorType */
    0x00, /* 2 bFirstInterface */
    0x02, /* 3 bInterfaceCount */
    0x02, /* 4 bFunctionClass : Communication */
    0x02, /* 5 bFunctionSubClass : Abstract Control Model */
    0x00, /* 6 bFunctionProtocol : Standard or enhanced AT Command set protocol */
    0x00, /* 7 iFunction : String descriptor index */
    /****************************************************************
     * Communication Class Interface Descriptor                     *
     ****************************************************************/
    0x09, /* 0 bLength */
    UX_INTERFACE_DESCRIPTOR_ITEM, /* 1 bDescriptorType */
    0x00, /* 2 bInterfaceNumber */
    0x00, /* 3 bAlternateSetting  : Alternate for SetInterface Request */
    0x01, /* 4 bNumEndpoints      : 1 Endpoint for Interface#1 */
    0x02, /* 5 bInterfaceClass    : Communications Interface Class(0x2) */
    0x02, /* 6 bInterfaceSubClass : Abstract Control Model(0x2) */
    0x01, /* 7 bInterfaceProtocol : Common AT command(0x01) */
    0x00, /* 8 iInterface Index */
    /****************************************************************
     * Header Functional Descriptor                                 *
     ****************************************************************/
    0x05, /* 0 bFunctionLength */
    0x24, /* 1 bDescriptorType : CS_INTERFACE(24h) */
    0x00, /* 2 bDescriptorSubtype : Header Functional Descriptor(0x0) */
    0x10, /* 3 bcdCDC Number  0x0110 == 1.10 */
    0x01, /* 4 bcdCDC */
    /****************************************************************
     * Abstract Control Management Functional Functional Descriptor *
     ****************************************************************/
    0x04, /* 0 bFunctionLength */
    0x24, /* 1 bDescriptorType : CS_INTERFACE(24h) */
    0x02, /* 2 bDescriptorSubtype : Abstract Control Management Functional Descriptor(0x2) */
    0x06, /* 3 bmCapabilities (Supports SendBreak, GetLineCoding, SetControlLineState, GetLineCoding) */
    /****************************************************************
     * Union Functional Descriptor                                  *
     ****************************************************************/
    0x05, /* 0 bFunctionLength */
    0x24, /* 1 bDescriptorType : CS_INTERFACE(24h) */
    0x06, /* 2 bDescriptorSubtype : Union Functional Descriptor(0x6) */
    0x00, /* 3 bMasterInterface */
    0x01, /* 4 bSubordinateInterface0 */
    /****************************************************************
     * Call Management Functional Descriptor                        *
     ****************************************************************/
    0x05, /* 0 bFunctionLength */
    0x24, /* 1 bDescriptorType */
    0x01, /* 2 bDescriptorSubtype : Call Management Functional Descriptor(0x1) */
    0x03, /* 3 bmCapabilities */
    0x01, /* 4 bDataInterface */
    /****************************************************************
     * CDC-ACM Endpoint descriptor (Interrupt) for Interface#0      *
     ****************************************************************/
    0x07, /* 0 bLength */
    UX_ENDPOINT_DESCRIPTOR_ITEM, /* 1 bDescriptorType */
    (UX_ENDPOINT_IN | 3), /* 2 bEndpointAddress */
    UX_INTERRUPT_ENDPOINT, /* 3 bmAttributes  */
    0x08, /* 4 wMaxPacketSize */
    0x00, /* 5 wMaxPacketSize */
    0x0F, /* 6 bInterval */
    /****************************************************************
     * CDC-ACM Data Class Interface Descriptor                      *
     ****************************************************************/
    0x09, /* 0 bLength */
    UX_INTERFACE_DESCRIPTOR_ITEM, /* 1 bDescriptorType */
    0x01, /* 2 bInterfaceNumber */
    0x00, /* 3 bAlternateSetting  : Alternate for SetInterface Request */
    0x02, /* 4 bNumEndpoints      : 2 Endpoints for Interface#0 */
    0x0A, /* 5 bInterfaceClass    : Data Interface Class(0xA) */
    0x00, /* 6 bInterfaceSubClass : Abstract Control Model */
    0x00, /* 7 bInterfaceProtocol : No class specific protocol required */
    0x00, /* 8 iInterface Index   : String descriptor index */
    /****************************************************************
     * CDC-ACM Endpoint Descriptor (Bulk-Out)  for Interface#1      *
     ****************************************************************/
    0x07, /* 0 bLength */
    UX_ENDPOINT_DESCRIPTOR_ITEM, /* 1 bDescriptorType */
    (UX_ENDPOINT_OUT | 1), /* 2 bEndpointAddress */
    UX_BULK_ENDPOINT, /* 3 bmAttributes  */
    0x40, /* 4 wMaxPacketSize */
    0x00, /* 5 wMaxPacketSize */
    0x00, /* 6 bInterval */
    /****************************************************************
     * CDC-ACM Endpoint Descriptor (Bulk-In) for Interface#1        *
     ****************************************************************/
    0x07, /* 0 bLength */
    UX_ENDPOINT_DESCRIPTOR_ITEM, /* 1 bDescriptorType */
    (UX_ENDPOINT_IN | 2), /* 2 bEndpointAddress */
    UX_BULK_ENDPOINT, /* 3 bmAttributes  */
    0x40, /* 4 wMaxPacketSize */
    0x00, /* 5 wMaxPacketSize */
    0x00, /* 6 bInterval */
};

#if defined(UX_DCD_SYNERY_USE_USBHS)
/***********************************************************************************************************************
 * USB CDC-ACM Interface Descriptor for HS mode g_usb_interface_desc_cdcacm_0
 **********************************************************************************************************************/
#if defined(__GNUC__)
static volatile const unsigned char g_usb_interface_desc_cdcacm_0_high_speed[] BSP_PLACE_IN_SECTION(".usb_interface_desc_hs") BSP_ALIGN_VARIABLE(1)
#else /* __ICCARM__ */
#pragma section = ".usb_interface_desc_hs" 1
__root static const unsigned char g_usb_interface_desc_cdcacm_0_high_speed[] BSP_PLACE_IN_SECTION(".usb_interface_desc_hs")
#endif
=
{
    /****************************************************************
     * Interface Association Descriptor(IAD)                        *
     ****************************************************************/
    0x08, /* 0 bLength */
    UX_INTERFACE_ASSOCIATION_DESCRIPTOR_ITEM, /* 1 bDescriptorType */
    0x00, /* 2 bFirstInterface */
    0x02, /* 3 bInterfaceCount */
    0x02, /* 4 bFunctionClass : Communication */
    0x02, /* 5 bFunctionSubClass : Abstract Control Model */
    0x00, /* 6 bFunctionProtocol : Standard or enhanced AT Command set protocol */
    0x00, /* 7 iFunction : String descriptor index */
    /****************************************************************
     * Communication Class Interface Descriptor                     *
     ****************************************************************/
    0x09, /* 0 bLength */
    UX_INTERFACE_DESCRIPTOR_ITEM, /* 1 bDescriptorType */
    0x00, /* 2 bInterfaceNumber */
    0x00, /* 3 bAlternateSetting  : Alternate for SetInterface Request */
    0x01, /* 4 bNumEndpoints      : 1 Endpoint for Interface#1 */
    0x02, /* 5 bInterfaceClass    : Communications Interface Class(0x2) */
    0x02, /* 6 bInterfaceSubClass : Abstract Control Model(0x2) */
    0x01, /* 7 bInterfaceProtocol : Common AT command(0x01) */
    0x00, /* 8 iInterface Index */
    /****************************************************************
     * Header Functional Descriptor                                 *
     ****************************************************************/
    0x05, /* 0 bFunctionLength */
    0x24, /* 1 bDescriptorType : CS_INTERFACE(24h) */
    0x00, /* 2 bDescriptorSubtype : Header Functional Descriptor(0x0) */
    0x10, /* 3 bcdCDC Number  0x0110 == 1.10 */
    0x01, /* 4 bcdCDC */
    /****************************************************************
     * Abstract Control Management Functional Functional Descriptor *
     ****************************************************************/
    0x04, /* 0 bFunctionLength */
    0x24, /* 1 bDescriptorType : CS_INTERFACE(24h) */
    0x02, /* 2 bDescriptorSubtype : Abstract Control Management Functional Descriptor(0x2) */
    0x06, /* 3 bmCapabilities (Supports SendBreak, GetLineCoding, SetControlLineState, GetLineCoding) */
    /****************************************************************
     * Union Functional Descriptor                                  *
     ****************************************************************/
    0x05, /* 0 bFunctionLength */
    0x24, /* 1 bDescriptorType : CS_INTERFACE(24h) */
    0x06, /* 2 bDescriptorSubtype : Union Functional Descriptor(0x6) */
    0x00, /* 3 bMasterInterface */
    0x01, /* 4 bSubordinateInterface0 */
    /****************************************************************
     * Call Management Functional Descriptor                        *
     ****************************************************************/
    0x05, /* 0 bFunctionLength */
    0x24, /* 1 bDescriptorType */
    0x01, /* 2 bDescriptorSubtype : Call Management Functional Descriptor(0x1) */
    0x03, /* 3 bmCapabilities */
    0x01, /* 4 bDataInterface */
    /****************************************************************
     * CDC-ACM Endpoint descriptor (Interrupt) for Interface#0      *
     ****************************************************************/
    0x07, /* 0 bLength */
    UX_ENDPOINT_DESCRIPTOR_ITEM, /* 1 bDescriptorType */
    (UX_ENDPOINT_IN | 3), /* 2 bEndpointAddress */
    UX_INTERRUPT_ENDPOINT, /* 3 bmAttributes  */
    0x08, /* 4 wMaxPacketSize */
    0x00, /* 5 wMaxPacketSize */
    0x0F, /* 6 bInterval */
    /****************************************************************
     * CDC-ACM Data Class Interface Descriptor                      *
     ****************************************************************/
    0x09, /* 0 bLength */
    UX_INTERFACE_DESCRIPTOR_ITEM, /* 1 bDescriptorType */
    0x01, /* 2 bInterfaceNumber */
    0x00, /* 3 bAlternateSetting  : Alternate for SetInterface Request */
    0x02, /* 4 bNumEndpoints      : 2 Endpoints for Interface#0 */
    0x0A, /* 5 bInterfaceClass    : Data Interface Class(0xA) */
    0x00, /* 6 bInterfaceSubClass : Abstract Control Model */
    0x00, /* 7 bInterfaceProtocol : No class specific protocol required */
    0x00, /* 8 iInterface Index   : String descriptor index */
    /****************************************************************
     * CDC-ACM Endpoint Descriptor (Bulk-Out)  for Interface#1      *
     ****************************************************************/
    0x07, /* 0 bLength */
    UX_ENDPOINT_DESCRIPTOR_ITEM, /* 1 bDescriptorType */
    (UX_ENDPOINT_OUT | 1), /* 2 bEndpointAddress */
    UX_BULK_ENDPOINT, /* 3 bmAttributes  */
    0x00, /* 4 wMaxPacketSize */
    0x02, /* 5 wMaxPacketSize */
    0x00, /* 6 bInterval */
    /****************************************************************
     * CDC-ACM Endpoint Descriptor (Bulk-In) for Interface#1        *
     ****************************************************************/
    0x07, /* 0 bLength */
    UX_ENDPOINT_DESCRIPTOR_ITEM, /* 1 bDescriptorType */
    (UX_ENDPOINT_IN | 2), /* 2 bEndpointAddress */
    UX_BULK_ENDPOINT, /* 3 bmAttributes  */
    0x00, /* 4 wMaxPacketSize */
    0x02, /* 5 wMaxPacketSize */
    0x00, /* 6 bInterval */
};
#endif

/* Size of this USB Interface Descriptor */
#define USB_IFD_SIZE_0x00       (sizeof(g_usb_interface_desc_cdcacm_0_full_speed))
/* Number of Interface this USB Interface Descriptor has */
#define USB_IFD_NUM_0x00        (2)
#ifndef USB_IFD_NUM_0
#define USB_IFD_NUM_0  USB_IFD_NUM_0x00
#define USB_IFD_SIZE_0 USB_IFD_SIZE_0x00
#else
#ifndef USB_IFD_NUM_1
#define USB_IFD_NUM_1  USB_IFD_NUM_0x00
#define USB_IFD_SIZE_1 USB_IFD_SIZE_0x00
#else
#ifndef USB_IFD_NUM_2
#define USB_IFD_NUM_2  USB_IFD_NUM_0x00
#define USB_IFD_SIZE_2 USB_IFD_SIZE_0x00
#else
#ifndef USB_IFD_NUM_3
#define USB_IFD_NUM_3  USB_IFD_NUM_0x00
#define USB_IFD_SIZE_3 USB_IFD_SIZE_0x00
#else
#ifndef USB_IFD_NUM_4
#define USB_IFD_NUM_4  USB_IFD_NUM_0x00
#define USB_IFD_SIZE_4 USB_IFD_SIZE_0x00
#else
#ifndef USB_IFD_NUM_5
#define USB_IFD_NUM_5  USB_IFD_NUM_0x00
#define USB_IFD_SIZE_5 USB_IFD_SIZE_0x00
#else
#ifndef USB_IFD_NUM_6
#define USB_IFD_NUM_6  USB_IFD_NUM_0x00
#define USB_IFD_SIZE_6 USB_IFD_SIZE_0x00
#else
#ifndef USB_IFD_NUM_7
#define USB_IFD_NUM_7  USB_IFD_NUM_0x00
#define USB_IFD_SIZE_7 USB_IFD_SIZE_0x00
#else
#ifndef USB_IFD_NUM_8
#define USB_IFD_NUM_8  USB_IFD_NUM_0x00
#define USB_IFD_SIZE_8 USB_IFD_SIZE_0x00
#else
#ifndef USB_IFD_NUM_9
#define USB_IFD_NUM_9  USB_IFD_NUM_0x00
#define USB_IFD_SIZE_9 USB_IFD_SIZE_0x00
#endif 
#endif
#endif
#endif
#endif
#endif
#endif
#endif
#endif
#endif
#if defined(__ICCARM__)
#define ux_v2_err_callback_WEAK_ATTRIBUTE
#pragma weak ux_v2_err_callback  = ux_v2_err_callback_internal
#elif defined(__GNUC__)
#define ux_v2_err_callback_WEAK_ATTRIBUTE   __attribute__ ((weak, alias("ux_v2_err_callback_internal")))
#endif
void ux_v2_err_callback(void * p_instance, void * p_data)
ux_v2_err_callback_WEAK_ATTRIBUTE;
/* Static memory pool allocation used by USBX system. */
static CHAR g_ux_pool_memory[18432];
/*******************************************************************************************************************//**
 * @brief      This is a weak example initialization error function.  It should be overridden by defining a user  function 
 *             with the prototype below.
 *             - void ux_v2_err_callback(void * p_instance, void * p_data)
 *
 * @param[in]  p_instance arguments used to identify which instance caused the error and p_data Callback arguments used to identify what error caused the callback.
 **********************************************************************************************************************/
void ux_v2_err_callback_internal(void * p_instance, void * p_data);
void ux_v2_err_callback_internal(void * p_instance, void * p_data)
{
    /** Suppress compiler warning for not using parameters. */
    SSP_PARAMETER_NOT_USED (p_instance);
    SSP_PARAMETER_NOT_USED (p_data);

    /** An error has occurred. Please check function arguments for more information. */
    BSP_CFG_HANDLE_UNRECOVERABLE_ERROR (0);
}

#ifdef UX_HOST_INITIALIZE
/* Function prototype for the function to notify a USB event from the USBX Host system. */
static UINT ux_system_host_change_function(ULONG event, UX_HOST_CLASS * host_class, VOID * instance);

#ifdef USB_HOST_STORAGE_CLASS_REGISTER

/* A semaphore for synchronizing to the USB media storage mount timing. */
static TX_SEMAPHORE ux_host_storage_semaphore_insertion;
static TX_SEMAPHORE ux_host_storage_semaphore_media_init_done;

/* Temporary storage place for the pointer to a USBX Host Mass Storage Class Instance. */
static UX_HOST_CLASS_STORAGE * g_ux_new_host_storage_instance;

/* Function prototype for the function to register the USBX Host Class Mass Storage. */
static void ux_host_stack_class_register_storage(void);

/* Function prototype for the function to notify changes happened to the USBX Host Class Mass Storage. */
static UINT ux_system_host_storage_change_function(ULONG event, VOID * instance);

/*******************************************************************************************************************//**
 * @brief      This is the function to register the USBX Host Class Mass Storage.
 **********************************************************************************************************************/
static void ux_host_stack_class_register_storage(void)
{
    UINT status;
    status = ux_host_stack_class_register(_ux_system_host_class_storage_name, ux_host_class_storage_entry);
    if (UX_SUCCESS != status)
    {
        ux_v2_err_callback(NULL, &status);
    }

    /* Create semaphores used for the USB Mass Storage Media Initialization. */
    status = tx_semaphore_create(&ux_host_storage_semaphore_insertion, "ux_host_storage_semaphore_insertion", 0);
    if (TX_SUCCESS != status)
    {
        ux_v2_err_callback(NULL, &status);
    }

    status = tx_semaphore_create(&ux_host_storage_semaphore_media_init_done, "ux_host_storage_semaphore_media_init_done", 1);
    if (TX_SUCCESS != status)
    {
        ux_v2_err_callback(NULL, &status);
    }
}

/*******************************************************************************************************************//**
 * @brief      This is the function to notify changes happened to the USBX Host Class Mass Storage.
 * @param[in]  event      Event code from happened to the USBX Host system.
 * @param[in]  instance   Pointer to a USBX Host class instance, which occurs a event.
 * @retval     USBX error code
 **********************************************************************************************************************/
static UINT ux_system_host_storage_change_function(ULONG event, VOID * instance)
{
    if (UX_DEVICE_INSERTION == event) /* Check if there is a device insertion. */
    {
        /* Get the semaphore for a USB Mass Storage Media initialization. */
        tx_semaphore_get (&ux_host_storage_semaphore_media_init_done, TX_WAIT_FOREVER);

        g_ux_new_host_storage_instance = instance;

        /* Put the semaphore for a USBX Mass Storage Media insertion. */
        tx_semaphore_put (&ux_host_storage_semaphore_insertion);
    }
    else if (UX_DEVICE_REMOVAL == event) /* Check if there is a device removal. */
    {
        g_ux_new_host_storage_instance = NULL;
    }
    return UX_SUCCESS;
}

/*******************************************************************************************************************//**
 * @brief      This is the function to get the FileX Media Control Block for a USB Mass Storage device.
 * @param[in]      new_instance     Pointer to a USBX Host Mass Storage Class instance.
 * @param[in/out]  p_storage_media  Pointer of the pointer to save an instance of the USBX Host Mass Storage Media.
 * @param[in/out]  p_fx_media       Pointer of the pointer to save an instance of FileX Media Control Block.
 * @retval     UX_HOST_CLASS_INSTANCE_UNKNOWN  The instance provided was not for a valid Mass Storage device.
 **********************************************************************************************************************/
UINT ux_system_host_storage_fx_media_get(UX_HOST_CLASS_STORAGE * instance, UX_HOST_CLASS_STORAGE_MEDIA ** p_storage_media,
        FX_MEDIA ** p_fx_media)
{
    UINT error = UX_SUCCESS;
    UX_HOST_CLASS * p_host_class;
    UX_HOST_CLASS_STORAGE_MEDIA * p_storage_media_local;
    FX_MEDIA * p_fx_media_local;
    int index;

    /** Get the USBX Host Mass Storage Class. */
    ux_host_stack_class_get(_ux_system_host_class_storage_name, &p_host_class);

    /** Get the pointer to a media attached to the class container for USB Host Mass Storage. */
    p_storage_media_local = (UX_HOST_CLASS_STORAGE_MEDIA *) p_host_class->ux_host_class_media;

    /** Get the pointer to a FileX Media Control Block. */
    for (index = 0; index < UX_HOST_CLASS_STORAGE_MAX_MEDIA; index++)
    {
        p_fx_media_local = &p_storage_media_local->ux_host_class_storage_media;
        if (p_fx_media_local->fx_media_driver_info != instance)
        {
            /* None of FileX Media Control Blocks matched to this USBX Host Mass Storage Instance. */
            p_storage_media_local++;
        }
        else
        {
            /* Found a FileX Media Control Block used for this USBX Host Mass Storage Instance. */
            break;
        }
    }
    if (UX_HOST_CLASS_STORAGE_MAX_MEDIA == index)
    {
        error = UX_HOST_CLASS_INSTANCE_UNKNOWN;
    }
    else
    {
        *p_storage_media = p_storage_media_local;
        *p_fx_media = p_fx_media_local;
    }

    /** Resume the USBX internal thread since the initialization of a USB Mass Storage Device done. */
    tx_semaphore_put (&ux_host_storage_semaphore_media_init_done);

    return error;
}
#endif /* USB_HOST_STORAGE_CLASS_REGISTER */

/*******************************************************************************************************************//**
 * @brief      This is the function to notify a USB event from the USBX Host system.
 * @param[in]  event     Event code from happened to the USBX Host system.
 * @param[in]  usb_class Pointer to a USBX Host class, which occurs a event.
 * @param[in]  instance  Pointer to a USBX Host class instance, which occurs a event.
 * @retval     USBX error code
 **********************************************************************************************************************/
static UINT ux_system_host_change_function(ULONG event, UX_HOST_CLASS * host_class, VOID * instance)
{
    UINT status = UX_SUCCESS;
    SSP_PARAMETER_NOT_USED (event);
    SSP_PARAMETER_NOT_USED (host_class);
    SSP_PARAMETER_NOT_USED (instance);

#if !defined(NULL)
    /* Call user function back for USBX Host Class event notification. */
    status = NULL(event, host_class, instance);
    if (UX_SUCCESS != status)
    {
        return status;
    }
#endif

#ifdef USB_HOST_STORAGE_CLASS_REGISTER
    /* Check the class container if it is for a USBX Host Mass Storage class. */
    if (UX_SUCCESS == _ux_utility_memory_compare(_ux_system_host_class_storage_name, host_class, _ux_utility_string_length_get(_ux_system_host_class_storage_name)))
    {
        status = ux_system_host_storage_change_function(event, instance);
    }
#endif
    return status;
}
#endif
#if defined(__ICCARM__)
#define g_sf_el_ux_dcd_fs_0_err_callback_WEAK_ATTRIBUTE
#pragma weak g_sf_el_ux_dcd_fs_0_err_callback  = g_sf_el_ux_dcd_fs_0_err_callback_internal
#elif defined(__GNUC__)
#define g_sf_el_ux_dcd_fs_0_err_callback_WEAK_ATTRIBUTE   __attribute__ ((weak, alias("g_sf_el_ux_dcd_fs_0_err_callback_internal")))
#endif
void g_sf_el_ux_dcd_fs_0_err_callback(void * p_instance, void * p_data)
g_sf_el_ux_dcd_fs_0_err_callback_WEAK_ATTRIBUTE;
#if (SF_EL_UX_CFG_FS_IRQ_IPL != BSP_IRQ_DISABLED)
/* USBFS ISR vector registering. */
#if !defined(SSP_SUPPRESS_ISR_g_sf_el_ux_dcd_fs_0) && !defined(SSP_SUPPRESS_ISR_USB)
SSP_VECTOR_DEFINE_UNIT(usbfs_int_isr, USB, FS, INT, 0);
#endif
#endif

/* Build-time constraint for non-supported configuration. */
#if (BSP_CFG_MCU_PART_SERIES == 0x1)
#warning "This MCU part does not have a USBHS port. Check sf_el_ux module configuration."
#endif

/* Prototype function for USBX DCD Initializer. */
static void ux_dcd_initialize(void);

#undef SYNERGY_NOT_DEFINED
#define SYNERGY_NOT_DEFINED (1)
/*******************************************************************************************************************//**
 * @brief      This is a weak example initialization error function.  It should be overridden by defining a user  function 
 *             with the prototype below.
 *             - void g_sf_el_ux_dcd_fs_0_err_callback(void * p_instance, void * p_data)
 *
 * @param[in]  p_instance arguments used to identify which instance caused the error and p_data Callback arguments used to identify what error caused the callback.
 **********************************************************************************************************************/
void g_sf_el_ux_dcd_fs_0_err_callback_internal(void * p_instance, void * p_data);
void g_sf_el_ux_dcd_fs_0_err_callback_internal(void * p_instance, void * p_data)
{
    /** Suppress compiler warning for not using parameters. */
    SSP_PARAMETER_NOT_USED (p_instance);
    SSP_PARAMETER_NOT_USED (p_data);

    /** An error has occurred. Please check function arguments for more information. */
    BSP_CFG_HANDLE_UNRECOVERABLE_ERROR (0);
}
#if ((SYNERGY_NOT_DEFINED != SYNERGY_NOT_DEFINED) && (SYNERGY_NOT_DEFINED != SYNERGY_NOT_DEFINED))
/***********************************************************************************************************************
 * The definition of wrapper interface for USBX Synergy Port DCD Driver to get a transfer module instance.
 **********************************************************************************************************************/
static UINT g_sf_el_ux_dcd_fs_0_initialize_transfer_support(ULONG dcd_io)
{
    UX_DCD_SYNERGY_TRANSFER dcd_transfer;
    dcd_transfer.ux_synergy_transfer_tx = (transfer_instance_t *)&SYNERGY_NOT_DEFINED;
    dcd_transfer.ux_synergy_transfer_rx = (transfer_instance_t *)&SYNERGY_NOT_DEFINED;
    return (UINT)ux_dcd_synergy_initialize_transfer_support(dcd_io, (UX_DCD_SYNERGY_TRANSFER *)&dcd_transfer);
} /* End of function g_sf_el_ux_dcd_fs_0_initialize_transfer_support() */
#endif

/***********************************************************************************************************************
 * Initializes USBX Device Controller Driver.
 **********************************************************************************************************************/
void ux_dcd_initialize(void)
{
    UINT status;
    /* Initializes the USB device controller, enabling DMA transfer if transfer module instances are given. */
#if ((SYNERGY_NOT_DEFINED == SYNERGY_NOT_DEFINED) || (SYNERGY_NOT_DEFINED == SYNERGY_NOT_DEFINED))
    status = (UINT) ux_dcd_synergy_initialize (R_USBFS_BASE);
#else
    status = g_sf_el_ux_dcd_fs_0_initialize_transfer_support(R_USBFS_BASE);
#endif
#undef SYNERGY_NOT_DEFINED
    if (UX_SUCCESS != status)
    {
        g_sf_el_ux_dcd_fs_0_err_callback (NULL, &status);
    }
} /* End of function ux_dcd_initialize() */
#if defined(__ICCARM__)
#define ux_device_err_callback_WEAK_ATTRIBUTE
#pragma weak ux_device_err_callback  = ux_device_err_callback_internal
#elif defined(__GNUC__)
#define ux_device_err_callback_WEAK_ATTRIBUTE   __attribute__ ((weak, alias("ux_device_err_callback_internal")))
#endif
void ux_device_err_callback(void * p_instance, void * p_data)
ux_device_err_callback_WEAK_ATTRIBUTE;
/* Definition for the special linker section for USB */
#if defined(__GNUC__)
extern uint32_t __usb_dev_descriptor_start_fs;
extern uint32_t __usb_descriptor_end_fs;
#if defined(UX_DCD_SYNERY_USE_USBHS)
extern uint32_t __usb_dev_descriptor_start_hs;
extern uint32_t __usb_descriptor_end_hs;
#endif
#endif

/* Counter to calculate number of Interfaces */
static uint8_t g_usbx_number_of_interface_count = 0;

/***********************************************************************************************************************
 * USB Device Descriptor for FS mode
 **********************************************************************************************************************/
#if defined(__GNUC__)
static volatile const unsigned char device_framework_full_speed[] BSP_PLACE_IN_SECTION(".usb_device_desc_fs") BSP_ALIGN_VARIABLE(1)
#else /* __ICCARM__ */
#pragma section = ".usb_device_desc_fs" 1
__root static const unsigned char device_framework_full_speed[] BSP_PLACE_IN_SECTION(".usb_device_desc_fs")
#endif
=
{
    0x12, /* 0 bLength */
    UX_DEVICE_DESCRIPTOR_ITEM, /* 1 bDescriptorType */
    0x00, /* 2 bcdUSB BCD(2.0) */
    0x02, /* 3 bcdUSB */
    0x02, /* 4 bDeviceClass    : Device Class */
#if (0x02 == 0x00) || (0x02 == 0x02) || (0x02 == 0x03) || (0x02 == 0x08)
    0x00, /* 5 bDeviceSubClass : none */
    0x00, /* 6 bDeviceProtocol : none */
#elif (0x02 == 0xEF)
    0x02, /* 5 bDeviceSubClass : Common Class(0x02) */
    0x01, /* 6 bDeviceProtocol :Interface Association Descriptor(IAD) */
#elif (0x02 == 0xFF)
    0x00, /* 5 bDeviceSubClass : none */
    0x00, /* 6 bDeviceProtocol : none */
#endif
    0x40, /* 7 bMaxPacketSize0 */
    (uint8_t)(0x045B), /* 8 idVendor */
    (uint8_t)(0x045B >> 8), /* 9 idVendor */
    (uint8_t)(0x0000), /* 10 idProduct */
    (uint8_t)(0x0000 >> 8), /* 11 idProduct */
    (uint8_t)(0x0000), /* 12 bcdDevice */
    (uint8_t)(0x0000 >> 8), /* 13 bcdDevice */
    0x00, /* 14 iManufacturer */
    0x00, /* 15 iProduct */
    0x00, /* 16 iSerialNumber */
    0x01, /* 17 bNumConfigurations */
};

#if defined(UX_DCD_SYNERY_USE_USBHS)
/***********************************************************************************************************************
 * USB Device Descriptor for HS Mode
 **********************************************************************************************************************/
#if defined(__GNUC__)
static volatile const unsigned char device_framework_high_speed[] BSP_PLACE_IN_SECTION(".usb_device_desc_hs") BSP_ALIGN_VARIABLE(1)
#else /* __ICCARM__ */
#pragma section = ".usb_device_desc_hs" 1
__root static const unsigned char device_framework_high_speed[] BSP_PLACE_IN_SECTION(".usb_device_desc_hs")
#endif
=
{
    0x12, /* 0 bLength */
    UX_DEVICE_DESCRIPTOR_ITEM, /* 1 bDescriptorType */
    0x00, /* 2 bcdUSB BCD(2.0) */
    0x02, /* 3 bcdUSB */
    0x02, /* 4 bDeviceClass    : Device Class */
#if (0x02 == 0x00) || (0x02 == 0x02) || (0x02 == 0x03) || (0x02 == 0x08)
    0x00, /* 5 bDeviceSubClass : none */
    0x00, /* 6 bDeviceProtocol : none */
#elif (0x02 == 0xEF) /* Composite device */
    0x02, /* 5 bDeviceSubClass : Common Class(0x02) */
    0x01, /* 6 bDeviceProtocol :Interface Association Descriptor(IAD) */
#elif (0x02 == 0xFF) /* Vendor specific device */
    0x00, /* 5 bDeviceSubClass : none */
    0x00, /* 6 bDeviceProtocol : none */
#endif
    0x40, /* 7 bMaxPacketSize0 */
    (uint8_t)(0x045B), /* 8 idVendor */
    (uint8_t)(0x045B >> 8), /* 9 idVendor */
    (uint8_t)(0x0000), /* 10 idProduct */
    (uint8_t)(0x0000 >> 8), /* 11 idProduct */
    (uint8_t)(0x0000), /* 12 bcdDevice */
    (uint8_t)(0x0000 >> 8), /* 13 bcdDevice */
    0x00, /* 14 iManufacturer */
    0x00, /* 15 iProduct */
    0x00, /* 16 iSerialNumber */
    0x01, /* 17 bNumConfigurations */
    /****************************************************************
     * Device qualifier descriptor                                  *
     ****************************************************************/
    0x0a, /* 0 bLength */
    UX_DEVICE_QUALIFIER_DESCRIPTOR_ITEM, /* 1 bDescriptorType */
    0x00, /* 2 bcdUSB BCD(2.0) */
    0x02, /* 3 bcdUSB */
    0x02, /* 4 bDeviceClass    : Device Class */
#if (0x02 == 0x02) || (0x02 == 0x03) || (0x02 == 0x08)
    0x00, /* 5 bDeviceSubClass : none */
    0x00, /* 6 bDeviceProtocol : none */
#elif (0x02 == 0xEF)
    0x02, /* 5 bDeviceSubClass : Common Class(0x02) */
    0x00, /* 6 bDeviceProtocol : none */
#elif (0x02 == 0xFF)
    0x00, /* 5 bDeviceSubClass : none */
    0x00, /* 6 bDeviceProtocol : none */
#endif
    0x40, /* 7 bMaxPacketSize0 */
    0x01, /* 8 bNumConfigs : 1 */
    0x00, /* 9 Reserve, fixed to 0 */
};
#endif

/***********************************************************************************************************************
 * Configuration Value Auto-calculation for USB Configuration Descriptor
 **********************************************************************************************************************/
#ifndef USB_IFD_NUM_0
#define USB_IFD_NUM_0  (0)
#define USB_IFD_SIZE_0 (0)
#endif
#ifndef USB_IFD_NUM_1
#define USB_IFD_NUM_1  (0)
#define USB_IFD_SIZE_1 (0)
#endif
#ifndef USB_IFD_NUM_2
#define USB_IFD_NUM_2  (0)
#define USB_IFD_SIZE_2 (0)
#endif
#ifndef USB_IFD_NUM_3
#define USB_IFD_NUM_3  (0)
#define USB_IFD_SIZE_3 (0)
#endif
#ifndef USB_IFD_NUM_4
#define USB_IFD_NUM_4  (0)
#define USB_IFD_SIZE_4 (0)
#endif
#ifndef USB_IFD_NUM_5
#define USB_IFD_NUM_5  (0)
#define USB_IFD_SIZE_5 (0)
#endif
#ifndef USB_IFD_NUM_6
#define USB_IFD_NUM_6  (0)
#define USB_IFD_SIZE_6 (0)
#endif
#ifndef USB_IFD_NUM_7
#define USB_IFD_NUM_7  (0)
#define USB_IFD_SIZE_7 (0)
#endif
#ifndef USB_IFD_NUM_8
#define USB_IFD_NUM_8  (0)
#define USB_IFD_SIZE_8 (0)
#endif
#ifndef USB_IFD_NUM_9
#define USB_IFD_NUM_9  (0)
#define USB_IFD_SIZE_9 (0)
#endif

#define USB_NUMBER_OF_INTERFACE USB_IFD_NUM_0 + USB_IFD_NUM_1 + USB_IFD_NUM_2 + USB_IFD_NUM_3 + USB_IFD_NUM_4 + USB_IFD_NUM_5 + USB_IFD_NUM_6 + USB_IFD_NUM_7 + USB_IFD_NUM_8 + USB_IFD_NUM_9
#define USB_TOTAL_LENGTH        USB_IFD_SIZE_0 + USB_IFD_SIZE_1 + USB_IFD_SIZE_2 + USB_IFD_SIZE_3 + USB_IFD_SIZE_4 + USB_IFD_SIZE_5 + USB_IFD_SIZE_6 + USB_IFD_SIZE_7 + USB_IFD_SIZE_8 + USB_IFD_SIZE_9

#if (0 != 0x00)
#define USB_CFG_DESCRIPTOR_bNumInterfaces     (0x00)
#else
#define USB_CFG_DESCRIPTOR_bNumInterfaces     (USB_NUMBER_OF_INTERFACE)
#endif
#if (0 != 0x00)
#define USB_CFG_DESCRIPTOR_wTotalLength       (0x00)
#else
#define USB_CFG_DESCRIPTOR_wTotalLength       (0x09 + USB_TOTAL_LENGTH)
#endif

/***********************************************************************************************************************
 * Consolidated USB Device Descriptor Framework on RAM for IAR build
 **********************************************************************************************************************/
#ifdef __ICCARM__
/* Memory area to complete USB device Descriptor Framework. */
static uint8_t device_framework_full_speed_ram[0x12 + USB_CFG_DESCRIPTOR_wTotalLength];
#if defined(UX_DCD_SYNERY_USE_USBHS)
static uint8_t device_framework_high_speed_ram[0x12 + 0x0a + USB_CFG_DESCRIPTOR_wTotalLength];
#endif
#endif

/***********************************************************************************************************************
 * USB Configuration Descriptor for FS mode
 **********************************************************************************************************************/
#if defined(__GNUC__)
static volatile const unsigned char ux_cfg_descriptor_full_speed[] BSP_PLACE_IN_SECTION(".usb_config_desc_fs") BSP_ALIGN_VARIABLE(1)
#else /* __ICCARM__ */
#pragma section = ".usb_config_desc_fs" 1
__root static const unsigned char ux_cfg_descriptor_full_speed[] BSP_PLACE_IN_SECTION(".usb_config_desc_fs")
#endif
=
{
    0x09, /* 0 bLength */
    UX_CONFIGURATION_DESCRIPTOR_ITEM, /* 1 bDescriptorType */
    (uint8_t)(USB_CFG_DESCRIPTOR_wTotalLength), /* 2 wTotalLength : This will be calculated at run-time. */
    (uint8_t)(USB_CFG_DESCRIPTOR_wTotalLength >> 8), /* 3 wTotalLength : This will be calculated at run-time. */
    (uint8_t)(USB_CFG_DESCRIPTOR_bNumInterfaces), /* 4 bNumInterfaces */
    0x01, /* 5 bConfigurationValue : Fixed to 1 since only one configuration is supported. */
    0x00, /* 6 iConfiguration */
    0x80 | (1 << 6) | (0 << 5), /* 7 bmAttributes */
    50, /* 8 bMaxPower */
};

#if defined(UX_DCD_SYNERY_USE_USBHS)
/***********************************************************************************************************************
 * USB Configuration Descriptor for HS mode
 **********************************************************************************************************************/
#if defined(__GNUC__)
static volatile const unsigned char ux_cfg_descriptor_high_speed[] BSP_PLACE_IN_SECTION(".usb_config_desc_hs") BSP_ALIGN_VARIABLE(1)
#else /* __ICCARM__ */
#pragma section = ".usb_config_desc_hs" 1
__root static const unsigned char ux_cfg_descriptor_high_speed[] BSP_PLACE_IN_SECTION(".usb_config_desc_hs")
#endif
=
{
    0x09, /* 0 bLength */
    UX_CONFIGURATION_DESCRIPTOR_ITEM, /* 1 bDescriptorType */
    (uint8_t)(USB_CFG_DESCRIPTOR_wTotalLength), /* 2 wTotalLength : This will be calculated at run-time. */
    (uint8_t)(USB_CFG_DESCRIPTOR_wTotalLength >> 8), /* 3 wTotalLength : This will be calculated at run-time. */
    (uint8_t)(USB_CFG_DESCRIPTOR_bNumInterfaces), /* 4 bNumInterfaces */
    0x01, /* 5 bConfigurationValue : Fixed to 1 since only one configuration is supported. */
    0x00, /* 6 iConfiguration */
    0x80 | (1 << 6) | (0 << 5), /* 7 bmAttributes */
    50, /* 8 bMaxPower */
};
#endif

/***********************************************************************************************************************
 * USB Language Framework (Default setting for US English)
 **********************************************************************************************************************/
#if(0 == 0) /* Create USB Language Framework with default setting if not defined by user. */
static volatile const UCHAR language_id_framework_default[] =
{ (uint8_t) (0x0409), /* Supported Language Code */
  (uint8_t) (0x0409 >> 8) /* US English as the default */
};
#endif

/***********************************************************************************************************************
 * Function to get size of USB String Descriptor
 **********************************************************************************************************************/
#if (0)
extern const UCHAR NULL[];
static ULONG ux_device_string_descriptor_size_get(void)
{
    ULONG size = 0;
    UCHAR * ptr = (UCHAR *)NULL;
    if(NULL != ptr)
    {
        for(INT i = 0; i < 0; i++)
        {
            ptr = ptr + 3; /* bLength at byte offset 3 */
            /* Counts bLength + Language code(2bytes) + bLength(1byte) */
            size = size + *ptr + 4;
            ptr = ptr + (*ptr) + 1;
        }
    }
    return size;
}
#endif

#if (0)
extern const UCHAR NULL[];
#endif
/*******************************************************************************************************************//**
 * @brief      This is a weak example initialization error function.  It should be overridden by defining a user  function 
 *             with the prototype below.
 *             - void ux_device_err_callback(void * p_instance, void * p_data)
 *
 * @param[in]  p_instance arguments used to identify which instance caused the error and p_data Callback arguments used to identify what error caused the callback.
 **********************************************************************************************************************/
void ux_device_err_callback_internal(void * p_instance, void * p_data);
void ux_device_err_callback_internal(void * p_instance, void * p_data)
{
    /** Suppress compiler warning for not using parameters. */
    SSP_PARAMETER_NOT_USED (p_instance);
    SSP_PARAMETER_NOT_USED (p_data);

    /** An error has occurred. Please check function arguments for more information. */
    BSP_CFG_HANDLE_UNRECOVERABLE_ERROR (0);
}
/***********************************************************************************************************************
 * Function prototypes for USBX CDC-ACM
 **********************************************************************************************************************/
static void g_ux_device_class_cdc_acm0_setup(void);

/***********************************************************************************************************************
 * USBX CDC-ACM Parameter Setup Function.
 **********************************************************************************************************************/
static UX_SLAVE_CLASS_CDC_ACM_PARAMETER g_ux_device_class_cdc_acm0_parameter;
void g_ux_device_class_cdc_acm0_setup(void)
{
    g_ux_device_class_cdc_acm0_parameter.ux_slave_class_cdc_acm_instance_activate = ux_cdc_device0_instance_activate;
    g_ux_device_class_cdc_acm0_parameter.ux_slave_class_cdc_acm_instance_deactivate =
            ux_cdc_device0_instance_deactivate;
    /* Initializes the device cdc class. */
    ux_device_stack_class_register (_ux_system_slave_class_cdc_acm_name, ux_device_class_cdc_acm_entry, 1, 0x00,
                                    (VOID *) &g_ux_device_class_cdc_acm0_parameter);

    /* Counts up the number of Interfaces. */
    g_usbx_number_of_interface_count = (uint8_t) (g_usbx_number_of_interface_count + 2);
}
#if SF_EL_NX_CFG_IRQ_IPL != BSP_IRQ_DISABLED
#if !defined(SSP_SUPPRESS_ISR_g_sf_el_nx) && !defined(SSP_SUPPRESS_ISR_EDMAC1)
SSP_VECTOR_DEFINE_CHAN(edmac_eint_isr, EDMAC, EINT, 1);
#endif
#endif

#ifndef NULL                                                                                                                                            
void NULL(nx_mac_address_t *p_mac_config);
#endif

extern void nx_ether_driver_support_mac_config_eth1(NX_IP_DRIVER *driver_req_ptr,
        void (*p_callback)(nx_mac_address_t *));
/*******************************************************************************************************************//**
 * The definition of wrapper interface for NETX Synergy  Driver to support runtime
 * MAC configuration.
 **********************************************************************************************************************/
void nx_ether_driver_wrapper1(NX_IP_DRIVER *driver_req_ptr)
{
    nx_ether_driver_support_mac_config_eth1 (driver_req_ptr, NULL);
}

/** Prototype for the driver entry function in sf_el_nx. */
VOID nx_ether_driver_eth1(NX_IP_DRIVER *driver_req_ptr);
/** Make user given name point to correct driver entry point. */
VOID (*g_sf_el_nx)(NX_IP_DRIVER * driver_req_ptr) = nx_ether_driver_wrapper1;
#if defined(__ICCARM__)
#define g_packet_pool0_err_callback_WEAK_ATTRIBUTE
#pragma weak g_packet_pool0_err_callback  = g_packet_pool0_err_callback_internal
#elif defined(__GNUC__)
#define g_packet_pool0_err_callback_WEAK_ATTRIBUTE   __attribute__ ((weak, alias("g_packet_pool0_err_callback_internal")))
#endif
void g_packet_pool0_err_callback(void * p_instance, void * p_data)
g_packet_pool0_err_callback_WEAK_ATTRIBUTE;
NX_PACKET_POOL g_packet_pool0;
uint8_t g_packet_pool0_pool_memory[(16 * 4096)];
/*******************************************************************************************************************//**
 * @brief      This is a weak example initialization error function.  It should be overridden by defining a user  function 
 *             with the prototype below.
 *             - void g_packet_pool0_err_callback(void * p_instance, void * p_data)
 *
 * @param[in]  p_instance arguments used to identify which instance caused the error and p_data Callback arguments used to identify what error caused the callback.
 **********************************************************************************************************************/
void g_packet_pool0_err_callback_internal(void * p_instance, void * p_data);
void g_packet_pool0_err_callback_internal(void * p_instance, void * p_data)
{
    /** Suppress compiler warning for not using parameters. */
    SSP_PARAMETER_NOT_USED (p_instance);
    SSP_PARAMETER_NOT_USED (p_data);

    /** An error has occurred. Please check function arguments for more information. */
    BSP_CFG_HANDLE_UNRECOVERABLE_ERROR (0);
}
#if defined(__ICCARM__)
#define g_ip0_err_callback_WEAK_ATTRIBUTE
#pragma weak g_ip0_err_callback  = g_ip0_err_callback_internal
#elif defined(__GNUC__)
#define g_ip0_err_callback_WEAK_ATTRIBUTE   __attribute__ ((weak, alias("g_ip0_err_callback_internal")))
#endif
void g_ip0_err_callback(void * p_instance, void * p_data)
g_ip0_err_callback_WEAK_ATTRIBUTE;
NX_IP g_ip0;
uint8_t g_ip0_stack_memory[2048];
uint8_t g_ip0_arp_cache_memory[512];
ULONG g_ip0_actual_status;
/*******************************************************************************************************************//**
 * @brief      This is a weak example initialization error function.  It should be overridden by defining a user  function 
 *             with the prototype below.
 *             - void g_ip0_err_callback(void * p_instance, void * p_data)
 *
 * @param[in]  p_instance arguments used to identify which instance caused the error and p_data Callback arguments used to identify what error caused the callback.
 **********************************************************************************************************************/
void g_ip0_err_callback_internal(void * p_instance, void * p_data);
void g_ip0_err_callback_internal(void * p_instance, void * p_data)
{
    /** Suppress compiler warning for not using parameters. */
    SSP_PARAMETER_NOT_USED (p_instance);
    SSP_PARAMETER_NOT_USED (p_data);

    /** An error has occurred. Please check function arguments for more information. */
    BSP_CFG_HANDLE_UNRECOVERABLE_ERROR (0);
}
void g_common_init(void)
{

    UINT status_ux_init;

    /** Initialize the USBX system. */
    status_ux_init = ux_system_initialize ((CHAR *) g_ux_pool_memory, 18432, UX_NULL, 0);
    if (UX_SUCCESS != status_ux_init)
    {
        ux_v2_err_callback (NULL, &status_ux_init);
    }

#ifdef UX_HOST_INITIALIZE
    /** Initialize the USBX Host stack. */
    status_ux_init = ux_host_stack_initialize(ux_system_host_change_function);
    if (UX_SUCCESS != status_ux_init)
    {
        ux_v2_err_callback(NULL,&status_ux_init);
    }

    /** Register USB Host classes. */
#ifdef USB_HOST_HUB_CLASS_REGISTER
    status_ux_init = ux_host_stack_class_register(_ux_system_host_class_hub_name, ux_host_class_hub_entry);
    if (UX_SUCCESS != status_ux_init)
    {
        ux_v2_err_callback(NULL,&status_ux_init);
    }
#endif

#ifdef USB_HOST_CDC_ACM_CLASS_REGISTER
    status_ux_init = ux_host_stack_class_register(_ux_system_host_class_cdc_acm_name, ux_host_class_cdc_acm_entry);
    if (UX_SUCCESS != status_ux_init)
    {
        ux_v2_err_callback(NULL,&status_ux_init);
    }
#endif

#ifdef USB_HOST_HID_CLASS_REGISTER
    status_ux_init = ux_host_stack_class_register(_ux_system_host_class_hid_name, ux_host_class_hid_entry);
    if (UX_SUCCESS != status_ux_init)
    {
        ux_v2_err_callback(NULL,&status_ux_init);
    }

    ux_host_class_hid_clients_register ();
#endif

#ifdef USB_HOST_STORAGE_CLASS_REGISTER
    ux_host_stack_class_register_storage();
#endif
#endif /* UX_HOST_INITIALIZE */
    {
        UINT status_ux_device_init;

        /** Calculate the size of USBX String Framework. */
        ULONG string_framework_size;
        UCHAR * p_string_framework;

#if(0) /* Check if the number of index in USBX String Framework is greater than zero. */
        {
            p_string_framework = (UCHAR *)NULL;
            string_framework_size = ux_device_string_descriptor_size_get();
        }
#else
        {
            p_string_framework = NULL;
            string_framework_size = 0;
        }
#endif

        /** Calculate the size of USB Language Framework. */
        ULONG language_framework_size;
        UCHAR * p_language_framework;

#if(0) /* Check if the number of index in USB Language Framework is greater than zero. */
        {
            p_language_framework = (UCHAR *)NULL;
            language_framework_size = 0 * 2;
        }
#else
        {
            p_language_framework = (UCHAR *) language_id_framework_default;
            language_framework_size = 2;
        }
#endif

        /** Initialize the USB Device stack. */
#ifdef __GNUC__
        status_ux_device_init = ux_device_stack_initialize(
#if defined(UX_DCD_SYNERY_USE_USBHS)
                (UCHAR *)device_framework_high_speed, ((uint32_t)&__usb_descriptor_end_hs - (uint32_t)&__usb_dev_descriptor_start_hs),
#else /* If USB DCD runs in FS mode, USB Device Descriptor for HS mode is not required. */
                NULL, 0,
#endif
                (UCHAR *)device_framework_full_speed, ((uint32_t)&__usb_descriptor_end_fs - (uint32_t)&__usb_dev_descriptor_start_fs),
                p_string_framework, string_framework_size,
                p_language_framework, language_framework_size, UX_NULL);
#else /* __ICCARM__ */

        uint8_t * p_device_framework;
#if defined(UX_DCD_SYNERY_USE_USBHS)
        p_device_framework = (uint8_t *)device_framework_high_speed_ram;
        memcpy (p_device_framework, device_framework_high_speed, sizeof(device_framework_high_speed));
        p_device_framework = p_device_framework + sizeof(device_framework_high_speed);
        memcpy (p_device_framework, ux_cfg_descriptor_high_speed, sizeof(ux_cfg_descriptor_high_speed));
        p_device_framework = p_device_framework + sizeof(ux_cfg_descriptor_high_speed);
        memcpy (p_device_framework, (uint8_t *)__section_begin(".usb_interface_desc_hs"), USB_TOTAL_LENGTH);
#endif

        /* Creates Device Descriptor Frameworks copying Descriptors from ROM. */
        p_device_framework = (uint8_t *) device_framework_full_speed_ram;
        memcpy (p_device_framework, device_framework_full_speed, sizeof(device_framework_full_speed));
        p_device_framework = p_device_framework + sizeof(device_framework_full_speed);
        /* Copy Configuration Descriptor */
        memcpy (p_device_framework, ux_cfg_descriptor_full_speed, sizeof(ux_cfg_descriptor_full_speed));
        p_device_framework = p_device_framework + sizeof(ux_cfg_descriptor_full_speed);
        /* Copy Interface Descriptors */
        memcpy (p_device_framework, (uint8_t *) __section_begin (".usb_interface_desc_fs"), USB_TOTAL_LENGTH);

        status_ux_device_init = ux_device_stack_initialize (
#if defined(UX_DCD_SYNERY_USE_USBHS)
                (UCHAR *)device_framework_high_speed_ram, (0x12 + 0x0a + USB_CFG_DESCRIPTOR_wTotalLength),
#else /* If USB DCD runs in FS mode, USB Device Descriptor for HS mode is not required. */
                NULL,
                0,
#endif
                (UCHAR *) device_framework_full_speed_ram,
                (0x12 + USB_CFG_DESCRIPTOR_wTotalLength), p_string_framework, string_framework_size,
                p_language_framework, language_framework_size, UX_NULL);
#endif
        if (UX_SUCCESS != status_ux_device_init)
        {
            ux_device_err_callback (NULL, &status_ux_device_init);
        }
    }
    /* Setups USB CDC-ACM Class to create the instance */
    g_ux_device_class_cdc_acm0_setup ();

    /* USB DCD initialization (Only executed once after all the class registration completed). */
    if (g_usbx_number_of_interface_count == USB_NUMBER_OF_INTERFACE)
    {
        /* Initializes USBX Device Control Driver */
        ux_dcd_initialize ();
    }
    /** Initialize the NetX system. */
    nx_system_initialize ();
    UINT g_packet_pool0_err;
    /* Create Client packet pool. */
    g_packet_pool0_err = nx_packet_pool_create (&g_packet_pool0, "g_packet_pool0 Packet Pool", 4096,
                                                &g_packet_pool0_pool_memory[0], (16 * 4096));
    if (NX_SUCCESS != g_packet_pool0_err)
    {
        g_packet_pool0_err_callback ((void *) &g_packet_pool0, &g_packet_pool0_err);
    }
    UINT g_ip0_err;
    /* Create an IP instance. */
    g_ip0_err = nx_ip_create (&g_ip0, "g_ip0 IP Instance", IP_ADDRESS (0, 0, 0, 0), IP_ADDRESS (255, 255, 255, 0),
                              &g_packet_pool0, g_sf_el_nx, &g_ip0_stack_memory[0], 2048, 3);
    if (NX_SUCCESS != g_ip0_err)
    {
        g_ip0_err_callback ((void *) &g_ip0, &g_ip0_err);
    }
#define SYNERGY_NOT_DEFINED     (0xFFFFFFFF)
#if (SYNERGY_NOT_DEFINED != 1)
    g_ip0_err = nx_arp_enable (&g_ip0, &g_ip0_arp_cache_memory[0], 512);
    if (NX_SUCCESS != g_ip0_err)
    {
        g_ip0_err_callback ((void *) &g_ip0, &g_ip0_err);
    }
#endif
#if (SYNERGY_NOT_DEFINED != SYNERGY_NOT_DEFINED)
    g_ip0_err = nx_rarp_enable(&g_ip0);
    if (NX_SUCCESS != g_ip0_err)
    {
        g_ip0_err_callback((void *)&g_ip0,&g_ip0_err);
    }
#endif
#if (SYNERGY_NOT_DEFINED != 1)
    g_ip0_err = nx_tcp_enable (&g_ip0);
    if (NX_SUCCESS != g_ip0_err)
    {
        g_ip0_err_callback ((void *) &g_ip0, &g_ip0_err);
    }
#endif
#if (SYNERGY_NOT_DEFINED != 1)
    g_ip0_err = nx_udp_enable (&g_ip0);
    if (NX_SUCCESS != g_ip0_err)
    {
        g_ip0_err_callback ((void *) &g_ip0, &g_ip0_err);
    }
#endif
#if (SYNERGY_NOT_DEFINED != 1)
    g_ip0_err = nx_icmp_enable (&g_ip0);
    if (NX_SUCCESS != g_ip0_err)
    {
        g_ip0_err_callback ((void *) &g_ip0, &g_ip0_err);
    }
#endif
#if (SYNERGY_NOT_DEFINED != 1)
    g_ip0_err = nx_igmp_enable (&g_ip0);
    if (NX_SUCCESS != g_ip0_err)
    {
        g_ip0_err_callback ((void *) &g_ip0, &g_ip0_err);
    }
#endif
#if (SYNERGY_NOT_DEFINED != SYNERGY_NOT_DEFINED)
    g_ip0_err = nx_ip_fragment_enable(&g_ip0);
    if (NX_SUCCESS != g_ip0_err)
    {
        g_ip0_err_callback((void *)&g_ip0,&g_ip0_err);
    }
#endif
    g_ip0_err = nx_ip_interface_status_check (&g_ip0, 0, NX_IP_INITIALIZE_DONE, &g_ip0_actual_status, NX_WAIT_FOREVER);
    if (NX_SUCCESS != g_ip0_err)
    {
        g_ip0_err_callback ((void *) &g_ip0, &g_ip0_err);
    }
#undef SYNERGY_NOT_DEFINED
}
