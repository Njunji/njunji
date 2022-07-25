package com.sammy.njunji.models;

import com.sammy.njunji.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.GenerationType.AUTO;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Server {

    @Id @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column (unique = true)
    @NotEmpty(message = "IP Address cannot be empty")
    private String ipAddress;
    private String name;
    private String memory;
    private String type;
    private String imageUrl;
    private Status status ;
}
