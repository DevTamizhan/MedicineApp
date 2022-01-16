package com.medicine.medicineapp.exception;

public class NotAuthorizedException extends RuntimeException {
    
    public NotAuthorizedException()
    {
        super();
    }

    public NotAuthorizedException(String message)
    {
        super(message);
    }

    public NotAuthorizedException(String message, Throwable innerException)
    {
        super(message,innerException);
    }

    public NotAuthorizedException(Throwable innerException)
    {
        super(innerException);
    }

}
