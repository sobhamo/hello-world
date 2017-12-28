package com.skt.thingplug_v2_0_device.data;

/**
 * AttributeControl.java
 *
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class AttributeControl {

    private String name;
    private int value;

    public AttributeControl(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
