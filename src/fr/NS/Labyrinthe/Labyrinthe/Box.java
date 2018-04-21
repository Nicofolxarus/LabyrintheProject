package fr.NS.Labyrinthe.Labyrinthe;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class Box {

	private int x;
	private int y;
	private int z;
	private int dx;
	private int dy;
	private int dz;
	private World w;
	
	public Box(Integer x, Integer y, Integer z, Integer dx, Integer dy, Integer dz,World w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.w = w;
	}
	
	@SuppressWarnings("deprecation")
	public void generate(){
		for (int x = getX(); x <= getDx(); x++) {
			for (int y = getY(); y <= getDy(); y++) {
				for (int z = getZ(); z <= getDz(); z++) {
					
					Location pastLoc = new Location(getWorld(), x, y, z);
					if(pastLoc.getBlock().getType() != Material.SMOOTH_BRICK){
						pastLoc.getBlock().setType(Material.SMOOTH_BRICK);
						Random random = new Random();
						int rand = random.nextInt(3);
						pastLoc.getBlock().setData((byte) rand);
					}
					
					
				}
			}
		}	
	}
	@SuppressWarnings("deprecation")
	public void generateSol(){
		for (int x = getX(); x <= getDx(); x++) {
			for (int y = getY(); y <= getDy(); y++) {
				for (int z = getZ(); z <= getDz(); z++) {
					Random random = new Random();
					int r = random.nextInt(100)+1;
					Location pastLoc = new Location(getWorld(), x, y, z);
					
					if(r <= 20){
						if(pastLoc.getBlock().getType() != Material.DIRT){
							pastLoc.getBlock().setType(Material.DIRT);
							pastLoc.getBlock().setData((byte) 1);
						}
					}else{
						if(pastLoc.getBlock().getType() != Material.SMOOTH_BRICK){
							pastLoc.getBlock().setType(Material.SMOOTH_BRICK);
							
							int rand = random.nextInt(3);
							pastLoc.getBlock().setData((byte) rand);
						}
					}
					
					
					
					
				}
			}
		}	
	}
	public void generate(Material material){
		for (int x = getX(); x <= getDx(); x++) {
			for (int y = getY(); y <= getDy(); y++) {
				for (int z = getZ(); z <= getDz(); z++) {
					Location pastLoc = new Location(getWorld(), x, y, z);
					if(pastLoc.getBlock().getType() != material){
						pastLoc.getBlock().setType(material);
					}
				}
			}
		}
	}
	@SuppressWarnings("deprecation")
	public void generate(Material material,byte data){
		for (int x = getX(); x <= getDx(); x++) {
			for (int y = getY(); y <= getDy(); y++) {
				for (int z = getZ(); z <= getDz(); z++) {
					Location pastLoc = new Location(getWorld(), x, y, z);
					if(pastLoc.getBlock().getType() != material && pastLoc.getBlock().getData() != data){
						pastLoc.getBlock().setType(material);
						pastLoc.getBlock().setData(data);
					}
				}
			}
		}	
	}
	@SuppressWarnings("deprecation")
	public void generate(Material... materials){
		int size = materials.length;		
		for (int x = getX(); x <= getDx(); x++) {
			for (int y = getY(); y <= getDy(); y++) {
				for (int z = getZ(); z <= getDz(); z++) {
					Location pastLoc = new Location(getWorld(), x, y, z);
					Random random = new Random();
					int rand = random.nextInt(size);
					Material mat = materials[rand];
					if(pastLoc.getBlock().getType() != mat){
						pastLoc.getBlock().setType(mat);
						if(mat == Material.LEAVES || mat == Material.LEAVES_2){
							pastLoc.getBlock().setData((byte) 4);
						}
					}
				}
			}
		}	
	}
	public void delete(){
		for (int x = getX(); x <= getDx(); x++) {
			for (int y = getY(); y <= getDy(); y++) {
				for (int z = getZ(); z <= getDz(); z++) {
					Location pastLoc = new Location(getWorld(), x, y, z);
					if(pastLoc.getBlock().getType() != Material.AIR){
						pastLoc.getBlock().setType(Material.AIR);	
					}
				}
			}
		}	
	}
	
	public void delete(Material material){
		for (int x = getX(); x <= getDx(); x++) {
			for (int y = getY(); y <= getDy(); y++) {
				for (int z = getZ(); z <= getDz(); z++) {
					Location pastLoc = new Location(getWorld(), x, y, z);
					if(pastLoc.getBlock().getType() != Material.AIR){
						if(pastLoc.getBlock().getType() != material){
							pastLoc.getBlock().setType(Material.AIR);
						}
					}
				}
			}
		}	
	}
	public void deleteOnly(Material material){
		for (int x = getX(); x <= getDx(); x++) {
			for (int y = getY(); y <= getDy(); y++) {
				for (int z = getZ(); z <= getDz(); z++) {
					Location pastLoc = new Location(getWorld(), x, y, z);
					if(pastLoc.getBlock().getType() != Material.AIR){
						if(pastLoc.getBlock().getType() == material){
							pastLoc.getBlock().setType(Material.AIR);
						}
					}
				}
			}
		}	
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public int getDz() {
		return dz;
	}

	public void setDz(int dz) {
		this.dz = dz;
	}

	public World getWorld() {
		return w;
	}

	public void setWorld(World w) {
		this.w = w;
	}
}