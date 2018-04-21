package fr.NS.Labyrinthe.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class Events implements Listener {
	
	
	@EventHandler
	public void weather(WeatherChangeEvent e) {
		e.setCancelled(true);
	}
	
}
