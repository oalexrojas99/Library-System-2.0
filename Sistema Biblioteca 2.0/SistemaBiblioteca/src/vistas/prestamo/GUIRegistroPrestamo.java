/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vistas.prestamo;

import DAOs.EjemplarDAO;
import vistas.libro.*;
import vistas.estudiantes.*;
import DAOs.EstudianteDAO;
import DAOs.LibroDAO;
import DAOs.TemaDAO;
import DAOs.PrestamoDAO;
import entidades.Ejemplar;
import entidades.Estudiante;
import entidades.Libro;

import entidades.Tema;
import entidades.Prestamo;
import excepcionesPropias.ExcepcionPropia;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import listasPropias.ListaEstudiantes;
import listasPropias.ListaLibros;
import listasPropias.ListaPrestamos;
import vistas.principal.GUIPrincipal;

/**
 *
 * @author Alexander
 */
public class GUIRegistroPrestamo extends javax.swing.JInternalFrame {

    /**
     * Creates new form GUIAgregarNuevoEjemplar
     */
    
    LibroDAO libroDAO = new LibroDAO();
    EstudianteDAO estudianteDAO = new EstudianteDAO();
    PrestamoDAO prestamoDAO = new PrestamoDAO();
    EjemplarDAO ejemplarDAO = new EjemplarDAO();
    ArrayList<Libro> listaLibros;
    ArrayList<Estudiante> listaEstudiantes;
    ArrayList<Prestamo> listaPrestamos;
    ArrayList<Ejemplar> listaEjemplar;
    
    public GUIRegistroPrestamo() {
        initComponents();
        cargarCodigo();
        cargarCboBoxEstudiante();
        cargarCboBoxLibro();
    }

    private void limpiar(){
        lblCodigo.setText("");
        cboEstudiantes.setSelectedIndex(0);
        cboLibros.setSelectedIndex(0);
        txtNumDias.setText("");
        
    }
    
    private void establecerPorDefecto(){
        cargarCodigo();
        limpiar();
    }
    
    private void cargarCodigo(){
        try {
            lblCodigo.setText(prestamoDAO.generarNuevoCodigo()+ "");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Hubo un error al generar código");
        }
    }
    
    public void irAGUIPrestamo(){
        GUIPrincipal.vacearDktpPane();
        
        GUIPrestamo guiPrestamo = new GUIPrestamo();
        GUIPrincipal.agregarJframe(guiPrestamo);
        guiPrestamo.setVisible(true);
    }
    
    private void cargarCboBoxEstudiante(){
        
        try {
            listaEstudiantes = new ArrayList<>();
            listaEstudiantes = estudianteDAO.obtenerEstudiantesActivos();
            //estudianteDAO.ordenarEstudiantesXNombreASC(listaEstudiantes, 0, listaEstudiantes.size() - 1);
            
            for (int i = 0; i < listaEstudiantes.size(); i++) {
                cboEstudiantes.addItem(listaEstudiantes.get(i).getId()+ " - " + listaEstudiantes.get(i).getNombres() + " " + listaEstudiantes.get(i).getApellidoPaterno() + " " + listaEstudiantes.get(i).getApellidoMaterno());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    private void cargarCboBoxEjemplares(int idLibro){
        
        cboEjemplar.removeAllItems();
        
        try {
            listaEjemplar = new ArrayList<>();
            listaEjemplar = ejemplarDAO.obtenerEjemplaresDisponiblesXLibro(idLibro);
            //estudianteDAO.ordenarEstudiantesXNombreASC(listaEstudiantes, 0, listaEstudiantes.size() - 1);
            
            for (int i = 0; i < listaEjemplar.size(); i++) {
                cboEjemplar.addItem(listaEjemplar.get(i).getIdejemplar() + "");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    private void cargarCboBoxLibro(){
        
        try {
            listaLibros = new ArrayList<>();
            listaLibros = libroDAO.obtenerLibrosConEjemplaresDisponibles();
            //libroDAO.ordenarLibrosXTituloASC(listaLibros, 0, listaLibros.size() - 1);
            
            for (int i = 0; i < listaLibros.size(); i++) {
                cboLibros.addItem(listaLibros.get(i).getId()+ " - " + listaLibros.get(i).getTitulo());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPaneEstudiante = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNumDias = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cboLibros = new javax.swing.JComboBox<>();
        cboEstudiantes = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cboEjemplar = new javax.swing.JComboBox<>();

        setPreferredSize(new java.awt.Dimension(910, 530));

        jPaneEstudiante.setPreferredSize(new java.awt.Dimension(900, 530));
        jPaneEstudiante.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCodigo.setBackground(new java.awt.Color(204, 204, 204));
        lblCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCodigo.setOpaque(true);
        jPaneEstudiante.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, 250, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setText("Generar préstamo");
        jPaneEstudiante.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 350, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Código:");
        jPaneEstudiante.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 80, -1));

        btnRegresar.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegresarMouseClicked(evt);
            }
        });
        jPaneEstudiante.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 330, 120, 50));

        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnRegistrar.setText("Registrar");
        btnRegistrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegistrarMouseClicked(evt);
            }
        });
        jPaneEstudiante.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 260, 120, 50));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Seleccionar estudiante:");
        jPaneEstudiante.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 160, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Seleccionar ejemplar:");
        jPaneEstudiante.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 180, -1));
        jPaneEstudiante.add(txtNumDias, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 220, 450, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Número de días del préstamo:");
        jPaneEstudiante.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 210, -1));

        cboLibros.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLibrosItemStateChanged(evt);
            }
        });
        jPaneEstudiante.add(cboLibros, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 160, 450, -1));

        jPaneEstudiante.add(cboEstudiantes, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 130, 450, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Seleccionar libro:");
        jPaneEstudiante.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 130, -1));

        jPaneEstudiante.add(cboEjemplar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 190, 450, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPaneEstudiante, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPaneEstudiante, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarMouseClicked
        // TODO add your handling code here:
        try {
            if(txtNumDias.getText().equals("")){
                throw new ExcepcionPropia("Debe completar y seleccionar todos los campos");
            }

            Prestamo objPrestamo = new Prestamo();

            objPrestamo.setId(Integer.parseInt(lblCodigo.getText()));
            
            String datos_estudiante[] = cboEstudiantes.getSelectedItem().toString().split(" - ");
            objPrestamo.getEstudiante().setId(Integer.parseInt(datos_estudiante[0]));
            
            int idEjemplar = Integer.parseInt(cboEjemplar.getSelectedItem().toString());
           
            objPrestamo.getEjemplar().setIdejemplar(idEjemplar);
            
            objPrestamo.setNum_dias_prestamo(Integer.parseInt(txtNumDias.getText().trim()));

            prestamoDAO.registrar(objPrestamo);

            JOptionPane.showMessageDialog(this, "Nuevo préstamo registrado exitosamente");
            irAGUIPrestamo();

            establecerPorDefecto();
        } catch (ExcepcionPropia | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Asegúrese de haber completado bien los datos.");
        }
    }//GEN-LAST:event_btnRegistrarMouseClicked

    private void btnRegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegresarMouseClicked
        // TODO add your handling code here:
        irAGUIPrestamo();
    }//GEN-LAST:event_btnRegresarMouseClicked

    private void cboLibrosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLibrosItemStateChanged
        // TODO add your handling code here:
        if(cboLibros.getSelectedIndex() != -1){
            String item[] = cboLibros.getSelectedItem().toString().split( " - ");
            int idLibro = Integer.parseInt(item[0]);
            cargarCboBoxEjemplares(idLibro);
        }
    }//GEN-LAST:event_cboLibrosItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cboEjemplar;
    private javax.swing.JComboBox<String> cboEstudiantes;
    private javax.swing.JComboBox<String> cboLibros;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPaneEstudiante;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JTextField txtNumDias;
    // End of variables declaration//GEN-END:variables
}
