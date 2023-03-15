package com.apostolisich.api.hotelio.dao.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="booking")
public class Booking {
	
	@Id
	@Column(name="booking_reference")
	private String bookingReference;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	@Column(name="creation_date")
	private LocalDateTime creationDate;
	
	@Column(name="amount")
	private BigDecimal amount;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="hotel_name")
	private String hotelName;
	
	@Column(name="check_in")
	private String checkIn;
	
	@Column(name="check_out")
	private String checkOut;
	
	@Column(name="room_description")
	private String roomDescription;
	
	@OneToMany(mappedBy="booking")
	private List<Guest> guests;
	
	@OneToOne(mappedBy="booking")
	private ContactDetails contactDetails;
	
	@OneToOne(mappedBy="booking")
	private Payment payment;
	
	public Booking() { }

	public Booking(String bookingReference, LocalDateTime creationDate, BigDecimal amount, String currency, String hotelName, String checkIn,
				   String checkOut, String roomDescription, List<Guest> guests, ContactDetails contactDetails, Payment payment) {
		this.bookingReference = bookingReference;
		this.creationDate = creationDate;
		this.amount = amount;
		this.currency = currency;
		this.hotelName = hotelName;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.roomDescription = roomDescription;
		setGuests(guests);
		setContactDetails(contactDetails);
		setPayment(payment);
	}

	public String getBookingReference() {
		return bookingReference;
	}

	public void setBookingReference(String bookingReference) {
		this.bookingReference = bookingReference;
	}

	public LocalDateTime getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}

	public String getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public List<Guest> getGuests() {
		return guests;
	}

	public void setGuests(List<Guest> guests) {
		guests.forEach( guest -> guest.setBooking(this));
		this.guests = guests;
	}

	public ContactDetails getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(ContactDetails contactDetails) {
		this.contactDetails = contactDetails;
		contactDetails.setBooking(this);
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		payment.setBooking(this);
		this.payment = payment;
	}
	
}
