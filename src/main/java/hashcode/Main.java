package hashcode;

public class Main {

	public static void main(String[] args) {
		String[] files = { "example", "small", "medium", "big" };
		for (String filename : files) {
			Reader.readFile(filename);

			Printer.printFile(filename);
		}
	}

}
