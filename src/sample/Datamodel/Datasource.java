package sample.Datamodel;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Datasource {

    private Connection connection;
    private PreparedStatement queryArtists;
    private PreparedStatement queryAlbumsByArtist;
    private PreparedStatement querySongsOfArtist;
    private PreparedStatement querySongsOfAlbum;
    private PreparedStatement updateArtistName;
    private PreparedStatement insertArtist;
    private PreparedStatement deleteSongsOfArtist;
    private PreparedStatement deleteAlbumsOfArtist;
    private PreparedStatement deleteArtist;
    private PreparedStatement addAlbum;
    private PreparedStatement checkNumberOfSongsInAlbum;
    private PreparedStatement removeAlbum;
    private PreparedStatement removeSongsOfAlbum;

    private static Datasource instance = new Datasource();

    public static Datasource getInstance() {
        return instance;
    }

    public static final String DATABASE_NAME = "music.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\kliens\\IdeaProjects\\JavaFXMusicDb\\src\\sample\\Datamodel\\" + DATABASE_NAME;

    public static final String ARTISTS_TABLE_NAME = "artists";
    public static final String ARTISTS_COLUMN_ID = "_id";
    public static final String ARTISTS_COLUMN_NAME = "name";

    public static final String ALBUMS_TABLE_NAME = "albums";
    public static final String ALBUMS_COLUMN_ID = "_id";
    public static final String ALBUMS_COLUMN_NAME = "name";
    public static final String ALBUMS_COLUMN_ARTIST = "artist";

    public static final String SONGS_TABLE_NAME = "songs";
    public static final String SONGS_COLUMN_ID = "_id";
    public static final String SONGS_COLUMN_TRACK = "track";
    public static final String SONGS_COLUMN_TITLE = "title";
    public static final String SONGS_COLUMN_ALBUM = "album";

    public static final String QUERY_ARTISTS = "SELECT * FROM " +  ARTISTS_TABLE_NAME;
    public static final String QUERY_ALBUMS_BY_ARTIST = "SELECT " + ALBUMS_TABLE_NAME + "." + ALBUMS_COLUMN_ID + ", " +  ALBUMS_TABLE_NAME + "." + ALBUMS_COLUMN_NAME + ", " +
            ALBUMS_TABLE_NAME + "." + ALBUMS_COLUMN_ARTIST + " FROM " +
            ALBUMS_TABLE_NAME + " INNER JOIN " + ARTISTS_TABLE_NAME + " ON " + ALBUMS_TABLE_NAME + "." + ALBUMS_COLUMN_ARTIST + " = " +
            ARTISTS_TABLE_NAME + "." + ARTISTS_COLUMN_ID + " WHERE " + ARTISTS_TABLE_NAME + "." + ARTISTS_COLUMN_NAME + " = ?";
            // SELECT albums.name FROM albums INNER JOIN artists ON albums.artist = artists._id WHERE artists.name = ?;

    public static final String QUERY_SONGS_OF_ARTIST = "SELECT " + SONGS_TABLE_NAME + "." + SONGS_COLUMN_ID + ", " + SONGS_TABLE_NAME + "." + SONGS_COLUMN_TRACK +
            ", " + SONGS_TABLE_NAME + "." + SONGS_COLUMN_TITLE + ", " + SONGS_TABLE_NAME + "." + SONGS_COLUMN_ALBUM + " FROM " + SONGS_TABLE_NAME + " INNER JOIN " +
             ALBUMS_TABLE_NAME + " ON " + SONGS_TABLE_NAME + "." + SONGS_COLUMN_ALBUM + " = " + ALBUMS_TABLE_NAME + "." + ALBUMS_COLUMN_ID + " INNER JOIN " +
            ARTISTS_TABLE_NAME + " ON " + ALBUMS_TABLE_NAME + "." + ALBUMS_COLUMN_ARTIST + " = " + ARTISTS_TABLE_NAME + "." + ARTISTS_COLUMN_ID + " WHERE " +
            ARTISTS_TABLE_NAME + "." + ARTISTS_COLUMN_NAME + " = ?";
    //SELECT songs.title FROM songs INNER JOIN albums ON songs.album = albums._id INNER JOIN artists ON albums.artist = artists._id WHERE artists.name = ?;

    public static final String QUERY_SONGS_OF_ALBUM = "SELECT " + SONGS_TABLE_NAME + "." + SONGS_COLUMN_ID + ", " + SONGS_TABLE_NAME + "." + SONGS_COLUMN_TRACK +
            ", " + SONGS_TABLE_NAME + "." + SONGS_COLUMN_TITLE + ", " + SONGS_TABLE_NAME + "." + SONGS_COLUMN_ALBUM + " FROM " + SONGS_TABLE_NAME + " INNER JOIN " +
            ALBUMS_TABLE_NAME + " ON " + SONGS_TABLE_NAME + "." + SONGS_COLUMN_ALBUM + " = " + ALBUMS_TABLE_NAME + "." + ALBUMS_COLUMN_ID + " WHERE " + ALBUMS_TABLE_NAME + "." +
            ALBUMS_COLUMN_NAME + " = ?";


    //SELECT songs.title FROM songs INNER JOIN albums ON songs.album = albums._id WHERE albums._id = ?;

    public static final String UPDATE_ARTIST_NAME = "UPDATE " + ARTISTS_TABLE_NAME + " SET " +
            ARTISTS_COLUMN_NAME + " = ? WHERE " +
            ARTISTS_COLUMN_NAME + " = ?;";

    public static final String INSERT_ARTIST = "INSERT INTO " + ARTISTS_TABLE_NAME + "(" + ARTISTS_COLUMN_NAME +
            ") VALUES(?);";

    public static final String DELETE_SONGS_OF_ARTIST = "DELETE FROM " + SONGS_TABLE_NAME + " WHERE " + SONGS_TABLE_NAME + "." + SONGS_COLUMN_ALBUM + " = (" +
            "SELECT " + ALBUMS_TABLE_NAME + "." + ALBUMS_COLUMN_ID + " FROM " + ALBUMS_TABLE_NAME + " WHERE " + ALBUMS_TABLE_NAME + "." + ALBUMS_COLUMN_ARTIST + " = (" +
            "SELECT " + ARTISTS_TABLE_NAME + "." + ARTISTS_COLUMN_ID + " FROM " + ARTISTS_TABLE_NAME + " WHERE " + ARTISTS_TABLE_NAME + "." + ARTISTS_COLUMN_NAME + " = ?));";

    public static final String DELETE_ALBUMS_OF_ARTIST = "DELETE FROM " + ALBUMS_TABLE_NAME + " WHERE " + ALBUMS_TABLE_NAME + "." + ALBUMS_COLUMN_ARTIST + " = (" +
            "SELECT " + ARTISTS_TABLE_NAME + "." + ARTISTS_COLUMN_ID + " FROM " + ARTISTS_TABLE_NAME + " WHERE " + ARTISTS_TABLE_NAME + "." + ARTISTS_COLUMN_NAME + " = ?);";

    public static final String DELETE_ARTIST = "DELETE FROM " + ARTISTS_TABLE_NAME + " WHERE " + ARTISTS_TABLE_NAME + "." + ARTISTS_COLUMN_NAME + " = ?;";

    public static final String ADD_ALBUM = "INSERT INTO " + ALBUMS_TABLE_NAME + "(" + ALBUMS_COLUMN_NAME + ", " + ALBUMS_COLUMN_ARTIST + ") " + "VALUES(?, ?);";

    public static final String CHECK_SONG_NUMBERS_OF_ALBUM = "SELECT COUNT(*) FROM " + SONGS_TABLE_NAME + " INNER JOIN " + ALBUMS_TABLE_NAME + " ON " +
            SONGS_TABLE_NAME + "." + SONGS_COLUMN_ALBUM + " = " + ALBUMS_TABLE_NAME + "." + ALBUMS_COLUMN_ID + " WHERE " + ALBUMS_TABLE_NAME + "." + ALBUMS_COLUMN_ID + " = ?;";

    public static final String REMOVE_ALBUM = "DELETE FROM " + ALBUMS_TABLE_NAME + " WHERE " + ALBUMS_TABLE_NAME + "." + ALBUMS_COLUMN_ID + " = ?;";

    public static final String REMOVE_SONGS_OF_ALBUM = "DELETE FROM " + SONGS_TABLE_NAME + " WHERE " + SONGS_COLUMN_ALBUM + " = ?;";

    public Connection getConnection() {
        return connection;
    }

    public boolean openDatabase() {
        try{
            connection = DriverManager.getConnection(CONNECTION_STRING);
            queryArtists = connection.prepareStatement(QUERY_ARTISTS);
            queryAlbumsByArtist = connection.prepareStatement(QUERY_ALBUMS_BY_ARTIST);
            querySongsOfArtist = connection.prepareStatement(QUERY_SONGS_OF_ARTIST);
            querySongsOfAlbum = connection.prepareStatement(QUERY_SONGS_OF_ALBUM);
            updateArtistName = connection.prepareStatement(UPDATE_ARTIST_NAME);
            insertArtist = connection.prepareStatement(INSERT_ARTIST);
            deleteSongsOfArtist = connection.prepareStatement(DELETE_SONGS_OF_ARTIST);
            deleteAlbumsOfArtist = connection.prepareStatement(DELETE_ALBUMS_OF_ARTIST);
            deleteArtist = connection.prepareStatement(DELETE_ARTIST);
            addAlbum = connection.prepareStatement(ADD_ALBUM);
            checkNumberOfSongsInAlbum = connection.prepareStatement(CHECK_SONG_NUMBERS_OF_ALBUM);
            removeAlbum = connection.prepareStatement(REMOVE_ALBUM);
            removeSongsOfAlbum = connection.prepareStatement(REMOVE_SONGS_OF_ALBUM);

            return true;
        }catch(SQLException e) {
            System.out.println("Couldn't open the database");
            e.printStackTrace();
            return false;
        }
    }

    public boolean closeDatabase() {
        try{
            queryArtists.close();
            querySongsOfAlbum.close();
            querySongsOfArtist.close();
            queryAlbumsByArtist.close();
            updateArtistName.close();
            insertArtist.close();
            deleteSongsOfArtist.close();
            deleteAlbumsOfArtist.close();
            deleteArtist.close();
            addAlbum.close();
            checkNumberOfSongsInAlbum.close();
            removeAlbum.close();

            connection.close();
            return true;
        }catch(SQLException e) {
            System.out.println("Couldn't close the database");
            e.printStackTrace();
            return false;
        }
    }

    public List<Artist> queryArtists() {

        try{
            ResultSet resultSet = queryArtists.executeQuery();
            if(resultSet.next() == false) {
                System.out.println("No artist was found");
                return null;
            }

            List<Artist> artistList = new ArrayList<>();
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                String artistName = resultSet.getString(2);
                Artist artist = new Artist(id, artistName);
                artistList.add(artist);
            }

            return artistList;

        }catch(SQLException e) {
            System.out.println("Couldn't query the artists");
            e.printStackTrace();
            return null;
        }

    }

    public List<Album> queryAlbumsByArtist(String artistName) {

        try{
            queryAlbumsByArtist.setString(1, artistName);
            ResultSet resultSet = queryAlbumsByArtist.executeQuery();
            if(resultSet.next() == false) {
                System.out.println("No album was found");
                return null;
            }

            List<Album> albumList = new ArrayList<>();
            while(resultSet.next()) {
                int albumId = resultSet.getInt(1);
                String albumName = resultSet.getString(2);
                String artist = resultSet.getString(3);
                Album album = new Album(albumId, albumName, artist);
                albumList.add(album);
            }

            return albumList;

        }catch(SQLException e) {
            System.out.println("Couldn't query the albums of the artist");
            e.printStackTrace();
            return null;
        }

    }

    public List<Song> querySongsOfArtist(String artist) {

        try{
            querySongsOfArtist.setString(1, artist);
            ResultSet resultSet = querySongsOfArtist.executeQuery();

            List<Song> songList = new ArrayList<>();
            while(resultSet.next()) {
                int songId = resultSet.getInt(1);
                int track = resultSet.getInt(2);
                String title = resultSet.getString(3);
                int albumId = resultSet.getInt(4);
                Song song = new Song(songId, track, title, albumId);
                songList.add(song);
            }
            return songList;
        }catch(SQLException e) {
            System.out.println("Couldn't query the songs of artist");
            return null;
        }


    }

    public List<Song> querySongsOfAlbum(String albumName) {
        try{
            querySongsOfAlbum.setString(1, albumName);
            ResultSet resultSet = querySongsOfAlbum.executeQuery();
            List<Song> songList = new ArrayList<>();
            while(resultSet.next()) {
                int songId = resultSet.getInt(1);
                int track = resultSet.getInt(2);
                String title = resultSet.getString(3);
                int albumId = resultSet.getInt(4);
                Song song = new Song(songId, track, title, albumId);
                songList.add(song);
            }
            return songList;
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong");
            return null;
        }
    }

    public boolean findArtistName(String newArtistName) {
        List<Artist> allArtists = queryArtists();
        for(Artist artist : allArtists) {
            if(artist.getName().equals(newArtistName)) {
                return true;
            }
        }
        return false;
    }

    public boolean updateArtistName(String oldName, String newName) {
        try{
            updateArtistName.setString(1, newName);
            updateArtistName.setString(2, oldName);
            updateArtistName.execute();
            return true;
        }catch(SQLException e) {
            System.out.println("Couldn't update the artist name in the database!");
            return false;
        }

    }

    public int insertArtist(String artistName) {
        try{
            insertArtist.setString(1, artistName);
            List<Artist> artistList = queryArtists();
            for(Artist artist : artistList) {
                if(artist.getName().equals(artistName)) {
                    return -2;
                }
            }
            insertArtist.execute();
            return 0;
        }catch(SQLException e) {
            System.out.println("Couldn't insert");
            return -1;
        }

    }

    public boolean deleteArtist(String name) {
        try{
            deleteSongsOfArtist.setString(1, name);
            deleteSongsOfArtist.execute();
            deleteAlbumsOfArtist.setString(1, name);
            deleteAlbumsOfArtist.execute();
            deleteArtist.setString(1, name);
            deleteArtist.execute();
            return true;
        }catch(SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean addAlbum(String albumName, int artistId) {
        try{
            addAlbum.setString(1, albumName);
            addAlbum.setString(2, Integer.toString(artistId));
            addAlbum.execute();
            return true;

        }catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Couldn't execute the sql statement");
            return false;
        }

    }

    public int songsExistInAlbum(int albumId) {
        try{
            checkNumberOfSongsInAlbum.setString(1, Integer.toString(albumId));
            ResultSet result = checkNumberOfSongsInAlbum.executeQuery();
            int count = result.getInt(1);
            if(count != 0) {
                return 1;
            }else {
                return 0;
            }

        }catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Couldn't execute the sql statement");
            return -1;
        }
    }

    public boolean removeAlbum(int albumId, boolean songsExist) {
        if(!songsExist) {
            try{
                removeAlbum.setString(1, Integer.toString(albumId));
                removeAlbum.execute();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Album removed");
                dialog.setContentText("Album successfully removed from the albums");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.showAndWait();
                return true;

            }catch(SQLException e) {
                e.printStackTrace();
                System.out.println("SQL error");
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Error");
                dialog.setContentText("Couldn't remove the album due to SQL error");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.showAndWait();
                return false;
            }
        }else {
            try{
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Confirmation");
                dialog.setContentText("There are songs attached to this album. With removing the album the songs will also be removed. Are you sure?");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
                Optional<ButtonType> result = dialog.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.YES) {
                    removeSongsOfAlbum.setString(1, Integer.toString(albumId));
                    removeSongsOfAlbum.execute();
                    removeAlbum.setString(1, Integer.toString(albumId));
                    removeAlbum.execute();
                    Dialog<ButtonType> dialogForConfirmation = new Dialog<>();
                    dialogForConfirmation.setTitle("Album removed");
                    dialogForConfirmation.setContentText("Album and its songs have been successfully removed.");
                    dialogForConfirmation.getDialogPane().getButtonTypes().add(ButtonType.OK);
                    dialogForConfirmation.showAndWait();
                    return true;
                }else {
                    return false;
                }


            }catch(SQLException e) {
                e.printStackTrace();
                System.out.println("SQL error");
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Error");
                dialog.setContentText("Couldn't remove the album due to SQL error");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.showAndWait();
                return false;
            }
        }

    }

}


