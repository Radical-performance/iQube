package com.radical.iqube.model.hibernate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


@NamedQuery(name = "Artist.findById", query = "SELECT a FROM Artist a WHERE a.id = :id")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "artist")
public class Artist {
    @Id
    private int id;
    private String name;
    private String image_url;
    private String tracklist_url;
    private int albums_count;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artist")
    private List<Album> albums;

//    public void setAlbums(Collection<Album> albums) {
//        this.albums = albums;
//    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }



    public List<Album> getAlbums() {
        return this.albums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return getId() == artist.getId() && getName().equals(artist.getName());
    }


    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name +
                ", image_url='" + image_url +
                ", tracklist_url='" + tracklist_url + 
                ", albums_count=" + albums_count +
                '}';
    }
}
