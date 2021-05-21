package fr.ul.miage.reseaux;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class Server {

	static int PORT = 80;
	static String webFolder = "C:\\WebRoot";
	String PROPERTIES_PATH;

	public Server(String pROPERTIES_PATH) {
		super();
		PROPERTIES_PATH = pROPERTIES_PATH;
	}

	public void runServer(){

		readProperties();

		// On commence par créer une socket sur le port d'écoute défini dans le fichier properties
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			System.out.println("écoute sur le port " + PORT + "...\n");

			/*
			 * On crée une socket pour chaque nouveau client
			 * On créer un thread pour chaque nouvelle connexion, afin de ne pas bloquer le thread principal
			 */
			while(true) {
				Socket clientSocket = serverSocket.accept();

				System.out.println("nouveau client connecté : " + clientSocket.getInetAddress().getHostAddress());

				Request request = new Request(clientSocket, webFolder);

				Thread thread = new Thread(request);

				thread.start();
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	//On lit les propriétes du fichier properties
	public void readProperties () {
		
		try {
			File obj = new File(PROPERTIES_PATH);
			Scanner myReader = new Scanner(obj);
			
			String[] properties;
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        properties = data.split(":", 2);
				
				switch (properties[0]) {
				// On récupère le port
				case "PORT" : 
					PORT = Integer.parseInt(properties[1].replaceFirst(" ", ""));
					break;

				// On récupère le dossier web où mettre les sites
				case "WebRoot" :
					webFolder = properties[1].replaceFirst(" ", "");
					break;
				}
		      }
		      myReader.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}


	}
}


