package org.jquantlib.model.marketmodels;


/** 
 * Engine collecting cash flows along a market-model simulation
 * 
 * @author Ueli Hofstetter
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
// TODO: code review :: please verify against QL/C++ code
public class AccountingEngine {
    
    public AccountingEngine(
            final MarketModelEvolver evolver,
            final Object /* const Clone<MarketModelMultiProduct>& product*/ Mark,
            final /*@Real*/ double initialNumeraireValue) {
        
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
    }
    
    
    
//        void multiplePathValues(SequenceStatistics& stats,
//                                Size numberOfPaths);
//      private:
//        Real singlePathValues(std::vector<Real>& values);
//
//        boost::shared_ptr<MarketModelEvolver> evolver_;
//        Clone<MarketModelMultiProduct> product_;
//
//        Real initialNumeraireValue_;
//        Size numberProducts_;
//
//        // workspace
//        std::vector<Real> numerairesHeld_;
//        std::vector<Size> numberCashFlowsThisStep_;
//        std::vector<std::vector<MarketModelMultiProduct::CashFlow> >
//                                                         cashFlowsGenerated_;
//        std::vector<MarketModelDiscounter> discounters_;


}
