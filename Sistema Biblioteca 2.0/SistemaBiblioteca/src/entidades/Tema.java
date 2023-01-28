
package entidades;

import java.io.Serializable;

public class Tema implements Serializable{
    
    private int id;
    private String descripcion;

    public Tema() {
        
    } 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
