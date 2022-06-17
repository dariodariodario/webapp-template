package com.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean complete = false;

    @Lob
    private String data;
    private long expiresSeconds = 60;

    @Builder.Default
    private Date lastModified = new Date();

    public boolean isExpired() {
        return complete || lastModified.toInstant().plusSeconds(expiresSeconds).isBefore(Instant.now());
    }
}
