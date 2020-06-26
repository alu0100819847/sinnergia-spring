package org.sinnergia.sinnergia.spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class UnsupportedExtension extends RuntimeException {
    private static final String DESCRIPTION = "Unsupported Extension (415)";

    public UnsupportedExtension(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}


