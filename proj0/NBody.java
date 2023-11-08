public class NBody {


    public static double readRadius(String file) {
        /*
        Read radius of the universe
         */
        In in = new In(file);

        int first = in.readInt();

        return in.readDouble();
    }

    public static Planet[] readPlanets(String file) {
        In in = new In(file);
        int arrayNum = in.readInt();
        double second_null = in.readDouble();
        Planet[] plList = new Planet[arrayNum];
        for (int i = 0; i < arrayNum; i++) {
            double xp = in.readDouble();
            double yp = in.readDouble();
            double vx = in.readDouble();
            double vy = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            plList[i] = new Planet(xp, yp, vx, vy, m, img);
        }

        return plList;
    }



    public static void main(String[] args) {
        // Read in the configuration
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] planets = NBody.readPlanets(filename);
        double uniRadius = NBody.readRadius(filename);

        // Scale the canvas : universe
        StdDraw.setXscale(-uniRadius, uniRadius);
        StdDraw.setYscale(-uniRadius, uniRadius);
        StdDraw.enableDoubleBuffering();

        // Simulation
        double t = 0;
        int num = planets.length;
        while (t <= T) {
            // update time
            double[] xForces = new double[num];
            double[] yForces = new double[num];
            for(int i = 0; i < num; i++){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for(int i = 0; i < num; i++){
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            // draw the background picture
            StdDraw.picture(0, 0, "images/starfield.jpg");

            // draw all the planets
            for (Planet planet : planets) {
                planet.draw();
            }

            StdDraw.show();
            StdDraw.pause(10);
            t += dt;
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", uniRadius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
