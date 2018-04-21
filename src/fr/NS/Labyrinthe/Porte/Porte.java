package fr.NS.Labyrinthe.Porte;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;

public class Porte {
	
	private Location loc1;
	private Location loc2;
	
	private int Timesec;
	
	private Material material;
	private byte data;
	private int dataMax;
	private boolean hasDataMax = false;
	
	private boolean putblock;
	private boolean portedouble;
	private boolean epaisseur = false;
	private boolean orientation;
	private int Size;

	private ArrayList<BlockAnimation> BlocksClose = new ArrayList<>();
	private ArrayList<BlockAnimation> BlocksOpen = new ArrayList<>();
	private Material[] mats;
	
	public Porte(int Size,Location loc1,Location loc2,int TimeSec,Material material,byte data,boolean portedouble,boolean putblock){
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.Timesec = TimeSec;
		this.material = material;
		this.data = data;
		this.putblock = putblock;
		this.portedouble = portedouble;
		this.Size = Size;
		calcule();
	}
	public Porte(int Size,Location loc1,Location loc2,int TimeSec,Material material,byte data,boolean portedouble){
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.Timesec = TimeSec;
		this.material = material;
		this.data = data;
		this.putblock = true;
		this.portedouble = portedouble;
		this.Size = Size;
		calcule();
	}
	public Porte(int Size,Location loc1,Location loc2,int TimeSec,Material material,byte data,boolean portedouble,boolean putblock,boolean epaisseur,boolean orientantion){
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.Timesec = TimeSec;
		this.material = material;
		this.data = data;
		this.putblock = putblock;
		this.portedouble = portedouble;
		this.Size = Size;
		this.epaisseur = epaisseur;
		this.orientation = orientantion;
		calcule();
	}
	public Porte(int Size,Location loc1,Location loc2,int TimeSec,boolean epaisseur,Material material,byte data,boolean portedouble,boolean orientantion){
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.Timesec = TimeSec;
		this.material = material;
		this.data = data;
		this.putblock = true;
		this.portedouble = portedouble;
		this.Size = Size;
		this.epaisseur = epaisseur;
		this.orientation = orientantion;
		calcule();
	}
	public Porte(int Size,Location loc1,Location loc2,int TimeSec,boolean epaisseur,Material material,byte data,boolean portedouble,boolean orientantion,Material... MatBlocks){
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.Timesec = TimeSec;
		this.material = material;
		this.data = data;
		this.putblock = true;
		this.portedouble = portedouble;
		this.Size = Size;
		this.epaisseur = epaisseur;
		this.orientation = orientantion;
		this.mats = MatBlocks;
		calcule();
	}
	public Porte(Location loc1,Location loc2,int TimeSec,int hauteur,boolean hasEpaisseur,Material material,int DataMax,boolean portedouble,boolean orientantion){
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.Timesec = TimeSec;
		this.material = material;
		this.dataMax = DataMax;
		this.hasDataMax = true;
		this.putblock = true;
		this.portedouble = portedouble;
		this.Size = hauteur;
		this.epaisseur = hasEpaisseur;
		this.orientation = orientantion;
		calcule();
	}
	public void Open(){
		for(BlockAnimation b : BlocksOpen){
			b.startAnimation();
		}
	}
	public void Close(){
		for(BlockAnimation b : BlocksClose){
			b.startAnimation();
		}
	}
	
	public void calcule(){
		if(mats != null){
			BlocksClose.add(new BlockAnimation(loc1, loc2, 5, putblock, mats));
			if(portedouble){
				double x = 0;
				double z = 0;
				double y = 0;
				if(loc1.getX() > loc2.getX()){
					x = loc1.getX() - ((loc1.getX()-loc2.getX())/2);
				}else{
					x = loc1.getX() + ((loc2.getX()-loc1.getX())/2);
				}
				if(loc1.getZ() > loc2.getZ()){
					z = loc1.getZ() - ((loc1.getZ()-loc2.getZ())/2);
				}else{
					z = loc1.getZ() + ((loc2.getZ()-loc1.getZ())/2);
				}
				if(loc1.getY() > loc2.getY()){
					y = loc1.getY() - ((loc1.getY()-loc2.getY())/2);
				}else{
					y = loc1.getY() + ((loc2.getY()-loc1.getY())/2);
				}
				if(epaisseur){
					if(orientation){ //Axe Z
						int dif = Math.abs(new BigDecimal(loc1.getBlockZ()).subtract(new BigDecimal(loc2.getBlockZ())).intValue());
						if(loc1.getBlockZ() > loc2.getBlockZ()){
							for(int zi = 0; zi <= dif;zi++){
								for(int i = 0; i <= Size;i++){
									BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()-zi), new Location(loc1.getWorld(), x, y+i,loc1.getBlockZ()-zi), Timesec, putblock, mats));
									BlocksClose.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()+zi), new Location(loc1.getWorld(), x, y+i, loc2.getBlockZ()+zi), Timesec, putblock, mats));
									BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), x, y+i,loc1.getBlockZ()-zi),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()-zi), Timesec, material, data, putblock,true));
									BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), x, y+i, loc2.getBlockZ()+zi),new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()+zi), Timesec, material, data, putblock,true));
								}
							}
						}else{
							for(int zi = 0; zi <= dif;zi++){
								for(int i = 0; i <= Size;i++){
									BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()+zi), new Location(loc1.getWorld(), x, y+i,loc1.getBlockZ()+zi), Timesec, putblock, mats));
									BlocksClose.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()-zi), new Location(loc1.getWorld(), x, y+i, loc2.getBlockZ()-zi), Timesec, putblock, mats));
									BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), x, y+i,loc1.getBlockZ()+zi),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()+zi), Timesec, material, data, putblock,true));
									BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), x, y+i, loc2.getBlockZ()-zi),new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()-zi), Timesec, material, data, putblock,true));
								}
							}
						}
					}else{//Axe X
						int dif = Math.abs(new BigDecimal(loc1.getBlockX()).subtract(new BigDecimal(loc2.getBlockX())).intValue());
						if(loc1.getBlockX() > loc2.getBlockX()){
							for(int xi = 0; xi <= dif;xi++){
								for(int i = 0; i <= Size;i++){
									BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX()-xi, loc1.getY()+i, loc1.getZ()), new Location(loc1.getWorld(), loc1.getBlockX()-xi, y+i,z), Timesec, putblock, mats));
									BlocksClose.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX()+xi, loc2.getY()+i, loc2.getZ()), new Location(loc1.getWorld(), loc2.getBlockX()+xi, y+i, z), Timesec, putblock, mats));
									BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getBlockX()-xi, y+i,z),new Location(loc1.getWorld(), loc1.getX()-xi, loc1.getY()+i, loc1.getZ()), Timesec, material, data, putblock,true));
									BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), loc2.getBlockX()+xi, y+i, z),new Location(loc2.getWorld(), loc2.getX()+xi, loc2.getY()+i, loc2.getZ()), Timesec, material, data, putblock,true));
								}
							}
						}else{
							for(int xi = 0; xi <= dif;xi++){
								for(int i = 0; i <= Size;i++){
									BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX()+xi, loc1.getY()+i, loc1.getZ()), new Location(loc1.getWorld(), loc1.getBlockX()+xi, y+i,z), Timesec, putblock, mats));
									BlocksClose.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX()-xi, loc2.getY()+i, loc2.getZ()), new Location(loc1.getWorld(), loc2.getBlockX()-xi, y+i, z), Timesec, putblock, mats));
									BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getBlockX()+xi, y+i,z),new Location(loc1.getWorld(), loc1.getX()+xi, loc1.getY()+i, loc1.getZ()), Timesec, material, data, putblock,true));
									BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), loc2.getBlockX()-xi, y+i, z),new Location(loc2.getWorld(), loc2.getX()-xi, loc2.getY()+i, loc2.getZ()), Timesec, material, data, putblock,true));
								}
							}
						}
					}
				}else{
					for(int i = 0; i <= Size;i++){
						BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()), new Location(loc1.getWorld(), x, y+i, z), Timesec, putblock, mats));
						BlocksClose.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()), new Location(loc1.getWorld(), x, y+i, z), Timesec, putblock, mats));
						BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), x, y+i, z),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()), Timesec, material, data, putblock,true));
						BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), x, y+i, z),new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()), Timesec, material, data, putblock,true));
					}
				}
			}else{
				if(epaisseur){
					if(orientation){
						int dif = Math.abs(new BigDecimal(loc1.getBlockZ()).subtract(new BigDecimal(loc2.getBlockZ())).intValue());
						if(loc1.getBlockZ() > loc2.getBlockZ()){
							for(int zi = 0; zi <= dif;zi++){
								for(int i = 0; i <= Size;i++){
									BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()-zi), new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc1.getZ()-zi), Timesec, putblock, mats));
									BlocksOpen.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()-zi),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()-zi), Timesec, material, data, putblock,true));
								}
							}
						}else{
							for(int zi = 0; zi <= dif;zi++){
								for(int i = 0; i <= Size;i++){
									BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()+zi), new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc1.getZ()+zi), Timesec, putblock, mats));
									BlocksOpen.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()+zi),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()+zi), Timesec, material, data, putblock,true));
								}
							}
						}
					}else{
						int dif = Math.abs(new BigDecimal(loc1.getBlockX()).subtract(new BigDecimal(loc2.getBlockX())).intValue());
						if(loc1.getBlockX() > loc2.getBlockX()){
							for(int xi = 0; xi <= dif;xi++){
								for(int i = 0; i <= Size;i++){
									BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()-xi), new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc1.getZ()-xi), Timesec, putblock, mats));
									BlocksOpen.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()-xi),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()-xi), Timesec, material, data, putblock,true));
								}
							}
						}else{
							for(int xi = 0; xi <= dif;xi++){
								for(int i = 0; i <= Size;i++){
									BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()+xi), new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc1.getZ()+xi), Timesec, putblock, mats));
									BlocksOpen.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()+xi),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()+xi), Timesec, material, data, putblock,true));
								}
							}
						}
					}
				}else{
					for(int i = 0; i <= Size;i++){
						BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()), new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()), Timesec, putblock, mats));
						BlocksOpen.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()), Timesec, material, data, putblock,true));
					}
				}
			}
			//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		}else{
			if(hasDataMax){
				if(portedouble){
					double x = 0;
					double z = 0;
					double y = 0;
					if(loc1.getX() > loc2.getX()){
						x = loc1.getX() - ((loc1.getX()-loc2.getX())/2);
					}else{
						x = loc1.getX() + ((loc2.getX()-loc1.getX())/2);
					}
					if(loc1.getZ() > loc2.getZ()){
						z = loc1.getZ() - ((loc1.getZ()-loc2.getZ())/2);
					}else{
						z = loc1.getZ() + ((loc2.getZ()-loc1.getZ())/2);
					}
					if(loc1.getY() > loc2.getY()){
						y = loc1.getY() - ((loc1.getY()-loc2.getY())/2);
					}else{
						y = loc1.getY() + ((loc2.getY()-loc1.getY())/2);
					}
					if(epaisseur){
						if(orientation){ //Axe Z
							int dif = Math.abs(new BigDecimal(loc1.getBlockZ()).subtract(new BigDecimal(loc2.getBlockZ())).intValue());
							if(loc1.getBlockZ() > loc2.getBlockZ()){
								for(int zi = 0; zi <= dif;zi++){
									for(int i = 0; i <= Size;i++){
										BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()-zi), new Location(loc1.getWorld(), x, y+i,loc1.getBlockZ()-zi), Timesec, material,dataMax, putblock));
										BlocksClose.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()+zi), new Location(loc1.getWorld(), x, y+i, loc2.getBlockZ()+zi), Timesec, material,dataMax, putblock));
										BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), x, y+i,loc1.getBlockZ()-zi),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()-zi), Timesec, material, data, putblock,true));
										BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), x, y+i, loc2.getBlockZ()+zi),new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()+zi), Timesec, material, data, putblock,true));
									}
								}
							}else{
								for(int zi = 0; zi <= dif;zi++){
									for(int i = 0; i <= Size;i++){
										BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()+zi), new Location(loc1.getWorld(), x, y+i,loc1.getBlockZ()+zi), Timesec, material,dataMax, putblock));
										BlocksClose.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()-zi), new Location(loc1.getWorld(), x, y+i, loc2.getBlockZ()-zi), Timesec, material,dataMax, putblock));
										BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), x, y+i,loc1.getBlockZ()+zi),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()+zi), Timesec, material, data, putblock,true));
										BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), x, y+i, loc2.getBlockZ()-zi),new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()-zi), Timesec, material, data, putblock,true));
									}
								}
							}
						}else{//Axe X
							int dif = Math.abs(new BigDecimal(loc1.getBlockX()).subtract(new BigDecimal(loc2.getBlockX())).intValue());
							if(loc1.getBlockX() > loc2.getBlockX()){
								for(int xi = 0; xi <= dif;xi++){
									for(int i = 0; i <= Size;i++){
										BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX()-xi, loc1.getY()+i, loc1.getZ()), new Location(loc1.getWorld(), loc1.getBlockX()-xi, y+i,z), Timesec, material,dataMax, putblock));
										BlocksClose.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX()+xi, loc2.getY()+i, loc2.getZ()), new Location(loc1.getWorld(), loc2.getBlockX()+xi, y+i, z), Timesec, material,dataMax, putblock));
										BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getBlockX()-xi, y+i,z),new Location(loc1.getWorld(), loc1.getX()-xi, loc1.getY()+i, loc1.getZ()), Timesec, material, data, putblock,true));
										BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), loc2.getBlockX()+xi, y+i, z),new Location(loc2.getWorld(), loc2.getX()+xi, loc2.getY()+i, loc2.getZ()), Timesec, material, data, putblock,true));
									}
								}
							}else{
								for(int xi = 0; xi <= dif;xi++){
									for(int i = 0; i <= Size;i++){
										BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX()+xi, loc1.getY()+i, loc1.getZ()), new Location(loc1.getWorld(), loc1.getBlockX()+xi, y+i,z), Timesec, material,dataMax, putblock));
										BlocksClose.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX()-xi, loc2.getY()+i, loc2.getZ()), new Location(loc1.getWorld(), loc2.getBlockX()-xi, y+i, z), Timesec, material,dataMax, putblock));
										BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getBlockX()+xi, y+i,z),new Location(loc1.getWorld(), loc1.getX()+xi, loc1.getY()+i, loc1.getZ()), Timesec, material, data, putblock,true));
										BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), loc2.getBlockX()-xi, y+i, z),new Location(loc2.getWorld(), loc2.getX()-xi, loc2.getY()+i, loc2.getZ()), Timesec, material, data, putblock,true));
									}
								}
							}
						}
					}else{
						for(int i = 0; i <= Size;i++){
							BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()), new Location(loc1.getWorld(), x, y+i, z), Timesec, material,dataMax, putblock));
							BlocksClose.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()), new Location(loc1.getWorld(), x, y+i, z), Timesec, material,dataMax, putblock));
							BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), x, y+i, z),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()), Timesec, material, data, putblock,true));
							BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), x, y+i, z),new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()), Timesec, material, data, putblock,true));
						}
					}
				}else{
					if(epaisseur){
						if(orientation){
							int dif = Math.abs(new BigDecimal(loc1.getBlockZ()).subtract(new BigDecimal(loc2.getBlockZ())).intValue());
							if(loc1.getBlockZ() > loc2.getBlockZ()){
								for(int zi = 0; zi <= dif;zi++){
									for(int i = 0; i <= Size;i++){
										BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()-zi), new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc1.getZ()-zi), Timesec, material,dataMax, putblock));
										BlocksOpen.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()-zi),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()-zi), Timesec, material, data, putblock,true));
									}
								}
							}else{
								for(int zi = 0; zi <= dif;zi++){
									for(int i = 0; i <= Size;i++){
										BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()+zi), new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc1.getZ()+zi), Timesec, material,dataMax, putblock));
										BlocksOpen.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()+zi),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()+zi), Timesec, material, data, putblock,true));
									}
								}
							}
						}else{
							int dif = Math.abs(new BigDecimal(loc1.getBlockX()).subtract(new BigDecimal(loc2.getBlockX())).intValue());
							if(loc1.getBlockX() > loc2.getBlockX()){
								for(int xi = 0; xi <= dif;xi++){
									for(int i = 0; i <= Size;i++){
										BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()-xi), new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc1.getZ()-xi), Timesec, material,dataMax, putblock));
										BlocksOpen.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()-xi),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()-xi), Timesec, material, data, putblock,true));
									}
								}
							}else{
								for(int xi = 0; xi <= dif;xi++){
									for(int i = 0; i <= Size;i++){
										BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()+xi), new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc1.getZ()+xi), Timesec, material,dataMax, putblock));
										BlocksOpen.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()+xi),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()+xi), Timesec, material, data, putblock,true));
									}
								}
							}
						}
					}else{
						for(int i = 0; i <= Size;i++){
							BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()), new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()), Timesec, material, data, putblock));
							BlocksOpen.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()), Timesec, material, data, putblock,true));
						}
					}
				}
			}else{
				if(portedouble){
					double x = 0;
					double z = 0;
					double y = 0;
					if(loc1.getX() > loc2.getX()){
						x = loc1.getX() - ((loc1.getX()-loc2.getX())/2);
					}else{
						x = loc1.getX() + ((loc2.getX()-loc1.getX())/2);
					}
					if(loc1.getZ() > loc2.getZ()){
						z = loc1.getZ() - ((loc1.getZ()-loc2.getZ())/2);
					}else{
						z = loc1.getZ() + ((loc2.getZ()-loc1.getZ())/2);
					}
					if(loc1.getY() > loc2.getY()){
						y = loc1.getY() - ((loc1.getY()-loc2.getY())/2);
					}else{
						y = loc1.getY() + ((loc2.getY()-loc1.getY())/2);
					}
					if(epaisseur){
						if(orientation){ //Axe Z
							int dif = Math.abs(new BigDecimal(loc1.getBlockZ()).subtract(new BigDecimal(loc2.getBlockZ())).intValue());
							if(loc1.getBlockZ() > loc2.getBlockZ()){
								for(int zi = 0; zi <= dif;zi++){
									for(int i = 0; i <= Size;i++){
										BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()-zi), new Location(loc1.getWorld(), x, y+i,loc1.getBlockZ()-zi), Timesec, material, data, putblock));
										BlocksClose.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()+zi), new Location(loc1.getWorld(), x, y+i, loc2.getBlockZ()+zi), Timesec, material, data, putblock));
										BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), x, y+i,loc1.getBlockZ()-zi),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()-zi), Timesec, material, data, putblock,true));
										BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), x, y+i, loc2.getBlockZ()+zi),new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()+zi), Timesec, material, data, putblock,true));
									}
								}
							}else{
								for(int zi = 0; zi <= dif;zi++){
									for(int i = 0; i <= Size;i++){
										BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()+zi), new Location(loc1.getWorld(), x, y+i,loc1.getBlockZ()+zi), Timesec, material, data, putblock));
										BlocksClose.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()-zi), new Location(loc1.getWorld(), x, y+i, loc2.getBlockZ()-zi), Timesec, material, data, putblock));
										BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), x, y+i,loc1.getBlockZ()+zi),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()+zi), Timesec, material, data, putblock,true));
										BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), x, y+i, loc2.getBlockZ()-zi),new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()-zi), Timesec, material, data, putblock,true));
									}
								}
							}
						}else{//Axe X
							int dif = Math.abs(new BigDecimal(loc1.getBlockX()).subtract(new BigDecimal(loc2.getBlockX())).intValue());
							if(loc1.getBlockX() > loc2.getBlockX()){
								for(int xi = 0; xi <= dif;xi++){
									for(int i = 0; i <= Size;i++){
										BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX()-xi, loc1.getY()+i, loc1.getZ()), new Location(loc1.getWorld(), loc1.getBlockX()-xi, y+i,z), Timesec, material, data, putblock));
										BlocksClose.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX()+xi, loc2.getY()+i, loc2.getZ()), new Location(loc1.getWorld(), loc2.getBlockX()+xi, y+i, z), Timesec, material, data, putblock));
										BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getBlockX()-xi, y+i,z),new Location(loc1.getWorld(), loc1.getX()-xi, loc1.getY()+i, loc1.getZ()), Timesec, material, data, putblock,true));
										BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), loc2.getBlockX()+xi, y+i, z),new Location(loc2.getWorld(), loc2.getX()+xi, loc2.getY()+i, loc2.getZ()), Timesec, material, data, putblock,true));
									}
								}
							}else{
								for(int xi = 0; xi <= dif;xi++){
									for(int i = 0; i <= Size;i++){
										BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX()+xi, loc1.getY()+i, loc1.getZ()), new Location(loc1.getWorld(), loc1.getBlockX()+xi, y+i,z), Timesec, material, data, putblock));
										BlocksClose.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX()-xi, loc2.getY()+i, loc2.getZ()), new Location(loc1.getWorld(), loc2.getBlockX()-xi, y+i, z), Timesec, material, data, putblock));
										BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getBlockX()+xi, y+i,z),new Location(loc1.getWorld(), loc1.getX()+xi, loc1.getY()+i, loc1.getZ()), Timesec, material, data, putblock,true));
										BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), loc2.getBlockX()-xi, y+i, z),new Location(loc2.getWorld(), loc2.getX()-xi, loc2.getY()+i, loc2.getZ()), Timesec, material, data, putblock,true));
									}
								}
							}
						}
					}else{
						for(int i = 0; i <= Size;i++){
							BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()), new Location(loc1.getWorld(), x, y+i, z), Timesec, material, data, putblock));
							BlocksClose.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()), new Location(loc1.getWorld(), x, y+i, z), Timesec, material, data, putblock));
							BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), x, y+i, z),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()), Timesec, material, data, putblock,true));
							BlocksOpen.add(new BlockAnimation(new Location(loc1.getWorld(), x, y+i, z),new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()), Timesec, material, data, putblock,true));
						}
					}
				}else{
					if(epaisseur){
						if(orientation){
							int dif = Math.abs(new BigDecimal(loc1.getBlockZ()).subtract(new BigDecimal(loc2.getBlockZ())).intValue());
							if(loc1.getBlockZ() > loc2.getBlockZ()){
								for(int zi = 0; zi <= dif;zi++){
									for(int i = 0; i <= Size;i++){
										BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()-zi), new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc1.getZ()-zi), Timesec, material, data, putblock));
										BlocksOpen.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()-zi),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()-zi), Timesec, material, data, putblock,true));
									}
								}
							}else{
								for(int zi = 0; zi <= dif;zi++){
									for(int i = 0; i <= Size;i++){
										BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()+zi), new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc1.getZ()+zi), Timesec, material, data, putblock));
										BlocksOpen.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()+zi),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()+zi), Timesec, material, data, putblock,true));
									}
								}
							}
						}else{
							int dif = Math.abs(new BigDecimal(loc1.getBlockX()).subtract(new BigDecimal(loc2.getBlockX())).intValue());
							if(loc1.getBlockX() > loc2.getBlockX()){
								for(int xi = 0; xi <= dif;xi++){
									for(int i = 0; i <= Size;i++){
										BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()-xi), new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc1.getZ()-xi), Timesec, material, data, putblock));
										BlocksOpen.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()-xi),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()-xi), Timesec, material, data, putblock,true));
									}
								}
							}else{
								for(int xi = 0; xi <= dif;xi++){
									for(int i = 0; i <= Size;i++){
										BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()+xi), new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc1.getZ()+xi), Timesec, material, data, putblock));
										BlocksOpen.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()+xi),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()+xi), Timesec, material, data, putblock,true));
									}
								}
							}
						}
					}else{
						for(int i = 0; i <= Size;i++){
							BlocksClose.add(new BlockAnimation(new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()), new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()), Timesec, material, data, putblock));
							BlocksOpen.add(new BlockAnimation(new Location(loc2.getWorld(), loc2.getX(), loc2.getY()+i, loc2.getZ()),new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+i, loc1.getZ()), Timesec, material, data, putblock,true));
						}
					}
				}
			}
		}

	}
	
	
}
