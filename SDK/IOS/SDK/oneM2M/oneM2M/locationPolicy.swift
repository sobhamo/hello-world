//
//  locationPolicy.swift
//  oneM2M
//
//  Created by SK Telecom on 2017. 7. 2..
//  Copyright © 2017년 SK Telecom. All rights reserved.
//

public class locationPolicy {
    
    //MARK: Properties
    var ni: String?      // nodeId
    var nm: String?      // name
    var dKey: String?    // deviceKey
    var los: String?     // locationSource
    var lbl: String?     // labels
    
    public init() {
    }
    
    public func ni(_ ni: String) -> locationPolicy {
        self.ni = ni
        return self
    }
    
    public func nm(_ nm: String) -> locationPolicy {
        self.nm = nm
        return self
    }
    
    public func dKey(_ dKey: String) -> locationPolicy {
        self.dKey = dKey
        return self
    }
    
    public func los(_ los: String) -> locationPolicy {
        self.los = los
        return self
    }
    
    public func lbl(_ lbl: String) -> locationPolicy {
        self.lbl = lbl
        return self
    }
}
