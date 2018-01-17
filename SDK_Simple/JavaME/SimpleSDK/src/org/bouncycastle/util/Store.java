package org.bouncycastle.util;

import javax.util.Collection;

public interface Store
{
    Collection getMatches(Selector selector)
        throws StoreException;
}
