public class NBody {
  public static double readRadius(String file) {
    In in = new In(file);
    int num_of_planets = in.readInt();
    double radius = in.readDouble();
    return radius;
  }

  public static Planet[] readPlanets(String file) {
    In in = new In(file);
    int num_of_planets = in.readInt();
    double radius = in.readDouble();
    Planet[] planets = new Planet[num_of_planets];
    int i = 0;
    while(i < num_of_planets) {
      double xpos = in.readDouble();
      double ypos = in.readDouble();
      double xvel = in.readDouble();
      double yvel = in.readDouble();
      double mass = in.readDouble();
      String name = in.readString();
      planets[i] = new Planet(xpos, ypos, xvel, yvel, mass, name);
      i = i + 1;
    }
    return planets;
  }

  public static void main(String[] args) {
    double T = Double.parseDouble(args[0]);
    double dt = Double.parseDouble(args[1]);
    String filename = args[2];
    double radius = readRadius(filename);
    Planet[] planets = readPlanets(filename);

    StdDraw.enableDoubleBuffering();
    StdDraw.setScale(-radius, radius);
    StdDraw.clear();
    StdDraw.picture(0, 0, "images/starfield.jpg");
    for(Planet p : readPlanets(filename)) {
      p.draw();
    }
    StdDraw.show();

    for(double time = 0; time < T; time = time + dt) {
      double[] xForces = new double[planets.length];
      double[] yForces = new double[planets.length];
      for(int i = 0; i < planets.length; i++) {
        xForces[i] = planets[i].calcNetForceExertedByX(planets);
        yForces[i] = planets[i].calcNetForceExertedByY(planets);
      }
      for(int j = 0; j < planets.length; j++) {
        planets[j].update(dt, xForces[j], yForces[j]);
      }
      StdDraw.picture(0, 0, "images/starfield.jpg");
      for(Planet p : planets) {
        p.draw();
      }
      StdDraw.show();
      StdDraw.pause(10);
    }

    StdOut.printf("%d\n", planets.length);
    StdOut.printf("%.2e\n", radius);
    for(int i = 0; i < planets.length; i++) {
      StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
    }
  }
}
