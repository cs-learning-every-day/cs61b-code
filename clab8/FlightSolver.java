import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {
	private int maxPassengerCount;

	public FlightSolver(ArrayList<Flight> flights) {
		PriorityQueue<Flight> startPQ = new PriorityQueue<>(Comparator.comparingInt(Flight::startTime));
		PriorityQueue<Flight> endPQ = new PriorityQueue<>(Comparator.comparingInt(Flight::endTime));
		startPQ.addAll(flights);
		endPQ.addAll(flights);

		int tally = 0;
		while (!startPQ.isEmpty()) {
			if (startPQ.peek().startTime() <= endPQ.peek().endTime()) {
				tally += startPQ.poll().passengers();
				maxPassengerCount = Math.max(tally, maxPassengerCount);
			} else {
				tally -= endPQ.poll().passengers();
			}
		}
	}

	public int solve() {
		return maxPassengerCount;
	}

}
