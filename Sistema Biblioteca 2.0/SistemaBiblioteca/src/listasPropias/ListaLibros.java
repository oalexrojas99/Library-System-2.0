/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listasPropias;

import entidades.Libro;

/**
 *
 * @author Alexander
 */
public class ListaLibros {
    
    private Nodo nodoPrincipal = null;
    private int longitud = 0;
    
    private class Nodo{
        public Libro libro;
        public Nodo sgte = null;
        
        public Nodo(Libro libro){
            this.libro = libro;
        }
    }
    
    public void agregarElementoSgte(Libro objLibro){
        Nodo nodo = new Nodo(objLibro);
        
        if(nodoPrincipal == null){
            nodoPrincipal = nodo;
        } else{
            Nodo puntero = nodoPrincipal;
            
            while(puntero.sgte != null){
                puntero = puntero.sgte;
            }
            
            puntero.sgte = nodo;
        }
        
        longitud++;
    }
    
    public void establecerElemento(int i, Libro objLibro){
        if(nodoPrincipal != null){
            Nodo puntero = nodoPrincipal;
            int contador = 0;
            
            while(contador < (i - 1)){
                puntero = puntero.sgte;
                contador++;
            }
            
            puntero.libro = objLibro;
        }
    }
    
    public Libro obtenerLibro(int i){
        if(nodoPrincipal == null){
            return null;
        } else{
            Nodo puntero = nodoPrincipal;
            int posicion = 0;
            
            while(posicion < i && puntero.sgte != null){
                puntero = puntero.sgte;
                posicion++;
            }
            
            if(posicion != i){
                return null;
            } else{
                return puntero.libro;
            }
        }
    }
    
    public int tamanio(){
        return longitud;
    }
    
}
