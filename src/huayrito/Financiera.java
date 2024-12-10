
package huayrito;

import Conexion.Conexion;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Financiera extends javax.swing.JFrame {

    Conexion conexion = new Conexion();
    Connection cn = conexion.ConectarBD();
    
    public Financiera() {
        initComponents();
        cargarEmpleados();
        actualizarTablaFinanzas();
    }
    
    private void cargarEmpleados() {
        String sql = "SELECT Nombre FROM empleados";

        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            comboEmpleado.removeAllItems();
            comboEmpleado.addItem("Seleccione un empleado");

            while (rs.next()) {
                comboEmpleado.addItem(rs.getString("Nombre"));
            }
        } catch (Exception e) {
            System.out.println("Error al llenar el combo de empleados: " + e);
        }
    }

    private int obtenerIdEmpleado(String empleado, Connection cn) {
        String sql = "SELECT ID_Empleado FROM empleados WHERE Nombre = ?";
        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql)) {
            pst.setString(1, empleado);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID_Empleado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener ID del empleado: " + e.getMessage());
        }
        return -1;
    }

    private void actualizarTablaFinanzas() {
        String[] columnas = {"ID", "Fecha", "Total Ventas", "Total Gastos", "Empleado"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        String sql = "SELECT f.ID_Finanza, f.Fecha, f.Total_Ventas, f.Total_Gastos, e.Nombre "
                   + "FROM finanzas f JOIN empleados e ON f.ID_Empleado = e.ID_Empleado";

        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("ID_Finanza");
                String fecha = rs.getString("Fecha");
                String totalVentas = rs.getString("Total_Ventas");
                String totalGastos = rs.getString("Total_Gastos");
                String empleado = rs.getString("Nombre");
                modelo.addRow(new Object[]{id, fecha, totalVentas, totalGastos, empleado});
            }
            tablaFinanzas.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        }
    }
    
    public void editarFinanza(String nuevaFecha, double nuevoTotalVentas, double nuevoTotalGastos,
                           String nuevoEmpleado, String fechaActual, double totalVentasActual,
                           double totalGastosActual, String empleadoActual) {
        try {
            int idEmpleadoNuevo = obtenerIdEmpleado(nuevoEmpleado, cn);
            int idEmpleadoActual = obtenerIdEmpleado(empleadoActual, cn);

            if (idEmpleadoNuevo == -1 || idEmpleadoActual == -1) {
                JOptionPane.showMessageDialog(null, "No se pudo obtener el ID de un empleado");
                return;
            }

            String sql = "UPDATE finanzas SET Fecha = ?, Total_Ventas = ?, Total_Gastos = ?, ID_Empleado = ? "
                       + "WHERE Fecha = ? AND Total_Ventas = ? AND Total_Gastos = ? AND ID_Empleado = ?";
            PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);

            pst.setString(1, nuevaFecha);
            pst.setDouble(2, nuevoTotalVentas);
            pst.setDouble(3, nuevoTotalGastos);
            pst.setInt(4, idEmpleadoNuevo);

            pst.setString(5, fechaActual);
            pst.setDouble(6, totalVentasActual);
            pst.setDouble(7, totalGastosActual);
            pst.setInt(8, idEmpleadoActual);

            int filasAfectadas = pst.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Finanza editada correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo editar la finanza");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar la finanza");
            System.out.println(e);
        }
    }

    public void eliminarFinanza(String fecha, double totalVentas, double totalGastos, String empleado) {
        try {
            int idEmpleado = obtenerIdEmpleado(empleado, cn);
            if (idEmpleado == -1) {
                JOptionPane.showMessageDialog(null, "No se pudo obtener el ID del empleado");
                return;
            }

            String sql = "DELETE FROM finanzas WHERE Fecha = ? AND Total_Ventas = ? AND Total_Gastos = ? AND ID_Empleado = ? LIMIT 1";
            PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);

            pst.setString(1, fecha);
            pst.setDouble(2, totalVentas);
            pst.setDouble(3, totalGastos);
            pst.setInt(4, idEmpleado);

            int filasAfectadas = pst.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Finanza eliminada correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la finanza para eliminar");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la finanza: " + e.getMessage());
            System.out.println(e);
        }
    }
    
    public static boolean validarNumeroPositivo(String numero) {
        try {
            double valor = Double.parseDouble(numero);
            return valor > 0;
        } catch (NumberFormatException e) {
            return false;
        }
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
        txtTotalVentas = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtTotalGastos = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaFinanzas = new javax.swing.JTable();
        comboEmpleado = new javax.swing.JComboBox<>();

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
        jLabel4.setText("FINANCIERA");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel10.setText("Registrar Transacción:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Fecha:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, -1, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Total Ventas:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, 30));
        jPanel1.add(txtTotalVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 300, 30));
        jPanel1.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 300, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel5.setText("Historial Financiero:");
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
        jLabel14.setText("Empleado:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 260, -1, 30));
        jPanel1.add(txtTotalGastos, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 200, 300, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Total Gastos:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 200, 80, 30));

        tablaFinanzas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Fecha", "Total Ventas", "Total Gastos", "ID_Empleado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaFinanzas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaFinanzasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaFinanzas);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 850, 250));

        jPanel1.add(comboEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 260, 300, 30));

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
        String fecha = txtFecha.getText();
        String totalVentasStr = txtTotalVentas.getText();
        String totalGastosStr = txtTotalGastos.getText();
        String empleado = (String) comboEmpleado.getSelectedItem();

        if (fecha.isEmpty() || totalVentasStr.isEmpty() || totalGastosStr.isEmpty() || "Seleccione un empleado".equals(empleado)) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos y seleccione un empleado válido.");
            return;
        }

        double totalVentas, totalGastos;
        
        if (!validarNumeroPositivo(totalVentasStr) || !validarNumeroPositivo(totalGastosStr)) {
            JOptionPane.showMessageDialog(this, "El total de ventas y gastos deben ser números válidos y mayores que cero.");
            return;
        }
        
        totalVentas = Double.parseDouble(totalVentasStr);
        totalGastos = Double.parseDouble(totalGastosStr);

        Conexion conexion = new Conexion();
        Connection cn = conexion.ConectarBD();

        int idEmpleado = obtenerIdEmpleado(empleado, cn);
        if (idEmpleado == -1) {
            JOptionPane.showMessageDialog(this, "Error al obtener el ID del empleado.");
            return;
        }

        String sql = "INSERT INTO finanzas (Fecha, Total_Ventas, Total_Gastos, ID_Empleado) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql)) {
            pst.setString(1, fecha);
            pst.setDouble(2, totalVentas);
            pst.setDouble(3, totalGastos);
            pst.setInt(4, idEmpleado);

            int resultado = pst.executeUpdate();

            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Finanza registrada exitosamente.");
                txtFecha.setText("");
                txtTotalVentas.setText("");
                txtTotalGastos.setText("");
                comboEmpleado.setSelectedIndex(0);
                actualizarTablaFinanzas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar la finanza.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int filaSeleccionada = tablaFinanzas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una finanza de la tabla");
        } else {
            String fechaActual = (String) tablaFinanzas.getValueAt(filaSeleccionada, 1);
            double totalVentasActual = Double.parseDouble(tablaFinanzas.getValueAt(filaSeleccionada, 2).toString());
            double totalGastosActual = Double.parseDouble(tablaFinanzas.getValueAt(filaSeleccionada, 3).toString());
            String empleadoActual = (String) tablaFinanzas.getValueAt(filaSeleccionada, 4);

            String nuevaFecha = txtFecha.getText();
            double nuevoTotalVentas = Double.parseDouble(txtTotalVentas.getText());
            double nuevoTotalGastos = Double.parseDouble(txtTotalGastos.getText());
            String nuevoEmpleado = (String) comboEmpleado.getSelectedItem();

            editarFinanza(nuevaFecha, nuevoTotalVentas, nuevoTotalGastos, nuevoEmpleado, 
                          fechaActual, totalVentasActual, totalGastosActual, empleadoActual);

            actualizarTablaFinanzas();
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tablaFinanzas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una finanza de la tabla");
        } else {
            String fecha = (String) tablaFinanzas.getValueAt(filaSeleccionada, 1);
            double totalVentas = Double.parseDouble(tablaFinanzas.getValueAt(filaSeleccionada, 2).toString());
            double totalGastos = Double.parseDouble(tablaFinanzas.getValueAt(filaSeleccionada, 3).toString());
            String empleado = (String) tablaFinanzas.getValueAt(filaSeleccionada, 4);

            eliminarFinanza(fecha, totalVentas, totalGastos, empleado);

            actualizarTablaFinanzas();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tablaFinanzasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaFinanzasMouseClicked
        int filaSeleccionada = tablaFinanzas.getSelectedRow();
        if (filaSeleccionada != -1) {
            String fecha = (String) tablaFinanzas.getValueAt(filaSeleccionada, 1);
            String totalVentas = tablaFinanzas.getValueAt(filaSeleccionada, 2).toString();
            String totalGastos = tablaFinanzas.getValueAt(filaSeleccionada, 3).toString();
            String empleado = (String) tablaFinanzas.getValueAt(filaSeleccionada, 4);

            txtFecha.setText(fecha);
            txtTotalVentas.setText(totalVentas);
            txtTotalGastos.setText(totalGastos);
            comboEmpleado.setSelectedItem(empleado);
        }
    }//GEN-LAST:event_tablaFinanzasMouseClicked


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
    private javax.swing.JComboBox<String> comboEmpleado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaFinanzas;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtTotalGastos;
    private javax.swing.JTextField txtTotalVentas;
    // End of variables declaration//GEN-END:variables
}
