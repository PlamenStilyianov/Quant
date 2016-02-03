package org.jquantlib;


/**
 * helper class to temporarily and safely change the settings
 * @author goovy
 */
public class SavedSettings {

    // PLEASE REVIEW THIS IMPLEMENTATION
    //
    // We cannot rely on finalize() for anything.
    // Method finalize() is called by GC when GC decides it's time for reclaiming memory occupied by this class.
    // This behaviour is neither deterministic nor reliable and may even never happen at all.
    //





    //    private Date evaluationDate_;
    //    private    boolean enforcesTodaysHistoricFixings_;
    //
    //      public SavedSettings(){
    //    	  Date d = Configuration.getSystemConfiguration(null).getGlobalSettings().getEvaluationDate();
    //    	  this.evaluationDate_ = DateFactory.getFactory().getDate(d.getDayOfMonth(), d.getMonth(), d.getYear());
    //    	  enforcesTodaysHistoricFixings_ = Configuration.getSystemConfiguration(null).isEnforcesTodaysHistoricFixings();
    //      }
    //
    //      public void destroy(){
    //    	  Configuration.getSystemConfiguration(null).getGlobalSettings().setEvaluationDate(this.evaluationDate_);
    //    	  Configuration.getSystemConfiguration(null).setEnforcesTodaysHistoricFixings(this.enforcesTodaysHistoricFixings_);
    //      }
    //
    //      @Override
    //      public void finalize(){
    //    	destroy();
    //      }
}

