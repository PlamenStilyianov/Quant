/*
 Copyright (C) 2008 Srinivas Hasti

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
package org.jquantlib.methods.finitedifferences;

import java.util.List;
import java.util.Vector;

import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.matrixutilities.Array;

/**
 * @author Srinivas Hasti
 *
 */
// TODO: Code review
//Using Type token to dynamically create instance of MixedScheme requires
//a extension class, so making this class abstract to force a type hierarchy

public abstract class ParallelEvolver<S extends Operator, T extends MixedScheme<S>>  {
	
    private final List<T> evolvers;
    
    private final Class<? extends Operator>		classS; 
    private final Class<? extends MixedScheme>	classT; 

    public ParallelEvolver(
    	    final Class<? extends Operator>		classS, 
    	    final Class<? extends MixedScheme>	classT, 
    		final List<S> L,
            final BoundaryConditionSet<BoundaryCondition<S>> bcs) {
    	this.classS = classS;
    	this.classT = classT;
        evolvers = new Vector<T>(L.size());
        for (int i = 0; i < L.size(); i++)
            evolvers.add(getEvolver(L.get(i), bcs.get(i)));
    }

    public List<Array> step(final List<Array> a, final double t) {
        for (int i = 0; i < evolvers.size(); i++)
            a.set(i, evolvers.get(i).step(a.get(i), t));

        return a;
    }

    public void setStep(final double dt) {
        for (int i = 0; i < evolvers.size(); i++)
            evolvers.get(i).setStep(dt);
    }

    protected T getEvolver(final S l, final List<BoundaryCondition<S>> bcs) {
        try {

            return (T) classT.getConstructor(Operator.class, List.class).newInstance(l, bcs);
        } catch (final Exception e) {
            throw new LibraryException(e); // QA:[RG]::verified
        }
    }

}
