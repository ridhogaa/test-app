package org.com.testbni.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "countries")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Countries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "names", columnDefinition = "jsonb")
    private String names;

    @Column(name = "capital", nullable = false)
    private String capital;

    @Column(name = "flag", nullable = false)
    private String flag;

    @Column(name = "code", length = 4, nullable = false)
    private String code;

    @Column(name = "alt_code", length = 4)
    private String altCode;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();
}
