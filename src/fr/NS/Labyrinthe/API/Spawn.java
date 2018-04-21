package fr.NS.Labyrinthe.API;

import org.bukkit.Location;
import org.bukkit.Material;

import fr.NS.Labyrinthe.Configuration.Message;

/*
 * This plugin was create by Seraleme
 * 
 * -You are not allowed to :
 * 		-Copy the code
 * 		-Write your name on this plugin
 * 	
 * To contact ous please send private message on twitter
 * @Seraleme
 * 
 */

public class Spawn {

	public static void generation() {
		for (int y = 199; y <= 204; y++) {
			for (int x = -20; x <= 20; x++) {
				for (int z = -20; z <= 20; z++) {
					Location block = new Location(Message.world, x, y, z);
					block.getBlock().setType(Material.BARRIER);
				}
			}
		}
		for (int y = 200; y <= 204; y++) {
			for (int x = -19; x <= 19; x++) {
				for (int z = -19; z <= 19; z++) {
					Location block = new Location(Message.world, x, y, z);
					block.getBlock().setType(Material.AIR);
				}
			}
		}
	}
	
	public static void remove() {
		for (int y = 204; y >= 199; y--) {
			for (int x = -20; x <= 20; x++) {
				for (int z = -20; z <= 20; z++) {
					Location block = new Location(Message.world, x, y, z);
					if(block != null){
						if(block.getBlock() != null){
							block.getBlock().setType(Material.AIR);
						}
					}
				}
			}
		}
	}

}
