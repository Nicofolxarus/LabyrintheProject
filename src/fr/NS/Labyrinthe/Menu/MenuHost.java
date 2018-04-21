package fr.NS.Labyrinthe.Menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.NS.Labyrinthe.API.ItemCreator;
import fr.NS.Labyrinthe.Configuration.Message;
import fr.NS.Labyrinthe.Gestion.Game;
import fr.NS.Labyrinthe.Gestion.Game.State;

public class MenuHost extends Menu{
	private String MenuName = Message.ColorFonce+"Host";
	private Inventory inv = Bukkit.createInventory(null, 4*9,MenuName);
	private Game g;
	private MenuConfigLabyrinthe menuConfigLabyrinthe;
	
	public MenuHost(Game g) {
		this.g = g;
		menuConfigLabyrinthe = new MenuConfigLabyrinthe(g);
		inv.setItem(11, new ItemCreator(Material.SMOOTH_BRICK).setName(Message.ColorFonce+"Parametre du Labyrinthe").addLore(Message.Chat_fleche+Message.ColorClair+"Cliquez pour paramétrer !").setDurability(1).getItem());
		
		inv.setItem(15, new ItemCreator(Material.WATCH).setName(Message.ColorFonce+"Parametre des Jours").addLore(Message.Chat_fleche+Message.ColorClair+"Cliquez pour paramétrer !").getItem());
		if(g.isGenerate()){
			inv.setItem(30,new ItemCreator(Material.BARRIER).setName(Message.ColorFonce + "Labyrinthe en cours de Génération").addLore("").addLore(Message.Chat_fleche+Message.ColorClair + "Vous ne pouvez pas démarré la partie").getItem());
			inv.setItem(32,new ItemCreator(Material.BARRIER).setName(Message.ColorFonce + "Labyrinthe en cours de Génération").addLore("").addLore(Message.Chat_fleche+Message.ColorClair + "Vous ne pouvez pas démarré la partie").getItem());
		}else{
			if(g.isStarting()){
				inv.setItem(32, new ItemCreator(Material.STAINED_CLAY).setName("§4Arrêter").setDurability(14).getItem());
			}else{
				inv.setItem(32, new ItemCreator(Material.STAINED_CLAY).setName("§aDémarrer").setDurability(5).getItem());
			}
			if(g.getLab() != null){
				if(g.getLab().isPreGenere()){
					inv.setItem(30, new ItemCreator(Material.STAINED_CLAY).setDurability(14).setName(Message.ColorFonce+"Réinitialisation du le labyrinthe").addLore("").addLore(Message.Chat_fleche+Message.ColorClair+"Cliquez pour réinitialiser le labyrinthe").getItem());
				}else{
					inv.setItem(30, new ItemCreator(Material.BEDROCK).setName(Message.ColorFonce+"Générer le Labyrinthe").addLore(Message.Chat_fleche+Message.ColorClair+"Cliquez pour générer le labyrinthe !").getItem());
				}
			}else{
				inv.setItem(30, new ItemCreator(Material.BEDROCK).setName(Message.ColorFonce+"Générer le Labyrinthe").addLore(Message.Chat_fleche+Message.ColorClair+"Cliquez pour générer le labyrinthe !").getItem());
			}
		}

		fillInventoryGlass(getInventory());
		
	}
	
	public void update(){
		if(g.isGenerate()){
			inv.setItem(30,new ItemCreator(Material.BARRIER).setName(Message.ColorFonce + "Labyrinthe en cours de Génération").addLore("").addLore(Message.Chat_fleche+Message.ColorClair + "Vous ne pouvez pas démarré la partie").getItem());
			inv.setItem(32,new ItemCreator(Material.BARRIER).setName(Message.ColorFonce + "Labyrinthe en cours de Génération").addLore("").addLore(Message.Chat_fleche+Message.ColorClair + "Vous ne pouvez pas démarré la partie").getItem());
		}else{
			if(g.isStarting()){
				inv.setItem(32, new ItemCreator(Material.STAINED_CLAY).setName("§4Arrêter").setDurability(14).getItem());
			}else{
				inv.setItem(32, new ItemCreator(Material.STAINED_CLAY).setName("§aDémarrer").setDurability(5).getItem());
			}
			if(g.getLab() != null){
				if(g.getLab().isPreGenere()){
					inv.setItem(30, new ItemCreator(Material.STAINED_CLAY).setDurability(14).setName(Message.ColorFonce+"Réinitialisation du le labyrinthe").addLore("").addLore(Message.Chat_fleche+Message.ColorClair+"Cliquez pour réinitialiser le labyrinthe").getItem());
				}else{
					inv.setItem(30, new ItemCreator(Material.BEDROCK).setName(Message.ColorFonce+"Générer le Labyrinthe").addLore(Message.Chat_fleche+Message.ColorClair+"Cliquez pour générer le labyrinthe !").getItem());
				}
			}else{
				inv.setItem(30, new ItemCreator(Material.BEDROCK).setName(Message.ColorFonce+"Générer le Labyrinthe").addLore(Message.Chat_fleche+Message.ColorClair+"Cliquez pour générer le labyrinthe !").getItem());
			}
		}
	}
	
	@Override
	public void actualise(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(e.getInventory().getTitle().equalsIgnoreCase(MenuName)){
			e.setCancelled(true);
			if(Condition(e, Material.SMOOTH_BRICK)){
				menuConfigLabyrinthe.open(p);
				return;
			}
			if(Condition(e, Material.STAINED_CLAY,"§aDémarrer")){
				if(g.getLab() != null){
					if(g.getLab().isPreGenere()){
						g.setStarting(true);
						g.setTimerStarting(15);
						g.setState(State.Generation);
						g.broadcast(Message.Prefix + Message.ColorClair + p.getName() + " §aa demarré la partie");
						update();
					}else{
						p.sendMessage(Message.Chat_fleche+"§cLe Labyrinthe n'est pas généré !");
					}
				}else{
					p.sendMessage(Message.Chat_fleche+"§cLe Labyrinthe n'est pas généré !");
				}
				return;
			}
			if(Condition(e, Material.STAINED_CLAY,"§4Arrêter")){
				g.setStarting(false);
				g.setTimerStarting(15);
				g.setState(State.Attente);
				g.broadcast(Message.Prefix + Message.ColorClair + p.getName() + " §ca arreté la partie");
				update();
				return;
			}
			if(Condition(e, Material.BEDROCK,Message.ColorFonce+"Générer le Labyrinthe")){
				g.Generé(p);
				update();
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

	public MenuConfigLabyrinthe getMenuConfigLabyrinthe() {
		return menuConfigLabyrinthe;
	}

	public void setMenuConfigLabyrinthe(MenuConfigLabyrinthe menuConfigLabyrinthe) {
		this.menuConfigLabyrinthe = menuConfigLabyrinthe;
	}

}
