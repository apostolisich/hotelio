package com.apostolisich.api.hotelio.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.apostolisich.api.hotelio.dao.entity.Booking;
import com.apostolisich.api.hotelio.dao.entity.ContactDetails;
import com.apostolisich.api.hotelio.dao.entity.Guest;
import com.apostolisich.api.hotelio.dao.entity.Payment;
import com.apostolisich.api.hotelio.hotelbooking.CreateHotelBookingRequest;
import com.apostolisich.api.hotelio.hotelbooking.CreateHotelBookingResponse;

@Repository
@Transactional
public class HotelBookingDAO {
	
	private EntityManager entityManager;
	
	@Autowired
	public HotelBookingDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * Searches the database for a booking with the given booking reference and then
	 * returns it.
	 * 
	 * @param bookingReference the booking reference for which the booking details will
	 * 						   be returned
	 * @return the {@code Booking} object
	 */
	public Booking getHotelBooking(String bookingReference) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		//TODO Add proper handling for cases that the booking reference isn't linked to a booking
		Booking booking = currentSession.get(Booking.class, bookingReference);
		
		return booking;
	}
	
	/**
	 * Creates a new booking based on the selected offer and stores all its details
	 * in the database.
	 * 
	 * @param hotelBookingRequest the {@code CreateHotelBookingRequest} for the current
	 * 					          booking.
	 * @return an {@code CreateHotelBookingResponse} which contains the created booking
	 * 		   reference
	 */
	public CreateHotelBookingResponse createNewBooking(BigDecimal amount, String currency, String hotelName, String checkIn, String checkOut, String roomDescription, CreateHotelBookingRequest hotelBookingRequest) {
		String newBookingReference = getRandomBookingReference();
		Booking newBooking = new Booking(newBookingReference, LocalDateTime.now(), amount, currency, hotelName, checkIn, checkOut, roomDescription);
	
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.save(newBooking);
		
		//Setting the created booking reference to all depended table entities and then
		//saving them as well
		Payment payment = hotelBookingRequest.getPayment();
		payment.setBooking(newBooking);
		currentSession.save(payment);
		
		ContactDetails contactDetails = hotelBookingRequest.getContactDetails();
		contactDetails.setBooking(newBooking);
		currentSession.save(contactDetails);
		
		List<Guest> guests = hotelBookingRequest.getGuests();
		guests.forEach( guest -> {
			guest.setBooking(newBooking);
			currentSession.save(guest);
		});
		
		
		return new CreateHotelBookingResponse(newBookingReference, true);
	}
	
	/**
	 * Creates a random booking reference which will consist of lower and upper characters
	 * along with digits.
	 * 
	 * @return the created booking reference
	 */
	private String getRandomBookingReference() {
        char[] alphanumericCharacters ="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        int bookingReferenceLength = 10;
 
        StringBuilder random = new StringBuilder();
        for(int i =0; i < bookingReferenceLength; i++) {
            int index = (int) (Math.random() * alphanumericCharacters.length);
            random.append(alphanumericCharacters[index]);
        }
        
        return random.toString();
    }
	
}
