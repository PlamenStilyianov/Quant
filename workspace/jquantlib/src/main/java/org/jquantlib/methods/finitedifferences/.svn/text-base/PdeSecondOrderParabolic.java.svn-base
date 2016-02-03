/*
 Copyright (C) 2008 Srinivas Hasti

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

package org.jquantlib.methods.finitedifferences;

import org.jquantlib.math.TransformedGrid;
import org.jquantlib.math.matrixutilities.Array;

/**
 * @author Srinivas Hasti
 * 
 */
public abstract class PdeSecondOrderParabolic implements Pde {
    
    public void generateOperator(/* Time */double t, TransformedGrid tg, TridiagonalOperator L) {
        for (int i = 1; i < tg.size() - 1; i++) {
            double sigma = diffusion(t, tg.grid(i));
            double nu = drift(t, tg.grid(i));
            double r = discount(t, tg.grid(i));
            double sigma2 = sigma * sigma;

            double pd = -(sigma2 / tg.dxm(i) - nu) / tg.dx(i);
            double pu = -(sigma2 / tg.dxp(i) + nu) / tg.dx(i);
            double pm = sigma2 / (tg.dxm(i) * tg.dxp(i)) + r;
            L.setMidRow(i, pd, pm, pu);
        }
    }
    
    public TransformedGrid applyGridType(Array a){ return null;}
        
}
