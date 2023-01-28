/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listasPropias;

import entidades.Tema;
import java.io.Serializable;

/**
 *
 * @author Alexander
 */
public class ListaTemas {
    
    private Nodo nodoPrincipal = null;
    private int longitud = 0;
    
    private class Nodo{
        public Tema tema;
        public Nodo sgte = null;
        
        public Nodo(Tema tema){
            this.tema = tema;
        }
    }
    
    public void agregarElementoSgte(Tema objTema){
        Nodo nodo = new Nodo(objTema);
        
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
    
    public void establecerElemento(int i, Tema objTema){
        if(nodoPrincipal != null){
            Nodo puntero = nodoPrincipal;
            int contador = 0;
            
            while(contador < (i - 1)){
                puntero = puntero.sgte;
                contador++;
            }
            
            puntero.tema = objTema;
        }
    }
    
    public Tema obtenerTema(int i){
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
                return puntero.tema;
            }
        }
    }
    
    public int tamanio(){
        return longitud;
    }
    
}
