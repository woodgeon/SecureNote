package com.example.securenote.domain.note.entity;

import com.example.securenote.domain.user.entity.User;
import com.example.securenote.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "note", indexes = @Index(name = "ix_note_user_created", columnList = "user_id, created_at"))
public class Note extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 120)
    private String title;

    @Lob
    private String body;
}
