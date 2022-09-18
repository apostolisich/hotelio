package com.apostolisich.api.hotelio.hotelbooking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apostolisich.api.hotelio.dao.HotelBookingDAO;
import com.apostolisich.api.hotelio.dao.entity.Booking;

@Service
public class GetHotelBookingService {
	
	private HotelBookingDAO hotelBookingDAO;
	
	@Autowired
	public GetHotelBookingService(HotelBookingDAO hotelBookingDAO) {
		this.hotelBookingDAO = hotelBookingDAO;
	}
	
	public Booking retrieveBooking(String bookingReference) {
		return hotelBookingDAO.getHotelBooking(bookingReference);
	}

}
