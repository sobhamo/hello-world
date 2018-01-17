package org.bouncycastle.cert.dane;

import javax.util.List;

public interface DANEEntryFetcher
{
    List getEntries() throws DANEException;
}
