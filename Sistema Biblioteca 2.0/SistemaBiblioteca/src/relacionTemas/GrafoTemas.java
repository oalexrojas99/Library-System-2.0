/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package relacionTemas;

import DAOs.TemaDAO;
import entidades.Tema;
import grafoNoDirigido.Arista;
import grafoNoDirigido.Grafo;
import grafoNoDirigido.Vertice;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import listasPropias.ListaTemas;

/**
 *
 * @author Alexander
 */
public class GrafoTemas implements Serializable{
    
    Grafo miGrafo;
    
    boolean sobreescribe = true;
    
    //Asignamos cada etiqueta en cada vértice
    public  ArrayList<Vertice> crearVertices(ArrayList<Tema> temas) throws SQLException{
        
        ArrayList<Vertice> vertices = new ArrayList<>();
        
        for(int i = 0; i < temas.size(); i++)
        {
            vertices.add(new Vertice(temas.get(i).getDescripcion()));
            //System.out.println(temas.obtenerTema(i).getDescripcion());	    	
        }
        
        return vertices;
    }
    
    public GrafoTemas(ArrayList<Vertice> vertices){
        miGrafo = new Grafo(vertices);
    }
    
    /*Agregar tema*/
    public void crearVertice(String tema){
        
        Vertice v = new Vertice(tema);
        
        miGrafo.insertarVertice(v, false);
    }
    
    /*Relacionar temas*/
    public void relacionarTemas(String tema1, String tema2){
        
        Vertice v1 = miGrafo.getVertice(tema1);
        Vertice v2 = miGrafo.getVertice(tema2);
        
        miGrafo.insertarArista(v1, v2);
    }
    
    /*Quitar la relacion entre 2 temas*/
    public void desvincularTemas(String tema1, String tema2){
        
        Vertice v1 = miGrafo.getVertice(tema1);
        Vertice v2 = miGrafo.getVertice(tema2);
        
       for(Arista arista : v1.getVecinos())
	    {
		if(arista.getVertice2().getEtiqueta().equals(tema1))
		    miGrafo.eliminarArista(arista);
	    }

	for(Arista arista : v2.getVecinos())
	    {
		if(arista.getVertice2().getEtiqueta().equals(tema2))
		    miGrafo.eliminarArista(arista);
	    }
    }
    
    public ArrayList<String> obtenerTemasRelacionados(String tema){
        
        ArrayList<String> temas = new ArrayList<>();
        Vertice v = miGrafo.getVertice(tema);
        
        for( int k = 0; k < v.getContarVecinos(); k++){
            
            String t1 = v.getVecino(k).getVertice1().getEtiqueta();
            String t2 = v.getVecino(k).getVertice2().getEtiqueta();
            
            if(!temas.contains(t1)){
                temas.add(t1);
            }
            
            if(!temas.contains(t2)){
                temas.add(t2);
            }
            
            temas.remove(tema);
                
        }
        
        return temas;
    }
    
    /*Serializar clase Grafo*/
    public void serializarGrafo(GrafoTemas grafo){
        try {
            ObjectOutputStream escribiendo_fichero = new ObjectOutputStream(new FileOutputStream("../SistemaBiblioteca_ExpoFinal/grafo.dat"));
            escribiendo_fichero.writeObject(grafo);
            escribiendo_fichero.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /*Deserializar clase Grafo*/
    public GrafoTemas deserializarGrafo(){
        
        GrafoTemas grafo = null;
                
        try {
            ObjectInputStream recuperando_fichero = new ObjectInputStream(new FileInputStream("../SistemaBiblioteca_ExpoFinal/grafo.dat"));
            grafo = (GrafoTemas) recuperando_fichero.readObject();
            recuperando_fichero.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return grafo;
    }
}
