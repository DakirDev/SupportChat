package de.dakir.supportchat.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import de.dakir.supportchat.Main;
import de.dakir.supportchat.PluginManager;
import de.dakir.supportchat.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

@CommandAlias("%command.support")
public class Support extends BaseCommand {

    @Default
    public void support(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Strings.onlyPlayer);
        }
        Player p = (Player) sender;

        if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.use") || p.hasPermission("supportchat.open")) {
            p.sendMessage(Strings.prefix + Strings.noSupportRequest);
        } else {
            if (HexxAPI.isSupporterOnline()) {
                if (HexxAPI.isInSupportChat(p)) {
                    p.sendMessage(Strings.prefix + Strings.inSupportChat);
                } else {
                    if (!(Data.supports.contains(p.getUniqueId()))) {
                        Data.supports.add(p.getUniqueId());
                        p.sendMessage(Strings.prefix + Strings.joinSupportQueue);
                        HexxAPI.sendSupportMessage(Strings.needSupport.replace("%player%", p.getName()));
                    } else {
                        Data.supports.remove(p.getUniqueId());
                        p.sendMessage(Strings.prefix + Strings.leaveSupportQueue);
                        HexxAPI.sendSupportMessage(Strings.needNoSupport.replace("%player%", p.getName()));
                    }
                }
            } else {
                p.sendMessage(Strings.prefix + Strings.noSupporterOnline);
            }
        }
    }

    @Subcommand("close")
    @CommandPermission("supportchat.close")
    public void supportClose(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Strings.onlyPlayer);
        }
        Player p = (Player) sender;

        if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.use") || p.hasPermission("supportchat.close")) {
            if (HexxAPI.isInSupportChat(p)) {
                HexxAPI.closeSupportChat(p);
            } else {
                p.sendMessage(Strings.prefix + Strings.notInSupportChat);
            }
        } else {
            p.sendMessage(Strings.prefix + Strings.noPermission);
        }
    }

    @Subcommand("help")
    @CommandPermission("supportchat.help")
    public void supportHelp(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Strings.onlyPlayer);
        }
        Player p = (Player) sender;

        if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.use") || p.hasPermission("supportchat.help")) {
            p.sendMessage(Strings.header);
            p.sendMessage(Strings.sc_help);
            if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.use")) {
                p.sendMessage(Strings.sc);
            }
            if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.use") || p.hasPermission("supportchat.list")) {
                p.sendMessage(Strings.sc_list);
            }
            if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.use") || p.hasPermission("supportchat.close")) {
                p.sendMessage(Strings.sc_close);
            }
            if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.use") || p.hasPermission("supportchat.open")) {
                p.sendMessage(Strings.sc_open);
            }
            if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.use") || p.hasPermission("supportchat.open")) {
                p.sendMessage(Strings.sc_open_player);
            }
            if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.data.*") || p.hasPermission("supportchat.data.list")) {
                p.sendMessage(Strings.sc_data_list);
            }
            if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.data.*") || p.hasPermission("supportchat.data.stats")) {
                p.sendMessage(Strings.sc_data_stats);
            }
            if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.data.*") || p.hasPermission("supportchat.data.delete")) {
                p.sendMessage(Strings.sc_data_delete);
            }
            if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.data.*") || p.hasPermission("supportchat.data.add")) {
                p.sendMessage(Strings.sc_data_add);
            }
            if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.data.*") || p.hasPermission("supportchat.data.remove")) {
                p.sendMessage(Strings.sc_data_remove);
            }
            if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.data.*") || p.hasPermission("supportchat.data.set")) {
                p.sendMessage(Strings.sc_data_set);
            }
            if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.data.*") || p.hasPermission("supportchat.data.reset")) {
                p.sendMessage(Strings.sc_data_reset);
            }
            if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.reload")) {
                p.sendMessage(Strings.sc_reload);
            }
            p.sendMessage(Strings.footer);
        } else {
            p.sendMessage(Strings.prefix + Strings.noPermission);
        }
    }

    @Subcommand("list")
    @CommandPermission("supportchat.list")
    public void supportList(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Strings.onlyPlayer);
        }
        Player p = (Player) sender;

        if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.use") || p.hasPermission("supportchat.list")) {
            if (Data.supports.size() == 0) {
                p.sendMessage(Strings.prefix + Strings.noSupportNeeded);
            } else if (Data.supports.size() == 1) {
                p.sendMessage(Strings.prefix + Strings.onePlayerNeedSupport.replace("%number%", Data.supports.size() + ""));
                p.sendMessage(Strings.prefix + Strings.playerInQueue.replace("%player%", Bukkit.getPlayer(Data.supports.get(0)).getName()));
            } else {
                p.sendMessage(Strings.prefix + Strings.morePlayerNeedSupport.replace("%number%", Data.supports.size() + ""));
                for (int i = 0; i < Data.supports.size(); i++) {
                    p.sendMessage(Strings.prefix + Strings.playerInQueue.replace("%player%", Bukkit.getPlayer(Data.supports.get(i)).getName()));
                }
            }
        } else {
            p.sendMessage(Strings.prefix + Strings.noPermission);
        }
    }

    @Subcommand("open")
    @CommandPermission("supportchat.open")
    @CommandCompletion("@players @nothing")
    public void supportOpen(CommandSender sender, @Optional String player) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Strings.onlyPlayer);
        }
        Player p = (Player) sender;

        if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.use") || p.hasPermission("supportchat.open")) {
            if (player == null) {
                if (Data.supports.size() == 0) {
                    p.sendMessage(Strings.prefix + Strings.noSupportNeeded);
                } else {
                    Player o = Bukkit.getPlayer(Data.supports.get(0));
                    HexxAPI.openSupportChat(p, o);
                }
            } else {
                try {
                    Player o = Bukkit.getPlayer(player);
                    if (o.hasPermission("supportchat.*") || o.hasPermission("supportchat.use") || o.hasPermission("supportchat.open")) {
                        p.sendMessage(Strings.prefix + Strings.playerIsSupporter);
                    } else {
                        HexxAPI.openSupportChat(p, o);
                    }
                } catch (NullPointerException e) {
                    p.sendMessage(Strings.prefix + Strings.playerNotFound);
                }
            }
        } else {
            p.sendMessage(Strings.prefix + Strings.noPermission);
        }
    }

    @Subcommand("reload")
    @CommandPermission("supportchat.reload")
    public void supportReload(CommandSender sender) {
        if (sender instanceof Player) {
            if (!(sender.hasPermission("supportchat.*") || sender.hasPermission("supportchat.reload"))) {
                sender.sendMessage(Strings.prefix + Strings.noPermission);
                return;
            }
        }

        ConfigManager.load();
        PluginManager.reloadScheudler();

        if (sender instanceof Player) {
            sender.sendMessage(Strings.prefix + Strings.reload);
        } else {
            sender.sendMessage(Strings.cprefix + Strings.reload);
        }
    }

    /*
    Data commands
     */
    @Subcommand("data add")
    @CommandPermission("supportchat.data.add")
    @CommandCompletion("@players @nothing")
    public void supportDataAdd(CommandSender sender, String player, String number) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Strings.onlyPlayer);
        }
        Player p = (Player) sender;

        if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.data.*") || p.hasPermission("supportchat.data.add")) {
            if (Data.enableMySQL) {
                if (MySQLData.isNameExists(player)) {
                    try {
                        int amount = Integer.parseInt(number);
                        MySQLData.addSupportsByName(player, amount);
                        p.sendMessage(Strings.prefix + Strings.dataPlayerSupportsAdded.replace("%player%", player).replace("%number%", String.valueOf(amount)));
                    } catch (NumberFormatException e) {
                        p.sendMessage(Strings.prefix + Strings.noNumber);
                    }
                } else {
                    p.sendMessage(Strings.prefix + Strings.playerNotFound);
                }
            } else {
                p.sendMessage(Strings.prefix + Strings.mysqlNotEnabled);
            }
        } else {
            p.sendMessage(Strings.prefix + Strings.noPermission);
        }
    }

    @Subcommand("data delete")
    @CommandPermission("supportchat.data.delete")
    @CommandCompletion("@players @nothing")
    public void supportDataDelete(CommandSender sender, String player) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Strings.onlyPlayer);
        }
        Player p = (Player) sender;

        if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.data.*") || p.hasPermission("supportchat.data.delete")) {
            if (Data.enableMySQL) {
                if (MySQLData.isNameExists(player)) {
                    MySQLData.deletePlayer(player);
                    p.sendMessage(Strings.prefix + Strings.dataPlayerDeleted.replace("%player%", player));
                } else {
                    p.sendMessage(Strings.prefix + Strings.playerNotFound);
                }
            } else {
                p.sendMessage(Strings.prefix + Strings.mysqlNotEnabled);
            }
        } else {
            p.sendMessage(Strings.prefix + Strings.noPermission);
        }
    }

    @Subcommand("data list")
    @CommandPermission("supportchat.data.list")
    public void supportDataList(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Strings.onlyPlayer);
        }
        Player p = (Player) sender;

        if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.data.*") || p.hasPermission("supportchat.data.list")) {
            if (Data.enableMySQL) {
                HashMap<Integer, String> list = MySQLData.getList();
                p.sendMessage(Strings.dataPlayerList_header);
                for (int i = 0; i < list.size(); i++) {
                    int id = i + 1;
                    p.sendMessage(Strings.dataPlayerList_entry.replace("%id%", String.valueOf(id)).replace("%player%", list.get(id)).replace("%number%", String.valueOf(MySQLData.getSupportsByName(list.get(id)))));
                }
                p.sendMessage(Strings.dataPlayerList_footer);
            } else {
                p.sendMessage(Strings.prefix + Strings.mysqlNotEnabled);
            }
        } else {
            p.sendMessage(Strings.prefix + Strings.noPermission);
        }
    }

    @Subcommand("data remove")
    @CommandPermission("supportchat.data.remove")
    @CommandCompletion("@players @nothing")
    public void supportDataRemove(CommandSender sender, String player, String number) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Strings.onlyPlayer);
        }
        Player p = (Player) sender;

        if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.data.*") || p.hasPermission("supportchat.data.remove")) {
            if (Data.enableMySQL) {
                if (MySQLData.isNameExists(player)) {
                    try {
                        int amount = Integer.parseInt(number);
                        MySQLData.removeSupportsByName(player, amount);
                        p.sendMessage(Strings.prefix + Strings.dataPlayerSupportsRemoved.replace("%player%", player).replace("%number%", String.valueOf(amount)));
                    } catch (NumberFormatException e) {
                        p.sendMessage(Strings.prefix + Strings.noNumber);
                    }
                } else {
                    p.sendMessage(Strings.prefix + Strings.playerNotFound);
                }
            } else {
                p.sendMessage(Strings.prefix + Strings.mysqlNotEnabled);
            }
        } else {
            p.sendMessage(Strings.prefix + Strings.noPermission);
        }
    }

    @Subcommand("data reset")
    @CommandPermission("supportchat.data.reset")
    public void supportDataReset(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Strings.onlyPlayer);
        }
        Player p = (Player) sender;

        if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.data.*") || p.hasPermission("supportchat.data.reset")) {
            if (Data.enableMySQL) {
                MySQLData.resetSupports();
                p.sendMessage(Strings.prefix + Strings.dataSupportsReset);
            } else {
                p.sendMessage(Strings.prefix + Strings.mysqlNotEnabled);
            }
        } else {
            p.sendMessage(Strings.prefix + Strings.noPermission);
        }
    }

    @Subcommand("data set")
    @CommandPermission("supportchat.data.set")
    @CommandCompletion("@players @nothing")
    public void supportDataSet(CommandSender sender, String player, String number) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Strings.onlyPlayer);
        }
        Player p = (Player) sender;

        if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.data.*") || p.hasPermission("supportchat.data.set")) {
            if (Data.enableMySQL) {
                if (MySQLData.isNameExists(player)) {
                    try {
                        int amount = Integer.parseInt(number);
                        MySQLData.setSupportsByName(player, amount);
                        p.sendMessage(Strings.prefix + Strings.dataPlayerSupportsSet.replace("%player%", player).replace("%number%", String.valueOf(amount)));
                    } catch (NumberFormatException e) {
                        p.sendMessage(Strings.prefix + Strings.noNumber);
                    }
                } else {
                    p.sendMessage(Strings.prefix + Strings.playerNotFound);
                }
            } else {
                p.sendMessage(Strings.prefix + Strings.mysqlNotEnabled);
            }
        } else {
            p.sendMessage(Strings.prefix + Strings.noPermission);
        }
    }

    @Subcommand("data stats")
    @CommandPermission("supportchat.data.stats")
    @CommandCompletion("@players @nothing")
    public void supportDataStats(CommandSender sender, String player) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Strings.onlyPlayer);
        }
        Player p = (Player) sender;

        if (p.hasPermission("supportchat.*") || p.hasPermission("supportchat.data.*") || p.hasPermission("supportchat.data.stats")) {
            if (Data.enableMySQL) {
                if (MySQLData.isNameExists(player)) {
                    int number = MySQLData.getSupportsByName(player);
                    p.sendMessage(Strings.prefix + Strings.dataPlayerStats.replace("%player%", player).replace("%number%", String.valueOf(number)));
                } else {
                    p.sendMessage(Strings.prefix + Strings.playerNotFound);
                }
            } else {
                p.sendMessage(Strings.prefix + Strings.mysqlNotEnabled);
            }
        } else {
            p.sendMessage(Strings.prefix + Strings.noPermission);
        }
    }
}