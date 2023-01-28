package DAOs;

import entidades.Ejemplar;
import entidades.Libro;
import entidades.Tema;
import excepcionesPropias.ExcepcionPropia;
import grafoNoDirigido.Grafo;
import grafoNoDirigido.Vertice;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jdbc.ConexionBD;
import listasPropias.ListaLibros;

public class EjemplarDAO {

    Connection conn;
    
    public EjemplarDAO(){
        /*Se crea la conexión con la BD cuando se realiza una instancia de la clase LibroDAO*/
        conn = ConexionBD.conectarMySQL();
    }
    
    public ArrayList<Ejemplar> obtenerEjemplaresDisponiblesXLibro(int idLibro) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT * FROM v_detalle_ejemplares WHERE idlibro = ? AND estadoejemplar = 1";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, idLibro);
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ArrayList<Ejemplar> listaEjemplares = new ArrayList<>();
        Ejemplar objEjemplar;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objEjemplar = new Ejemplar();
            objEjemplar.setIdejemplar(Integer.parseInt(rs.getString(1)));
            
            listaEjemplares.add(objEjemplar);
        }
        
        return listaEjemplares;
    }
    
    public int generarNuevoCodigo() throws SQLException{
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        Statement stm = conn.createStatement();
        String sql = "SELECT MAX(idejemplar) FROM ejemplar";
        
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
    
    public void agregarEjemplar(int idLibro) throws SQLException{
        
        //Estableciendo la sentencia de inserción SQL
        String sql = "INSERT INTO ejemplar (idlibro) "
                + "VALUES (?);";
        PreparedStatement pstm_insert = conn.prepareStatement(sql);
        
        //Asignando parámetros de la sentencia
        pstm_insert.setInt(1, idLibro);
        
        //Manipulando, finalmente, la base de datos
        pstm_insert.executeUpdate();
    }
    
    public void ordenarLibrosXAnioASC(List<Libro> listaLibros){
        
        
    }
    
    public void ordenarLibrosXAnioDESC(List<Libro> listaLibros){
        Collections.reverse(listaLibros);
    }
    
    public void ordenarLibrosXTituloASC(List<Libro> listaLibros, int izq, int der){
        
    }
    
    public void ordenarLibrosXTituloDESC(List<Libro> listaLibros){
        Collections.reverse(listaLibros);
    }
    
}
