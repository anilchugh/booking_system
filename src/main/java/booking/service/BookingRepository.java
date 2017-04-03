package booking.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import booking.domain.Booking;

@RepositoryRestResource(collectionResourceRel = "bookings", path = "bookings")
public interface BookingRepository extends CrudRepository<Booking, Long> {
	List<Booking> findByCustomerId(@Param("customerId") Long customerId);
	
	List<Booking> findByRoomId(@Param("roomId") Long roomId);

}
