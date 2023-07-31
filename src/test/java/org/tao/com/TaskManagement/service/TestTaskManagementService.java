package org.tao.com.TaskManagement.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.tao.com.TaskManagement.dto.TaskResponse;
import org.tao.com.TaskManagement.exception.ResourceNotFoundException;
import org.tao.com.TaskManagement.model.Tasks;
import org.tao.com.TaskManagement.repository.TaskManagementRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class TestTaskManagementService {

    @Autowired
    private TaskManagementService taskManagementService;

    @MockBean
    private TaskManagementRepository taskManagementRepository;


    @Test
    @DisplayName("Test Create Task")
    void testCreateTask() throws ResourceNotFoundException {
        Tasks tasks = getTaskDetails();
        doReturn(tasks).when(taskManagementRepository).save(any());
        TaskResponse taskResponse = taskManagementService.createTask(tasks);
        Assertions.assertNotNull(taskResponse);
        Assertions.assertEquals("inProgress", taskResponse.getStatus());
    }

    @Test
    @DisplayName("Test Update Task")
    void testUpdateTask() throws ResourceNotFoundException {
        Tasks tasks = getTaskDetails();
        tasks.setStatus("completed");
        doReturn(Optional.of(tasks)).when(taskManagementRepository).findById(any());
        doReturn(tasks).when(taskManagementRepository).save(any());
        TaskResponse taskResponse = taskManagementService.updateTask(tasks,1);
        Assertions.assertNotNull(taskResponse);
        Assertions.assertEquals("completed", taskResponse.getStatus());
    }

    @Test
    @DisplayName("Test Delete Task")
    void testDeleteTask() throws ResourceNotFoundException {
        Tasks tasks = getTaskDetails();
        doReturn(Optional.of(tasks)).when(taskManagementRepository).findById(any());
        Map<String, Boolean> taskResponse = taskManagementService.deleteTask(1);
        Assertions.assertNotNull(taskResponse);
        Assertions.assertEquals(true, taskResponse.get("deleted") );
    }

    @Test
    @DisplayName("Test Get All Tasks")
    void testGetAllTasks()  {
        List<Tasks> tasksList = new ArrayList<>();
        tasksList.add(getTaskDetails());
        doReturn(tasksList).when(taskManagementRepository).findAll();
        List<TaskResponse> taskResponse = taskManagementService.getAllTasks();
        Assertions.assertNotNull(taskResponse);
        Assertions.assertEquals(LocalDate.now().toString(), taskResponse.get(0).getDueDate() );
    }

    @Test
    @DisplayName("Test Assign Task")
    void testAssignTask() throws ResourceNotFoundException {
        Tasks tasks = getTaskDetails();
        tasks.setUserId(2);
        doReturn(Optional.of(tasks)).when(taskManagementRepository).findTasksByUserIdAndTaskId(2L,1L);
        doReturn(tasks).when(taskManagementRepository).save(any());
        TaskResponse taskResponse = taskManagementService.assignTask(2, 1);
        Assertions.assertNotNull(taskResponse);
        Assertions.assertEquals(2L, taskResponse.getUserId() );
    }

    @Test
    @DisplayName("Test Get User Assigned Tasks")
    void testGetUserAssignedTasks() throws ResourceNotFoundException {
        List<Tasks> tasksList = new ArrayList<>();
        tasksList.add(getTaskDetails());
        doReturn(Optional.of(tasksList)).when(taskManagementRepository).findAllByUserId(2L);
        List<TaskResponse> taskResponse = taskManagementService.getUserAssignedTasks(2L);
        Assertions.assertNotNull(taskResponse);
        Assertions.assertEquals(LocalDate.now().toString(), taskResponse.get(0).getDueDate() );
    }

    @Test
    @DisplayName("Test Update Task Progress")
    void testUpdateTaskProgress() throws ResourceNotFoundException {
        Tasks tasks = getTaskDetails();
        doReturn(Optional.of(tasks)).when(taskManagementRepository).findById(1L);
        doReturn(tasks).when(taskManagementRepository).save(any());
        TaskResponse taskResponse = taskManagementService.updateTaskProgress(75, 1);
        Assertions.assertNotNull(taskResponse);
        Assertions.assertEquals(LocalDate.now().toString(), taskResponse.getDueDate() );
    }

    @Test
    @DisplayName("Test Get Overdue Tasks")
    void testGetOverdueTasks()  {
        List<Tasks> tasksList = new ArrayList<>();
        Tasks tasks = getTaskDetails();
        tasks.setDueDate(LocalDate.now().minusDays(1).toString());
        tasksList.add(tasks);
        doReturn(tasksList).when(taskManagementRepository).findAll();
        List<TaskResponse> taskResponse = taskManagementService.getOverdueTasks();
        Assertions.assertNotNull(taskResponse);
        Assertions.assertEquals(LocalDate.now().minusDays(1).toString(), taskResponse.get(0).getDueDate() );
    }

    @Test
    @DisplayName("Test Get Task By Status")
    void testGetTaskByStatus()  {
        List<Tasks> tasksList = new ArrayList<>();
        doReturn(tasksList).when(taskManagementRepository).getAllTasksByStatus("completed");
        List<TaskResponse> taskResponse = taskManagementService.getTaskByStatus("completed");
        Assertions.assertNotNull(taskResponse);
        Assertions.assertEquals(0, taskResponse.size() );
    }

    @Test
    @DisplayName("Test Get Completed Tasks Statistics")
    void testGetCompletedTasksStatistics() {
        List<Tasks> tasksList = new ArrayList<>();
        doReturn(1L).when(taskManagementRepository).count();
        doReturn(1L).when(taskManagementRepository).getCompltedTaskCount("completed");
        Map<String, Long> taskResponse = taskManagementService.getCompletedTasksStatistics();
        Assertions.assertNotNull(taskResponse);
        Assertions.assertEquals(100, taskResponse.get("percentageOfCompletedTasks") );
    }



    private Tasks getTaskDetails(){
        Tasks tasks = new Tasks();
        tasks.setTitle("title1");
        tasks.setDescription("description1");
        tasks.setUserId(1);
        tasks.setProgress(50);
        tasks.setStatus("inProgress");
        tasks.setDueDate(LocalDate.now().toString());
        return tasks;
    }
}
