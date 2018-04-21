package fr.NS.Labyrinthe.Menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.NS.Labyrinthe.Configuration.Message;
import fr.NS.Labyrinthe.Generale.Main;
import fr.NS.Labyrinthe.Gestion.Game;
import fr.NS.Labyrinthe.Gestion.Joueur;

public class MenuValidation extends Menu {
	
	private String MenuName = Message.ColorFonce+"Validation";
	private Inventory inv = Bukkit.createInventory(null, 9,MenuName);
	
	public MenuValidation() {
		inv.setItem(0, getItemGlass());
		inv.setItem(8, getItemGlass());
		inv.setItem(3, getItemValider());
		inv.setItem(5, getItemAnnuler());
	}
	
	@Override
	public void actualise(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(e.getInventory().getTitle().equalsIgnoreCase(MenuName)){
			e.setCancelled(true);
			if(Condition(e, getItemAnnuler().getType(), getItemAnnuler().getItemMeta().getDisplayName())){
				close(p);
				return;
			}
			if(Condition(e, getItemValider().getType(), getItemValider().getItemMeta().getDisplayName())){
				for(Player o : Bukkit.getOnlinePlayers()){
					if(o != p){
						o.hidePlayer(p);
						p.hidePlayer(o);
					}
				}
				Joueur j = Joueur.getJoueur(p);
				Game g = new Game(1200, 12,j);
				g.addMembre(j);
				Main.getInstance().getGames().add(g);
				
				p.getInventory().clear();
				p.getInventory().setItem(0, getItem_Menu_Host());
				p.getInventory().setItem(8, getItem_Quitter_Partie());
				p.teleport(Message.centre);
				return;
			}
		}
	}
	
	@Override
	public void close(Player p) {
		p.closeInventory();
	}

	@Override
	public Inventory getInventory() {
		return inv;
	}
	
	
	
}
