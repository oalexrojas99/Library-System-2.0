/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vistas.libro;

import vistas.estudiantes.*;
import DAOs.EstudianteDAO;
import DAOs.IdiomaDAO;
import DAOs.LibroDAO;
import DAOs.TemaDAO;
import entidades.Estudiante;
import entidades.Idioma;
import entidades.Libro;
import entidades.Tema;
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
import listasPropias.ListaLibros;
import listasPropias.ListaTemas;
import vistas.tema.GUIRegistroTema;
import vistas.principal.GUIPrincipal;

/**
 *
 * @author Alexander
 */
public class GUILibro extends javax.swing.JInternalFrame {

    /**
     * Creates new form GUILibro
     */
    
    LibroDAO libroDAO = new LibroDAO();
    TemaDAO materiaDAO = new TemaDAO();
    IdiomaDAO idiomaDAO = new IdiomaDAO();
    ArrayList<Libro> listaLibros;
    ArrayList<Tema> listaTemas;
    ArrayList<Idioma> listaIdiomas;
    
    public GUILibro() {
        initComponents();
        configurarRadioButtonOrden();
        configurarEncabezadoJTable();
        llenarJTableLibros();
        cargarCboBoxTema();
        cargarCboBoxIdioma();
    }
    
    private void configurarRadioButtonOrden(){
        btnGrpOrdenamiento.add(rdbtAnioASC);
        btnGrpOrdenamiento.add(rdbtAnioDESC);
        btnGrpOrdenamiento.add(rdbtTituloASC);
        btnGrpOrdenamiento.add(rdbtTituloDESC);
    }
    
    private void limpiar(){
        txtBusquedaTitulo.setText("");
        lblCodigo.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtIsbn.setText("");
        cboIdioma.setSelectedIndex(0);
        cboTema.setSelectedIndex(0);
    }
    
    private void establecerPorDefecto(){
        limpiar();
        llenarJTableLibros();
        cargarCboBoxTema();
        cargarCboBoxIdioma();
    }
    
    private void configurarEncabezadoJTable(){
        jTableLibros.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 14));
        jTableLibros.getTableHeader().setOpaque(false);
        jTableLibros.getTableHeader().setForeground(Color.BLACK);
        
    }
    
    private void cargarCboBoxTema(){
        
        try {
            listaTemas = materiaDAO.obtenerTodosTemas();
            //materiaDAO.ordenarTemasXDescripcionASC(listaMaterias, 0, listaMaterias.size() - 1);
            
            for (int i = 0; i < listaTemas.size(); i++) {
                cboTema.addItem(listaTemas.get(i).getId()+ " - " + listaTemas.get(i).getDescripcion());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    private void cargarCboBoxIdioma(){
        
        try {
            listaIdiomas = idiomaDAO.obtenerIdiomas();
            //materiaDAO.ordenarTemasXDescripcionASC(listaMaterias, 0, listaMaterias.size() - 1);
            
            for (int i = 0; i < listaIdiomas.size(); i++) {
                cboIdioma.addItem(listaIdiomas.get(i).getIdidioma()+ " - " + listaIdiomas.get(i).getDescripcion());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    private void llenarJTableLibros(){
        DefaultTableModel dtm = new DefaultTableModel();
        
        listaLibros = new ArrayList<>();
        
        try {
            listaLibros = libroDAO.obtenerLibros();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Hubo un problema al cargar los datos de libros.");
        }
        
        String[] cabecera = {"Código", "ISBN", "Título", "Autor", "Tema", "Año", "Idioma"}; 
        dtm.setColumnIdentifiers(cabecera);
        dtm.setColumnCount(cabecera.length);
        Object[] dataLibros = new Object[dtm.getColumnCount()];
        
        for (int i = 0; i < listaLibros.size(); i++) {
            dataLibros[0] = listaLibros.get(i).getId();
            dataLibros[1] = listaLibros.get(i).getIsbn();
            dataLibros[2] = listaLibros.get(i).getTitulo();
            dataLibros[3] = listaLibros.get(i).getAutores();
            dataLibros[4] = listaLibros.get(i).getTema().getDescripcion();
            dataLibros[5] = listaLibros.get(i).getAniopublicacion();
            dataLibros[6] = listaLibros.get(i).getIdioma().getDescripcion();
            dtm.addRow(dataLibros);
        }
        jTableLibros.setModel(dtm);
    }
    
    private void llenarJTableLibros(ArrayList<Libro> libros){
        
        DefaultTableModel dtm = new DefaultTableModel();
        
        String[] cabecera = {"Código", "ISBN", "Título", "Autor", "Tema", "Año", "Idioma"}; 
        dtm.setColumnIdentifiers(cabecera);
        dtm.setColumnCount(cabecera.length);
        Object[] dataLibros = new Object[dtm.getColumnCount()];
        
        for (int i = 0; i < libros.size(); i++) {
            dataLibros[0] = listaLibros.get(i).getId();
            dataLibros[1] = listaLibros.get(i).getIsbn();
            dataLibros[2] = listaLibros.get(i).getTitulo();
            dataLibros[3] = listaLibros.get(i).getAutores();
            dataLibros[4] = listaLibros.get(i).getTema().getDescripcion();
            dataLibros[5] = listaLibros.get(i).getAniopublicacion();
            dataLibros[6] = listaLibros.get(i).getIdioma().getDescripcion();
            dtm.addRow(dataLibros);
        }
        jTableLibros.setModel(dtm);
    }
    
    public void irAGUIRegistrarLibro(){
        GUIPrincipal.vacearDktpPane();
        
        GUIRegistroLibro guiRegistroLibro = new GUIRegistroLibro();
        GUIPrincipal.agregarJframe(guiRegistroLibro);
        guiRegistroLibro.setVisible(true);
    }
    
    public void irAGUIAgregarEjemplar(){
        GUIPrincipal.vacearDktpPane();
        
        GUIAgregarNuevoEjemplar guiAgregarNuevoEjemplar = new GUIAgregarNuevoEjemplar();
        GUIPrincipal.agregarJframe(guiAgregarNuevoEjemplar);
        guiAgregarNuevoEjemplar.setVisible(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGrpOrdenamiento = new javax.swing.ButtonGroup();
        jPaneEstudiante = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        txtBusquedaTitulo = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableLibros = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        lblCodigo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtIsbn = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtAutor = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cboTema = new javax.swing.JComboBox<>();
        btnEliminar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnNuevoEjemplar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtAnio = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnNuevoLibro = new javax.swing.JButton();
        cboIdioma = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        rdbtTituloDESC = new javax.swing.JRadioButton();
        rdbtTituloASC = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        rdbtAnioASC = new javax.swing.JRadioButton();
        rdbtAnioDESC = new javax.swing.JRadioButton();

        setPreferredSize(new java.awt.Dimension(900, 530));

        jPaneEstudiante.setPreferredSize(new java.awt.Dimension(900, 530));
        jPaneEstudiante.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        jLabel31.setText("Buscar título:");
        jPaneEstudiante.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, -1));

        txtBusquedaTitulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBusquedaTituloKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaTituloKeyReleased(evt);
            }
        });
        jPaneEstudiante.add(txtBusquedaTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 420, -1));

        jTableLibros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableLibros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableLibrosMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTableLibros);

        jPaneEstudiante.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 130, 600, 350));

        lblCodigo.setBackground(new java.awt.Color(204, 204, 204));
        lblCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCodigo.setOpaque(true);
        jPaneEstudiante.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 250, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Datos de libro");
        jPaneEstudiante.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("ISBN");
        jPaneEstudiante.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Ordenar por título:");
        jPaneEstudiante.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, -1, -1));
        jPaneEstudiante.add(txtIsbn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 250, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Título");
        jPaneEstudiante.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));
        jPaneEstudiante.add(txtTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 250, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Autor");
        jPaneEstudiante.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));
        jPaneEstudiante.add(txtAutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 250, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Tema");
        jPaneEstudiante.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        cboTema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTemaActionPerformed(evt);
            }
        });
        jPaneEstudiante.add(cboTema, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 250, -1));

        btnEliminar.setText("Eliminar");
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
        });
        jPaneEstudiante.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 390, 130, 40));

        btnGuardar.setText("Guardar");
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
        });
        jPaneEstudiante.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 120, 40));

        btnNuevoEjemplar.setText("Nuevo ejemplar");
        btnNuevoEjemplar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNuevoEjemplarMouseClicked(evt);
            }
        });
        jPaneEstudiante.add(btnNuevoEjemplar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 440, 130, 40));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Idioma");
        jPaneEstudiante.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));
        jPaneEstudiante.add(txtAnio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 250, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Año de publicación");
        jPaneEstudiante.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));

        btnNuevoLibro.setText("Nuevo libro");
        btnNuevoLibro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNuevoLibroMouseClicked(evt);
            }
        });
        jPaneEstudiante.add(btnNuevoLibro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 120, 40));

        cboIdioma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboIdiomaActionPerformed(evt);
            }
        });
        jPaneEstudiante.add(cboIdioma, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 250, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Código");
        jPaneEstudiante.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        rdbtTituloDESC.setText("Descendente");
        rdbtTituloDESC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtTituloDESCActionPerformed(evt);
            }
        });
        jPaneEstudiante.add(rdbtTituloDESC, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 100, -1, -1));

        rdbtTituloASC.setText("Ascendente");
        rdbtTituloASC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtTituloASCActionPerformed(evt);
            }
        });
        jPaneEstudiante.add(rdbtTituloASC, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Ordenar por año de publicación:");
        jPaneEstudiante.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 50, -1, -1));

        rdbtAnioASC.setText("Ascendente");
        rdbtAnioASC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtAnioASCActionPerformed(evt);
            }
        });
        jPaneEstudiante.add(rdbtAnioASC, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 70, -1, -1));

        rdbtAnioDESC.setText("Descendente");
        rdbtAnioDESC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtAnioDESCActionPerformed(evt);
            }
        });
        jPaneEstudiante.add(rdbtAnioDESC, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 100, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPaneEstudiante, javax.swing.GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPaneEstudiante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBusquedaTituloKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaTituloKeyReleased
        
    }//GEN-LAST:event_txtBusquedaTituloKeyReleased

    private void jTableLibrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableLibrosMouseClicked
        int filaSeleccionada = 0;
        
        if(jTableLibros.getSelectedRow() != -1){
            filaSeleccionada = jTableLibros.getSelectedRow();
            lblCodigo.setText(jTableLibros.getValueAt(filaSeleccionada, 0) + "");
            txtIsbn.setText(jTableLibros.getValueAt(filaSeleccionada, 1) + "");
            txtTitulo.setText(jTableLibros.getValueAt(filaSeleccionada, 2) + "");
            txtAutor.setText(jTableLibros.getValueAt(filaSeleccionada, 3) + "");
            txtAnio.setText(jTableLibros.getValueAt(filaSeleccionada, 5) + "");
        }
    }//GEN-LAST:event_jTableLibrosMouseClicked

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        // TODO add your handling code here:
        try {
            Libro objLibro = new Libro(); 

            objLibro.setId(Integer.parseInt(lblCodigo.getText()));
            objLibro.setIsbn(txtIsbn.getText().trim());
            objLibro.setTitulo(txtTitulo.getText().trim());
            objLibro.setAutores(txtAutor.getText().trim());
            
            String detalle_tema[] = cboTema.getSelectedItem().toString().split(" - ");
            String detalle_idioma[] = cboIdioma.getSelectedItem().toString().split(" - ");
            
            objLibro.getTema().setId(Integer.parseInt(detalle_tema[0]));
            objLibro.getIdioma().setIdidioma(Integer.parseInt(detalle_idioma[0]));
            
            objLibro.setAniopublicacion(Integer.parseInt(txtAnio.getText().trim()));

            libroDAO.actualizar(objLibro);

            JOptionPane.showMessageDialog(this, String.format("Datos del libro %s actualizados correctamente.", objLibro.getTitulo()));

            establecerPorDefecto();
        } catch (ExcepcionPropia | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(this, "Asegúrese de seleccionar un registro de la tabla.");
            JOptionPane.showMessageDialog(this, "Asegúrese de seleccionar un registro de la tabla y colocada adecuadamente los datos en los campos.");
        }
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        // TODO add your handling code here:
        
        int opcion = JOptionPane.showConfirmDialog(this,"¿Está seguro de que desea eliminar este registro?", "Confirmación de eliminación de datos de estudiante", JOptionPane.YES_NO_OPTION);
        
        if(opcion == JOptionPane.YES_OPTION){
            try {
                Libro objLibro = new Libro();

                objLibro.setId(Integer.parseInt(lblCodigo.getText()));
                objLibro.setTitulo(txtTitulo.getText().trim());

                libroDAO.eliminar(objLibro);

                JOptionPane.showMessageDialog(this, String.format("Datos del libro %s eliminados correctamente.", objLibro.getTitulo()));

                establecerPorDefecto();
            } catch (ExcepcionPropia | SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Asegúrese de seleccionar un registro de la tabla.");
            }
        }
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void txtBusquedaTituloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaTituloKeyPressed
        // TODO add your handling code here:
        if(!(txtBusquedaTitulo.getText().equals(""))){
            try {
                llenarJTableLibros(libroDAO.obtenerLibrosXTitulo(txtBusquedaTitulo.getText().trim()));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }//GEN-LAST:event_txtBusquedaTituloKeyPressed

    private void btnNuevoLibroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoLibroMouseClicked
        // TODO add your handling code here:
        irAGUIRegistrarLibro();
    }//GEN-LAST:event_btnNuevoLibroMouseClicked

    private void cboTemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTemaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTemaActionPerformed

    private void cboIdiomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboIdiomaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboIdiomaActionPerformed

    private void btnNuevoEjemplarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoEjemplarMouseClicked
        // TODO add your handling code here:
        irAGUIAgregarEjemplar();
    }//GEN-LAST:event_btnNuevoEjemplarMouseClicked

    private void rdbtTituloDESCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbtTituloDESCActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            listaLibros = libroDAO.obtenerLibros();
            
            libroDAO.ordenarLibrosXTituloDESC(listaLibros);
            llenarJTableLibros(listaLibros);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_rdbtTituloDESCActionPerformed

    private void rdbtTituloASCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbtTituloASCActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            listaLibros = libroDAO.obtenerLibros();
            
            libroDAO.ordenarLibrosXTituloASC(listaLibros);
            llenarJTableLibros(listaLibros);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_rdbtTituloASCActionPerformed

    private void rdbtAnioASCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbtAnioASCActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            listaLibros = libroDAO.obtenerLibros();
            
            libroDAO.ordenarLibrosXAnioASC(listaLibros);
            llenarJTableLibros(listaLibros);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_rdbtAnioASCActionPerformed

    private void rdbtAnioDESCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbtAnioDESCActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            listaLibros = libroDAO.obtenerLibros();
            
            libroDAO.ordenarLibrosXAnioDESC(listaLibros);
            llenarJTableLibros(listaLibros);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_rdbtAnioDESCActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.ButtonGroup btnGrpOrdenamiento;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevoEjemplar;
    private javax.swing.JButton btnNuevoLibro;
    private javax.swing.JComboBox<String> cboIdioma;
    private javax.swing.JComboBox<String> cboTema;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPaneEstudiante;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTableLibros;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JRadioButton rdbtAnioASC;
    private javax.swing.JRadioButton rdbtAnioDESC;
    private javax.swing.JRadioButton rdbtTituloASC;
    private javax.swing.JRadioButton rdbtTituloDESC;
    private javax.swing.JTextField txtAnio;
    private javax.swing.JTextField txtAutor;
    private javax.swing.JTextField txtBusquedaTitulo;
    private javax.swing.JTextField txtIsbn;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
