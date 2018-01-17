package org.bouncycastle.cert.path;

import javax.util.HashSet;
import javax.util.Set;

import org.bouncycastle.cert.X509CertificateHolder;

class CertPathUtils
{
    static Set getCriticalExtensionsOIDs(X509CertificateHolder[] certificates)
    {
        Set criticalExtensions = new HashSet();

        for (int i = 0; i != certificates.length; i++)
        {
            criticalExtensions.addAll(certificates[i].getCriticalExtensionOIDs());
        }

        return criticalExtensions;
    }
}
