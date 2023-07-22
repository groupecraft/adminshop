package fr.groupecraft.adminshop.players;

import fr.groupecraft.adminshop.item.ItemsDB;
import fr.groupecraft.adminshop.utils.Config;
import fr.groupecraft.adminshop.utils.Lang;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Date;

public class MessagePlayer {

    private static MessagePlayer instance;
    private ItemStack item;
    private long time;

    private MessagePlayer(){

    }
    public static MessagePlayer getInstance(){
        if(instance==null)instance = new MessagePlayer();
        return instance;
    }
    public ItemStack getItem(){ return item; }
    /**
     * save the item as the last item added in the bazard
     * and save the time when it was added
     *
     * @param item the last item added in the bazard
     * */
    public void setLastItem(ItemStack item){
        if(item==null)return;
        this.item=item;
        this.time=new Date().getTime();
    }
    /**
     * message the player to inform him that a new item has been added to the bazard if he hadn't received a message yet
     * and if it hasn't been too long since the last item was added if the message time is able in the configuration
     * @param player the player
     * */
    public void messagePlayer(@NonNull Player player){
        if(PlayerDB.getInstance().hasPlayer(player.getUniqueId())) return; //the player already received the message
        if(new Date().getTime()-time> Config.MESSAGE_TIME.getInt()){//if the message time is disable Date().getTime()-time>0>-1
            item=null;
            time=0;
            return;
        }
        player.sendMessage(Lang.MESSAGE_BEFORE.getString() + item.getItemMeta().getDisplayName()+ Lang.MESSAGE_AFTER.getString());
        PlayerDB.getInstance().addPlayer(player.getUniqueId());
    }
}
