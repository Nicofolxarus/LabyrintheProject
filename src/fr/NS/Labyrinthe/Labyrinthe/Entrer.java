package fr.NS.Labyrinthe.Labyrinthe;

public class Entrer<var1,var2> {
	
	private var1 key;
	private var2 value;
	
	public Entrer(var1 key, var2 value){
		this.key = key;
		this.value = value;
	}

	public var1 getKey() {
		return key;
	}

	public void setKey(var1 key) {
		this.key = key;
	}

	public var2 getValue() {
		return value;
	}

	public void setValue(var2 value) {
		this.value = value;
	}
	public String string(){
		return "[" + key + "+" + value + "]";
	}
}
