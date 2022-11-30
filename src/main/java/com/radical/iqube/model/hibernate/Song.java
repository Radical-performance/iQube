package com.radical.iqube.model.hibernate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "songs")
public class Song {
    @Id
    private int id;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "album_id")
    private Album album;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Column
    private String title;

    @Column
    private String url;

    @Column
    private int duration;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Song song = (Song) o;

        if (!Objects.equals(title, song.title)) return false;
        return Objects.equals(url, song.url);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
