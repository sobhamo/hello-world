package com.skt.thingplug_v2_0_device.data;

import java.util.ArrayList;
import java.util.List;


public class DeviceDescriptor {
    private String displayName;
    private String deviceDescriptorName;
    private String deviceDescriptorId;

    public DeviceDescriptor(String displayName, String deviceDescriptorName, String deviceDescriptorId) {
        this.displayName = displayName;
        this.deviceDescriptorName = deviceDescriptorName;
        this.deviceDescriptorId = deviceDescriptorId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDeviceDescriptorName() {
        return deviceDescriptorName;
    }

    public String getDeviceDescriptorId() {
        return deviceDescriptorId;
    }
}
