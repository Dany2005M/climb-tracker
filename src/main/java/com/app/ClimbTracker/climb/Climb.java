package com.app.ClimbTracker.climb;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
public class Climb {

    @Id @GeneratedValue
    private Long id;

    @NotBlank(message = "Grade cannot be empty")
    private String grade;

    @NotBlank(message = "Style cannot be empty")
    @Size(max = 20,message = "Style is too long")
    private String style;
    private Boolean isCompleted;
    private LocalDate date;

    public Climb() {}

    public Climb(String grade, String style, Boolean isCompleted, LocalDate date) {
        this.grade = grade;
        this.style = style;
        this.isCompleted = isCompleted;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getGrade() {
        return grade;
    }

    public String getStyle() {
        return style;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
