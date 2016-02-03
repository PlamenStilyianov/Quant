package org.jquantlib.samples;

import org.jquantlib.QL;

public class Swap implements Runnable {

    public static void main(final String[] args) {
        new Swap().run();
    }

    @Override
    public void run() {
        QL.validateExperimentalMode();
    }

}
