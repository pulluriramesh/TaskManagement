package org.tao.com.TaskManagement.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tao.com.TaskManagement.dto.TaskResponse;
import org.tao.com.TaskManagement.exception.ResourceNotFoundException;
import org.tao.com.TaskManagement.model.Tasks;
import org.tao.com.TaskManagement.repository.TaskManagementRepository;
import org.tao.com.TaskManagement.repository.UserRepository;
import org.tao.com.TaskManagement.util.TaskManagerConstants;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskManagementServiceImpl implements TaskManagementService{

    private static final String COMPLETED_STATUS = "completed";

    @Autowired
    private TaskManagementRepository taskManagementRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public TaskResponse createTask(Tasks tasks) throws ResourceNotFoundException {
        TaskManagerConstants.getLocalDate(tasks.getDueDate());
        TaskResponse taskResponse = new TaskResponse();
        Tasks taskDetails = taskManagementRepository.save(tasks);
        BeanUtils.copyProperties(taskDetails, taskResponse);
        return taskResponse;
    }

    @Override
    public TaskResponse updateTask(Tasks tasks,long taskId) throws ResourceNotFoundException {
        TaskResponse taskResponse = new TaskResponse();
        Tasks task = taskManagementRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + taskId));
            task.setTitle(tasks.getTitle());
            task.setDescription(tasks.getDescription());
            task.setStatus(tasks.getStatus());
            task.setDueDate(tasks.getDueDate());
            if(tasks.getStatus().equalsIgnoreCase("completed")){
                task.setCompletedDate(LocalDate.now().toString());
            }
            Tasks taskDetails = taskManagementRepository.save(task);
            BeanUtils.copyProperties(taskDetails, taskResponse);
        return taskResponse;
    }

    public Map<String,Boolean> deleteTask(long taskId) throws ResourceNotFoundException {
            Tasks task = taskManagementRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + taskId));
            taskManagementRepository.delete(task);
        Map<String,Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return  response;
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        List<TaskResponse> list = new ArrayList<>();
        List<Tasks> tasksList = taskManagementRepository.findAll();
        tasksList.stream().forEach(li->{
            TaskResponse taskResponse = new TaskResponse();
            BeanUtils.copyProperties(li,taskResponse);
            list.add(taskResponse);
        });
        return list;
    }

    @Override
    public TaskResponse assignTask(long userId, long taskId) throws ResourceNotFoundException {
        TaskResponse taskResponse = new TaskResponse();

       Tasks tasks = taskManagementRepository.findTasksByUserIdAndTaskId(userId, taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task/User details not found for this TaskId/UserId :: " + userId ));

        tasks.setUserId(userId);
        Tasks taskDetails = taskManagementRepository.save(tasks);
        BeanUtils.copyProperties(taskDetails, taskResponse);
        return taskResponse;
    }

    @Override
    public List<TaskResponse> getUserAssignedTasks(long userId) throws ResourceNotFoundException {
        List<TaskResponse> list = new ArrayList<>();
        List<Tasks> tasksList = taskManagementRepository.findAllByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + userId));
        tasksList.stream().forEach(li->{
            TaskResponse taskResponse = new TaskResponse();
            BeanUtils.copyProperties(li,taskResponse);
            list.add(taskResponse);
        });
        return list;
    }

    @Override
    public TaskResponse updateTaskProgress(long progress, long taskId) throws ResourceNotFoundException {
        TaskResponse taskResponse = new TaskResponse();
        Tasks task = taskManagementRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + taskId));
        task.setProgress(progress);
        Tasks taskDetails = taskManagementRepository.save(task);
        BeanUtils.copyProperties(taskDetails, taskResponse);
        return taskResponse;
    }

    @Override
    public List<TaskResponse> getOverdueTasks() {
        List<TaskResponse> list = new ArrayList<>();
       List<Tasks> tasksList = taskManagementRepository.findAll();
        List<Tasks> taskList = tasksList.stream().filter(li -> LocalDate.parse(li.getDueDate()).isBefore(LocalDate.now())).collect(Collectors.toList());
        taskList.stream().forEach(li->{
            TaskResponse taskResponse = new TaskResponse();
            BeanUtils.copyProperties(li,taskResponse);
            list.add(taskResponse);
        });
        return list;
    }

    @Override
    public List<TaskResponse> getTaskByStatus(String status) {
        List<TaskResponse> list = new ArrayList<>();
        List<Tasks> tasksList = taskManagementRepository.getAllTasksByStatus(status);
        tasksList.stream().forEach(li->{
            TaskResponse taskResponse = new TaskResponse();
            BeanUtils.copyProperties(li,taskResponse);
            list.add(taskResponse);
        });
        return list;
    }

    @Override
    public List<TaskResponse> getTasksByGivenDateRange(String startDate, String endDate) {
        List<TaskResponse> list = new ArrayList<>();
        List<Tasks> tasksList = taskManagementRepository.getAllTasksByDateRange(startDate, endDate);
        tasksList.stream().filter(li-> li.getStatus().equalsIgnoreCase(COMPLETED_STATUS)).forEach(li->{
            TaskResponse taskResponse = new TaskResponse();
            BeanUtils.copyProperties(li,taskResponse);
            list.add(taskResponse);
        });
        return list;
    }

    @Override
    public Map<String,Long> getCompletedTasksStatistics() {
        long totalTasks = taskManagementRepository.count();
        long completedTasks = taskManagementRepository.getCompltedTaskCount(COMPLETED_STATUS);
        long percentageOfCompletedTasks = (completedTasks *100)/ totalTasks;
        Map<String,Long> response = new HashMap<>();
        response.put("percentageOfCompletedTasks", percentageOfCompletedTasks);
        return response;
    }


}
