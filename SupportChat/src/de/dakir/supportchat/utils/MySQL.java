package de.dakir.supportchat.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import de.dakir.supportchat.Main;

public class MySQL {
	
	private static int MySQLSchedulerID;
	
	public static String host;
	public static String port;
	public static String database;
	public static String username;
	public static String password;
	public static Connection con;
	
	public static void connect(){
		if(!isConnected()){
			try {
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false", username, password);
				System.out.println("[SupportChat-MySQL] Connection established!");
				
				onReconnectScheduler();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void disconnect(){
		if(isConnected()){
			try {
				con.close();
				System.out.println("[SupportChat-MySQL] Connection closed!");
				
				Bukkit.getScheduler().cancelTask(MySQLSchedulerID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void onReconnectScheduler(){
		MySQLSchedulerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
            public void run(){
            	onReconnect();
            }
        }, 600*20L, 600*20L);
    }
 
    private static void onReconnect(){
        if(con != null){
            try {
            	con.close();
            } catch(SQLException e) {
                System.err.println("[SupportChat-MySQL] Connection could not be disconnected!");
                e.printStackTrace();
            }
        }
   
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run(){
                try {
                    con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false", username, password);
                } catch(SQLException e) {
                    System.err.println("[SupportChat-MySQL] Connection failed!");
                    e.printStackTrace();
                }
            }
        }, 1L);
    }
	
	public static boolean isConnected(){
		return (con == null ? false : true);
	}
	
	public static Connection getConnection(){
		return con;
	}
	
	public static ResultSet query(String qry){
		try {
			PreparedStatement ps = con.prepareStatement(qry);
			return ps.executeQuery();
		} catch(SQLException e) {
			
		}
		return null;
	}
	
	//////////////////////////////////////////
	
	public static void createTable(){
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Data (UUID VARCHAR(100), Name VARCHAR(100), Supports INTEGER)");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

