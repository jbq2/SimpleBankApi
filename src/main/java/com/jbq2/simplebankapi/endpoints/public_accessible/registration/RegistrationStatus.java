package com.jbq2.simplebankapi.endpoints.public_accessible.registration;

/* describes registration success/errors */
public enum RegistrationStatus {
    SUCCESS,
    FAIL_BAD_EMAIL,
    FAIL_BAD_PASSWORD,
    FAIL_BAD_MATCH,
    FAIL_EMAIL_EXISTS,
    FAIL_BAD_ROLE_SAVE
}
