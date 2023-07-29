package org.tao.com.TaskManagement.exception;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TaskException extends Exception{

    private static final long serialVersionUID = 6200032433624183423L;

    private LocalDate timestamp;
    private String message;
    private String details;

    public TaskException(LocalDate timestamp, String message, String details){
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }


}
