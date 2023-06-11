public class Vector {
  private double x;
  private double y;

  public Vector(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public void add(Vector v) {
    x += v.getX();
    y += v.getY();
  }

  public void addX(int num) {
    x += num;
  }

  public void addY(int num) {
    y += num;
  }

  public void sub(Vector v) {
    x -= v.getX();
    y -= v.getY();
  }

  public void multScalar(double d) {
    x *= d;
    y *= d;
  }

  public void divScalar(double d) {
    x /= d;
    y /= d;
  }

  public double magnitude() {
    return Math.sqrt(x * x + y * y);
  }

  public void normalize() {
    double m = magnitude();
    if (m != 0) {
      divScalar(m);
    }
  }

  public double dot(Vector v) {
    return x * v.getX() + y * v.getY();
  }

  // returns acute angle between two vectors
  public double angleBetween(Vector v) {
    double ratio = this.dot(v) / (this.magnitude() * v.magnitude());
    if (ratio > 1) {
      ratio = 1;
    }
    if (ratio < -1) {
      ratio = -1;
    }
    double angle = Math.acos(ratio);
    if (angle > Math.PI / 2) {
      angle = Math.PI - angle;
    }
    return angle;
  }

  public double angle() {
    return Math.atan2(y, x);
  }

  // returns a new vector in the direction that a vector v would bounce of this
  // vector with the same magnitude as v
  public Vector bounceAngle(Vector v) {
    double a1 = this.angle();
    double a2 = v.angle();
    double newAngle = 2 * a1 - a2;
    if (Math.abs(a1 - a2) < 0.7) {
      newAngle = a1;
    }
    double magnitude = v.magnitude();
    Vector newVector = new Vector(magnitude * Math.cos(newAngle), magnitude * Math.sin(newAngle));
    newVector.multScalar(0.5 * Math.cos(this.angleBetween(v)) + 0.5);
    return newVector;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public String toString() {
    return "[" + x + ", " + y + "]";
  }

}
