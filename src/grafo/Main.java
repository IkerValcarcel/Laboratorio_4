package grafo;

import GestionWeb.CargadorDeFicheros;
import GestionWeb.CatalogoWebs;
import GestionWeb.Web;
import LinkedList.UnorderedListADT;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //Con lista entera de webs
        CargadorDeFicheros.getCargadorDeFicheros().cargar("words.txt","index.txt","pld-arcs-1-N.txt");
        CatalogoWebs listaWebs = CatalogoWebs.getCatalogoWebs();
        //Con lista pequeña de pruebas
        /*
        CatalogoWebs listaWebs = CatalogoWebs.getCatalogoWebs();
        listaWebs.anadirWeb(new Web("0", "hello.com"));
        listaWebs.anadirWeb(new Web("1", "world.com"));
        listaWebs.anadirWeb(new Web("2", "hello.net"));
        listaWebs.anadirWeb(new Web("3", "world.net"));
        listaWebs.anadirWeb(new Web("4", "hello.org"));
        listaWebs.anadirWeb(new Web("5", "world.org"));
        listaWebs.anadirWeb(new Web("6", "hello.eus"));
        listaWebs.anadirWeb(new Web("7", "world.eus"));


        UnorderedListADT<Web> arcos = new UnorderedListADT<Web>();
        arcos.addToRear(listaWebs.buscarWebPorIndice(1));
        arcos.addToRear(listaWebs.buscarWebPorIndice(3));
        arcos.addToRear(listaWebs.buscarWebPorIndice(5));
        arcos.addToRear(listaWebs.buscarWebPorIndice(7));
        listaWebs.buscarWebPorIndice(0).setEnlaces(arcos);

        arcos = new UnorderedListADT<Web>();
        arcos.addToRear(listaWebs.buscarWebPorIndice(2));
        listaWebs.buscarWebPorIndice(1).setEnlaces(arcos);

        arcos = new UnorderedListADT<Web>();
        listaWebs.buscarWebPorIndice(2).setEnlaces(arcos);

        arcos = new UnorderedListADT<Web>();
        arcos.addToRear(listaWebs.buscarWebPorIndice(2));
        arcos.addToRear(listaWebs.buscarWebPorIndice(4));
        arcos.addToRear(listaWebs.buscarWebPorIndice(6));
        arcos.addToRear(listaWebs.buscarWebPorIndice(7));
        listaWebs.buscarWebPorIndice(3).setEnlaces(arcos);

        arcos = new UnorderedListADT<Web>();
        arcos.addToRear(listaWebs.buscarWebPorIndice(0));
        arcos.addToRear(listaWebs.buscarWebPorIndice(1));
        arcos.addToRear(listaWebs.buscarWebPorIndice(2));
        arcos.addToRear(listaWebs.buscarWebPorIndice(3));
        arcos.addToRear(listaWebs.buscarWebPorIndice(5));
        listaWebs.buscarWebPorIndice(4).setEnlaces(arcos);

        arcos = new UnorderedListADT<Web>();
        arcos.addToRear(listaWebs.buscarWebPorIndice(0));
        arcos.addToRear(listaWebs.buscarWebPorIndice(1));
        arcos.addToRear(listaWebs.buscarWebPorIndice(2));
        arcos.addToRear(listaWebs.buscarWebPorIndice(3));
        arcos.addToRear(listaWebs.buscarWebPorIndice(6));
        arcos.addToRear(listaWebs.buscarWebPorIndice(7));
        listaWebs.buscarWebPorIndice(5).setEnlaces(arcos);

        arcos = new UnorderedListADT<Web>();
        arcos.addToRear(listaWebs.buscarWebPorIndice(2));
        arcos.addToRear(listaWebs.buscarWebPorIndice(3));
        arcos.addToRear(listaWebs.buscarWebPorIndice(7));
        listaWebs.buscarWebPorIndice(6).setEnlaces(arcos);

        arcos = new UnorderedListADT<Web>();
        listaWebs.buscarWebPorIndice(7).setEnlaces(arcos);

        */


        Graph grafo = new Graph();
        grafo.crearGrafo(listaWebs);

        grafo.setPageRank();

        for (String a : grafo.pageRank.keySet()) System.out.println(a + " " + grafo.pageRank.get(a));
        try {
            File Obj = new File("resultadoPageRank.txt");
            PrintWriter out = new PrintWriter("guardarWebs.txt");
            for (String a : grafo.pageRank.keySet()){
                String imprimir=a + " " + grafo.pageRank.get(a);
                out.println(imprimir);
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }

        System.out.println();
        System.out.println("buscar");
        ArrayList<Par> buscados = grafo.buscar("world");
        for (int i = 0; i < buscados.size(); i++) {
            System.out.println("URL: " + buscados.get(i).web + " PageRank: " + buscados.get(i).pageRank);
        }

        System.out.println();
        System.out.println("buscar");
        buscados = grafo.buscar("hello");
        for (int i = 0; i < buscados.size(); i++) {
            System.out.println("URL: " + buscados.get(i).web + " PageRank: " + buscados.get(i).pageRank);
        }

        System.out.println();
        System.out.println("buscar");
        buscados = grafo.buscar(".net");
        for (int i = 0; i < buscados.size(); i++) {
            System.out.println("URL: " + buscados.get(i).web + " PageRank: " + buscados.get(i).pageRank);
        }

    }
}
