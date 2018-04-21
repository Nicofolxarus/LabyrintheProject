package fr.NS.Labyrinthe.Events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.NS.Labyrinthe.Configuration.Message;
import fr.NS.Labyrinthe.Generale.Main;
import fr.NS.Labyrinthe.Gestion.Game;
import fr.NS.Labyrinthe.Gestion.Game.State;
import fr.NS.Labyrinthe.Gestion.Joueur;
import fr.NS.Labyrinthe.Menu.MenuManager;

public class EventJoinLeave implements Listener {
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		Joueur j = Joueur.getJoueur(p);
		j.setPlayer(p);
		j.sendTablist(Message.Prefix+"\n    §l§8§m-+------------------------+-\n", "\n§r    §l§8§m-+------------------------+-\n"+Message.Chat_fleche+Message.ColorClair+"by "+Message.ColorFonce+"Seraleme & Nicofolxarus");
		if(j.getGame() == null){
			p.teleport(new Location(Message.world, 0, 200, 0));
			p.getInventory().setItem(0, MenuManager.getItem_Rejoindre_Partie());
			if(p.isOp())p.getInventory().setItem(4, MenuManager.getItem_Création_Partie());
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				@Override
				public void run() {
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
				}
			},5);
		}else{
			if(j.getGame().isState(State.Attente)){
				
			}else if(j.getGame().isState(State.Game)){
				
			}
		}
	}
	
	
	@EventHandler
	public void leave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
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
	}
}
