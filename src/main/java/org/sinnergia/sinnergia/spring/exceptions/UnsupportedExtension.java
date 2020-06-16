package org.sinnergia.sinnergia.spring.exceptions;

public class UnsupportedExtension extends RuntimeException {
    private static final String DESCRIPTION = "Unsupported Extension (415)";

    public UnsupportedExtension(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
