
package DAOs;

import entidades.Libro;
import entidades.Prestamo;
import excepcionesPropias.ExcepcionPropia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import jdbc.ConexionBD;
import listasPropias.ListaLibros;
import listasPropias.ListaPrestamos;

public class PrestamoDAO {
    
    Connection conn;
    
    public PrestamoDAO(){
        /*Se crea la conexión con la BD cuando se realiza una instancia de la clase PrestamoDAO*/
        conn = ConexionBD.conectarMySQL();
    }
    
    public void registrar(Prestamo objPrestamo) throws ExcepcionPropia, SQLException{
        //Estableciendo la sentencia de inserción SQL
        String sql = "INSERT INTO prestamo (idestudiante, idejemplar, fechaprestamo, numdiasprestamo) VALUES (?, ?, CURDATE(), ?);";
        PreparedStatement pstm_insert = conn.prepareStatement(sql);
        
        //Asignando parámetros de la sentencia
        pstm_insert.setInt(1, objPrestamo.getEstudiante().getId());
        pstm_insert.setInt(2, objPrestamo.getEjemplar().getIdejemplar());
        pstm_insert.setInt(3, objPrestamo.getNum_dias_prestamo());
        
        //Manipulando, finalmente, la base de datos
        pstm_insert.executeUpdate();
    }
    
    public void modificarEstadoPrestamo(int idPrestamo) throws ExcepcionPropia, SQLException{
        //Estableciendo la sentencia de inserción SQL
        String sql = "UPDATE prestamo SET estadoprestamo = 0 WHERE idprestamo = ?";
        PreparedStatement pstm_insert = conn.prepareStatement(sql);
        
        //Asignando parámetros de la sentencia
        pstm_insert.setInt(1, idPrestamo);
        
        //Manipulando, finalmente, la base de datos
        pstm_insert.executeUpdate();
    }
    
    public ArrayList<Prestamo> obtenerDetallePrestamos() throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        
        Statement stm = conn.createStatement();
        String sql = "SELECT * FROM v_detalle_prestamos;";
        
        //Recuperando clientes
        ResultSet rs = stm.executeQuery(sql);
        Prestamo objPrestamo;
        ArrayList<Prestamo> listaPrestamos = new ArrayList<>();
        
        while(rs.next()){
            objPrestamo = new Prestamo();
            
            objPrestamo.setId(rs.getInt(17));
            objPrestamo.getEstudiante().setNombres(rs.getString(13));
            objPrestamo.getEstudiante().setApellidoPaterno(rs.getString(14));
            objPrestamo.getEstudiante().setApellidoMaterno(rs.getString(15));
            objPrestamo.getEjemplar().getLibro().setTitulo(rs.getString(5));
            objPrestamo.getEjemplar().setIdejemplar(rs.getInt(1));
            objPrestamo.setFechaPrestamo(castear_a_GregorianCalendar(rs.getString(18)));
            objPrestamo.setFechaFinalizacionPrestamo(castear_a_GregorianCalendar(rs.getString(21)));
            objPrestamo.setDescripcion_estado_prestamo(rs.getString(22));
            
            listaPrestamos.add(objPrestamo);
        }
        
        return listaPrestamos;
    }
    
    public ArrayList<Prestamo> obtenerPrestamosXIdEstudiante(int idEstudiante) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT * FROM v_detalle_prestamos WHERE idestudiante = ?;";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, idEstudiante);
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        Prestamo objPrestamo;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objPrestamo = new Prestamo();
            objPrestamo.setId(rs.getInt(17));
            objPrestamo.getEstudiante().setNombres(rs.getString(13));
            objPrestamo.getEstudiante().setApellidoPaterno(rs.getString(14));
            objPrestamo.getEstudiante().setApellidoMaterno(rs.getString(15));
            objPrestamo.getEjemplar().getLibro().setTitulo(rs.getString(5));
            objPrestamo.getEjemplar().setIdejemplar(rs.getInt(1));
            objPrestamo.setFechaPrestamo(castear_a_GregorianCalendar(rs.getString(18)));
            objPrestamo.setFechaFinalizacionPrestamo(castear_a_GregorianCalendar(rs.getString(21)));
            objPrestamo.setDescripcion_estado_prestamo(rs.getString(22));
            
            prestamos.add(objPrestamo);
        }
        
        
        return prestamos;
    }
    
    public ArrayList<Prestamo> obtenerPrestamosXIdEstudiante_XEstado(int idEstudiante,int estado) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT * FROM v_detalle_prestamos WHERE idestudiante = ? AND estadoprestamo = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, idEstudiante);
        pstm.setInt(2, estado);
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        Prestamo objPrestamo;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objPrestamo = new Prestamo();
            objPrestamo.setId(rs.getInt(17));
            objPrestamo.getEstudiante().setNombres(rs.getString(13));
            objPrestamo.getEstudiante().setApellidoPaterno(rs.getString(14));
            objPrestamo.getEstudiante().setApellidoMaterno(rs.getString(15));
            objPrestamo.getEjemplar().getLibro().setTitulo(rs.getString(5));
            objPrestamo.getEjemplar().setIdejemplar(rs.getInt(1));
            objPrestamo.setFechaPrestamo(castear_a_GregorianCalendar(rs.getString(18)));
            objPrestamo.setFechaFinalizacionPrestamo(castear_a_GregorianCalendar(rs.getString(21)));
            objPrestamo.setDescripcion_estado_prestamo(rs.getString(22));
            
            prestamos.add(objPrestamo);
        }
        
        
        return prestamos;
    }
    
    public ArrayList<Prestamo> obtenerEjemplaresPrestadosXLibro(int idLibro) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT * FROM v_detalle_prestamos WHERE idlibro = ?;";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, idLibro);
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        Prestamo objPrestamo;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objPrestamo = new Prestamo();
            objPrestamo.setId(rs.getInt(17));
            objPrestamo.getEstudiante().setNombres(rs.getString(13));
            objPrestamo.getEstudiante().setApellidoPaterno(rs.getString(14));
            objPrestamo.getEstudiante().setApellidoMaterno(rs.getString(15));
            objPrestamo.getEjemplar().getLibro().setTitulo(rs.getString(5));
            objPrestamo.getEjemplar().setIdejemplar(rs.getInt(1));
            objPrestamo.setFechaPrestamo(castear_a_GregorianCalendar(rs.getString(18)));
            objPrestamo.setFechaFinalizacionPrestamo(castear_a_GregorianCalendar(rs.getString(21)));
            objPrestamo.setDescripcion_estado_prestamo(rs.getString(22));
            
            prestamos.add(objPrestamo);
        }
        
        
        return prestamos;
    }
    
    public ArrayList<Prestamo> obtenerPrestamosXEstado(int estado) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT * FROM v_detalle_prestamos WHERE estadoprestamo = ?;";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, estado);
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        Prestamo objPrestamo;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objPrestamo = new Prestamo();
            objPrestamo.setId(rs.getInt(17));
            objPrestamo.getEstudiante().setNombres(rs.getString(13));
            objPrestamo.getEstudiante().setApellidoPaterno(rs.getString(14));
            objPrestamo.getEstudiante().setApellidoMaterno(rs.getString(15));
            objPrestamo.getEjemplar().getLibro().setTitulo(rs.getString(5));
            objPrestamo.getEjemplar().setIdejemplar(rs.getInt(1));
            objPrestamo.setFechaPrestamo(castear_a_GregorianCalendar(rs.getString(18)));
            objPrestamo.setFechaFinalizacionPrestamo(castear_a_GregorianCalendar(rs.getString(21)));
            objPrestamo.setDescripcion_estado_prestamo(rs.getString(22));
            
            prestamos.add(objPrestamo);
        }
        
        return prestamos;
    }
    
    public ArrayList<Prestamo> obtenerPrestamosFueraDePlazo() throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT * FROM v_detalle_prestamos WHERE fechadevolucion < CURDATE() AND estadoprestamo = 1;";
        PreparedStatement pstm = conn.prepareStatement(sql);
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        Prestamo objPrestamo;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objPrestamo = new Prestamo();
            objPrestamo.setId(rs.getInt(17));
            objPrestamo.getEstudiante().setNombres(rs.getString(13));
            objPrestamo.getEstudiante().setApellidoPaterno(rs.getString(14));
            objPrestamo.getEstudiante().setApellidoMaterno(rs.getString(15));
            objPrestamo.getEjemplar().getLibro().setTitulo(rs.getString(5));
            objPrestamo.getEjemplar().setIdejemplar(rs.getInt(1));
            objPrestamo.setFechaPrestamo(castear_a_GregorianCalendar(rs.getString(18)));
            objPrestamo.setFechaFinalizacionPrestamo(castear_a_GregorianCalendar(rs.getString(21)));
            objPrestamo.setDescripcion_estado_prestamo(rs.getString(22));
            
            prestamos.add(objPrestamo);
        }
        
        return prestamos;
    }
    
    public GregorianCalendar castear_a_GregorianCalendar(String fecha){
        //Recibe AAAA-MM-DD
        
        GregorianCalendar formato_simplificado;
        
        String partes_fecha[] = fecha.split("-");
        
        int anio = Integer.parseInt(partes_fecha[0]);
        
        int mes = Integer.parseInt(partes_fecha[1]) - 1;
        
        int dia = Integer.parseInt(partes_fecha[2]);
        
        formato_simplificado = new GregorianCalendar(anio, mes, dia);
        
        return formato_simplificado;
    }
    
    public int generarNuevoCodigo() throws SQLException{
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        Statement stm = conn.createStatement();
        String sql = "SELECT MAX(idprestamo) FROM prestamo";
        
        /*Ejecutando la consulta y los datos seleccionados se almacenan en ResultSet*/
        ResultSet rs = stm.executeQuery(sql);
        int codigoAutogenerado = 0;
        
        while(rs.next()){
            if(rs.getString(1) == null){
                codigoAutogenerado = 0;
            }else{
                codigoAutogenerado = Integer.parseInt(rs.getString(1));
            }
        }
        
        return codigoAutogenerado + 1;
    }

}
