package oshiru.springboot_template.exception;

/** Feature: Retry Request */
public class OshiruRetryableException extends OshiruException {

    public OshiruRetryableException(String message) {
        super(message);
    }
}
