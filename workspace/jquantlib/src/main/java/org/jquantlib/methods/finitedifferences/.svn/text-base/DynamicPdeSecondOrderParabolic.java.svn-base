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

/**
 * @author Srinivas Hasti
 */
import java.lang.reflect.Proxy;

import org.jquantlib.lang.reflect.DynamicProxyInvocationHandler;

public class DynamicPdeSecondOrderParabolic extends PdeSecondOrderParabolic {

	private Pde pde;

	private DynamicPdeSecondOrderParabolic(Pde pde) {
		this.pde = pde;
	}

	@Override
	public double diffusion(double t, double x) {
		return pde.diffusion(t, x);
	}

	@Override
	public double discount(double t, double x) {
		return pde.discount(t, x);
	}

	@Override
	public double drift(double t, double x) {
		return pde.drift(t, x);
	}

	public static <T> DynamicPdeSecondOrderParabolic getInstance(T process) {
		Pde parabolic = (Pde) Proxy.newProxyInstance(
				Pde.class.getClassLoader(),
				new Class[] { Pde.class },
				new DynamicProxyInvocationHandler<T>(process));
		return new DynamicPdeSecondOrderParabolic(parabolic);
	}

}
