public class Vector {
  private double x;
  private double y;

  public Vector(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public void set(Vector v) {
    x = v.getX();
    y = v.getY();
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

  public Vector projection(Vector v) {
    Vector v1 = new Vector(x, y);
    v1.normalize();
    v1.multScalar(v.dot(v1));
    return v1;
  }

  public double distance(Vector v) {
    return Math.sqrt(Math.pow(x - v.getX(), 2) + Math.pow(y - v.getY(), 2));
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
    Vector newVector;
    if (this.angleBetween(v) < 1.15 && v.magnitude() < 5) {
      newVector = this.projection(v);
    } else {
      double newAngle = 2 * this.angle() - v.angle();
      double magnitude = v.magnitude();
      newVector = new Vector(magnitude * Math.cos(newAngle), magnitude * Math.sin(newAngle));
      newVector.multScalar(0.3 * Math.cos(this.angleBetween(v)) + 0.7);
    }
    return newVector;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public String toString() {
    return String.format("[%.4f, %.4f]", x, y);
  }

}
