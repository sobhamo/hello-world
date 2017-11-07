//
//  oneM2M.swift
//  oneM2M
//
//  Created by SK Telecom on 2017. 7. 1..
//  Copyright © 2017년 SK Telecom. All rights reserved.
//

import Foundation

public class oneM2M {
    
    var mqttClient: MQTTClient?
    var content: String = ""
    
    /**
     * @brief init
     * @param[in] mqttClinet : MQTT client
     */
    public init(mqttClient: MQTTClient) {
        self.mqttClient = mqttClient
    }
    
    /**
     * @brief make attribute string data
     * @param[in] payload : payload buffer
     * @param[in] attrs : oneM2M_Attribute list data pointer
     */
    func setElement(_ payload: inout String,_ attrs:[String:String?]) {
        for (key, value) in attrs {
            if let va = value {
                payload += String(format: "<%@>%@</%@>", key, va, key)
            }
        }
    }
    
    /**
     * @brief make primitive_content attribute string data
     * @param[in] payload : payload buffer
     * @param[in] resourceType : oneM2M resource type value
     * @param[in] attrs : oneM2M_Attribute list data pointer
     */
    func setPCElement(_ payload: inout String,_ resourceType: ResourceType,_ attrs:[String:String?]) {
        var pc: String = ""
        setElement(&pc, attrs);
        payload += String(format: "<%@><%@>%@</%@></%@>", Attribute.PC, resourceType.resourceTypeString,
                          pc, resourceType.resourceTypeString, Attribute.PC)
    }

    /**
     * @brief make xml header with ty, op, ri, to
     * @param[in] payload : payload buffer
     * @param[in] resourceType : oneM2M resource type value
     * @param[in] operation : operation
     * @param[in] to : target
     * @param[in] ri : request id
     */
    func setHeader(_ payload: inout String,_ resourceType: ResourceType,_ operation: RequestType,_ to: String,_ ri: String) {
        payload = Attribute.XML_HEADER_V1
       
        // op & to & ri
        if operation == .CREATE {
            let attrs  = [Attribute.OP: String(operation.rawValue), Attribute.TO: to, Attribute.TY: String(resourceType.rawValue), Attribute.RI: ri]
            setElement(&payload, attrs);
        } else {
            let attrs  = [Attribute.OP: String(operation.rawValue), Attribute.TO: to, Attribute.RI: ri]
            setElement(&payload, attrs);
        }
    }
    
    /**
     * @brief make xml footer
     */
    func setFooter(_ payload: inout String) {
        payload += Attribute.XML_FOOTER_V1;
    }
    
    /**
     * @brief check attributes
     * @param[in] resourceType : oneM2M resource type value
     * @param[in] operation : operation
     * @param[in] to : target
     * @param[in] ri : request id
     * @return Result : result code
     */
    func checkAttributes(_ resourceType: ResourceType,_ operation: RequestType,_ to: String,_ ri: String) -> Result {
        var rc = Result.TP_SDK_SUCCESS;
        _console("ty:\(resourceType), op:\(operation), to:\(to), ri:\(ri)");
    
        // check resourceType & operation
        switch(resourceType) {
            case .CSEBase:
                if operation != .RETRIEVE {
                    rc = .TP_SDK_NOT_SUPPORTED;
                }
            case .node, .remoteCSE, .container, .mgmtCmd, .locationPolicy, .AE, .mgmtObj:
                if operation == .RETRIEVE {
                    rc = .TP_SDK_NOT_SUPPORTED;
                }
            case .contentInstance:
                if operation == .RETRIEVE || operation == .UPDATE {
                    rc = .TP_SDK_NOT_SUPPORTED;
                }
            case .execInstance:
                if operation != .UPDATE {
                    rc = .TP_SDK_NOT_SUPPORTED;
                }
            default:
                rc = .TP_SDK_NOT_SUPPORTED;
        }
    
        // check to & ri & pc is NULL?
        if rc == .TP_SDK_SUCCESS {
            if to.isEmpty || ri.isEmpty {
                rc = .TP_SDK_INVALID_PARAMETER;
            }
        }
        return rc
    }

    /**
     * @brief oneM2M V1 request
     * @param[in] resourceType : oneM2M resource type value
     * @param[in] operation : operation
     * @param[in] to : target
     * @param[in] ri : request id
     * @param[in] pc : primitive contents
     * @return UInt16 : publish message id(if success lager than 0)
     */
    public func tp_oneM2M_V1_Request(_ resourceType: ResourceType,_ operation: RequestType,_ to: String,_ ri: String,_ pc: AnyObject) -> UInt16 {
        var rc: UInt16 = 0
        let check = checkAttributes(resourceType, operation, to, ri);
        if check != .TP_SDK_SUCCESS {
            return rc
        }
        
        var payload: String = ""
        setHeader(&payload, resourceType, operation, to, ri);
    
        switch(resourceType) {
        case .CSEBase:
            let CSEBase: CSEBase = pc as! CSEBase;
            let attrs = [Attribute.FR:CSEBase.ni];
            setElement(&payload, attrs);
        case .node:
            let node: node = pc as! node;
            let attrs = [Attribute.FR:node.ni, Attribute.NM:node.ni, Attribute.DKEY:node.dKey]
            setElement(&payload, attrs);
            if operation != .DELETE {
                let attrs = [Attribute.NI:node.ni, Attribute.HCL:node.hcl, Attribute.MGA:node.mga]
                setPCElement(&payload, resourceType, attrs);
            }
        case .remoteCSE:
            let remoteCSE: remoteCSE = pc as! remoteCSE;
            let attrs = [Attribute.FR:remoteCSE.ni, Attribute.DKEY:remoteCSE.dKey, Attribute.PASSCODE:remoteCSE.passCode, Attribute.NM:remoteCSE.nm]
            setElement(&payload, attrs);
            if operation != .DELETE {
                let attrs = [Attribute.CST:remoteCSE.cst, Attribute.CSI:remoteCSE.ni, Attribute.POA:remoteCSE.poa, Attribute.RR:remoteCSE.rr, Attribute.NL:remoteCSE.nl]
                setPCElement(&payload, resourceType, attrs);
            }
        case .container:
            let container: container = pc as! container;
            let attrs = [Attribute.FR:container.ni, Attribute.NM:container.nm, Attribute.DKEY:container.dKey]
            setElement(&payload, attrs);
            if operation != .DELETE {
                let attrs = [Attribute.LBL:container.lbl]
                setPCElement(&payload, resourceType, attrs);
            }
        case .mgmtCmd:
            let mgmtCmd: mgmtCmd = pc as! mgmtCmd;
            let attrs = [Attribute.FR:mgmtCmd.ni, Attribute.NM:mgmtCmd.nm, Attribute.DKEY:mgmtCmd.dKey]
            setElement(&payload, attrs);
            if operation != .DELETE {
                let attrs = [Attribute.CMT:mgmtCmd.cmt, Attribute.EXE:mgmtCmd.exe, Attribute.EXT:mgmtCmd.ext, Attribute.LBL:mgmtCmd.lbl]
                setPCElement(&payload, resourceType, attrs);
            }
        case .contentInstance:
            let contentInstance: contentInstance = pc as! contentInstance;
            let attrs = [Attribute.FR:contentInstance.ni, Attribute.DKEY:contentInstance.dKey]
            setElement(&payload, attrs);
            if operation != .DELETE {
                let attrs = [Attribute.CNF:contentInstance.cnf, Attribute.CON:contentInstance.con, Attribute.LBL:contentInstance.lbl]
                setPCElement(&payload, resourceType, attrs);
            }
        case .locationPolicy:
            let locationPolicy: locationPolicy = pc as! locationPolicy;
            let attrs = [Attribute.FR:locationPolicy.ni, Attribute.DKEY:locationPolicy.dKey, Attribute.NM:locationPolicy.nm]
            setElement(&payload, attrs);
            if operation != .DELETE {
                let attrs = [Attribute.LOS:locationPolicy.los, Attribute.LBL:locationPolicy.lbl]
                setPCElement(&payload, resourceType, attrs);
            }
        case .AE:
            let AE: AE = pc as! AE;
            let attrs = [Attribute.FR:AE.ni, Attribute.DKEY:AE.dKey]
            setElement(&payload, attrs);
            if operation != .DELETE {
                let attrs = [Attribute.API:AE.api, Attribute.APN:AE.apn]
                setPCElement(&payload, resourceType, attrs);
            }
        case .execInstance:
            let execInstance: execInstance = pc as! execInstance;
            let attrs = [Attribute.FR:execInstance.ni, Attribute.DKEY:execInstance.dKey]
            setElement(&payload, attrs);
            if operation != .DELETE {
                let attrs = [Attribute.EXR:execInstance.exr, Attribute.EXS:execInstance.exs]
                setPCElement(&payload, resourceType, attrs);
            }
        default:
            break
        }
        setFooter(&payload);
        _console("request: \(payload)")
        if let mqttClient = self.mqttClient {
            rc = mqttClient.publishMessage(message: payload)
        }
        return rc
    }
    
    /**
     * @brief register node, remoteCSE
     * @param[in] resourceType : resource type
     * @param[in] fr : from
     * @param[in] to : to
     * @param[in] ri : request id
     * @param[in] mga : node mga
     * @param[in] nm : remoteCSE name
     * @param[in] cst : remoteCSE type
     * @param[in] passcode : remoteCSE passcode
     * @param[in] poa : remoteCSE pointOfAccess
     * @param[in] nl : remoteCSE nodeLink
     * @return UInt16 : publish message id(if success lager than 0)
     */
    public func tpRegisterDevice(_ resourceType: ResourceType,_ fr: String,_ to: String,_ ri: String,_ mga: String?,_ nm: String?,_ cst: String?,_ passCode: String?,_ poa: String?,_ nl: String?) -> UInt16 {
        var rc: UInt16 = 0
        switch(resourceType) {
        case .node:
            let nodeObj:node = node().ni(fr).mga(mga)
            rc = tp_oneM2M_V1_Request(resourceType, .CREATE, to, ri, nodeObj)
            break
        case .remoteCSE:
            let remoteCSEObj:remoteCSE = remoteCSE().ni(fr).nm(nm!).cst(cst).passCode(passCode!).poa(poa).rr("true").nl(nl)
            rc = tp_oneM2M_V1_Request(resourceType, .CREATE, to, ri, remoteCSEObj)
            break
        default:
            break
        }
        return rc
    }
    
    /**
     * @brief register container
     * @param[in] fr : from
     * @param[in] to : to
     * @param[in] ri : request id
     * @param[in] nm : container name
     * @param[in] dKey : dKey
     * @param[in] lbl : labels
     * @return UInt16 : publish message id(if success lager than 0)
     */
    public func tpRegisterContainer(_ fr: String,_ to: String,_ ri: String,_ nm: String,_ dKey: String,_ lbl: String?) -> UInt16 {
        var rc: UInt16 = 0
        let containerObj:container = container().ni(fr).nm(nm).dKey(dKey).lbl(lbl)
        rc = tp_oneM2M_V1_Request(.container, .CREATE, to, ri, containerObj)
        return rc
    }
    
    /**
     * @brief register mgmtCmd
     * @param[in] fr : from
     * @param[in] to : to
     * @param[in] ri : request id
     * @param[in] nm : mgmtCmd name
     * @param[in] dKey : dKey
     * @param[in] cmt : cmdType
     * @param[in] exe : execEnable
     * @param[in] ext : execTarget
     * @param[in] lbl : labels
     * @return UInt16 : publish message id(if success lager than 0)
     */
    public func tpRegisterMgmtCmd(_ fr: String,_ to: String,_ ri: String,_ nm: String,_ dKey: String,_ cmt: String,_ exe: String,_ ext: String,_ lbl: String?) -> UInt16 {
        var rc: UInt16 = 0
        let mgmtCmdObj:mgmtCmd = mgmtCmd().ni(fr).nm(nm).dKey(dKey).cmt(cmt).exe(exe).ext(ext).lbl(lbl)
        rc = tp_oneM2M_V1_Request(.mgmtCmd, .CREATE, to, ri, mgmtCmdObj)
        return rc
    }
    
    /**
     * @brief add content data of contentInstance
     * @param[in] data : data
     * @return Result : result code
     */
    public func tpAddData(_ data: String) -> Result {
        self.content.append(data)
        return .TP_SDK_SUCCESS
    }
    
    /**
     * @brief report
     * @param[in] fr : from
     * @param[in] to : to
     * @param[in] ri : request id
     * @param[in] dKey : dKey
     * @param[in] cnf : contentInfo
     * @param[in] con : content
     * @param[in] useAddedData : use added data flag
     * @return UInt16 : publish message id(if success lager than 0)
     */
    public func tpReport(_ fr: String,_ to: String,_ ri: String,_ dKey: String,_ cnf: String,_ con: String?,_ useAddedData: Bool) -> UInt16 {
        var rc: UInt16 = 0
        let content = useAddedData ? self.content:con
        let contentInstanceObj:contentInstance = contentInstance().ni(fr).dKey(dKey).cnf(cnf).con(content!)
        rc = tp_oneM2M_V1_Request(.contentInstance, .CREATE, to, ri, contentInstanceObj)
        self.content = ""
        return rc
    }
    
    /**
     * @brief report
     * @param[in] fr : from
     * @param[in] to : to
     * @param[in] ri : request id
     * @param[in] dKey : dKey
     * @param[in] exr : execResult
     * @param[in] exs : execStatus
     * @return UInt16 : publish message id(if success lager than 0)
     */
    public func tpResult(_ fr: String,_ to: String,_ ri: String,_ dKey: String,_ exr: String,_ exs: String) -> UInt16 {
        var rc: UInt16 = 0
        let execInstanceObj:execInstance = execInstance().ni(fr).dKey(dKey).exr(exr).exs(exs)
        rc = tp_oneM2M_V1_Request(.execInstance, .UPDATE, to, ri, execInstanceObj);
        return rc;
    }
    
}
