package org.tao.com.TaskManagement.util;

import org.springframework.util.ObjectUtils;
import org.tao.com.TaskManagement.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskManagerConstants {

    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static LocalDate getLocalDate(String localDate) throws ResourceNotFoundException {
        try{
            if(!ObjectUtils.isEmpty(localDate)){
                return LocalDate.parse(localDate,dateFormatter);
            }
        }catch (Exception ex){
            throw new ResourceNotFoundException("DueDate format is wrong, correct format is YYYY-MM-DD ");
        }
        return null;
    }
}
