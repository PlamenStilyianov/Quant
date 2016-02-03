package org.jquantlib.instruments;

import java.io.Serializable;
import java.util.Comparator;

import org.jquantlib.cashflow.CashFlow;

/**
 * @author Ueli Hofstetter
 */
public class EarlierThanCashFlowComparator implements Comparator<CashFlow>, Serializable  {

	/**
	 * Compares its two arguments for order.
	 * Returns a negative integer, zero, or a positive integer as the
	 * first argument is less than, equal to, or greater than the second.
	 */
	@Override
	public int compare(final CashFlow o1, final CashFlow o2) {
		if(o1.date().le(o2.date()))
            return -1;
		if(o2.date().le(o1.date()))
            return 1;
		return 0;
	}


}
