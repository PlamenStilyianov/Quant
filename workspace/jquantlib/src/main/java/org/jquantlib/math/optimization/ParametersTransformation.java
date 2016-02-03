
package org.jquantlib.math.optimization;

import org.jquantlib.math.matrixutilities.Array;

public interface ParametersTransformation {
	public Array direct(Array x);
	public Array inverse(Array x);
}
