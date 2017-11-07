//
//  node.swift
//  oneM2M
//
//  Created by SK Telecom on 2017. 7. 2..
//  Copyright © 2017년 SK Telecom. All rights reserved.
//

public class node {
    
    //MARK: Properties
    var ni: String?     // nodeId
    var dKey: String?   // deviceKey
    var hcl: String?    // hostCSELink
    var mga: String?    // mga
    
    public init() {
    }
    
    public func ni(_ ni: String) -> node {
        self.ni = ni
        return self
    }
    
    public func hcl(_ hcl: String) -> node {
        self.hcl = hcl
        return self
    }
    
    public func dKey(_ dKey: String) -> node {
        self.dKey = dKey
        return self
    }
    
    public func mga(_ mga: String?) -> node {
        self.mga = mga
        return self
    }
}
