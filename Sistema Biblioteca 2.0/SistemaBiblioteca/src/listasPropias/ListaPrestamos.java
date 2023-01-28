/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listasPropias;

import entidades.Prestamo;
import entidades.Tema;

/**
 *
 * @author Alexander
 */
public class ListaPrestamos {
    
    private Nodo nodoPrincipal = null;
    private int longitud = 0;
    
    private class Nodo{
        public Prestamo prestamo;
        public Nodo sgte = null;
        
        public Nodo(Prestamo prestamo){
            this.prestamo = prestamo;
        }
    }
    
    public void agregarElementoSgte(Prestamo objPrestamo){
        Nodo nodo = new Nodo(objPrestamo);
        
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
    
    public void establecerElemento(int i, Prestamo objPrestamo){
        if(nodoPrincipal != null){
            Nodo puntero = nodoPrincipal;
            int contador = 0;
            
            while(contador < (i - 1)){
                puntero = puntero.sgte;
                contador++;
            }
            
            puntero.prestamo = objPrestamo;
        }
    }
    
    public Prestamo obtenerPrestamo(int i){
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
                return puntero.prestamo;
            }
        }
    }
    
    public int tamanio(){
        return longitud;
    }
    
}
