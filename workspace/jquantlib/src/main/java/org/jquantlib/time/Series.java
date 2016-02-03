/*
 Copyright (C) 2008 Srinivas Hasti
 Copyright (C) 2010 Ricahrd Gomes

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

package org.jquantlib.time;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jquantlib.QL;

/**
 * Container for historical data
 * <p>
 * This class acts as a generic repository for a set of historical data.
 * Any single datum can be accessed through its date, while
 * sets of consecutive data can be accessed through iterators.
 *
 * @see TimeSeriesDouble
 *
 * @author Srinivas Hasti
 * @author Richard Gomes
 */
public class Series<K,V> implements NavigableMap<K,V> {

    private static final String UNSUPPORTED_KEY_OBJECT   = "only Long and Date are acceptable as key objects";
    private static final String UNSUPPORTED_VALUE_OBJECT = "only Double and IntervalPrice are acceptable as value objects";

    //
    // private fields
    //

    private final NavigableMap<K,V> delegate;

    private final Class<K> classK;
    private final Class<V> classV;


    //
    // public constructors
    //

    public Series(final Class<K> classK, final Class<V> classV) {
		QL.require(classK!=null, "Generic type <K> is null");
		QL.require(classV!=null, "Generic type <V> is null");

		this.classK = classK;
		this.classV = classV;
		
        this.delegate = new TreeMap<K,V>();
    }

    
    //
    // implements NavigableMap<K,V>
    //

    @Override
	public java.util.Map.Entry<K, V> ceilingEntry(final K key) {
        return delegate.ceilingEntry(key);
    }


    @Override
	public K ceilingKey(final K key) {
        return delegate.ceilingKey(key);
    }


    @Override
	public void clear() {
        delegate.clear();
    }


    @Override
	public Comparator<? super K> comparator() {
        return delegate.comparator();
    }


    @Override
	public boolean containsKey(final Object key) {
        return delegate.containsKey(key);
    }


    @Override
	public boolean containsValue(final Object value) {
        return delegate.containsValue(value);
    }


    @Override
	public NavigableSet<K> descendingKeySet() {
        return delegate.descendingKeySet();
    }


    @Override
	public NavigableMap<K, V> descendingMap() {
        return delegate.descendingMap();
    }


    @Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
        return delegate.entrySet();
    }


    @Override
    public boolean equals(final Object o) {
        return delegate.equals(o);
    }


    @Override
	public java.util.Map.Entry<K, V> firstEntry() {
        return delegate.firstEntry();
    }


    @Override
	public K firstKey() {
        return delegate.firstKey();
    }


    @Override
	public java.util.Map.Entry<K, V> floorEntry(final K key) {
        return delegate.floorEntry(key);
    }


    @Override
	public K floorKey(final K key) {
        return delegate.floorKey(key);
    }


    @Override
	public V get(final Object key) {
        return delegate.get(key);
    }


    @Override
    public int hashCode() {
        return delegate.hashCode();
    }


    @Override
	public NavigableMap<K, V> headMap(final K toKey, final boolean inclusive) {
        return delegate.headMap(toKey, inclusive);
    }


    @Override
	public SortedMap<K, V> headMap(final K toKey) {
        return delegate.headMap(toKey);
    }


    @Override
	public java.util.Map.Entry<K, V> higherEntry(final K key) {
        return delegate.higherEntry(key);
    }


    @Override
	public K higherKey(final K key) {
        return delegate.higherKey(key);
    }


    @Override
	public boolean isEmpty() {
        return delegate.isEmpty();
    }


    @Override
	public Set<K> keySet() {
        return delegate.keySet();
    }


    @Override
	public java.util.Map.Entry<K, V> lastEntry() {
        return delegate.lastEntry();
    }


    @Override
	public K lastKey() {
        return delegate.lastKey();
    }


    @Override
	public java.util.Map.Entry<K, V> lowerEntry(final K key) {
        return delegate.lowerEntry(key);
    }


    @Override
	public K lowerKey(final K key) {
        return delegate.lowerKey(key);
    }


    @Override
	public NavigableSet<K> navigableKeySet() {
        return delegate.navigableKeySet();
    }


    @Override
	public java.util.Map.Entry<K, V> pollFirstEntry() {
        return delegate.pollFirstEntry();
    }


    @Override
	public java.util.Map.Entry<K, V> pollLastEntry() {
        return delegate.pollLastEntry();
    }


    @Override
	public V put(final K key, final V value) {
        return delegate.put(key, value);
    }


    @Override
	public void putAll(final Map<? extends K, ? extends V> m) {
        delegate.putAll(m);
    }


    @Override
	public V remove(final Object key) {
        return delegate.remove(key);
    }


    @Override
	public int size() {
        return delegate.size();
    }


    @Override
	public NavigableMap<K, V> subMap(final K fromKey, final boolean fromInclusive, final K toKey, final boolean toInclusive) {
        return delegate.subMap(fromKey, fromInclusive, toKey, toInclusive);
    }


    @Override
	public SortedMap<K, V> subMap(final K fromKey, final K toKey) {
        return delegate.subMap(fromKey, toKey);
    }


    @Override
	public NavigableMap<K, V> tailMap(final K fromKey, final boolean inclusive) {
        return delegate.tailMap(fromKey, inclusive);
    }


    @Override
	public SortedMap<K, V> tailMap(final K fromKey) {
        return delegate.tailMap(fromKey);
    }


    @Override
	public Collection<V> values() {
        return delegate.values();
    }


}
