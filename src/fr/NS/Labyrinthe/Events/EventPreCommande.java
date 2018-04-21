package fr.NS.Labyrinthe.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.NS.Labyrinthe.Monde.Monde;
import fr.NS.Labyrinthe.Timer.Timer;

public class EventPreCommande implements Listener {
	@EventHandler
	public static void onRide(PlayerCommandPreprocessEvent e) {
		String com = e.getMessage().toLowerCase();
//		Player p = e.getPlayer();
		if (com.contains("/rl") || com.contains("/bukkit:rl") || com.contains("/reload") || com.contains("/bukkit:reload")) {
			e.setCancelled(true);
			Timer.stop();
			for(Player pl : Bukkit.getOnlinePlayers()){
				pl.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
			}
			Monde.deleteFlatWorld();
		}
		
	}
}
