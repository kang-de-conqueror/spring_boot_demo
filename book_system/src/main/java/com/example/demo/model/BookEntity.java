package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class BookEntity {
    @JsonProperty("id")
    private String id;
    @NotNull(message = "Please input title")
    @NotEmpty(message = "Please input title")
    @JsonProperty("title")
    private String title;
    @NotNull(message = "Please input author")
    @NotEmpty(message = "Please input author")
    @JsonProperty("author")
    private String author;
    @JsonProperty("publish_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;
    @JsonProperty("description")
    private String description;
}
