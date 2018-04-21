package fr.NS.Labyrinthe.Porte;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftBat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.NS.Labyrinthe.Generale.Main;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

@SuppressWarnings("deprecation")
public class BlockAnimation implements Listener {
	
	private enum Symbole{
		Cercle,Ligne;
	}
	
	public static boolean isListener = false;
	public static ArrayList<FallingBlock> fbList = new ArrayList<>();
	
	public int timer = 5;
	public int timerTemp = 5;
	
	private Symbole symbole;
	
	private double X = 0;
	private double Y = 0;
	private double Z = 0;
	
	private boolean putblock = false;
	private boolean isSpawn = false;
	private boolean isRunning = false;
	private boolean isAir = false;
	
	private int task;
	
	private FallingBlock fb;
	private Bat bat;
	
	private Material mat;
	private byte data;
	private int dataMax = 0;
	private boolean hasDataMax = false;
	
	private ArrayList<Material> mats;
	
	private ArrayList<Block> LastBlock = new ArrayList<>();
	
	private ArrayList<Block> LastBlock1 = new ArrayList<>();
	private ArrayList<Block> LastBlock2 = new ArrayList<>();
	private ArrayList<Block> LastBlock3 = new ArrayList<>();
	private int Last = 1;
	
	private Location loc1;
	private Location loc2;
	
	@SuppressWarnings("unused")
	private int rayon;
	
	public BlockAnimation(Location loc1,Location loc2,int TimeSec,Material material,byte data,boolean putblock){
		symbole = Symbole.Ligne;
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.timer = TimeSec*4;
		this.putblock = putblock;
		this.mat = material;
		this.data = data;
		if(!isListener){
			Bukkit.getPluginManager().registerEvents(this,Main.getInstance());
		}
		if(loc1.getBlockX() <= loc2.getBlockX()){
			this.X =  Math.abs(new BigDecimal(loc1.getBlockX() - loc2.getBlockX()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}else{
			this.X = -( new BigDecimal(loc1.getBlockX() - loc2.getBlockX()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}
		if(loc1.getBlockY() <= loc2.getBlockY()){
			this.Y =  Math.abs(new BigDecimal(loc1.getBlockY() - loc2.getBlockY()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}else{
			this.Y = -( new BigDecimal(loc1.getBlockY() - loc2.getBlockY()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}
		if(loc1.getBlockZ() <= loc2.getBlockZ()){
			this.Z =  Math.abs(new BigDecimal(loc1.getBlockZ() - loc2.getBlockZ()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}else{
			this.Z = -( new BigDecimal(loc1.getBlockZ() - loc2.getBlockZ()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}
	}
	public BlockAnimation(Location loc1,Location loc2,int TimeSec,Material material,int dataMax,boolean putblock){
		symbole = Symbole.Ligne;
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.timer = TimeSec*4;
		this.putblock = putblock;
		this.mat = material;
		this.dataMax = dataMax;
		this.hasDataMax = true;
		if(!isListener){
			Bukkit.getPluginManager().registerEvents(this,Main.getInstance());
		}
		if(loc1.getBlockX() <= loc2.getBlockX()){
			this.X =  Math.abs(new BigDecimal(loc1.getBlockX() - loc2.getBlockX()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}else{
			this.X = -( new BigDecimal(loc1.getBlockX() - loc2.getBlockX()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}
		if(loc1.getBlockY() <= loc2.getBlockY()){
			this.Y =  Math.abs(new BigDecimal(loc1.getBlockY() - loc2.getBlockY()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}else{
			this.Y = -( new BigDecimal(loc1.getBlockY() - loc2.getBlockY()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}
		if(loc1.getBlockZ() <= loc2.getBlockZ()){
			this.Z =  Math.abs(new BigDecimal(loc1.getBlockZ() - loc2.getBlockZ()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}else{
			this.Z = -( new BigDecimal(loc1.getBlockZ() - loc2.getBlockZ()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}
	}
	public BlockAnimation(Location loc1,Location loc2,int TimeSec,boolean putblock,Material... material){
		symbole = Symbole.Ligne;
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.timer = TimeSec*4;
		this.putblock = putblock;
		this.mats = new ArrayList<>(Arrays.asList(material));
		if(!isListener){
			Bukkit.getPluginManager().registerEvents(this,Main.getInstance());
		}
		if(loc1.getBlockX() <= loc2.getBlockX()){
			this.X =  Math.abs(new BigDecimal(loc1.getBlockX() - loc2.getBlockX()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}else{
			this.X = -( new BigDecimal(loc1.getBlockX() - loc2.getBlockX()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}
		if(loc1.getBlockY() <= loc2.getBlockY()){
			this.Y =  Math.abs(new BigDecimal(loc1.getBlockY() - loc2.getBlockY()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}else{
			this.Y = -( new BigDecimal(loc1.getBlockY() - loc2.getBlockY()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}
		if(loc1.getBlockZ() <= loc2.getBlockZ()){
			this.Z =  Math.abs(new BigDecimal(loc1.getBlockZ() - loc2.getBlockZ()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}else{
			this.Z = -( new BigDecimal(loc1.getBlockZ() - loc2.getBlockZ()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}
	}
	public BlockAnimation(Location loc1,Location loc2,int TimeSec,Material material,byte data,boolean putblock,boolean air){
		symbole = Symbole.Ligne;
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.timer = TimeSec*4;
		this.putblock = putblock;
		this.mat = material;
		this.data = data;
		this.isAir = air;
		if(!isListener){
			Bukkit.getPluginManager().registerEvents(this,Main.getInstance());
		}
		if(loc1.getBlockX() <= loc2.getBlockX()){
			this.X =  Math.abs(new BigDecimal(loc1.getBlockX() - loc2.getBlockX()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}else{
			this.X = -( new BigDecimal(loc1.getBlockX() - loc2.getBlockX()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}
		if(loc1.getBlockY() <= loc2.getBlockY()){
			this.Y =  Math.abs(new BigDecimal(loc1.getBlockY() - loc2.getBlockY()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}else{
			this.Y = -( new BigDecimal(loc1.getBlockY() - loc2.getBlockY()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}
		if(loc1.getBlockZ() <= loc2.getBlockZ()){
			this.Z =  Math.abs(new BigDecimal(loc1.getBlockZ() - loc2.getBlockZ()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}else{
			this.Z = -( new BigDecimal(loc1.getBlockZ() - loc2.getBlockZ()).divide(new BigDecimal(timer),new MathContext(2, RoundingMode.CEILING)).doubleValue());
		}
	}
	public BlockAnimation(Location centre,int rayon,int TimeSec,Material material,byte data,boolean putblock){
		symbole = Symbole.Cercle;
		this.rayon = rayon;
		this.data = data;
		this.timer = TimeSec*4;
		this.mat = material;
		this.putblock = putblock;
		
	}
	
	public void spawn(){
		if(isSpawn){
			delete();
		}
//		Location loc0 = new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+50, loc1.getZ());
		
		if(mat == null){
			fb = (FallingBlock) loc1.getWorld().spawnFallingBlock(loc1, mats.get(0), data);
		}else{
			fb = (FallingBlock) loc1.getWorld().spawnFallingBlock(loc1, mat, data);
		}
		fb.setTicksLived(999999999);
		fb.setDropItem(false);
		
		
		Location loc = new Location(loc1.getWorld(), loc1.getBlockX()+0.5, loc1.getBlockY()-0.63, loc1.getBlockZ()+0.5);
		
		bat = (Bat) loc1.getWorld().spawnEntity(loc, EntityType.BAT);
		bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999999, 1, false, false));
		bat.setTicksLived(999999999);
		bat.setMaxHealth(80);
		
		Entity nmsEntity = ((CraftEntity) bat).getHandle();
		NBTTagCompound tag = new NBTTagCompound();
		nmsEntity.c(tag);
		tag.setInt("BatFlags", 0);
		tag.setInt("NoAI", 1);
		tag.setInt("Silent", 1);
		tag.setInt("Invulnerable", 1);
		nmsEntity.f(tag);
		
		bat.setPassenger(fb);
		
		Random rand = new Random();
		if(mat == null){
			loc1.getBlock().setType(mats.get(rand.nextInt(mats.size())));
		}else{
			loc1.getBlock().setType(mat);
			if(hasDataMax){
				loc1.getBlock().setData((byte) rand.nextInt(dataMax));
			}else{
				loc1.getBlock().setData(data);
			}
		}
	}
	

	public void startAnimation(){
		if(!isRunning){
			isRunning = true;
			if(!isSpawn){
				spawn();
			}
			LastBlock1.clear();
			LastBlock2.clear();
			LastBlock3.clear();
			LastBlock.clear();
			timerTemp = timer;
			task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
				@Override
				public void run() {
					bat.setTicksLived(999999999);
					fb.setTicksLived(999999999);
					
					CraftBat cas = (CraftBat) bat;
					Entity entity = cas.getHandle();
					if(symbole == Symbole.Ligne){
						entity.locX = new BigDecimal(entity.locX).add(new BigDecimal(X)).doubleValue();
						entity.locY = new BigDecimal(entity.locY).add(new BigDecimal(Y)).doubleValue();
						entity.locZ = new BigDecimal(entity.locZ).add(new BigDecimal(Z)).doubleValue();	
//						entity.yaw = entity.yaw;
//						entity.pitch = entity.pitch;
					}//else if(symbole == Symbole.Cercle){
						
						
//					}
					
					if(putblock){
						if(isAir){
							if(Last == 1){
								for(Block bl : LastBlock1){
									bl.setType(Material.AIR);
								}
								LastBlock1.clear();
								Block b = bat.getLocation().getBlock().getRelative(BlockFace.UP);
								if(!LastBlock.contains(b)){
									LastBlock1.add(b);
									LastBlock.add(b);
								}
								Last = 2;
							}else if(Last == 2){
								for(Block bl : LastBlock2){
									bl.setType(Material.AIR);
								}
								LastBlock2.clear();
								Block b = bat.getLocation().getBlock().getRelative(BlockFace.UP);
								if(!LastBlock.contains(b)){
									LastBlock2.add(b);
									LastBlock.add(b);
								}
								Last = 3;
							}else if(Last == 3){
								for(Block bl : LastBlock3){
									bl.setType(Material.AIR);
								}
								LastBlock3.clear();
								Block b = bat.getLocation().getBlock().getRelative(BlockFace.UP);
								if(!LastBlock.contains(b)){
									LastBlock3.add(b);
									LastBlock.add(b);
								}
								Last = 1;
							}
							
						}else{
							if(Last == 1){
								setBlock(LastBlock1);
								LastBlock1.clear();
								Block b = bat.getLocation().getBlock().getRelative(BlockFace.UP);
								if(!LastBlock.contains(b)){
									LastBlock1.add(b);
									LastBlock.add(b);
								}
								Last = 2;
							}else if(Last == 2){
								setBlock(LastBlock2);
								LastBlock2.clear();
								Block b = bat.getLocation().getBlock().getRelative(BlockFace.UP);
								if(!LastBlock.contains(b)){
									LastBlock2.add(b);
									LastBlock.add(b);
								}
								Last = 3;
							}else if(Last == 3){
								setBlock(LastBlock3);
								LastBlock3.clear();
								Block b = bat.getLocation().getBlock().getRelative(BlockFace.UP);
								if(!LastBlock.contains(b)){
									LastBlock3.add(b);
									LastBlock.add(b);
								}
								Last = 1;
							}
						}
					}
					
					timerTemp--;
					if(timerTemp <= 0){
						if(putblock){
							if(isAir){
								Block b = loc2.getBlock();
								b.setType(Material.AIR);
							}else{
								Block b = loc2.getBlock();
								Random rand = new Random();
								if(mat != null){
									b.setType(mat);
									if(hasDataMax){
										b.setData((byte) rand.nextInt(dataMax));
									}else{
										b.setData(data);
									}
								}else{
									b.setType(mats.get(rand.nextInt(mats.size())));
								}
							}
						}
						stopAnimation();
					}
				}
			}, 5,5);			
		}
	}
	private void setBlock(ArrayList<Block> b){
		Random rand = new Random();
		for(Block bl : b){
			if(mat == null){
				bl.setType(mats.get(rand.nextInt(mats.size())));
			}else{
				bl.setType(mat);
				if(hasDataMax){
					bl.setData((byte) rand.nextInt(dataMax));
				}else{
					bl.setData(data);
				}
			}
		}
	}
	
	public void stopAnimation(){
		Bukkit.getScheduler().cancelTask(task);
		delete();
		isRunning = false;
		timerTemp = timer;
	}
	private void delete(){
		if(fb != null){
			fb.remove();
		}
		if(bat != null){
			bat.remove();
		}
	}
	
	@EventHandler
	public static void BlockChange(EntityBlockFormEvent e){
		if(e.getBlock().getType() != Material.SAND && e.getBlock().getType() !=  Material.GRAVEL){
			//e.setCancelled(true);
		}
	}
	
}