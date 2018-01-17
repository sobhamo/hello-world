package org.bouncycastle.crypto.params;

import javax.math.BigInteger;

public class GOST3410PrivateKeyParameters
        extends GOST3410KeyParameters
{
    private BigInteger      x;

    public GOST3410PrivateKeyParameters(
        BigInteger      x,
        GOST3410Parameters   params)
    {
        super(true, params);

        this.x = x;
    }

    public BigInteger getX()
    {
        return x;
    }
}
