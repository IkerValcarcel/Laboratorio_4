package GestionWeb;

import java.util.ArrayList;
import java.util.HashMap;

public class CatalogoPalabras {

    private HashMap<String,Palabra> listaPalabras;
    private static CatalogoPalabras miCatalogo;

    private CatalogoPalabras(){
        this.listaPalabras = new HashMap<String, Palabra>();
    }

    public static CatalogoPalabras getCatalogoPalabras(){
        if (miCatalogo == null){
            miCatalogo = new CatalogoPalabras();
        }
        return miCatalogo;
    }
    public void anadir(Palabra palabra){
        //pre: entra un objeto no nulo de clase palabra
        //post: el objeto ha sido añadido al HashMap listaPalabras
        this.listaPalabras.put(palabra.getTexto(),palabra);
    }
    public boolean estaLaPalabra(String palabra){
        //pre: entra una String con la key de una palbra
        //post: se devuelve true si el objeto con la key introducida esta y false si no esta
        return this.listaPalabras.containsKey(palabra);
    }
    public Palabra buscarPalabra(String palabra){
        //pre: entra un String correspondiente a una key del HashMap
        //post: si la key esta se devuelve su value y si no esta se devuelve null
        if (this.listaPalabras.containsKey(palabra)){
            return this.listaPalabras.get(palabra);
        }
        else {
                System.out.println("Esa palabra no esta en el catalogo de palabras");
                return null;
        }
    }
    public ArrayList<Web> buscarWebsDeLaPalabra(String pPalabra){
        //pre: entra una palabra que corresponde a una key del HashMap
        /*post: si el key existe en el HashMap se devuelve la lista de webs asociada a la palabra buscada y s no esta se
        * devuelve null*/
        if (this.estaLaPalabra(pPalabra)){
            return this.buscarPalabra(pPalabra).getWebsReferenciadas();
        }else{
            System.out.println("La palabra que ha introducido es incorrecta");
            return null;
        }
    }
}
