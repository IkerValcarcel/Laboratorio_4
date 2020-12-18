package GestionWeb;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long tiempo = System.nanoTime();

		String wordsfilelocation="words.txt";
		String webfilelocation ="index.txt";
		String linkfilelocation="pld-arcs-1-N.txt";

		CargadorDeFicheros.getCargadorDeFicheros().cargar(wordsfilelocation,webfilelocation,linkfilelocation);
		System.out.println(((System.nanoTime() - tiempo) * Math.pow(10,-9)) + " s");

		Menu.elegir();

	}

}