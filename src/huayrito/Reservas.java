
package huayrito;

import Conexion.Conexion;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Reservas extends javax.swing.JFrame {

    Conexion conexion = new Conexion();
    Connection cn = conexion.ConectarBD();

    public Reservas() {
        initComponents();
        cargarClientes();
        cargarMesas();
        actualizarTablaReservas();
    }

    private void cargarClientes() {
        String sql = "SELECT Nombre FROM clientes";

        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            comboCliente.removeAllItems();
            comboCliente.addItem("Seleccione un cliente");

            while (rs.next()) {
                comboCliente.addItem(rs.getString("Nombre"));
            }
        } catch (Exception e) {
            System.out.println("Error al llenar el combo de clientes: " + e);
        }
    }

    private void cargarMesas() {
        String sql = "SELECT ID_Mesa FROM mesas";

        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            comboMesa.removeAllItems();
            comboMesa.addItem("Seleccione una mesa");

            while (rs.next()) {
                comboMesa.addItem(rs.getString("ID_Mesa"));
            }
        } catch (Exception e) {
            System.out.println("Error al llenar el combo de mesas: " + e);
        }
    }

    private int obtenerIdCliente(String cliente, Connection cn) {
        String sql = "SELECT ID_Cliente FROM clientes WHERE Nombre = ?";
        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql)) {
            pst.setString(1, cliente);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID_Cliente");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener ID de cliente: " + e.getMessage());
        }
        return -1;
    }
    
    private int obtenerIdMesa(int mesa, Connection cn) {
        String sql = "SELECT ID_Mesa FROM mesas WHERE ID_Mesa = ?";
        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql)) {
            pst.setInt(1, mesa);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID_Mesa");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener ID de mesa: " + e.getMessage());
        }
        return -1;
    }

    private void actualizarTablaReservas() {
        String[] columnas = {"ID Reserva", "Fecha", "Hora", "Cliente", "Mesa"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        String sql = "SELECT r.ID_Reserva, r.Fecha_Reserva, r.Hora_Reserva, c.Nombre, m.ID_Mesa "
                   + "FROM reservas r "
                   + "JOIN clientes c ON r.ID_Cliente = c.ID_Cliente "
                   + "JOIN mesas m ON r.ID_Mesa = m.ID_Mesa";

        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("ID_Reserva");
                String fecha = rs.getString("Fecha_Reserva");
                String hora = rs.getString("Hora_Reserva");
                String cliente = rs.getString("Nombre");
                String mesa = rs.getString("ID_Mesa");
                modelo.addRow(new Object[]{id, fecha, hora, cliente, mesa});
            }
            tablaReservas.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las reservas: " + e.getMessage());
        }
    }
    
    public void editarReserva(String fechaNueva, String horaNueva, String clienteNuevo, int mesaNueva, 
                          String fechaActual, String horaActual, String clienteActual, int mesaActual) {
        try {
            int idClienteNuevo = obtenerIdCliente(clienteNuevo, cn);
            int idClienteActual = obtenerIdCliente(clienteActual, cn);
            int idMesaNueva = obtenerIdMesa(mesaNueva, cn);
            int idMesaActual = obtenerIdMesa(mesaActual, cn);

            if (idClienteNuevo == -1 || idMesaNueva == -1 || idClienteActual == -1 || idMesaActual == -1) {
                JOptionPane.showMessageDialog(null, "No se pudo obtener los IDs de los clientes o mesas.");
                return;
            }

            String sql = "UPDATE reservas SET Fecha_Reserva = ?, Hora_Reserva = ?, ID_Cliente = ?, ID_Mesa = ? " +
                         "WHERE Fecha_Reserva = ? AND Hora_Reserva = ? AND ID_Cliente = ? AND ID_Mesa = ?";
            PreparedStatement p1 = (PreparedStatement) cn.prepareStatement(sql);

            p1.setString(1, fechaNueva);
            p1.setString(2, horaNueva);
            p1.setInt(3, idClienteNuevo);
            p1.setInt(4, idMesaNueva);

            p1.setString(5, fechaActual);
            p1.setString(6, horaActual);
            p1.setInt(7, idClienteActual);
            p1.setInt(8, idMesaActual);

            int filasAfectadas = p1.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Reserva editada correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo editar la reserva");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar la reserva: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminarReserva(String fecha, String hora, String cliente, int mesa) {
        try {
            int idCliente = obtenerIdCliente(cliente, cn);
            int idMesa = obtenerIdMesa(mesa, cn);

            if (idCliente == -1 || idMesa == -1) {
                JOptionPane.showMessageDialog(null, "No se pudo obtener el ID de cliente o mesa");
                return;
            }

            String sql = "DELETE FROM reservas WHERE Fecha_Reserva = ? AND Hora_Reserva = ? AND ID_Cliente = ? AND ID_Mesa = ? LIMIT 1";
            PreparedStatement p1 = (PreparedStatement) cn.prepareStatement(sql);

            p1.setString(1, fecha);
            p1.setString(2, hora);
            p1.setInt(3, idCliente);
            p1.setInt(4, idMesa);

            int filasAfectadas = p1.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Reserva eliminada correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar la reserva");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la reserva: " + e.getMessage());
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
        jLabel7 = new javax.swing.JLabel();
        comboMesa = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtHoraReserva = new javax.swing.JTextField();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaReservas = new javax.swing.JTable();
        comboCliente = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtFechaReserva = new javax.swing.JTextField();

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
        jLabel4.setText("RESERVAS");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel10.setText("Registrar Reserva:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Mesa:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, -1, 30));

        jPanel1.add(comboMesa, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 300, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel5.setText("Lista de Reservas:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, -1, -1));

        btnRegistrar.setBackground(new java.awt.Color(255, 153, 51));
        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 330, 250, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Reserva:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 210, -1, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Fecha de");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 190, -1, 30));
        jPanel1.add(txtHoraReserva, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 260, 300, 30));

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

        tablaReservas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cliente", "Mesa", "Fecha de Reserva", "Hora de Reserva"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaReservas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaReservasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaReservas);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 850, 250));

        jPanel1.add(comboCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 300, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Cliente:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Reserva:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 270, -1, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Hora de");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 250, -1, 30));
        jPanel1.add(txtFechaReserva, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 200, 300, 30));

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
        String fecha = txtFechaReserva.getText();
        String hora = txtHoraReserva.getText();
        String cliente = (String) comboCliente.getSelectedItem();
        int mesa = Integer.parseInt((String) comboMesa.getSelectedItem());

        if (fecha.isEmpty() || hora.isEmpty() || "Seleccione un cliente".equals(cliente) || mesa == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos y seleccione una opción válida.");
            return;
        }

        int idCliente = obtenerIdCliente(cliente, cn);
        if (idCliente == -1) {
            JOptionPane.showMessageDialog(this, "Error al obtener el ID del cliente.");
            return;
        }

        int idMesa = obtenerIdMesa(mesa, cn);
        if (idMesa == -1) {
            JOptionPane.showMessageDialog(this, "Error al obtener el ID de la mesa.");
            return;
        }

        String sql = "INSERT INTO reservas (Fecha_Reserva, Hora_Reserva, ID_Cliente, ID_Mesa) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql)) {
            pst.setString(1, fecha);
            pst.setString(2, hora);
            pst.setInt(3, idCliente);
            pst.setInt(4, idMesa);

            int resultado = pst.executeUpdate();

            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Reserva registrada exitosamente.");
                txtFechaReserva.setText("");
                txtHoraReserva.setText("");
                comboCliente.setSelectedIndex(0);
                comboMesa.setSelectedIndex(0);
                actualizarTablaReservas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar la reserva.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int filaSeleccionada = tablaReservas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una reserva de la tabla");
        } else {
            String fechaActual = tablaReservas.getValueAt(filaSeleccionada, 1).toString();
            String horaActual = tablaReservas.getValueAt(filaSeleccionada, 2).toString();
            String clienteActual = tablaReservas.getValueAt(filaSeleccionada, 3).toString();
            int mesaActual = (Integer)tablaReservas.getValueAt(filaSeleccionada, 4);
            String fechaNueva = txtFechaReserva.getText();
            String horaNueva = txtHoraReserva.getText();
            String clienteNuevo = (String) comboCliente.getSelectedItem();
            int mesaNueva = (Integer) comboMesa.getSelectedItem();

            editarReserva(fechaNueva, horaNueva, clienteNuevo, mesaNueva, 
                          fechaActual, horaActual, clienteActual, mesaActual);

            actualizarTablaReservas();
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tablaReservas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una reserva de la tabla");
        } else {
            String fecha = tablaReservas.getValueAt(filaSeleccionada, 1).toString();
            String hora = tablaReservas.getValueAt(filaSeleccionada, 2).toString();
            String cliente = tablaReservas.getValueAt(filaSeleccionada, 3).toString();
            String mesa = tablaReservas.getValueAt(filaSeleccionada, 4).toString();

            eliminarReserva(fecha, hora, cliente, Integer.parseInt(mesa));

            actualizarTablaReservas();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tablaReservasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaReservasMouseClicked
        int filaSeleccionada = tablaReservas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una reserva de la tabla");
        } else {
            String fecha = tablaReservas.getValueAt(filaSeleccionada, 1).toString();
            String hora = tablaReservas.getValueAt(filaSeleccionada, 2).toString();
            String cliente = tablaReservas.getValueAt(filaSeleccionada, 3).toString();
            String mesa = tablaReservas.getValueAt(filaSeleccionada, 4).toString();

            txtFechaReserva.setText(fecha);
            txtHoraReserva.setText(hora);
            comboCliente.setSelectedItem(cliente);
            comboMesa.setSelectedItem(mesa);
        }
    }//GEN-LAST:event_tablaReservasMouseClicked

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
    private javax.swing.JComboBox<String> comboCliente;
    private javax.swing.JComboBox<String> comboMesa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JTable tablaReservas;
    private javax.swing.JTextField txtFechaReserva;
    private javax.swing.JTextField txtHoraReserva;
    // End of variables declaration//GEN-END:variables
}
