package fr.NS.Labyrinthe.Events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class EventMove implements Listener {
	
	
	@EventHandler
	public void EventMoveEvent(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		loc.subtract(0,1,0);
		if(loc.getBlock().getType() == Material.DIAMOND_BLOCK){
			Vector vec = p.getLocation().getDirection();
			vec.setX(1);
			vec.setZ(0);
			vec.setY(1);
			p.setVelocity(vec);
		}
		if(loc.getBlock().getType() == Material.GOLD_BLOCK){
			Vector vec = p.getLocation().getDirection();
			vec.setX(-1);
			vec.setZ(0);
			vec.setY(1);
			p.setVelocity(vec);
		}
		if(loc.getBlock().getType() == Material.REDSTONE_BLOCK){
			Vector vec = p.getLocation().getDirection();
			vec.setY(0.5);
			vec.multiply(2);
			p.getWorld().playSound(p.getLocation(), Sound.FUSE, 1, 1);
			p.setVelocity(vec);
		}
	}
	@EventHandler
	public void Event(EntityDamageEvent e) {
		if(e.getCause() == DamageCause.FALL){
			e.setCancelled(true);
		}
	}
}
