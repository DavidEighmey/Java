public class Game {
	public static void main(String[] args){
		int numberOfGuesses = 0;

		OldGameHelper helper = new OldGameHelper();

		SimpleDotCom theDotCom = new SimpleDotCom();

		int randomNum = (int) (Math.random() * 5);

		int[] locations = {randomNum, randomNum+1, randomNum+2};

		theDotCom.setLocationCells(locations);

		boolean isAlive = true;

		while(isAlive == true){

			String guess = helper.getUserInput("Enter a number");
			String result = theDotCom.checkYourself(guess);

			numberOfGuesses++;
			System.out.println("numberOfGuesses" + numberOfGuesses);
			if(result.equals("Kill")){
				isAlive = false;
				System.out.println("You took " + numberOfGuesses + " guesses.");
				break;
			}
		}
	}
}
