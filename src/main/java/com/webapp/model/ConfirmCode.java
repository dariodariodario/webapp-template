package com.webapp.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class ConfirmCode {
    @Id
    private String code;
    private String type;
    private String content;
    private Date expiration;
}
