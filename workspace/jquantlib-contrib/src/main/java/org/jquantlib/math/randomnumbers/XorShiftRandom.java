package org.jquantlib.math.randomnumbers;

import java.util.Random;

/**
 * An unbelievably fast, high-quality pseudorandom number generator suggested by George Marsaglia
 * in <a href="http://www.jstatsoft.org/v08/i14/paper/">&ldquo;Xorshift RNGs&rdquo;</a>,
 * <i>Journal of Statistical Software</i>, 8:1&minus;6, 2003.
 * Calls to {@link #nextLong()} will be one order of magnitude faster than {@link Random}'s.
 * <p>
 * This class extends {@link Random}, overriding (as usual) the {@link Random#next(int)} method. Nonetheless,
 * since the generator is inherently 64-bit also {@link Random#nextLong()} and {@link Random#nextDouble()}
 * have been overridden for speed (preserving, of course, {@link Random}'s semantics).
 */
public class XorShiftRandom extends Random {
	private static final long serialVersionUID = 1L;

	/** The internal state (and last returned value) of the algorithm. */
	private long x;

	public XorShiftRandom() {
		this( new Random().nextLong() );
	}

	public XorShiftRandom( final long seed ) {
		x = seed;
	}

	@Override
	protected int next( int bits ) {
		return (int)( nextLong() >>> ( 64 - bits ) );
	}

	@Override
	public long nextLong() {
		x ^= x << 13;
		x ^= x >>> 7;
		return x ^= ( x << 17 );
	}
	
	@Override
	 public double nextDouble() {
		return ( nextLong() >>> 11 ) / (double) ( 1L << 53 );
	}
	
	public static void main( String arg[] ) {
		final int n = Integer.parseInt( arg[ 0 ] );
		final Random xorShiftRandom = new XorShiftRandom();
		long x = 0;
		final long time = - System.currentTimeMillis();
		for( int k = n; k-- != 0; ) x |= xorShiftRandom.nextLong();
		System.err.println( 1000000.0 * ( time + System.currentTimeMillis() ) / n + " ns/gen" );
		System.err.println( x ); // To avoid excision.
	}
}
