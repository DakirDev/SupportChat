package de.dakir.supportchat.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.dakir.supportchat.utils.HexxAPI;
import de.dakir.supportchat.utils.Strings;

public class PlayerChat implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onChat(AsyncPlayerChatEvent e){
		if(e.isCancelled()){
			return;
		}

        Player p = e.getPlayer();
        if(HexxAPI.isInSupportChat(p)){
        	String namecolor = Strings.userColor;
        	if(p.hasPermission("bungeesupportchat.*") || p.hasPermission("bungeesupportchat.use") || p.hasPermission("bungeesupportchat.open")){
        		namecolor = Strings.supporterColor;
        	}
        	
        	p.sendMessage(Strings.prefix + namecolor + p.getName() + " §8\u00BB " + Strings.chatColor + e.getMessage());
        	HexxAPI.getSupportChatPartner(p).sendMessage(Strings.prefix + namecolor + p.getName() + " §8\u00BB " + Strings.chatColor + e.getMessage());
        	e.setCancelled(true);
        }
	}

}