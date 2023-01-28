/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package medicion_complejidad;

import DAOs.EstudianteDAO;
import DAOs.LibroDAO;
import entidades.Estudiante;
import entidades.Libro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import jdbc.ConexionBD;

/**
 *
 * @author Alexander
 */
public class Funciones {
    
    Connection conn;
    EstudianteDAO dao = new EstudianteDAO();
    LibroDAO libroDao = new LibroDAO();
    
    public Funciones(){
        conn = ConexionBD.conectarMySQL();
    }
    
    public void recuperar_y_mostrar_N_libros(int N) throws SQLException{
        
        long tiempo_incial_nano = System.nanoTime();
        long tiempo_final_nano;
        
        Statement stm = conn.createStatement();
        String sql = "SELECT * FROM v_libros_temas_idiomas LIMIT ?";
        
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, N);
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ArrayList<Libro> libros = new ArrayList<>();
        
        Libro objLibro;
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
            
            libros.add(objLibro);
        }
        
        libroDao.ordenarLibrosXAnioASC(libros);
        
        System.out.println("*********--------------Relación libros--------------*********");
        for (int i = 0; i < libros.size(); i++) {
            System.out.println(libros.get(i).toString());
        }
        
        tiempo_final_nano = System.nanoTime();
        
        long total_tiempo_ejecución = tiempo_final_nano - tiempo_incial_nano;
        
        System.out.println("\n\nTiempo de la ejecución en NANOS: " + total_tiempo_ejecución);
    }
    
    public void recuperar_y_mostrar_N_estudiantes(int N) throws SQLException{
        
        long tiempo_incial_nano = System.nanoTime();
        long tiempo_final_nano;
        
        Statement stm = conn.createStatement();
        String sql = "SELECT * FROM estudiante LIMIT ?";
        
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, N);
        
        //Recuperando datos de libro
        ResultSet rs = pstm.executeQuery();
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        
        Estudiante objEstudiante;
        while(rs.next()){
            objEstudiante = new Estudiante();
            objEstudiante.setId(Integer.parseInt(rs.getString(1)));
            objEstudiante.setNombres(rs.getString(2));
            objEstudiante.setApellidoPaterno(rs.getString(3));
            objEstudiante.setApellidoMaterno(rs.getString(4));
            objEstudiante.setEstado(Integer.parseInt(rs.getString(5)));
            
            estudiantes.add(objEstudiante);
        }
        
        dao.ordenarEstudiantesXApellidosASC(estudiantes);
        
        System.out.println("*********--------------Relación estudiantes--------------*********");
        for (int i = 0; i < estudiantes.size(); i++) {
            System.out.println(estudiantes.get(i).toString());
        }
        
        tiempo_final_nano = System.nanoTime();
        
        long total_tiempo_ejecución = tiempo_final_nano - tiempo_incial_nano;
        
        System.out.println("\n\nTiempo de la ejecución en NANOS: " + total_tiempo_ejecución);
    }
}
