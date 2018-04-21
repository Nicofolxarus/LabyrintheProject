package fr.NS.Labyrinthe.Gestion;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.NS.Labyrinthe.ScoreBoard.ScoreBoard;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
/*
 * This plugin was create by Seraleme
 * 
 * -You are not allowed to :
 * 		-Copy the code
 * 		-Write your name on this plugin
 * 	
 * To contact me send private message on twitter
 * @Seraleme
 * 
 */
public class Joueur {
	
	private UUID uuid;
	private Player p;
	
	private ScoreBoard sc;
	private Game game;
	public static HashMap<UUID,Joueur> Joueurs = new HashMap<>();
	private boolean isHost = false;
	private boolean isSpectateur = false;
	private boolean isVanish = false;
	private int kill = 0;
	
	public Joueur(UUID uuid) {
		this.uuid = uuid;
		this.p = Bukkit.getPlayer(uuid);
	}
	public Joueur(Player p) {
		this.uuid = p.getUniqueId();
		this.p = p;
	}
	
	public UUID getUuid() {
		return uuid;
	}

	public boolean isconnect() {
		return p.isOnline();
	}
	
	public boolean isOp(){
		return p.isOp();
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Player getPlayer() {
		return p;
	}

	public void vanish() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if(!p.equals(this.p)){
				p.hidePlayer(this.p);
			}
		}
		setVanish(true);
	}

	public void unvanish() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if(!p.equals(this.p)){
				p.showPlayer(this.p);
			}
		}
		setVanish(false);
	}

	public void clearall() {
		if (p != null) {
			p.getInventory().clear();
			p.getInventory().setHelmet(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setBoots(null);
			p.setLevel(0);
			p.setExp(0);
			p.setFireTicks(0);
			p.setMaxHealth(20);
			EntityLiving el = ((CraftPlayer)p).getHandle();
			el.setAbsorptionHearts(0);
			p.getActivePotionEffects().forEach(eff -> p.removePotionEffect(eff.getType()));
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 20, 200));
		}
	}

	public void sendTablist(String header, String footer) {
		if (header == null)
			header = "";
		if (footer == null)
			footer = "";

		IChatBaseComponent tabHeader = ChatSerializer.a("{\"text\":\"" + header + "\"}");
		IChatBaseComponent tabFooter = ChatSerializer.a("{\"text\":\"" + footer + "\"}");

		PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabHeader);

		try {
			Field field = headerPacket.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(headerPacket, tabFooter);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(headerPacket);
		}

	}

	public void playSound(Sound s) {
		p.getWorld().playSound(p.getLocation(), s, 0.3F, 0.8F);
	}
	public void playSound(Sound s,int volume) {
		p.getWorld().playSound(p.getLocation(), s, 1, volume);
	}

	public void sendTitle(String msgTitle, String msgSubTitle, int ticks) {
		IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + msgTitle + "\"}");
		IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\": \"" + msgSubTitle + "\"}");
		PacketPlayOutTitle p1 = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
		PacketPlayOutTitle p2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(p1);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(p2);
		sendTime(ticks);
	}

	public void sendTitleNofondu(String msgTitle, String msgSubTitle, int ticks) {
		IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + msgTitle + "\"}");
		IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\": \"" + msgSubTitle + "\"}");
		PacketPlayOutTitle p1 = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
		PacketPlayOutTitle p2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(p1);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(p2);
		sendTimeNoFondu(ticks);
	}

	private void sendTimeNoFondu(int ticks) {
		PacketPlayOutTitle p1 = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, 0, ticks, 0);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(p1);
	}

	private void sendTime(int ticks) {
		PacketPlayOutTitle p1 = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, 20, ticks, 20);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(p1);
	}
	public void sendActionBar(String message) {
		IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
	}
	public static Joueur getJoueur(Player p){
		if(Joueurs.get(p.getUniqueId()) == null){
			return Joueur.createJoueur(p);
		}else{
			return Joueurs.get(p.getUniqueId());
		}
		
	}
	public static Joueur getJoueur(UUID uuid){
		if(Joueurs.get(uuid) == null){
			return Joueur.createJoueur(uuid);
		}else{
			return Joueurs.get(uuid);
		}
	}
	public void deleteJoueur(){
		Joueurs.remove(uuid);
	}
	public static Joueur createJoueur(Player p){
		if(Joueurs.get(p.getUniqueId()) == null){
			Joueur j = new Joueur(p);
			Joueurs.put(p.getUniqueId(),j);
			return j;
		}else{
			return Joueur.getJoueur(p);
		}
		
	}
	public static Joueur createJoueur(UUID uuid){
		if(Joueurs.get(uuid) == null){
			Joueur j = new Joueur(uuid);
			Joueurs.put(uuid,j);
			return j;
		}else{
			return Joueur.getJoueur(uuid);
		}
	}
	public static HashMap<UUID,Joueur> getJoueurs(){
		return Joueurs;
	}
	public void setPlayer(Player p) {
		this.p = p;
	}
	public ScoreBoard getScoreBoard() {
		return sc;
	}
	public void setScoreBoard(ScoreBoard sc) {
		this.sc = sc;
	}
	public int getKill() {
		return kill;
	}
	public void setKill(int kill) {
		this.kill = kill;
	}
	public boolean isHost() {
		return isHost;
	}
	public void setHost(boolean isHost) {
		this.isHost = isHost;
	}
	public boolean isSpectateur() {
		return isSpectateur;
	}
	public void setSpectateur(boolean isSpectateur) {
		this.isSpectateur = isSpectateur;
	}
	public boolean isVanish() {
		return isVanish;
	}
	public void setVanish(boolean isVanish) {
		this.isVanish = isVanish;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
}
