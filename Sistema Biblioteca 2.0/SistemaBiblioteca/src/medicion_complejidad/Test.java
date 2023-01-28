/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package medicion_complejidad;

import DAOs.EstudianteDAO;
import entidades.Estudiante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.ConexionBD;

/**
 *
 * @author Alexander
 */
public class Test {

    static Connection conn;
    
    static Funciones f = new Funciones();
    
    public static void main(String[] args) {
        
        
        try {
            /*Función: Recuperar datos de estudiantes de la BD, ordenarlos de forma
            ascendente según sus NOMBRES utilizando el algoritmo Quicksort y,
            finalmente, mostrarlos al usuario.*/
            // f.recuperar_y_mostrar_N_estudiantes(87);
            
            /*Función: Recuperar datos de libros de la BD, ordenarlos de forma
            ascendente según su AÑO DE PUBLICACIÓN utilizando el algoritmo MergeSort y,
            finalmente, mostrarlos al usuario.*/
            f.recuperar_y_mostrar_N_libros(90);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
            
    }
    
}
