//
//  Definitions.swift
//  oneM2M
//
//  Created by SK Telecom on 2017. 2. 18..
//  Copyright © 2017년 SK Telecom. All rights reserved.
//

/**
 * Conn Ack
 */
public enum MQTTConnAck: UInt8 {
    case accept = 0
    case unacceptableProtocolVersion
    case identifierRejected
    case serverUnavailable
    case badUsernameOrPassword
    case notAuthorized
    case reserved
}


public enum Result: Int8 {
    case TP_SDK_SUCCESS = 0
    case TP_SDK_FAILURE = -1
    /**
     * Return code: Resource type or Operation is not supported
     */
    case TP_SDK_NOT_SUPPORTED = -13
    /**
     * Return code: Parameter is invalid
     */
    case TP_SDK_INVALID_PARAMETER = -14
}

public enum RequestType: UInt8 {
    case CREATE = 1
    case RETRIEVE
    case UPDATE
    case DELETE
    case NOTIFY
}

public enum ResourceType: UInt16 {
    case accessControlPolicy = 1
    case AE
    case container
    case contentInstance
    case CSEBase
    case delivery
    case eventConfig
    case execInstance
    case group
    case locationPolicy
    case m2mServiceSubscriptionProfile
    case mgmtCmd
    case mgmtObj
    case node
    case pollingChannel
    case remoteCSE
    case request
    case schedule
    case serviceSubscribedNode
    case statsCollect
    case statsConfig
    case subscription
    case accessControlPolicyAnnc = 10001
    case AEAnnc
    case containerAnnc
    case contentInstanceAnnc
    case groupAnnc = 10009
    case locationPolicyAnnc
    case mgmtObjAnnc = 10013
    case nodeAnnc
    case remoteCSEAnnc = 10016
    case scheduleAnnc = 10018
}

enum MQTTConnectionStatus {
    case DISCONNECTED
    case CONNECTING
    case CONNECTED
}

public struct Attribute {
    static let ACP = "acp"					// resource accessControlPolicy
    static let AE = "ae"                    // resource AE
    static let CNT = "cnt"                  // resource container
    static let CIN = "cin"                  // resource contentInstance
    static let EXIN = "exin"                // resource execInstance
    static let LCP = "lcp"                  // resource locationPolicy
    static let MGC = "mgc"                  // resource mgmtCmd
    static let NOD = "nod"                  // resource node
    static let CSR = "csr"                  // resource remoteCSE
    static let ANI = "ani"                  // resource areaNwkInfo
    static let OP = "op"                    // operation
    static let TO = "to"                    // to
    static let FR = "fr"                    // from
    static let RQI = "rqi"                  // Request Identifier
    static let TY = "ty"                    // Resource type
    static let NM = "nm"                    // name
    static let PC = "pc"                    // Primitive Content
    static let LBL = "lbl"                  // Label
    static let RSC = "rsc"                  // responseStatusCode
    static let ACPI = "acpi"                // accessControlPolicyIDs
    static let CT = "ct"                    // Creation time
    static let ET = "et"                    // Expiration time
    static let LT = "lt"                    // Last modified time
    static let PI = "pi"                    // Parent ID
    static let RI = "ri"                    // Resource ID
    static let ST = "st"                    // State tag
    static let RN = "rn"                    // Resource name
    static let PV = "pv"                    // privileges
    static let PVS = "pvs"                  // selfPrivileges
    static let API = "api"                  // App-ID
    static let AEI = "aei"                  // AE-ID
    static let APN = "apn"                  // appName
    static let POA = "poa"                  // Point of access
    static let NL = "nl"                    // Node link
    static let CR = "cr"                    // Creator
    static let MNI = "mni"                  // maxNrOfInstances
    static let MBS = "mbs"                  // maxByteSize
    static let MIA = "mia"                  // maxInstanceAge
    static let CNI = "cni"                  // Current number of instances
    static let CBS = "cbs"                  // Current byte size
    static let LI = "li"                    // locationID
    static let CNF = "cnf"                  // Content info
    static let CS = "cs"                    // Content Size
    static let CON = "con"                  // Content
    static let CST = "cst"                  // CSE Type
    static let CSI = "csi"                  // CSE ID
    static let EXS = "exs"					// execStatus
    static let EXR = "exr"                  // execResult
    static let EXD = "exd"                  // execDisable
    static let EXT = "ext"                  // execTarget
    static let EXM = "exm"                  // execMode
    static let EXF = "exf"                  // execFrequency
    static let EXY = "exy"                  // execDelay
    static let EXN = "exn"                  // execNumber
    static let EXRA = "exra"                // execReqArgs
    static let EXE = "exe"                  // execEnable
    static let LOS = "los"                  // locationSource
    static let LOU = "lou"                  // locationUpdatePeriod
    static let LOT = "lot"                  // locationTargetID
    static let LOR = "lor"                  // locationServer
    static let LON = "lon"                  // locationContainerName
    static let DC = "dc"                    // description
    static let CMT = "cmt"                  // cmdType
    static let MGD = "mgd"                  // mgmtDefinition
    static let NI = "ni"                    // Node ID
    static let HCL = "hcl"                  // hostCSELink
    static let CB = "cb"                    // CSEBase
    static let RR = "rr"                    // Request rechability
    static let GWST = "gwst"                // Gateway status
    static let SSST = "ssst"                // Sensor status
    static let ANT = "ant"                  // areaNwkType
    static let LDV = "ldv"                  // listOfDevices
    
    static let PASSCODE = "passCode"        // passcode
    static let DKEY = "dKey"                // deviceKey
    static let UKEY = "uKey"                // userKey
    static let MGA = "mga"
    
    static let XML_HEADER_V1 = "<m2m:req xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.onem2m.org/xml/protocols CDT-requestPrimitive-v1_0_0.xsd\"><cty>application/vnd.onem2m-prsp+xml</cty>"
    static let XML_FOOTER_V1 = "</m2m:req>"
}

public extension ResourceType {
    var resourceTypeString: String {
        switch self {
        case .node:
            return Attribute.NOD
        case .remoteCSE:
            return Attribute.CSR
        case .AE:
            return Attribute.AE
        case .mgmtCmd:
            return Attribute.MGC
        case .execInstance:
            return Attribute.EXIN
        case .mgmtObj:
            return Attribute.ANI
        case .locationPolicy:
            return Attribute.LCP
        case .container:
            return Attribute.CNT
        case .contentInstance:
            return Attribute.CIN
        default:
            return ""
        }
    }
}

func _console(_ info: String) {
    print("TP_ONEMEM_SDK : \(info)")
}


