
package DAOs;

import entidades.Idioma;
import entidades.Tema;
import excepcionesPropias.ExcepcionPropia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import jdbc.ConexionBD;

public class IdiomaDAO {
    
    Connection conn;
    
    public IdiomaDAO(){
        
        /*Se crea la conexión con la BD cuando se realiza una instancia de la clase TemaDAO*/
        conn = ConexionBD.conectarMySQL();
    }
    
    /*Método para recuperar los datos de la tabla Idioma de la base de datos y almacenarlos
    en una estructura List que contenga objetos de tipo Tema.*/
    public ArrayList<Idioma> obtenerIdiomas() throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        Statement stm = conn.createStatement();
        String sql = "SELECT * FROM idioma ORDER BY descripcion ASC";
        
        /*Ejecutando la consulta y los datos seleccionados se almacenan en ResultSet*/
        ResultSet rs = stm.executeQuery(sql);
        Idioma objIdioma;
        ArrayList<Idioma> idiomas = new ArrayList<>();
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Materia. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objIdioma = new Idioma();
            objIdioma.setIdidioma(Integer.parseInt(rs.getString(1)));
            objIdioma.setDescripcion(rs.getString(2));
            idiomas.add(objIdioma);
        }
        
        return idiomas;
    }
    
    /*Método de ordenamiento. UTILIZAR un algoritmo de ordenamiento complejo visto en clase.
        Ordenar por descripción de tema de forma ASCENDENTE.*/
    public void ordenarIdiomasXDescripcionASC(ArrayList<Idioma> temas){
        
    }
    
    
    /*Método que invierte el orden de los elementos de la lista.*/
    public void ordenarIdiomasXDescripcionDESC(ArrayList<Idioma> temas){
        
        Collections.reverse(temas);
    }
    
}
