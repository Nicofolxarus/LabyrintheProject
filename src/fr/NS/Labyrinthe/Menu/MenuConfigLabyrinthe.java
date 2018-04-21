package fr.NS.Labyrinthe.Menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.NS.Labyrinthe.API.ItemCreator;
import fr.NS.Labyrinthe.Configuration.Message;
import fr.NS.Labyrinthe.Gestion.Game;
import fr.NS.Labyrinthe.Menu.MenuConfigParametre.Parametre;

public class MenuConfigLabyrinthe extends Menu {
	
	private String MenuName = Message.ColorFonce+"Config Labyrinthe";
	private Inventory inv = Bukkit.createInventory(null, 4*9,MenuName);
	private Game g;

	
	public MenuConfigLabyrinthe(Game g) {
		this.g = g;
		inv.setItem(11, new ItemCreator(Material.IRON_FENCE).setName(Message.ColorFonce+"Taille").addLore("").addLore(Message.ColorClair+"Longueur : §f"+g.getParamLongeur()+" case(s)").addLore(Message.ColorClair+"Largeur : §f"+g.getParamLargeur()+" case(s)").getItem());
		inv.setItem(13, new ItemCreator(Material.SMOOTH_BRICK).setDurability(1).setName(Message.ColorFonce+"Epaisseur des Murs").addLore("").addLore(Message.Chat_fleche+Message.ColorClair+g.getParamEpaisseurMur()+" block(s)").getItem());
		inv.setItem(15, new ItemCreator(Material.STEP).setDurability(5).setName(Message.ColorFonce+"Largeur Couloir").addLore("").addLore(Message.Chat_fleche+Message.ColorClair+g.getParamLargeurCouloir()+" block(s)").getItem());
		
		inv.setItem(21, new ItemCreator(Material.INK_SACK).setDurability(15).setName(Message.ColorFonce+"Hauteur Mur").addLore("").addLore(Message.Chat_fleche+Message.ColorClair+g.getParamHauteurMur()+" block(s)").getItem());
		inv.setItem(23, new ItemCreator(Material.COMPASS).setName(Message.ColorFonce+"Rayon du centre").addLore("").addLore(Message.Chat_fleche+Message.ColorClair+g.getParamRayonCentre()+" case(s)").getItem());
		
		inv.setItem(35, getItemGlassRetour());
		fillInventoryGlass(inv);
	}
	
	public void updateMenu(Parametre p){
		switch (p) {
		case Largeur:
			inv.setItem(11, new ItemCreator(Material.IRON_FENCE).setName(Message.ColorFonce+"Taille").addLore("").addLore(Message.ColorClair+"Longueur : §f"+g.getParamLongeur()+" case(s)").addLore(Message.ColorClair+"Largeur : §f"+g.getParamLargeur()+" case(s)").getItem());
			break;
		case Longueur:
			inv.setItem(11, new ItemCreator(Material.IRON_FENCE).setName(Message.ColorFonce+"Taille").addLore("").addLore(Message.ColorClair+"Longueur : §f"+g.getParamLongeur()+" case(s)").addLore(Message.ColorClair+"Largeur : §f"+g.getParamLargeur()+" case(s)").getItem());
			break;
		case HauteurMur:
			inv.setItem(21, new ItemCreator(Material.INK_SACK).setDurability(15).setName(Message.ColorFonce+"Hauteur Mur").addLore("").addLore(Message.Chat_fleche+Message.ColorClair+g.getParamHauteurMur()+" block(s)").getItem());
			break;
		case EpaisseurMur:
			inv.setItem(13, new ItemCreator(Material.SMOOTH_BRICK).setDurability(1).setName(Message.ColorFonce+"Epaisseur des Murs").addLore("").addLore(Message.Chat_fleche+Message.ColorClair+g.getParamEpaisseurMur()+" block(s)").getItem());
			break;
		case LargeurCouloir:
			inv.setItem(15, new ItemCreator(Material.STEP).setDurability(5).setName(Message.ColorFonce+"Largeur Couloir").addLore("").addLore(Message.Chat_fleche+Message.ColorClair+g.getParamLargeurCouloir()+" block(s)").getItem());
			break;
		case RayonCentre:
			inv.setItem(23, new ItemCreator(Material.COMPASS).setName(Message.ColorFonce+"Rayon du centre").addLore("").addLore(Message.Chat_fleche+Message.ColorClair+g.getParamRayonCentre()+" case(s)").getItem());
			break;
		default:
			break;
		}
	}
	
	@Override
	public void actualise(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(e.getInventory().getTitle().equalsIgnoreCase(MenuName)){
			e.setCancelled(true);
			if(Condition(e, Material.IRON_FENCE)){
				new MenuConfigParametre(g, Parametre.Largeur).open(p);
				return;
			}
			if(Condition(e, Material.SMOOTH_BRICK)){
				new MenuConfigParametre(g, Parametre.EpaisseurMur).open(p);
				return;
			}
			if(Condition(e, Material.STEP)){
				new MenuConfigParametre(g, Parametre.LargeurCouloir).open(p);
				return;
			}
			if(Condition(e, Material.INK_SACK)){
				new MenuConfigParametre(g, Parametre.HauteurMur).open(p);
				return;
			}
			if(Condition(e, Material.COMPASS)){
				new MenuConfigParametre(g, Parametre.RayonCentre).open(p);
				return;
			}
			if(Condition(e, getItemGlassRetour().getType(),getItemGlassRetour().getItemMeta().getDisplayName())){
				g.getMenuhost().open(p);
				return;
			}
		}
	}

	@Override
	public void close(Player p) {
		
	}

	@Override
	public Inventory getInventory() {
		return inv;
	}

}
