package com.dmrl.storeverything.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * User repository
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.login = :login")
    User findByLogin(@Param("login") String login);

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    User findByUserId(Long id);

    @Query("SELECT u FROM User u WHERE u.id <> ?1")
    List<User> findAllUserOtherThanThis(Long id);

    @Query("UPDATE User u SET u.firstName = :newFirstName, u.lastName = :newLastName WHERE u.id = :selectedId")
    void updateUserName(@Param("newFirstName") String newFirstName,
                        @Param("newLastName") String newLastName,
                        @Param("selectedId") Long selectedId
    );

}
