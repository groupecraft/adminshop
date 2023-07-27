package fr.groupecraft.adminshop.bazard;

import fr.groupecraft.adminshop.item.BazardItem;
import fr.groupecraft.adminshop.item.ItemsDB;
import fr.groupecraft.adminshop.players.MessagePlayer;
import fr.groupecraft.adminshop.utils.Config;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
/**
 * class that manage all the items in the bazard
 * */
public class Items {

    protected List<BazardItem> items;

    protected Items(){
        items = new ArrayList<>();
    }
    /**
     * add the item to the list, update the open bazard and notify everyone of it
     *
     * @param item the item to add
     */
    public void add(BazardItem item){

    }
    /**
     * save the list of items using the ItemDB class and erase all the content in the database
     * */
    public void saveItems(){
        ItemsDB.getInstance().resetDB();
        ItemsDB.getInstance().saveItems(items);
    }

    /**
     * load the list of items in the db using the ItemDB class and reset the list of items in the list in the ram
     * @warning all items in the list will be removed
     */
    public void loadItems(){
        items.clear();
        items= ItemsDB.getInstance().getItems();
    }

    /**
     * add an item to the list and if the auto-save is enabled it saves it in the database
     * and set the last item added to the bazard with the {@link fr.groupecraft.adminshop.players.MessagePlayer#setLastItem(ItemStack)} method
     * @param item the item to add to the list
     * */
    public void addItem(@NonNull BazardItem item) throws IllegalArgumentException {
        if(hasItem(item)) throw new IllegalArgumentException("Item already in the shop");
        items.add(item);
        MessagePlayer.getInstance().setLastItem(item.getItem());
        if(Config.AUTO_SAVE.getBool()) ItemsDB.getInstance().addItem(item);
    }

    /**
    * get the Bazard item with an certain itemStack by comparing the encoded string of the itemstack with the one of all items in the list
    * @param item non null itemStack
    * @return the bazard item or null if not in the list
     */
    public BazardItem getBazardItem(@NonNull ItemStack item){
        String str= ItemsDB.encodeItem(item);
        for(BazardItem item2 : items){
            if(str.equals(ItemsDB.encodeItem(item2.getItem()))) return item2;
        }
        return null;
    }
    /**
     * test if the ItemStack of a bazard item is in the list by using the {@link Items#getBazardItem(ItemStack)} method
     * */
    public boolean hasItem(@NonNull BazardItem item){
        return getBazardItem(item.getItem()) !=null;
    }

}
