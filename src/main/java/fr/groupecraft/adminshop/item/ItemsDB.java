package fr.groupecraft.adminshop.item;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Sql lite usage.
 *
 * Item qu price
 */
public class ItemsDB {

    private Connection connection;
    private static ItemsDB instance;

    private ItemsDB(){
        try {
            //create the data-base and the table
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS Items ( `Item` VARCHAR NOT NULL, 'qu' INT, 'price' INT, UNIQUE(Item)");
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
     * Gets the list of all bazardItems
     *
     * @return list of all the bazardItems
     */
    public ArrayList<BazardItem> getItems() {
        ArrayList<BazardItem> items = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from 'Items' WHERE 1 = 1"); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                try {
                    ItemStack item = decodeItem(resultSet.getString("Item"));
                    items.add(new BazardItem(item, resultSet.getInt("qu"), resultSet.getInt("price")));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (items.size() == 0) return null;
        return items;
    }

    /**
     * save the list of items in the database using the {@link ItemsDB#addItem(BazardItem)} method
     * @param items the list of items
     * */
    public void saveItems(List<BazardItem> items){
        for(BazardItem item : items){
            addItem(item);
        }
    }
    /**
     * Add an item if not present in the database, otherwise, do nothing
     *
     * @param item the bazard itme to add
     */
    public void addItem(BazardItem item) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO 'Items' ('Item','qu','price') VALUES(?,?,?)")) {

            //preparation
            statement.setString(1, encodeItem(item.getItem()));
            statement.setInt(2, item.getQuantity());
            statement.setInt(3, item.getPrice());

            statement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reset all the database
     */
    public void resetDB(){
        try(PreparedStatement statement = connection.prepareStatement("DELETE FROM `Items` WHERE 1 = 1 ") ){
            statement.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * remove an item from the database
     *
     * @param item the item
     */
    public void removeItem(BazardItem item){
        if(connection == null) retryConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM 'Items' WHERE 'Item' = '" + encodeItem(item.getItem()) + "'");
            ResultSet result = preparedStatement.executeQuery();) {
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateItem(BazardItem item){

    }
    public static String encodeItem(ItemStack itemStack) {
        Gson gson =new GsonBuilder()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();

        return gson.toJson(itemStack);
    }

    public ItemStack decodeItem(String string) {
        Gson gson =new GsonBuilder()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();

        ItemStack item = gson.fromJson(string, ItemStack.class);
        return item;
    }
    public static ItemsDB getInstance(){
        if(instance==null) instance = new ItemsDB();
        return instance;
    }
}
