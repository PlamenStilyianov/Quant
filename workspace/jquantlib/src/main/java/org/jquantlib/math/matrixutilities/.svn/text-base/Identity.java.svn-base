package org.jquantlib.math.matrixutilities;

import java.util.EnumSet;
import java.util.Set;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.math.matrixutilities.internal.Address;

/**
 * Identity Matrix
 *
 * @author Richard Gomes
 */
@QualityAssurance(quality = Quality.Q1_TRANSLATION, version = Version.OTHER, reviewers = { "Richard Gomes" })
public class Identity extends Matrix {

    /**
     * Creates an identity matrix
     *
     * @return An m-by-n matrix with ones on the diagonal and zeros elsewhere.
     */
    public Identity(final int dim) {
        this(dim, EnumSet.noneOf(Address.Flags.class));
    }


    /**
     * Default constructor
     * <p>
     * Builds a Matrix with dimensions 1x1
     *
     * @param flags is a <code>Set&lt;Address.Flags&gt;</code>
     *
     * @see Address.Flags
     */
    public Identity(final int dim, final Set<Address.Flags> flags) {
        super(dim, dim, flags);
        final int offset = addr.isFortran() ? 1 : 0;
        for (int i = offset; i < dim+offset; i++) {
            this.set(i, i, 1.0);
        }
    }


}
