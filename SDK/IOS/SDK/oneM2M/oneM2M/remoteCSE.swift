//
//  remoteCSE.swift
//  oneM2M
//
//  Created by SK Telecom on 2017. 7. 2..
//  Copyright © 2017년 SK Telecom. All rights reserved.
//

public class remoteCSE {
    
    //MARK: Properties
    var ni: String?          // nodeId
    var dKey: String?        // deviceKey
    var nm: String?          // name
    var passCode: String?    // passcode
    var cst: String?         // cseType
    var poa: String?         // pointOfAccess
    var rr: String?          // requestRechability
    var nl: String?          // nodeLink
    
    public init() {
    }
    
    public func ni(_ ni: String) -> remoteCSE {
        self.ni = ni
        return self
    }
    
    public func nm(_ nm: String) -> remoteCSE {
        self.nm = nm
        return self
    }
    
    public func dKey(_ dKey: String) -> remoteCSE {
        self.dKey = dKey
        return self
    }
    
    public func passCode(_ passCode: String) -> remoteCSE {
        self.passCode = passCode
        return self
    }
    
    public func cst(_ cst: String?) -> remoteCSE {
        self.cst = cst
        return self
    }
    
    public func poa(_ poa: String?) -> remoteCSE {
        self.poa = poa
        return self
    }
    
    public func rr(_ rr: String?) -> remoteCSE {
        self.rr = rr
        return self
    }
    
    public func nl(_ nl: String?) -> remoteCSE {
        self.nl = nl
        return self
    }
}

