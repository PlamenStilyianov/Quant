/*
 Copyright (C) 2007 Richard Gomes

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
 Copyright (C) 2006 StatPro Italia srl

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

// ===========================================================================
// NOTE: The following copyright notice applies to the original code,
//
// Copyright (C) 2002 Peter J�ckel "Monte Carlo Methods in Finance".
// All rights reserved.
//
// Permission to use, copy, modify, and distribute this software is freely
// granted, provided that this notice is preserved.
// ===========================================================================
package org.jquantlib.methods.montecarlo;

import java.util.Arrays;

import org.jquantlib.time.TimeGrid;

/**
 * Builds Wiener process paths using Gaussian variates
 * <p>
 * This class generates normalized (i.e., unit-variance) paths as sequences of variations. In order to obtain the actual path of the
 * underlying, the returned variations must be multiplied by the integrated variance (including time) over the corresponding time
 * step.
 *
 * @author Richard Gomes
 */
public class BrownianBridge {

    private final /* @NonNegative */int size_;
    private final /* @Time */double[] t_;
    private final /* @Real */double[] sqrtdt_;
    private final /* @NonNegative */int[] bridgeIndex_, leftIndex_, rightIndex_;
    private final /* @Real */double[] leftWeight_, rightWeight_, stdDev_;

    /**
     * unit-time path
     *
     * @param steps
     */
    public BrownianBridge(final/* @NonNegative */int steps) {
        if (System.getProperty("EXPERIMENTAL")==null)
            throw new UnsupportedOperationException("Work in progress");
        this.size_ = steps;
        this.t_ = new double[this.size_];
        this.sqrtdt_ = new double[this.size_];

        this.bridgeIndex_ = new int[this.size_];
        this.leftIndex_ = new int[this.size_];
        this.rightIndex_ = new int[this.size_];

        this.leftWeight_ = new double[this.size_];
        this.rightWeight_ = new double[this.size_];
        this.stdDev_ = new double[this.size_];

        for (int i = 0; i < size_; ++i) {
            t_[i] = /* @Time */ (i + 1);
        }
        initialize();
    }

    /**
     * generic times
     *
     * @note the starting time of the path is assumed to be 0 and must not be included
     *
     * @param times
     */
    public BrownianBridge(final/* @Time */double[] times) {
        this.size_ = times.length;
        this.t_ = Arrays.copyOfRange(times, 0, this.size_);
        this.sqrtdt_ = new double[this.size_];

        this.bridgeIndex_ = new int[this.size_];
        this.leftIndex_ = new int[this.size_];
        this.rightIndex_ = new int[this.size_];

        this.leftWeight_ = new double[this.size_];
        this.rightWeight_ = new double[this.size_];
        this.stdDev_ = new double[this.size_];

        initialize();
    }

    /**
     * generic times
     *
     * @param timeGrid
     */
    public BrownianBridge(final TimeGrid timeGrid) {
        this.size_ = timeGrid.size() - 1;
        this.t_ = new double[this.size_];
        this.sqrtdt_ = new double[this.size_];

        this.bridgeIndex_ = new int[this.size_];
        this.leftIndex_ = new int[this.size_];
        this.rightIndex_ = new int[this.size_];

        this.leftWeight_ = new double[this.size_];
        this.rightWeight_ = new double[this.size_];
        this.stdDev_ = new double[this.size_];

        for (int i = 0; i < size_; ++i) {
            t_[i] = timeGrid.get(i + 1);
        }
        initialize();
    }

    private void initialize() {

        sqrtdt_[0] = Math.sqrt(t_[0]);
        for (int i = 1; i < size_; ++i) {
            sqrtdt_[i] = Math.sqrt(t_[i] - t_[i - 1]);
        }

        // map is used to indicate which points are already constructed.
        // If map[i] is zero, path point i is yet unconstructed.
        // map[i]-1 is the index of the variate that constructs
        // the path point # i.
        final int[] map = new int[size_];
        Arrays.fill(map, 0);

        // The first point in the construction is the global step.
        map[size_ - 1] = 1;
        // The global step is constructed from the first variate.
        bridgeIndex_[0] = size_ - 1;
        // The variance of the global step
        stdDev_[0] = Math.sqrt(t_[size_ - 1]);
        // The global step to the last point in time is special.
        leftWeight_[0] = rightWeight_[0] = 0.0;
        for (int j = 0, i = 1; i < size_; ++i) {
            // Find the next unpopulated entry in the map.
            while (map[j] != 0) {
                ++j;
            }
            int k = j;
            // Find the next populated entry in the map from there.
            while (map[k] == 0) {
                ++k;
            }
            // l-1 is now the index of the point to be constructed next.
            final int l = j + ((k - 1 - j) >> 1);
            map[l] = i;
            // The i-th Gaussian variate will be used to set point l-1.
            bridgeIndex_[i] = l;
            leftIndex_[i] = j;
            rightIndex_[i] = k;
            if (j != 0) {
                leftWeight_[i] = (t_[k] - t_[l]) / (t_[k] - t_[j - 1]);
                rightWeight_[i] = (t_[l] - t_[j - 1]) / (t_[k] - t_[j - 1]);
                stdDev_[i] = Math.sqrt(((t_[l] - t_[j - 1]) * (t_[k] - t_[l])) / (t_[k] - t_[j - 1]));
            } else {
                leftWeight_[i] = (t_[k] - t_[l]) / t_[k];
                rightWeight_[i] = t_[l] / t_[k];
                stdDev_[i] = Math.sqrt(t_[l] * (t_[k] - t_[l]) / t_[k]);
            }
            j = k + 1;
            if (j >= size_) {
                j = 0; // wrap around
            }
        }
    }

    public/* @NonNegative */int size() /* @ReadOnly */{
        return size_;
    }

    public final/* @Time */double[] times() /* @ReadOnly */{
        return t_;
    }

    /**
     * Brownian-bridge constructor
     */
    //
    //TODO: Improve this method.
    // This method in particular presents a very weak interface which is
    // potentially risky for critical systems due to the possibility of failure.
    // The point is that we have possibilities of NullPointerException and
    // ArrayIndexOutOfBoundsException which can be easily avoided.
    // -- Richard Gomes
    //
    public void transform(final double[] input, final double[] output) /* @ReadOnly */{
        if (input == null || input.length == 0)
            throw new IllegalArgumentException("invalid sequence");
        if (input.length != size_)
            throw new IllegalArgumentException("incompatible sequence size");
        // We use output to store the path...
        output[size_ - 1] = stdDev_[0] * input[0];
        for (int i = 1; i < size_; ++i) {
            final int j = leftIndex_[i];
            final int k = rightIndex_[i];
            final int l = bridgeIndex_[i];
            if (j != 0) {
                output[l] = leftWeight_[i] * output[j - 1] + rightWeight_[i] * output[k] + stdDev_[i] * input[i];
            } else {
                output[l] = rightWeight_[i] * output[k] + stdDev_[i] * input[i];
            }
        }
        // ...after which, we calculate the variations and
        // normalize to unit times
        for (int i = size_ - 1; i >= 1; --i) {
            output[i] -= output[i - 1];
            output[i] /= sqrtdt_[i];
        }
        output[0] /= sqrtdt_[0];
    }

}
