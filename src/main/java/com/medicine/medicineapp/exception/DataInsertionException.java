package com.medicine.medicineapp.exception;

public class DataInsertionException extends RuntimeException {

    public DataInsertionException()
    {
        super();
    }

    public DataInsertionException(String message)
    {
        super(message);
    }

    public DataInsertionException(String message, Throwable innerException)
    {
        super(message,innerException);
    }

    public DataInsertionException(Throwable innerException)
    {
        super(innerException);
    }
    
}
