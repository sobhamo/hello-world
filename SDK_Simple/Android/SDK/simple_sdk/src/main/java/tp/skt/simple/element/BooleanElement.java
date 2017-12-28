package tp.skt.simple.element;

/**
 * BooleanElement.java
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class BooleanElement {
    public String name;
    public Boolean value;

    /**
     *
     *
     */
    public BooleanElement() {
    }

    /**
     * @param name
     * @param value
     */
    public BooleanElement(String name, Boolean value) {
        this.name = name;
        this.value = value;
    }
}
