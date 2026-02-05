package com.ruben.cementerio.repository;

import com.ruben.cementerio.entity.ImplementacionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImplementacionServiceRepository extends JpaRepository<ImplementacionService, Integer> {
}
