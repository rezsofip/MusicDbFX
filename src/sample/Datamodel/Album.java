package sample.Datamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Album {

    private SimpleIntegerProperty _id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty artistId;

    public Album(int _id, String name, int artistId) {
        this._id = new SimpleIntegerProperty(_id);
        this.name = new SimpleStringProperty(name);
        this.artistId = new SimpleIntegerProperty(artistId);
    }

    public int get_id() {
        return _id.get();
    }

    public SimpleIntegerProperty _idProperty() {
        return _id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getArtistId() {
        return artistId.get();
    }

    public SimpleIntegerProperty artistIdProperty() {
        return artistId;
    }
}
