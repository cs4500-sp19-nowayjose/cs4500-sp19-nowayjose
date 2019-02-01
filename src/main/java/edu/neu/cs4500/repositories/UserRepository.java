package edu.neu.cs4500.repositories;

import edu.neu.cs4500.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query(value="SELECT user FROM User user")
    public List<User> findAllUsers();
    @Query(value="SELECT user FROM User user WHERE user.id=:id")
    public User findUserById(@Param("id") Integer id);
    @Query(value="SELECT user FROM User user WHERE user.username=:username")
    public User findByUsername(@Param("username") String username);
}