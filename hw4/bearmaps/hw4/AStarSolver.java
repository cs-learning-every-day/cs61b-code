package bearmaps.hw4;

import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.introcs.Stopwatch;

import java.util.*;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
	private int numStatesExplored;
	private double explorationTime;
	private double solutionWeight;
	private SolverOutcome outcome;
	private List<Vertex> solution = new LinkedList<>();

	private Map<Vertex, Double> distToMap = new HashMap<>();
	private Map<Vertex, Double> heuristicMap = new HashMap<>();
	private Map<Vertex, Vertex> edgeToMap = new HashMap<>();
	// 我的ArrayHeapMinPQ有BUG 在proj2ab中 测试写少了，没测出来
	private DoubleMapPQ<Vertex> fringe = new DoubleMapPQ<>();

	public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
		double h = input.estimatedDistanceToGoal(start, end);
		fringe.add(start, 0.0 + h);
		distToMap.put(start, 0.0);
		heuristicMap.put(start, h);
		edgeToMap.put(start, null);


		Vertex smallestV;
		List<WeightedEdge<Vertex>> neighbors;
		Stopwatch stopwatch = new Stopwatch();
		while (fringe.size() > 0) {
			smallestV = fringe.getSmallest();

			if (smallestV.equals(end)) {
				solutionWeight = distToMap.get(smallestV);
				outcome = SolverOutcome.SOLVED;
				solverSolution(end);
				explorationTime = stopwatch.elapsedTime();
				return;
			}

			fringe.removeSmallest();
			numStatesExplored++;

			neighbors = input.neighbors(smallestV);
			for (WeightedEdge<Vertex> neighbor : neighbors) {
				relax(neighbor, input, end);
			}

			if (stopwatch.elapsedTime() > timeout) {
				outcome = SolverOutcome.TIMEOUT;
				solution.clear();
				explorationTime = stopwatch.elapsedTime();
				return;
			}
		}
		outcome = SolverOutcome.UNSOLVABLE;
		solution.clear();
		explorationTime = stopwatch.elapsedTime();
	}

	private void solverSolution(Vertex goal) {
		solution.add(goal);
		Vertex p = edgeToMap.get(goal);
		while (p != null) {
			solution.add(p);
			p = edgeToMap.get(p);
		}
		Collections.reverse(solution);
	}

	private void relax(WeightedEdge<Vertex> edge, AStarGraph<Vertex> input, Vertex end) {
		Vertex p = edge.from();
		Vertex q = edge.to();
		double weight = edge.weight();

		if (!distToMap.containsKey(q)) {
			distToMap.put(q, Double.POSITIVE_INFINITY);
		}

		if (!heuristicMap.containsKey(q)) {
			heuristicMap.put(q, input.estimatedDistanceToGoal(q, end));
		}


		double curD = distToMap.get(p) + weight;
		if (curD < distToMap.get(q)) {
			edgeToMap.put(q, p);
			distToMap.replace(q, curD);

			double d = distToMap.get(q) + heuristicMap.get(q);
			if (fringe.contains(q)) {
				fringe.changePriority(q, d);
			} else {
				fringe.add(q, d);
			}
		}
	}

	@Override
	public SolverOutcome outcome() {
		return outcome;
	}

	@Override
	public List<Vertex> solution() {
		return solution;
	}

	@Override
	public double solutionWeight() {
		return solutionWeight;
	}

	@Override
	public int numStatesExplored() {
		return numStatesExplored;
	}

	@Override
	public double explorationTime() {
		return explorationTime;
	}
}
