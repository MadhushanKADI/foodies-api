package com.group16.api.Repository;

// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.group16.api.Entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    
    // Custom SQL query to fetch users by email
    @Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true)
    User getUsersByEmail(@Param("email") String email);
}
