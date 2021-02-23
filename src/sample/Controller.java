package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Datamodel.Album;
import sample.Datamodel.Artist;
import sample.Datamodel.Datasource;
import sample.Datamodel.Song;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static javafx.collections.FXCollections.observableArrayList;

public class Controller {

@FXML
   public TableView tableView;
   public static Artist currentlySelectedArtist;

    public static Artist getCurrentlySelectedArtist() {
        return currentlySelectedArtist;
    }

    public void showAlbumsByArtist() {
        Artist selectedArtist;
        ObservableList<Album> observableList;
        List<Album> albumList = new ArrayList<>();
        try {
            selectedArtist = (Artist) tableView.getSelectionModel().getSelectedItem();
        }catch (ClassCastException e) {
            Dialog dialog = new Dialog();
            dialog.setTitle("Error");
            dialog.contentTextProperty().setValue("No artist was selected!");
            System.out.println("No artist selected");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
            return;
        }

        try{
            albumList = Datasource.getInstance().queryAlbumsByArtist(selectedArtist.getName());
            observableList = observableArrayList(albumList);
            for(Album album : albumList) {
                System.out.println(album.getName());
            }
        } catch(NullPointerException e) {
            Dialog dialog = new Dialog();
            dialog.setTitle("Error");
            dialog.contentTextProperty().setValue("No album was found!");
            System.out.println("No album was found selected");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
            return;
        }


        observableList.sort(new Comparator<Album>() {
            @Override
            public int compare(Album o1, Album o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        switchToAlbumTableView();
        tableView.setItems(observableList);

    }

    public void showArtists() {
        UpdateArtistController.primaryTableView = this.tableView;
        AddArtistController.mainWindowTableView = tableView;
        switchToArtistTableView();
        ObservableList observableList = FXCollections.observableList(Datasource.getInstance().queryArtists());
        tableView.setItems(observableList);
        observableList.sort(new Comparator<Artist>() {
            @Override
            public int compare(Artist o1, Artist o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public void viewSongsOfArtist() {

        Artist selectedArtist = null;
        ObservableList<Song> observableList;
        try {
            selectedArtist = (Artist) tableView.getSelectionModel().getSelectedItem();
        }catch (ClassCastException e) {
            Dialog dialog = new Dialog();
            dialog.setTitle("Error");
            dialog.contentTextProperty().setValue("No artist was selected!");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
            return;
        }

        try{
            observableList = observableArrayList(Datasource.getInstance().querySongsOfArtist(selectedArtist.getName()));
        } catch(NullPointerException e) {
            Dialog dialog = new Dialog();
            dialog.setTitle("Error");
            dialog.contentTextProperty().setValue("No artist was selected!");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
            return;
        }


        switchToSongTableView();
        tableView.setItems(observableList);
        observableList.sort(new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
    }

    public void viewSongsOfAlbum() {

        Album selectedAlbum = null;
        ObservableList<Song> observableList;
        try {
            selectedAlbum = (Album) tableView.getSelectionModel().getSelectedItem();
        }catch (ClassCastException e) {
            Dialog dialog = new Dialog();
            dialog.setTitle("Error");
            dialog.contentTextProperty().setValue("No album was selected!");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
            return;
        }

        try{
            observableList = observableArrayList(Datasource.getInstance().querySongsOfAlbum(selectedAlbum.getName()));
        } catch(NullPointerException e) {
            Dialog dialog = new Dialog();
            dialog.setTitle("Error");
            dialog.contentTextProperty().setValue("No album was selected!");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
            return;
        }

        observableList.sort(new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
        switchToSongTableView();
        tableView.setItems(observableList);
    }

    public void switchToArtistTableView() {

        tableView.getColumns().clear();

        TableColumn idColumn = new TableColumn("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<Artist, Integer>("_id"));
        TableColumn nameColumn = new TableColumn("Artist name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Artist, String>("name"));
        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(nameColumn);

    }

    public void switchToAlbumTableView() {
        this.tableView.getColumns().clear();

        TableColumn idColumn = new TableColumn("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<Album, Integer>("_id"));
        TableColumn nameColumn = new TableColumn("Album name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Album, String>("name"));
        TableColumn artistNameColumn = new TableColumn("Artist name");
        artistNameColumn.setCellValueFactory(new PropertyValueFactory<Album, Integer>("artistId"));
        this.tableView.getColumns().add(idColumn);
        this.tableView.getColumns().add(nameColumn);
        this.tableView.getColumns().add(artistNameColumn);

    }

    public void switchToSongTableView() {

        this.tableView.getColumns().clear();

        TableColumn idColumn = new TableColumn("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<Song, Integer>("_id"));
        TableColumn trackColumn = new TableColumn("Track");
        trackColumn.setCellValueFactory(new PropertyValueFactory<Song, Integer>("track"));
        TableColumn titleNameColumn = new TableColumn("Title");
        titleNameColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
        TableColumn albumNameColumn = new TableColumn("Album");
        albumNameColumn.setCellValueFactory(new PropertyValueFactory<Song, Integer>("album"));
        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(trackColumn);
        tableView.getColumns().add(titleNameColumn);
        tableView.getColumns().add(albumNameColumn);

    }

    @FXML
    public void updateArtist() {
        this.currentlySelectedArtist = (Artist) tableView.getSelectionModel().getSelectedItem();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(tableView.getScene().getWindow());
        dialog.setTitle("Update artist");
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateArtist.fxml"));
            Parent root = fxmlLoader.load();
            UpdateArtistController dialogController = fxmlLoader.getController();
            dialog.getDialogPane().setContent(root);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                dialogController.updateArtist();
            }
        }catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
        }


    }

    public void currentlySelectedArtist() {
        try{
            this.currentlySelectedArtist = (Artist) tableView.getSelectionModel().getSelectedItem();
            System.out.println("Artist selected");
        }catch(ClassCastException e) {

        }

    }

    public void addArtist() {
       Dialog<ButtonType> dialog = new Dialog<>();
       try{
           FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddArtist.fxml"));
           Parent root = fxmlLoader.load();
           AddArtistController addArtistController = fxmlLoader.getController();
           dialog.getDialogPane().setContent(root);
           dialog.setTitle("Add new artist");
           dialog.initOwner(this.tableView.getScene().getWindow());
           dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
           dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
           Optional<ButtonType> result = dialog.showAndWait();
           if(result.isPresent() && result.get() == ButtonType.OK) {
                addArtistController.addArtist();
           }
       }catch(IOException e) {
           System.out.println("Couldn't load the fxml");
       }

    }

    public void removeArtist() {
       Dialog<ButtonType> dialog = new Dialog<>();
       dialog.setTitle("Remove artist");
       dialog.initOwner(this.tableView.getScene().getWindow());
       dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
       dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
       dialog.setContentText("Are you sure you want to remove the artist \" " + currentlySelectedArtist.getName() + " \" from the artists?");
       Optional<ButtonType> result = dialog.showAndWait();

       if(result.isPresent() && result.get() == ButtonType.YES) {
           if(Datasource.getInstance().deleteArtist(currentlySelectedArtist.getName()) == false) {
               System.out.println("Couldn't delete the artist");
           } else {
               List<Artist> artistList = Datasource.getInstance().queryArtists();
               ObservableList<Artist> observableList = FXCollections.observableArrayList(artistList);
               tableView.setItems(observableList);
               System.out.println("Deletion has been successful");
           }

       }
    }

    public void addAlbum() {
        try{
            Artist selectedArtist = (Artist) this.tableView.getSelectionModel().getSelectedItem();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Add album to artist");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddAlbum.fxml"));
            Parent root = fxmlLoader.load();
            AddAlbumController addAlbumController = fxmlLoader.getController();
            dialog.getDialogPane().setContent(root);
            Optional<ButtonType> result = dialog.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                String albumName = addAlbumController.getTextField().getText();
                int artistId = selectedArtist.get_id();

                addAlbumController.addAlbum(albumName, artistId);
            }


        }catch(ClassCastException e) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Error");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.setContentText("Please select an artist");
            dialog.showAndWait();
        }catch(IOException e) {
            System.out.println("Couldn't load the fxml file");
        }
    }

    public void removeAlbum() {
        try{
            Album selectedAlbum = (Album) this.tableView.getSelectionModel().getSelectedItem();
            int result = Datasource.getInstance().songsExistInAlbum(selectedAlbum.get_id());
            if(result == -1) {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Error");
                dialog.setContentText("Couldn't query the songs of the album");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.showAndWait();
            }else if(result == 0) {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Confirmation");
                dialog.setContentText("Are you sure you want to remove the album " + selectedAlbum.getName() + " from the albums?");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
                Optional<ButtonType> resultButton = dialog.showAndWait();
                if(resultButton.isPresent() && resultButton.get() == ButtonType.YES) {
                    Datasource.getInstance().removeAlbum(selectedAlbum.get_id(), false);
                }

            }else if(result == 1) {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Confirmation");
                dialog.setContentText("Are you sure you want to remove the album " + selectedAlbum.getName() + " from the albums?");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
                Optional<ButtonType> resultButton = dialog.showAndWait();
                if(resultButton.isPresent() && resultButton.get() == ButtonType.YES) {
                    Datasource.getInstance().removeAlbum(selectedAlbum.get_id(), true);
                    ObservableList<Album> observableList = FXCollections.observableArrayList(Datasource.getInstance().queryAlbumsByArtist(currentlySelectedArtist.getName()));
                    tableView.setItems(observableList);
                }
            }
        }catch(ClassCastException e) {
            e.printStackTrace();
            System.out.println("Please select an album");
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Error");
            dialog.setContentText("Please select an album");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
        }

    }

    @FXML
    public void updateAlbum() {
        try{
            Album selectedAlbum = (Album) this.tableView.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateAlbum.fxml"));
            Parent root = fxmlLoader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.getDialogPane().setContent(root);
            dialog.setTitle("Update album");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            UpdateAlbumController updateAlbumController = fxmlLoader.getController();
            Optional<ButtonType> resultButton = dialog.showAndWait();
            if(resultButton.isPresent() && resultButton.get() == ButtonType.OK) {
                String albumName = updateAlbumController.getTextField().getText();
                String trimmedAlbumName = albumName.trim();
                int artistId = selectedAlbum.getArtistId();
                if(trimmedAlbumName.equals("")) {
                    Dialog<ButtonType> blankAlbumNameDialog = new Dialog<>();
                    blankAlbumNameDialog.setTitle("Error");
                    blankAlbumNameDialog.setContentText("The album name cannot be empty. Please enter another one.");
                    blankAlbumNameDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                    blankAlbumNameDialog.showAndWait();
                }else {
                    int result = Datasource.getInstance().albumExistsForArtist(trimmedAlbumName, artistId);
                    if(result == -1) {
                        Dialog<ButtonType> blankAlbumNameDialog = new Dialog<>();
                        blankAlbumNameDialog.setTitle("Error");
                        blankAlbumNameDialog.setContentText("Couldn't query the album name.");
                        blankAlbumNameDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                        blankAlbumNameDialog.showAndWait();
                    }else if(result == 1) {
                        Dialog<ButtonType> blankAlbumNameDialog = new Dialog<>();
                        blankAlbumNameDialog.setTitle("Error");
                        blankAlbumNameDialog.setContentText("The entered album name already exists for this artist.");
                        blankAlbumNameDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                        blankAlbumNameDialog.showAndWait();
                    }else if(result == 0) {
                        Datasource.getInstance().updateAlbumName(trimmedAlbumName, artistId);
                        List<Album> albumList = new ArrayList<>();
                        int artistId2 = selectedAlbum.getArtistId();
                        albumList.addAll(Datasource.getInstance().queryAlbumsByArtist(Integer.toString(artistId2)));
                        this.tableView.setItems(FXCollections.observableArrayList(albumList));
                    }
                }

            }
        }catch(ClassCastException e) {
            e.printStackTrace();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Error");
            dialog.setContentText("No album selected. Please select an album you want to update");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
        }catch(IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load the fxml file");
        }


    }

    @FXML
    public void addSong() {
        try{
            Album selectedAlbum = (Album) this.tableView.getSelectionModel().getSelectedItem();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Add song");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddSong.fxml"));
            Parent root = fxmlLoader.load();
            dialog.getDialogPane().setContent(root);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            Optional<ButtonType> resultButton = dialog.showAndWait();
            if(resultButton.isPresent() && resultButton.get() == ButtonType.OK) {
                AddSongController addSongController = fxmlLoader.getController();
                int numberOfSongs = Datasource.getInstance().numberOfSongs(addSongController.getTextField().getText(), selectedAlbum.get_id());
                if(numberOfSongs > 0) {
                    Dialog<ButtonType> dialog2 = new Dialog<>();
                    dialog2.setTitle("Error");
                    dialog2.setContentText("A song already exists with this name in the album.");
                    dialog2.getDialogPane().getButtonTypes().add(ButtonType.OK);
                    dialog2.showAndWait();
                }else if(numberOfSongs == 0) {
                    Datasource.getInstance().insertSong(Datasource.getInstance().nextTrackNumber(selectedAlbum.get_id()), addSongController.getTextField().getText(), selectedAlbum.get_id());
                }
            }
        }catch(ClassCastException e) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Error");
            dialog.setContentText("Please select an album");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
        }catch(IOException e) {
            System.out.println("Couldn't load the fxml file.");
        }
    }

}
