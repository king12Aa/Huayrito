
package huayrito;

import Conexion.Conexion;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Productos extends javax.swing.JFrame {
    
    Conexion conexion = new Conexion();
    Connection cn = conexion.ConectarBD();
    
    public Productos() {
        initComponents();
        cargarCategorias();
        actualizarTablaProductos();
    }
    
    private void cargarCategorias() {
        String sql = "SELECT Nombre_Categoria FROM categorías";
        
        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            comboCategoria.removeAllItems();
            comboCategoria.addItem("Seleccione una categoría");

            while (rs.next()) {
                comboCategoria.addItem(rs.getString("Nombre_Categoria"));
            }
        } catch (Exception e) {
            System.out.println("Error al llenar el combo: " + e);
        }
    }
    
    private int obtenerIdCategoria(String categoria, Connection cn) {
        String sql = "SELECT ID_Categoria FROM categorías WHERE Nombre_Categoria = ?";
        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql)) {
            pst.setString(1, categoria);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID_Categoria");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener ID de la categoría: " + e.getMessage());
        }
        return -1;
    }
    
    private void actualizarTablaProductos() {
        String[] columnas = {"ID", "Nombre", "Precio", "Stock", "Categoría"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        String sql = "SELECT p.ID_Producto, p.Nombre_Producto, p.Precio, p.Stock, c.Nombre_Categoria "
                   + "FROM productos p JOIN categorías c ON p.ID_Categoria = c.ID_Categoria";

        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("ID_Producto");
                String nombre = rs.getString("Nombre_Producto");
                String precio = rs.getString("Precio");
                String stock = rs.getString("Stock");
                String categoria = rs.getString("Nombre_Categoria");
                modelo.addRow(new Object[]{id, nombre, precio, stock, categoria});
            }
            tablaProductos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        }
    }
    
    public void editarProducto(String nombreNuevo, double precioNuevo, int stockNuevo, String categoriaNueva,
                           String nombreActual, double precioActual, int stockActual, String categoriaActual) {
        try {
            int idCategoriaNueva = obtenerIdCategoria(categoriaNueva, cn);
            int idCategoriaActual = obtenerIdCategoria(categoriaActual, cn);

            if (idCategoriaNueva == -1 || idCategoriaActual == -1) {
                JOptionPane.showMessageDialog(null, "No se pudo obtener el ID de una o más categorías");
                return;
            }

            String sql = "UPDATE productos SET Nombre_Producto = ?, Precio = ?, Stock = ?, ID_Categoria = ? " +
                         "WHERE Nombre_Producto = ? AND Precio = ? AND Stock = ? AND ID_Categoria = ?";
            PreparedStatement p1 = (PreparedStatement) cn.prepareStatement(sql);

            p1.setString(1, nombreNuevo);
            p1.setDouble(2, precioNuevo);
            p1.setInt(3, stockNuevo);
            p1.setInt(4, idCategoriaNueva);

            p1.setString(5, nombreActual);
            p1.setDouble(6, precioActual);
            p1.setInt(7, stockActual);
            p1.setInt(8, idCategoriaActual);

            int filasAfectadas = p1.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Producto editado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo editar el producto");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar el producto");
            System.out.println(e);
        }
    }
    
    public void eliminarProducto(String nombre, double precio, int stock, String categoria) {
        try {
            int idCategoria = obtenerIdCategoria(categoria, cn);
            if (idCategoria == -1) {
                JOptionPane.showMessageDialog(null, "No se pudo obtener el ID de la categoría");
                return;
            }

            String sql = "DELETE FROM productos WHERE Nombre_Producto = ? AND Precio = ? AND Stock = ? AND ID_Categoria = ? LIMIT 1";
            PreparedStatement p1 = (PreparedStatement) cn.prepareStatement(sql);

            p1.setString(1, nombre);
            p1.setDouble(2, precio);
            p1.setInt(3, stock);
            p1.setInt(4, idCategoria);

            int filasAfectadas = p1.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Producto eliminado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el producto para eliminar");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + e.getMessage());
            System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnReservas = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnFinanciera = new javax.swing.JButton();
        btnCliente = new javax.swing.JButton();
        btnPedidos = new javax.swing.JButton();
        btnEmpleados = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        comboCategoria = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(195, 79, 79));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\USER\\Downloads\\logo_Huayrito (1).png")); // NOI18N
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 220, 220));

        btnReservas.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnReservas.setText("RESERVAS");
        btnReservas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservasActionPerformed(evt);
            }
        });
        jPanel3.add(btnReservas, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, 240, 50));

        btnProductos.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnProductos.setText("PRODUCTOS");
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });
        jPanel3.add(btnProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 240, 50));

        btnFinanciera.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnFinanciera.setText("FINANCIERA");
        btnFinanciera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinancieraActionPerformed(evt);
            }
        });
        jPanel3.add(btnFinanciera, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 690, 240, 50));

        btnCliente.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnCliente.setText("CLIENTES");
        btnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });
        jPanel3.add(btnCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 530, 240, 50));

        btnPedidos.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnPedidos.setText("PEDIDOS");
        btnPedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPedidosActionPerformed(evt);
            }
        });
        jPanel3.add(btnPedidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, 240, 50));

        btnEmpleados.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnEmpleados.setText("EMPLEADOS");
        btnEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpleadosActionPerformed(evt);
            }
        });
        jPanel3.add(btnEmpleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 610, 240, 50));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 800));

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel4.setText("PRODUCTOS");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel10.setText("Registrar Producto:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Nombre:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, -1, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Precio:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, -1, 30));
        jPanel1.add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 300, 30));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 300, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel5.setText("Lista de Productos:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, -1, -1));

        btnEditar.setBackground(new java.awt.Color(255, 153, 51));
        btnEditar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 450, 250, -1));

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Categoria", "Precio", "Cantidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaProductos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 850, 250));

        btnEliminar.setBackground(new java.awt.Color(255, 153, 51));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 450, 250, -1));

        btnRegistrar.setBackground(new java.awt.Color(255, 153, 51));
        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 330, 250, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Categoria:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 260, -1, 30));

        jPanel1.add(comboCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 260, 300, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Stock:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 200, -1, 30));
        jPanel1.add(txtStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 200, 300, 30));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 920, 800));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        Productos p1 = new Productos();
        p1.setVisible(true);
        p1.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnReservasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservasActionPerformed
        Reservas p1 = new Reservas();
        p1.setVisible(true);
        p1.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnReservasActionPerformed

    private void btnPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedidosActionPerformed
        Pedidos p1 = new Pedidos();
        p1.setVisible(true);
        p1.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnPedidosActionPerformed

    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
        Cliente p1 = new Cliente();
        p1.setVisible(true);
        p1.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnClienteActionPerformed

    private void btnEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpleadosActionPerformed
        Empleados p1 = new Empleados();
        p1.setVisible(true);
        p1.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnEmpleadosActionPerformed

    private void btnFinancieraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinancieraActionPerformed
        Financiera p1 = new Financiera();
        p1.setVisible(true);
        p1.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnFinancieraActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        String nombre = txtNombre.getText();
        String precio = txtPrecio.getText();
        String stockStr = txtStock.getText();
        String categoria = (String) comboCategoria.getSelectedItem();

        if (nombre.isEmpty() || precio.isEmpty() || stockStr.isEmpty() || "Seleccione una categoría".equals(categoria)) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos y seleccione una categoría válida.");
            return;
        }

        int stock;
        try {
            stock = Integer.parseInt(stockStr);
            if (stock < 0) {
                JOptionPane.showMessageDialog(this, "El stock debe ser un número mayor o igual a cero.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El stock debe ser un número válido.");
            return;
        }

        Conexion conexion = new Conexion();
        Connection cn = conexion.ConectarBD();
        
        int idCategoria = obtenerIdCategoria(categoria, cn);
        if (idCategoria == -1) {
            JOptionPane.showMessageDialog(this, "Error al obtener el ID de la categoría.");
            return;
        }
        
        String sql = "INSERT INTO productos (Nombre_Producto, Precio, Stock, ID_Categoria) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql)) {
            pst.setString(1, nombre);
            pst.setDouble(2, Double.parseDouble(precio));
            pst.setInt(3, stock);
            pst.setInt(4, idCategoria);

            int resultado = pst.executeUpdate();

            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Producto registrado exitosamente.");
                txtNombre.setText("");
                txtPrecio.setText("");
                txtStock.setText("");
                comboCategoria.setSelectedIndex(0);
                actualizarTablaProductos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el producto.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto de la tabla");
        } else {
            String nombreActual = (String) tablaProductos.getValueAt(filaSeleccionada, 1);
            double precioActual = Double.parseDouble(tablaProductos.getValueAt(filaSeleccionada, 2).toString());
            int stockActual = Integer.parseInt(tablaProductos.getValueAt(filaSeleccionada, 3).toString());
            String categoriaActual = (String) tablaProductos.getValueAt(filaSeleccionada, 4);

            String nombreNuevo = txtNombre.getText();
            double precioNuevo = Double.parseDouble(txtPrecio.getText());
            int stockNuevo = Integer.parseInt(txtStock.getText());
            String categoriaNueva = (String) comboCategoria.getSelectedItem();

            editarProducto(nombreNuevo, precioNuevo, stockNuevo, categoriaNueva, 
                           nombreActual, precioActual, stockActual, categoriaActual);

            actualizarTablaProductos();
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto de la tabla");
        } else {
            String nombre = (String) tablaProductos.getValueAt(filaSeleccionada, 1);
            double precio = Double.parseDouble(tablaProductos.getValueAt(filaSeleccionada, 2).toString());
            int stock = Integer.parseInt(tablaProductos.getValueAt(filaSeleccionada, 3).toString());
            String categoria = (String) tablaProductos.getValueAt(filaSeleccionada, 4);

            eliminarProducto(nombre, precio, stock, categoria);

            actualizarTablaProductos();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tablaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductosMouseClicked
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto de la tabla");
        } else {
            String nombre = (String) tablaProductos.getValueAt(filaSeleccionada, 1);
            String precio = tablaProductos.getValueAt(filaSeleccionada, 2).toString();
            String stock = tablaProductos.getValueAt(filaSeleccionada, 3).toString();
            String categoria = (String) tablaProductos.getValueAt(filaSeleccionada, 4);

            txtNombre.setText(nombre);
            txtPrecio.setText(precio);
            txtStock.setText(stock);
            comboCategoria.setSelectedItem(categoria);
        }
    }//GEN-LAST:event_tablaProductosMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEmpleados;
    private javax.swing.JButton btnFinanciera;
    private javax.swing.JButton btnPedidos;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnReservas;
    private javax.swing.JComboBox<String> comboCategoria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables
}
