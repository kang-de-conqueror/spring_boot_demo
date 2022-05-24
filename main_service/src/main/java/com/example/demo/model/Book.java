package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "book", schema = "book_system")
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Integer id;
    @Column(name = "title")
    @JsonProperty("title")
    private String title;
    @Column(name = "author")
    @JsonProperty("author")
    private String author;
    @Column(name = "publish_date")
    @JsonProperty("publish_date")
    private Date publishDate;
    @Column(name = "description")
    @JsonProperty("description")
    private String description;
    @Column(name = "is_active")
    @JsonProperty("is_active")
    private Boolean isActive;
}
