package es.datastructur.synthesizer;

import java.util.HashSet;
import java.util.Set;

//Note: This file will not compile until you complete task 1 (BoundedQueue).
public class DrumString {
	/**
	 * Constants. Do not change. In case you're curious, the keyword final
	 * means the values cannot be changed at runtime.
	 */
	private static final int SR = 44100;      // Sampling Rate
	private static final double DECAY = .996; // energy decay factor

	/* Buffer for storing sound data. */
	private BoundedQueue<Double> buffer;

	/* Create a guitar string of the given frequency.  */
	public DrumString(double frequency) {
		long capacity = Math.round(SR / frequency);
		buffer = new ArrayRingBuffer<>((int) capacity);
		for (int i = 0; i < capacity; i++) {
			buffer.enqueue(0.0);
		}
	}


	/* Pluck the guitar string by replacing the buffer with white noise. */
	public void pluck() {
		int len = buffer.capacity();
		double r;
		Set<Double> randomSet = new HashSet<>();
		for (int i = 0; i < len; i++) {
			buffer.dequeue();
			r = Math.random() - 0.5;

			if (!randomSet.contains(r)) {
				r = Math.random() - 0.5;
				randomSet.add(r);
				buffer.enqueue(r);
			}
		}
	}


	/* Advance the simulation one time step by performing one iteration of
	 * the Karplus-Strong algorithm.
	 */
	public void tic() {
		double firstSample = buffer.dequeue();
		double secondSample = buffer.peek();
		double newSample = (firstSample + secondSample) / 2 * DECAY;
		double flag = Math.random();
		if (flag <= 0.5) {
			newSample = -newSample;
		}
		buffer.enqueue(newSample);
	}

	/* Return the double at the front of the buffer. */
	public double sample() {
		return buffer.peek();
	}
}
