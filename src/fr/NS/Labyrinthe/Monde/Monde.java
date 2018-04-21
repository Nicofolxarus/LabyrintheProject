package fr.NS.Labyrinthe.Monde;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.scheduler.BukkitTask;

import fr.NS.Labyrinthe.Configuration.Message;
import fr.NS.Labyrinthe.Generale.Main;

public class Monde {
	private static BukkitTask teleportation;
	private static boolean isFinishTpRegeneration = false;
	private static int FinishTpRegeneration;
	private static boolean inRegeneration = false;
	
	public static void deleteFlatWorld(){
		if(!inRegeneration){
			inRegeneration = true;
			Bukkit.broadcastMessage(Message.Prefix+Message.ColorFonce+"Redémarrage en cours !");
			//teleport
			AsyncTeleportation(new ArrayList<Player>(Bukkit.getOnlinePlayers()), ((World) Bukkit.getWorlds().toArray()[0]).getHighestBlockAt(0, 0).getLocation(),true);
			
			FinishTpRegeneration = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
				@Override
				public void run() {
					if(isFinishTpRegeneration){
						isFinishTpRegeneration = false;
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
							@Override
							public void run() {
								regenerateWorld2(Message.world);
								Bukkit.getScheduler().cancelTask(FinishTpRegeneration);
							}
						},20);
					}
				}
			},20,20);
		}
	}
	
	private static void regenerateWorld2(World world){
		if(world != null){
			deleteWorld(world.getName());
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				Bukkit.reload();
			}	
		},100);
	}
	
	public static int yMax = 60;
	public static void createFlatWorld(){
		try {
			Message.world = createWorld("Labyrinthe", Environment.NORMAL,WorldType.FLAT,new ChunkGenerator() {
			    @SuppressWarnings("deprecation")
				@Override
			    public byte[] generate(World world, Random random, int Cx, int Cz) {
			        byte[] blocks = new byte[32768];//Empty byte array
			        int x,y,z;
			        
			        y = 0;
			        for(x = 0;x < 16; ++x){
			        	for(z = 0;z < 16; ++z){
				        	blocks[((x * 16 + z) * 128 + y)] = (byte) Material.BEDROCK.getId();
				        }
			        }
			        
			        for(x = 0;x < 16; ++x){
			        	for(z = 0;z < 16; ++z){
			        		for(y = 1;y < yMax; ++y){
			        			blocks[((x * 16 + z) * 128 + y)] = (byte) Material.STONE.getId();
			        		}
				        }
			        }
			        
			        y = yMax;
			        for(x = 0;x < 16; ++x){
			        	for(z = 0;z < 16; ++z){
				        	blocks[((x * 16 + z) * 128 + y)] = (byte) Material.BEDROCK.getId();
				        }
			        }
			        return blocks;
			    }
			},new OrePopulator());
		} catch (Exception e) {
			Message.world = (World) Bukkit.getWorlds().toArray()[0];
		}
		Message.centre = new Location(Message.world, 0, 200, 0);
	}

	public static void deleteWorld(String world) {
		Bukkit.unloadWorld(world, true);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				deleteWorld(new File(world));
			}
		},20);
	}

	private static void deleteWorld(File path) {
		if (path.exists()) {
			File files[] = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteWorld(files[i]);
				} else {
					files[i].delete();
				}
			}
			Bukkit.getConsoleSender().sendMessage(Message.Prefix + "Delete Completed !");
		}
	}
	
	private static World createWorld(String name,Environment environment,WorldType worldtype,ChunkGenerator generate,BlockPopulator... bplist){
		World w1 = Bukkit.getWorld(name);
		if(w1 == null){
			WorldCreator wc = new WorldCreator(name);
			Long MapSeed = new Random().nextLong();
			wc.seed(MapSeed);
			wc.environment(environment);
			wc.type(worldtype);
			wc.generator(generate);
			World w = wc.createWorld();
			for(BlockPopulator bp : bplist){
				w.getPopulators().add(bp);
			}
			return w;
		}else{
			return w1;
		}
	}
	
	public static void AsyncTeleportation(ArrayList<Player> list,Location l,boolean fini){
		teleportation = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
			Integer current_player = 0;
			Boolean finish = false;
			Location loc = l;
			@Override
			public void run() {
				Long timer = System.currentTimeMillis();
				// Generation Diffences jours
				while (System.currentTimeMillis() - 50 < timer) {
					current_player++;
					if (list.size() >= current_player) {
						Player p = list.get(current_player - 1);
						if(loc.getChunk() != null){
							if (!loc.getChunk().isLoaded()) {
								loc.getChunk().load();
							}
						}
						p.teleport(loc);
					}else {
						finish = true;
						break;
					}
				}
				if(finish){
					isFinishTpRegeneration = fini;
					teleportation.cancel();
				}
			}
		}, 0, 1);
	}
}
