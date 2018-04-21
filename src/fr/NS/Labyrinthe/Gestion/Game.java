package fr.NS.Labyrinthe.Gestion;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.NS.Labyrinthe.Configuration.Message;
import fr.NS.Labyrinthe.Generale.Main;
import fr.NS.Labyrinthe.Labyrinthe.Labyrinthe;
import fr.NS.Labyrinthe.Menu.MenuHost;
import fr.NS.Labyrinthe.Menu.MenuManager;
import fr.NS.Labyrinthe.Monde.Monde;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Game {
	public enum State {
		Attente,Generation,Game,Fin;
	}
	private ArrayList<Joueur> Membre = new ArrayList<Joueur>();

	private Vote vote;

	private MenuHost menuhost = new MenuHost(this);
	
	// Labyrinthe
	private Labyrinthe lab;
	private int ParamLongeur = 0;
	private int ParamLargeur = 0;
	private int ParamLargeurCouloir;
	private int ParamHauteurMur;
	private int ParamEpaisseurMur = 1;
	private int ParamRayonCentre = 1;
	private Location ParamLoc;

	private int Timetemp = 0;
	private int Jourtemp = 0;
	
	private int TimeMax = 1200;
	private int JourMax = 8;

	private boolean Pause = false;
	private boolean Start = false;
	private boolean Starting = false;
	private boolean Generate = false;
	private int TimerStarting = 15;
	
	private ArrayList<Joueur> Leader = new ArrayList<>();

	public Game(Integer Time, Integer Jour, Joueur j) {
		this.JourMax = Jour;
		this.TimeMax = Time;
		Leader.add(j);
//		scoreboard = new ScoreBoard(Message.ColorFonce + "Labyrinthe");
//		scoreboard.addLine(12, Message.ColorGris + "§m-=-------------------=-");
//		scoreboard.addLine(9, "     ");
//		scoreboard.addLine(8, "§7► " + Message.ColorFonce + "Joueurs Restant : " + Message.ColorClair + Membre.size());
//		scoreboard.addLine(6, "§7► " + Message.ColorFonce + "Jour : " + Message.ColorClair + Jourtemp);
//		scoreboard.addLine(4, "§7► " + Message.ColorFonce + "Timer :" + Message.ColorClair + " 0 min");
//		scoreboard.addLine(3, "  ");
//		scoreboard.addLine(2, Message.ColorGris + "§m-=-------------------=- ");
//		scoreboard.addLine(1, Message.ColorFonce + "         ");
	}

	public void Generé(Player p) {
		if(!isGenerate()){
			if (ParamLongeur != 0 && ParamLargeur != 0 && ParamHauteurMur != 0 && ParamLargeurCouloir != 0 && ParamEpaisseurMur != 0) {
				setGenerate(true);
				if(lab == null){
					ParamLoc = getNewLocation();
					lab = new Labyrinthe(ParamLoc, JourMax, true);
				}else{
					lab.delete();
					ParamLoc = getNewLocation();
					lab = new Labyrinthe(ParamLoc, JourMax, true);
				}
				lab.setLongueur(ParamLongeur);
				lab.setLargeur(ParamLargeur);
				lab.setLargeurCouloir(ParamLargeurCouloir);
				lab.setHauteurMur(ParamHauteurMur);
				lab.setEpaisseurMur(ParamEpaisseurMur);
				lab.setRayon(ParamRayonCentre);
				lab.calcule(true,this);
			} else {
				p.sendMessage(Message.Prefix + Message.ColorClair + "Veuillez configurer le Labyrinthe.");
			}
		}else{
			p.sendMessage(Message.Prefix + Message.ColorClair + "Le Labyrinthe est déjà généré !");
		}

	}
	private static int zone = 1;
	private static int multiply = 1;
	public static Location getNewLocation(){
		int x = 500;
		int y = Monde.yMax+1;
		int z = 500;
		
		switch (zone) {
		case 1:
			x = Math.abs(x) * multiply;
			z = Math.abs(z) * multiply;
			break;
		case 2:
			x = -(Math.abs(x) * multiply);
			z = Math.abs(z) * multiply;
			break;
		case 3:
			x = -(Math.abs(x) * multiply);
			z = -(Math.abs(z) * multiply);
			break;
		case 4:
			x = Math.abs(x) * multiply;
			z = -(Math.abs(z) * multiply);
			multiply++;
			break;

		default:
			break;
		}
		zone++;
		return new Location(Message.world, x, y, z);
	}
	public void delete(){
		if(!getMembre().isEmpty()){
			ArrayList<Joueur> list = new ArrayList<>(getMembre());
			for(Joueur jo : list){
				leavePlayer(jo);
			}
		}
		Main.getInstance().getGames().remove(this);
		MenuManager.getMenuselectiongame().refresh();
	}
	public void leavePlayer(Joueur jo){
			Player po = jo.getPlayer();
			removeMembre(jo);
			for(Player o : Bukkit.getOnlinePlayers()){
				if(o != po){
					if(Joueur.getJoueur(o).getGame() == null){
						o.showPlayer(po);
						po.showPlayer(o);
					}else{
						o.hidePlayer(po);
						po.hidePlayer(o);
					}
				}
			}
			po.getInventory().clear();
			po.teleport(Message.centre);
			po.getInventory().setItem(0, MenuManager.getItem_Rejoindre_Partie());
			po.getInventory().setItem(4, MenuManager.getItem_Création_Partie());
	}
	public void vote() {
		vote = new Vote(this);
		for (Joueur joueur : Membre) {
			Player p = joueur.getPlayer();
			p.sendMessage("§m-=----------------------------------------------=-");
			p.sendMessage("");
			p.sendMessage("  "+ChatColor.UNDERLINE + "§6Vote :");
			TextComponent msg = new TextComponent("    §7► Cliquez ici pour Voter ! ");
			msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/labi vote"));
			p.spigot().sendMessage(msg);

			p.sendMessage("");
			p.sendMessage("§m-=----------------------------------------------=-");
		}
	}

	public Vote getVote() {
		return vote;
	}

	public void Start() {
		Start = true;
	}
	public int getTime() {
		return Timetemp;
	}
	

	public void setTime(int time) {
		Timetemp = time;
		for(Joueur j : getMembre()){
			j.getPlayer().setPlayerTime((24000*time)/getTimeMax(), false);
		}
	}

	public static String timeToString(int seconde){
		int Heure = seconde / 3600;
		int Minute= (seconde - (Heure * 3600))/60;
		int Seconde = seconde - ((Heure *3600)+(Minute*60));
		
		if(Heure < 1 && Minute < 1){
			return Seconde +"s";
		}else if(Heure < 1){
			return Minute+"min "+ Seconde +"s";
		}else{
			return Heure +"h "+Minute+"min";
		}
	}
	public static String booleanToString(boolean active){
		if(active){
			return ChatColor.GREEN+"Activé";
		}else{
			return ChatColor.RED+"Désactivé";
		}
	}

	public int getJour() {
		return Jourtemp;
	}

	public void setJour(int jourtemp) {
		Jourtemp = jourtemp;
	}

	public ArrayList<Joueur> getMembre() {
		return Membre;
	}

	public void setMembre(ArrayList<Joueur> membre) {
		Membre = membre;
	}

	public void addMembre(UUID uuid) {
		Joueur j = Joueur.getJoueur(uuid);
		if (j != null) {
			j.setGame(this);
			if (!Membre.contains(j)) {
				Membre.add(j);
			}
		}
	}
	
	public void addMembre(Joueur j) {
		if (j != null) {
			j.setGame(this);
			if (!Membre.contains(j)) {
				Membre.add(j);
			}
		}
	}

	public void removeMembre(UUID uuid) {
		Joueur j = Joueur.getJoueur(uuid);
		if (j != null) {
			j.setGame(null);
			if (Membre.contains(j)) {
				Membre.remove(j);
			}
		}
	}
	public void removeMembre(Joueur j) {
		if (j != null) {
			j.setGame(null);
			if (Membre.contains(j)) {
				Membre.remove(j);
			}
		}
	}
	public boolean isLeader(Joueur j){
		return Leader.contains(j);
	}
	public Joueur getFirstLeader(){
		return Leader.get(0);
	}
	public ArrayList<Joueur> getLeaders(){
		return Leader;
	}
	public Labyrinthe getLab() {
		return lab;
	}

	public void setLab(Labyrinthe lab) {
		this.lab = lab;
	}

	public boolean isPause() {
		return Pause;
	}

	public void setPause(boolean pause) {
		Pause = pause;
	}

	public boolean isStarting() {
		return Starting;
	}

	public boolean isGenerate() {
		return Generate;
	}

	public void setGenerate(boolean generate) {
		Generate = generate;
	}

	public void Starting() {
		if (TimerStarting == 15 || TimerStarting == 10 || (TimerStarting <= 5 && TimerStarting > 0)) {
			for (Joueur joueur : Membre) {
				joueur.sendTitle(Message.Title_Prefix, "Démarage dans " + TimerStarting + " s !", 20);
			}
		}
		if ((TimerStarting <= 5 && TimerStarting > 0)) {
			for (Joueur joueur : Membre) {
				joueur.sendTitleNofondu(Message.Title_Prefix, "Démarage dans " + TimerStarting + " s !", 40);
			}
		}
		if (TimerStarting == 0) {
			Location loc = new Location(lab.getLocCentre().getWorld(), lab.getLocCentre().getBlockX(), lab.getLocCentre().getBlockY()-3, lab.getLocCentre().getBlockZ()); 
			for (Joueur joueur : Membre) {
				joueur.sendTitleNofondu(Message.Title_Prefix, "Bon Jeu !", 40);
				joueur.clearall();
				joueur.getPlayer().teleport(loc);
			}
			Starting = false;
			TimerStarting = 1;
			lab.Generation();
			Start();
		}
	}

	public void setStarting(boolean starting) {
		Starting = starting;
	}

	public int getTimerStarting() {
		return TimerStarting;
	}

	public void setTimerStarting(int timerStarting) {
		TimerStarting = timerStarting;
	}

	public int getTimeMax() {
		return TimeMax;
	}

	public void setTimeMax(int timeMax) {
		TimeMax = timeMax;
	}

	public int getJourMax() {
		return JourMax;
	}

	public void setJourMax(int jourMax) {
		JourMax = jourMax;
	}
	public boolean isStart() {
		return Start;
	}

	public void setStart(boolean start) {
		Start = start;
	}
	
	public void broadcast(String message){
		for(Joueur j : this.Membre){
			if(j.isconnect()){
				j.getPlayer().sendMessage(message);	
			}
		}
	}
	
	private State currentState = State.Attente;

	public void setState(State state) {
		currentState = state;
	}

	public boolean isState(State state) {
		return currentState == state;
	}

	public State getState() {
		return currentState;
	}

	public MenuHost getMenuhost() {
		return menuhost;
	}

	public void setMenuhost(MenuHost menuhost) {
		this.menuhost = menuhost;
	}

	public int getParamLongeur() {
		return ParamLongeur;
	}

	public void setParamLongeur(int paramLongeur) {
		ParamLongeur = paramLongeur;
	}

	public int getParamLargeur() {
		return ParamLargeur;
	}

	public void setParamLargeur(int paramLargeur) {
		ParamLargeur = paramLargeur;
	}

	public int getParamLargeurCouloir() {
		return ParamLargeurCouloir;
	}

	public void setParamLargeurCouloir(int paramLargeurCouloir) {
		ParamLargeurCouloir = paramLargeurCouloir;
	}

	public int getParamHauteurMur() {
		return ParamHauteurMur;
	}

	public void setParamHauteurMur(int paramHauteurMur) {
		ParamHauteurMur = paramHauteurMur;
	}

	public int getParamEpaisseurMur() {
		return ParamEpaisseurMur;
	}

	public void setParamEpaisseurMur(int paramEpaisseurMur) {
		ParamEpaisseurMur = paramEpaisseurMur;
	}

	public int getParamRayonCentre() {
		return ParamRayonCentre;
	}

	public void setParamRayonCentre(int paramRayonCentre) {
		ParamRayonCentre = paramRayonCentre;
	}
}
