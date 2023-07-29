package org.tao.com.TaskManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tao.com.TaskManagement.model.Tasks;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskManagementRepository extends JpaRepository<Tasks, Long> {

    Optional<List<Tasks>> findAllByUserId(long userId);

    @Query(value ="select tasks.* from TASKS tasks, USERS users where users.ID=?1 and tasks.ID=?2 ", nativeQuery = true)
    Optional<Tasks>  findTasksByUserIdAndTaskId(@Param("userId") Long userId,@Param("taskId") Long taskId);


    @Query(value = "from Tasks task where task.status =:status")
    List<Tasks> getAllTasksByStatus(String status);

    @Query(value = "from Tasks task where task.dueDate BETWEEN :startDate AND :endDate")
    public List<Tasks> getAllTasksByDateRange(@Param("startDate") String startDate, @Param("endDate")String endDate);

    @Query(value =  "select count(*) from Tasks task where task.status =:status")
    long getCompltedTaskCount(@Param("status") String status);
}
