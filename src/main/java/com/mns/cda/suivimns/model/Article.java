package com.mns.cda.suivimns.model;

import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idArticle;

    @CreationTimestamp
    @Column(updatable = false)
    protected LocalDateTime creationDate;

    @UpdateTimestamp
    protected LocalDateTime modificationDate;


    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    protected String content;

    @ManyToOne
    @JoinColumn(name = "id_knowledge")
    protected Knowledge knowledge;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "id_technician")
    protected Technician technician;
}
