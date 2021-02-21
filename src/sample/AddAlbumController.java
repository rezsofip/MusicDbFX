package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import sample.Datamodel.Datasource;

public class AddAlbumController {

    @FXML
    private TextField textField;

    public TextField getTextField() {
        return textField;
    }

    public void addAlbum(String albumName, int artistId) {
        boolean result = Datasource.getInstance().addAlbum(albumName, artistId);
        if(!result)  {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Error");
            dialog.setContentText("Couldn't add " + albumName + " album to the albums");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
        }else {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Album added");
            dialog.setContentText(albumName + " has been successfully added to the albums");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
        }
    }
}
