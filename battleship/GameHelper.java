import java.io.*;
import java.util.*;

/*
helper.getUserInput("Enter a Guess");
helper.placeDotCom(3);
*/
public class GameHelper{
	
	private static final String alphebet = "abcdefg";
	private int gridLength = 7;
	private int gridSize = 49;
	private int [] grid = new int[gridSize];
	private int comCount = 0;

	public String getUserInput(String userInput){
		String inputLine = null;
		
		System.out.print(userInput + " ");
		try {

			BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
			inputLine = is.readLine();
			if(inputLine.length() == 0)	return null;
		
		}catch (IOException e){
			System.out.println("IOException: " + e);
		}
		return inputLine.toLowerCase();
	}

	public ArrayList<String> placeDotCom(int comSize, boolean giveMeAnswer){
		
		ArrayList<String> alphaCells = new ArrayList<String>();

		String temp = null;
		int[] coords = new int[comSize];
		int attempts = 0;
		boolean success = false;
		int location = 0;

		comCount++;
		int incr = 1;

		if((comCount%2) == 1){
			incr = gridLength;
		}

		while(!success & attempts++ <200){

			location = (int) (Math.random() * gridSize);

			//System.out.print("Try my location: " + location);
			int x = 0;
			success = true;

			while(success && x <comSize){
				
				if(grid[location] ==0){
					coords[x++] = location;
					location += incr;

					if(location >= gridSize){
						success = false;
					}

					if(x>0 && location % gridLength == 0){
						success = false;
					}
				} else{
					//System.out.print("used location: " +location);
					success = false;
				}
			}
		}
		
		int x = 0;
		int row = 0;
		int column = 0;
		System.out.println("\n");

		while(x < comSize){

			grid[coords[x]] = 1;								//marked grid as used
			row  = (int) (coords[x] /gridLength); 				//get row
			column = coords[x] %gridLength;						// get numeric column value
			temp = String.valueOf(alphebet.charAt(column)); 	//convert to alpha

			alphaCells.add(temp.concat(Integer.toString(row)));
			x++;

			if (giveMeAnswer){
				System.out.print("  coord " +x+ " = "+ alphaCells.get(x-1));
			}
		}
	

		System.out.println("\n");

		return alphaCells;
	}
}