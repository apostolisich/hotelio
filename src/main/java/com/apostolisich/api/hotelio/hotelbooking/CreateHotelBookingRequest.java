package com.apostolisich.api.hotelio.hotelbooking;

import java.util.List;

import com.apostolisich.api.hotelio.dao.entity.ContactDetails;
import com.apostolisich.api.hotelio.dao.entity.Guest;
import com.apostolisich.api.hotelio.dao.entity.Payment;

/**
 * A class that represent the CreateHotelBooking request which can be used in order
 * to create a booking for a selected hotel offer.
 * 
 * For simplicity, it also uses the Hibernate entities for Passenger, ContactDetails
 * and Payment because the vast majority of the fields is the same, in order to avoid
 * declaring the classes here again.
 */
@SuppressWarnings("unused")
public class CreateHotelBookingRequest {
	
	private String provider;
	private String offerId;
	private List<Guest> guests;
	private ContactDetails contactDetails;
	private Payment payment;
	
	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getOfferId() {
		return offerId;
	}

	private void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public List<Guest> getGuests() {
		return guests;
	}

	public void setGuests(List<Guest> guests) {
		this.guests = guests;
	}

	public ContactDetails getContactDetails() {
		return contactDetails;
	}

	private void setContactDetails(ContactDetails contactDetails) {
		this.contactDetails = contactDetails;
	}

	public Payment getPayment() {
		return payment;
	}

	private void setPayment(Payment payment) {
		this.payment = payment;
	}
	
}
