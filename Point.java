/** 
  * ICS4U
  * 
  * Point Class
 */
public class Point {
  private double x, y; // variables for coordinates
  
  //constructors
  public Point(double xcoord, double ycoord)
  {
    this.x = xcoord;
    this.y = ycoord;
  }
  
  //get x coordinate
  public double getX()
  {
    return x;
  }

  //get y coordinate
  public double getY()
  {
    return y;
  }
  
  //toString method override
  @Override
  public String toString()
  {
    if(x == 0 || y == 0)
    {
      return "";
    }
    return "(" + (int)(x * 1000) / 1000.0 + ", " + (int)(y * 1000) / 1000.0 + ")"; //return point as string with values rounded
  }
  
}
