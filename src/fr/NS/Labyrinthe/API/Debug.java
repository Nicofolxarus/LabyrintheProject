package fr.NS.Labyrinthe.API;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Debug {

	public static ArrayList<Player> debugpllist = new ArrayList<>();
	public static boolean console = true;

	public static void send_debug(String st) {
		for (Player p : debugpllist) {
			p.sendMessage(ChatColor.GOLD + "Labyrinthe > Debug(APIv1.0): " + st);
		}
		if (console == true) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Labyrinthe > Debug(APIv1.0): " + st);
		}
	}

	public static void send_inportantdebug(String st) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(ChatColor.RED + "-----FATALE-----  Labyrinthe > Debug(APIv1.0): " + st + ChatColor.RED + "  -----FATALE-----");
		}
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "-----FATALE-----  Labyrinthe > Debug(APIv1.0): " + st + ChatColor.RED + "  -----FATALE-----");
	}
}
