class GoodDogTestDrive{
	
	public static void main (String[] args){
		GoodDog one = new GoodDog();
		GoodDog two = new GoodDog();

		one.setSize(70);
		two.setSize(8);

		System.out.println("Dog One: " + one.getSize());
		System.out.println("Dog Two: " + two.getSize());
		
		one.bark();
		two.bark();

	}
}