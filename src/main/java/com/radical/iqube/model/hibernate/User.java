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
@Table(name = "users")

public class User {
    @Id
    private int id;
    private String nickname;
    private String email;

//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
//    private Credentials credentials;




}
