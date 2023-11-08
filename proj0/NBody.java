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

    public static double forceComponentX(Planet myPln, Planet[] plList) {
        double forceX = 0;
        for (Planet p : plList) {
            if (!myPln.equals(p)) {
                forceX += myPln.calcForceExertedByX(p);
            }
        }
        return forceX;
    }

    public static double forceComponentY(Planet myPln, Planet[] plList) {
        double forceY = 0;
        for (Planet p : plList) {
            if (!myPln.equals(p)) {
                forceY += myPln.calcForceExertedByY(p);
            }
        }
        return forceY;
    }

    public static void main(String[] args) {
        // Read in the configuration
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] Planets = NBody.readPlanets(filename);
        double uniRadius = NBody.readRadius(filename);

        // Scale the canvas : universe
        StdDraw.setXscale(-uniRadius, uniRadius);
        StdDraw.setYscale(-uniRadius, uniRadius);
        StdDraw.enableDoubleBuffering();

        // Simulation
        double t = 0;

        while (t <= T) {
            // update time
            t += dt;

            // update by iteration
            // everyone have interaction with all others : You need to override methods in Planet
            for (Planet p : Planets) {
                p.update(dt, NBody.forceComponentX(p,Planets), NBody.forceComponentY(p,Planets));
            }


            // draw everything at once
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet p : Planets) {
                StdDraw.picture(p.xxPos, p.yyPos, "images/"+p.imgFileName);
            }
            StdDraw.show();

            // sleep
            StdDraw.pause(5);
        }

        StdOut.printf("%d\n", Planets.length);
        StdOut.printf("%.2e\n", Planets);
        for (int i = 0; i < Planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    Planets[i].xxPos, Planets[i].yyPos, Planets[i].xxVel,
                    Planets[i].yyVel, Planets[i].mass, Planets[i].imgFileName);
        }
    }
}
