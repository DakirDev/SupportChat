package de.dakir.supportchat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import de.dakir.supportchat.commands.Support;
import de.dakir.supportchat.events.PlayerChat;
import de.dakir.supportchat.events.PlayerQuit;
import de.dakir.supportchat.utils.Data;
import de.dakir.supportchat.utils.HexxAPI;
import de.dakir.supportchat.utils.Strings;

public class PluginManager {

    //ActionBarAPI
    public static boolean works = true;
    public static String nmsver;
    public static boolean useOldMethods = false;
    //ActionBarAPI

    public static void load() {
        registerEvents();
        registerCommands();
        if (Data.enableQueueNotification) {
            registerScheduler();
        }

        nmsver = Bukkit.getServer().getClass().getPackage().getName();
        nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
        System.out.println(nmsver);

        if (nmsver.equalsIgnoreCase("v1_8_R1") || nmsver.startsWith("v1_7_")) {
            useOldMethods = true;
        }
    }

    public static void registerEvents() {
        org.bukkit.plugin.PluginManager pm = Bukkit.getPluginManager();
        Plugin pl = Main.instance;
        pm.registerEvents(new PlayerChat(), pl);
        pm.registerEvents(new PlayerQuit(), pl);
    }

    public static void registerCommands() {
        Bukkit.getPluginCommand("support").setExecutor(new Support());
    }

    public static void registerScheduler() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                if (Data.supports.size() > 0) {
                    if (Data.supports.size() == 1) {
                        HexxAPI.sendSupportMessage(Strings.onePlayerNeedSupport.substring(0, Strings.onePlayerNeedSupport.length() - 1).replace("%number%", Data.supports.size() + ""));
                    } else if (Data.supports.size() > 1) {
                        HexxAPI.sendSupportMessage(Strings.morePlayerNeedSupport.substring(0, Strings.morePlayerNeedSupport.length() - 1).replace("%number%", Data.supports.size() + ""));
                    }
                }
            }
        }, Data.queueNotificationInterval * 20L, Data.queueNotificationInterval * 20L);
    }

    public static void reloadScheudler() {
        Bukkit.getScheduler().cancelTasks(Main.instance);
        if (Data.enableQueueNotification) {
            registerScheduler();
        }
    }
}
