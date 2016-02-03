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
package org.jquantlib.math.optimization;

import org.jquantlib.math.matrixutilities.Array;

public class ProjectedCostFunction extends CostFunction {

    private int numberOfFreeParameters;
    private final Array fixedParameters;
    private final Array actualParameters;
    private final boolean [] parametersFreedoms_;
    private final CostFunction costFunction;


    public ProjectedCostFunction(final CostFunction costFunction, final Array parameterValues, final boolean [] parametersFreedoms) {
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
        this.numberOfFreeParameters = 0;
        // TODO: code review :: use of clone()
        this.fixedParameters  = parameterValues.clone();
        this.actualParameters = parameterValues.clone();
        this.parametersFreedoms_ = parametersFreedoms; // TODO: clone() ?
        this.costFunction = costFunction;

        if (fixedParameters.size() != parametersFreedoms_.length)
            throw new IllegalArgumentException("fixedParameters_.size()!=parametersFreedoms_.size()");
        for(int i = 0; i<parametersFreedoms_.length; i++){
            if(!parametersFreedoms_[i]) {
                numberOfFreeParameters++;
            }
            if(!(numberOfFreeParameters>0))
                throw new ArithmeticException("numberOfFreeParameters==0");
        }
    }

    public void mapFreeParameters(final Array parametersValues){
        if(!(parametersValues.size() == numberOfFreeParameters))
            throw new IllegalArgumentException("parametersValues.size()!=numberOfFreeParameters");
        int i = 0;
        for(int j = 0; j<actualParameters.size(); j++)
            if(!parametersFreedoms_[j]) {
                actualParameters.set(j, parametersValues.get(i++));
            }
    }

    @Override
    public double value(final Array freeParameters){
        mapFreeParameters(freeParameters);
        return costFunction.value(actualParameters);
    }

    //FIXME: check Disposable template
    @Override
    public Array values(final Array freeParameters){
        mapFreeParameters(freeParameters);
        return costFunction.values(actualParameters);
    }

    //FIXME: check Disposable template
    public Array project(final Array parameters){
        if(!(parameters.size() == parametersFreedoms_.length))
            throw new ArithmeticException("parameters.size()!=parametersFreedoms_.size()");
        final Array projectedParameters = new Array(numberOfFreeParameters);
        int i = 0;
        for(int j=0; j<parametersFreedoms_.length; j++)
            if(!parametersFreedoms_[j]) {
                projectedParameters.set(i++,parameters.get(j));
            }
        return projectedParameters;
    }

    //FIXME: check Disposable template
    public Array include(final Array projectedParameters){
        if(!(projectedParameters.size() == numberOfFreeParameters))
            throw new IllegalArgumentException("projectedParameters.size()!=numberOfFreeParameters");
        // TODO: code review :: use of clone()
        final Array y = fixedParameters.clone();
        int i = 0;
        for(int j = 0; j<y.size(); j++)
            if(!parametersFreedoms_[j]) {
                y.set(j, projectedParameters.get(i++));
            }
        return y;
    }
}
