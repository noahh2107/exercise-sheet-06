package de.unistuttgart.iste.sqa.pse.sheet06.homework.exceptions;

public class LazyHamsterGameApp {
	public static void main(String[] args) {
		final LazyHamsterGame game = new LazyHamsterGame("/territories/Territory.ter");
		game.doRun();
	}
}
