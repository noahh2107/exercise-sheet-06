package de.unistuttgart.iste.sqa.pse.sheet06.presence.stairs;

import de.hamstersimulator.objectsfirst.external.simple.game.SimpleHamsterGame;
import de.hamstersimulator.objectsfirst.datatypes.Location;
import de.hamstersimulator.objectsfirst.datatypes.Size;
import de.unistuttgart.iste.sqa.pse.sheet06.presence.stairs.exceptions.UnsurmountableStepException;
import de.unistuttgart.iste.sqa.pse.sheet06.presence.stairs.exceptions.ClimbingAbortedException;

/**
 * Describe the purpose of this class here.
 *
 * @author Sven Olderdissen, Noah Hofecker
 * @version 1.0
 */
public class StairsHamsterGame extends SimpleHamsterGame {

	/**
	 * Creates a new {@link StairsHamsterGame}.<br>
	 */
	public StairsHamsterGame() {
		// replace "/territories/StairsTerritory.ter" in the following line of code with
		// "/territories/TooHighStairsTerritory.ter" or
		// "/territories/TooWideStairsTerritory.ter" to load other territories.
		this.loadTerritoryFromResourceFile("/territories/TooWideStairsTerritory.ter");
		this.displayInNewGameWindow();
		game.startGame();
	}

	/**
	 * Ignore this method.<br>
	 * Put your code in {@link StairsHamsterGame#climbStairs}!
	 */
	@Override
	protected void run() {
		climbStairs();
	}

	/**
	 * Lets paule climb up a single step
	 * 
	 * Requires that the step has a height of 1
	 * Ensures that paule is on the next step iff the step has a height of 1
	 * 
	 * @throws UnsurmountableStepException when the step has a height of more than 1
	 */
	private void movePauleToNextStep() throws UnsurmountableStepException {

		paule.turnLeft();
		paule.move();
		turnPauleRight();
		if (paule.frontIsClear()) {
			paule.move();
		} else {
			throw new UnsurmountableStepException(getRequestedUIMode());
		}
	}

	/**
	 * Checks whether paule is on the top of the staircase
	 * 
	 * @return boolean true iff paule is on the top of the staircase, i.e. when there is not another stair in front of paule
	 */
	private boolean hasReachedTop() {
		// return paule.frontIsClear();
		Location paulesLocation = paule.getLocation();
		int territoryMaxColumn = game.getTerritory().getTerritorySize().getColumnCount() - 1;
		System.out.println(territoryMaxColumn);
		System.out.println(paulesLocation);

		boolean hasPauleReachedRight = paulesLocation.getColumn() == territoryMaxColumn;
		boolean hasPauleReachedTop = paulesLocation.getRow() == 0;
		return (hasPauleReachedRight || hasPauleReachedTop);
	}

	/**
	 * Attempts to make paule climb all of the staircase's steps directly in front of him
	 * 
	 * Ensures that paule has reached the top of the staircase iff there is no step with a height of more than 1
	 *
	 * @throws ClimbingAbortedException iff the staircase has a step with a height of more than 1
	 */
	private void climbStairs() throws ClimbingAbortedException {
		while (!hasReachedTop()) {
			pauleMoveToNextWall();
			try {
				movePauleToNextStep();
			} catch (UnsurmountableStepException e) {
				throw new ClimbingAbortedException(getRequestedUIMode());
			}
		}	
		paule.write(":3");
	}
	/**
	 * Moves Paule forwards to the next wall
	 *
	 * Ensures that Paule is standing in front of a wall.
	 */
	private void pauleMoveToNextWall() {
		while (paule.frontIsClear()){
			paule.move();
		}
	}
	
	/**
	 * Turns Paule 90 degree to his right.
	 */
	private void turnPauleRight() {
		paule.turnLeft();
		paule.turnLeft();
		paule.turnLeft();
	}
}
