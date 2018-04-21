package fr.NS.Labyrinthe.Generale;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import fr.NS.Labyrinthe.API.Spawn;
import fr.NS.Labyrinthe.Configuration.Message;
import fr.NS.Labyrinthe.Gestion.Game;
import fr.NS.Labyrinthe.Gestion.Game.State;
import fr.NS.Labyrinthe.Gestion.Joueur;
import fr.NS.Labyrinthe.Menu.MenuManager;
import fr.NS.Labyrinthe.Monde.Monde;

public class Main extends JavaPlugin implements Listener {
	
	public static Main instance;
	public ArrayList<Game> games = new ArrayList<>();
	
	@Override
	public void onLoad() {
		instance = this;
		Bukkit.getConsoleSender().sendMessage("§6§m--=---------------------------=--§r §6Labyrinthe §6§m--=---------------------------=--");
		Bukkit.getConsoleSender().sendMessage("§6§m--=---------------------------=--§r    §eLoad    §6§m--=---------------------------=--");
	}
	
	
	@Override
	public void onEnable() {
		instance = this;
		Manager.RegisterCommandes(this);
		Manager.RegisterEvents(this);
		Manager.RegisterTimers();
		Monde.createFlatWorld();
		Spawn.generation();
		
		for(Player p : Bukkit.getOnlinePlayers()){
			Joueur j = Joueur.getJoueur(p);
			j.sendTablist(Message.Prefix+"\n    §l§8§m-+------------------------+-\n", "\n§r    §l§8§m-+------------------------+-\n"+Message.Chat_fleche+Message.ColorClair+"by "+Message.ColorFonce+"Seraleme & Nicofolxarus");
			p.teleport(new Location(Message.world, 0, 200, 0));
			j.clearall();
			if(j.getGame() == null){
				p.teleport(new Location(Message.world, 0, 200, 0));
				p.getInventory().setItem(0, MenuManager.getItem_Rejoindre_Partie());
				p.getInventory().setItem(4, MenuManager.getItem_Création_Partie());
			}else{
				if(j.getGame().isState(State.Attente)){
					
				}else if(j.getGame().isState(State.Game)){
					
				}
				
			}
		}
		Bukkit.getConsoleSender().sendMessage("§6§m--=---------------------------=--§r §6Labyrinthe §6§m--=---------------------------=--");
		Bukkit.getConsoleSender().sendMessage("§6§m--=---------------------------=--§r   §eStart    §6§m--=---------------------------=--");
	}
	
	@Override
	public void onDisable() {
		for(Player p : Bukkit.getOnlinePlayers()){
			for(Player o : Bukkit.getOnlinePlayers()){
				if(o != p){
					o.showPlayer(p);
					p.showPlayer(o);
				}
			}
		}

		Bukkit.getConsoleSender().sendMessage("§6§m--=---------------------------=--§r §6Labyrinthe §6§m--=---------------------------=--");
		Bukkit.getConsoleSender().sendMessage("§6§m--=---------------------------=--§r    §eEnd     §6§m--=---------------------------=--");
	}
	
	public static Main getInstance(){
		return instance;
	}
	public ArrayList<Game> getGames(){
		return games;
	}
}
