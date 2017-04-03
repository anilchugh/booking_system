package booking;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import booking.domain.BookingRequest;
import booking.domain.Customer;
import booking.domain.RoomType;

/**
 * Integration test to run the application.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("scratch")
// Separate profile for web tests to avoid clashing databases
public class BookingApplicationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}


	@Test
	public void testFindBookingsByCustomerId() throws Exception {

		this.mvc.perform(get("/api/bookings/search/findByCustomerId?customerId=1")).andExpect(status().isOk())
				.andExpect(jsonPath("_embedded.bookings", hasSize(3)));
	}
	
	@Test
	public void testFindBookingsByRoomId() throws Exception {

		this.mvc.perform(get("/api/bookings/search/findByRoomId?roomId=1")).andExpect(status().isOk())
				.andExpect(jsonPath("_embedded.bookings", hasSize(2)));
	}

	@Test
	public void testGetTotalReservationByRoomNumberAndDateRangeSuccess() throws Exception {

		this.mvc.perform(
				get("/api/rooms/search/getTotalReservationByRoomNumberAndDateRange?roomNumber=101&checkInDate=2017-01-05&checkOutDate=2017-01-10"))
				.andExpect(status().isOk()).andExpect(content().string("0"));
	}

	@Test
	public void testGetTotalReservationByRoomNumberAndDateRangeWithRoomBooked() throws Exception {

		this.mvc.perform(
				get("/api/rooms/search/getTotalReservationByRoomNumberAndDateRange?roomNumber=101&checkInDate=2016-12-31&checkOutDate=2017-01-06"))
				.andExpect(content().string("1"));
	}

	@Test
	public void testBookingCreateWithExistingCustomerSuccess() throws Exception {

		Customer customer = new Customer();
		customer.setId(1L);

		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setCheckInDate(new SimpleDateFormat("yyyy-MM-dd").parse("2016-31-12"));
		bookingRequest.setCheckOutDate(new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-06"));
		bookingRequest.setRoomType(RoomType.SINGLE);
		bookingRequest.setCustomer(customer);

		this.mvc.perform(post("/booking/create").content(new ObjectMapper().writeValueAsBytes(bookingRequest))
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(content().string(notNullValue()));
	}

	@Test
	public void testBookingCreateWithNewCustomerSuccess() throws Exception {

		Customer customer = new Customer();
		customer.setForename("Peter");
		customer.setSurname("Wallace");
		customer.setAddress("5/13 Hawkhead Close, Edinburgh EH7 5FG");

		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setCheckInDate(new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-10"));
		bookingRequest.setCheckOutDate(new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-16"));
		bookingRequest.setRoomType(RoomType.DOUBLE);
		bookingRequest.setCustomer(customer);

		this.mvc.perform(post("/booking/create").content(new ObjectMapper().writeValueAsBytes(bookingRequest))
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(content().string(notNullValue()));
	}

}
