package fr.groupecraft.adminshop.utils;

import fr.groupecraft.adminshop.players.MessagePlayer;
import fr.groupecraft.adminshop.players.PlayerDB;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player pl= e.getPlayer();
        if(PlayerDB.getInstance().hasPlayer(pl.getUniqueId())) return;
        MessagePlayer.getInstance().messagePlayer(pl);
    }
}
