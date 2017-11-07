//
//  mgmtCmd.swift
//  oneM2M
//
//  Created by SK Telecom on 2017. 7. 2..
//  Copyright © 2017년 SK Telecom. All rights reserved.
//

public class mgmtCmd {
    
    //MARK: Properties
    var ni: String?      // nodeId
    var nm: String?      // name
    var dKey: String?    // deviceKey
    var cmt: String?     // cmtType
    var exe: String?     // execEnable
    var ext: String?     // execTarget
    var lbl: String?     // labels
    
    public init() {
    }
    
    public func ni(_ ni: String) -> mgmtCmd {
        self.ni = ni
        return self
    }
    
    public func nm(_ nm: String) -> mgmtCmd {
        self.nm = nm
        return self
    }
    
    public func dKey(_ dKey: String) -> mgmtCmd {
        self.dKey = dKey
        return self
    }
    
    public func cmt(_ cmt: String) -> mgmtCmd {
        self.cmt = cmt
        return self
    }
    
    public func exe(_ exe: String) -> mgmtCmd {
        self.exe = exe
        return self
    }
    
    public func ext(_ ext: String) -> mgmtCmd {
        self.ext = ext
        return self
    }
    
    public func lbl(_ lbl: String?) -> mgmtCmd {
        self.lbl = lbl
        return self
    }
}
