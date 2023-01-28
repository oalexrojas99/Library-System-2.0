/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listasPropias;

import entidades.Estudiante;
import entidades.Tema;

/**
 *
 * @author Alexander
 */
public class ListaEstudiantes {
    
    private Nodo nodoPrincipal = null;
    private int longitud = 0;
    
    private class Nodo{
        public Estudiante estudiante;
        public Nodo sgte = null;
        
        public Nodo(Estudiante estudiante){
            this.estudiante = estudiante;
        }
    }
    
    public void agregarElementoSgte(Estudiante objEstudiante){
        Nodo nodo = new Nodo(objEstudiante);
        
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
    
    public void establecerElemento(int i, Estudiante objEstudiante){
        if(nodoPrincipal != null){
            Nodo puntero = nodoPrincipal;
            int contador = 0;
            
            while(contador < (i - 1)){
                puntero = puntero.sgte;
                contador++;
            }
            
            puntero.estudiante = objEstudiante;
        }
    }
    
    public Estudiante obtenerEstudiante(int i){
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
                return puntero.estudiante;
            }
        }
    }
    
    public int tamanio(){
        return longitud;
    }
    
}
