/*
 Copyright (C) 2008  Q. Boiler

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

package org.jquantlib.experimental;


import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import org.joda.primitives.list.impl.ArrayDoubleList;

/**
 *
 * @author Q. Boiler
 */
public class StrategyLoadingList implements java.util.List {
	private java.util.List wrappedList;
	enum TYPE { LIST, DOUBLE_ARRAY_LIST};
	private TYPE type = TYPE.LIST;
	PrimativeList strategy = new DefaultPrimativeListStrategy();
	
	public StrategyLoadingList(java.util.List list){
		if(list instanceof ArrayDoubleList){
			type=TYPE.DOUBLE_ARRAY_LIST;
			strategy = new ListDoubleArrayListStrategy();
			((ListDoubleArrayListStrategy)strategy).dal
				=(ArrayDoubleList)list;
			
		}else{
			type=TYPE.LIST;
			((DefaultPrimativeListStrategy)strategy).pwl=(java.util.List)list;
		}
		wrappedList = list;
	}

	@Override
	public int size() {
		return wrappedList.size();
	}

	@Override
	public boolean isEmpty() {
		return wrappedList.isEmpty();
	}

	@Override
	public boolean contains(Object arg0) {
		return wrappedList.contains(arg0);
	}

	@Override
	public Iterator iterator() {
		return wrappedList.iterator();
	}

	@Override
	public Object[] toArray() {
		//  TODO  This should be one of the sophisticated ones....
		return wrappedList.toArray();
	}

	@Override
	public Object[] toArray(Object[] arg0) {
		//  TODO This should be very sophisticated.
		return wrappedList.toArray();
	}

	@Override
	public boolean add(Object arg0) {
		return wrappedList.add(arg0);
	}
	public boolean add(double d){
		return strategy.add(d);
	}
	public boolean add(int d){
		return strategy.add(d);
	}

	@Override
	public boolean remove(Object arg0) {
		return wrappedList.remove(arg0);
	}

	@Override
	public boolean containsAll(Collection arg0) {
		return wrappedList.containsAll(arg0);
	}

	@Override
	public boolean addAll(Collection arg0) {
		return wrappedList.addAll(arg0);
	}

	@Override
	public boolean addAll(int arg0, Collection arg1) {
		return wrappedList.addAll(arg0,arg1);
	}

	@Override
	public boolean removeAll(Collection arg0) {
		return wrappedList.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection arg0) {
		return wrappedList.retainAll(arg0);
	}

	@Override
	public void clear() {
		wrappedList.clear();
	}

	@Override
	public Object get(int arg0) {
		return wrappedList.get(arg0);
	}

	@Override
	public Object set(int arg0, Object arg1) {
		return wrappedList.set(arg0, arg1);
	}

	@Override
	public void add(int arg0, Object arg1) {
		wrappedList.add(arg0, arg1);
	}

	@Override
	public Object remove(int arg0) {
		return wrappedList.remove(arg0);
	}

	@Override
	public int indexOf(Object arg0) {
		return wrappedList.indexOf(arg0);
	}

	@Override
	public int lastIndexOf(Object arg0) {
		return wrappedList.lastIndexOf(arg0);
	}

	@Override
	public ListIterator listIterator() {
		return wrappedList.listIterator();
	}

	@Override
	public ListIterator listIterator(int arg0) {
		return wrappedList.listIterator();
	}

	@Override
	public java.util.List subList(int arg0, int arg1) {
		return wrappedList.subList(arg0,arg1);
	}
	

}
