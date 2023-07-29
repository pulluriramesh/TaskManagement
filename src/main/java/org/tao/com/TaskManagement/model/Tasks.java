package org.tao.com.TaskManagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.jpa.repository.Temporal;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "Tasks")
public class Tasks implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Title is required.")
    private String title;

    private String description;

    @NotNull(message = "The due date is required.")
    @JsonFormat(pattern = "YYYY-MM-DD", shape = JsonFormat.Shape.STRING)
    @Column(name="date")
    private String dueDate;

   private String status;

   @Column(name="userid")
   private long userId;

   private long progress;

    @Column(name="completed_date")
    private String completedDate;

}
