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
package org.jquantlib.lang.reflect;

import java.lang.reflect.Method;

/**
 * @author Srinivas Hasti
 * 
 */
//TODO: add comments and explain what this class is about

// FIXME: Remove code which employs reflection [ Richard Gomes ]

public class DynamicProxyInvocationHandler<T> implements java.lang.reflect.InvocationHandler {

    private final T delegate;

    public DynamicProxyInvocationHandler(final T obj) {
        this.delegate = obj;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        return  delegate.getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(delegate, args);
       
    }

}
