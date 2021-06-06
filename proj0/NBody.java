public class NBody {
    private static final String BG_IMG = "images/starfield.jpg";

    /**
     * 读取文件中的宇宙半径
     * 
     * @param planetsTxtPath
     * @return
     */
    public static Double readRadius(String planetsTxtPath) {
        In in = new In(planetsTxtPath);
        in.readInt();
        return in.readDouble();
    }

    /**
     * 读取文件中的星体数据
     * 
     * @param planetsTxtPath
     * @return
     */
    public static Body[] readBodies(String planetsTxtPath) {
        In in = new In(planetsTxtPath);
        int n = in.readInt();
        Body[] bodies = new Body[n];

        in.readDouble();
        for (int i = 0; i < n; i++) {
            bodies[i] = new Body(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(),
                    in.readString());
        }
        return bodies;
    }

    public static void main(String[] args) {
        double T = 157788000.0;
        double dt = 25000.0;
        String filename = "./data/galaxy.txt";

        if (args.length > 2) {
            T = Double.parseDouble(args[0]);
            dt = Double.parseDouble(args[1]);
            filename = args[2];
        }

        double radius = NBody.readRadius(filename);
        Body[] bodies = NBody.readBodies(filename);
        int n = bodies.length;

        StdDraw.enableDoubleBuffering();

        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, BG_IMG);
        for (int i = 0; i < n; i++) {
            bodies[i].draw();
        }
        StdDraw.show();
        StdAudio.play("audio/2001.mid");

        double[] xForces = new double[n];
        double[] yForces = new double[n];
        for (double t = 0.0; t <= T; t += dt) {
            for (int i = 0; i < n; i++) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
                bodies[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.picture(0, 0, BG_IMG);
            for (int i = 0; i < n; i++) {
                bodies[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", bodies[i].xxPos, bodies[i].yyPos,
                    bodies[i].xxVel, bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }
    }
}
