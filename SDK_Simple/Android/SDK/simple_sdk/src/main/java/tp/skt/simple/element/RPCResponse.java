package tp.skt.simple.element;

import android.text.TextUtils;

import tp.skt.simple.common.Define;

/**
 * RPCResponse.java
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class RPCResponse {
    /**
     * command
     **/
    private StringElement cmd;
    /**
     * command ID
     **/
    private NumberElement cmdId;
    /**
     * JSON RPC version
     **/
    private StringElement jsonrpc;
    /**
     * request ID from server
     **/
    private NumberElement id;
    /**
     * control result
     **/
    private StringElement result;
    /**
     * isSuccess flag
     **/
    private boolean isSuccess;
    /**
     * result body(ArrayElement)
     **/
    private ArrayElement resultArray;

    /**
     *
     *
     */
    public RPCResponse() {
    }

    /**
     * @param cmd
     * @param cmdId
     * @param jsonrpc
     * @param id
     * @param result
     * @param success
     * @param resultArray
     */
    public RPCResponse(String cmd, int cmdId, String jsonrpc, long id, String result, boolean success, ArrayElement resultArray) {
        this.setCmd(cmd);
        this.setCmdId(cmdId);
        this.setJsonrpc(jsonrpc);
        this.setId(id);
        this.setResult(result);
        this.setSuccess(success);
        this.setResultArray(resultArray);
    }

    /**
     * @return
     */
    public StringElement getCmd() {
        return cmd;
    }

    /**
     * @param cmd
     */
    public void setCmd(String cmd) {
        if (TextUtils.isEmpty(cmd) == false) {
            this.cmd = new StringElement(Define.CMD, cmd);
        }
    }

    /**
     * @return
     */
    public NumberElement getCmdId() {
        return cmdId;
    }

    /**
     * @param cmdId
     */
    public void setCmdId(int cmdId) {
        this.cmdId = new NumberElement(Define.CMD_ID, cmdId);
    }

    /**
     * @return
     */
    public StringElement getJsonrpc() {
        return jsonrpc;
    }

    /**
     * @param jsonrpc
     */
    public void setJsonrpc(String jsonrpc) {
        if (TextUtils.isEmpty(jsonrpc) == false) {
            this.jsonrpc = new StringElement(Define.JSONRPC, jsonrpc);
        }
    }

    /**
     * @return
     */
    public NumberElement getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(long id) {
        this.id = new NumberElement(Define.ID, id);
    }

    /**
     * @return
     */
    public StringElement getResult() {
        return result;
    }

    /**
     * @param result
     */
    public void setResult(String result) {
        if (TextUtils.isEmpty(result) == false) {
            this.result = new StringElement(Define.RESULT, result);
        }
    }

    /**
     * is control success
     *
     * @return success flag
     */
    public boolean isSuccess() {
        return isSuccess;
    }

    /**
     * set control isSuccess flag
     *
     * @param success true if isSuccess
     */
    public void setSuccess(boolean success) {
        this.isSuccess = success;
    }

    /**
     * get result ArrayElement
     *
     * @return result ArrayElement
     */
    public ArrayElement getResultArray() {
        return resultArray;
    }

    /**
     * set result ArrayElement
     *
     * @param resultArray result ArrayElement
     */
    public void setResultArray(ArrayElement resultArray) {
        this.resultArray = resultArray;
    }

}
