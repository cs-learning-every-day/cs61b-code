import es.datastructur.synthesizer.GuitarString;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class GuitarHero {
	public static void main(String[] args) {
		final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
		GuitarString[] guitarStrings = new GuitarString[37];
		while (true) {
			/* check if the user has typed a key; if so, process it */
			if (StdDraw.hasNextKeyTyped()) {
				char key = StdDraw.nextKeyTyped();
				if (keyboard.indexOf(key) >= 0) {
					int idx = keyboard.indexOf(key);
					guitarStrings[idx] = new GuitarString(440.0 * Math.pow(2, (idx - 24) / 12.0));
					guitarStrings[idx].pluck();
				}
			}

			/* compute the superposition of samples */
			double sample=0.0;
			for (int i = 0; i < guitarStrings.length; i++) {
				if (guitarStrings[i] != null) {
					sample += guitarStrings[i].sample();
				}
			}

			/* play the sample on standard audio */
			StdAudio.play(sample);

			/* advance the simulation of each guitar string by one step */
			for (int i = 0; i < guitarStrings.length; i++) {
				if (guitarStrings[i] != null) {
					guitarStrings[i].tic();
				}
			}
		}
	}
}
