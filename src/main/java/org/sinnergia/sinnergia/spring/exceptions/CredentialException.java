package org.sinnergia.sinnergia.spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CredentialException extends RuntimeException {
    private static final String DESCRIPTION ="Credential Exception (401)";

    public CredentialException(String detail){
        super(DESCRIPTION + ". " + detail);
    }

}
