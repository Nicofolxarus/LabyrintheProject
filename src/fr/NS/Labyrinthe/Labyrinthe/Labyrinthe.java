package fr.NS.Labyrinthe.Labyrinthe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.TrapDoor;
import org.bukkit.scheduler.BukkitTask;

import fr.NS.Labyrinthe.API.Debug;
import fr.NS.Labyrinthe.Generale.Main;
import fr.NS.Labyrinthe.Gestion.Game;
import fr.NS.Labyrinthe.Porte.Porte;

public class Labyrinthe {

	public enum Orientation {
		Horizontale, Verticale;
	}
	
	public enum Direction {
		Nord, Sud, Est, Ouest;
	}

	private BukkitTask taskcalcul;
	private BukkitTask taskgeneration;
	private BukkitTask taskpregeneration;
	private BukkitTask taskdelete;

	private Labyrinthe lab = this;
	
	private int longueur = 100;
	private int largeur = 100;
	private int largeurCouloir = 5;
	private int hauteurmur = 15;
	private int epaisseurmur = 3;

	private int day = -1;

	private boolean calcul = false;
	private boolean pregeneration = false;

	private boolean cercle = true;
	private int rayon = 20;
	
	private Integer days;
	private Location loc;
	private Location locCentre;
	private boolean locationcentre = false;
	
	private int TimeSecPorte = 3;
	
	private long time;
	private int Sequence = 0;//porte
	
	private ArrayList<Case> Cases = new ArrayList<>();
	private ArrayList<Integer> PortesSequence = new ArrayList<>(Arrays.asList(2,3,1,5,8,6,7,4));//séquence
	private ArrayList<ArrayList<Entrer<Mur, Boolean>>> diferencejourmur = new ArrayList<>();
	private HashMap<Integer,Mur> PortesExt = new HashMap<>();
	private HashMap<Direction,Mur> PortesInt = new HashMap<>();
	
	
	public Labyrinthe(Location loc, int days) {
		this.loc = loc;
		this.lab = this;
		this.days = days;
	}

	public Labyrinthe(Location loccentre, int days, boolean isloccentre) {
		this.loc = loccentre;
		this.setLocCentre(loccentre);
		this.lab = this;
		this.days = days;
		this.locationcentre = isloccentre;
	}
	
	public void PorteSequence(){
		//Portes Sequence
		int Lastsequence = Sequence - 1;
		if(Lastsequence < 0){
			Lastsequence = PortesSequence.size() -1;
		}
		Mur lmur = PortesExt.get(PortesSequence.get(Lastsequence));
		if(lmur != null){
			if(lmur.getBox() != null){
				if(lmur.getPorte() != null){
					lmur.getPorte().Close();
				}else{
					lmur.getBox().generate();
				}
			}
		}
		
		int num = PortesSequence.get(Sequence);
		Mur mur = PortesExt.get(num);
		if(mur != null){
			if(mur.getBox() != null){
				if(mur.getPorte() != null){
					mur.getPorte().Open();
				}else{
					mur.getBox().delete();
				}
				if(mur.getOrientation() == Orientation.Horizontale){ //Nord et Sud
					new Box(mur.getBox().getX(), mur.getBox().getY(), mur.getBox().getZ()-1, mur.getBox().getDx(), mur.getBox().getDy(), mur.getBox().getDz()+1, mur.getBox().getWorld()).deleteOnly(Material.LEAVES);
				}else if(mur.getOrientation() == Orientation.Verticale){ //Nord et Sud
					new Box(mur.getBox().getX()-1, mur.getBox().getY(), mur.getBox().getZ(), mur.getBox().getDx()+1, mur.getBox().getDy(), mur.getBox().getDz(), mur.getBox().getWorld()).deleteOnly(Material.LEAVES);
				}
			}
		}
		Sequence++;
		if(Sequence >= PortesSequence.size()){
			Sequence = 0;
		}
	}
	
	public void openDoor(Direction d){
		Mur m = PortesInt.get(d);
		if(m.isDoor()){
			if(m.getPorte() != null){
				m.getPorte().Open();
				if(m.getOrientation() == Orientation.Horizontale){ //Nord et Sud
					new Box(m.getBox().getX(), m.getBox().getY(), m.getBox().getZ()-1, m.getBox().getDx(), m.getBox().getDy(), m.getBox().getDz()+1, m.getBox().getWorld()).deleteOnly(Material.LEAVES);
				}else if(m.getOrientation() == Orientation.Verticale){ //Nord et Sud
					new Box(m.getBox().getX()-1, m.getBox().getY(), m.getBox().getZ(), m.getBox().getDx()+1, m.getBox().getDy(), m.getBox().getDz(), m.getBox().getWorld()).deleteOnly(Material.LEAVES);
				}
			}
		}
	}
	public void closeDoor(Direction d){
		Mur m = PortesInt.get(d);
		if(m.isDoor()){
			if(m.getPorte() != null){
				m.getPorte().Close();
			}
		}
	}
	public void openDoor(int num){
		Mur m = PortesExt.get(num);
		if(m.isDoor()){
			if(m.getPorte() != null){
				m.getPorte().Open();
				if(m.getOrientation() == Orientation.Horizontale){ //Nord et Sud
					new Box(m.getBox().getX(), m.getBox().getY(), m.getBox().getZ()-1, m.getBox().getDx(), m.getBox().getDy(), m.getBox().getDz()+1, m.getBox().getWorld()).deleteOnly(Material.LEAVES);
				}else if(m.getOrientation() == Orientation.Verticale){ //Nord et Sud
					new Box(m.getBox().getX()-1, m.getBox().getY(), m.getBox().getZ(), m.getBox().getDx()+1, m.getBox().getDy(), m.getBox().getDz(), m.getBox().getWorld()).deleteOnly(Material.LEAVES);
				}
			}
		}
	}
	public void closeDoor(int num){
		Mur m = PortesExt.get(num);
		if(m.isDoor()){
			if(m.getPorte() != null){
				m.getPorte().Close();
			}
		}
	}
	public void setAscenseur(Location l){
		Location loc = new Location(l.getWorld(), l.getBlockX(), l.getBlockY()-1, l.getBlockZ());
		
		//Set Contour en Andesite Polished
		new Box(loc.getBlockX()-2, loc.getBlockY(), loc.getBlockZ()-2, loc.getBlockX()+1, loc.getBlockY(), loc.getBlockZ()+2, loc.getWorld()).generate(Material.STONE, (byte) 6);
		
		setTrapDoor(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), BlockFace.WEST);
		setTrapDoor(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()-1), BlockFace.WEST);
		setTrapDoor(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()+1), BlockFace.WEST);
	
		setTrapDoor(new Location(loc.getWorld(), loc.getBlockX()-1, loc.getBlockY(), loc.getBlockZ()), BlockFace.EAST);
		setTrapDoor(new Location(loc.getWorld(), loc.getBlockX()-1, loc.getBlockY(), loc.getBlockZ()-1), BlockFace.EAST);
		setTrapDoor(new Location(loc.getWorld(), loc.getBlockX()-1, loc.getBlockY(), loc.getBlockZ()+1), BlockFace.EAST);
		
		//sol Cage
		new Box(loc.getBlockX()-3, loc.getBlockY()-3, loc.getBlockZ()-3, loc.getBlockX()+2, loc.getBlockY()-3, loc.getBlockZ()+3, loc.getWorld()).generate(Material.STONE, (byte) 6);
		//contour en Bedrock
		new Box(loc.getBlockX()-3, loc.getBlockY()-2, loc.getBlockZ()-3, loc.getBlockX()+2, loc.getBlockY()-1, loc.getBlockZ()+3, loc.getWorld()).generate(Material.BEDROCK);
		//Fence Iron
		new Box(loc.getBlockX()-2, loc.getBlockY()-2, loc.getBlockZ()-2, loc.getBlockX()+1, loc.getBlockY()-1, loc.getBlockZ()+2, loc.getWorld()).generate(Material.IRON_FENCE);
		
		new Box(loc.getBlockX()-1, loc.getBlockY()-2, loc.getBlockZ()-1, loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()+1, loc.getWorld()).delete();
	}
	
	private void setTrapDoor(Location l,BlockFace bf){
		l.getBlock().setType(Material.IRON_TRAPDOOR);
		BlockState state = l.getBlock().getState();
        TrapDoor trapdoor = (TrapDoor) state.getData();
        trapdoor.setOpen(false);
        trapdoor.setFacingDirection(bf);
        state.update();
	}
	public void calcule(boolean pregenere,Game g) {
		Cases.clear();
		PortesInt.clear();
		diferencejourmur.clear();
		PortesExt.clear();
		taskcalcul = Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				time = System.currentTimeMillis();
				Debug.send_debug("Démarrage des calcules !  (" + longueur + " x " + largeur + ";" + days + ")");
				// generation des cases
				Debug.send_debug("Generation des cases");
				int CaseOff = 0;
				int Size = largeur * longueur;
				int x = -1;
				int z = 0;
				for (int i = 1; i <= Size; i++) {
					x++;
					if (x >= longueur) {
						z++;
						x = 0;
					}
					Case cas = new Case(lab, i, x, z);
					Cases.add(cas);
				}

				// Create center

				// calcules et links des murs
				Debug.send_debug("Creation des murs");
				for (Case cas : Cases) {
					if (cas.getMurNord() == null) {
						Mur m = new Mur(false, Orientation.Horizontale);
						cas.setMurNord(m);
						m.setCase1(cas);
						Case cn = getothercase(cas, Direction.Nord);
						if (cn != null) {
							Case ca = cn;
							m.setCase2(ca);
							ca.setMurSud(m);
						}
					}

					if (cas.getMurSud() == null) {
						Mur m = new Mur(false, Orientation.Horizontale);
						cas.setMurSud(m);
						m.setCase1(cas);
						Case cs = getothercase(cas, Direction.Sud);
						if (cs != null) {
							Case ca = cs;
							m.setCase2(ca);
							ca.setMurNord(m);
						}
					}

					if (cas.getMurOuest() == null) {
						Mur m = new Mur(false, Orientation.Verticale);
						cas.setMurOuest(m);
						m.setCase1(cas);
						Case co = getothercase(cas, Direction.Ouest);
						if (co != null) {
							Case ca = co;
							m.setCase2(ca);
							ca.setMurEst(m);
						}
					}

					if (cas.getMurEst() == null) {
						Mur m = new Mur(false, Orientation.Verticale);
						cas.setMurEst(m);
						m.setCase1(cas);
						Case ce = getothercase(cas, Direction.Est);
						if (ce != null) {
							Case ca = ce;
							m.setCase2(ca);
							ca.setMurOuest(m);
						}
					}
				}

				// prise en compte des cases off
				Debug.send_debug("Mise en place des cases off");

				if (cercle) {
					int xmin = (longueur / 2) - rayon;
					int xmax = (longueur / 2) + rayon;
					int zmin = (largeur / 2) - rayon;
					int zmax = (largeur / 2) + rayon;
//					int xcenter = longueur / 2;
//					int zcenter = largeur / 2;

					for (Case c : Cases) {
						//centre
						if (c.getX() > xmin && c.getX() < xmax && c.getZ() > zmin && c.getZ() < zmax) {
//							if (Math.pow(Math.pow(xcenter - Math.pow(Math.pow(c.getX(), 2), 0.5), 2)+Math.pow(zcenter - Math.pow(Math.pow(c.getZ(), 2), 0.5), 2),0.5) < rayon) {
								c.setOff(true);
								CaseOff++;
//							}
						}
					}
				}
				
				int xMid = longueur/2;
				int zMid = largeur/2;
				// calcules des murs non ouvrables
				for (Case c : Cases) {
					Case cn = getothercase(c, Direction.Nord);
					Case cs = getothercase(c, Direction.Sud);
					Case co = getothercase(c, Direction.Ouest);
					Case ce = getothercase(c, Direction.Est);

					if (cn != null) {
						if (cn.isOff()) {
							if (c.getMurNord().getCase1() != null && c.getMurNord().getCase2() != null) {
								if (c.getMurNord().getCase1().isOff() && c.getMurNord().getCase2().isOff()) {
									c.getMurNord().setLock(true);
									c.getMurNord().setOuvert(true);
								} else if(((!c.getMurNord().getCase1().isOff() && c.getMurNord().getCase2().isOff()) || (c.getMurNord().getCase1().isOff() && !c.getMurNord().getCase2().isOff())) && (c.getX() == xMid)){
									PortesInt.put(Direction.Nord, c.getMurNord());
									c.getMurNord().setLock(true);
									c.getMurNord().setDoor(true);
								}else{
									c.getMurNord().setLock(true);
								}
							}
						}
					} else {
						c.getMurNord().setLock(true);
					}
					if (cs != null) {
						if (cs.isOff()) {
							if (c.getMurSud().getCase1() != null && c.getMurSud().getCase2() != null) {
								if (c.getMurSud().getCase1().isOff() && c.getMurSud().getCase2().isOff()) {
									c.getMurSud().setLock(true);
									c.getMurSud().setOuvert(true);
								} else if(((!c.getMurSud().getCase1().isOff() && c.getMurSud().getCase2().isOff()) || (c.getMurSud().getCase1().isOff() && !c.getMurSud().getCase2().isOff())) && (c.getX() == xMid)){
									PortesInt.put(Direction.Sud, c.getMurSud());
									c.getMurSud().setLock(true);
									c.getMurSud().setDoor(true);
								} else {
									c.getMurSud().setLock(true);
								}
							}
						}
					} else {
						c.getMurSud().setLock(true);
					}
					if (co != null) {
						if (co.isOff()) {
							if (c.getMurOuest().getCase1() != null && c.getMurOuest().getCase2() != null) {
								if (c.getMurOuest().getCase1().isOff() && c.getMurOuest().getCase2().isOff()) {
									c.getMurOuest().setLock(true);
									c.getMurOuest().setOuvert(true);
								} else if(((!c.getMurOuest().getCase1().isOff() && c.getMurOuest().getCase2().isOff()) || (c.getMurOuest().getCase1().isOff() && !c.getMurOuest().getCase2().isOff())) && (c.getZ() == zMid)){
									PortesInt.put(Direction.Ouest, c.getMurOuest());
									c.getMurOuest().setLock(true);
									c.getMurOuest().setDoor(true);
								} else {
									c.getMurOuest().setLock(true);
								}
							}
						}
					} else {
						c.getMurOuest().setLock(true);
					}
					if (ce != null) {
						if (ce.isOff()) {
							if (c.getMurEst().getCase1() != null && c.getMurEst().getCase2() != null) {
								if (c.getMurEst().getCase1().isOff() && c.getMurEst().getCase2().isOff()) {
									c.getMurEst().setLock(true);
									c.getMurEst().setOuvert(true);
								} else if(((!c.getMurEst().getCase1().isOff() && c.getMurEst().getCase2().isOff()) || (c.getMurEst().getCase1().isOff() && !c.getMurEst().getCase2().isOff())) && (c.getZ() == zMid)){
									PortesInt.put(Direction.Est, c.getMurEst());
									c.getMurEst().setLock(true);
									c.getMurEst().setDoor(true);
								} else {
									c.getMurEst().setLock(true);
								}
							}
						}
					} else {
						c.getMurEst().setLock(true);
					}
				}
				// calcule des murs enlevable qui prenent de la place dans
				// l'algo
				Debug.send_debug("Creation d'une list des murs modifiables");
				ArrayList<Mur> MurEnlevable = calculdestroyablewall();
				ArrayList<ArrayList<Mur>> allstatemur = new ArrayList<>();
				// calcules des murs pour chacun des murs
				for (int d = 0; d <= days; d++) {
					Debug.send_debug("Calcules jours : " + d);
					ArrayList<Mur> murtemporairmentenlevable = new ArrayList<Mur>(MurEnlevable);
					ArrayList<Mur> allmuropen = new ArrayList<>();
					for (int wall = 1; wall < (Cases.size() - CaseOff);wall++) {
						Random random = new Random();
						int rand = random.nextInt(murtemporairmentenlevable.size());
						Mur m = murtemporairmentenlevable.get(rand);
						allmuropen.add(m);
						int num = m.getCase1().getNumero();
						int numchange = m.getCase2().getNumero();
						for (Case oc : Cases) {
							if (oc.getNumero() == numchange) {
								oc.setNumero(num);
							}
						}
						// Verification des murs similaires
						for (int i = 0; i < murtemporairmentenlevable.size(); i++) {
							Mur mu = murtemporairmentenlevable.get(i);
							if (mu.getCase1().getNumero() == mu.getCase2().getNumero()) {
								murtemporairmentenlevable.remove(mu);
								i--;
							}
						}
					}
					for (Case c : Cases) {
						c.setNumero(c.getId());
					}
					allstatemur.add(allmuropen);
				}

				int daytrated = -1;
				for (ArrayList<Mur> alistm : allstatemur) {
					Debug.send_debug("Calcules differences jours : " + (daytrated + 1));
					ArrayList<Entrer<Mur, Boolean>> mursmodif = new ArrayList<>();
					if (alistm == allstatemur.get(0)) {
						for (Mur m : MurEnlevable) {
							if (!alistm.contains(m)) {
								mursmodif.add(new Entrer<Mur, Boolean>(m, false));
							}
						}
					} else {
						ArrayList<Mur> listprecedente = new ArrayList<Mur>(allstatemur.get(daytrated));
						for (Mur m : listprecedente) {
							if (!alistm.contains(m)) {
								// ferme
								mursmodif.add(new Entrer<Mur, Boolean>(m, false));
							}
						}
						for (Mur m : alistm) {
							if (!listprecedente.contains(m)) {
								// ouvre
								mursmodif.add(new Entrer<Mur, Boolean>(m, true));
							}
						}
					}
					diferencejourmur.add(mursmodif);
					daytrated++;
				}
				allstatemur.clear();
				
				
				//x -> Longueur
				//z -> Largeur
				
				//nord
				int p1 = new BigDecimal(Math.ceil(new BigDecimal(longueur).multiply(new BigDecimal("0.33")).doubleValue())).intValue();
				int p2 = new BigDecimal(Math.ceil(new BigDecimal(longueur).multiply(new BigDecimal("0.66")).doubleValue())).intValue();
				
				//est
				int p3 = new BigDecimal(Math.ceil(new BigDecimal(largeur).multiply(new BigDecimal("0.33")).doubleValue())).intValue(); // 1/3
				int p4 = new BigDecimal(Math.ceil(new BigDecimal(largeur).multiply(new BigDecimal("0.66")).doubleValue())).intValue(); // 2/3
				
				//sud
				int p5 = p1;
				int p6 = p2;
				
				//ouest
				int p7 = p3;
				int p8 = p4;
				
				for (Case cas : Cases) {
					for (Mur mur : cas.getMur()) {
						if (!mur.isLock()) {
							mur.setOuvert(true);
						}
					}
					//nord
					if(cas.getX() == (longueur-1) && cas.getZ() == p1){
						PortesExt.put(1, cas.getMurEst());
						cas.getMurEst().setDoor(true);
					}else if(cas.getX() == (longueur-1) && cas.getZ() == p2){
						PortesExt.put(2, cas.getMurEst());
						cas.getMurEst().setDoor(true);
					}else 
					//est	
					if(cas.getX() == p3 && cas.getZ() == (largeur-1)){
						PortesExt.put(3, cas.getMurSud());
						cas.getMurSud().setDoor(true);
					}else if(cas.getX() == p4 && cas.getZ() == (largeur-1)){
						PortesExt.put(4, cas.getMurSud());
						cas.getMurSud().setDoor(true);
					}else
						
					//sud
					if(cas.getX() == 0 && cas.getZ() == p5){
						PortesExt.put(5, cas.getMurOuest());
						cas.getMurOuest().setDoor(true);
					}else if(cas.getX() == 0 && cas.getZ() == p6){
						PortesExt.put(6, cas.getMurOuest());
						cas.getMurOuest().setDoor(true);
					}else
					//ouest
					if(cas.getX() == p7 && cas.getZ() == 0){
						PortesExt.put(7, cas.getMurNord());
						cas.getMurNord().setDoor(true);
					}else if(cas.getX() == p8 && cas.getZ() == 0){
						PortesExt.put(8, cas.getMurNord());
						cas.getMurNord().setDoor(true);
					}
				}
				Debug.send_debug("Calcules fait en :  " + ((double) (System.currentTimeMillis() - time) / 1000) + "s");
				Bukkit.getConsoleSender().sendMessage("§cCalcul fini");
				setCalcul(true);
				if(pregenere){
					preGeneration(g);
				}
				taskcalcul.cancel();
			}
		});
	}

	public void Generation() {
		if (isCalcul() && isPreGenere()) {
			time = System.currentTimeMillis();

			ArrayList<Entrer<Mur, Boolean>> diferencejouractuel;
			day++;
			if (day < diferencejourmur.size()) {
				diferencejouractuel = diferencejourmur.get(day);
				taskgeneration = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
					Integer current_mur = 0;
					Integer current_case = 0;
					Boolean finish = false;
					
					@Override
					public void run() {
						Long timer = System.currentTimeMillis();
						// Generation Diffences jours
						while (System.currentTimeMillis() - 50 < timer) {
							current_mur++;
							current_case++;
							if (diferencejouractuel.size() > (current_mur-1)) {
								Entrer<Mur, Boolean> e = diferencejouractuel.get(current_mur - 1);
								if (e.getValue()){
									if (e.getKey().getBox() != null) {
										Box b = e.getKey().getBox();
										// b.delete();
										b.generate(Material.LEAVES, Material.AIR, Material.AIR, Material.AIR,Material.AIR);
										
										if (e.getKey().getOrientation() == Orientation.Horizontale) {
											new Box(b.getX() + 1, b.getY(), b.getZ(), b.getDx() - 1, b.getDy(),b.getDz(), b.getWorld()).delete();
										} else if (e.getKey().getOrientation() == Orientation.Verticale) {
											new Box(b.getX(), b.getY(), b.getZ() + 1, b.getDx(), b.getDy(),b.getDz() - 1, b.getWorld()).delete();
										}
									}
								} else {
									if (e.getKey().getBox() != null) {
										e.getKey().getBox().generate();
									}
								}
								e.getKey().setOuvert(e.getValue());
								current_case = 0;
							}else if(Cases.size() > (current_case-1)){
								// Generation feuilles
								Case cases = Cases.get(current_case-1);
								if (!cases.isOff()) {
									Box b = cases.getBox();
									if(b != null){
										Box decor = new Box(b.getX(), b.getY(), b.getZ(), b.getDx(), b.getDy(), b.getDz(),b.getWorld());
		
										decor.generate(Material.LEAVES, Material.AIR, Material.AIR, Material.AIR,
												Material.AIR);
		
										Box cutdecor = new Box(b.getX() + 1, b.getY(), b.getZ() + 1, b.getDx() - 1, b.getDy(),
												b.getDz() - 1, b.getWorld());
										Box cutdecor1 = new Box(b.getX() + 1, b.getY(), b.getZ() + 1, b.getDx() - 1, b.getDy(),
												b.getDz() - 1, b.getWorld());
		
										if (cases.getMurEst().isOuvert()) {
											cutdecor.setDx(cutdecor.getDx() + 1);
										}
										if (cases.getMurOuest().isOuvert()) {
											cutdecor.setX(cutdecor.getX() - 1);
										}
		
										if (cases.getMurNord().isOuvert()) {
											cutdecor1.setZ(cutdecor1.getZ() - 1);
										}
										if (cases.getMurSud().isOuvert()) {
											cutdecor1.setDz(cutdecor1.getDz() + 1);
										}
		
										cutdecor.delete();
										cutdecor1.delete();
									}
								}
							}else{
								finish = true;
								break;
							}
						}
						if (finish) {
							Debug.send_debug("Generation fait en :  "
									+ ((double) (System.currentTimeMillis() - time) / 1000) + "s");
							Bukkit.getConsoleSender().sendMessage("§cGeneration fini");
							taskgeneration.cancel();
						}
					}
				}, 0, 1);
				
				
			}
		}
	}

	// public void Generation(Integer day) {
	//
	// if (calcfinish == true) {
	// if (days >= day) {
	// time = System.currentTimeMillis();
	// Debug.send_debug(
	// "Starting generation!! (" + Longueur + "*" + Largeur + ";" + days + ") at
	// day: " + day);
	// setState("Generation en cour");
	// genfinish = false;
	// ArrayList<Entrer<Mur, Boolean>> diferencejouractuel;
	// if (day > 0) {
	// diferencejouractuel = diferencejourmur.get(day - 1);
	// } else {
	// diferencejouractuel = new ArrayList<>();
	// }
	// taskgen = Bukkit.getScheduler().runTaskTimer(Main.instance, new
	// Runnable() {
	// ArrayList<Location> loca = new ArrayList<>();
	// int num = -1;
	// boolean pregen = false;
	//
	// @Override
	// public void run() {
	// long timer = System.currentTimeMillis();
	// if (day == 0) {
	// while (System.currentTimeMillis() - 50 <= timer && !genfinish) {
	// num++;
	// if (!pregen) {
	// setAvencment(1);
	// if (num >= Cases.size()) {
	// num = -1;
	// pregen = true;
	// } else {
	// Case ca = Cases.get(num);
	// Location loctemp = ca.getLoc();
	// new Generator(loctemp, Empty, false);
	// }
	// } else {
	// setAvencment(2);
	// if (num >= Cases.size()) {
	// genfinish = true;
	// Debug.send_debug("Generation done en: "
	// + ((double) (System.currentTimeMillis() - time) / 1000) + "s");
	// taskgen.cancel();
	// } else {
	// Case ca = Cases.get(num);
	// Location loctemp = ca.getLoc();
	// new Generator(loctemp, ground, true);
	// if (!ca.isOff()) {
	// Location Locpil1 = new Location(loctemp.getWorld(),
	// loctemp.getX() + sizecase, loctemp.getY(),
	// loctemp.getZ() + sizecase);
	// Location Locpil2 = new Location(loctemp.getWorld(),
	// loctemp.getX() - sizecase, loctemp.getY(),
	// loctemp.getZ() + sizecase);
	// Location Locpil3 = new Location(loctemp.getWorld(),
	// loctemp.getX() + sizecase, loctemp.getY(),
	// loctemp.getZ() - sizecase);
	// Location Locpil4 = new Location(loctemp.getWorld(),
	// loctemp.getX() - sizecase, loctemp.getY(),
	// loctemp.getZ() - sizecase);
	// if (!loca.contains(Locpil1)) {
	// new Generator(Locpil1, pilar, true);
	// loca.add(Locpil1);
	// }
	// if (!loca.contains(Locpil2)) {
	// new Generator(Locpil2, pilar, true);
	// loca.add(Locpil2);
	// }
	// if (!loca.contains(Locpil3)) {
	// new Generator(Locpil3, pilar, true);
	// loca.add(Locpil3);
	// }
	// if (!loca.contains(Locpil4)) {
	// new Generator(Locpil4, pilar, true);
	// loca.add(Locpil4);
	// }
	// for (Mur m : ca.getMur()) {
	// if (m.isLock()) {
	// Location locm = m.getLoc();
	// if (!loca.contains(locm)) {
	// if (m.getOrientation() == Orientation.Horizontale) {
	// new Generator(locm, wallhohight, true);
	// } else if (m.getOrientation() == Orientation.Verticale) {
	// new Generator(locm, wallvehight, true);
	// }
	// loca.add(locm);
	// }
	// }
	// }
	// }
	// }
	// }
	// }
	// } else {
	// while (System.currentTimeMillis() - 50 <= timer && !genfinish) {
	// num++;
	// setAvencment(3);
	// if (num >= diferencejouractuel.size()) {
	// genfinish = true;
	// Debug.send_debug("Generation done en: "
	// + ((double) (System.currentTimeMillis() - time) / 1000) + "s");
	// taskgen.cancel();
	// } else {
	// Entrer<Mur, Boolean> e = diferencejouractuel.get(num);
	// Mur m = e.getKey();
	// boolean b = e.getValue();
	// Location locm = m.getLoc();
	// m.setOuvert(b);
	// if (b) {
	// if (m.getOrientation() == Orientation.Horizontale) {
	// new Generator(locm, wallho, false);
	// } else if (m.getOrientation() == Orientation.Verticale) {
	// new Generator(locm, wallve, false);
	// }
	// } else {
	// if (m.getOrientation() == Orientation.Horizontale) {
	// new Generator(locm, wallho, true);
	// } else if (m.getOrientation() == Orientation.Verticale) {
	// new Generator(locm, wallve, true);
	// }
	// }
	// }
	// }
	// }
	// }
	// }, 0, 1);
	// } else {
	// Debug.send_inportantdebug("Vous ne pouvais pas generer plus de template
	// que vous en avez calculer");
	// }
	// } else {
	// Debug.send_inportantdebug("Generation c'ant beging if calcules are not
	// dones !!!!");
	// }
	// }

	public void preGeneration(Game g) {
		if (isCalcul()) {
			
			if (locationcentre == true) {
				int xcenter = longueur / 2;
				int zcenter = largeur / 2;
				Case centre = null;
				for (Case ca : Cases) {
					if (ca.getX() == xcenter && ca.getZ() == zcenter) {
						centre = ca;
						break;
					}
				}

				int x = loc.getBlockX() - (centre.getX() * epaisseurmur) - (centre.getX() * largeurCouloir)
						- (largeurCouloir / 2);
				int z = loc.getBlockZ() - (centre.getZ() * epaisseurmur) - (centre.getZ() * largeurCouloir)
						- (largeurCouloir / 2);

				loc = new Location(loc.getWorld(), x, loc.getBlockY(), z);
			}
			
			taskpregeneration = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
				Integer current_case = 0;
				Boolean finish = false;
				@Override
				public void run() {
					// setBox Cases
					Long timer = System.currentTimeMillis();
					
					while (System.currentTimeMillis() - 50 < timer) {
						current_case++;
						if (Cases.size() > (current_case-1)) {
							Case cases = Cases.get(current_case-1);
							int x = loc.getBlockX() + (cases.getX() * epaisseurmur) + (cases.getX() * lab.largeurCouloir);
							int dx = x + (lab.largeurCouloir - 1);

							int z = loc.getBlockZ() + (cases.getZ() * epaisseurmur) + (cases.getZ() * lab.largeurCouloir);
							int dz = z + (lab.largeurCouloir - 1);

							int dy = (loc.getBlockY() + lab.hauteurmur);
							int y = loc.getBlockY();
							World w = loc.getWorld();

//							 ArmorStand armor = (ArmorStand) w.spawnEntity(new
//							 Location(w,(x+dx)/2,y,(z+dz)/2),EntityType.ARMOR_STAND);
//							 armor.setCustomNameVisible(true);
//							 armor.setGravity(false);
//							 armor.setCustomName("x : "+cases.getX()+" z :"+cases.getZ());

							cases.setBox(new Box(x, y, z, dx, dy, dz, w));
							cases.getBox().delete();
							// setBox sol
							if (!cases.isOff()) {
								new Box(x, y - 1, z, dx, y - 1, dz, w).generateSol();
							} else {
								new Box(x, y - 1, z, dx, y - 1, dz, w).generate(Material.GRASS);
							}

							// setBox Mur
							if (cases.getMurEst().getBox() == null) {
								cases.getMurEst().setBox(new Box(dx + 1, y, z, dx + epaisseurmur, dy, dz, w));
								if(cases.getMurEst().isDoor()){
									cases.getMurEst().setPorte(new Porte(new Location(w, dx+1, y, z), new Location(w, dx+epaisseurmur, y, dz), lab.TimeSecPorte,lab.hauteurmur, true, Material.SMOOTH_BRICK, 3, true, false));
								}
								cases.getMurEst().getBox().delete();
								// setBox sol
								if (cases.getMurEst().getOtherCase(cases) != null) {
									if (cases.getMurEst().getOtherCase(cases).isOff()) {
										new Box(dx + 1, y - 1, z, dx + epaisseurmur, y - 1, dz, w).generate(Material.GRASS);
									} else {
										new Box(dx + 1, y - 1, z, dx + epaisseurmur, y - 1, dz, w).generate();
									}
								} else {
									new Box(dx + 1, y - 1, z, dx + epaisseurmur, y - 1, dz, w).generate();
								}
							}
							if (cases.getMurOuest().getBox() == null) {
								cases.getMurOuest().setBox(new Box(x - epaisseurmur, y, z, x - 1, dy, dz, w));
								if(cases.getMurOuest().isDoor()){
									cases.getMurOuest().setPorte(new Porte(new Location(w, x - epaisseurmur, y, z), new Location(w, x - 1, y, dz), lab.TimeSecPorte,lab.hauteurmur, true, Material.SMOOTH_BRICK, 3, true, false));
								}
								cases.getMurOuest().getBox().delete();
								// setBox sol
								if (cases.getMurOuest().getOtherCase(cases) != null) {
									if (cases.getMurOuest().getOtherCase(cases).isOff()) {
										new Box(x - epaisseurmur, y - 1, z, x - 1, y - 1, dz, w).generate(Material.GRASS);
									} else {
										new Box(x - epaisseurmur, y - 1, z, x - 1, y - 1, dz, w).generate();
									}
								} else {
									new Box(x - epaisseurmur, y - 1, z, x - 1, y - 1, dz, w).generate();
								}
							}
							if (cases.getMurNord().getBox() == null) {
								cases.getMurNord().setBox(new Box(x, y, z - epaisseurmur, dx, dy, z - 1, w));
								if(cases.getMurNord().isDoor()){
									cases.getMurNord().setPorte(new Porte(new Location(w, x , y, z - epaisseurmur), new Location(w, dx , y, z - 1), lab.TimeSecPorte,lab.hauteurmur, true, Material.SMOOTH_BRICK, 3, true, true));
								}
								cases.getMurNord().getBox().delete();
								// setBox sol
								if (cases.getMurNord().getOtherCase(cases) != null) {
									if (cases.getMurNord().getOtherCase(cases).isOff()) {
										new Box(x, y - 1, z - epaisseurmur, dx, y - 1, z - 1, w).generate(Material.GRASS);
									} else {
										new Box(x, y - 1, z - epaisseurmur, dx, y - 1, z - 1, w).generate();
									}
								} else {
									new Box(x, y - 1, z - epaisseurmur, dx, y - 1, z - 1, w).generate();
								}
							}
							if (cases.getMurSud().getBox() == null) {
								cases.getMurSud().setBox(new Box(x, y, dz + 1, dx, dy, dz + epaisseurmur, w));
								if(cases.getMurSud().isDoor()){
									cases.getMurSud().setPorte(new Porte(new Location(w, x , y, dz + 1), new Location(w, dx , y, dz + epaisseurmur), lab.TimeSecPorte,lab.hauteurmur, true, Material.SMOOTH_BRICK, 3, true, true));
								}
								cases.getMurSud().getBox().delete();
								// setBox sol
								if (cases.getMurSud().getOtherCase(cases) != null) {
									if (cases.getMurSud().getOtherCase(cases).isOff()) {
										new Box(x, y - 1, dz + 1, dx, y - 1, dz + epaisseurmur, w).generate(Material.GRASS);
									} else {
										new Box(x, y - 1, dz + 1, dx, y - 1, dz + epaisseurmur, w).generate();
									}
								} else {
									new Box(x, y - 1, dz + 1, dx, y - 1, dz + epaisseurmur, w).generate();
								}
							}

							if (!cases.isOff()) {
								// setBox Pillier
								Box pilier1 = new Box(x - epaisseurmur, y - 1, z - epaisseurmur, x - 1, dy, z - 1, w);
								Box pilier2 = new Box(dx + 1, y - 1, z - epaisseurmur, dx + epaisseurmur, dy, z - 1, w);
								Box pilier3 = new Box(dx + 1, y - 1, dz + 1, dx + epaisseurmur, dy, dz + epaisseurmur, w);
								Box pilier4 = new Box(x - epaisseurmur, y - 1, dz + 1, x - 1, dy, dz + epaisseurmur, w);

								pilier1.generate();
								pilier2.generate();
								pilier3.generate();
								pilier4.generate();
							} else {
								// setBox Pillier
								Box Dessouspilier1 = new Box(x - epaisseurmur, y - 1, z - epaisseurmur, x - 1, y - 1, z - 1,
										w);
								Box Dessouspilier2 = new Box(dx + 1, y - 1, z - epaisseurmur, dx + epaisseurmur, y - 1,
										z - 1, w);
								Box Dessouspilier3 = new Box(dx + 1, y - 1, dz + 1, dx + epaisseurmur, y - 1,
										dz + epaisseurmur, w);
								Box Dessouspilier4 = new Box(x - epaisseurmur, y - 1, dz + 1, x - 1, y - 1,
										dz + epaisseurmur, w);

								Dessouspilier1.generate(Material.GRASS);
								Dessouspilier2.generate(Material.GRASS);
								Dessouspilier3.generate(Material.GRASS);
								Dessouspilier4.generate(Material.GRASS);

								Box pilier1 = new Box(x - epaisseurmur, y, z - epaisseurmur, x - 1, dy, z - 1, w);
								Box pilier2 = new Box(dx + 1, y, z - epaisseurmur, dx + epaisseurmur, dy, z - 1, w);
								Box pilier3 = new Box(dx + 1, y, dz + 1, dx + epaisseurmur, dy, dz + epaisseurmur, w);
								Box pilier4 = new Box(x - epaisseurmur, y, dz + 1, x - 1, dy, dz + epaisseurmur, w);

								pilier1.delete(Material.SMOOTH_BRICK);
								pilier2.delete(Material.SMOOTH_BRICK);
								pilier3.delete(Material.SMOOTH_BRICK);
								pilier4.delete(Material.SMOOTH_BRICK);
							}

							for (Mur mur : cases.getMur()) {
								if (mur != null) {
									if (!mur.isGenerate()) {
										if (mur.isLock()) {
											if (mur.getBox() != null) {
												if (mur.isOuvert()) {
													mur.getBox().delete();
													mur.setGenerate(true);
												} else {
													mur.getBox().generate();
													mur.setGenerate(true);
												}
											}
										}
									}
								}
							}
						}else{
							finish = true;
							break;
						}
					}
					
					if (finish) {
						lab.setAscenseur(lab.locCentre);
						setPreGeneration(true);
						Bukkit.getConsoleSender().sendMessage("§cPreGeneration fini");
						g.setGenerate(false);
						g.getMenuhost().update();
						taskpregeneration.cancel();
					}
				}
			}, 0, 1);
		}
	}

	public void delete() {
		taskdelete = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				Case cases = null;
				ArrayList<Case> cas = new ArrayList<>(Cases);
				Cases.clear();
				diferencejourmur.clear();
				for (Case ca : cas) {
					if (ca.getX() == (longueur - 1) && ca.getZ() == (largeur - 1)) {
						cases = ca;
					}
				}
				if (cases != null) {
					int x = loc.getBlockX() + (cases.getX() * epaisseurmur) + (cases.getX() * lab.largeurCouloir);
					int dx = x + (lab.largeurCouloir - 1);

					int z = loc.getBlockZ() + (cases.getZ() * epaisseurmur) + (cases.getZ() * lab.largeurCouloir);
					int dz = z + (lab.largeurCouloir - 1);

					int dy = (loc.getBlockY() + lab.hauteurmur);

					Box labyrinthe = new Box(loc.getBlockX() - epaisseurmur, loc.getBlockY() - 1,
							loc.getBlockZ() - epaisseurmur, dx + epaisseurmur, dy, dz + epaisseurmur, loc.getWorld());
					labyrinthe.delete();
				}
				setCalcul(false);
				setPreGeneration(false);
				day = -1;
				taskdelete.cancel();
			}
		}, 0, 1);
	}

	private Case getothercase(Case c, Direction direction) {
		int x = c.getX();
		int z = c.getZ();

		if (direction == Direction.Nord) {
			z = z - 1;
		} else if (direction == Direction.Sud) {
			z = z + 1;
		} else if (direction == Direction.Ouest) {
			x = x - 1;
		} else if (direction == Direction.Est) {
			x = x + 1;
		} else {
			return null;
		}
		for (Case ca : Cases) {
			if (ca.getX() == x && ca.getZ() == z) {
				return ca;
			}
		}
		return null;
	}

	private ArrayList<Mur> calculdestroyablewall() {
		ArrayList<Mur> MurEnlevable = new ArrayList<>();
		for (Case ca : Cases) {
			// toutes les cases
			for (Mur m : ca.getMur()) {
				// tous les murs de toutes les cases
				if (!m.isLock() && !m.isOuvert()) {
					// si le mur n'est pas lock et est fermé
					Case c1 = m.getCase1();
					Case c2 = m.getCase2();
					if (c1 != null && c2 != null) {
						// si le mur possède bien 2 cases
						if (c1.getNumero() != c2.getNumero()) {
							// si les murs sont fermé mais ont deja le meme
							// numero
							// (theoriquement oblige)
							if (!MurEnlevable.contains(m)) {
								// si le mur n'est pas deja enregistré
								MurEnlevable.add(m);
							}
						}
					}
				}
			}
		}
		return MurEnlevable;
	}

	public int getLongueur() {
		return longueur;
	}

	public void setLongueur(int longeur) {
		longueur = longeur;
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int Largeur) {
		largeur = Largeur;
	}

	public int getLargeurCouloir() {
		return largeurCouloir;
	}

	public void setLargeurCouloir(int LargeurCouloir) {
		largeurCouloir = LargeurCouloir;
	}

	public int getHauteurMur() {
		return hauteurmur;
	}

	public void setHauteurMur(int hauteurMur) {
		hauteurmur = hauteurMur;
	}

	public int getEpaisseurMur() {
		return epaisseurmur;
	}

	public void setEpaisseurMur(int epaisseurMur) {
		epaisseurmur = epaisseurMur;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public boolean isCalcul() {
		return calcul;
	}

	public void setCalcul(boolean calcul) {
		this.calcul = calcul;
	}

	public boolean isPreGenere() {
		return pregeneration;
	}

	public void setPreGeneration(boolean preGeneration) {
		this.pregeneration = preGeneration;
	}

	public int getRayon() {
		return rayon;
	}

	public void setRayon(int rayon) {
		this.rayon = rayon;
	}

	public boolean isCercle() {
		return cercle;
	}

	public void setCercle(boolean cercle) {
		this.cercle = cercle;
	}

	public Location getLocCentre() {
		return locCentre;
	}

	public void setLocCentre(Location locCentre) {
		this.locCentre = locCentre;
	}
}