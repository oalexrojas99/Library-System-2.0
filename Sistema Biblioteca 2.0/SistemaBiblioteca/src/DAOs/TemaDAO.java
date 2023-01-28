
package DAOs;

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

public class TemaDAO {
    
    Connection conn;
    
    public TemaDAO(){
        
        /*Se crea la conexión con la BD cuando se realiza una instancia de la clase TemaDAO*/
        conn = ConexionBD.conectarMySQL();
    }
    
    public void registrar(Tema objTema) throws ExcepcionPropia, SQLException{
        
        //Validar la no repitencia de algun mismo nombre de Tema
        //List<Tema> materias = new ArrayList<>();
        ArrayList<Tema> temas;
        temas = obtenerTodosTemas();
        
        for (int i = 0; i < temas.size(); i++) {
            if(temas.get(i).getDescripcion().equalsIgnoreCase(objTema.getDescripcion())){
                throw new ExcepcionPropia("Ya existe esta descripción de tema en el sistema. REGISTRO NO PROCEDE.");
            }
        }
        
        //Estableciendo la sentencia de inserción SQL
        String sql = "INSERT INTO tema (descripcion) VALUES (?);";
        PreparedStatement pstm_insert = conn.prepareStatement(sql);
        
        //Asignando parámetros a la sentencia
        pstm_insert.setString(1, objTema.getDescripcion());
        
        //Manipulando, finalmente, la base de datos
        pstm_insert.executeUpdate();
        
        /*CREAR NUEVO VERTICE*/
    }

    public void actualizar(Tema objMateria) throws ExcepcionPropia, SQLException{
        
        //Validar la no repitencia de nombre de descripción de materia a excepción del mismo objeto
        //List<Tema> materias = new ArrayList<>();
        ArrayList<Tema> temas;
        temas = obtenerTodosTemas();
        
        /*Quitamos el objeto en cuestión para poder comparar con los otros objetos Tema*/
        temas.remove(objMateria);
        
        //Bucle para comparar con todos los datos de Tema
        for (int i = 0; i < temas.size(); i++) {
            if(temas.get(i).getDescripcion().equalsIgnoreCase(objMateria.getDescripcion())){
                throw new ExcepcionPropia("Ya existe esta descripción de tema en el sistema. ACTUALIZACIÓN NO PROCEDE.");
            }
        }
        
        //Estableciendo la sentencia SQL para la actualización y asignando parámetros
        String sql_update = "UPDATE tema SET descripcion = ? WHERE idtema = ?";
        PreparedStatement pstm_update = conn.prepareStatement(sql_update);
        
        pstm_update.setString(1, objMateria.getDescripcion());
        pstm_update.setInt(2, objMateria.getId());
        
        //Manipulación en la BD
        pstm_update.executeUpdate();
    }
    
    public void eliminar(Tema objTema) throws SQLException, Exception{
        
        //Estableciendo la sentencia SQL para consulta y asignando el parámetro necesario (su ID)
        String sql_delete = "DELETE FROM tema WHERE idtema = ?";
        PreparedStatement pstm = conn.prepareStatement(sql_delete);
        pstm.setInt(1, objTema.getId());
        
        //Manipulando datos en la base de datos
        pstm.executeUpdate();
    }
    
    /*Método para recuperar los datos de la tabla Tema de la base de datos y almacenarlos
    en una estructura List que contenga objetos de tipo Tema.*/
    public ArrayList<Tema> obtenerTodosTemas() throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        Statement stm = conn.createStatement();
        String sql = "SELECT * FROM tema ORDER BY descripcion ASC";
        
        /*Ejecutando la consulta y los datos seleccionados se almacenan en ResultSet*/
        ResultSet rs = stm.executeQuery(sql);
        Tema objTema;
        ArrayList<Tema> temas = new ArrayList<>();
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Materia. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objTema = new Tema();
            objTema.setId(Integer.parseInt(rs.getString(1)));
            objTema.setDescripcion(rs.getString(2));
            temas.add(objTema);
        }
        
        return temas;
    }
    
    /*Método para recuperar todos los datos de tema según los caracteres contenidos en la
        columna 'descripcion' de la tabla Tema de la base de datos. Dichos datos se alojarán
        en una estructura de Lista.*/
    public ArrayList<Tema> obtenerTemasXDescripcion(String cadena_contenido) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT * FROM tema WHERE descripcion LIKE ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, "%" + cadena_contenido + "%");
        
        //Recuperando datos de materia
        ResultSet rs = pstm.executeQuery();
        ArrayList<Tema> temas = new ArrayList<>();
        Tema objTema;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Materia. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objTema = new Tema();
            objTema.setId(Integer.parseInt(rs.getString(1)));
            objTema.setDescripcion(rs.getString(2));
            temas.add(objTema);
        }
        
        return temas;
    }
    
    public ArrayList<Tema> obtenerTemas(ArrayList<String> descripciones) throws SQLException{
        
        String sql = "SELECT * FROM tema WHERE descripcion = ?";
        
        for (int i = 1; i < descripciones.size(); i++) {
            sql = sql.concat(" OR descripcion = ?");
        }
        
        /*Estableciendo la sentencia SQL para consulta*/
        //String sql = "SELECT * FROM materia WHERE descripcion LIKE ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        
        pstm.setString(1, descripciones.get(0));
        for (int i = 1; i < descripciones.size(); i++) {
            pstm.setString(i + 1, descripciones.get(i));
        }
        
        //Recuperando datos de tema
        ResultSet rs = pstm.executeQuery();
        ArrayList<Tema> temas = new ArrayList<Tema>();
        Tema objTema;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Materia. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objTema = new Tema();
            objTema.setId(Integer.parseInt(rs.getString(1)));
            objTema.setDescripcion(rs.getString(2));
            temas.add(objTema);
        }
        
        return temas;
    }
    
    /*Método de ordenamiento. UTILIZAR un algoritmo de ordenamiento complejo visto en clase.
        Ordenar por descripción de tema de forma ASCENDENTE.*/
    public void ordenarTemasXDescripcionASC(ArrayList<Tema> temas){
        
    }
    
    
    /*Método que invierte el orden de los elementos de la lista.*/
    public void ordenarTemasXDescripcionDESC(ArrayList<Tema> temas){
        
        Collections.reverse(temas);
    }
    
    /*Método que retornará un valor entero que será el código asignado en el nuevo
    registro de Tema.*/
    public int generarNuevoCodigo() throws SQLException{
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        Statement stm = conn.createStatement();
        String sql = "SELECT MAX(idtema) FROM tema";
        
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
    
    public Tema obtenerTemasXID(int id) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT * FROM tema WHERE idtema = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        
        //Recuperando datos de tema
        ResultSet rs = pstm.executeQuery();
        Tema objTema = new Tema();
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Materia. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objTema.setId(Integer.parseInt(rs.getString(1)));
            objTema.setDescripcion(rs.getString(2));
        }
        
        return objTema;
    }
    
}
