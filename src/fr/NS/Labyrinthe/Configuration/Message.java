package fr.NS.Labyrinthe.Configuration;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

public class Message {

	
	// Monde
	public static World world;
	public static String world_nether = "world_nether";
	public static Location centre;
	
	// Prefix
	public static String ColorClair = "" + ChatColor.DARK_GRAY;
	public static String ColorFonce = "" + ChatColor.DARK_AQUA;
	public static String ColorGris = "" + ChatColor.GRAY;
	public static String Chat_fleche = ChatColor.DARK_GRAY + "► ";
	public static String Prefix = ChatColor.DARK_GRAY + "[" + ColorFonce +"Labyrinthe"+ ChatColor.DARK_GRAY + "] ";

	// Join Leave
	public static String Join = ChatColor.GRAY + " a rejoint la partie !";
	public static String Leave = ChatColor.GRAY + " a quitté la partie !";

	// Menu
	public static String Menu_Labyrinthe = ColorFonce + "Labyrinthe";

	// Timer
	public static String Title_Prefix = ColorFonce +"Labyrinthe";

}
