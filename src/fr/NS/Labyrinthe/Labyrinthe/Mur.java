package fr.NS.Labyrinthe.Labyrinthe;

import fr.NS.Labyrinthe.Labyrinthe.Labyrinthe.Orientation;
import fr.NS.Labyrinthe.Porte.Porte;

public class Mur {
	
	private boolean ouvert = true;
	private boolean lock = false;
	private boolean generate = false;
	
	private boolean isDoor = false;
	private Porte porte;
	
	private Orientation orientation;
	
	private Box box;
	
	private Case case1;
	private Case case2;

	public Mur(boolean ouvert, Orientation orientation) {
		this.setOuvert(ouvert);
		this.orientation = orientation;
	}

	public boolean isOuvert() {
		return ouvert;
	}

	public void setOuvert(boolean ouvert) {
		this.ouvert = ouvert;
	}

	public Case getCase1() {
		return case1;
	}

	public void setCase1(Case case1) {
		this.case1 = case1;
	}

	public Case getCase2() {
		return case2;
	}

	public void setCase2(Case case2) {
		this.case2 = case2;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}
	
	public Case getOtherCase(Case cas) {
		if (case1 != cas && case2 == cas) {
			return case1;
		} else if (case1 == cas && case2 != cas) {
			return case2;
		}
		return null;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public boolean isGenerate() {
		return generate;
	}

	public void setGenerate(boolean generate) {
		this.generate = generate;
	}

	public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}

	public boolean isDoor() {
		return isDoor;
	}

	public void setDoor(boolean isDoor) {
		this.isDoor = isDoor;
	}

	public Porte getPorte() {
		return porte;
	}

	public void setPorte(Porte porte) {
		this.porte = porte;
	}
}
