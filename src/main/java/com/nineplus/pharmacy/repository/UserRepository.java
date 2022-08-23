package com.nineplus.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nineplus.pharmacy.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query("select u from Users u where u.userName = :userName")
	User findByUserName(@Param("userName")String userName);

}
