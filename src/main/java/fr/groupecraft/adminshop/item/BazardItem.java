package fr.groupecraft.adminshop.item;

import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;

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
