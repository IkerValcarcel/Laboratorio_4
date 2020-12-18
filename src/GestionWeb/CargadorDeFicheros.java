package GestionWeb;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import LinkedList.UnorderedListADT;
import LinkedList.IteratorCLL;

public class CargadorDeFicheros {

	private static CargadorDeFicheros miCargador = null;

	private CargadorDeFicheros() {}

	public static CargadorDeFicheros getCargadorDeFicheros() {
		if (miCargador == null) {
			miCargador = new CargadorDeFicheros();
		}
		return miCargador;
	}
	public void cargar(String wordsFileLocation, String webFileLocation, String linkFileLocation){
		//pre: entran tres Strings que contienen las direcciones validas de los ficheros que se van a cargar
		/*post: los ficheros index y pId-arcs-1N se cargan en forma de clases y se almacenan en un HashMap con los URL como keys
		y en un ArrayList que estan en la MAE CatalogoWebs y el fichero words se almacena en un HashMap en la MAE
		CatalogoPalabras usando las mismas palabras como keys*/
		ClassLoader classLoader = getClass().getClassLoader();

		URL archivoPalbras = classLoader.getResource(wordsFileLocation);
		URL archivoWebs = classLoader.getResource(webFileLocation);
		URL archivoEnlaces = classLoader.getResource(linkFileLocation);

		this.cargarPalabras(new File(archivoPalbras.getFile()));
		this.cargarWebs(new File(archivoWebs.getFile()));
		this.cargarEnlaces(new File(archivoEnlaces.getFile()));

	}
	private void cargarPalabras(File location){
		//pre: entra el fichero words
		//post: se carga el fichero en el HashMap de la MAE CatalogoPalabras
		try {
			Scanner entradaPalabras = new Scanner(new FileReader(location));
			String lineaPalabra;
			CatalogoPalabras catalogoPalabras = CatalogoPalabras.getCatalogoPalabras();

			while (entradaPalabras.hasNext()){
				lineaPalabra = entradaPalabras.nextLine();
				catalogoPalabras.anadir(new Palabra(lineaPalabra));
			}
			entradaPalabras.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	private void cargarWebs(File location){
		//precondicion: entra el fichero index
		/*postcondicion: se ha cargado el fichero de index en forma de clases y se ha almacenado en el HashMap y
		ArrayList de la MAE CatalogoWebs y ademas a añadido una lista de webs a cada palabra que contienen las
		webs cuyo URL contiene la palabra*/
		try{
			Scanner entradaIndex = new Scanner(location);

			String lineaIndex;
			String[] partesIndex;

			CatalogoWebs catalogoWebs=CatalogoWebs.getCatalogoWebs();
			Web web;

			CatalogoPalabras catalogoPalabras = CatalogoPalabras.getCatalogoPalabras();

			int longitudPalabra;
			String subPalabra;

			while (entradaIndex.hasNext()) {
				lineaIndex = entradaIndex.nextLine();

				partesIndex = lineaIndex.split(" ");

				web = new Web(partesIndex[1], partesIndex[0]);
				web.buscarPalabras();
				catalogoWebs.anadirWeb(web);
			}
			entradaIndex.close();

		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	private void cargarEnlaces(File location){
		//pre: entra el fichero pId-arcs-1-N
		/*post: Se ha añadido a cada web una lista de otras webs que, segun el fichero entrante, esta referenciada en
		la misma*/
		try {
			Scanner entradaEnlaces = new Scanner(new FileReader(location));

			String lineaEnlaces;
			String[] partesEnlaces;

			int indiceWeb = 0;

			CatalogoWebs catalogoWebs = CatalogoWebs.getCatalogoWebs();
			UnorderedListADT<Web> listaWebsEnlazadas;
			while (entradaEnlaces.hasNext()) {
				lineaEnlaces = entradaEnlaces.nextLine();
				try {
					partesEnlaces = lineaEnlaces.split(" --> ");
					partesEnlaces = partesEnlaces[1].split(" ");
				}catch (IndexOutOfBoundsException e){
					partesEnlaces = null;
				}
				listaWebsEnlazadas = new UnorderedListADT<Web>();
				if (partesEnlaces != null) {
					for (int i = 0; i < partesEnlaces.length; i++) {
						listaWebsEnlazadas.addToRear(catalogoWebs.buscarWebPorIndice(Integer.parseInt(partesEnlaces[i])));
					}
				}
				catalogoWebs.buscarWebPorIndice(indiceWeb).setEnlaces(listaWebsEnlazadas);
				indiceWeb++;
			}
			entradaEnlaces.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}