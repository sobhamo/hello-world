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
     * method
     **/
    private StringElement method;
    /**
     * control result
     **/
    private StringElement result;
    /**
     * fail flag
     **/
    private boolean fail;
    /**
     * result body(ArrayElement)
     **/
    private ArrayElement resultArray;
    /**
     * result body(String)
     **/
    private String resultBody;

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
     * @param method
     * @param result
     * @param resultArray
     * @param resultBody
     */
    public RPCResponse(String cmd, int cmdId, String jsonrpc, long id, String method, String result, boolean fail, ArrayElement resultArray, String resultBody) {
        this.setCmd(cmd);
        this.setCmdId(cmdId);
        this.setJsonrpc(jsonrpc);
        this.setId(id);
        this.setMethod(method);
        this.setResult(result);
        this.setFail(fail);
        this.setResultArray(resultArray);
        this.setResultBody(resultBody);
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
    public StringElement getMethod() {
        return method;
    }

    /**
     * @param method
     */
    public void setMethod(String method) {
        if (TextUtils.isEmpty(method) == false) {
            this.method = new StringElement(Define.METHOD, method);
        }
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
     * is control fail
     *
     * @return fail flag
     */
    public boolean isFail() {
        return fail;
    }

    /**
     * set control fail flag
     *
     * @param fail true if fail
     */
    public void setFail(boolean fail) {
        this.fail = fail;
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

    /**
     * get result body string
     *
     * @return result body string
     */
    public String getResultBody() {
        return resultBody;
    }

    /**
     * set result body string
     *
     * @param resultBody result body string
     */
    public void setResultBody(String resultBody) {
        this.resultBody = resultBody;
    }
}
