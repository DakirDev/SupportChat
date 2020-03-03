package de.dakir.supportchat.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.dakir.supportchat.PluginManager;

public class HexxAPI {
	
	public static void sendSupportMessage(String message){
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.hasPermission("supportchat.*") || p.hasPermission("supportchat.use") || p.hasPermission("supportchat.open")){
				p.sendMessage(Strings.prefix + message);
				sendActionBar(p, Strings.prefix + message);
			}
		}
	}
	
	public static boolean isSupporterOnline(){
		boolean online = false;
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.hasPermission("supportchat.*") || p.hasPermission("supportchat.use") || p.hasPermission("supportchat.open")){
				online = true;
			}
		}
		return online;
	}
	
	public static Player getSupportChatPartner(Player p){
	    if(Data.inSupport.containsKey(p.getUniqueId())){
	    	return Bukkit.getPlayer(Data.inSupport.get(p.getUniqueId()));
	    }
	    for(UUID uuid : Data.inSupport.keySet()){
	    	if(Data.inSupport.get(uuid).equals(p.getUniqueId())){
	    		return Bukkit.getPlayer(uuid);
	    	}
	    }
	    return null;
	}
	
	public static boolean isInSupportChat(Player p){
	    if((Data.inSupport.containsKey(p.getUniqueId()) || (Data.inSupport.containsValue(p.getUniqueId())))){
	    	return true;
	    }else{
	    	return false;
	    }
	}
	
	public static void closeSupportChat(Player p){
	    if(isInSupportChat(p)){
	    	if(Data.inSupport.containsKey(p.getUniqueId())){
	    		Bukkit.getPlayer(Data.inSupport.get(p.getUniqueId())).sendMessage(Strings.prefix + Strings.closeSupportChat);
        		p.sendMessage(Strings.prefix + Strings.closeSupportChat);
		        Data.inSupport.remove(p.getUniqueId());
	    	}else{
		        for(UUID uuid : Data.inSupport.keySet()){
		        	if(Data.inSupport.get(uuid).equals(p.getUniqueId())){
		        		Data.inSupport.remove(uuid);
		        		Bukkit.getPlayer(uuid).sendMessage(Strings.prefix + Strings.closeSupportChat);
		        		p.sendMessage(Strings.prefix + Strings.closeSupportChat);
		        	}
		        }
	    	}
	    }
	}
	
	public static void openSupportChat(Player supporter, Player spieler){
		if(HexxAPI.isInSupportChat(supporter)){
			supporter.sendMessage(Strings.prefix + Strings.inSupportChat);
		}else if(HexxAPI.isInSupportChat(spieler)){
			supporter.sendMessage(Strings.prefix + Strings.playerIsInSupportChat.replace("%player%", spieler.getName()));
		}else{
			if(Data.supports.contains(spieler.getUniqueId())){
				Data.supports.remove(spieler.getUniqueId());
			}
			Data.inSupport.put(supporter.getUniqueId(), spieler.getUniqueId());
			
			if(Data.enableMySQL) {
				if((!(MySQLData.isPlayerExists(supporter.getUniqueId().toString()))) && (supporter.hasPermission("supportchat.*") || supporter.hasPermission("supportchat.use") || supporter.hasPermission("supportchat.open"))){
					MySQLData.createPlayer(supporter.getUniqueId());
					MySQLData.addSupport(supporter.getUniqueId());
				}else{
					MySQLData.addSupport(supporter.getUniqueId());
					MySQLData.updateName(supporter.getUniqueId());
				}
			}
			
			spieler.sendMessage(Strings.prefix + Strings.openSupportChat);
			spieler.sendMessage(Strings.prefix + Strings.openSupportChat_head.replace("%player%", supporter.getName()));
			spieler.sendMessage(Strings.prefix + Strings.openSupportChat_user.replace("%player%", spieler.getName()));
			spieler.sendMessage(Strings.prefix + Strings.openSupportChat_space);
			spieler.sendMessage(Strings.prefix + Strings.openSupportChat_hellomessage.replace("%player%", supporter.getName()));
			supporter.sendMessage(Strings.prefix + Strings.openSupportChat);
			supporter.sendMessage(Strings.prefix + Strings.openSupportChat_head.replace("%player%", supporter.getName()));
			supporter.sendMessage(Strings.prefix + Strings.openSupportChat_user.replace("%player%", spieler.getName()));
			supporter.sendMessage(Strings.prefix + Strings.openSupportChat_space);
			supporter.sendMessage(Strings.prefix + Strings.openSupportChat_hellomessage.replace("%player%", supporter.getName()));
		}
	}
	
	public static void sendActionBar(Player player, String message) {
		if (!player.isOnline()) {
            return;
        }

        if (PluginManager.nmsver.startsWith("v1_12_") || PluginManager.nmsver.startsWith("v1_13_") || PluginManager.nmsver.startsWith("v1_14_") || PluginManager.nmsver.startsWith("v1_15_")) {
            sendActionBarPost112(player, message);
        } else {
            sendActionBarPre112(player, message);
        }
	}
	
	private static void sendActionBarPost112(Player player, String message) {
		String nmsver = PluginManager.nmsver;
		if (!player.isOnline()) {
            return;
        }

        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + PluginManager.nmsver + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(player);
            Object ppoc;
            Class<?> c4 = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
            Class<?> c5 = Class.forName("net.minecraft.server." + nmsver + ".Packet");
            Class<?> c2 = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
            Class<?> c3 = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
            Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + nmsver + ".ChatMessageType");
            Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
            Object chatMessageType = null;
            for (Object obj : chatMessageTypes) {
                if (obj.toString().equals("GAME_INFO")) {
                    chatMessageType = obj;
                }
            }
            Object o = c2.getConstructor(new Class<?>[]{String.class}).newInstance(message);
            ppoc = c4.getConstructor(new Class<?>[]{c3, chatMessageTypeClass}).newInstance(o, chatMessageType);
            Method m1 = craftPlayerClass.getDeclaredMethod("getHandle");
            Object h = m1.invoke(craftPlayer);
            Field f1 = h.getClass().getDeclaredField("playerConnection");
            Object pc = f1.get(h);
            Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c5);
            m5.invoke(pc, ppoc);
        } catch (Exception ex) {
            ex.printStackTrace();
            PluginManager.works = false;
        }
	}
	
	private static void sendActionBarPre112(Player player, String message) {
		String nmsver = PluginManager.nmsver;
		if (!player.isOnline()) {
            return;
        }

        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(player);
            Object ppoc;
            Class<?> c4 = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
            Class<?> c5 = Class.forName("net.minecraft.server." + nmsver + ".Packet");
            if (PluginManager.useOldMethods) {
                Class<?> c2 = Class.forName("net.minecraft.server." + nmsver + ".ChatSerializer");
                Class<?> c3 = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
                Method m3 = c2.getDeclaredMethod("a", String.class);
                Object cbc = c3.cast(m3.invoke(c2, "{\"text\": \"" + message + "\"}"));
                ppoc = c4.getConstructor(new Class<?>[]{c3, byte.class}).newInstance(cbc, (byte) 2);
            } else {
                Class<?> c2 = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
                Class<?> c3 = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
                Object o = c2.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                ppoc = c4.getConstructor(new Class<?>[]{c3, byte.class}).newInstance(o, (byte) 2);
            }
            Method m1 = craftPlayerClass.getDeclaredMethod("getHandle");
            Object h = m1.invoke(craftPlayer);
            Field f1 = h.getClass().getDeclaredField("playerConnection");
            Object pc = f1.get(h);
            Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c5);
            m5.invoke(pc, ppoc);
        } catch (Exception ex) {
            ex.printStackTrace();
            PluginManager.works = false;
        }
	}
}
