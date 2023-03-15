package com.apostolisich.api.hotelio.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.apostolisich.api.hotelio.dao.entity.Booking;
import com.apostolisich.api.hotelio.dao.entity.ContactDetails;
import com.apostolisich.api.hotelio.dao.entity.Guest;
import com.apostolisich.api.hotelio.dao.entity.Payment;
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
	 * @param amount the total cost of the booking
	 * @param currency the amount currency
	 * @param hotelName the name of the hotel
	 * @param checkIn the check-in date
	 * @param checkOut the check-out date
	 * @param roomDescription a brief description of the room
	 * @param guests a {@code List} of {@code Guest} object containing the details of all
	 *               the guests
	 * @param contactDetails a {@code ContactDetails} object that includes all the contact
	 *                       details
	 * @param payment a {@code Payment} object that includes all the payment details
	 * @return an {@code CreateHotelBookingResponse} which contains the created booking
	 * 		   reference
	 */
	public CreateHotelBookingResponse createNewBooking(BigDecimal amount, String currency, String hotelName, String checkIn, String checkOut,
													  String roomDescription, List<Guest> guests, ContactDetails contactDetails, Payment payment) {
		String newBookingReference = getRandomBookingReference();
		Booking newBooking = new Booking(newBookingReference, LocalDateTime.now(), amount, currency, hotelName, checkIn, checkOut, roomDescription, guests, contactDetails, payment);
	
		Session currentSession = entityManager.unwrap(Session.class);
		
		//Setting the created booking reference to all depended table entities and then
		//saving them as well. The "newBooking" variable will be saved automatically.
		payment.setBooking(newBooking);
		currentSession.save(payment);
		
		contactDetails.setBooking(newBooking);
		currentSession.save(contactDetails);
		
		guests.forEach( guest -> {
			guest.setBooking(newBooking);
			currentSession.save(guest);
		});
		
		return new CreateHotelBookingResponse(newBookingReference, true);
	}
	
	/**
	 * Creates a random booking reference which will consist of uppercase characters
	 * along with digits.
	 * 
	 * @return the created booking reference
	 */
	private String getRandomBookingReference() {
        char[] alphanumericCharacters ="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        int bookingReferenceLength = 10;
 
        StringBuilder random = new StringBuilder();
        for(int i = 0; i < bookingReferenceLength; i++) {
            int index = (int) (Math.random() * alphanumericCharacters.length);
            random.append(alphanumericCharacters[index]);
        }
        
        return random.toString();
    }
	
}
