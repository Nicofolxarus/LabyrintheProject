package fr.NS.Labyrinthe.Commandes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import fr.NS.Labyrinthe.API.Debug;

public class ComDebug implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (cmd.getName().equals("debug") && args.length == 0) {

			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (Debug.debugpllist.contains(p)) {
					Debug.debugpllist.remove(p);
					sender.sendMessage("Debug -> Desactive");
				} else {
					Debug.debugpllist.add(p);
					sender.sendMessage("Debug -> Active");
				}
			}
			if (sender instanceof ConsoleCommandSender) {
				if (Debug.console == true) {
					Debug.console = false;
					sender.sendMessage("Debug -> Desactive");
				} else {
					Debug.console = true;
					sender.sendMessage("Debug -> Active");
				}
			}
		}
		return false;
	}
}
