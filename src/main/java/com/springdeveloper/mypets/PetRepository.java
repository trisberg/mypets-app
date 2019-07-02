package com.springdeveloper.mypets;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface PetRepository extends PagingAndSortingRepository<Pet, Long> {

	List<Pet> findByName(@Param("name") String name);

}
