package fr.NS.Labyrinthe.Menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.NS.Labyrinthe.API.ItemCreator;
import fr.NS.Labyrinthe.Configuration.Message;
import fr.NS.Labyrinthe.Generale.Main;
import fr.NS.Labyrinthe.Gestion.Game;
import fr.NS.Labyrinthe.Gestion.Game.State;
import fr.NS.Labyrinthe.Gestion.Joueur;

public class MenuSelectionGame extends Menu {
	
	private String MenuName = Message.ColorFonce+"Menu Sélection Game";
	private Inventory inv = Bukkit.createInventory(null, 4*9,MenuName);
	
	public MenuSelectionGame() {
		for(int i = 0; i < inv.getSize();i++){
			if((i < 9) || (i%9 == 0) || ((i+1)%9 ==0) || (i > inv.getSize()-9)){
				inv.setItem(i, getItemGlass());
			}
		}
		
		inv.setItem(inv.getSize()-1, getItemGlassRetour());
		
		for(Game g : Main.getInstance().getGames()){
			int durability;
			String cliquez;
			if(g.isState(State.Attente)){
				durability = 5;
				cliquez = Message.Chat_fleche+Message.ColorClair+"Cliquez pour rejoindre";
			}else{
				durability = 14;
				cliquez = Message.Chat_fleche+Message.ColorClair+"Cliquez pour spect";
			}
			
			inv.addItem(new ItemCreator(Material.STAINED_CLAY).setDurability(durability).setName(Message.ColorFonce+"§lHost : "+Message.ColorClair + g.getFirstLeader().getPlayer().getName()).addLore("").addLore(cliquez).getItem());
		}
		
	}
	
	public void refresh(){
		inv.remove(Material.STAINED_CLAY);
		for(Game g : Main.getInstance().getGames()){
			int durability;
			String cliquez;
			if(g.isState(State.Attente)){
				durability = 5;
				cliquez = Message.Chat_fleche+Message.ColorClair+"Cliquez pour rejoindre";
			}else{
				durability = 14;
				cliquez = Message.Chat_fleche+Message.ColorClair+"Cliquez pour spect";
			}
			
			inv.addItem(new ItemCreator(Material.STAINED_CLAY).setDurability(durability).setName(Message.ColorFonce+"§lHost : "+Message.ColorClair + g.getFirstLeader().getPlayer().getName()).addLore("").addLore(cliquez).getItem());
		}
	}
	
	@Override
	public void actualise(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(e.getInventory().getTitle().equalsIgnoreCase(MenuName)){
			e.setCancelled(true);
			if(Condition(e, getItemGlassRetour().getType(),getItemGlassRetour().getItemMeta().getDisplayName())){
				close(p);
				return;
			}
			if(Condition(e, Material.STAINED_CLAY)){
				String Host = e.getCurrentItem().getItemMeta().getDisplayName();
				Host = Host.replaceAll(Message.ColorFonce+"§lHost : "+Message.ColorClair, "");
				Player pl = Bukkit.getPlayer(Host);
				if(pl != null){
					Joueur jl = Joueur.getJoueur(pl);
					Joueur j = Joueur.getJoueur(p);
					jl.getGame().addMembre(j);
					
					for(Player o : Bukkit.getOnlinePlayers()){
						if(o != p){
							if(jl.getGame().getMembre().contains(Joueur.getJoueur(o))){
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
					p.getInventory().setItem(8, getItem_Quitter_Partie());
				}
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
