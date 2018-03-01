package hashcode;

import hashcode.dumb.DumbAlgo;

public class Main {

	public static void main(String[] args) {
		String[] files = { "a_example"/*, "b_should_be_easy", "c_no_hurry", "d_metropolis", "e_high_bonus"*/ };
		for (String filename : files) {
			processOneFile(filename);
		}
	}

	private static void processOneFile(String filename) {
		Situation situation = Reader.readFile(filename);
		IAlgo algo = new DumbAlgo(); // TODO faudrait écrire un vrai algo pour que ça marche
		Resultat resultat = algo.trouveMeilleurResultat(situation);
		Printer.printFile(filename, resultat);
	}

}
