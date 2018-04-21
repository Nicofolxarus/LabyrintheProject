package fr.NS.Labyrinthe.Timer;

/*
 * This plugin was create by Seraleme and Nicofolxarus
 * 
 * -You are not allowed to :
 * 		-Copy the code
 * 		-Write your name on this plugin
 * 	
 * To contact ous please send private message on twitter
 * @Seraleme
 * @Nicofolxarus_
 * 
 */
public enum State {
	Attente,Generation,Game,Fin;

	private static State currentState;

	public void setState(State state) {
		currentState = state;
	}

	public boolean isState(State state) {
		return currentState == state;
	}

	public State getState() {
		return currentState;
	}
}
