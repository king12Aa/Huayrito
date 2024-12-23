
package huayrito;

import Conexion.Conexion;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Pedidos extends javax.swing.JFrame {

    Conexion conexion = new Conexion();
    Connection cn = conexion.ConectarBD();
    
    public Pedidos() {
        initComponents();
        cargarClientes();
        cargarEmpleados();
        cargarProductos();
        actualizarTabla();
        actualizarTablaPedidos();
    }
    
    private void actualizarTablaPedidos() {
        String[] columnas = {"ID", "Cliente", "Empleado", "Fecha de pedido", "Total"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        String sql = "SELECT p.ID_Pedido, c.Nombre AS Cliente, e.Nombre AS Empleado, p.Fecha_Pedido, p.Total "
                   + "FROM pedidos p "
                   + "JOIN clientes c ON p.ID_Cliente = c.ID_Cliente "
                   + "JOIN empleados e ON p.ID_Empleado = e.ID_Empleado";

        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                String idPedido = rs.getString("ID_Pedido");
                String cliente = rs.getString("Cliente");
                String empleado = rs.getString("Empleado");
                String fechaPedido = rs.getString("Fecha_Pedido");
                String total = rs.getString("Total");

                modelo.addRow(new Object[]{idPedido, cliente, empleado, fechaPedido, total});
            }
            tablaPedidos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        }
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
            System.out.println("Error al llenar el combo: " + e);
        }
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
            System.out.println("Error al llenar el combo: " + e);
        }
    }
    
    private void cargarProductos() {
        String sql = "SELECT Nombre_Producto FROM productos";
        
        try (PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            comboProducto.removeAllItems();
            comboProducto.addItem("Seleccione un producto");

            while (rs.next()) {
                comboProducto.addItem(rs.getString("Nombre_Producto"));
            }
        } catch (Exception e) {
            System.out.println("Error al llenar el combo: " + e);
        }
    }
    
    private void actualizarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
        modelo.setRowCount(0);

        try {
            String sql = "SELECT * FROM detalles_pedido where ID_Pedido is null";
            PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int idProducto = rs.getInt("ID_Producto");
                String nombreProducto = obtenerNombreProducto(idProducto);
                int cantidad = rs.getInt("Cantidad");
                double precio = obtenerPrecioProducto(idProducto);
                double subtotal = rs.getDouble("Subtotal");

                modelo.addRow(new Object[]{nombreProducto, cantidad, precio, subtotal});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la tabla: " + e.getMessage());
        }
    }
    
    private String obtenerNombreProducto(int idProducto) {
        String nombreProducto = "";
        try {
            String sql = "SELECT Nombre_Producto FROM productos WHERE ID_Producto = ?";
            PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);
            pst.setInt(1, idProducto);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                nombreProducto = rs.getString("Nombre_Producto");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener el nombre del producto: " + e.getMessage());
        }
        return nombreProducto;
    }
    
    private double obtenerPrecioProducto(int idProducto) {
        double precio = 0.0;
        try {
            String sql = "SELECT Precio FROM productos WHERE ID_Producto = ?";
            PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);
            pst.setInt(1, idProducto);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                precio = rs.getDouble("Precio");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener el precio del producto: " + e.getMessage());
        }
        return precio;
    }
    
    private int obtenerIdProducto(String nombreProducto) {
        int idProducto = -1;
        try {
            String sql = "SELECT ID_Producto FROM productos WHERE Nombre_Producto = ?";
            PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);
            pst.setString(1, nombreProducto);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                idProducto = rs.getInt("ID_Producto");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener el ID del producto: " + e.getMessage());
        }
        return idProducto;
    }
    
    private void agregarProductoABD(int idProducto, int cantidad, double subtotal) {
        try {
            String sql = "INSERT INTO detalles_pedido (ID_Producto, Cantidad, Subtotal) VALUES (?, ?, ?)";
            PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);
            pst.setInt(1, idProducto);
            pst.setInt(2, cantidad);
            pst.setDouble(3, subtotal);

            int filasAfectadas = pst.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Producto agregado al pedido correctamente.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar producto a la base de datos: " + e.getMessage());
        }
    }
    
    private void eliminarProductoDeABD(int idProducto) {
        try {
            String sql = "DELETE FROM detalles_pedido WHERE ID_Producto = ?";
            PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);
            pst.setInt(1, idProducto);

            int filasAfectadas = pst.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar producto de la base de datos: " + e.getMessage());
        }
    }
    
    private void registrarPedido() {
        String clienteSeleccionado = (String) comboCliente.getSelectedItem();
        String empleadoSeleccionado = (String) comboEmpleado.getSelectedItem();
        String fechaPedido = txtFechaPedido.getText();

        if (clienteSeleccionado == null || clienteSeleccionado.equals("Seleccione un cliente") || 
            empleadoSeleccionado == null || empleadoSeleccionado.equals("Seleccione un empleado") || 
            fechaPedido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un cliente, un empleado y una fecha.");
            return;
        }


        int idCliente = obtenerIdCliente(clienteSeleccionado);
        int idEmpleado = obtenerIdEmpleado(empleadoSeleccionado);

        double total = 0.0;
        try {
            String sql = "SELECT Subtotal FROM detalles_pedido WHERE ID_Pedido IS NULL";
            PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                total += rs.getDouble("Subtotal");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al calcular el total: " + e.getMessage());
        }

        try {
            String sql = "INSERT INTO pedidos (Fecha_Pedido, Total, ID_Cliente, ID_Empleado) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, fechaPedido);
            pst.setDouble(2, total);
            pst.setInt(3, idCliente);
            pst.setInt(4, idEmpleado);
            int filasAfectadas = pst.executeUpdate();

            if (filasAfectadas > 0) {
                // Obtener el ID_Pedido generado
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int idPedido = rs.getInt(1);

                    actualizarDetallesPedido(idPedido);

                    JOptionPane.showMessageDialog(this, "Pedido registrado correctamente.");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar el pedido: " + e.getMessage());
        }
    }

    private int obtenerIdCliente(String nombreCliente) {
        int idCliente = -1;
        try {
            String sql = "SELECT ID_Cliente FROM clientes WHERE Nombre = ?";
            PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);
            pst.setString(1, nombreCliente);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                idCliente = rs.getInt("ID_Cliente");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener el ID del cliente: " + e.getMessage());
        }
        return idCliente;
    }

    private int obtenerIdEmpleado(String nombreEmpleado) {
        int idEmpleado = -1;
        try {
            String sql = "SELECT ID_Empleado FROM empleados WHERE Nombre = ?";
            PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);
            pst.setString(1, nombreEmpleado);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                idEmpleado = rs.getInt("ID_Empleado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener el ID del empleado: " + e.getMessage());
        }
        return idEmpleado;
    }

    private void actualizarDetallesPedido(int idPedido) {
        try {
            String sql = "UPDATE detalles_pedido SET ID_Pedido = ? WHERE ID_Pedido IS NULL";
            PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);
            pst.setInt(1, idPedido);
            pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar los detalles del pedido: " + e.getMessage());
        }
    }
    
    public void eliminarPedido(int idPedido) {
        try {
            String sqlDetalles = "DELETE FROM detalles_pedido WHERE ID_Pedido = ?";
            try (PreparedStatement p1 = (PreparedStatement) cn.prepareStatement(sqlDetalles)) {
                p1.setInt(1, idPedido);
                p1.executeUpdate();
            }

            String sqlPedido = "DELETE FROM pedidos WHERE ID_Pedido = ?";
            try (PreparedStatement p2 = (PreparedStatement) cn.prepareStatement(sqlPedido)) {
                p2.setInt(1, idPedido);
                int filasAfectadas = p2.executeUpdate();
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Pedido eliminado correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el pedido para eliminar");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el pedido: " + e.getMessage());
            System.out.println(e);
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
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPedidos = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        btnEliminarProducto = new javax.swing.JButton();
        comboEmpleado = new javax.swing.JComboBox<>();
        comboProducto = new javax.swing.JComboBox<>();
        txtCantidad = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        comboCliente = new javax.swing.JComboBox<>();
        btnRegistrar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtFechaPedido = new javax.swing.JTextField();
        btnAgregarProducto = new javax.swing.JButton();

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
        jLabel4.setText("PEDIDOS");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel10.setText("Registrar Pedido:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Cliente:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, -1, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Empleado:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, -1, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel5.setText("Lista de Pedidos:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, -1, -1));

        tablaPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cliente", "Empleado", "Fecha de Pedido", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaPedidos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 850, 250));

        btnEliminar.setBackground(new java.awt.Color(255, 153, 51));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 450, 250, -1));

        btnEliminarProducto.setBackground(new java.awt.Color(255, 153, 51));
        btnEliminarProducto.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnEliminarProducto.setText("Eliminar");
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 170, 100, 30));

        jPanel1.add(comboEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, 300, 30));

        jPanel1.add(comboProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 130, 240, 30));
        jPanel1.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 170, 240, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Cantidad:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, -1, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Producto:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, -1, 30));

        jPanel1.add(comboCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 300, 30));

        btnRegistrar.setBackground(new java.awt.Color(255, 153, 51));
        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 330, 250, -1));

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre_Producto", "Cantidad", "Precio", "Subtotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaProductos);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 220, 340, 90));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Fecha de");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, -1, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Pedido:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, -1, 30));
        jPanel1.add(txtFechaPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 300, 30));

        btnAgregarProducto.setBackground(new java.awt.Color(255, 153, 51));
        btnAgregarProducto.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAgregarProducto.setText("Agregar");
        btnAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProductoActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 130, 100, 30));

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

    private void btnAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoActionPerformed
        String productoSeleccionado = (String) comboProducto.getSelectedItem();
        String cantidadStr = txtCantidad.getText();

        if (productoSeleccionado == null || productoSeleccionado.equals("Seleccione un producto") || cantidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto y una cantidad.");
            return;
        }

        int cantidad;
        
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número válido.");
            return;
        }

        int idProducto = obtenerIdProducto(productoSeleccionado);
        double precio = obtenerPrecioProducto(idProducto);
        double subtotal = precio * cantidad;

        DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
        modelo.addRow(new Object[]{productoSeleccionado, cantidad, precio, subtotal});

        agregarProductoABD(idProducto, cantidad, subtotal);
    }//GEN-LAST:event_btnAgregarProductoActionPerformed

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto de la tabla.");
        } else {
            String nombreProducto = (String) tablaProductos.getValueAt(filaSeleccionada, 0);
            int idProducto = obtenerIdProducto(nombreProducto);

            DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
            modelo.removeRow(filaSeleccionada);

            eliminarProductoDeABD(idProducto);
        }
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        String clienteSeleccionado = (String) comboCliente.getSelectedItem();
        String empleadoSeleccionado = (String) comboEmpleado.getSelectedItem();
        String fechaPedido = txtFechaPedido.getText();

        if (clienteSeleccionado == null || clienteSeleccionado.equals("Seleccione un cliente") || 
            empleadoSeleccionado == null || empleadoSeleccionado.equals("Seleccione un empleado") || 
            fechaPedido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un cliente, un empleado y una fecha.");
            return;
        }

        int idCliente = obtenerIdCliente(clienteSeleccionado);
        int idEmpleado = obtenerIdEmpleado(empleadoSeleccionado);

        double total = 0.0;
        try {
            String sql = "SELECT Subtotal FROM detalles_pedido WHERE ID_Pedido IS NULL";
            PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                total += rs.getDouble("Subtotal");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al calcular el total: " + e.getMessage());
        }

        try {
            String sql = "INSERT INTO pedidos (Fecha_Pedido, Total, ID_Cliente, ID_Empleado) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = (PreparedStatement) cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, fechaPedido);
            pst.setDouble(2, total);
            pst.setInt(3, idCliente);
            pst.setInt(4, idEmpleado);
            int filasAfectadas = pst.executeUpdate();

            if (filasAfectadas > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int idPedido = rs.getInt(1);

                    actualizarDetallesPedido(idPedido);

                    JOptionPane.showMessageDialog(this, "Pedido registrado correctamente.");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar el pedido: " + e.getMessage());
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tablaPedidos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un pedido de la tabla");
        } else {
            int idPedido = Integer.parseInt(tablaPedidos.getValueAt(filaSeleccionada, 0).toString());

            eliminarPedido(idPedido);

            actualizarTablaPedidos();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarProducto;
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnEmpleados;
    private javax.swing.JButton btnFinanciera;
    private javax.swing.JButton btnPedidos;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnReservas;
    private javax.swing.JComboBox<String> comboCliente;
    private javax.swing.JComboBox<String> comboEmpleado;
    private javax.swing.JComboBox<String> comboProducto;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaPedidos;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtFechaPedido;
    // End of variables declaration//GEN-END:variables
}
