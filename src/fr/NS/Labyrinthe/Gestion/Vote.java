package fr.NS.Labyrinthe.Gestion;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;

import fr.NS.Labyrinthe.API.ItemCreator;
import fr.NS.Labyrinthe.Configuration.Message;

public class Vote {
	
	public Vote(Vote vote,Player p){
		vote.openInventory(p);
	}
	
	
	private Game game;
	private Inventory inv;
	private HashMap<UUID, UUID> Vote = new HashMap<>();

	public Vote(Game game) {
		this.game = game;
		int nbslot;
		if (game.getMembre().size() <= 9) {
			nbslot = 9;
		} else if (game.getMembre().size() <= 18) {
			nbslot = 18;
		} else if (game.getMembre().size() <= 27) {
			nbslot = 27;
		} else if (game.getMembre().size() <= 36) {
			nbslot = 36;
		} else if (game.getMembre().size() <= 45) {
			nbslot = 45;
		} else {
			nbslot = 45;
		}

		inv = Bukkit.createInventory(null, nbslot, Message.ColorFonce + "Vote");

		int i = 0;
		for (Joueur joueur : game.getMembre()) {
			inv.setItem(i,new ItemCreator(Material.SKULL_ITEM).setDurability((short) 3).setOwner(joueur.getPlayer().getName()).setName("§6" + joueur.getPlayer().getName()).addLore("§8► §eCliquer pour voter").getItem());
			i++;
		}
	}

	public UUID getResult() {
		HashMap<UUID, Integer> result = new HashMap<>();

		for (UUID key : Vote.keySet()) {
			if (!result.containsKey(Vote.get(key))) {
				result.put(Vote.get(key), 1);
			} else {
				int nb = result.get(Vote.get(key));
				nb++;
				result.put(Vote.get(key), nb);
			}
		}
		UUID winner = null;
		int vote = 0;
		for (UUID uuid : result.keySet()) {
			if (result.get(uuid) > vote) {
				vote = result.get(uuid);
				winner = uuid;
			}
		}

		return winner;

	}

	public void inventoryClickEvent(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getTitle().equalsIgnoreCase(inv.getTitle())) {
			if (e.getCurrentItem() == null) {
				e.setCancelled(true);
				return;
			}
			if (e.getCurrentItem().getType() == Material.SKULL_ITEM && e.getCurrentItem().getDurability() == 3) {
				if (!aVote(p.getUniqueId())) {
					SkullMeta head = (SkullMeta) e.getCurrentItem().getItemMeta();
					String pl = head.getOwner();
					Player player = Bukkit.getPlayer(pl);

					if (player != null) {
						addVote(p.getUniqueId(), player.getUniqueId());
					}
				} else {
					p.sendMessage(Message.Chat_fleche+"§c Vous avez deja Voté !");
				}
			}
			e.setCancelled(true);
		}
	}

	public void addVote(UUID joueur, UUID vote) {
		if (!aVote(joueur)) {
			Vote.put(joueur, vote);
		}
	}

	public boolean aVote(UUID uuid) {
		if (Vote.containsKey(uuid)) {
			return true;
		} else {
			return false;
		}
	}

	public void openInventory(Player p) {
		if (p != null) {
			p.openInventory(inv);
		}
	}

}
