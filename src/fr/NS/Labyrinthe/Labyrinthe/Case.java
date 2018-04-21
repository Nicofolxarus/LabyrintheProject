package fr.NS.Labyrinthe.Labyrinthe;

import java.util.ArrayList;

import org.bukkit.Location;

public class Case {

	private boolean off = false;

	private Location loc;
	
	private int Numero;
	private int Id;
	private int x;
	private int z;
	
	private Box box;
	
	private Mur nord;
	private Mur sud;
	private Mur est;
	private Mur ouest;

	public Case(Labyrinthe labi,Location Loc, Integer Numero,int x,int z) {
		this.Numero = Numero;
		this.Id = Numero;
		this.loc = Loc;
		this.x = x;
		this.z = z;
	}
	public Case(Labyrinthe labi, Integer Numero,int x,int z) {
		this.Numero = Numero;
		this.Id = Numero;
		this.x = x;
		this.z = z;
	}

	public Mur getMurSud() {
		return sud;
	}

	public void setMurSud(Mur sud) {
		this.sud = sud;
	}

	public Mur getMurNord() {
		return nord;
	}

	public void setMurNord(Mur nord) {
		this.nord = nord;
	}

	public Mur getMurEst() {
		return est;
	}

	public void setMurEst(Mur est) {
		this.est = est;
	}

	public Mur getMurOuest() {
		return ouest;
	}

	public void setMurOuest(Mur ouest) {
		this.ouest = ouest;
	}

	public boolean isOff() {
		return off;
	}

	public void setOff(boolean off) {
		this.off = off;
	}

	public int getNumero() {
		return Numero;
	}

	public void setNumero(int numero) {
		Numero = numero;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public ArrayList<Mur> getMur() {
		ArrayList<Mur> murs = new ArrayList<>();
		if (nord != null) {
			murs.add(nord);
		}
		if (sud != null) {
			murs.add(sud);
		}
		if (est != null) {
			murs.add(est);
		}
		if (ouest != null) {
			murs.add(ouest);
		}
		return murs;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}
	public Box getBox() {
		return box;
	}
	public void setBox(Box box) {
		this.box = box;
	}
}
