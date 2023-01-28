
package entidades;

import java.util.GregorianCalendar;

public class Prestamo {
    
    private int id;
    private Estudiante estudiante;
    private Ejemplar ejemplar;
    private GregorianCalendar fechaPrestamo;
    private int num_dias_prestamo;
    private GregorianCalendar fechaFinalizacionPrestamo;
    private int estado;
    private String descripcion_estado_prestamo;
    

    public Prestamo() {
        estudiante = new Estudiante();
        ejemplar = new Ejemplar();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNum_dias_prestamo(int num_dias_prestamo) {
        this.num_dias_prestamo = num_dias_prestamo;
    }

    public int getNum_dias_prestamo() {
        return num_dias_prestamo;
    }

    public void setFechaPrestamo(GregorianCalendar fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }
    
    public String getFechaPrestamo() {
        
        String anio = fechaPrestamo.get(fechaPrestamo.YEAR) + "";
        
        /*if(fechaPrestamo.get(fechaPrestamo.MONTH) < 10){
            mes = "0" + fechaPrestamo.get(fechaPrestamo.MONTH);
        } else{
            mes = fechaPrestamo.get(fechaPrestamo.MONTH) + "";
        }*/
        String mes;
        
        if(fechaFinalizacionPrestamo.get(fechaFinalizacionPrestamo.MONTH) < 10){
            mes = "0" + (fechaFinalizacionPrestamo.get(fechaFinalizacionPrestamo.MONTH) + 1);
        } else{
            mes = (fechaFinalizacionPrestamo.get(fechaFinalizacionPrestamo.MONTH) + 1) + "";
        }
        
        String dia = fechaPrestamo.get(fechaPrestamo.DAY_OF_MONTH) + "";
        
        return dia + "-" + mes + "-" + anio;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public String getEstado() {
        
        switch (estado) {
            case 0:
                return "Finalizado";
            case 1:
                return "En vigencia";
            default:
                return "Fuera de plazo";
        }
    }

    public void setFechaFinalizacionPrestamo(GregorianCalendar fechaFinalizacionPrestamo) {
        this.fechaFinalizacionPrestamo = fechaFinalizacionPrestamo;
    }

    public String getFechaFinalizacionPrestamo() {
        String anio = fechaFinalizacionPrestamo.get(fechaFinalizacionPrestamo.YEAR) + "";
        
        String mes;
        
        if(fechaFinalizacionPrestamo.get(fechaFinalizacionPrestamo.MONTH) < 10){
            mes = "0" + (fechaFinalizacionPrestamo.get(fechaFinalizacionPrestamo.MONTH) + 1);
        } else{
            mes = (fechaFinalizacionPrestamo.get(fechaFinalizacionPrestamo.MONTH) + 1) + "";
        }
        
        String dia = fechaFinalizacionPrestamo.get(fechaFinalizacionPrestamo.DAY_OF_MONTH) + "";
        
        return dia + "-" + mes + "-" + anio;
    }

    public void setEjemplar(Ejemplar ejemplar) {
        this.ejemplar = ejemplar;
    }

    public Ejemplar getEjemplar() {
        return ejemplar;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setDescripcion_estado_prestamo(String descripcion_estado_prestamo) {
        this.descripcion_estado_prestamo = descripcion_estado_prestamo;
    }

    public String getDescripcion_estado_prestamo() {
        return descripcion_estado_prestamo;
    }
    
}
