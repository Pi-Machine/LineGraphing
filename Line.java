/** 
  * ICS4U
  * 
  * Line Class
  */
import java.awt.*;
public class Line
{
  private double A, B, C; //private variables
  
  //default constructor
  public Line()
  {
  }
  
  //constructor to assign private variables
  public Line(double a, double b, double c)
  {
    this.A = a;
    this.B = b;
    this.C = c;
  }
  
  //readLine method to construct a Line with appropriate parameters from a String input
  public boolean readLine(String equation)
  {
    boolean left = true; //left or right of equals sign
    boolean sign = true; //deterimine if plus or minus sign
    //results
    double a = 0;
    double b = 0;
    double c = 0;
    equation = equation.replace(" ", ""); //remove all spaces
    int last = 0; //beginning index for each individul term
    
    //if equation is empty
    if(equation.equals(""))
      return true;
    
    //loop through all characters in string
    for(int i = 0 ; i < equation.length(); i++)
    {
      char current = equation.charAt(i);
      if(!(current == '=' || current == '+' || current == '-' || current == 'x' || current == 'y' || current == 'X' || current == 'Y' || (current < 58 && current > 47)))//invalid characters
      {
        return false; //false condition, invalid characters
      }
      if(!left && current == '=')//2 equal signs
      {
        return false; //false condition, 2 equals signs
      }
      
      if(current == '+' || current == '-' || current == '=' || i == equation.length() - 1)//"breakpoints" at +, -, = signs or end of string to break into separate terms
      {
        //get the term
        String term;
        if(i == equation.length() - 1)
        {
          term = equation.substring(last, i + 1);
        }else{
          term = equation.substring(last, i);
        }
        
        //primary processing
        if(term.length() == 0)
        {
          if(current == '-')
          {
            sign = !sign; //change the sign if term is only a single minus sign
          }
        }else{
          char var = term.charAt(term.length() - 1); //find the variable (x, y, or constant term) at end of term
          boolean add = (left && sign) || !(left || sign); //*XOR LOGIC GATE* - booelans with the sign and the side of the equation to determine if add or subtract
          double value = 0;
          try{
            if(term.length() == 1)//only single character term without coefficients
            {
              if(var == 'x' || var == 'X' || var == 'y' || var == 'Y') //set coefficient value to 1 for variables
              {
                value = 1;
              }else if(!term.equals("-"))
              {
                value = Double.parseDouble(term); //set coefficient value to the term itself for constants
              }
            }else{ //longer terms
              value = Double.parseDouble(term.substring(0, term.length() - 1)); //set coefficient value to the value of 
            }
            
            //add or subtract to a, b, c accordingly
            if(var == 'x' || var == 'X') //x
            {
              if(add)
              {
                a += value;
              }else{
                a -= value;
              }
            }else if(var == 'y' || var == 'Y')//y
            {
              if(add)
              {
                b += value;
              }else{
                b -= value;
              }
            }else//constant term
            {
              if(add)
              {
                c += Double.parseDouble(term);
              }else{
                c -= Double.parseDouble(term);
              }
            }
            if(current == '-')//change of signs
            {
              sign = false;
            }else{
              sign = true;
            }
          }catch(Exception e)
          {
            return false; //if invalid equation went wrong, equation is invalid
          }
        }
        last = i + 1;
        if(current == '=') //determine which side of equal sign
        {
          left = false;
        }
      }
    }
    this.A = a;
    this.B = b;
    this.C = c;
    return true;
  }
  
  //method to find x intercept
  public Double xint ()
  {
    if(!isHorizontal()) //if not horizontal
      return (double)(-C/A); //calculate x intercept
    return null; //return null for horizontal lines
  }
  
  //method to find y intercept
  public Double yint()
  {
    if(!isVertical()) //if not vertical
      return (double)(-C/B); //calculate y intercept
    return null; //return null for horizontal lines
  }
  
  //determine if the line is horizontal
  public boolean isHorizontal()
  {
    return A == 0; //determine whether the line is horizontal
  }
  
  //determine if the line is vertical
  public boolean isVertical()
  {
    return B == 0; //determine whether the line is vertical
  }
  
  //method to find the slope
  public Double slope()
  {
    if(!isVertical() && !isHorizontal()) //if valid line
      return (double)(-A/B);
    if(isHorizontal()) //slope is 0 for horizontal lines
      return new Double(0);
    return null;
  }
  
  public Point intersects(Line line)
  {
    //if both lines are parallel or vertical or horizontal or doesn't exist (special cases)
    if(line.slope() == this.slope() || (line.isVertical() && this.isVertical()) || (line.isHorizontal() && this.isHorizontal()) || line == null)
    {
      return null;
    }
    
    //if one line is vertical and other is horizontal (special cases)
    if(line.isVertical() && this.isHorizontal())
    {
      return new Point(line.xint(), this.yint());
    }
    if(line.isHorizontal() && this.isVertical())
    {
      return new Point(this.xint(), line.yint());
    }
    
    //only one vertical line (special cases)
    if(line.isVertical()) //if one line is vertical, other is normal
    {
      return new Point(line.xint(), this.solve(line.xint())); 
    }
    if(this.isVertical()) //if one line is vertical, other is normal
    {
      return new Point(this.xint(), line.solve(this.xint()));
    }
    
    //solving for x in  L1 = m1x1 + b1, L2 = m2x2 + b2 in a normal situation
    double x = (this.yint() - line.yint()) / (line.slope() - this.slope());
    double y = this.solve(x);
    return new Point(x, y);
  }
  
  //solve the equation (return the y value) given the x
  public Double solve(double x)
  {
    if(!isVertical())
      return this.slope() * x + this.yint();
    return null;
  }
  
  //solve the equation (return the y value) given the x and a scale
  public Double solve(double x, double hzoom, double vzoom)
  {
    if(!isVertical())
      return vzoom * (this.slope() * x / hzoom + this.yint());
    return null;
  }
  
  //determine whether the equation is empty
  public boolean isEmpty()
  {
    if(A == 0 && B == 0 && C == 0)
    {
      return true;
    }
    return false;
  }
  
  //draws the line
  public void drawLine (Graphics g)
  {   
    if(this.xint() != null || this.yint() != null)
    {
      if(this.isVertical()) //draw a vertical line (special case)
      {
        g.drawLine((int)(UserInterface.H_ZOOM * this.xint() + UserInterface.ORIGIN_X), 0, (int)(UserInterface.H_ZOOM * this.xint() + UserInterface.ORIGIN_X), UserInterface.DRAW_HEIGHT);
      }else if(this.isHorizontal()) //draw a horizontal line (special case)
      {
        g.drawLine(0, (int)(UserInterface.ORIGIN_Y - UserInterface.V_ZOOM * this.yint()), UserInterface.DRAW_HEIGHT, (int)(UserInterface.ORIGIN_Y - UserInterface.V_ZOOM * this.yint()));
      }else{ //draw a normal non-horizontal, non-vertical line
        g.drawLine(0, (int)(UserInterface.ORIGIN_Y - this.solve(0 - UserInterface.ORIGIN_X, UserInterface.H_ZOOM, UserInterface.V_ZOOM)), UserInterface.DRAW_WIDTH, (int)(UserInterface.ORIGIN_Y - this.solve(UserInterface.DRAW_WIDTH - UserInterface.ORIGIN_X, UserInterface.H_ZOOM, UserInterface.V_ZOOM)));
      }
    }
    g.setColor(Color.BLACK);
  }
  
  //toString method overridden
  @Override
  public String toString ()
  {
    //if the equation is empty return no equation
    if(isEmpty())
    {
      return "No equation";
    }
    
    //ouput string
    String out = "";
    //A variable
    if(A == 1) //coefficient of 1
    {
      out += "x";
    }else if(A == -1) //coefficient of -1
    {
      out += "-x";
    }
    else if(A != 0) //other coefficients
    {
      out += A + "x";
    }
    
    //B variable
    if(B != 0)
    {
      //keep + or - sign if there is a coefficient for x
      if(A != 0)
      {
        out += (B<0?" - ":" + "); //add sign for y part
      }
      out += ((B == 1 || B == -1)?"y":Math.abs(B) + "y"); //print absolute value of y coefficient
    }
    
    //C variable
    if(C != 0)
    {
      if(B != 0 || A != 0) //keep + or - sign if there is a coefficient for x or y
      {
        out += (C<0?" - ":" + "); //add sign for constant term
      }
      out += Math.abs(C); //add absolute value of last parameter
    }
    out += " = 0"; //finish equation
    return out; //return the output string
  }
}
