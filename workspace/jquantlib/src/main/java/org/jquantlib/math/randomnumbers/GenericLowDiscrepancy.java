/*
 Copyright (C) 2007 Richard Gomes

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
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
 Copyright (C) 2004 Ferdinando Ametrano
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2004 Walter Penschke

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

package org.jquantlib.math.randomnumbers;

import java.lang.reflect.Constructor;

import org.jquantlib.QL;
import org.jquantlib.lang.exceptions.LibraryException;


/**
 *
 * @author Richard Gomes
 * @param <T> represents the sample type
 * @param <URSG> represents the UniformRandomSequenceGenerator<T>
 * @param <IC> represents the InverseCumulative
 */
public class GenericLowDiscrepancy<RSG extends UniformRandomSequenceGenerator, IC extends InverseCumulative> {

    //
    // static private fields
    //

    //
    // FIXME:: code review :: it's not clear how should this variable be used.
    // Declared as private final till we discover what's the trick with it.
    //
    static private final boolean allowsErrorEstimate = false;

    //
    // FIXME: QuantLib:: This variable apparently is never initialized!!!
    //
    // The following command
    //
    //       find . -name '*.*pp' -exec fgrep -H -i 'icInstance' {} \;
    //
    // does not return any occurrence of icInstance in the left side of an assignment.
    // So, we declare this variable as private final and initialize with null.
    // This can change as soon as we find what's the trick with it.
    //
    static final private GenericLowDiscrepancy icInstance = null;
    
    
    
    
    private Class<? extends UniformRandomSequenceGenerator>	classRSG;
    private Class<? extends InverseCumulative>				classIC;
    


    protected InverseCumulativeRsg<RSG, IC> makeSequenceGenerator(
    	    final Class<? extends UniformRandomSequenceGenerator>	classRSG,
    	    final Class<? extends InverseCumulative>				classIC,
            final /*@NonNegative*/ int dimension, 
            final /*@NonNegative*/ long seed) {

        QL.validateExperimentalMode();

        this.classRSG = classRSG;
        this.classIC = classIC;
        
        // instantiate a RandomSequenceGenerator given its generic type (first generic parameter)
        final RSG rsg;
        try {
            // obtain RSG Class from first generic parameter
            final Constructor<RSG> c = (Constructor<RSG>) classRSG.getConstructor(int.class, long.class);
            rsg = c.newInstance(dimension, seed);
        } catch (final Exception e) {
            throw new LibraryException(e); // QA:[RG]::verified
        }

        // instantiate a InverseCumulative given its generic type (second generic parameter)
        final IC ic;
        try {
            // obtain IC Class from second generic parameter
            final Constructor<IC> c;
            if (icInstance!=null) {
                c = (Constructor<IC>) classIC.getConstructor(rsg.getClass(), classIC.getClass());
                ic = c.newInstance(rsg, icInstance);
            } else {
                c = (Constructor<IC>) classIC.getConstructor(rsg.getClass());
                ic = c.newInstance(rsg);
            }
        } catch (final Exception e) {
            throw new LibraryException(e); // QA:[RG]::verified
        }
        return (InverseCumulativeRsg<RSG, IC>) ic;
    }

}