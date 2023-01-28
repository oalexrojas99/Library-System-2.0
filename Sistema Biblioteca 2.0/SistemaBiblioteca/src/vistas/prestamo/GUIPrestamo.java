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
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import listasPropias.ListaEstudiantes;
import listasPropias.ListaLibros;
import listasPropias.ListaPrestamos;
import vistas.tema.GUIRegistroTema;
import vistas.principal.GUIPrincipal;

/**
 *
 * @author Alexander
 */
public class GUIPrestamo extends javax.swing.JInternalFrame {

    /**
     * Creates new form GUILibro
     */
    
    LibroDAO libroDAO = new LibroDAO();
    PrestamoDAO prestamoDAO = new PrestamoDAO();
    EstudianteDAO estudianteDAO = new EstudianteDAO();
    EjemplarDAO ejemplarDAO = new EjemplarDAO();
    ArrayList<Estudiante> listaEstudiantes;
    ArrayList<Libro> listaLibros;
    ArrayList<Prestamo> listaPrestamos;
    ArrayList<Ejemplar> listaEjemplar;
    
    public GUIPrestamo() {
        initComponents();
        configurarEncabezadoJTable();
        configurarRadioButtonOrden();
        cargarCboBoxEstudiantes();
        cargarCboBoxLibros();
        llenarJTablePrestamos();
    }
    
    private void configurarRadioButtonOrden(){
        btnGrpEstado.add(rdbtnEnVigencia);
        btnGrpEstado.add(rdbtnFinalizado);
        btnGrpEstado.add(rdbtnFueraPlazo);
    }
    
    private void establecerPorDefecto(){
        llenarJTablePrestamos();
        cargarCboBoxEstudiantes();
        cargarCboBoxLibros();
    }
    
    private void configurarEncabezadoJTable(){
        jTablePrestamos.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 14));
        jTablePrestamos.getTableHeader().setOpaque(false);
        jTablePrestamos.getTableHeader().setForeground(Color.BLACK);
        
    }
    
    private void cargarCboBoxEstudiantes(){
        
        try {
            listaEstudiantes = new ArrayList<Estudiante>();
            listaEstudiantes = estudianteDAO.obtenerEstudiantesActivos();
            //estudianteDAO.ordenarEstudiantesXNombreASC(listaEstudiantes, 0, listaEstudiantes.size() - 1);
            
            for (int i = 0; i < listaEstudiantes.size(); i++) {
                cboEstudiantes.addItem(listaEstudiantes.get(i).getId() + " - " + listaEstudiantes.get(i).getNombres() + " " + listaEstudiantes.get(i).getApellidoPaterno() + " " + listaEstudiantes.get(i).getApellidoMaterno());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    private void cargarCboBoxLibros(){
        
        try {
            listaLibros = new ArrayList<>();
            listaLibros = libroDAO.obtenerLibros();
            //libroDAO.ordenarLibrosXTituloASC(listaLibros, 0, listaLibros.size() - 1);
            
            for (int i = 0; i < listaLibros.size(); i++) {
                cboLibros.addItem(listaLibros.get(i).getId()+ " - " + listaLibros.get(i).getTitulo());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    private void llenarJTablePrestamos(){
        /*DefaultTableModel dtm = new DefaultTableModel();
        
        listaPrestamos = new ArrayList<>();
        
        try {
            listaPrestamos = prestamoDAO.obtenerDetallePrestamos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Hubo un problema al cargar los datos de prestamos.");
        }
        
        String[] cabecera = {"Id Préstamo", "Estudiante", "Libro", "ID ejemplar", "Fecha préstamo", "Caducación", "Descripción"}; 
        dtm.setColumnIdentifiers(cabecera);
        dtm.setColumnCount(cabecera.length);
        Object[] dataLibros = new Object[dtm.getColumnCount()];
        
        for (int i = 0; i < listaPrestamos.tamanio(); i++) {
            dataLibros[0] = listaPrestamos.obtenerPrestamo(i).getId();
            dataLibros[1] = listaPrestamos.obtenerPrestamo(i).getNombres_estudiante();
            dataLibros[2] = listaPrestamos.obtenerPrestamo(i).getTitulo_libro();
            dataLibros[3] = listaPrestamos.obtenerPrestamo(i).getNumejemplar_libro();
            dataLibros[4] = listaPrestamos.obtenerPrestamo(i).getFechaPrestamo();
            dataLibros[5] = listaPrestamos.obtenerPrestamo(i).getFechaFinalizacionPrestamo();
            dtm.addRow(dataLibros);
        }
        jTablePrestamos.setModel(dtm);*/
    }
    
    private void llenarJTablePrestamos(ArrayList<Prestamo> prestamos){
        
        DefaultTableModel dtm = new DefaultTableModel();
        
        String[] cabecera = {"Id Préstamo", "Estudiante", "Libro", "ID ejemplar", "Fecha préstamo", "Caducación", "Descripción"}; 
        dtm.setColumnIdentifiers(cabecera);
        dtm.setColumnCount(cabecera.length);
        Object[] dataLibros = new Object[dtm.getColumnCount()];
        
        for (int i = 0; i < prestamos.size(); i++) {
            dataLibros[0] = prestamos.get(i).getId();
            dataLibros[1] = prestamos.get(i).getEstudiante().getApellidoPaterno() + " " + prestamos.get(i).getEstudiante().getApellidoMaterno();
            dataLibros[2] = prestamos.get(i).getEjemplar().getLibro().getTitulo();
            dataLibros[3] = prestamos.get(i).getEjemplar().getIdejemplar();
            dataLibros[4] = prestamos.get(i).getFechaPrestamo();
            dataLibros[5] = prestamos.get(i).getFechaFinalizacionPrestamo();
            dataLibros[6] = prestamos.get(i).getDescripcion_estado_prestamo();
            dtm.addRow(dataLibros);
        }
        jTablePrestamos.setModel(dtm);
    }
    
    public void irAGUIGenerarPrestamo(){
        GUIPrincipal.vacearDktpPane();
        
        GUIRegistroPrestamo guiRegistroPrestamor = new GUIRegistroPrestamo();
        GUIPrincipal.agregarJframe(guiRegistroPrestamor);
        guiRegistroPrestamor.setVisible(true);
    }
    
    public void irAGUIRealizarDevolucion(){
        GUIPrincipal.vacearDktpPane();
        
        GUIRealizarDevolucion guiDevolucion = new GUIRealizarDevolucion();
        GUIPrincipal.agregarJframe(guiDevolucion);
        guiDevolucion.setVisible(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGrpEstado = new javax.swing.ButtonGroup();
        jPaneEstudiante = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTablePrestamos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jLabel2 = new javax.swing.JLabel();
        cboLibros = new javax.swing.JComboBox<>();
        btnDevolucion = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cboEstudiantes = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        rdbtnFueraPlazo = new javax.swing.JRadioButton();
        rdbtnEnVigencia = new javax.swing.JRadioButton();
        rdbtnFinalizado = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        btnGenerarPrestamo1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(900, 530));

        jPaneEstudiante.setPreferredSize(new java.awt.Dimension(900, 530));
        jPaneEstudiante.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTablePrestamos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(jTablePrestamos);

        jPaneEstudiante.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 150, 620, 330));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("libros");
        jPaneEstudiante.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 220, 30));

        cboLibros.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLibrosItemStateChanged(evt);
            }
        });
        jPaneEstudiante.add(cboLibros, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 70, 320, -1));

        btnDevolucion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDevolucion.setText("Devolución");
        btnDevolucion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDevolucionMouseClicked(evt);
            }
        });
        jPaneEstudiante.add(btnDevolucion, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 190, 40));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Préstamos según estado:");
        jPaneEstudiante.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, 250, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Ejemplares prestados al estudiante:");
        jPaneEstudiante.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 280, -1));

        cboEstudiantes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboEstudiantesItemStateChanged(evt);
            }
        });
        jPaneEstudiante.add(cboEstudiantes, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 320, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Ejemplares de libros prestados por título:");
        jPaneEstudiante.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 280, -1));

        rdbtnFueraPlazo.setText("Fuera de plazo");
        rdbtnFueraPlazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtnFueraPlazoActionPerformed(evt);
            }
        });
        jPaneEstudiante.add(rdbtnFueraPlazo, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 120, -1, -1));

        rdbtnEnVigencia.setText("En vigencia");
        rdbtnEnVigencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtnEnVigenciaActionPerformed(evt);
            }
        });
        jPaneEstudiante.add(rdbtnEnVigencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, -1, -1));

        rdbtnFinalizado.setText("Finalizado");
        rdbtnFinalizado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtnFinalizadoActionPerformed(evt);
            }
        });
        jPaneEstudiante.add(rdbtnFinalizado, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 120, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Préstamo y devolución de");
        jPaneEstudiante.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 220, 30));

        btnGenerarPrestamo1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnGenerarPrestamo1.setText("Generar préstamo");
        btnGenerarPrestamo1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGenerarPrestamo1MouseClicked(evt);
            }
        });
        jPaneEstudiante.add(btnGenerarPrestamo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 190, 40));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setOpaque(true);
        jPaneEstudiante.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 50, 600, 10));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setOpaque(true);
        jPaneEstudiante.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, 600, 10));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPaneEstudiante, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPaneEstudiante, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDevolucionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDevolucionMouseClicked
        // TODO add your handling code here:
        irAGUIRealizarDevolucion();
    }//GEN-LAST:event_btnDevolucionMouseClicked

    private void cboEstudiantesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboEstudiantesItemStateChanged
        // TODO add your handling code here:
        if(cboEstudiantes.getSelectedIndex() != -1){
            String item[] = cboEstudiantes.getSelectedItem().toString().split( " - ");
            int estudianteId = Integer.parseInt(item[0]);
            try {
                llenarJTablePrestamos(prestamoDAO.obtenerPrestamosXIdEstudiante(estudianteId));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }//GEN-LAST:event_cboEstudiantesItemStateChanged

    private void cboLibrosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLibrosItemStateChanged
        // TODO add your handling code here:
        if(cboLibros.getSelectedIndex() != -1){
            String item[] = cboLibros.getSelectedItem().toString().split( " - ");
            int idLibro = Integer.parseInt(item[0]);
            try {
                llenarJTablePrestamos(prestamoDAO.obtenerEjemplaresPrestadosXLibro(idLibro));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }//GEN-LAST:event_cboLibrosItemStateChanged

    private void btnGenerarPrestamo1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGenerarPrestamo1MouseClicked
        // TODO add your handling code here:
        irAGUIGenerarPrestamo();
    }//GEN-LAST:event_btnGenerarPrestamo1MouseClicked

    private void rdbtnFueraPlazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbtnFueraPlazoActionPerformed
        // TODO add your handling code here:
        if(rdbtnFueraPlazo.isSelected()){
            try {
                llenarJTablePrestamos(prestamoDAO.obtenerPrestamosFueraDePlazo());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }//GEN-LAST:event_rdbtnFueraPlazoActionPerformed

    private void rdbtnFinalizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbtnFinalizadoActionPerformed
        // TODO add your handling code here:
        if(rdbtnFinalizado.isSelected()){
            try {
                llenarJTablePrestamos(prestamoDAO.obtenerPrestamosXEstado(0));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }

    }//GEN-LAST:event_rdbtnFinalizadoActionPerformed

    private void rdbtnEnVigenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbtnEnVigenciaActionPerformed
        // TODO add your handling code here:
        if(rdbtnEnVigencia.isSelected()){
            try {
                llenarJTablePrestamos(prestamoDAO.obtenerPrestamosXEstado(1));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }//GEN-LAST:event_rdbtnEnVigenciaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDevolucion;
    private javax.swing.JButton btnGenerarPrestamo1;
    private javax.swing.ButtonGroup btnGrpEstado;
    private javax.swing.JComboBox<String> cboEstudiantes;
    private javax.swing.JComboBox<String> cboLibros;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPaneEstudiante;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTablePrestamos;
    private javax.swing.JRadioButton rdbtnEnVigencia;
    private javax.swing.JRadioButton rdbtnFinalizado;
    private javax.swing.JRadioButton rdbtnFueraPlazo;
    // End of variables declaration//GEN-END:variables
}
