package booking.service;

import org.springframework.data.repository.CrudRepository;

import booking.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
