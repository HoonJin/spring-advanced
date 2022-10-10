package com.example.spring.dbtx.propagation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Log {

    @Id
    @GeneratedValue
    private Long id;

    private String message;

    public Log(String username) {
        this.message = username;
    }
}
