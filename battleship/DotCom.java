import java.util.*;

public class DotCom{

	private ArrayList<String> locationCells;
	private String name;

	public void setLocationCells(ArrayList<String> locs){
		locationCells = locs;
	}

	public void setName(String n){
		name = n;
	}

	public String checkYourself(String stringGuess){
		System.out.println("checkYourself stringGuess: " +stringGuess);
		String result = "Miss";

		if (stringGuess.equals("givemepie")) {
			result = "You have stolen the pie!";
			return result;
			for (DotCom dotComToSet: dotComsList){
					ArrayList<String> newLocation = helper.placeDotCom(3, true);
					dotComToSet.setLocationCells(newLocation);
		}
		
		int index = locationCells.indexOf(stringGuess);

		if (index >=0 ){
			locationCells.remove(index);

			if(locationCells.isEmpty()){
				result = "Kill";
				System.out.println("Ouch you sunk " + name + "! ");
			} else {
				result = "Hit";
			}
		}

		return result;
	}
}