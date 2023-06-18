// Name: Delin Gu and Ryan Zhu
// Date: June 17th, 2021
// Assignment: FINAL ISU!!!
// Description: A class implementing common vector functionalities

public class Vector {
  private double x;
  private double y;

  public Vector(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Vector(Vector v) {
    this.x = v.getX();
    this.y = v.getY();
  }

  // sets this vector to the same as another vector v
  public void set(Vector v) {
    x = v.getX();
    y = v.getY();
  }

  // adds another vector v to this vector
  public void add(Vector v) {
    x += v.getX();
    y += v.getY();
  }

  // adds a number to the x or y component of this vector
  public void addX(int num) {
    x += num;
  }

  public void addY(int num) {
    y += num;
  }

  // subtracts another vector v from this vector
  public void sub(Vector v) {
    x -= v.getX();
    y -= v.getY();
  }

  // multiplies or divides this vector by a scalar
  public void multScalar(double d) {
    x *= d;
    y *= d;
  }

  // multiplies or divides this vector by a scalar
  public void divScalar(double d) {
    x /= d;
    y /= d;
  }

  // returns the magnitude of this vector
  public double magnitude() {
    return Math.sqrt(x * x + y * y);
  }

  // returns the unit vector of this vector
  public void normalize() {
    double m = magnitude();
    if (m != 0) {
      divScalar(m);
    }
  }

  // returns the dot product of this vector and another vector v
  public double dot(Vector v) {
    return x * v.getX() + y * v.getY();
  }

  // returns the vector projection of another vector v onto this vector
  public Vector projection(Vector v) {
    Vector v1 = new Vector(x, y);
    v1.normalize();
    v1.multScalar(v.dot(v1));
    return v1;
  }

  // returns the distance between this point and another point v
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

  // returns the polar angle of this vector in radians
  public double angle() {
    return Math.atan2(y, x);
  }

  // returns a new vector in the direction that a vector v would bounce of this
  // vector with the same magnitude as v
  public Vector bounceAngle(Vector v) {
    Vector newVector;
    Vector unit1 = new Vector(x, y);
    unit1.normalize();
    Vector unit2 = new Vector(v.getX(), v.getY());
    unit2.normalize();
    if (Math.abs(unit1.dot(unit2) / (unit1.magnitude() * unit2.magnitude())) > 0.75) {
      // if vectors are close to parallel, no bounce
      newVector = this.projection(v);
    } else {
      // otherwise, bounce
      double newAngle = 2 * this.angle() - v.angle();
      double magnitude = v.magnitude();
      newVector = new Vector(magnitude * Math.cos(newAngle), magnitude * Math.sin(newAngle));
      newVector.multScalar(0.3 * Math.cos(this.angleBetween(v)) + 0.7);
    }
    return newVector;
  }

  // getters
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
