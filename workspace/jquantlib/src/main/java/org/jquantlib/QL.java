/*
 Copyright (C) 2010 Richard Gomes

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
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2003, 2004, 2005, 2006, 2007 StatPro Italia srl

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

package org.jquantlib;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.jquantlib.lang.exceptions.LibraryException;

/**
 * Static methods for validation and emiting log messages
 *
 * @author Richard Gomes
 * @author Srinivas Hasti
 */
public class QL {

    /**
     * Throws an error if a <b>pre-condition</b> is not verified
     * <p>
     * @param condition is a condition to be verified
     * @param message is a message emitted.
     * @throws a LibraryException if the condition is not met
     */
    public static void require(
            final boolean condition,
            final String format,
            final Object...objects) throws RuntimeException {
        if (!condition)
            throw new LibraryException(String.format(format, objects));
    }

    /**
     * Throws an error if a <b>pre-condition</b> is not verified
     * <p>
     * @param condition is a condition to be verified
     * @param message is a message emitted.
     * @throws a LibraryException if the condition is not met
     */
    public static void require(
            final boolean condition,
            final String message) throws RuntimeException {
        if (!condition)
            throw new LibraryException(message);
    }

    /**
     * Throws an error if a <b>pre-condition</b> is not verified
     * <p>
     * @param condition is a condition to be verified
     * @param klass is a Class which extends RuntimeException
     * @param message is a message emitted.
     * @throws a LibraryException if the condition is not met
     */
    public static void require(
            final boolean condition,
            final Class<? extends RuntimeException> klass,
            final String message) throws RuntimeException {
        if (!condition) {
            try {
                final Constructor<? extends RuntimeException> c = klass.getConstructor(String.class);
                throw c.newInstance(message);
            } catch (final SecurityException e) {
                e.printStackTrace();
            } catch (final NoSuchMethodException e) {
                e.printStackTrace();
            } catch (final IllegalArgumentException e) {
                e.printStackTrace();
            } catch (final InstantiationException e) {
                e.printStackTrace();
            } catch (final IllegalAccessException e) {
                e.printStackTrace();
            } catch (final InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }



    
    public static void ensure(
            final boolean condition,
            final String format,
            final Object...objects) throws RuntimeException {
        if (!condition)
            throw new LibraryException(String.format(format, objects));
    }

    
    /**
     * Throws an error if a <b>post-condition</b> is not verified
     * <p>
     * @note  this method should <b>never</b> be removed from bytecode by AspectJ.
     *        If you do so, you must be plenty sure of effects and risks of this decision.
     * <p>
     * @param condition is a condition to be verified
     * @param message is a message emitted.
     * @throws a LibraryException if the condition is not met
     */
    public static void ensure(
            final boolean condition,
            final String message) throws RuntimeException {
        if (!condition)
            throw new LibraryException(message);
    }



    //=========================================================
    //
    //                   A T T E N T I O N
    //
    //            QL.fail() was removed on purpose.
    //
    //         Please throw LibraryException instead.
    //
    //=========================================================





    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void error(final String message) {
        if (JQuantLib.logger!=null) {
            JQuantLib.logger.error(message);
        } else {
            System.err.printf("ERROR: %s\n", message);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void error(final String message, final Throwable t) {
        if (JQuantLib.logger!=null) {
            JQuantLib.logger.error(message, t);
        } else {
            System.err.printf("ERROR: %s : %s\n", message, t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void error(final Throwable t) {
        if (JQuantLib.logger!=null) {
            JQuantLib.logger.error(t.getMessage(), t);
        } else {
            System.err.printf("ERROR: %s\n", t.getMessage());
            System.err.println(t.getMessage());
            t.printStackTrace(System.err);
        }
    }




    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void warn(final String message) {
        if (JQuantLib.logger!=null) {
            JQuantLib.logger.warn(message);
        } else {
            System.err.printf("WARN: %s\n", message);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void warn(final String message, final Throwable t) {
        if (JQuantLib.logger!=null) {
            JQuantLib.logger.warn(message, t);
        } else {
            System.err.printf("WARN: %s : %s\n", message, t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void warn(final Throwable t) {
        if (JQuantLib.logger!=null) {
            JQuantLib.logger.warn(t.getMessage(), t);
        } else {
            System.err.printf("WARN: %s\n", t.getMessage());
            System.err.println(t.getMessage());
            t.printStackTrace(System.err);
        }
    }




    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void info(final String message) {
        if (JQuantLib.logger!=null) {
            JQuantLib.logger.info(message);
        } else {
            System.err.printf("INFO: %s\n", message);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void info(final String message, final Throwable t) {
        if (JQuantLib.logger!=null) {
            JQuantLib.logger.info(message, t);
        } else {
            System.err.printf("INFO: %s : %s\n", message, t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void info(final Throwable t) {
        if (JQuantLib.logger!=null) {
            JQuantLib.logger.info(t.getMessage(), t);
        } else {
            System.err.printf("INFO: %s\n", t.getMessage());
            System.err.println(t.getMessage());
            t.printStackTrace(System.err);
        }
    }




    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void debug(final String message) {
        if (JQuantLib.logger!=null) {
            JQuantLib.logger.debug(message);
        } else {
            System.err.printf("DEBUG: %s\n", message);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void debug(final String message, final Throwable t) {
        if (JQuantLib.logger!=null) {
            JQuantLib.logger.debug(message, t);
        } else {
            System.err.printf("DEBUG: %s : %s\n", message, t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void debug(final Throwable t) {
        if (JQuantLib.logger!=null) {
            JQuantLib.logger.debug(t.getMessage(), t);
        } else {
            System.err.printf("DEBUG: %s\n", t.getMessage());
            System.err.println(t.getMessage());
            t.printStackTrace(System.err);
        }
    }



    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void trace(final String message) {
        if (JQuantLib.logger!=null) {
            JQuantLib.logger.trace(message);
        } else {
            System.err.printf("TRACE: %s\n", message);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void trace(final String message, final Throwable t) {
        if (JQuantLib.logger!=null) {
            JQuantLib.logger.trace(message, t);
        } else {
            System.err.printf("TRACE: %s : %s\n", message, t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void trace(final Throwable t) {
        if (JQuantLib.logger!=null) {
            JQuantLib.logger.trace(t.getMessage(), t);
        } else {
            System.err.printf("TRACE: %s\n", t.getMessage());
            System.err.println(t.getMessage());
            t.printStackTrace(System.err);
        }
    }



    /**
     * This method to validate whether code is being run in
     * experimental mode or not
     */
    public static void validateExperimentalMode() {
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
    }

}
