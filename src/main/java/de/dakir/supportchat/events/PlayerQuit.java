package de.dakir.supportchat.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.dakir.supportchat.utils.Data;
import de.dakir.supportchat.utils.HexxAPI;
import de.dakir.supportchat.utils.Strings;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (Data.supports.contains(p.getUniqueId())) {
            Data.supports.remove(p.getUniqueId());
            HexxAPI.sendSupportMessage(Strings.needNoSupport.replace("%player%", p.getName()));
        } else if (HexxAPI.isInSupportChat(p)) {
            HexxAPI.closeSupportChat(p);
        }
    }

}
