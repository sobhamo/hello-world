//
//  container.swift
//  oneM2M
//
//  Created by SK Telecom on 2017. 7. 2..
//  Copyright © 2017년 SK Telecom. All rights reserved.
//

public class container {
    
    //MARK: Properties
    var ni: String?      // nodeId
    var nm: String?      // name
    var dKey: String?    // deviceKey
    var lbl: String?     // labels
    
    public init() {
    }
    
    public func ni(_ ni: String) -> container {
        self.ni = ni
        return self
    }
    
    public func nm(_ nm: String) -> container {
        self.nm = nm
        return self
    }
    
    public func dKey(_ dKey: String) -> container {
        self.dKey = dKey
        return self
    }
    
    public func lbl(_ lbl: String?) -> container {
        self.lbl = lbl
        return self
    }
}
