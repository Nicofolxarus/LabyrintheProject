package fr.NS.Labyrinthe.Menu;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.NS.Labyrinthe.API.ItemCreator;
import fr.NS.Labyrinthe.Generale.Main;
/*
 * This plugin was create by Seraleme
 * 
 * -You are not allowed to :
 * 		-Copy the code
 * 		-Write your name on this plugin
 * 	
 * To contact me send private message on twitter
 * @Seraleme
 * 
 */
public abstract class Menu{
	
	private static ItemStack Item_Annuler = new ItemCreator(Material.STAINED_GLASS_PANE).setDurability(14).setName("§cAnnuler").getItem();
	private static ItemStack Item_Valider = new ItemCreator(Material.STAINED_GLASS_PANE).setDurability(5).setName("§aValider").getItem();
	private static ItemStack Item_Glass = new ItemCreator(Material.STAINED_GLASS_PANE).setDurability(15).setName(" ").getItem();
	private static ItemStack Item_GlassRetour = new ItemCreator(Material.STAINED_GLASS_PANE).setDurability(14).setName("§4Go Back").getItem();
	private boolean isQuit = false;
	
	public Menu(){
		
	}
	
	public void open(Player p){
		if(getInventory() != null){
			Menu menu = this;
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				@Override
				public void run() {
					p.openInventory(getInventory());
					MenuManager.menus.put(p.getUniqueId(),menu);
				}
			}, 1);
		}
	}
	public void open(Player p,Inventory inv){
		if(getInventory() != null){
			Menu menu = this;
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				@Override
				public void run() {
					p.openInventory(inv);
					MenuManager.menus.put(p.getUniqueId(),menu);
				}
			}, 1);
		}
	}
	public void addMenutoRefresh(Player p){
		Menu menu = this;
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				MenuManager.menus.put(p.getUniqueId(),menu);
			}
		}, 1);
	}
	public abstract void actualise(InventoryClickEvent e);
	public abstract void close(Player p);
	public abstract Inventory getInventory();
	
	
	
	
	public void resetQuit(){
		Menu menu = this;
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				menu.setQuit(false);
			}
		}, 2);
	}
	public boolean isQuit(){
		return isQuit;
	}
	public void setQuit(boolean isQuit){
		this.isQuit = isQuit;
	}
	public static void quitInventory(Player p){
		if(MenuManager.menus.containsKey(p.getUniqueId())){
			MenuManager.menus.remove(p.getUniqueId());
		}
	}
	
	public static boolean Condition(InventoryClickEvent e, Material Item, String Name) {
		if (e.getCurrentItem() == null)
			return false;
		if (e.getCurrentItem().getType() == Item) {
			if (e.getCurrentItem().getItemMeta().getDisplayName() == null)
				return false;
			if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Name)) {
				return true;
			}
		}
		return false;
	}
	public static boolean ConditionContain(InventoryClickEvent e, Material Item, String Name) {
		if (e.getCurrentItem() == null)
			return false;
		if (e.getCurrentItem().getType() == Item) {
			if (e.getCurrentItem().getItemMeta().getDisplayName() == null)
				return false;
			if (e.getCurrentItem().getItemMeta().getDisplayName().contains(Name)) {
				return true;
			}
		}
		return false;
	}
	public static boolean Condition(InventoryClickEvent e, Material Item) {
		if (e.getCurrentItem() == null)
			return false;
		if (e.getCurrentItem().getType() == Item) {
			return true;
		}
		return false;
	}
	public static void fillInventoryGlass(Inventory inv){
		for(int i = 0;i<inv.getSize();i++){
			if(inv.getItem(i) != null){
				if(inv.getItem(i).getType() == Material.AIR){
					inv.setItem(i, getItemGlass());
				}
			}else{
				inv.setItem(i, getItemGlass());
			}
		}
	}
	
	public static ItemStack getItem_Menu_Host() {
		return MenuManager.getItem_Menu_Host();
	}
	public static ItemStack getItem_Quitter_Partie() {
		return MenuManager.getItem_Quitter_Partie();
	}
	public static ItemStack getItemGlass(){
		return Item_Glass;
	}
	public static ItemStack getItemGlassRetour(){
		return Item_GlassRetour;
	}
	public static ItemStack getItemValider(){
		return Item_Valider;
	}
	public static ItemStack getItemAnnuler(){
		return Item_Annuler;
	}
	
	public static MenuSelectionGame getMenuSelectionGame() {
		return MenuManager.getMenuselectiongame();
	}
}
