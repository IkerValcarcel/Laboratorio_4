package GestionWeb;

import java.util.ArrayList;

public class Palabra {

    private String texto;
    private ArrayList<Web> websReferenciadas;

    public Palabra(String pTexto){
        this.texto = pTexto;
        this.websReferenciadas = new ArrayList<Web>();
    }
    public String getTexto(){
        //post: devuelve el texto de la palabra
        return this.texto;
    }
    public void anadir(Web web){
        //pre: entra un objeto de clase Web no nulo
        //post: se añade al ArrayList websReferenciadas
        this.websReferenciadas.add(web);
    }
    public ArrayList getWebsReferenciadas(){
        //post: devuelve el ArrayList websReferenciadas de la Palabra
        return this.websReferenciadas;
    }

}
