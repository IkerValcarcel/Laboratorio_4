package grafo;

import GestionWeb.CatalogoWebs;
import GestionWeb.Web;
import LinkedList.IteratorCLL;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Graph {
	
      HashMap<String, Integer> th;
      String[] keys;
      ArrayList<Integer>[] adjList;
      HashMap<String,Double> pageRank;
      ArrayList<String>[] adjListInvertida;
	
	public void crearGrafo(CatalogoWebs lista){
		// Post: crea el grafo desde la lista de webs
		//       Los nodos son nombres de webs
		
            // Paso 1: llenar “th”
            // COMPLETAR CÓDIGO
		th = new HashMap<String, Integer>();
		for(int i = 0; i < lista.getNumeroDeWebs(); i++){
			th.put(lista.buscarWebPorIndice(i).getUrl(),i);
		}

            // Paso 2: llenar “keys”
		keys = new String[th.size()];
		for (String k: th.keySet()) keys[th.get(k)] = k;

            // Paso 3: llenar “adjList”
            // COMPLETAR CÓDIGO
		adjList = new ArrayList[lista.getNumeroDeWebs()];
		for (int i = 0; i < adjList.length; i++){
			adjList[i] = new ArrayList<Integer>();
			IteratorCLL<Web> itr = lista.buscarWebPorIndice(i).getEnlaces().iterator();
			while (itr.hasNext()){
				adjList[i].add(Integer.parseInt(itr.next().data().getIndex()));
			}
		}
		//Creacion del HashMap pageRank y asignacion de sus valores iniciales (1/numero de paginas web)
		pageRank = new HashMap<String,Double>();
		for (String a: th.keySet()) {
			pageRank.put(a, (double) (1/th.size()));
		}

		//Creacion de una lista de adyacencia invertida que facilitara el algoritmo setPageRank
		adjListInvertida = invertirAdjList();
	}
	
	public void print(){
	   for (int i = 0; i < adjList.length; i++){
		System.out.print("Element: " + i + " " + keys[i] + " --> ");
		for (int k: adjList[i])  System.out.print(keys[k] + " ### ");
		
		System.out.println();
	   }
	}
	
	public boolean estanConectados(String a1, String a2){
		boolean enc = false;
		if (th.containsKey(a1) && th.containsKey(a2)) {
			int pos1 = th.get(a1);
			int pos2 = th.get(a2);
			boolean[] examinados = new boolean[th.size()];
			Stack<Integer> porExaminar = new Stack<Integer>();
			porExaminar.push(pos1);
			while (!enc && !porExaminar.isEmpty()) {
				int examinandose = porExaminar.pop();
				if (examinandose == pos2) {
					enc = true;
				} else {
					examinados[examinandose] = true;
					for (Integer enlace : adjList[examinandose]) {
						if (!examinados[enlace])
							porExaminar.push(enlace);
					}
				}
			}
		}
		return enc;

	}

	public ArrayList<String> comoEstanConectados(String a1, String a2){
		boolean enc = false;
		ArrayList<String> camino = new ArrayList<String>();
		if (th.containsKey(a1) && th.containsKey(a2)) {
			int pos1 = th.get(a1);
			int pos2 = th.get(a2);
			boolean[] examinados = new boolean[th.size()];
			Stack<Integer> porExaminar = new Stack<Integer>();
			Integer[] trayecto = new Integer[th.size()];
			porExaminar.push(pos1);
			while (!enc && !porExaminar.isEmpty()) {
				int examinandose = porExaminar.pop();
				if (examinandose == pos2) {
					enc = true;
				} else {
					examinados[examinandose] = true;
					for (Integer enlace : adjList[examinandose]) {
						if (!examinados[enlace]) {
							porExaminar.push(enlace);
							trayecto[enlace] = examinandose;
						}
					}
				}
			}
			if (enc) {
				Stack<Integer> invertir = new Stack<Integer>();
				int aux = pos2;
				invertir.push(pos2);
				while (aux != pos1) {
					aux = trayecto[aux];
					invertir.push(aux);
				}
				invertir.push(pos1);
				while (!invertir.isEmpty()) {
					camino.add(keys[invertir.pop()]);
				}
			}
		}
		return camino;
	}

	public void setPageRank(){
		double d = 0.85;
		double n = th.size();
		double umbral = 0.0001;
		double sumDiferencias;
		HashMap<String,Double> nuevoPR;
		do {
			sumDiferencias = 0;
			nuevoPR = new HashMap<String, Double>();
			for (String url : pageRank.keySet()) {
				double sumatorio = 0;
				for (String enlace : adjListInvertida[th.get(url)]) {
					sumatorio = sumatorio + pageRank.get(enlace) / adjList[th.get(enlace)].size();
				}
				nuevoPR.put(url, ((1 - d) / n) + d * sumatorio);
				sumDiferencias = sumDiferencias + abs(pageRank.get(url) - nuevoPR.get(url));
			}
			pageRank = nuevoPR;
		}while (sumDiferencias>umbral);

	}

	private ArrayList<String>[] invertirAdjList(){
		ArrayList<String>[] invertido = new ArrayList[adjList.length];
		for (int i = 0; i < invertido.length; i++) {
			invertido[i] = new ArrayList<String>();
		}

		for (int i = 0; i < adjList.length; i++) {
			for(Integer a: adjList[i]) {
				invertido[a].add(keys[i]);
			}
		}
		return invertido;
	}

	private double abs(double x){
		if (x<0) x=-x;
		return x;
	}

	public ArrayList<Par> buscar(String palabraClave){
		Par nuevo;
		ArrayList<Par> websEncontradas = new ArrayList<Par>();
		for (String web: pageRank.keySet()){
			if (web.contains(palabraClave)){
				nuevo = new Par(web, pageRank.get(web));
				websEncontradas.add(nuevo);
			}
		}
		quicksort(websEncontradas,0,websEncontradas.size()-1);
		return websEncontradas;
	}
	public ArrayList<Par> buscar(String palabraClave1,String palabraClave2){
		Par nuevo;
		ArrayList<Par> websEncontradas = new ArrayList<Par>();
		for (String web: pageRank.keySet()){
			if (web.contains(palabraClave1) && web.contains(palabraClave2)){
				nuevo = new Par(web, pageRank.get(web));
				websEncontradas.add(nuevo);
			}
		}
		quicksort(websEncontradas,0,websEncontradas.size()-1);
		return websEncontradas;
	}

	private static void quicksort(ArrayList<Par> lista,int izq, int der){
		//pre: entra una lista y dos indices que separan el ArrayList
		//post: devuelven un ArrayList con la lista introducida ordenada
		Par pivote=lista.get(izq);
		int i=izq;
		int j=der;
		while(i<j){
			while (lista.get(i).pageRank<pivote.pageRank){
				i++;
			}
			while(lista.get(j).pageRank>pivote.pageRank){
				j--;
			}
			if(i<=j){
				swap(i,j,lista);
				i++;
				j--;
			}
		}

		if(izq<j){
			quicksort(lista, izq, j);
		}
		if (i<der){
			quicksort(lista, i, der);
		}
	}
	private static void swap(int i, int j, ArrayList<Par> lista) {
		//pre: entran dos indices y una lista
		//post: los elementos de la lista que estan en los indices introducidos se intercambian
		Par aux= lista.get(i);
		lista.set(i,lista.get(j));
		lista.set(j,aux);
	}



}
