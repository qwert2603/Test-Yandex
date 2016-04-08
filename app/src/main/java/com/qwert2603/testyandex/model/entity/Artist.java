package com.qwert2603.testyandex.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс - исполнитель.
 * Создан с помощью http://www.jsonschema2pojo.org/
 */
public class Artist {

    private int id;
    private String name;
    private List<String> genres = new ArrayList<>();
    private int tracks;
    private int albums;
    private String link;
    private String description;
    private Cover cover;

    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The genres
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * @param genres The genres
     */
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    /**
     * @return The tracks
     */
    public int getTracks() {
        return tracks;
    }

    /**
     * @param tracks The tracks
     */
    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    /**
     * @return The albums
     */
    public int getAlbums() {
        return albums;
    }

    /**
     * @param albums The albums
     */
    public void setAlbums(int albums) {
        this.albums = albums;
    }

    /**
     * @return The link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The cover
     */
    public Cover getCover() {
        return cover;
    }

    /**
     * @param cover The cover
     */
    public void setCover(Cover cover) {
        this.cover = cover;
    }

}
