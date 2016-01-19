/** 
  * ICS4U
  * 
  * Data Class
 */
import java.awt.*;
import javax.swing.*;
public class Data extends JPanel {
  
  //info for the display of one equation
  private JTextField equationField = new JTextField (25); //text field for String input
  private JLabel current = new JLabel(); //equation as a string
  private JLabel xintField = new JLabel(); //x intercept
  private JLabel yintField = new JLabel(); //y intercept
  private JLabel slopeField = new JLabel(); //slope
  
  public Data(String message)
  {
    JPanel input = new JPanel();
    input.add (new JLabel (message));
    input.add (equationField);
        
    //Panels with text and output field for each value
    JPanel currentEquation = createNewPanel("The current linear equation is: ", current);
    JPanel xint = createNewPanel("There is a x-intercept at: ", xintField);
    JPanel yint = createNewPanel("There is a y-intercept at: ", yintField);
    JPanel slope = createNewPanel("The slope is: ", slopeField);
    
    //set Layout and add panels
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    add(input);
    add(currentEquation);
    add(xint);
    add(yint);
    add(slope);
    setPreferredSize(new Dimension(500, 200));
  }
  
  //method to create each panel with a message and the variable output text as JLabel
  public JPanel createNewPanel(String message, JLabel label)
  {
    JPanel pane = new JPanel();
    pane.add(new JLabel(message));
    pane.add(label);
    return pane;
  }
  
  //Setter methods (modifiers) for each value field
  public void setcurrent(String message)
  {
    this.current.setText(message);
  }
  
  public void setxintField(String message)
  {
    this.xintField.setText(message);
  }
  
  public void setyintField(String message)
  {
    this.yintField.setText(message);
  }
  
  public void setslopeField(String message)
  {
    this.slopeField.setText(message);
  }
  
  //returns the String input into text field
  public String getEquation()
  {
    return equationField.getText();
  }
}
