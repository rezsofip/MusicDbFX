package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sample.Datamodel.Datasource;

import java.io.IOException;

public class AddArtistController {

    @FXML
    private TextField textField;
    public static TableView mainWindowTableView;

public void addArtist() {
    String cleanedName = textField.getText().trim();
    int insertResult = Datasource.getInstance().insertArtist(cleanedName);

    if(insertResult  == 0) {
        Dialog dialog = new Dialog();
        dialog.initOwner(mainWindowTableView.getScene().getWindow());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.setTitle("Adding new artist");
        dialog.setContentText(cleanedName + " has been successfully added to the artist list.");
        System.out.println("Artist successfully added to the list.");
        dialog.showAndWait();
        mainWindowTableView.setItems(FXCollections.observableArrayList(Datasource.getInstance().queryArtists()));
    } else if(insertResult == -2) {
        Dialog dialog = new Dialog();
        dialog.initOwner(mainWindowTableView.getScene().getWindow());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.setTitle("Error");
        dialog.setContentText("There is already an artist with name: " + cleanedName);
        System.out.println("Artist is already in the list with this name");
        dialog.showAndWait();
    } else {
        Dialog dialog = new Dialog();
        dialog.initOwner(mainWindowTableView.getScene().getWindow());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.setTitle("Error");
        dialog.setContentText("Database error");
        System.out.println("Couldn't insert to database");
        dialog.showAndWait();
    }

}

}
