package com.radical.iqube.model.hibernate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "album")
@NamedQuery(name = "Album.findById", query = "SELECT a FROM Album a WHERE a.id = :id")
public class Album {
    @Id
    private int id;

    //JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artist_id")
    @ToString.Exclude
    private Artist artist;

    public Artist getArtist() {return artist;}

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    private String title;

//    @Type(type = "timestamp")
    private String release_date;   //localdate<<<

    private String tracklist_url;
    private String img_url;



    @JsonInclude
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "album")
    private List<Song>songs;

//    public Collection<Song> getSongs() {
//        return songs;
//    }

    public void setSongs(Collection<Song> songs) {
        this.songs = (List<Song>) songs;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Album album = (Album) o;
        return Objects.equals(id, album.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getRelease_date(), getTracklist_url());
    }

    @Override
    public String toString() {
        return "Album{" +
                "id="  +  id +
                ", artist="  +  artist +
                ", title='"  +  title  +
                ", release_date="  +  release_date +
                ", tracklist_url='"  +  tracklist_url +
                ", img_url='" + img_url +
                '}';
    }
}
