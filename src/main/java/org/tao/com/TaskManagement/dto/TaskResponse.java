package org.tao.com.TaskManagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TaskResponse implements Serializable {
    private static final long serialVersionUID = 2897778598714876089L;

    private long id;

    private String title;

    private String description;

    private String dueDate;

    private String status;

    private long userId;

    private long progress;

    private String completedDate;

}
