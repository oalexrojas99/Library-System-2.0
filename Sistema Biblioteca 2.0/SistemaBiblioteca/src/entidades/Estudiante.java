
package entidades;

import com.mysql.cj.xdevapi.Type;

public class Estudiante {
    private int id;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private int estado;

    public Estudiante() {
        
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNombres() {
        return nombres;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }
    
    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public String getEstado() {
        
        switch (estado) {
            case 0:
                return "Inactivo";
            case 1:
                return "Activo";
            default:
                return "";
        }
    }
    
    @Override
    public String toString(){
        return String.format("{ID: %s, Nombres: %s, Apellido paterno: %s, Apellido materno: %s, Estado: %s}", 
                getId(), getNombres(), getApellidoPaterno(), getApellidoMaterno(), getEstado());
    }
}
