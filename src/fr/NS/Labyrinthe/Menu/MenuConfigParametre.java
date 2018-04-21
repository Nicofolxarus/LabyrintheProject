package fr.NS.Labyrinthe.Menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.NS.Labyrinthe.API.ItemCreator;
import fr.NS.Labyrinthe.API.ItemCreator.BannerPreset;
import fr.NS.Labyrinthe.Configuration.Message;
import fr.NS.Labyrinthe.Gestion.Game;

public class MenuConfigParametre extends Menu {
	
	public enum Parametre{HauteurMur,EpaisseurMur,Largeur,Longueur,LargeurCouloir,RayonCentre;}
	
	
	private String MenuName = Message.ColorFonce+"Config Labyrinthe";
	private Inventory inv;
	private Game g;
	private Parametre param;
	
	public MenuConfigParametre(Game g,Parametre param) {
		this.g = g;
		this.param = param;
		
		if(param == Parametre.Longueur || param == Parametre.Largeur){
			inv = Bukkit.createInventory(null, 4*9,MenuName);
			
			inv.setItem(10, new ItemCreator(Material.BANNER).setBasecolor(DyeColor.RED).addBannerPreset(BannerPreset.moin,DyeColor.BLACK).setName(ChatColor.RED+"-10").addallItemsflags().getItem());
			inv.setItem(11, new ItemCreator(Material.BANNER).setBasecolor(DyeColor.ORANGE).addBannerPreset(BannerPreset.moin,DyeColor.BLACK).setName(ChatColor.GOLD+"-5").addallItemsflags().getItem());
			inv.setItem(12, new ItemCreator(Material.BANNER).setBasecolor(DyeColor.YELLOW).addBannerPreset(BannerPreset.moin,DyeColor.BLACK).setName(ChatColor.YELLOW+"-1").addallItemsflags().getItem());
			inv.setItem(14, new ItemCreator(Material.BANNER).setBasecolor(DyeColor.LIGHT_BLUE).addBannerPreset(BannerPreset.plus,DyeColor.BLACK).setName(ChatColor.AQUA+"+1").addallItemsflags().getItem());
			inv.setItem(15, new ItemCreator(Material.BANNER).setBasecolor(DyeColor.LIME).addBannerPreset(BannerPreset.plus,DyeColor.BLACK).setName(ChatColor.GREEN+"+5").addallItemsflags().getItem());
			inv.setItem(16, new ItemCreator(Material.BANNER).setBasecolor(DyeColor.GREEN).addBannerPreset(BannerPreset.plus,DyeColor.BLACK).setName(ChatColor.DARK_GREEN+"+10").addallItemsflags().getItem());
			
			inv.setItem(13, getItemParametre(Parametre.Longueur));
			
			inv.setItem(19, new ItemCreator(Material.BANNER).setBasecolor(DyeColor.RED).addBannerPreset(BannerPreset.moin,DyeColor.BLACK).setName(ChatColor.RED+"-10").addallItemsflags().getItem());
			inv.setItem(20, new ItemCreator(Material.BANNER).setBasecolor(DyeColor.ORANGE).addBannerPreset(BannerPreset.moin,DyeColor.BLACK).setName(ChatColor.GOLD+"-5").addallItemsflags().getItem());
			inv.setItem(21, new ItemCreator(Material.BANNER).setBasecolor(DyeColor.YELLOW).addBannerPreset(BannerPreset.moin,DyeColor.BLACK).setName(ChatColor.YELLOW+"-1").addallItemsflags().getItem());
			inv.setItem(23, new ItemCreator(Material.BANNER).setBasecolor(DyeColor.LIGHT_BLUE).addBannerPreset(BannerPreset.plus,DyeColor.BLACK).setName(ChatColor.AQUA+"+1").addallItemsflags().getItem());
			inv.setItem(24, new ItemCreator(Material.BANNER).setBasecolor(DyeColor.LIME).addBannerPreset(BannerPreset.plus,DyeColor.BLACK).setName(ChatColor.GREEN+"+5").addallItemsflags().getItem());
			inv.setItem(25, new ItemCreator(Material.BANNER).setBasecolor(DyeColor.GREEN).addBannerPreset(BannerPreset.plus,DyeColor.BLACK).setName(ChatColor.DARK_GREEN+"+10").addallItemsflags().getItem());
			
			inv.setItem(22, getItemParametre(Parametre.Largeur));
			
			inv.setItem(35, getItemGlassRetour());
			fillInventoryGlass(inv);
		}else{
			inv = Bukkit.createInventory(null, 3*9,MenuName);
			
			inv.setItem(10, new ItemCreator(Material.BANNER).setBasecolor(DyeColor.RED).addBannerPreset(BannerPreset.moin,DyeColor.BLACK).setName(ChatColor.RED+"-10").addallItemsflags().getItem());
			inv.setItem(11, new ItemCreator(Material.BANNER).setBasecolor(DyeColor.ORANGE).addBannerPreset(BannerPreset.moin,DyeColor.BLACK).setName(ChatColor.GOLD+"-5").addallItemsflags().getItem());
			inv.setItem(12, new ItemCreator(Material.BANNER).setBasecolor(DyeColor.YELLOW).addBannerPreset(BannerPreset.moin,DyeColor.BLACK).setName(ChatColor.YELLOW+"-1").addallItemsflags().getItem());
			inv.setItem(14, new ItemCreator(Material.BANNER).setBasecolor(DyeColor.LIGHT_BLUE).addBannerPreset(BannerPreset.plus,DyeColor.BLACK).setName(ChatColor.AQUA+"+1").addallItemsflags().getItem());
			inv.setItem(15, new ItemCreator(Material.BANNER).setBasecolor(DyeColor.LIME).addBannerPreset(BannerPreset.plus,DyeColor.BLACK).setName(ChatColor.GREEN+"+5").addallItemsflags().getItem());
			inv.setItem(16, new ItemCreator(Material.BANNER).setBasecolor(DyeColor.GREEN).addBannerPreset(BannerPreset.plus,DyeColor.BLACK).setName(ChatColor.DARK_GREEN+"+10").addallItemsflags().getItem());
			
			inv.setItem(13, getItemParametre(param));
			
			inv.setItem(26, getItemGlassRetour());
			fillInventoryGlass(inv);
		}
		
		
		
		
	}
	public ItemStack getItemParametre(Parametre p){
		switch (p) {
		case Largeur:
			return new ItemCreator(Material.IRON_FENCE).setName(Message.ColorFonce+"Largeur").addLore("").addLore(Message.Chat_fleche+Message.ColorClair+g.getParamLargeur()+" case(s)").getItem();
		case Longueur:
			return new ItemCreator(Material.IRON_FENCE).setName(Message.ColorFonce+"Longueur").addLore("").addLore(Message.Chat_fleche+Message.ColorClair+g.getParamLongeur()+" case(s)").getItem();
		case HauteurMur:
			return new ItemCreator(Material.INK_SACK).setDurability(15).setName(Message.ColorFonce+"Hauteur Mur").addLore("").addLore(Message.Chat_fleche+Message.ColorClair+g.getParamHauteurMur()+" block(s)").getItem();
		case EpaisseurMur:
			return new ItemCreator(Material.SMOOTH_BRICK).setDurability(1).setName(Message.ColorFonce+"Epaisseur des Murs").addLore("").addLore(Message.Chat_fleche+Message.ColorClair+g.getParamEpaisseurMur()+" block(s)").getItem();
		case LargeurCouloir:
			return new ItemCreator(Material.STEP).setDurability(5).setName(Message.ColorFonce+"Largeur Couloir").addLore("").addLore(Message.Chat_fleche+Message.ColorClair+g.getParamLargeurCouloir()+" block(s)").getItem();
		case RayonCentre:
			return new ItemCreator(Material.COMPASS).setName(Message.ColorFonce+"Rayon du centre").addLore("").addLore(Message.Chat_fleche+Message.ColorClair+g.getParamRayonCentre()+" case(s)").getItem();
		default:
			break;
		}
		return null;
	}
	@Override
	public void actualise(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(e.getInventory().getTitle().equalsIgnoreCase(MenuName)){
			e.setCancelled(true);
			if(Condition(e, getItemGlassRetour().getType(),getItemGlassRetour().getItemMeta().getDisplayName())){
				g.getMenuhost().getMenuConfigLabyrinthe().open(p);
				return;
			}
			if(Condition(e, Material.BANNER,ChatColor.RED+"-10")){
				if(e.getSlot() == 10){
					if(param == Parametre.Longueur || param == Parametre.Largeur){
						setValueParametre(-10, Parametre.Longueur);
					}else{
						setValueParametre(-10, param);
					}
					return;
				}
				if(e.getSlot() == 19){
					setValueParametre(-10, Parametre.Largeur);
					return;
				}
			}
			if(Condition(e, Material.BANNER,ChatColor.GOLD+"-5")){
				if(e.getSlot() == 11){
					if(param == Parametre.Longueur || param == Parametre.Largeur){
						setValueParametre(-5, Parametre.Longueur);
					}else{
						setValueParametre(-5, param);
					}
					return;
				}
				if(e.getSlot() == 20){
					setValueParametre(-5, Parametre.Largeur);
					return;
				}
			}
			if(Condition(e, Material.BANNER,ChatColor.YELLOW+"-1")){
				if(e.getSlot() == 12){
					if(param == Parametre.Longueur || param == Parametre.Largeur){
						setValueParametre(-1, Parametre.Longueur);
					}else{
						setValueParametre(-1, param);
					}
					return;
				}
				if(e.getSlot() == 21){
					setValueParametre(-1, Parametre.Largeur);
					return;
				}
			}
			if(Condition(e, Material.BANNER,ChatColor.AQUA+"+1")){
				if(e.getSlot() == 14){
					if(param == Parametre.Longueur || param == Parametre.Largeur){
						setValueParametre(+1, Parametre.Longueur);
					}else{
						setValueParametre(+1, param);
					}
					return;
				}
				if(e.getSlot() == 23){
					setValueParametre(+1, Parametre.Largeur);
					return;
				}
			}
			if(Condition(e, Material.BANNER,ChatColor.GREEN+"+5")){
				if(e.getSlot() == 15){
					if(param == Parametre.Longueur || param == Parametre.Largeur){
						setValueParametre(+5, Parametre.Longueur);
					}else{
						setValueParametre(+5, param);
					}
					return;
				}
				if(e.getSlot() == 24){
					setValueParametre(+5, Parametre.Largeur);
					return;
				}
			}
			if(Condition(e, Material.BANNER,ChatColor.DARK_GREEN+"+10")){
				if(e.getSlot() == 16){
					if(param == Parametre.Longueur || param == Parametre.Largeur){
						setValueParametre(+10, Parametre.Longueur);
					}else{
						setValueParametre(+10, param);
					}
					return;
				}
				if(e.getSlot() == 25){
					setValueParametre(+10, Parametre.Largeur);
					return;
				}
			}
		}
	}

	public void setValueParametre(int x,Parametre param){
		switch (param) {
		case Largeur:
			g.setParamLargeur(g.getParamLargeur()+x);
			inv.setItem(22, getItemParametre(Parametre.Largeur));
			break;
		case Longueur:
			g.setParamLongeur(g.getParamLongeur()+x);
			inv.setItem(13, getItemParametre(Parametre.Longueur));
			break;
		case HauteurMur:
			g.setParamHauteurMur(g.getParamHauteurMur()+x);
			break;
		case EpaisseurMur:
			g.setParamEpaisseurMur(g.getParamEpaisseurMur()+x);
			break;
		case LargeurCouloir:
			g.setParamLargeurCouloir(g.getParamLargeurCouloir()+x);
			break;
		case RayonCentre:
			g.setParamRayonCentre(g.getParamRayonCentre()+x);
			break;
		default:
			break;
		}
		g.getMenuhost().getMenuConfigLabyrinthe().updateMenu(param);
		if(param != Parametre.Longueur && param != Parametre.Largeur){
			inv.setItem(13, getItemParametre(param));
		}
	}
	
	@Override
	public void close(Player p) {
		
	}

	@Override
	public Inventory getInventory() {
		return inv;
	}

}
