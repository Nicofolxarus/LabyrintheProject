package fr.NS.Labyrinthe.Menu;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.NS.Labyrinthe.API.ItemCreator;
import fr.NS.Labyrinthe.Configuration.Message;
import fr.NS.Labyrinthe.Gestion.Game;
import fr.NS.Labyrinthe.Gestion.Joueur;
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
public class MenuManager implements Listener{
	public static HashMap<UUID,Menu> menus = new HashMap<>();
	
	private static ItemStack Item_Menu_Host = new ItemCreator(Material.REDSTONE_COMPARATOR).setName(Message.ColorFonce+"Host").addLore("").addLore(Message.Chat_fleche+Message.ColorClair+"Configurez la partie").setGlow(true).getItem();
	
	private static ItemStack Item_Création_Partie = new ItemCreator(Material.PAPER).setName(Message.ColorFonce+"Créer").addLore("").addLore(Message.Chat_fleche+Message.ColorClair+"Créer une partie").setGlow(true).getItem();
	private static ItemStack Item_Rejoindre_Partie = new ItemCreator(Material.COMPASS).setName(Message.ColorFonce+"Rejoindre une partie").setGlow(true).getItem();
	
	private static ItemStack Item_Quitter_Partie = new ItemCreator(Material.BED).setName(Message.ColorFonce+"Quitter la partie").setGlow(true).getItem();
	
	private static MenuSelectionGame menuselectiongame = new MenuSelectionGame();
	
	@EventHandler
	public static void InteractEvent(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == null)
			return;
		if (e.getAction() == Action.PHYSICAL)
			return;
		if (e.getItem() == null)
			return;
		if (e.getItem().getType() == Material.AIR)
			return;
		if(Interact(e,Item_Création_Partie.getType(), Item_Création_Partie.getItemMeta().getDisplayName())){
			e.setCancelled(true);
			new MenuValidation().open(p);
			return;
		}
		if(Interact(e,Item_Rejoindre_Partie.getType(), Item_Rejoindre_Partie.getItemMeta().getDisplayName())){
			e.setCancelled(true);
			getMenuselectiongame().open(p);
			return;
		}
		if(Interact(e,Item_Menu_Host.getType(), Item_Menu_Host.getItemMeta().getDisplayName())){
			e.setCancelled(true);
			Joueur j = Joueur.getJoueur(p);
			if(j.getGame() != null){
				j.getGame().getMenuhost().open(p);
			}
			return;
		}
		if(Interact(e,Item_Quitter_Partie.getType(), Item_Quitter_Partie.getItemMeta().getDisplayName())){
			e.setCancelled(true);
			Joueur j = Joueur.getJoueur(p);
			Game g = j.getGame();
			if(g != null){
				if(g.isLeader(j) && g.getLeaders().size() == 1){
					g.delete();
					
				}else{
					g.removeMembre(j);
					for(Player o : Bukkit.getOnlinePlayers()){
						if(o != p){
							if(Joueur.getJoueur(o).getGame() == null){
								o.showPlayer(p);
								p.showPlayer(o);
							}else{
								o.hidePlayer(p);
								p.hidePlayer(o);
							}
						}
					}
					p.getInventory().clear();
					p.teleport(Message.centre);
					p.getInventory().setItem(0, MenuManager.getItem_Rejoindre_Partie());
					p.getInventory().setItem(4, MenuManager.getItem_Création_Partie());
				}
			}
			return;
		}
	}
	
	@EventHandler
	public static void ClickEvent(InventoryClickEvent e) {
		UUID uuid = e.getWhoClicked().getUniqueId();
		if(!menus.isEmpty()){
			if(menus.containsKey(uuid)){
				menus.get(uuid).actualise(e);
				return;
			}
		}
	}
	@EventHandler
	public static void CloseEvent(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if(p != null){
			if(menus.containsKey(p.getUniqueId())){
				Menu menu = menus.get(p.getUniqueId());
				menus.remove(p.getUniqueId());
				if(!menu.isQuit()){
					menu.close(p);
				}
			}
		}
	}

	public static boolean Interact(PlayerInteractEvent e, Material Item, String Name) {
		if ((e.getItem().getType() == Item)
				&& ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))) {
			if (e.getItem().getItemMeta().getDisplayName() == null)
				return false;
			if (e.getItem().getItemMeta().getDisplayName().equals(Name)) {
				return true;
			}
		}
		return false;
	}
	public static boolean Interact(PlayerInteractEvent e, Material Item) {
		if ((e.getItem().getType() == Item)
				&& ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))) {
			return true;
		}
		return false;
	}
	public static boolean InteractEntity(PlayerInteractEntityEvent e, Material Type, String Name) {
		if (e.getPlayer().getItemInHand().getType() == Type) {
			if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName() == null)
				return false;
			if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(Name)) {
				return true;
			}
		}
		return false;
	}

	public static ItemStack getItem_Menu_Host() {
		return Item_Menu_Host;
	}

	public static void setItem_Menu_Host(ItemStack item_Menu_Host) {
		Item_Menu_Host = item_Menu_Host;
	}

	public static ItemStack getItem_Création_Partie() {
		return Item_Création_Partie;
	}

	public static void setItem_Création_Partie(ItemStack item_Création_Partie) {
		Item_Création_Partie = item_Création_Partie;
	}

	public static ItemStack getItem_Rejoindre_Partie() {
		return Item_Rejoindre_Partie;
	}

	public static void setItem_Rejoindre_Partie(ItemStack item_Rejoindre_Partie) {
		Item_Rejoindre_Partie = item_Rejoindre_Partie;
	}

	public static ItemStack getItem_Quitter_Partie() {
		return Item_Quitter_Partie;
	}

	public static MenuSelectionGame getMenuselectiongame() {
		return menuselectiongame;
	}

	public static void setMenuselectiongame(MenuSelectionGame menuselectiongame) {
		MenuManager.menuselectiongame = menuselectiongame;
	}
}