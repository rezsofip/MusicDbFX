package sample.Datamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Album {

    private SimpleIntegerProperty _id;
    private SimpleStringProperty name;
    private SimpleStringProperty artistName;

    public Album(int _id, String name, String artistName) {
        this._id = new SimpleIntegerProperty(_id);
        this.name = new SimpleStringProperty(name);
        this.artistName = new SimpleStringProperty(artistName);
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

    public String getArtistName() {
        return artistName.get();
    }

    public SimpleStringProperty artistNameProperty() {
        return artistName;
    }
}
