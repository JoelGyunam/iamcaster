package com.iamcaster.regional.userregion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iamcaster.regional.userregion.domain.UserRegion;

public interface UserRegionJpaRepository extends JpaRepository<UserRegion, Integer> {
	

}
