
package huayrito;

import Conexion.Conexion;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Cliente extends javax.swing.JFrame {
    
    Conexion conexion = new Conexion();
    Connection cn = conexion.ConectarBD();
    
    public Cliente() {
        initComponents();
        actualizarTablaClientes();
    }
    
    private void actualizarTablaClientes() {
        String[] columnas = {"ID", "Nombre", "Teléfono", "Email", "Dirección"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        String sql = "SELECT ID_Cliente, Nombre, Teléfono, Email, Dirección from clientes";

        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("ID_Cliente");
                String nombre = rs.getString("Nombre");
                String telefono = rs.getString("Teléfono");
                String email = rs.getString("Email");
                String direccion = rs.getString("Dirección");
                modelo.addRow(new Object[]{id, nombre, telefono, email, direccion});
            }
            tablaClientes.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        }
    }
    
    public void eliminarCliente(String nombre, String telefono, String email) { 
        try {
            String sql = "DELETE FROM clientes WHERE Nombre = ? AND Teléfono = ? AND Email = ? LIMIT 1";
            PreparedStatement p1 = (PreparedStatement) cn.prepareStatement(sql);

            p1.setString(1, nombre);
            p1.setString(2, telefono);
            p1.setString(3, email);

            int filasAfectadas = p1.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el cliente para eliminar");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el cliente: " + e.getMessage());
            System.out.println(e);
        }
    }
    
    public static boolean validarNombre(String nombre) {
        String regex = "^[a-zA-Z\\s]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nombre);
        return matcher.matches();
    }

    public static boolean validarTelefono(String telefono) {
        String regex = "^\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(telefono);
        return matcher.matches();
    }

    public static boolean validarEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validarDireccion(String direccion) {
        String regex = "^[a-zA-Z0-9\\s,.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(direccion);
        return matcher.matches();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnReservas = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnFinanciera = new javax.swing.JButton();
        btnCliente = new javax.swing.JButton();
        btnPedidos = new javax.swing.JButton();
        btnEmpleados = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(195, 79, 79));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\USER\\Downloads\\logo_Huayrito (1).png")); // NOI18N
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 220, 220));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 800));

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel4.setText("CLIENTES");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel10.setText("Registrar Cliente:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Nombre:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Telefono:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel5.setText("Lista de Clientes:");
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

        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Telefono", "Email", "Direccion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaClientes);

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

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Dirección:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 260, -1, 30));
        jPanel1.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 260, 300, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Electrónico:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 210, -1, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Correo");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 190, 50, 30));
        jPanel1.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 200, 300, 30));
        jPanel1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 300, 30));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 300, 30));

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

    private void btnReservasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservasActionPerformed
        Reservas p1 = new Reservas();
        p1.setVisible(true);
        p1.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnReservasActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        Productos p1 = new Productos();
        p1.setVisible(true);
        p1.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnFinancieraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinancieraActionPerformed
        Financiera p1 = new Financiera();
        p1.setVisible(true);
        p1.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnFinancieraActionPerformed

    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
        Cliente p1 = new Cliente();
        p1.setVisible(true);
        p1.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnClienteActionPerformed

    private void btnPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedidosActionPerformed
        Pedidos p1 = new Pedidos();
        p1.setVisible(true);
        p1.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnPedidosActionPerformed

    private void btnEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpleadosActionPerformed
        Empleados p1 = new Empleados();
        p1.setVisible(true);
        p1.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnEmpleadosActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        
        String nombre = txtNombre.getText();
        String telefono = txtTelefono.getText();
        String email = txtCorreo.getText();
        String direccion = txtDireccion.getText();

        if (nombre.isEmpty() || telefono.isEmpty() || email.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos");
            return;
        }
        
        if (!validarNombre(nombre)) {
            JOptionPane.showMessageDialog(this, "El nombre solo puede contener letras y espacios.");
            return;
        }

        if (!validarTelefono(telefono)) {
            JOptionPane.showMessageDialog(this, "El teléfono debe contener 9 dígitos.");
            return;
        }

        if (!validarEmail(email)) {
            JOptionPane.showMessageDialog(this, "El correo electrónico no tiene un formato válido.");
            return;
        }

        if (!validarDireccion(direccion)) {
            JOptionPane.showMessageDialog(this, "La dirección contiene caracteres no válidos.");
            return;
        }
    
        String sql = "INSERT INTO clientes (Nombre, Teléfono, Email, Dirección) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql)) {
            pst.setString(1, nombre);
            pst.setString(2, telefono);
            pst.setString(3, email);
            pst.setString(4, direccion);

            int resultado = pst.executeUpdate();

            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Cliente registrado exitosamente.");
                txtNombre.setText("");
                txtTelefono.setText("");
                txtCorreo.setText("");
                txtDireccion.setText("");
                actualizarTablaClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar cliente.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        String nombre = txtNombre.getText();
        String telefono = txtTelefono.getText();
        String email = txtCorreo.getText();
        String direccion = txtDireccion.getText();

        if (nombre.isEmpty() || telefono.isEmpty() || email.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos");
            return;
        }

        int row = tablaClientes.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para editar.");
            return;
        }

        String id = tablaClientes.getValueAt(row, 0).toString();

        String sql = "UPDATE clientes SET Nombre = ?, Teléfono = ?, Email = ?, Dirección = ? WHERE ID_Cliente = ?";

        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql)) {
            pst.setString(1, nombre);
            pst.setString(2, telefono);
            pst.setString(3, email);
            pst.setString(4, direccion);
            pst.setString(5, id);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado con éxito.");
                actualizarTablaClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el cliente.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al editar el cliente: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente de la tabla");
        } else {
            String nombre = (String) tablaClientes.getValueAt(filaSeleccionada, 1);
            String telefono = (String) tablaClientes.getValueAt(filaSeleccionada, 2);
            String email = (String) tablaClientes.getValueAt(filaSeleccionada, 3);

            int opcion = JOptionPane.showConfirmDialog(
                null, 
                "¿Estás seguro de que deseas eliminar al cliente " + nombre + "?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {
                eliminarCliente(nombre, telefono, email);
                actualizarTablaClientes();
                JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Eliminación cancelada.");
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tablaClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaClientesMouseClicked
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente de la tabla");
        } else {
            String nombre = (String) tablaClientes.getValueAt(filaSeleccionada, 1);
            String telefono = (String) tablaClientes.getValueAt(filaSeleccionada, 2);
            String email = (String) tablaClientes.getValueAt(filaSeleccionada, 3);
            String direccion = (String) tablaClientes.getValueAt(filaSeleccionada, 4);

            txtNombre.setText(nombre);
            txtTelefono.setText(telefono);
            txtCorreo.setText(email);
            txtDireccion.setText(direccion);
        }
    }//GEN-LAST:event_tablaClientesMouseClicked


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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
