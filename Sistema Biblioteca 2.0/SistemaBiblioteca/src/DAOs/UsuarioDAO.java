package DAOs;

import entidades.Usuario;
import excepcionesPropias.ExcepcionPropia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import jdbc.ConexionBD;

public class UsuarioDAO {

    Connection conn;
    ArrayList<Usuario> usuarios;
    
    public UsuarioDAO(){
        conn = ConexionBD.conectarMySQL();
    }
    
    public void registrar(Usuario objUsuario) throws ExcepcionPropia, SQLException{
        
        //validarDatosUsuario(objUsuario);
       
        //Validar la no repitencia de algun otro nombre de usuario y correo elentronico
        /*ArrayList<Cliente> totalClientes = obtenerClientes();
        
        for (int i = 0; i < totalClientes.size(); i++) {
            if(totalClientes.get(i).getNombreCompleto().equals(objCliente.getNombreCompleto())){
                throw new DatoNoValido("Nombre de cliente ya registrado al sistema.");
            }
            
            if(totalClientes.get(i).getCelular().equals(objCliente.getCelular())){
                throw new DatoNoValido("Número telefónico ya registrado al sistema.");
            }
        }*/
        
        //Estableciendo la sentencia de inserción SQL
        String sql = "INSERT INTO usuario (email, nombreusuario, contrasena) VALUES (?, ?, ?);";
        PreparedStatement pstm_insert = conn.prepareStatement(sql);
        
        //Asignando parámetros de la sentencia
        pstm_insert.setString(1, objUsuario.getEmail());
        pstm_insert.setString(2, objUsuario.getNombreusuario());
        pstm_insert.setString(3, objUsuario.getContrasenia());
        
        //Manipulando, finalmente, la base de datos
        pstm_insert.executeUpdate();
    }

    public void actualizar(Usuario objUsuario) throws ExcepcionPropia, SQLException{
        
        //Validar no repitencia de nombre de cliente ni de celular a excepción del mismo objeto
        //ArrayList<Cliente> totalClientes = obtenerClientes();
        
        /*for (int i = 0; i < usuarios.size(); i++) {
            if(!(usuarios.get(i).getId() == objUsuario.getId())){
                if(usuarios.get(i).getNombreCompleto().equals(objCliente.getNombreCompleto())){
                    throw new DatoNoValido("Nombre de cliente ya registrado al sistema.");
                }
            
                if(totalClientes.get(i).getCelular().equals(objCliente.getCelular())){
                    throw new DatoNoValido("Número telefónico ya registrado al sistema.");
                }
            }
        }*/
        //Estableciendo la sentencia SQL para la actualización y asignando parámetros
        String sql_update = "UPDATE usuario SET email = ?, nombreusuario = ?,"
                + "contrasena = ? WHERE idusuario = ?";
        PreparedStatement pstm_update = conn.prepareStatement(sql_update);
        
        pstm_update.setString(1, objUsuario.getEmail());
        pstm_update.setString(2, objUsuario.getNombreusuario());
        pstm_update.setString(3, objUsuario.getContrasenia());
        pstm_update.setInt(4, objUsuario.getId());
        
        //Manipulación en la BD
        pstm_update.executeUpdate();
    }

    public void obtenerUsuarios(List<Usuario> listaUsuarios) throws SQLException{
        
        /*Estableciendo la sentencia SQL para consulta. Utilizamos createStatement()
        ya que, para la ejecución de la sentencia SQL, no se necesita parámetros.*/
        
        Statement stm = conn.createStatement();
        String sql = "SELECT * FROM usuario";
        
        //Recuperando clientes
        ResultSet rs = stm.executeQuery(sql);
        Usuario objUsuario;
        
        while(rs.next()){
            objUsuario = new Usuario();
            objUsuario.setId(Integer.parseInt(rs.getString(1)));
            objUsuario.setEmail(rs.getString(2));
            objUsuario.setNombreusuario(rs.getString(3));
            objUsuario.setContrasenia(rs.getString(4));
            objUsuario.setEstado(Integer.parseInt(rs.getString(5)));
            listaUsuarios.add(objUsuario);
        }
    }

    public void eliminar(Usuario objUsuario) throws SQLException, Exception{
        
        //Estableciendo la sentencia SQL para consulta y asignando el parámetro necesario
        String sql_delete = "DELETE FROM materia WHERE clienteId = ?";
        PreparedStatement pstm = conn.prepareStatement(sql_delete);
        pstm.setInt(1, objUsuario.getId());
        
        //Manipulando datos en la base de datos
        pstm.executeUpdate();
    }

    public boolean login(String user, String pass) throws SQLException{
        
        /*Validar estado activo*/
        
        String sql = "SELECT * FROM usuario WHERE nombreusuario = ? AND contrasena = ?";
        
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, user);
        pstm.setString(2, pass);
        
        //Recuperando usuario
        ResultSet rs = pstm.executeQuery();
        
        return rs.next();
    }
}
