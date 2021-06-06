
public class Body {
    public static final double G = 6.67E-11;
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Body(double xP, double yP, double xV, double yV, double m, String img) {
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    public Body(Body b) {
        this(b.xxPos, b.yyPos, b.xxVel, b.yyVel, b.mass, b.imgFileName);
    }

    /**
     * 返回两物体之间的距离 r^2 = dx^2 + dy^2
     * 
     * @param other
     * @return
     */
    public double calcDistance(Body other) {
        return Math.sqrt(square(this.xxPos - other.xxPos) + square(this.yyPos - other.yyPos));
    }

    /**
     * 返回两个物体之间的引力
     * 
     * @param other
     * @return
     */
    public double calcForceExertedBy(Body other) {
        return G * this.mass * other.mass / square(calcDistance(other));
    }

    /**
     * 返回this物体受到other物体X轴方向的力
     * 
     * @param other
     * @return
     */
    public double calcForceExertedByX(Body other) {
        return calcForceExertedBy(other) * (other.xxPos - this.xxPos) / calcDistance(other);
    }

    /**
     * 返回this物体受到other物体Y轴方向的力
     * 
     * @param other
     * @return
     */
    public double calcForceExertedByY(Body other) {
        return calcForceExertedBy(other) * (other.yyPos - this.yyPos) / calcDistance(other);
    }

    /**
     * 返回this物体受到others中所有物体X轴方向力的和
     * 
     * @param others
     * @return
     */
    public double calcNetForceExertedByX(Body[] others) {
        double result = 0.0;
        for (Body other : others) {
            if (this.equals(other))
                continue;
            result += this.calcForceExertedByX(other);
        }
        return result;
    }

    /**
     * 返回this物体受到others中所有物体Y轴方向力的和
     * 
     * @param others
     * @return
     */
    public double calcNetForceExertedByY(Body[] others) {
        double result = 0.0;
        for (Body other : others) {
            if (this.equals(other))
                continue;
            result += this.calcForceExertedByY(other);
        }
        return result;
    }

    /**
     * 更新物体在deltaT内受到的力之后的 坐标和速度
     * 
     * @param deltaT
     * @param xForce
     * @param yForce
     */
    public void update(double deltaT, double xForce, double yForce) {
        double accelerationX = xForce / this.mass;
        double accelerationY = yForce / this.mass;

        this.xxVel = this.xxVel + deltaT * accelerationX;
        this.yyVel = this.yyVel + deltaT * accelerationY;

        this.xxPos = this.xxPos + deltaT * this.xxVel;
        this.yyPos = this.yyPos + deltaT * this.yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }

    /**
     * 返回某数的平方
     * 
     * @param num
     * @return
     */
    private double square(double num) {
        return num * num;
    }

}