package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import sample.Datamodel.Artist;
import sample.Datamodel.Datasource;

import java.io.IOException;
import java.sql.ResultSet;

public class UpdateArtistController {

    @FXML
    private TextField artistName;
    @FXML
    private Label infoLabel;
    public static TableView primaryTableView;

    public void updateArtist() {
        String artistName = this.artistName.getText();
        String cleanedArtistName = artistName.trim();
        if(Datasource.getInstance().findArtistName(cleanedArtistName)) {
            infoLabel.setText("There is an artist with the given name! Choose another name!");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            try{
                fxmlLoader.load();
            }catch(IOException e ){
                System.out.println("Couldn't load the fxml file");
            }

            Controller mainWindowController = fxmlLoader.getController();
            Artist selectedArtist = mainWindowController.currentlySelectedArtist;
            String oldName = selectedArtist.getName();
            Datasource.getInstance().updateArtistName(oldName, cleanedArtistName);
            ObservableList<Artist> observableList = FXCollections.observableArrayList(Datasource.getInstance().queryArtists());
            primaryTableView.setItems(observableList);
            primaryTableView.refresh();
            infoLabel.setText("The artist name " + oldName + " has been updated to " + cleanedArtistName);
        }
    }


}
