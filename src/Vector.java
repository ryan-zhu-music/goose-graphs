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

  public void sub(Vector v) {
    x -= v.getX();
    y -= v.getY();
  }

  public void mult(double d) {
    x *= d;
    y *= d;
  }

  public void div(double d) {
    x /= d;
    y /= d;
  }

  public double mag() {
    return Math.sqrt(x * x + y * y);
  }

  public void normalize() {
    double m = mag();
    if (m != 0) {
      div(m);
    }
  }

  public int getX() {
    return (int) x;
  }

  public int getY() {
    return (int) y;
  }

}
