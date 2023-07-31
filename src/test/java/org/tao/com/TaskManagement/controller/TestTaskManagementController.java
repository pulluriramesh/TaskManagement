package org.tao.com.TaskManagement.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.tao.com.TaskManagement.model.Tasks;
import org.tao.com.TaskManagement.service.TaskManagementService;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TaskManagementController.class)
public class TestTaskManagementController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskManagementService taskManagementService;


    @Test
    public void createTaskAPI() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(getTaskDetails())))
                .andExpect(status().isOk());
    }

    @Test
    public void updateTaskAPI() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/{taskId}",1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(getTaskDetails())))
                .andExpect(status().isOk());
    }

    @Test
    public void updateTaskProgressAPI() throws Exception
    {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("progress", "50");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/{taskId}/progress",1)
                        .contentType("application/json")
                        .params(params))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTaskAPI() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/{taskId}",2)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(getTaskDetails())))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllTasksAPI() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(getTaskDetails())))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserAssignedTasksAPI() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{userId}/tasks",1)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void getOverdueTasksAPI() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/overdue")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void getTaskByStatusAPI() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/status/{status}", "inComplete")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCompletedTasksByGivenDateRangeAPI() throws Exception
    {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("startDate", LocalDate.now().minusDays(1).toString());
        params.add("endDate", LocalDate.now().toString());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/completed")
                        .contentType("application/json")
                        .params(params))
                .andExpect(status().isOk());
    }

    @Test
    public void getCompletedTasksStatisticsAPI() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/statistics")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void assignTaskAPI() throws Exception
    {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("userId", "1");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks/{taskId}/assign",1)
                        .params(params)
                        .contentType("application/json"))
                        .andExpect(status().isOk());
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
