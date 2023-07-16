package com.example.lovelypet.exception;

public class ServiceHistoryException extends BaseException {
    public ServiceHistoryException(String code) {
        super("booking." + code);
    }

    //CREATE
    public static ServiceHistoryException createBookingIdNull() {
        return new ServiceHistoryException("create.booking.id.null");
    }

    public static ServiceHistoryException createHotelIdNull() {
        return new ServiceHistoryException("create.hotel.id.null");
    }

    public static ServiceHistoryException createUserIdNull() {
        return new ServiceHistoryException("create.user.id.null");
    }


}
