/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author Alexander
 */
public class Ejemplar {
    
    private int idejemplar;
    private Libro libro;
    private int estado;
    
    public Ejemplar(){
        libro = new Libro();
    }

    public void setIdejemplar(int idejemplar) {
        this.idejemplar = idejemplar;
    }

    public int getIdejemplar() {
        return idejemplar;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public String getEstado() {
        
        switch (estado) {
            case 0:
                return "No disponible";
            case 1:
                return "Disponible";
            default:
                return "";
        }
    }
}
