/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package booking.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import booking.domain.Booking;
import booking.domain.BookingRequest;
import booking.domain.Customer;
import booking.domain.Room;
import booking.service.BookingRepository;
import booking.service.CustomerRepository;
import booking.service.RoomRepository;

@RestController
public class BookingController {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * Create a new booking, sets up the customer if not found
	 * @param bookingRequest containing the booking details
	 * @return booking confirmation details
	 */
	@RequestMapping(path = "/booking/create", method = RequestMethod.POST)
	public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest bookingRequest) {
		
		if (!bookingRequest.getCheckInDate().before(bookingRequest.getCheckOutDate())) {
			throw new IllegalArgumentException("The check in date must be before the check out date");
		}
		
		Customer customer = bookingRequest.getCustomer();
		if (customer.getId() == null) {
			//save new customer
			customer = customerRepository.save(customer);
		} else {
			//lookup customer by id
			customer = customerRepository.findOne(customer.getId());
		}

		//get all rooms matching room type
		List<Room> rooms = roomRepository.findByRoomType(bookingRequest.getRoomType());

		boolean isBooked = false;
		for (Room room : rooms) {
			boolean noReservationsFound = (0 == roomRepository.getTotalReservationByRoomNumberAndDateRange(
					room.getRoomNumber(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate()));

			if (noReservationsFound && !isBooked) {
				// create new room booking
				Booking booking = new Booking();
				booking.setCheckInDate(bookingRequest.getCheckInDate());
				booking.setCheckOutDate(bookingRequest.getCheckOutDate());
				booking.setRoom(room);
				booking.setCustomer(customer);
				return new ResponseEntity<Booking>(bookingRepository.save(booking), HttpStatus.CREATED);
			}
		}

		//return empty booking
		return ResponseEntity.noContent().build();

	}
}
