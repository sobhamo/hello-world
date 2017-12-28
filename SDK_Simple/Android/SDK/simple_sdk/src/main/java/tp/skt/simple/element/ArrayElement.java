package tp.skt.simple.element;

import java.util.ArrayList;
import java.util.List;

/**
 * ArrayElement.java
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class ArrayElement {

    public List<Object> elements = new ArrayList<Object>();

    /**
     *
     *
     */
    public ArrayElement() {
    }

    /**
     * @param elements
     */
    public ArrayElement(List<Object> elements) {
        this.elements = elements;
    }

    /**
     * add StringElement, NumberElement, BooleanElement
     *
     * @param element
     */
    public void addElement(Object element) {
        elements.add(element);
    }

    /**
     * add StringElement
     *
     * @param name
     * @param value
     */
    public void addStringElement(String name, String value) {
        StringElement stringElement = new StringElement(name, value);
        addElement(stringElement);
    }

    /**
     * add NumberElement
     *
     * @param name
     * @param value
     */
    public void addNumberElement(String name, Number value) {
        NumberElement numberElement = new NumberElement(name, value);
        addElement(numberElement);
    }

    /**
     * add BooleanElement
     *
     * @param name
     * @param value
     */
    public void addBooleanElement(String name, Boolean value) {
        BooleanElement booleanElement = new BooleanElement(name, value);
        addElement(booleanElement);
    }
}