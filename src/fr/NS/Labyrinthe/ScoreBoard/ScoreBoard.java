package fr.NS.Labyrinthe.ScoreBoard;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreBoard {
	
	private Scoreboard scoreboard;
	private String name;
	private Objective Sidebar;
	
	private ArrayList<Objective> objectifs = new ArrayList<>();
	private ArrayList<Player> players = new ArrayList<>();
	
	public ScoreBoard(String name){
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Initialisation(ChatColor.GOLD+"Labyrinthe");
	}
	
	public ScoreBoard(Player p){
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Initialisation(ChatColor.GOLD+"Labyrinthe");
	}
	
	private void Initialisation(String nom){
		this.name = "sb." + new Random().nextInt(99999999);
		Sidebar = scoreboard.registerNewObjective(name, "dummy");
		Sidebar = scoreboard.getObjective(name);
		Sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
		Sidebar.setDisplayName(nom);
	}
	
	public void addPlayer(Player p){
		p.setScoreboard(scoreboard);
		if(!players.contains(p)){
			players.add(p);
		}
	}
	
	public void removePlayer(Player p){
		p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		if(players.contains(p)){
			players.remove(p);
		}
	}	
	public void addLine(int nb,String ligne){
		Sidebar.getScore(ligne).setScore(nb);
	}
	public void setLine(int nb,String ligne){
		for(String lignes : scoreboard.getEntries()){
			if(Sidebar.getScore(lignes).getScore() == nb){
				scoreboard.resetScores(lignes);
			}
		}
		Sidebar.getScore(ligne).setScore(nb);
	}
	public void removeLine(int nb){
		for(String lignes : scoreboard.getEntries()){
			if(Sidebar.getScore(lignes).getScore() == nb){
				scoreboard.resetScores(lignes);
			}
		}
	}
	public void removeLineEqual(String ligne){
		for(String lignes : scoreboard.getEntries()){
			if(lignes.equalsIgnoreCase(ligne)){
				scoreboard.resetScores(lignes);
			}
		}
	}
	public void removeLineContain(String ligne){
		for(String lignes : scoreboard.getEntries()){
			if(lignes.contains(ligne)){
				scoreboard.resetScores(lignes);
			}
		}
	}
	
	public void setNameSideBar(String name){
		Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
		if(objective != null){
			objective.setDisplayName(name);
		}
	}
	
	public void addObjective(String Name,String type,DisplaySlot slot){
		Objective objective;
		scoreboard.registerNewObjective(Name, "health");
		objective = scoreboard.getObjective(Name);
		objective.setDisplaySlot(slot);
		if(!objectifs.contains(objective)){
			objectifs.add(objective);
		}
	}
	public void deleteObjective(String Name){
		Objective objective = scoreboard.getObjective(name);
		if(objective != null){
			objective.unregister();
			if(objectifs.contains(objective)){
				objectifs.remove(objective);
			}
		}
	}
	public void deleteObjective(DisplaySlot slot){
		Objective objective = scoreboard.getObjective(slot);
		if(objective != null){
			objective.unregister();
			if(objectifs.contains(objective)){
				objectifs.remove(objective);
			}
		}
	}
}
