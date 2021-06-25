package creatures;

import huglife.*;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class TestClorus {
	private static final double delta = 0.0001;

	@Test
	public void testBasics() {
		Clorus clorus = new Clorus(1);
		assertEquals(1, clorus.energy(), delta);
		assertEquals(new Color(34, 0, 231), clorus.color());
		clorus.move();
		assertEquals(0.97, clorus.energy(), delta);
		clorus.stay();
		assertEquals(0.96, clorus.energy(), delta);
	}

	@Test
	public void testReplicate() {
		Clorus c = new Clorus(2);
		Clorus child = c.replicate();
		assertEquals(1.0, c.energy(), 0.01);
		assertEquals(1.0, child.energy(), 0.01);
		Clorus cchild = child.replicate();
		assertEquals(0.5, child.energy(), 0.01);
		assertEquals(0.5, cchild.energy(), 0.01);
	}

	@Test
	public void testChoose() {

		// No empty adjacent spaces; stay.
		Clorus c = new Clorus(1.2);
		HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
		surrounded.put(Direction.TOP, new Impassible());
		surrounded.put(Direction.BOTTOM, new Impassible());
		surrounded.put(Direction.LEFT, new Impassible());
		surrounded.put(Direction.RIGHT, new Impassible());

		Action actual = c.chooseAction(surrounded);
		Action expected = new Action(Action.ActionType.STAY);

		assertEquals(expected, actual);

		// Surrounded by Plips in four directions. No empty adjacent spaces; stay.
		c = new Clorus(1.2);
		HashMap<Direction, Occupant> surroundedPlip = new HashMap<Direction, Occupant>();
		surroundedPlip.put(Direction.TOP, new Plip(0.5));
		surroundedPlip.put(Direction.BOTTOM, new Plip(2));
		surroundedPlip.put(Direction.LEFT, new Plip(1.3));
		surroundedPlip.put(Direction.RIGHT, new Plip(0.8));

		actual = c.chooseAction(surroundedPlip);
		expected = new Action(Action.ActionType.STAY);

		assertEquals(expected, actual);


		// Plip nearby; should attack Plip.
		c = new Clorus(1.2);
		HashMap<Direction, Occupant> topPlip = new HashMap<Direction, Occupant>();
		topPlip.put(Direction.TOP, new Plip(0.8));
		topPlip.put(Direction.BOTTOM, new Empty());
		topPlip.put(Direction.LEFT, new Empty());
		topPlip.put(Direction.RIGHT, new Empty());

		actual = c.chooseAction(topPlip);
		expected = new Action(Action.ActionType.ATTACK, Direction.TOP);

		assertEquals(expected, actual);


		// Energy >= 1 and no plip nearby; replicate towards an empty space.
		c = new Clorus(1.2);
		HashMap<Direction, Occupant> leftEmpty = new HashMap<Direction, Occupant>();
		leftEmpty.put(Direction.TOP, new Impassible());
		leftEmpty.put(Direction.BOTTOM, new Impassible());
		leftEmpty.put(Direction.LEFT, new Empty());
		leftEmpty.put(Direction.RIGHT, new Impassible());

		actual = c.chooseAction(leftEmpty);
		Action unexpected = new Action(Action.ActionType.REPLICATE, Direction.RIGHT);

		assertNotEquals(unexpected, actual);


		// Energy < 1 and no plip nearby; move to an empty space.
		c = new Clorus(.99);

		actual = c.chooseAction(leftEmpty);
		expected = new Action(Action.ActionType.MOVE, Direction.LEFT);

		assertEquals(expected, actual);

	}
}
