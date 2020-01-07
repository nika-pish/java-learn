import ru.stqa.learn.Point;

public class MyFirstProgram {
	public static void main(String[] args) {
		System.out.println("Hello, World");


		Point p1 = new Point(3, 5);
		Point p2 = new Point(6, 8);


		System.out.println("Расстояние между двумя точками = "+ p1.distance(p2));

	}


}

