/*
 Copyright (C) 2009 Richard Gomes

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
package org.jquantlib;

import org.slf4j.Logger;


/**
 * This class allows global definitions
 *
 * @author Richard Gomes
 */
public class JQuantLib {

    /* @PackagePrivate*/ static Logger logger;

    /**
     * This method injects a org.slf4j.Logger into JQuantLib.
     * <p>
     * JQuantLib is dependent on SLF4J interface but not dependent on any logging implementation, which means that JQuantLib
     * delegates to the calling application code the responsability of choosing whatever implementation of SLF4J it is more
     * convenient, if any. If no Logger is injected, all messages are simply sent to System.err.
     *
     * @note that JQuantLib <i>never</i> synchronizes any access to the Logger.
     */
    public final static void setLogger(final Logger logger) {
        JQuantLib.logger = logger;
    }

}
