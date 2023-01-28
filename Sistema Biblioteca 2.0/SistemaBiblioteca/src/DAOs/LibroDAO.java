package DAOs;

import entidades.Libro;
import excepcionesPropias.ExcepcionPropia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import jdbc.ConexionBD;

public class LibroDAO {

    Connection conn;
    
    public LibroDAO(){
        /*Se crea la conexión con la BD cuando se realiza una instancia de la clase LibroDAO*/
        conn = ConexionBD.conectarMySQL();
    }
    
    public void registrar(Libro objLibro) throws ExcepcionPropia, SQLException{
        
        //Validar la no repitencia de algun otro título o ISBN de Libro
        ArrayList<Libro> libros = new ArrayList<>();
        libros = obtenerLibros();
        
        for (int i = 0; i < libros.size(); i++) {
            if(libros.get(i).getTitulo().equalsIgnoreCase(objLibro.getTitulo())){
                throw new ExcepcionPropia("Título del libro ya registrado en el sistema.");
            }
            
            if(libros.get(i).getIsbn().equalsIgnoreCase(objLibro.getIsbn())){
                throw new ExcepcionPropia("ISBN del libro ya registrado en el sistema.");
            }
        }
        
        //Estableciendo la sentencia de inserción SQL
        String sql = "INSERT INTO libro (isbn, titulo, autor, idtema, ididioma, anio) VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement pstm_insert = conn.prepareStatement(sql);
        
        //Asignando parámetros de la sentencia
        pstm_insert.setString(1, objLibro.getIsbn());
        pstm_insert.setString(2, objLibro.getTitulo());
        pstm_insert.setString(3, objLibro.getAutores());
        pstm_insert.setInt(4, objLibro.getTema().getId());
        pstm_insert.setInt(5, objLibro.getIdioma().getIdidioma());
        pstm_insert.setInt(6, objLibro.getAniopublicacion());
        
        //Manipulando, finalmente, la base de datos
        pstm_insert.executeUpdate();
    }

    public void actualizar(Libro objLibro) throws ExcepcionPropia, SQLException{
        
        //Validar la no repitencia del nombre delestudiante a excepción del mismo objeto
        ArrayList<Libro> libros = new ArrayList<>();
        libros = obtenerLibros();
        
        //Bucle para comparar con todos los datos de Libro
        for (int i = 0; i < libros.size(); i++) {
            if(libros.get(i).getId() != objLibro.getId()){
                if(libros.get(i).getTitulo().equalsIgnoreCase(objLibro.getTitulo())){
                    throw new ExcepcionPropia("Título del libro ya registrado en el sistema.");
                }

                if(libros.get(i).getIsbn().equalsIgnoreCase(objLibro.getIsbn())){
                    throw new ExcepcionPropia("ISBN del libro ya registrado en el sistema.");
                }
            }
        }
        
        //Estableciendo la sentencia SQL para la actualización y asignando parámetros
        String sql_update = "UPDATE libro SET isbn = ?, titulo = ?, autor = ?, idtema = ?, ididioma = ?, anio = ? WHERE idlibro = ?";
        PreparedStatement pstm_update = conn.prepareStatement(sql_update);
        
        pstm_update.setString(1, objLibro.getIsbn());
        pstm_update.setString(2, objLibro.getTitulo());
        pstm_update.setString(3, objLibro.getAutores());
        pstm_update.setInt(4, objLibro.getTema().getId());
        pstm_update.setInt(5, objLibro.getIdioma().getIdidioma());
        pstm_update.setInt(6, objLibro.getAniopublicacion());
        
        /*if(objLibro.getEstado().equals("Disponible")){
            pstm_update.setInt(7, 1);
        } else{
            pstm_update.setInt(7, 0);
        }*/
        
        pstm_update.setInt(7, objLibro.getId());
        
        //Manipulación en la BD
        pstm_update.executeUpdate();
    }
    
    public void eliminar(Libro objLibro) throws SQLException, Exception{
        
        //Estableciendo la sentencia SQL para consulta y asignando el parámetro necesario
        String sql_delete = "DELETE FROM libro WHERE idlibro = ?";
        PreparedStatement pstm = conn.prepareStatement(sql_delete);
        pstm.setInt(1, objLibro.getId());
        
        //Manipulando datos en la base de datos
        pstm.executeUpdate();
    }
    
    public ArrayList<Libro> obtenerLibros() throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        Statement stm = conn.createStatement();
        String sql = "SELECT * FROM v_libros_temas_idiomas ORDER BY titulo ASC;";
        
         /*Ejecutando la consulta y los datos seleccionados se almacenarán en ResultSet*/
        ResultSet rs = stm.executeQuery(sql);
        Libro objLibro;
        ArrayList<Libro> listaLibros = new ArrayList<>();
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objLibro = new Libro();
            objLibro.setId(Integer.parseInt(rs.getString(1)));
            objLibro.setIsbn(rs.getString(2));
            objLibro.setTitulo(rs.getString(3));
            objLibro.setAutores(rs.getString(4));
            objLibro.getTema().setId(rs.getInt(5));
            objLibro.getIdioma().setIdidioma(rs.getInt(6));
            objLibro.setAniopublicacion(rs.getInt(7));
            objLibro.getTema().setDescripcion(rs.getString(8));
            objLibro.getIdioma().setDescripcion(rs.getString(9));
            
            listaLibros.add(objLibro);
        }
        
        return listaLibros;
    }
    
    public ArrayList<Libro> obtenerLibrosXTitulo(String cadena) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT * FROM v_libros_temas_idiomas WHERE titulo LIKE ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, "%" + cadena + "%");
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ArrayList<Libro> listaLibros = new ArrayList<>();
        Libro objLibro;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objLibro = new Libro();
            objLibro.setId(Integer.parseInt(rs.getString(1)));
            objLibro.setIsbn(rs.getString(2));
            objLibro.setTitulo(rs.getString(3));
            objLibro.setAutores(rs.getString(4));
            objLibro.getTema().setId(rs.getInt(5));
            objLibro.getIdioma().setIdidioma(rs.getInt(6));
            objLibro.setAniopublicacion(rs.getInt(7));
            objLibro.getTema().setDescripcion(rs.getString(8));
            objLibro.getIdioma().setDescripcion(rs.getString(9));
            
            listaLibros.add(objLibro);
        }
        
        return listaLibros;
    }
    
    public ArrayList<Libro> obtenerLibrosConEjemplaresDisponibles() throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        Statement stm = conn.createStatement();
        String sql = "SELECT\n" +
                    "    l.*\n" +
                    "    FROM libro l\n" +
                    "    INNER JOIN ejemplar e ON l.idlibro = e.idlibro\n" +
                    "    WHERE\n" +
                    "		e.estadoejemplar = 1\n" +
                    "    GROUP BY l.idlibro\n" +
                    "    HAVING COUNT(*) > 1";
        
         /*Ejecutando la consulta y los datos seleccionados se almacenarán en ResultSet*/
        ResultSet rs = stm.executeQuery(sql);
        Libro objLibro;
        ArrayList<Libro> listaLibros = new ArrayList<>();
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objLibro = new Libro();
            objLibro.setId(Integer.parseInt(rs.getString(1)));
            objLibro.setTitulo(rs.getString(3));
            
            listaLibros.add(objLibro);
        }
        
        return listaLibros;
    }
    
    public ArrayList<Libro> consultarLibros(int anio1, int anio2, int idTema) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "CALL SP_consulta_catalogo_libros (?, ?, ?);";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, anio1);
        pstm.setInt(2, anio2);
        pstm.setInt(3, idTema);
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ArrayList<Libro> listaLibros = new ArrayList<>();
        Libro objLibro;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objLibro = new Libro();
            objLibro.setId(Integer.parseInt(rs.getString(1)));
            objLibro.setIsbn(rs.getString(2));
            objLibro.setTitulo(rs.getString(3));
            objLibro.setAutores(rs.getString(4));
            objLibro.getTema().setId(rs.getInt(5));
            objLibro.getIdioma().setIdidioma(rs.getInt(6));
            objLibro.setAniopublicacion(rs.getInt(7));
            objLibro.getTema().setDescripcion(rs.getString(8));
            objLibro.getIdioma().setDescripcion(rs.getString(9));
            
            listaLibros.add(objLibro);
        }
        
        return listaLibros;
    }
    
    public ArrayList<Libro> obtenerLibrosRelacionados(ArrayList<String> temas) throws SQLException{
        
        String sql = "SELECT * FROM v_libros_temas_idiomas WHERE tema = ?";
        
        for (int i = 1; i < temas.size(); i++) {
            sql = sql.concat(" OR tema = ?");
        }
        
        /*Estableciendo la sentencia SQL para consulta*/
        //String sql = "SELECT * FROM materia WHERE descripcion LIKE ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        
        pstm.setString(1, temas.get(0).toString());
        for (int i = 1; i < temas.size(); i++) {
            pstm.setString(i + 1, temas.get(i).toString());
        }
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ArrayList<Libro> librosRelacionados = new ArrayList<Libro>();
        Libro objLibro;
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Materia. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objLibro = new Libro();
            objLibro.setId(Integer.parseInt(rs.getString(1)));
            objLibro.setIsbn(rs.getString(2));
            objLibro.setTitulo(rs.getString(3));
            objLibro.setAutores(rs.getString(4));
            objLibro.getTema().setId(rs.getInt(5));
            objLibro.getIdioma().setIdidioma(rs.getInt(6));
            objLibro.setAniopublicacion(rs.getInt(7));
            objLibro.getTema().setDescripcion(rs.getString(8));
            objLibro.getIdioma().setDescripcion(rs.getString(9));
            
            librosRelacionados.add(objLibro);
        }
        
        return librosRelacionados;
    }
    
    public Libro obtenerLibroXID(int idLibro) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta*/
        String sql = "SELECT * FROM v_libros_temas_idiomas WHERE idlibro = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, idLibro);
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        Libro objLibro = new Libro();
        
        /*Recorremos todas las filas de ResultSet tras cada invocación del método next().
        Los datos de cada fila los almacenamos en cada objeto de tipo Libro. Posteriormente
        el objeto lo almacenamos en la estructura List*/
        while(rs.next()){
            objLibro = new Libro();
            objLibro.setId(Integer.parseInt(rs.getString(1)));
            objLibro.setIsbn(rs.getString(2));
            objLibro.setTitulo(rs.getString(3));
            objLibro.setAutores(rs.getString(4));
            objLibro.getTema().setId(rs.getInt(5));
            objLibro.getIdioma().setIdidioma(rs.getInt(6));
            objLibro.setAniopublicacion(rs.getInt(7));
            objLibro.getTema().setDescripcion(rs.getString(8));
            objLibro.getIdioma().setDescripcion(rs.getString(9));
        }
        
        return objLibro;
    }
    
    public int generarNuevoCodigo() throws SQLException{
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        Statement stm = conn.createStatement();
        String sql = "SELECT MAX(idlibro) FROM libro";
        
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
    
    public void ordenarLibrosXAnioASC(ArrayList<Libro> listaLibros){
        mergesort_Anio(listaLibros, 0, listaLibros.size() - 1);
    }
    
    public void ordenarLibrosXAnioDESC(ArrayList<Libro> listaLibros){
        ordenarLibrosXAnioASC(listaLibros);
        Collections.reverse(listaLibros);
    }
    
    public void ordenarLibrosXTituloASC(ArrayList<Libro> listaLibros){
        ordenacionShell(listaLibros);
    }
    
    public void ordenarLibrosXTituloDESC(ArrayList<Libro> listaLibros){
        ordenarLibrosXTituloASC(listaLibros);
        Collections.reverse(listaLibros);
    }
    
    public void mergesort_Anio(ArrayList<Libro> listaLibros, int izq, int der){
        if (izq < der){
            int m = (izq + der) / 2;
            mergesort_Anio(listaLibros, izq, m);
            mergesort_Anio(listaLibros, m+1, der);                                                                                
            merge_anio(listaLibros, izq, m, der);                                                                                 
        }
    }
    
    public void merge_anio(ArrayList<Libro> listaLibros,int izq, int m, int der){
        int i, j, k;
        
        ArrayList<Libro> sub_lista = new ArrayList<>(); //array auxiliar
        for (int l = 0; l < listaLibros.size(); l++) {
            sub_lista.add(null);
        }
                
        for (i = izq; i <= der; i++)      //copia ambas mitades en el array auxiliar 
            sub_lista.set(i, listaLibros.get(i));

        i = izq; 
        j = m + 1; 
        k = izq;

        while (i <= m && j <= der) //copia el siguiente elemento más grande                                      
            if (sub_lista.get(i).getAniopublicacion() <=  sub_lista.get(j).getAniopublicacion())
                 listaLibros.set(k++, sub_lista.get(i++));
            else
                listaLibros.set(k++, sub_lista.get(j++));

        while (i <= m)         //copia los elementos que quedan de la
            listaLibros.set(k++, sub_lista.get(i++));
    }
    
    public void ordenacionShell(ArrayList<Libro> listaLibros) {
        int N = listaLibros.size();
        int incremento = N;
        do {
            incremento = incremento / 2;
            for (int k = 0; k < incremento; k++) {
                for (int i = incremento + k; i < N; i += incremento) {
                    int j = i;
                    while (j - incremento >= 0 && listaLibros.get(j).getTitulo().
                            compareToIgnoreCase(listaLibros.get(j - incremento).getTitulo()) < 0) {
                        Libro tmp = listaLibros.get(j);
                        listaLibros.set(j, listaLibros.get(j - incremento));
                        listaLibros.set(j - incremento, tmp);
                        j -= incremento;
                    }
                }
            }
        } while (incremento > 1);
    }
}
