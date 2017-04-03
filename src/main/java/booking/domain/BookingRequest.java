package booking.domain;

import java.io.Serializable;
import java.util.Date;

public class BookingRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date checkInDate;

	private Date checkOutDate;

	private RoomType roomType;

	private Customer customer;

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "BookingRequest [checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate + ", roomType="
				+ roomType + ", customer=" + customer + "]";
	}
	
	

}
