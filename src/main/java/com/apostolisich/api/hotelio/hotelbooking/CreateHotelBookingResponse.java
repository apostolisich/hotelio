package com.apostolisich.api.hotelio.hotelbooking;

/**
 * A class that represents the response to the CreateHotelBooking request.
 * It includes the created booking reference for the booking which can
 * then be used in order to retrieve the details of the created booking.
 */
public class CreateHotelBookingResponse {
	
	private String bookingReference;
	private boolean success;
	
	public CreateHotelBookingResponse(String bookingReference, boolean success) {
		this.bookingReference = bookingReference;
		this.success = success;
	}

	public String getBookingReference() {
		return bookingReference;
	}
	
	public void setBookingReference(String bookingReference) {
		this.bookingReference = bookingReference;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
