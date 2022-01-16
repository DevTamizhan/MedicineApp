package com.medicine.medicineapp.exception;

public class DataNotFoundException extends RuntimeException {
    
    public DataNotFoundException()
    {
        super();
    }

    public DataNotFoundException(String message)
    {
        super(message);
    }

    public DataNotFoundException(String message, Throwable innerException)
    {
        super(message,innerException);
    }

    public DataNotFoundException(Throwable innerException)
    {
        super(innerException);
    }
    
}
