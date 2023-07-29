package org.tao.com.TaskManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tao.com.TaskManagement.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

}
