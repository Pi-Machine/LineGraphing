/** 
  * ICS4U
  * 
  * UserInterface Class
  */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
public class UserInterface extends JFrame {
  //global constants
  static final int GRAPH_WIDTH = 750; //area for entire graphing display, including buttons
  static final int GRAPH_HEIGHT = 750;
  static final int BUFFER_BUTTON = 10; //size for move buttons
  static final int BUFFER_OUTER = 25; //size for buffer around the graphing display
  static final int SPACING = 50; //spacing between each region in display area
  static final int DRAW_WIDTH = GRAPH_WIDTH - 8 * BUFFER_BUTTON; //area for the actual graph
  static final int DRAW_HEIGHT = GRAPH_HEIGHT - 4 * BUFFER_BUTTON;
  static final int SHIFT = 4; //how much to shift by with each button click
  static final String dne = "Does not exist"; //does not exist message output
  static final String[] symbols = {"Up", ">", "Down", "<"}; //arrows for buttons
  //global constants for initialization
  static final int I_ORIGIN_X = DRAW_WIDTH / 2; //intial x position of origin
  static final int I_ORIGIN_Y = DRAW_HEIGHT / 2; //intial y position of origin
  static final int I_ZOOM = 10; //inital zoom
  static final int I_SCALE = 5; //intial scale
  //changeable global variables
  static int ORIGIN_X = I_ORIGIN_X; //x position of origin
  static int ORIGIN_Y = I_ORIGIN_Y; //y position of origin
  static int H_ZOOM = I_ZOOM; //horizontal zoom
  static int V_ZOOM = 5; //vertical zoom
  static int SCALE = I_SCALE; //scale for each tick
  //other private variables
  private ArrayList<Data> displays = new ArrayList<Data>(); // displays for two equation input/output
  private ArrayList<String> equations = new ArrayList<String>(); //equations
  private ArrayList<Line> lines = new ArrayList<Line>(); //Lines
  private JLabel intersectionText = new JLabel(); //Intersection Point JLabel
  private JButton submitLine = new JButton(); //Submit button
  private JTextField inScale = new JTextField(3); //scale input text
  private JButton zoomIn = new JButton("Zoom In"); //buttons for zooming and scaling
  private JButton zoomOut = new JButton("Zoom Out");
  private JButton rescale = new JButton("Rescale");
  private JButton helpButton = new JButton("Help!!!"); //displays help text (\t doesn't work, so I used spaces instead)
  private String helpText = "Welcome to my graphing application!\n     >Enter any equation(s) you want"
    + "\n     >All the data will be displayed if it exists"
    + "\n     >The line(s) will be graphed in colour"
    + "\n     >Enter a valid value to rescale the labels"
    + "\n     >Enter values to set bounds"
    + "\n     >Press the buttons to zoom in and out"
    + "\nEnjoy!"; //help text
  private ArrayList<JButton> move = new ArrayList<JButton>(); // 0 - up, 1 - right, 2 - down, 3 - left for moving graph
  private JButton boundButton = new JButton("Set Bounds"); // button to enter bounds
  private ArrayList<JTextField> boundText = new ArrayList<JTextField>(); // bound input
  
  public UserInterface ()
  {
    equations.add("");//initialize 2 equations
    equations.add("");
    
    lines.add(new Line());//initialize 2 lines
    lines.add(new Line());
    
    JPanel content = new JPanel (); //left panel for input and output text
    content.setLayout (new FlowLayout ());
    
    JPanel equationDisplays = new JPanel();//panel for input and output of equations
    equationDisplays.setLayout(new BoxLayout(equationDisplays, BoxLayout.Y_AXIS)); //centre align
    equationDisplays.add(createBuffer(SPACING)); //add spacing at top
    
    //create displays for two equations
    Data display1 = new Data("Enter the first Line: ");
    Data display2 = new Data("Enter the second Line: ");
    displays.add(display1); //add displays for the two equations to ArrayList
    displays.add(display2);
    for(int i = 0; i < displays.size(); i++)
    {
      equationDisplays.add(displays.get(i));
    }
    
    submitLine = new JButton ("Submit"); //submit button with ActionListener
    submitLine.addActionListener (new BtnListener ()); 
    equationDisplays.add(submitLine); //add submit button
    equationDisplays.add(createBuffer(20)); //add spacing after button
    
    //display for intersection of lines
    JPanel intersectionDisplay = new JPanel();
    intersectionDisplay.add(new JLabel("The intersection of the two lines is: "));
    intersectionDisplay.add(intersectionText);
    equationDisplays.add(intersectionDisplay);
    equationDisplays.add(createBuffer(SPACING));
    
    //display for scale
    JPanel scaling = new JPanel();
    rescale.addActionListener(new BtnListener()); //rescale button with ActionListener
    scaling.add(new JLabel("Scale: "));
    scaling.add(inScale);
    scaling.add(rescale);
    equationDisplays.add(scaling);
    
    //display for zoom options
    JPanel zooming = new JPanel();
    zoomIn.addActionListener(new BtnListener()); //ActionListener for zoom and help buttons
    zoomOut.addActionListener(new BtnListener());
    helpButton.addActionListener(new BtnListener());
    zooming.add(zoomIn);
    zooming.add(zoomOut);
    equationDisplays.add(zooming);
    equationDisplays.add(createBuffer(SPACING));
    
    //set Bounds
    JPanel bounds = new JPanel();
    for(int i = 0; i < 4; i++)
    {
      if(i == 0)
      {
        bounds.add(new JLabel("Top"));
      }else if(i == 1)
      {
        bounds.add(new JLabel("Right"));
      }else if(i == 2)
      {
        bounds.add(new JLabel("Down"));
      }else if(i == 3)
      {
        bounds.add(new JLabel("Left"));
      }
      boundText.add(new JTextField(5));
      bounds.add(boundText.get(i));
    }
    equationDisplays.add(bounds);
    boundButton.addActionListener(new BtnListener());
    equationDisplays.add(boundButton);
    equationDisplays.add(createBuffer(SPACING));
    
    //help button
    equationDisplays.add(helpButton);
    equationDisplays.add(createBuffer(SPACING));
    
    //graph buttons init
    for(int i = 0; i < 4; i++)
    {
      JButton temp = new JButton(symbols[i] + "");
      temp.addActionListener(new BtnListener());
      move.add(temp);
    }
    //graph area initialization
    JPanel buttonPlane = new JPanel(); //button plane includes graph and buttons
    buttonPlane.setLayout(new BorderLayout());
    JPanel containplane = new JPanel(); //contain plane includes button plane and outside buffer
    containplane.setLayout(null);
    containplane.add(buttonPlane);
    containplane.setPreferredSize(new Dimension(GRAPH_WIDTH + 2 * BUFFER_OUTER, GRAPH_HEIGHT + 2 * BUFFER_OUTER));
    DrawArea plane = new DrawArea(); //plane is only for the graph inside itself
    Insets insets = containplane.getInsets();
    buttonPlane.setBounds(BUFFER_OUTER + insets.left, BUFFER_OUTER + insets.top, GRAPH_WIDTH, GRAPH_HEIGHT);
    buttonPlane.add(move.get(0), BorderLayout.PAGE_START);
    buttonPlane.add(move.get(1), BorderLayout.LINE_END);
    buttonPlane.add(move.get(2), BorderLayout.PAGE_END);
    buttonPlane.add(move.get(3), BorderLayout.LINE_START);
    buttonPlane.add(plane, BorderLayout.CENTER);
    
    //set entire panel
    content.setLayout(new BorderLayout(30, 100));
    content.add(equationDisplays, BorderLayout.LINE_START);
    content.add(containplane, BorderLayout.LINE_END);
    
    //set entire frame details
    setContentPane (content);
    pack();
    setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo (null);
    setTitle("Line Graphing and Calculation");
    
    setVisible (true);
    
    JOptionPane.showMessageDialog(this, "Welcome to my line graphing application!"); //welcome message
  }
  
  //help dialog box
  public void help()
  {
    JOptionPane.showMessageDialog(this, helpText);
  }
  
  //error dialog for incorrect equation
  public void errorEquation()
  {
    JOptionPane.showMessageDialog(this, "Please enter a valid equation.", "Invalid equation", JOptionPane.ERROR_MESSAGE);
  }
  
  //error dialog for invalid bounds
  public void errorBounds()
  {
    JOptionPane.showMessageDialog(this, "Please enter valid bounds. There may be empty fields or the input may be too large or too small.", "Invalid Bounds", JOptionPane.ERROR_MESSAGE);
  }
  
  //error dialog for incorrect equation
  public void errorScale()
  {
    JOptionPane.showMessageDialog(this, "Please enter a valid scale. The scale must be a number between 1 and 1000 (inclusive).", "Invalid scale", JOptionPane.ERROR_MESSAGE);
  }
  
  //create an empty JPanel to act as a buffering area between displays
  public JPanel createBuffer(int size)
  {
    JPanel buffer = new JPanel();
    buffer.setPreferredSize(new Dimension(500, size));
    return buffer;
  }
  
  //display data whenever submit button is pressed
  public void display()
  {
    for(int i = 0; i < displays.size(); i++)
    {
      Data current = displays.get(i);
      Line line = lines.get(i);
      current.setcurrent(line.toString());
      if(line.xint() != null){//set x-intercept if exists
        current.setxintField((int) (100 * line.xint()) / 100.0 + "");
      }else{
        current.setxintField(dne);
      }
      if(line.yint() != null){//set y-intercept if exists
        current.setyintField((int) (100 * line.yint()) / 100.0 + "");
      }else{
        current.setyintField(dne);
      }
      if(line.slope() != null){//set slope if exists
        current.setslopeField((int) (100 * line.slope()) / 100.0 + "");
      }else{
        current.setslopeField(dne);
      }
    }
  }
  
  //method to process setting the bounds
  public boolean setBounds()
  {
    //getting the input
    int top = Integer.parseInt(boundText.get(0).getText());
    int right = Integer.parseInt(boundText.get(1).getText());
    int bottom = Integer.parseInt(boundText.get(2).getText());
    int left = Integer.parseInt(boundText.get(3).getText());
    
    //calculations
    int vertical = top - bottom;
    int horizontal = right - left;
    
    //determine if bounds are valid
    if(vertical < 5 || horizontal < 5 || (top > 0 && bottom > 0) || (bottom < 0 && top < 0) || (right > 0 && left > 0) || (left < 0 && right < 0))
    {
      return false;
    }
    
    //set the zoom
    int zoom = DRAW_HEIGHT / vertical;
    if(zoom <= 0)
    { //must be valid zoom
      return false;
    }
    V_ZOOM = zoom;
    zoom = DRAW_HEIGHT / horizontal;
    if(zoom <= 0)
    {
      return false; //must be valid zoom
    }
    H_ZOOM = zoom;
    
    //recentre the origin
    ORIGIN_X = 0 - left * H_ZOOM;
    ORIGIN_Y = DRAW_HEIGHT + bottom * V_ZOOM;
    return true;
  }
  
  //method to process setting the scale
  public void setScale()
  {
    try{
      int temp = Integer.parseInt(inScale.getText());
      if(temp > 0 && temp < 1010)
      {
        SCALE = temp;
      }else{
        errorScale();
      }
    }catch(Exception e2)
    {
      errorScale();
    }
  }
  
  //Main method
  public static void main (String[] args)
  {
    UserInterface window = new UserInterface ();
    window.toFront();
  }
  
  
  //INNER CLASSES  
  class BtnListener implements ActionListener // Inner class for handling events
  {
    public void actionPerformed (ActionEvent e)
    {
      if(e.getSource() == submitLine) //for the submit button
      {
        if(!inScale.getText().equals("")) //get the scale
        {
          setScale();
        }
        
        //primary input operations
        for(int i = 0; i < displays.size(); i ++)
        {
          equations.set(i, displays.get(i).getEquation()); //get text and add each line as appropriate
          
          if(!lines.get(i).readLine(equations.get(i))) //call readLine method for each line
          {
            errorEquation();
          }else{
            display();
            repaint (); // refresh graph
          }
        }
        
        //get intersection if there are two lines
        if(!lines.get(0).isEmpty() && !lines.get(1).isEmpty())
        {
          Point p = lines.get(0).intersects(lines.get(1));
          if(p != null)
          {
            intersectionText.setText(p.toString());
          }else{
            intersectionText.setText("No point");
          }
        }
      }else if(e.getSource() == zoomIn) //for the zoomIn button
      {
          H_ZOOM *= 2;
          V_ZOOM *= 2;
        repaint();
      }else if(e.getSource() == zoomOut) //for the zoomOut button
      {
        if(H_ZOOM >= 2)
          H_ZOOM *= 0.5;
        if(V_ZOOM >= 2)
          V_ZOOM *= 0.5;
        repaint();
      }else if(e.getSource() == rescale) //for the rescale button
      {
        setScale();
        repaint();
      }else if(e.getSource() == helpButton) //for the help button
      {        
        help();
      }else if(e.getSource() == move.get(0)) //for the move up button
      {
        ORIGIN_Y += V_ZOOM * SHIFT;
        repaint();
      }else if(e.getSource() == move.get(1)) //for the move right button
      {
        ORIGIN_X -= H_ZOOM * SHIFT;
        repaint();
      }else if(e.getSource() == move.get(2)) //for the move down button
      {
        ORIGIN_Y -= V_ZOOM * SHIFT;
        repaint();
      }else if(e.getSource() == move.get(3)) //for the move left button
      {
        ORIGIN_X += H_ZOOM * SHIFT;
        repaint();
      }else if(e.getSource() == boundButton) //for the setBounds button
      {
        try
        {
          if(!setBounds())
          {
            errorBounds();
          }
          repaint();
        }catch(Exception e2)
        {
          errorBounds();
        }
      }
    }
  }
  
  //Inner class for the graph drawing
  class DrawArea extends JPanel
  {
    public DrawArea ()
    {
      this.setPreferredSize (new Dimension (DRAW_WIDTH, DRAW_HEIGHT)); // size setting
    }
    
    //draw each line
    public void paintComponent (Graphics g)
    {
      drawGrid(g);
      for(int i = 0; i < lines.size(); i++)
      { 
        if(i == 0)
        {
          g.setColor(Color.RED); //red for first line
        }else if(i == 1)
        {
          g.setColor(Color.BLUE); //blue for second line
        }
        lines.get(i).drawLine(g);
      }
    }
    
    //draw grid and labels
    public void drawGrid(Graphics g)
    {
      //create a white background
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, DRAW_WIDTH, DRAW_HEIGHT);
      
      //loop to draw grid lines and labels
      for(int i = 0; i < (DRAW_HEIGHT - ORIGIN_Y) / V_ZOOM; i+=SCALE) // y-axis bottom labeling and grid
      {
        drawVert(g, i);
      }
      for(int i = -1 * SCALE; i > (0 - ORIGIN_Y) / V_ZOOM; i-=SCALE) // y-axis top labeling and grid
      {
        drawVert(g, i);
      }
      for(int i = 0; i < (DRAW_WIDTH - ORIGIN_X) / H_ZOOM; i+=SCALE) // x-axis right labeling and grid
      {
        drawHor(g, i);
      }
      for(int i = -1 * SCALE; i > (0 - ORIGIN_X) / H_ZOOM; i-=SCALE) // x-axis left labeling and grid
      {
        drawHor(g, i);
      }
      
      //draw the axis on top
      g.setColor(Color.BLACK);
      g.drawLine(ORIGIN_X, 0, ORIGIN_X, DRAW_HEIGHT);//y-axis
      g.drawLine(0, ORIGIN_Y, DRAW_WIDTH, ORIGIN_Y);//x-axis
    }
  }
  
  //draw grid helper
  public void drawVert(Graphics g, int i)
  {
        g.setColor(Color.BLACK);
        g.drawString(-1 * i + "", ORIGIN_X + 10, i * V_ZOOM + ORIGIN_Y);
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(0, i * V_ZOOM + ORIGIN_Y, DRAW_WIDTH, i * V_ZOOM + ORIGIN_Y);
  }
  //draw grid helper 2
  public void drawHor(Graphics g, int i)
  {
        g.setColor(Color.BLACK);
        g.drawString(i + "", i * H_ZOOM + ORIGIN_X, ORIGIN_Y + 15);
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(i * H_ZOOM + ORIGIN_X, 0, i * H_ZOOM + ORIGIN_X, DRAW_HEIGHT);
  }
}
