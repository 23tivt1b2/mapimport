package data;

import java.io.Serializable;
import java.time.LocalTime;

public class Performance implements Serializable {

    private Artist artist;
    private LocalTime beginTime;
    private LocalTime endTime;

    public Performance(Artist artist,LocalTime beginTime, LocalTime endTime) {
        this.artist = artist;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public Performance(Artist artist,LocalTime beginTime) {
        this.artist = artist;
        this.beginTime = beginTime;
        this.endTime = beginTime.plusHours(1);
    }

    public void setArtist(Artist name) {
        this.artist = name;
    }

    public Artist getArtist() {
        return this.artist;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return this.artist.toString();
    }
}
