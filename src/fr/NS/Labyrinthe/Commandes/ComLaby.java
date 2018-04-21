package fr.NS.Labyrinthe.Commandes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.NS.Labyrinthe.Gestion.Joueur;
import fr.NS.Labyrinthe.Labyrinthe.Labyrinthe.Direction;

public class ComLaby implements CommandExecutor {
	
	private static boolean door = true;
//	private static Vote vote;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {
			Player p = ((Player) sender);
			Joueur j = Joueur.getJoueur(p);
			if (args.length == 1){
//				if(args[0].equalsIgnoreCase("startvote")){
//					vote = new Vote(Values.game);
//					for (Joueur joueur : Values.game.getMembre()) {
//						Player pl = joueur.getPlayer();
//						pl.sendMessage("§m-=----------------------------------------------=-");
//						pl.sendMessage("");
//						pl.sendMessage("  "+ChatColor.UNDERLINE + "§6Vote :");
//						TextComponent msg1 = new TextComponent("    §7► Cliquez ici pour Voter ! ");
//						msg1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/laby vote"));
//						pl.spigot().sendMessage(msg1);
//
//						pl.sendMessage("");
//						pl.sendMessage("§m-=----------------------------------------------=-");
//					}
//				}
//				if(args[0].equalsIgnoreCase("result")){
//					Bukkit.broadcastMessage("Resultat : "+Bukkit.getPlayer(vote.getResult()));
//				}
//				if(args[0].equalsIgnoreCase("vote")){
//					vote.openInventory(p);
//				}
				/*if(args[0].equalsIgnoreCase("next")){
					lab.Generation();
				}
				if(args[0].equalsIgnoreCase("create")){
					lab = new Labyrinthe(p.getLocation(),3,true);
//					lab.calcule();
				}
				if(args[0].equalsIgnoreCase("pregenere")){
//					lab.preGeneration();
				}
				if(args[0].equalsIgnoreCase("genere")){
					lab.Generation();
				}
				if(args[0].equalsIgnoreCase("delete")){
					lab.delete();
				}*/
				if(args[0].equalsIgnoreCase("porte")){
					if(door){
						j.getGame().getLab().openDoor(Direction.Nord);
					}else{
						j.getGame().getLab().closeDoor(Direction.Nord);
					}
					door = !door;
				}
				if(args[0].equalsIgnoreCase("seq")){
					j.getGame().getLab().PorteSequence();
					p.sendMessage("§a Séquence !");
				}
			}/*else if (args.length == 8){
				if(args[0].equalsIgnoreCase("create")){
					lab = new Labyrinthe(p.getLocation(),3,true);
					lab.setLongueur(Integer.parseInt(args[1]));
					lab.setLargeur(Integer.parseInt(args[2]));
					lab.setLargeurCouloir(Integer.parseInt(args[3]));
					lab.setHauteurMur(Integer.parseInt(args[4]));
					lab.setEpaisseurMur(Integer.parseInt(args[5]));
					lab.setRayon(Integer.parseInt(args[6]));
					lab.setDays(Integer.parseInt(args[7]));
//					lab.calcule();
				}
			}else{
				p.sendMessage("/laby create longueur largeur couloir hauteur epaiseurMur rayon day");
			}*/
		}
		return false;
	}
}
