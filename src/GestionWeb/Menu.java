package GestionWeb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import LinkedList.Node;
import LinkedList.UnorderedListADT;
import LinkedList.IteratorCLL;

public class Menu {

    public static void elegir(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el numero correspondiente a la accion a realizar");
        System.out.println("Introduzca '1' si desea buscar por URL ");
        System.out.println("Introduzca '2' si desea buscar por indice:");
        System.out.println("Introduzca '3' si desea añadir una nueva web");
        System.out.println("Introduzca '4' si desea eliminar una web ");
        System.out.println("Introduzca '5' si desea guardar los datos en un archivo");
        System.out.println("Introduzca '6' si desea obtener las webs enlazadas de una URL que introduzca.");
        System.out.println("Introduzca '7' si desea ordenar las webs alfabeticamente.");
        System.out.println("Introduzca '8' si desea obtener las webs que contienen una palabra clave en su URL.");
        System.out.println("Si desea salir del programa introduzca cualquier otra cosa");
        String palabra = sc.nextLine();
        if (palabra.equals("1")){
            buscarPorURL();
        }else if (palabra.equals("2")){
            buscarPorIndex();
        }else if (palabra.equals("3")) {
            nuevaWeb();
        }else if (palabra.equals("4")) {
            eliminarWeb();
        }else if (palabra.equals("5")) {
            guardarWebs();
        }else if (palabra.equals("6")){
            buscarWebsEnlazadas();
        }else if (palabra.equals("7")){
            ordenarWebs();
        }else if (palabra.equals("8")){
            websQueContienen();
        }

    }
    private static void buscarPorURL(){
        //pre: se introduce un URL por teclado
        //post: si el URL corresponde a una web de CatalogoWebs imprime los datos de la web
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el URL de la web que desea buscar: ");
        String entradaTeclado = sc.nextLine();
        if (CatalogoWebs.getCatalogoWebs().estaWeb(entradaTeclado)) {
            System.out.println("El indice de la web que ha buscado es " + CatalogoWebs.getCatalogoWebs().buscarWebPorURL(entradaTeclado).getIndex());
        }else{
            System.out.println("La web no esta.");
        }
        elegir();
    }
    private static void buscarPorIndex(){
        //pre: se introduce un indice por teclado
        //post: si el indice corresponde a alguna web de la lista se imprime los datos de la web
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el indice que desea buscar:");
        String index = sc.nextLine();
        try{
            Web web = CatalogoWebs.getCatalogoWebs().buscarWebPorIndice(Integer.parseInt(index));
            System.out.println("La URL correspondiente al indice es: " + web.getUrl());
        }catch (IndexOutOfBoundsException | NumberFormatException | NullPointerException f){
            System.out.println("El indice no es valido o el indice ha sido borrado.");
        }
        elegir();
    }

    private static void nuevaWeb(){
        //post: se crea una web a partir de los datos introducidos por el usuario y se añade a CatalogoWeb
        CatalogoWebs.getCatalogoWebs().crearWeb();
        elegir();
    }

    private static void eliminarWeb(){
        //post: se elimina la web con el mismo URL que el introducido por el usuario.
        CatalogoWebs.getCatalogoWebs().eliminarWeb();
        elegir();
    }
    private static void guardarWebs(){
        //post: se guarda la informacion de CatalogoWebs en un nuevo fichero
        CatalogoWebs.getCatalogoWebs().guardar();
        elegir();
    }
    private static void buscarWebsEnlazadas(){
        //pre: el usuario introduce un URL
        //post: si el URL exite se devuelven todas las webs enlazadas correspondientes
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el URL de una web para obtener sus webs enlazadas");
        String url = sc.nextLine();
        UnorderedListADT<Web> listaEnlazadas= CatalogoWebs.getCatalogoWebs().getWebsEnlazadasDe(url);
        if (listaEnlazadas!=null){
            IteratorCLL<Web> iterator=listaEnlazadas.iterator();
            Node<Web> web;
            while(iterator.hasNext()){
                web= iterator.next();
                System.out.print("| " + web.data().getIndex() + web.data().getUrl() + " ");
            }
            System.out.println("|");
        }
        elegir();
    }
    private static void ordenarWebs() {
        //Se devuelve una lista ordenada de todas las webs del catalogo
        ArrayList<Web> listaWebsOrdenadas = CatalogoWebs.getCatalogoWebs().ordenarWebs();
        Iterator<Web> itr = listaWebsOrdenadas.iterator();
        Web web;
        while (itr.hasNext()){
            web = itr.next();
            System.out.println(web.getIndex() + " " + web.getUrl());
        }
        elegir();
    }
    private static void websQueContienen() {
        //pre: el usuario introduce una palabra que este en CatalogoPalabras
        //post: devuelve todas las webs cuya URL contenga esa palabra
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca una palabra para obtener las webs que la contienen");
        String palabra = sc.nextLine();
        ArrayList<Web> listaWebsDePalabra = CatalogoPalabras.getCatalogoPalabras().buscarWebsDeLaPalabra(palabra);
        if(listaWebsDePalabra!=null){
            Iterator<Web> iterator=listaWebsDePalabra.iterator();
            Web web;
            while(iterator.hasNext()){
                web= iterator.next();
                System.out.print( "| " + web.getIndex() + " " + web.getUrl() + " ");
            }
            System.out.println("|");
        }
            else {
                System.out.println("No contiene webs referenciadas");
            }
        elegir();
    }
}
