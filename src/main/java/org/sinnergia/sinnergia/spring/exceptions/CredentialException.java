package org.sinnergia.sinnergia.spring.exceptions;

public class CredentialException extends RuntimeException {
    private static final String DESCRIPTION ="Credential Exception (401)";

    public CredentialException(String detail){
        super(DESCRIPTION + ". " + detail);
    }
}
