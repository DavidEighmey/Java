import java.util.*;

public class DotComBust{
	
	private GameHelper helper = new GameHelper();
	private ArrayList<DotCom> dotComsList = new ArrayList<DotCom>();
	private int numOfGuesses = 0;
	private boolean pie= false;

	private void setUpGame(){

		DotCom one = new DotCom();
		one.setName("Pets.com");
		DotCom two = new DotCom();
		two.setName("eToys.com");
		DotCom three = new DotCom();
		three.setName("Go2.com");

		dotComsList.add(one);
		dotComsList.add(two);
		dotComsList.add(three);
			
		System.out.println("Your goal is to sink thre dot coms.");
		System.out.println("Pets.com, eToys.com, Go2.com");

		for (DotCom dotComToSet: dotComsList){
			ArrayList<String> newLocation = helper.placeDotCom(3, pie);
			dotComToSet.setLocationCells(newLocation);
		}
	}

	private void startPlaying(){

		while(!dotComsList.isEmpty()){
			String userGuess = helper.getUserInput("Enter a Guess");
			
			checkUserGuess(userGuess);
		}
		finishGame();
	}

	private void checkUserGuess(String userGuess){
		//System.out.println("piePhrase:"+ piePhrase);
		//System.out.println("UserGuess:" + userGuess);

		
		numOfGuesses++;

		String result = "Miss";

		for(DotCom dotComToTest: dotComsList){
			result = dotComToTest.checkYourself(userGuess);

			if (result.equals("Hit")){
				break;
			}
			if (result.equals("Kill")){
				dotComsList.remove(dotComToTest);
				break;
			}
			if(result.equals("You have stolen the pie!")){
				System.out.println("\n");
				pie = true;
				reset();
				break;
			}
		}
		System.out.println(result);
	}

	public void reset(){
		numOfGuesses = 0;
		for (DotCom dotComToSet: dotComsList){
					ArrayList<String> newLocation = helper.placeDotCom(3, pie);
					dotComToSet.setLocationCells(newLocation);
		}
	}

	private void finishGame(){

		System.out.println("All Dot Coms are Dead! Your stock is now worless!");
		if(numOfGuesses<=18){
			System.out.println("It only took you " + numOfGuesses + " guesses.");
			System.out.println("You got out before your options sank.");

		} else {
			System.out.println("It took you long enough! " + numOfGuesses + " guesses.");
			System.out.println("Fishes are partying wiht your options");
		}
	}

	public static void main(String[] args){
		DotComBust game = new DotComBust();

		game.setUpGame();
		game.startPlaying();
	}

}