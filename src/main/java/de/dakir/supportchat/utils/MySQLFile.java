package de.dakir.supportchat.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class MySQLFile {

    public void setStandard() {
        FileConfiguration cfg = getFileConfiguration();

        cfg.options().copyDefaults(true);

        cfg.addDefault("host", "localhost");
        cfg.addDefault("port", "3306");
        cfg.addDefault("database", "database");
        cfg.addDefault("username", "username");
        cfg.addDefault("password", "password");

        try {
            cfg.save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getFile() {
        return new File("plugins/SupportChat/mysql.yml");
    }

    private FileConfiguration getFileConfiguration() {
        return YamlConfiguration.loadConfiguration(getFile());
    }

    public void readData() {
        FileConfiguration cfg = getFileConfiguration();

        MySQL.host = cfg.getString("host");
        MySQL.port = cfg.getString("port");
        MySQL.database = cfg.getString("database");
        MySQL.username = cfg.getString("username");
        MySQL.password = cfg.getString("password");
    }
}
