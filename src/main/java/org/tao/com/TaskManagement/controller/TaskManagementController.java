package org.tao.com.TaskManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tao.com.TaskManagement.dto.TaskResponse;
import org.tao.com.TaskManagement.exception.ResourceNotFoundException;
import org.tao.com.TaskManagement.model.Tasks;
import org.tao.com.TaskManagement.service.TaskManagementService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@EnableScheduling
@RestController
@RequestMapping("/api")
@Validated
public class TaskManagementController {

    @Autowired
    private TaskManagementService taskManagementService;

    @PostMapping(value ="/tasks")
    public TaskResponse createTask(@Valid @RequestBody Tasks tasks) throws ResourceNotFoundException {
        return taskManagementService.createTask(tasks);
    }

    @PutMapping("/tasks/{taskId}")
    public TaskResponse updateTask(@Valid @RequestBody Tasks tasks, @PathVariable(value = "taskId") long taskId) throws ResourceNotFoundException {
        return taskManagementService.updateTask(tasks, taskId);
    }

    @DeleteMapping("/tasks/{taskId}")
    public Map< String, Boolean > deleteTask(@PathVariable(value = "taskId") Long taskId ) throws ResourceNotFoundException {
         return taskManagementService.deleteTask(taskId);
    }

    @GetMapping("/tasks")
    public List< TaskResponse > getAllTasks( ) {
        return taskManagementService.getAllTasks();
    }

    @PostMapping("/tasks/{taskId}/assign")
    public TaskResponse assignTask(@RequestParam int userId, @PathVariable(value = "taskId") long taskId ) throws ResourceNotFoundException {
        return taskManagementService.assignTask(userId,taskId);
    }

    @GetMapping("/users/{userId}/tasks")
    public List<TaskResponse>  getUserAssignedTasks(@PathVariable(value = "userId") long userId ) throws ResourceNotFoundException {
        return taskManagementService.getUserAssignedTasks(userId);
    }

    @PutMapping("/tasks/{taskId}/progress")
    public TaskResponse  updateTaskProgress(@RequestParam long progress,@PathVariable(value = "taskId") long taskId  ) throws ResourceNotFoundException {
        return taskManagementService.updateTaskProgress(progress, taskId);
    }

    @GetMapping("/tasks/overdue")
    public List<TaskResponse>  getOverdueTasks( )  {
        return taskManagementService.getOverdueTasks();
    }

    @GetMapping("/tasks/status/{status}")
    public List<TaskResponse>  getTaskByStatus(@PathVariable(value = "status") String status){
        return taskManagementService.getTaskByStatus(status);
    }

    @GetMapping("/tasks/completed")
    public List<TaskResponse>  getTasksByGivenDateRange(@RequestParam String startDate,@RequestParam String endDate) {
        return taskManagementService.getTasksByGivenDateRange(startDate,endDate);
    }

    @GetMapping("/tasks/statistics")
    public Map<String,Long>  getCompletedTasksStatistics() {
        return taskManagementService.getCompletedTasksStatistics();
    }
}
