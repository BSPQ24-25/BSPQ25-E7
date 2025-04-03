package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Este método permite encontrar un Customer por su ID
    Optional<Customer> findById(Long id);

    // Si quieres agregar más búsquedas por algún atributo, también puedes hacerlo
    Optional<Customer> findByEmail(String email);  // Un ejemplo de búsqueda por email
}
