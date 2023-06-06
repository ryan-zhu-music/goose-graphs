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

  public void subtract(Vector v) {
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

  public void getUnit() {
    double m = magnitude();
    if (m != 0) {
      divScalar(m);
    }
  }

  public int getX() {
    return (int) x;
  }

  public int getY() {
    return (int) y;
  }

  public String toString() {
    return "[" + x + ", " + y + "]";
  }

}
