package com.radical.iqube.model.hibernate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "credentials")
public class Credentials {


    @Id
    private String login;
    private String password;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;




}
