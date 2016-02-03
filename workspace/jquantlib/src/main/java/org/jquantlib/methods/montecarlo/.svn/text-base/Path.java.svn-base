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
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2003, 2004, 2005, 2006 StatPro Italia srl
 Copyright (C) 2003 Ferdinando Ametrano

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

package org.jquantlib.methods.montecarlo;

import org.jquantlib.time.TimeGrid;

/**
 * Single-factor random walk
 *
 * @note The path includes the initial asset value as its first point.
 *
 * @author Richard Gomes
 */

// FIXME: code review: verify if DoubleReference should be used here

public class Path {

    //
    // private fields
    //

    /**
     * This field represents the {@link TimeGrid} held by this Path.
     * <p>
     * This field can has public read access via getter
     * but can only be written by friend classes (same package)
     */
    private TimeGrid timeGrid_;

    /**
     * This field represents contains a double[] held by this Path.
     * <p>
     * This field can has public read access via getter
     * but can only be written by friend classes (same package)
     */
    private double[] values_;


    //
    // public getters
    //

    public TimeGrid getTimeGrid_() {
        return timeGrid_;
    }

    public double[] getValues_() {
        return values_;
    }

    public double getValues_(final int i) {
        return values_[i];
    }

    //
    // package private setters
    //

    /*@PackagePrivate*/ void setTimeGrid_(final TimeGrid timeGrid_) {
        this.timeGrid_ = timeGrid_;
    }

    /*@PackagePrivate*/ void setValues_(final double[] values) {
        this.values_ = values;
    }

    /*@PackagePrivate*/ void setValues_(final int i, final double value) {
        this.values_[i] = value;
    }


    //
    // public constructors
    //

    public Path(final TimeGrid timeGrid) {
        this(timeGrid, null);
    }

    public Path(final TimeGrid timeGrid, final double[] values) {
        if (System.getProperty("EXPERIMENTAL")==null)
            throw new UnsupportedOperationException("Work in progress");
        this.timeGrid_ = timeGrid;
        if (values == null || values.length == 0) {
            values_ = new double[timeGrid_.size()];
        } else {
            this.values_ = values; // TODO: clone() ?
        }
        if (values_.length != timeGrid_.size())
            throw new IllegalArgumentException("different number of times and asset values"); // FIXME: message
    }


    //
    // public methods
    //

    public boolean empty() /* @ReadOnly */{
        return timeGrid_.empty();
    }

    public/* @NonNegative */int length() /* @ReadOnly */{
        return timeGrid_.size();
    }

    public/* @Time */double time(/* @NonNegative */final int i) /* @ReadOnly */{
        return timeGrid_.get(i);
    }

//XXX
//    public final TimeGrid timeGrid() /* @ReadOnly */{
//        return timeGrid_;
//    }

//XXX
//    public DoubleForwardIterator forwardIterator() /* @ReadOnly */{
//        return values_.forwardIterator();
//    }
//
//    public DoubleReverseIterator reverseIterator() /* @ReadOnly */{
//        return values_.reverseIterator();
//    }


//
//XXX
//
//    //
//    // read-only versions of get, at, value, front and back
//    //
//
//    public/* @Real */double get(/* @NonNegative */int i) /* @ReadOnly */{
//        return values_.get(i);
//    }
//
//    public/* @Real */double at(/* @NonNegative */int i) /* @ReadOnly */{
//        return values_.at(i);
//    }
//
//    public/* @Real */double value(/* @NonNegative */int i) /* @ReadOnly */{
//        return values_.get(i);
//    }
//
//    public/* @Real */double front() /* @ReadOnly */{
//        return values_.get(0);
//    }
//
//    public/* @Real */double back() /* @ReadOnly */{
//        return values_.get(values_.size() - 1);
//    }
//
//
//
//    //
//    // read-write versions of get, at, value, front and back
//    //
//
//    public DoubleReference getReference(/*@NonNegative*/ int i) {
//        return values_.getReference(i);
//    }
//
//    public DoubleReference atReference(/*@NonNegative*/ int i) {
//        return values_.atReference(i);
//    }
//
//    public DoubleReference valueReference(/*@NonNegative*/ int i) {
//        return values_.getReference(i);
//    }
//
//    public DoubleReference frontReference() {
//        return values_.frontReference();
//    }
//
//    public DoubleReference backReference() {
//        return values_.backReference();
//    }
//
}
