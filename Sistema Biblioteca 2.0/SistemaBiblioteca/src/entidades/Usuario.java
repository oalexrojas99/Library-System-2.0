
package entidades;

public class Usuario{
    private int id;
    private String email;
    private String nombreusuario;
    private String contrasenia;
    private int estado;

    public Usuario(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    
    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getContrasenia() {
        return contrasenia;
    }
    
    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public String getEstado() {
        
        switch (estado) {
            case 0:
                return "Activo";
            case 1:
                return "Inactivo";
            default:
                return "";
        }
    }
}
