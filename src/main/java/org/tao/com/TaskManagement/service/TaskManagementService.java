package org.tao.com.TaskManagement.service;

import org.tao.com.TaskManagement.dto.TaskResponse;
import org.tao.com.TaskManagement.exception.ResourceNotFoundException;
import org.tao.com.TaskManagement.model.Tasks;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TaskManagementService {

    TaskResponse createTask (Tasks tasks) throws ResourceNotFoundException;

    TaskResponse updateTask (Tasks tasks,long taskId) throws ResourceNotFoundException;

    Map< String, Boolean > deleteTask (long taskId) throws ResourceNotFoundException;

    List<TaskResponse> getAllTasks ();

    TaskResponse assignTask (long userId, long taskId) throws ResourceNotFoundException;

    List<TaskResponse> getUserAssignedTasks (long userId) throws ResourceNotFoundException;

    TaskResponse updateTaskProgress (long progress, long taskId) throws ResourceNotFoundException;

    List<TaskResponse> getOverdueTasks();

    List<TaskResponse> getTaskByStatus(String status);

    List<TaskResponse> getTasksByGivenDateRange(String startDate, String endDate);

    Map<String,Long> getCompletedTasksStatistics();


}
