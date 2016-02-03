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

public class Europe {

    /**
     * Bulgarian lev The ISO three-letter code is BGL; the numeric code is 100. It is divided in 100 stotinki.
     * 
     * @category currencies
     */
    public static class BGLCurrency extends Currency {
        public BGLCurrency() {
            Data bglData = new Data("Bulgarian lev", "BGL", 100, "lv", "", 100, new Rounding(), "%1$.2f %3%");
            data = bglData;
        }
    };

    /**
     * Belarussian ruble The ISO three-letter code is BYR; the numeric code is 974. It has no subdivisions.
     * 
     * @category currencies
     */
    public static class BYRCurrency extends Currency {
        public BYRCurrency() {
            Data byrData = new Data("Belarussian ruble", "BYR", 974, "BR", "", 1, new Rounding(), "%2% %1$.0f");
            data = byrData;
        }
    };

    /**
     * Swiss franc The ISO three-letter code is CHF; the numeric code is 756. It is divided in 100 cents.
     * 
     * @category currencies
     */
    public static class CHFCurrency extends Currency {
        public CHFCurrency() {
            Data chfData = new Data("Swiss franc", "CHF", 756, "SwF", "", 100, new Rounding(), "%3% %1$.2f");
            data = chfData;
        }
    };

    /**
     * Cyprus pound The ISO three-letter code is CYP; the numeric code is 196. It is divided in 100 cents.
     * 
     * @category currencies
     */
    public static class CYPCurrency extends Currency {
        public CYPCurrency() {
            Data cypData = new Data("Cyprus pound", "CYP", 196, "\\xA3 C", "", 100, new Rounding(), "%3% %1$.2f");
            data = cypData;
        }
    };

    /**
     * Czech koruna The ISO three-letter code is CZK; the numeric code is 203. It is divided in 100 haleru.
     * 
     * @category currencies
     */
    public static class CZKCurrency extends Currency {
        public CZKCurrency() {
            Data czkData = new Data("Czech koruna", "CZK", 203, "Kc", "", 100, new Rounding(), "%1$.2f %3%");
            data = czkData;
        }
    };

    /**
     * Danish krone The ISO three-letter code is DKK; the numeric code is 208. It is divided in 100 �re.
     * 
     * @category currencies
     */
    public static class DKKCurrency extends Currency {
        public DKKCurrency() {
            Data dkkData = new Data("Danish krone", "DKK", 208, "Dkr", "", 100, new Rounding(), "%3% %1$.2f");
            data = dkkData;
        }
    };

    /**
     * Estonian kroon The ISO three-letter code is EEK; the numeric code is 233. It is divided in 100 senti.
     * 
     * @category currencies
     */
    public static class EEKCurrency extends Currency {
        public EEKCurrency() {
            Data eekData = new Data("Estonian kroon", "EEK", 233, "KR", "", 100, new Rounding(), "%1$.2f %2%");
            data = eekData;
        }
    };

    /**
     * European Euro The ISO three-letter code is EUR; the numeric code is 978. It is divided in 100 cents.
     * 
     * @category currencies
     */
    public static class EURCurrency extends Currency {
        public EURCurrency() {
            Data eurData = new Data("European Euro", "EUR", 978, "", "", 100, new Rounding.ClosestRounding(2), "%2% %1$.2f");
            data = eurData;
        }
    };

    /**
     * British pound sterling The ISO three-letter code is GBP; the numeric code is 826. It is divided in 100 pence.
     * 
     * @category currencies
     */
    public static class GBPCurrency extends Currency {
        public GBPCurrency() {
            Data gbpData = new Data("British pound sterling", "GBP", 826, "\\xA3", "p", 100, new Rounding(), "%3% %1$.2f");
            data = gbpData;
        }
    };

    /**
     * Hungarian forint The ISO three-letter code is HUF; the numeric code is 348. It has no subdivisions.
     * 
     * @category currencies
     */
    public static class HUFCurrency extends Currency {
        public HUFCurrency() {
            Data hufData = new Data("Hungarian forint", "HUF", 348, "Ft", "", 1, new Rounding(), "%1$.0f %3%");
            data = hufData;
        }
    };

    /**
     * Icelandic kron The ISO three-letter code is ISK; the numeric code is 352. It is divided in 100 aurar.
     * 
     * @category currencies
     */
    public static class ISKCurrency extends Currency {
        public ISKCurrency() {
            Data iskData = new Data("Iceland krona", "ISK", 352, "IKr", "", 100, new Rounding(), "%1$.2f %3%");
            data = iskData;
        }
    };

    /**
     * Lithuanian litas The ISO three-letter code is LTL; the numeric code is 440. It is divided in 100 centu.
     * 
     * @category currencies
     */
    public static class LTLCurrency extends Currency {
        public LTLCurrency() {
            Data ltlData = new Data("Lithuanian litas", "LTL", 440, "Lt", "", 100, new Rounding(), "%1$.2f %3%");
            data = ltlData;
        }
    };

    /**
     * Latvian lat The ISO three-letter code is LVL; the numeric code is 428. It is divided in 100 santims.
     * 
     * @category currencies
     */
    public static class LVLCurrency extends Currency {
        public LVLCurrency() {
            Data lvlData = new Data("Latvian lat", "LVL", 428, "Ls", "", 100, new Rounding(), "%3% %1$.2f");
            data = lvlData;
        }
    };

    /**
     * Maltese lira The ISO three-letter code is MTL; the numeric code is 470. It is divided in 100 cents.
     * 
     * @category currencies
     */
    public static class MTLCurrency extends Currency {
        public MTLCurrency() {
            Data mtlData = new Data("Maltese lira", "MTL", 470, "Lm", "", 100, new Rounding(), "%3% %1$.2f");
            data = mtlData;
        }
    };

    /**
     * Norwegian krone The ISO three-letter code is NOK; the numeric code is 578. It is divided in 100 �re.
     * 
     * @category currencies
     */
    public static class NOKCurrency extends Currency {
        public NOKCurrency() {
            Data nokData = new Data("Norwegian krone", "NOK", 578, "NKr", "", 100, new Rounding(), "%3% %1$.2f");
            data = nokData;
        }
    };

    /**
     * Polish zloty The ISO three-letter code is PLN; the numeric code is 985. It is divided in 100 groszy.
     * 
     * @category currencies
     */
    public static class PLNCurrency extends Currency {
        public PLNCurrency() {
            Data plnData = new Data("Polish zloty", "PLN", 985, "zl", "", 100, new Rounding(), "%1$.2f %3%");
            data = plnData;
        }
    };

    /**
     * Romanian leu The ISO three-letter code was ROL; the numeric code was 642. It was divided in 100 bani.
     * 
     * Obsoleted by the new leu since July 2005.
     * 
     * @category currencies
     */
    public static class ROLCurrency extends Currency {
        public ROLCurrency() {
            Data rolData = new Data("Romanian leu", "ROL", 642, "L", "", 100, new Rounding(), "%1$.2f %3%");
            data = rolData;
        }
    };

    /**
     * Romanian new leu The ISO three-letter code is RON; the numeric code is 946. It is divided in 100 bani.
     * 
     * @category currencies
     */
    public static class RONCurrency extends Currency {
        public RONCurrency() {
            Data ronData = new Data("Romanian new leu", "RON", 946, "L", "", 100, new Rounding(), "%1$.2f %3%");
            data = ronData;
        }
    };

    /**
     * Swedish krona The ISO three-letter code is SEK; the numeric code is 752. It is divided in 100 �re.
     * 
     * @category currencies
     */
    public static class SEKCurrency extends Currency {
        public SEKCurrency() {
            Data sekData = new Data("Swedish krona", "SEK", 752, "kr", "", 100, new Rounding(), "%1$.2f %3%");
            data = sekData;
        }
    };

    /**
     * Slovenian tolar The ISO three-letter code is SIT; the numeric code is 705. It is divided in 100 stotinov.
     * 
     * @category currencies
     */
    public static class SITCurrency extends Currency {
        public SITCurrency() {
            Data sitData = new Data("Slovenian tolar", "SIT", 705, "SlT", "", 100, new Rounding(), "%1$.2f %3%");
            data = sitData;
        }
    };

    /**
     * Slovak koruna The ISO three-letter code is SKK; the numeric code is 703. It is divided in 100 halierov.
     * 
     * @category currencies
     */
    public static class SKKCurrency extends Currency {
        public SKKCurrency() {
            Data skkData = new Data("Slovak koruna", "SKK", 703, "Sk", "", 100, new Rounding(), "%1$.2f %3%");
            data = skkData;
        }
    };

    /**
     * Turkish lira The ISO three-letter code was TRL; the numeric code was 792. It was divided in 100 kurus.
     * 
     * Obsoleted by the new Turkish lira since 2005..
     * 
     * @category currencies
     */
    public static class TRLCurrency extends Currency {
        public TRLCurrency() {
            Data trlData = new Data("Turkish lira", "TRL", 792, "TL", "", 100, new Rounding(), "%1$.0f %3%");
            data = trlData;
        }
    };

    /**
     * New Turkish lira The ISO three-letter code is TRY; the numeric code is 949. It is divided in 100 kurus.
     * 
     * @category currencies
     */
    public static class TRYCurrency extends Currency {
        public TRYCurrency() {
            Data tryData = new Data("New Turkish lira", "TRY", 949, "YTL", "", 100, new Rounding(), "%1$.2f %3%");
            data = tryData;
        }
    };

    // currencies obsoleted by Euro

    /**
     * Austrian shilling The ISO three-letter code was ATS; the numeric code was 40.It was divided in 100 groschen. Obsoleted by the
     * Euro since 1999
     * 
     * @category currencies
     */
    public static class ATSCurrency extends Currency {
        public ATSCurrency() {
            Data atsData = new Data("Austrian shilling", "ATS", 40, "", "", 100, new Rounding(), "%2% %1$.2f", new EURCurrency());
            data = atsData;
        }
    };

    /**
     * Belgian franc The ISO three-letter code was BEF; the numeric code was 56.It had no subdivisions. Obsoleted by the Euro since
     * 1999
     * 
     * @category currencies
     */
    public static class BEFCurrency extends Currency {
        public BEFCurrency() {
            Data befData = new Data("Belgian franc", "BEF", 56, "", "", 1, new Rounding(), "%2% %1$.0f", new EURCurrency());
            data = befData;
        }
    };

    /**
     * Deutsche mark The ISO three-letter code was DEM; the numeric code was 276.It was divided in 100 pfennig. Obsoleted by the
     * Euro since 1999
     * 
     * @category currencies
     */
    public static class DEMCurrency extends Currency {
        public DEMCurrency() {
            Data demData = new Data("Deutsche mark", "DEM", 276, "DM", "", 100, new Rounding(), "%1$.2f %3%", new EURCurrency());
            data = demData;
        }
    };

    /**
     * Spanish peseta The ISO three-letter code was ESP; the numeric code was 724.It was divided in 100 centimos. Obsoleted by the
     * Euro since 1999
     * 
     * @category currencies
     */
    public static class ESPCurrency extends Currency {
        public ESPCurrency() {
            Data espData = new Data("Spanish peseta", "ESP", 724, "Pta", "", 100, new Rounding(), "%1$.0f %3%", new EURCurrency());
            data = espData;
        }
    };

    /**
     * Finnish markka The ISO three-letter code was FIM; the numeric code was 246.It was divided in 100 penni�. Obsoleted by the
     * Euro since 1999
     * 
     * @category currencies
     */
    public static class FIMCurrency extends Currency {
        public FIMCurrency() {
            Data fimData = new Data("Finnish markka", "FIM", 246, "mk", "", 100, new Rounding(), "%1$.2f %3%", new EURCurrency());
            data = fimData;
        }
    };

    /**
     * French franc The ISO three-letter code was FRF; the numeric code was 250.It was divided in 100 centimes. Obsoleted by the
     * Euro since 1999
     * 
     * @category currencies
     */
    public static class FRFCurrency extends Currency {
        public FRFCurrency() {
            Data frfData = new Data("French franc", "FRF", 250, "", "", 100, new Rounding(), "%1$.2f %2%", new EURCurrency());
            data = frfData;
        }
    };

    /**
     * Greek drachma The ISO three-letter code was GRD; the numeric code was 300.It was divided in 100 lepta. Obsoleted by the Euro
     * since 1999
     * 
     * @category currencies
     */
    public static class GRDCurrency extends Currency {
        public GRDCurrency() {
            Data grdData = new Data("Greek drachma", "GRD", 300, "", "", 100, new Rounding(), "%1$.2f %2%", new EURCurrency());
            data = grdData;
        }
    };

    /**
     * Irish punt The ISO three-letter code was IEP; the numeric code was 372.It was divided in 100 pence. Obsoleted by the Euro
     * since 1999
     * 
     * @category currencies
     */
    public static class IEPCurrency extends Currency {
        public IEPCurrency() {
            Data iepData = new Data("Irish punt", "IEP", 372, "", "", 100, new Rounding(), "%2% %1$.2f", new EURCurrency());
            data = iepData;
        }
    };

    /**
     * Italian lira The ISO three-letter code was ITL; the numeric code was 380. It had no subdivisions. Obsoleted by the Euro since
     * 1999
     * 
     * @category currencies
     */
    public static class ITLCurrency extends Currency {
        public ITLCurrency() {
            Data itlData = new Data("Italian lira", "ITL", 380, "L", "", 1, new Rounding(), "%3% %1$.0f", new EURCurrency());
            data = itlData;
        }
    };

    /**
     * Luxembourg franc The ISO three-letter code was LUF; the numeric code was 442.It was divided in 100 centimes.. Obsoleted by
     * the Euro since 1999
     * 
     * @category currencies
     */
    public static class LUFCurrency extends Currency {
        public LUFCurrency() {
            Data lufData = new Data("Luxembourg franc", "LUF", 442, "F", "", 100, new Rounding(), "%1$.0f %3%", new EURCurrency());
            data = lufData;
        }
    };

    // ! Dutch guilder
    /*
     * ! The ISO three-letter code was NLG; the numeric code was 528. It was divided in 100 cents.
     * 
     * Obsoleted by the Euro since 1999.
     * 
     * \ingroup currencies
     */
    /**
     * Dutch guilder The ISO three-letter code was NLG; the numeric code was 528.It was divided in 100 cents. Obsoleted by the Euro
     * since 1999
     * 
     * @category currencies
     */
    public static class NLGCurrency extends Currency {
        public NLGCurrency() {
            Data nlgData = new Data("Dutch guilder", "NLG", 528, "f", "", 100, new Rounding(), "%3% %1$.2f", new EURCurrency());
            data = nlgData;
        }
    };

    /**
     * Portuguese escudo The ISO three-letter code was PTE; the numeric code was 620.It was divided in 100 centavos. Obsoleted by
     * the Euro since 1999
     * 
     * @category currencies
     */
    public static class PTECurrency extends Currency {
        public PTECurrency() {
            Data pteData = new Data("Portuguese escudo", "PTE", 620, "Esc", "", 100, new Rounding(), "%1$.0f %3%",
                    new EURCurrency());
            data = pteData;
        }
    };

}
