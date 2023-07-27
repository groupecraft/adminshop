package fr.groupecraft.adminshop.item;

import fr.groupecraft.adminshop.utils.Lang;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * The type Bazard item.
 */
public class BazardItem {

    private final ItemStack item;
    private int quantity, price;

    /**
     * Instantiates a new Bazard item.
     *
     * @param item     the item
     * @param quantity the quantity
     * @param price    the price
     */
    public BazardItem(@NonNull ItemStack item, int quantity, int price) {
        this.item = item;
        item.setAmount(1);
        this.quantity = quantity;
        this.price = price;

    }

    /**
     * Gets the item
     *
     * @return the item
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * return the item with the additional lore saying the quantity and the price of the item
     * */
    public ItemStack getShopItem(){
        ItemStack it= item.clone();
        ItemMeta meta= it.getItemMeta();
        List<String> lore = meta.getLore();
        lore.add(Lang.NUMDER_BEFORE.getString()+quantity+Lang.NUMBER_AFTER.getString());
        lore.add(Lang.PRICE_BEFORE.getString()+price+Lang.PRICE_AFTER.getString());
        meta.setLore(lore);
        it.setItemMeta(meta);
        return it;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() { return quantity; }

    /**
     * synchronise method that buy an item and decrease the quantity by one
     */
    public synchronized void buyItem(){ quantity--;}

}
