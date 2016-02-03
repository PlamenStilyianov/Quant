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

public class Oceania {
    

    /**
     * Australian dollar
     * The ISO three-letter code is AUD; the numeric code is 36. It is divided in 100 cents.
     * @category currencies
     */
    public static class AUDCurrency extends  Currency {
      public AUDCurrency() {
            Data audData=new Data("Australian dollar", "AUD", 36,
                                               "A$", "", 100,
                                               new Rounding(),
                                               "%3% %1$.2f");
            data = audData;
        }
    };

    /**
     * New Zealand dollar
     * The ISO three-letter code is NZD; the numeric code is 554. It is divided in 100 cents.
     * @category currencies
     */
    public static class NZDCurrency extends Currency {
      public NZDCurrency() {
            Data nzdData=new Data("New Zealand dollar", "NZD", 554,
                                             "NZ$", "", 100,
                                             new Rounding(),
                                             "%3% %1$.2f");
            data = nzdData;
        }
    };

}
