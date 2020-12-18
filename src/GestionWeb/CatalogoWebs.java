package GestionWeb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import LinkedList.UnorderedListADT;
import LinkedList.IteratorCLL;

public class CatalogoWebs {

    private ArrayList<Web> catalogo;
    private HashMap<String,Web> diccionario;
    private static CatalogoWebs miCatalogo;


    private CatalogoWebs(){
        this.catalogo = new ArrayList<Web>();
        this.diccionario = new HashMap<String, Web>();
    }

    public static CatalogoWebs getCatalogoWebs(){
        if (miCatalogo == null){
            miCatalogo = new CatalogoWebs();
        }
        return miCatalogo;
    }
    private Iterator<Web> getIterador(){
        return this.catalogo.iterator();
    }

    public void anadirWeb(Web web){
        //pre: entra un objeto de la clase web
        //post: se introduce en el HashMap diccionario y en el ArrayList catalogo
        this.diccionario.put(web.getUrl(),web);
        this.catalogo.add(web);
    }
    public Web buscarWebPorIndice(int pIndex){
        //pre: entra un int
        //post: se devuelve el objeto correspondiente a este indice si es que existe tambien en el HashMap
        Web web = this.catalogo.get(pIndex);
        if (this.diccionario.containsKey(web.getUrl())){
            return web;
        }else {
            return null;
        }
    }
    public Web buscarWebPorURL(String pNombre){
        //pre: entra una URL
        //post: devuelve el objeto cuya URL se ha introducido
        return this.diccionario.get(pNombre);
    }

    public UnorderedListADT<Web> getWebsEnlazadasDe(String pURL){
        //pre: entra una URL
        //post: devuelve la lista de webs del objeto cuya URL se ha introducido
        if(this.diccionario.containsKey(pURL)){
            if(this.buscarWebPorURL(pURL).getEnlaces()==null){
                System.out.println("Esta web no tiene enlaces.");
            }
            return this.buscarWebPorURL(pURL).getEnlaces();}
        else{
            System.out.println("La web no esta en la lista, introduzca otro URL");
            return null;
        }
        }

    public int getNumeroDeWebs(){
        //post: devuelve el numero de elementos que tiene el HashMap diccionario
        return this.diccionario.size();
    }

    public void crearWeb(){
        /*post: apartir de entradas que se hacen por teclado, donde se introduce el URL y los arcos de una web, se crea
        * un nuevo objeto de tipo Web que si su URL no esta repetido se añade a la lista*/
        Scanner sc = new Scanner(System.in);
        Web nuevaWeb;
        System.out.println("Introduzca el URL que tendra la nueva web:");
        String entradaTeclado = sc.nextLine();
        String url = entradaTeclado;
        if (!this.estaWeb(url)) {
            try{
                System.out.println("Introduzca los indices de los enlaces que direcciona esta web y cuando haya terminado introduzca un valor fuera del rango");
                System.out.println("El rango esta comprendido entre el cero y el " + (this.getNumeroDeWebs()-1));
                UnorderedListADT<Web> listaArcos = new UnorderedListADT<Web>();
                entradaTeclado = sc.nextLine();

                int arco = Integer.valueOf(entradaTeclado);
                while (arco >= 0 && arco < this.getNumeroDeWebs()) {
                    listaArcos.addToRear(this.buscarWebPorIndice(arco));
                    entradaTeclado = sc.nextLine();
                    arco = Integer.parseInt(entradaTeclado);
                }
                nuevaWeb = new Web(String.valueOf(this.getNumeroDeWebs()), url);
                nuevaWeb.setEnlaces(listaArcos);
                this.anadirWeb(nuevaWeb);
                System.out.println("La web ha sido anadida con exito. Su URL es " + nuevaWeb.getUrl() + " y su indice es " + nuevaWeb.getIndex());

            }catch (NumberFormatException e){
                System.out.println("La operacion ha sido cancelada ya que solo debe de introducir numeros en los enlaces.");
            }
        }else {
            System.out.println("La URL introduzida ya esta en uso.");
        }
    }

    public void eliminarWeb(){
        /*post: solo se eliminar la web del hashmap para mantener los indices del ArrayList y cuando se busque por
        indice si la URL no esta en el HashMap devuelve null.*/
        Scanner sc = new Scanner(System.in);
        String entradaTeclado;
        System.out.println("Introduzca el URL de la web que desee eliminar.");
        entradaTeclado = sc.nextLine();
        if (this.diccionario.containsKey(entradaTeclado)){
            this.diccionario.remove(entradaTeclado);
        }else{
            System.out.println("La URL introducida no existe.");
        }
    }
    public boolean estaWeb(String pURL){
        //post: si la key introducida esta en diccionario devuelve true y si no esta devuelve false.
        return this.diccionario.containsKey(pURL);
    }

    private static ArrayList<Web> quicksort(ArrayList<Web> listaWebs,int izq, int der){
        //pre: entra una lista y dos indices que separan el ArrayList
        //post: devuelven un ArrayList con la lista introducida ordenada
        Web pivote=listaWebs.get(izq);
        int i=izq;
        int j=der;
        while(i<j){
            while (listaWebs.get(i).getUrl().compareTo(pivote.getUrl())<0){
                i++;
            }
            while(listaWebs.get(j).getUrl().compareTo(pivote.getUrl())> 0){
                j--;
            }
            if(i<=j){
                listaWebs=swap(i,j,listaWebs);
                i++;
                j--;
            }
        }

        if(izq<j){
            quicksort(listaWebs, izq, j);
        }
        if (i<der){
            quicksort(listaWebs, i, der);
        }
        return listaWebs;
    }

    private static ArrayList<Web> swap(int i, int j, ArrayList<Web> listaWebs) {
       //pre: entran dos indices y una lista
       //post: los elementos de la lista que estan en los indices introducidos se intercambian
       Web aux= listaWebs.get(i);
       listaWebs.set(i,listaWebs.get(j));
       listaWebs.set(j,aux);
       return listaWebs;

    }
    public ArrayList ordenarWebs(){
        //post: devuelven un ArrayList con la lista de webs en diccionario ordenada
        ArrayList<Web> listaWebs=new ArrayList<>(this.diccionario.values());
        quicksort(listaWebs,0,listaWebs.size()-1);
        return listaWebs;
    }
    public void guardar(){
        //post: almacena todos los datos de CatalogoWebs en un nuevo fichero de texto
        ArrayList<Web> valuesList = new ArrayList<Web>(diccionario.values());
        Iterator<Web> itr = valuesList.iterator();
        Web web;

        //Se va  aguardar de la sigiente manera: INDICE PaginaWeb  --> ENLACES
        try {
            File Obj = new File("guardarWebs.txt");
            PrintWriter out = new PrintWriter("guardarWebs.txt");
            while(itr.hasNext()){
                web=itr.next();
                String Imprimir="";
                Imprimir=web.getIndex() + " " + web.getUrl() + " --> " + web.getEnlacesImpresos();
                out.println(Imprimir);
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException  i) {
            i.printStackTrace();
        }
    }

}