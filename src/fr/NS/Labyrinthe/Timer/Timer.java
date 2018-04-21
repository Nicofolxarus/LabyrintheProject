package fr.NS.Labyrinthe.Timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import fr.NS.Labyrinthe.Generale.Main;
import fr.NS.Labyrinthe.Gestion.Game;
import fr.NS.Labyrinthe.Menu.MenuManager;

public class Timer implements Listener {

	static int Task;
	public static void stop(){
		Bukkit.getScheduler().cancelTask(Task);
	}
	public static void start() {
		Task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
			@Override
			public void run() {
				MenuManager.getMenuselectiongame().refresh();
				if (Main.getInstance().getGames() != null) {
					if (!Main.getInstance().getGames().isEmpty()) {
						for (Game game : Main.getInstance().getGames()) {
							// Timer
							if(game.isStart()){
								if(!game.isPause()){
									game.setTime(game.getTime() + 1);
									if (game.getTime() > game.getTimeMax()) {
										game.setJour(game.getJour() + 1);
										game.setTime(0);
										if (game.getJour() > game.getJourMax()) {
											// Tous dans le Labyrinthe
										}
									}
									if (game.getTime() == 10) {
										game.vote();
									}
									if (game.getTime() == 20) {
										Player p = Bukkit.getPlayer(game.getVote().getResult());
										if(p != null){
											game.broadcast("§3Result : §e"+ p.getName());
										}else{
											game.broadcast("§cError Result");
										}
									}
								}
							}
							if (game.isStarting()) {
								game.Starting();
								game.setTimerStarting(game.getTimerStarting() - 1);
							}
							
						}
					}
				}
			}
		}, 20, 20);
	}

}
