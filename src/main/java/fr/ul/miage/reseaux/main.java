package fr.ul.miage.reseaux;

public class main {
	public static String PROPERTIES_PATH = "";

	public static void main(String[] args) {
		// TODO Auto-generated method stub


		boolean correctArgs = false;

		if(args.length <= 1) {
			System.err.println("Il manque l'argument du fichier properties : --properties [Path]");
		} else if (args.length >= 3) {
			System.err.println("Il y a trop d'arguments");
		} else {
			if(args[0].equalsIgnoreCase("--properties")) {
				System.out.println("Path du fichier properties : " + args[1]);
				PROPERTIES_PATH = args[1];
				correctArgs = true;
			} else {
				System.err.println("Le paramètre est incorrecte, il doit être : --properties [Path]");
			}
		}

		if(correctArgs) {
			Server server = new Server(PROPERTIES_PATH);
			server.runServer();
		}

	}

}
