import java.awt.*;
import java.util.*;

public class SandLab
{
  //Step 4,6
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  
  //do not add any more fields below
  private int[][] grid;
  private SandDisplay display;
  
  
  /**
   * Constructor for SandLab
   * @param numRows The number of rows to start with
   * @param numCols The number of columns to start with;
   */
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    // Change this value to add more buttons
    //Step 4,6
    names = new String[4];
    // Each value needs a name for the button
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    
    //1. Add code to initialize the data member grid with same dimensions
    grid = new int [numRows][numCols];
    
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
    //2. Assign the values associated with the parameters to the grid
   grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
      //Step 3
   //Hint - use a nested for loop
	  for(int i = 0; i < grid.length; i++)
	  {
		  for(int j = 0; j < grid[0].length ; j++)
		  {
			  Color gray = Color.GRAY;
			  Color black = Color.BLACK;
			  Color yellow = new Color(220, 160, 90);
			  Color blue = Color.BLUE;
			  if(grid[i][j] == EMPTY)
			  {
				  display.setColor(i, j, black);
			  }
			  else if(grid[i][j] == METAL)
			  {
				  display.setColor(i, j, gray);
			  }
			  else if(grid[i][j] == SAND)
			  {
				  display.setColor(i, j, yellow);
			  }
			  else
			  {
				  display.setColor(i, j, blue);
			  }
		  }
	  }
    
  }

  //Step 5,7
  //called repeatedly.
  //causes one random particle in grid to maybe do something.
  public void step()
  {
    //Remember, you need to access both row and column to specify a spot in the array
    //The scalar refers to how big the value could be
    //int someRandom = (int) (Math.random() * scalar)
    //remember that you need to watch for the edges of the array
    
	  Random rand = new Random();
	  int direction = rand.nextInt(2);
	  int direction2 = rand.nextInt(2);
	  
	  
	  //Sand Random
	  int randomRow = (int) (Math.random() * grid.length - 1);
	  int randomCol = (int) (Math.random() * grid[0].length -1);
	  //Sand Physics
	  if(grid[randomRow][randomCol] == SAND && grid[randomRow + 1][randomCol] == EMPTY)
	  {
		  grid[randomRow][randomCol] = EMPTY;
		  grid[randomRow + 1][randomCol] = SAND;
	  }
	  if(grid[randomRow][randomCol] == SAND && grid[randomRow + 1][randomCol] == WATER)
	  {
		  grid[randomRow][randomCol] = WATER;
		  grid[randomRow + 1][randomCol] = SAND;
	  }
	  //Water Physics
	  if(grid[randomRow][randomCol] == WATER && grid[randomRow + 1][randomCol] == EMPTY)
	  {

		  grid[randomRow][randomCol] = EMPTY;
		  grid[randomRow + 1][randomCol] = WATER;
		  
		  if(randomCol -1 != -1 && direction == 0 && grid[randomRow + 1][randomCol - 1] == EMPTY)
		  {
			  grid[randomRow + 1][randomCol - 1] = WATER;
			  grid[randomRow + 1][randomCol] = EMPTY;
		  }
		  else if(direction == 1 && grid[randomRow + 1][randomCol + 1] == EMPTY)
		  {
			  grid[randomRow + 1][randomCol + 1] = WATER;
			  grid[randomRow + 1][randomCol] = EMPTY;
		  }
		  else
		  {
			  
		  }
	  }
    
  }
  
  //do not modify this method!
  public void run()
  {
    while (true) // infinite loop
    {
      for (int i = 0; i < display.getSpeed(); i++)
      {
        step();
      }
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
      {
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
      }
    }
  }
}
