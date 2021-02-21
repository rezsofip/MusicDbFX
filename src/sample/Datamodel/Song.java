package sample.Datamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Song {

    private SimpleIntegerProperty _id;
    private SimpleIntegerProperty track;
    private SimpleStringProperty title;
    private SimpleIntegerProperty album;

    public Song(int _id, int track, String title, int album) {
        this._id = new SimpleIntegerProperty(_id);
        this.track = new SimpleIntegerProperty(track);
        this.title = new SimpleStringProperty(title);
        this.album = new SimpleIntegerProperty(album);
    }

    public int get_id() {
        return _id.get();
    }

    public SimpleIntegerProperty _idProperty() {
        return _id;
    }

    public int getTrack() {
        return track.get();
    }

    public SimpleIntegerProperty trackProperty() {
        return track;
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public int getAlbum() {
        return album.get();
    }

    public SimpleIntegerProperty albumProperty() {
        return album;
    }
}
