package hashcode;

import hashcode.dumb.DumbAlgo;
import hashcode.dumb.NoHurryAlgo;

public class Main {

	public static void main(String[] args) {
		String[] files = { "a_example", "b_should_be_easy", "c_no_hurry", "d_metropolis", "e_high_bonus" };
		for (String filename : files) {
			processOneFile(filename);
		}
	}

	private static void processOneFile(String filename) {
		Situation situation = Reader.readFile(filename);
		IAlgo algo = new DumbAlgo();
		if (filename.equals("c_no_hurry")) {
			algo = new NoHurryAlgo();
		}
		Resultat resultat = algo.trouveMeilleurResultat(situation);
		Printer.printFile(filename, resultat);
	}

}
