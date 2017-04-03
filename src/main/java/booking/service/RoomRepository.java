package booking.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;

import booking.domain.Room;
import booking.domain.RoomType;

@RepositoryRestResource(collectionResourceRel = "rooms", path = "rooms")
public interface RoomRepository extends CrudRepository<Room, Long> {

	List<Room> findByRoomType(@Param("roomType") RoomType roomType);

	@Query("select count(r) from Room r inner join r.bookings b where r.roomNumber = ?1 and b.checkOutDate > ?2 and b.checkInDate < ?3")
	Long getTotalReservationByRoomNumberAndDateRange(@Param("roomNumber") Long roomNumber,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @Param("checkInDate") Date checkInDate,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @Param("checkOutDate") Date checkOutDate);

}
