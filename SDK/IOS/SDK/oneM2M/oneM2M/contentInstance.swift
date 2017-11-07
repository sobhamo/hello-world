//
//  contentInstance.swift
//  oneM2M
//
//  Created by SK Telecom on 2017. 7. 2..
//  Copyright © 2017년 SK Telecom. All rights reserved.
//

public class contentInstance {
    
    //MARK: Properties
    var ni: String?      // nodeId
    var dKey: String?    // deviceKey
    var cnf: String?     // contentInfo
    var con: String?     // content
    var lbl: String?     // labels
    
    public init() {
    }
    
    public func ni(_ ni: String) -> contentInstance {
        self.ni = ni
        return self
    }
    
    public func cnf(_ cnf: String) -> contentInstance {
        self.cnf = cnf
        return self
    }
    
    public func dKey(_ dKey: String) -> contentInstance {
        self.dKey = dKey
        return self
    }
    
    public func con(_ con: String) -> contentInstance {
        self.con = con
        return self
    }
    
    public func lbl(_ lbl: String) -> contentInstance {
        self.lbl = lbl
        return self
    }}
