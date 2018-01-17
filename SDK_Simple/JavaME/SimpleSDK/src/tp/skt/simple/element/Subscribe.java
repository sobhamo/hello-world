package tp.skt.simple.element;

import java.util.Vector;
import tp.java.util.TextUtils;

import tp.skt.simple.common.Define;

/**
 * Subscribe.java
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class Subscribe {
    /** command **/
    private StringElement cmd;
    /** service name **/
    private StringElement serviceName;
    /** device name **/
    private StringElement deviceName;
    /** sensor node ID(optional) **/
    private StringElement sensorNodeId;
    /** is target all **/
    private BooleanElement isTargetAll;
    /** attribute array **/
    private Vector attribute;
    /** telemetry array **/
    private Vector telemetry;
    /** command ID **/
    private IntElement cmdId;

    /**
     *
     *
     */
    public Subscribe() {
    }

    /**
     *
     *
     * @param cmd
     * @param serviceName
     * @param deviceName
     * @param sensorNodeId
     * @param isTargetAll
     * @param attribute
     * @param telemetry
     * @param cmdId
     */
    public Subscribe(String cmd, String serviceName, String deviceName, String sensorNodeId, boolean isTargetAll, Vector attribute, Vector telemetry, int cmdId) {
        this.setCmd(cmd);
        this.setServiceName(serviceName);
        this.setDeviceName(deviceName);
        this.setSensorNodeId(sensorNodeId);
        this.setIsTargetAll(isTargetAll);
        this.setAttribute(attribute);
        this.setTelemetry(telemetry);
        this.setCmdId(cmdId);
    }

    public StringElement getCmd() {
        return cmd;
    }

    public StringElement getServiceName() {
        return serviceName;
    }

    public StringElement getDeviceName() {
        return deviceName;
    }

    public StringElement getSensorNodeId() {
        return sensorNodeId;
    }

    public BooleanElement getIsTargetAll() {
        return isTargetAll;
    }

    public Vector getAttribute() {
        return attribute;
    }

    public Vector getTelemetry() {
        return telemetry;
    }

    public IntElement getCmdId() {
        return cmdId;
    }

    /**
     *
     *
     * @param cmd
     */
    public void setCmd(String cmd) {
        this.cmd = new StringElement(Define.CMD, cmd);
    }

    /**
     *
     *
     * @param serviceName
     */
    public void setServiceName(String serviceName) {
        if(TextUtils.isEmpty(serviceName) == false) {
            this.serviceName = new StringElement(Define.SERVICE_NAME, serviceName);
        }
    }

    /**
     *
     *
     * @param deviceName
     */
    public void setDeviceName(String deviceName) {
        if(TextUtils.isEmpty(deviceName) == false) {
            this.deviceName = new StringElement(Define.DEVICE_ID, deviceName);
        }
    }

    /**
     *
     *
     * @param sensorNodeId
     */
    public void setSensorNodeId(String sensorNodeId) {
        if(TextUtils.isEmpty(sensorNodeId) == false) {
            this.sensorNodeId = new StringElement(Define.SENSOR_NODE_ID, sensorNodeId);
        }
    }

    /**
     *
     *
     * @param isTargetAll
     */
    public void setIsTargetAll(boolean isTargetAll) {
        this.isTargetAll = new BooleanElement(Define.IS_TARGET_ALL, isTargetAll);
    }

    /**
     *
     *
     * @param attribute
     */
    public void setAttribute(Vector attribute) {
        if(attribute != null) {
            this.attribute = attribute;
        }
    }

    /**
     * add attribute
     *
     * @param attribute
     */
    public void addAttribute(String attribute) {
        if(TextUtils.isEmpty(attribute) == false) {
            if(this.attribute == null) this.attribute = new Vector();
            this.attribute.addElement(attribute);
        }
    }

    /**
     *
     *
     * @param telemetry
     */
    public void setTelemetry(Vector telemetry) {
        if(telemetry != null) {
            this.telemetry = telemetry;
        }
    }

    /**
     * add telemetry
     *
     * @param telemetry
     */
    public void addTelemetry(String telemetry) {
        if(TextUtils.isEmpty(telemetry) == false) {
            if(this.telemetry == null) this.telemetry = new Vector();
            this.telemetry.addElement(telemetry);
        }
    }

    /**
     *
     *
     * @param cmdId
     */
    public void setCmdId(int cmdId) {
        this.cmdId = new IntElement(Define.CMD_ID, cmdId);
    }
}
