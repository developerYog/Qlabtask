package com.yogesh.QlabTask.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="title", nullable=false, unique = true)
    private String title;
    @Column(name="content", nullable=false)
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
