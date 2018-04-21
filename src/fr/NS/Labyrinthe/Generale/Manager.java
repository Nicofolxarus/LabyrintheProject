package fr.NS.Labyrinthe.Generale;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.NS.Labyrinthe.Commandes.ComDebug;
import fr.NS.Labyrinthe.Commandes.ComLaby;
import fr.NS.Labyrinthe.Events.EventJoinLeave;
import fr.NS.Labyrinthe.Events.EventMove;
import fr.NS.Labyrinthe.Events.EventPreCommande;
import fr.NS.Labyrinthe.Events.Events;
import fr.NS.Labyrinthe.Menu.MenuManager;
import fr.NS.Labyrinthe.Timer.Timer;

public class Manager {
	
	public static void RegisterEvents(Main pl){
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new EventMove(),pl);
		pm.registerEvents(new EventJoinLeave(),pl);
		pm.registerEvents(new Events(),pl);
		pm.registerEvents(new EventPreCommande(),pl);
		pm.registerEvents(new Timer(),pl);
		pm.registerEvents(new MenuManager(), pl);
	}
	
	public static void RegisterCommandes(Main pl){
		Bukkit.getPluginCommand("debug").setExecutor(new ComDebug());
		Bukkit.getPluginCommand("laby").setExecutor(new ComLaby());
		
	}
	
	public static void RegisterTimers(){
		Timer.start();
	}
}
