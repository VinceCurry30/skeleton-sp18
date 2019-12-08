import java.lang.Math;

public class Planet {
  public double xxPos;
  public double yyPos;
  public double xxVel;
  public double yyVel;
  public double mass;
  public String imgFileName;
  public static final double G = 6.67e-11;

  public Planet(double xP, double yP, double xV, double yV, double m, String img) {
    xxPos = xP;
    yyPos = yP;
    xxVel = xV;
    yyVel = yV;
    mass = m;
    imgFileName = img;
  }

  public Planet(Planet p) {
    xxPos = p.xxPos;
    yyPos = p.yyPos;
    xxVel = p.xxVel;
    yyVel = p.yyVel;
    mass = p.mass;
    imgFileName = p.imgFileName;
  }

  public double calcDistance(Planet p) {
    return Math.sqrt((xxPos - p.xxPos) * (xxPos - p.xxPos) + (yyPos - p.yyPos) * (yyPos - p.yyPos));
  }

  public double calcForceExertedBy(Planet p) {
    return G * mass * p.mass / calcDistance(p) / calcDistance(p);
  }

  public double calcForceExertedByX(Planet p) {
    return calcForceExertedBy(p) * (p.xxPos - xxPos) / calcDistance(p);
  }

  public double calcForceExertedByY(Planet p) {
    return calcForceExertedBy(p) * (p.yyPos - yyPos) / calcDistance(p);
  }

  public double calcNetForceExertedByX(Planet[] allp) {
    double sumX = 0;
    for(Planet p : allp) {
      if(!p.equals(this)) {
        sumX = sumX + calcForceExertedByX(p);
      }
    }
    return sumX;
  }

  public double calcNetForceExertedByY(Planet[] allp) {
    double sumY = 0;
    for(Planet p : allp) {
      if(!p.equals(this)) {
        sumY = sumY + calcForceExertedByY(p);
      }
    }
    return sumY;
  }

  public void update(double time, double xforce, double yforce) {
    double accelerationX, accelerationY;
    accelerationX = xforce / mass;
    accelerationY = yforce / mass;
    xxVel = xxVel + accelerationX * time;
    yyVel = yyVel + accelerationY * time;
    xxPos = xxPos + xxVel * time;
    yyPos = yyPos + yyVel * time;
  }

  public void draw() {
    String imageToDraw = "images/" + imgFileName;
    StdDraw.picture(xxPos, yyPos, imageToDraw);
  }
}
