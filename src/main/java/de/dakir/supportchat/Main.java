package de.dakir.supportchat;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.dakir.supportchat.metrics.Metrics;
import de.dakir.supportchat.utils.ConfigManager;
import de.dakir.supportchat.utils.Data;
import de.dakir.supportchat.utils.MySQL;
import de.dakir.supportchat.utils.MySQLFile;
import de.dakir.supportchat.utils.Strings;

public class Main extends JavaPlugin {
	
	public static Plugin instance;
	
	@Override
	public void onEnable() {
		Main.instance = this;
		
		ConfigManager.checkFiles();
		ConfigManager.load();
		
		PluginManager.load();
		
		MySQLFile file2 = new MySQLFile();
		file2.setStandard();
		file2.readData();
		
		if(Data.enableMySQL) {
			MySQL.connect();
			MySQL.createTable();
		}
		
		@SuppressWarnings("unused")
		Metrics metrics = new Metrics(this, 2413);
		
		System.out.println(Strings.cprefix + "Plugin has been enabled!");
	}
	
	@Override
	public void onDisable(){
		MySQL.disconnect();
		
		System.out.println(Strings.cprefix + "Plugin has been disabled!");
	}

}
