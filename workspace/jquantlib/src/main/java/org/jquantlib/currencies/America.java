/*
 Copyright (C) 2009 Ueli Hofstetter

 This source code is release under the BSD License.

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the JQuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <jquant-devel@lists.sourceforge.net>. The license is also available online at
 <http://www.jquantlib.org/index.php/LICENSE.TXT>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.

 JQuantLib is based on QuantLib. http://quantlib.org/
 When applicable, the original copyright notice follows this notice.
 */
/*
 Copyright (C) 2004, 2005 StatPro Italia srl

 This file is part of QuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://quantlib.org/

 QuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <quantlib-dev@lists.sf.net>. The license is also available online at
 <http://quantlib.org/license.shtml>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.
 */
package org.jquantlib.currencies;

import org.jquantlib.math.Rounding;

public class America {
    /**
     * Argentinian peso The ISO three-letter code is ARS; the numeric code is 32. It is divided in 100 centavos.
     * 
     * @category currencies
     */
    public static class ARSCurrency extends Currency {
        public ARSCurrency() {
            Data arsData = new Data("Argentinian peso", "ARS", 32, "", "", 100, new Rounding(), "%2% %1$.2f");
            data = arsData;
        }
    };

    /**
     * Brazilian real The ISO three-letter code is BRL; the numeric code is 986. It is divided in 100 centavos.
     * 
     * @category currencies
     */
    public static class BRLCurrency extends Currency {
        public BRLCurrency() {
            Data brlData = new Data("Brazilian real", "BRL", 986, "R$", "", 100, new Rounding(), "%3% %1$.2f");
            data = brlData;
        }
    };

    /**
     * Canadian dollar The ISO three-letter code is CAD; the numeric code is 124. It is divided in 100 cents.
     * 
     * @category currencies
     */
    public static class CADCurrency extends Currency {
        public CADCurrency() {
            Data cadData = new Data("Canadian dollar", "CAD", 124, "Can$", "", 100, new Rounding(), "%3% %1$.2f");
            data = cadData;
        }
    };

    /**
     * Chilean peso The ISO three-letter code is CLP; the numeric code is 152. It is divided in 100 centavos.
     * 
     * @category currencies
     */
    public static class CLPCurrency extends Currency {
        public CLPCurrency() {
            Data clpData = new Data("Chilean peso", "CLP", 152, "Ch$", "", 100, new Rounding(), "%3% %1$.0f");
            data = clpData;
        }
    };

    /**
     * Colombian peso The ISO three-letter code is COP; the numeric code is 170. It is divided in 100 centavos.
     * 
     * @category currencies
     */
    public static class COPCurrency extends Currency {
        public COPCurrency() {
            Data copData = new Data("Colombian peso", "COP", 170, "Col$", "", 100, new Rounding(), "%3% %1$.2f");
            data = copData;
        }
    };

    /**
     * Mexican peso The ISO three-letter code is MXN; the numeric code is 484. It is divided in 100 centavos.
     * 
     * @category currencies
     */
    public static class MXNCurrency extends Currency {
        public MXNCurrency() {
            Data mxnData = new Data("Mexican peso", "MXN", 484, "Mex$", "", 100, new Rounding(), "%3% %1$.2f");
            data = mxnData;
        }
    };

    /**
     * Peruvian nuevo sol The ISO three-letter code is PEN; the numeric code is 604. It is divided in 100 centimos.
     * 
     * @category currencies
     */
    public static class PENCurrency extends Currency {
        public PENCurrency() {
            Data penData = new Data("Peruvian nuevo sol", "PEN", 604, "S/.", "", 100, new Rounding(), "%3% %1$.2f");
            data = penData;
        }
    };

    /**
     * Peruvian inti The ISO three-letter code was PEI. It was divided in 100 centimos. A numeric code is not available; as per ISO
     * 3166-1, we assign 998 as a user-defined code.
     * 
     * Obsoleted by the nuevo sol since July 1991.
     * 
     * @category currencies
     */
    public static class PEICurrency extends Currency {
        public PEICurrency() {
            Data peiData = new Data("Peruvian inti", "PEI", 998, "I/.", "", 100, new Rounding(), "%3% %1$.2f");
            data = peiData;
        }
    };

    /**
     * Peruvian sol The ISO three-letter code was PEH; A numeric code is not available; as per ISO 3166-1, we assign 999 as a
     * user-defined code. It was divided in 100 centavos.
     * 
     * Obsoleted by the inti since February 1985.
     * 
     * @category currencies
     */
    public static class PEHCurrency extends Currency {
        public PEHCurrency() {
            Data pehData = new Data("Peruvian sol", "PEH", 999, "S./", "", 100, new Rounding(), "%3% %1$.2f");
            data = pehData;
        }
    };

    /**
     * Trinidad & Tobago dollar The ISO three-letter code is TTD; the numeric code is 780. It is divided in 100 cents.
     * 
     * @category currencies
     */
    public static class TTDCurrency extends Currency {
        public TTDCurrency() {
            Data ttdData = new Data("Trinidad & Tobago dollar", "TTD", 780, "TT$", "", 100, new Rounding(), "%3% %1$.2f");
            data = ttdData;
        }
    };

    /**
     * U.S. dollar The ISO three-letter code is USD; the numeric code is 840. It is divided in 100 cents.
     * 
     * @category currencies
     */
    public static class USDCurrency extends Currency {
        public USDCurrency() {
            Data usdData = new Data("U.S. dollar", "USD", 840, "$", "\u00A2", 100, new Rounding(), "%3% %1$.2f");
            data = usdData;
        }
    };

    /**
     * Venezuelan bolivar The ISO three-letter code is VEB; the numeric code is 862. It is divided in 100 centimos.
     * 
     * @category currencies
     */
    public static class VEBCurrency extends Currency {
        public VEBCurrency() {
            Data vebData = new Data("Venezuelan bolivar", "VEB", 862, "Bs", "", 100, new Rounding(), "%3% %1$.2f");
            data = vebData;
        }
    };

}
