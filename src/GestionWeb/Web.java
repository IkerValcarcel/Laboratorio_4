package GestionWeb;

import java.util.ArrayList;
import java.util.Iterator;
import LinkedList.UnorderedListADT;
import LinkedList.IteratorCLL;

public class Web {

	private String index;
	private String url;
	private UnorderedListADT<Web> enlaces;
	
	public Web(String pIndex, String pUrl) {
		this.index = pIndex;
		this.url = pUrl;
		this.enlaces = null;
	}

	public void setEnlaces(UnorderedListADT<Web> pEnlaces){
		//pre: entra una lista de enlaces
		//post: la lista se le asigna al atributo enlaces.
		this.enlaces = pEnlaces;
	}
	public String getIndex() {
		//post: devuelve el indice de la Web
		return this.index;
	}
	public String getUrl() {
		//post: devuelve la URL de la Web
		return this.url;
	}
	public UnorderedListADT<Web> getEnlaces() {
		//post: devuelve la lista de los arcos
		return this.enlaces;
	}
	public void buscarPalabras(){
		/*post: apartir del URL del objeto de tipo web se busca que palabras estan contenidas en el URL y si una palabra
		es contenida en el URL se añade la web a la lista de webs de esa palabra*/
		int longitudPalabra = this.url.length();
		String subPalabra;
		CatalogoPalabras catalogoPalabras = CatalogoPalabras.getCatalogoPalabras();
		if (longitudPalabra >= 3){
			for (int i = 0; i<longitudPalabra-2; i++){
				for (int j = i+2; j<longitudPalabra; j++){
					subPalabra =this.getUrl().substring(i,j);
					if (catalogoPalabras.estaLaPalabra(subPalabra)){
						catalogoPalabras.buscarPalabra(subPalabra).anadir(this);
					}
				}
			}
		}
	}
	public String getEnlacesImpresos(){
		//post: pone en forma de String todos los datos de la Web
		String enlacesImpresos = "";
		if (!(this.enlaces == null)) {
			Iterator<Web> itr = this.enlaces.iterator();
			while (itr.hasNext()){
				enlacesImpresos = enlacesImpresos + itr.next().getIndex() + " ";
			}
		}
		return enlacesImpresos;
	}
}
