package com.skt.thingplug_v2_0_device.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 백승엽 on 2017-11-17.
 */

public class DeviceInfo {
    private List<String> services = new ArrayList<>();
    private List<String> descriptors = new ArrayList<>();

    public List<String> getServices() {
        return services;
    }

    public List<String> getDescriptors() {
        return descriptors;
    }

    public void addService(String service) {
        services.add(service);
    }

    public void addDescriptor(String descriptor) {
        descriptors.add(descriptor);
    }
}
