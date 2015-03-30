public class Beer {
	
	public static void main(String[] args){
		int beerNum = 99;
		String word = "bottles";

		while(beerNum > 0){

			
			
				System.out.println(beerNum + " " + word );

				beerNum = beerNum - 1;

			if(beerNum == 0) {
				System.out.println("no more beer");
			}
			if (beerNum == 1){
				word = "bottle";
			}
		}
	}
}