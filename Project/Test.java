public class Test {
	public static void main(String[] args) {
		Course[] course = new Course[2];
		Lecturer[] lecturer = new Lecturer[2];
		Venue[] venue = new Venue[2];
		Schedule[] schedule = new Schedule[2];
		Section[] section = new Section[2];

		course[0] = new Course("CSC1100", "Elements of Programming", 3);
		lecturer[0] = new Lecturer("Dr Suriani");
		venue[0] = new Venue("Lab 6");
		schedule[0] = new Schedule(1);
		section[0] = new Section(course[0], lecturer[0], venue[0], schedule[0], 1, 30);
		
		System.out.println(section[1]);
	}
}