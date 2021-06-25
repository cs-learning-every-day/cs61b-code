package creatures;

import huglife.*;

import java.awt.*;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class Clorus extends Creature {

	public Clorus(double energy) {
		super("clorus");
		this.energy = energy;
	}

	public Clorus() {
		this(-1);
	}

	@Override
	public void move() {
		energy -= 0.03;
	}

	@Override
	public void attack(Creature c) {
		energy += c.energy();
	}

	@Override
	public Clorus replicate() {
		energy /= 2;
		return new Clorus(energy);
	}

	@Override
	public void stay() {
		energy -= 0.01;
	}

	@Override
	public Action chooseAction(Map<Direction, Occupant> neighbors) {
		Deque<Direction> emptyNeighbors = new LinkedList<>();
		Deque<Direction> neighborPlips = new LinkedList<>();
		;
		for (Map.Entry<Direction, Occupant> pair : neighbors.entrySet()) {
			if (pair.getValue().name().equals("plip")) {
				neighborPlips.push(pair.getKey());
			}
			if (pair.getValue().name().equals("empty")) {
				emptyNeighbors.push(pair.getKey());
			}
		}

		if (emptyNeighbors.isEmpty()) {
			return new Action(Action.ActionType.STAY);
		}

		if (!neighborPlips.isEmpty()) {
			return new Action(Action.ActionType.ATTACK, HugLifeUtils.randomEntry(neighborPlips));
		}

		if (energy >= 1) {
			return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(emptyNeighbors));
		}

		return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(emptyNeighbors));
	}

	@Override
	public Color color() {
		return new Color(34, 0, 231);
	}
}
