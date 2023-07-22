package fr.groupecraft.adminshop.players;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 * The type Sql lite usage.
 */
public class PlayerDB {

    private Connection connection;
    private static PlayerDB instance;

    private PlayerDB(){
        try {
            //create the data-base and the table
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS Pls ( `uuid` VARCHAR NOT NULL )");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * test if the connection is active
     *
     * @return the state of the connection
     */
    public boolean isConnected(){return connection != null;}

    /**
     * try to reconnect to the db
     * @return the stat of the connection
     */
    public boolean retryConnection(){
        if(connection != null) return true;
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return connection != null;
    }

    /**
     * Gets the list of all the players
     *
     * @return list of the uuid of the players
     */
    public ArrayList<UUID> getPlayers() {
        ArrayList<UUID> uuids = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select 'uuid' from 'Pls' WHERE 1 = 1"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                try {
                    uuids.add(UUID.fromString(resultSet.getString("uuid")));
                }catch (Exception e){
                    continue;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (uuids.size() == 0) return null;
        return uuids;
    }

    /**
     * Add a player.
     *
     * @param player the player to add
     */
    public void addPlayer(UUID player) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO 'Pls' ('uuid') VALUES(?)")) {

            //preparation
            statement.setString(1, player.toString());

            statement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reset all the database
     */
    public void resetDB(){
        try(PreparedStatement statement = connection.prepareStatement("DELETE FROM `Pls` WHERE 1 = 1 ") ){
            statement.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * look if the player is in the db
     *
     * @param player the player
     * @return the boolean
     */
    public boolean hasPlayer(UUID player){
        if(connection == null) retryConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT 'uuid' FROM 'Pls' WHERE 'uuid' = '" + player.toString() + "'");
            ResultSet result = preparedStatement.executeQuery();) {

            //results
            if(!result.next()) return false;
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static PlayerDB getInstance(){
        if(instance==null) instance = new PlayerDB();
        return instance;
    }
}
