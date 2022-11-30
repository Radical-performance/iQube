package com.radical.iqube.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    protected String login;
    protected String password;
    protected String email;
    protected String nickname;
//    protected int[]playlists;
//    protected  int[]tracklist;
}



