/*
 Copyright (C) 2008 Richard Gomes

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

package org.jquantlib.methods.montecarlo;

/**
 * Weighted sample
 * 
 * @author Dominik Holenstein
 * @author Richard Gomes
 * 
 * @param <T>
 */

//TODO: Add JavaDocs
//TODO: Code review

// Sample<Double>
// Sample<Array>
// Sample<Path>
// Sample<MultiPath>

public final class Sample<T> {
    
    //
    // private fields
    //

    /**
     * This field represents the value held by this Sample.
     */
    //XXX This field has public read access via getter but can be written by friend classes (same package)
    private T value;

    /**
     * This field represents the weight held by this Sample.
     */
    //XXX This field has public read access via getter but can be written by friend classes (same package)
    private double weight;
    

    //
    // public constructors
    //
    
    public Sample(final T value, double weight) {
        if (System.getProperty("EXPERIMENTAL")==null) throw new UnsupportedOperationException("Work in progress");
        this.value=value;
        this.weight=weight;
    }

    
    //
    // public getters
    //
    
    public T value() {
        return value;
    }

    public double weight() {
        return weight;
    }

    
    //
    // package private setters
    //
    
    
    /*@PackagePrivate*/ void setValue(T value) {
        this.value = value;
    }

    /*@PackagePrivate*/ void setWeight(double weight) {
        this.weight = weight;
    }

}
