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
import java.util.Scanner;
import java.util.stream.Stream;

public class Server {

	static int PORT = 80;
	static String webFolder;

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

			InputStream is = getClass().getResourceAsStream("/properties.txt");
			InputStreamReader steamReader = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(steamReader);

			String line;
			String[] properties;

			while ((line = br.readLine()) != null) {
				properties = line.split(":", 2);
				
				switch (properties[0]) {
				// On récupère le port
				case "PORT" : 
					PORT = Integer.parseInt(properties[1].replaceFirst(" ", ""));
					break;

				// On récupère le dossier web où mettre les sites
				case "Web" :
					webFolder = properties[1].replaceFirst(" ", "");
					break;
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}


