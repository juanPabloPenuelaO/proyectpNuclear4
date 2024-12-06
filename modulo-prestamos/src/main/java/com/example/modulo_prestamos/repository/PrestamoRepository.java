package com.example.modulo_prestamos.repository;

import com.example.modulo_prestamos.entity.Prestamo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepository extends MongoRepository<Prestamo, String> {
}