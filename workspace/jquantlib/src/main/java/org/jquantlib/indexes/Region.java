/*
 Copyright (C) 2011 Tim Blackler

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

package org.jquantlib.indexes;


/**
 * 
 * Region, i.e. geographical area, specification
 * used for inflation applicability
 * 
 * @author Tim Blackler
 */
public abstract class Region {

	protected final class Data {
		 private final String name;
		 private final String code;
		 
		 public Data(String name, String code) {
			 this.name = name;
			 this.code = code;
		 }
		 
		 public String name() {
			 return name;
		 }
		 
		 public String code() {
			 return code;
		 }
	}
	
	protected Data data;
	
	public String name() {
		return data.name();
	}

	public String code() {
		return data.code();
	}

	public static boolean eq(final Region r1, final Region r2) {
		return r1.name().equals(r2.name());
	}


	public static boolean ne(final Region r1, final Region r2) {
		return !(r1.name().equals(r2.name()));
	}

	
}