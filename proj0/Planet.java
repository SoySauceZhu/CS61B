public class Planet {
    double xxPos;
    double yyPos;
    double xxVel;
    double yyVel;
    double mass;
    String imgFileName;

    static final double G = 6.67e-11;

    // Constructor to initialize the variables
    public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String img) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = img;
    }

    public Planet(Planet otherPlanet) {
        this.xxPos = otherPlanet.xxPos;
        this.yyPos = otherPlanet.yyPos;
        this.xxVel = otherPlanet.xxVel;
        this.yyVel = otherPlanet.yyVel;
        this.mass = otherPlanet.mass;
        this.imgFileName = otherPlanet.imgFileName;
    }

    public double calcDistance(Planet p) {
        return Math.sqrt((p.xxPos - this.xxPos)*(p.xxPos - this.xxPos)
                + (p.yyPos - this.yyPos) * (p.yyPos - this.yyPos));
    }

    public double calcForceExertedBy(Planet p) {
        return (this.mass * p.mass * Planet.G) / (this.calcDistance(p) * this.calcDistance(p));
    }

    public double calcForceExertedByX(Planet that) {
        return calcForceExertedBy(that) * (that.xxPos - this.xxPos) / calcDistance(that);
    }

    public double calcForceExertedByY(Planet that) {
        return calcForceExertedBy(that) * (that.yyPos - this.yyPos) / calcDistance(that);
    }

    public void update(double dt, double fX, double fY) {
        this.xxVel += fX / mass * dt;
        this.yyVel += fY / mass * dt;
        this.xxPos += this.xxVel * dt;
        this.yyPos += this.yyVel * dt;
    }

}