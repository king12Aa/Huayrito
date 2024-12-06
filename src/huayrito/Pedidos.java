
package huayrito;

public class Pedidos extends javax.swing.JFrame {

    public Pedidos() {
        initComponents();
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
        tablaReservas = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
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

        btnEditar.setBackground(new java.awt.Color(255, 153, 51));
        btnEditar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnEditar.setText("Editar");
        jPanel1.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 450, 250, -1));

        tablaReservas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaReservas);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 850, 250));

        btnEliminar.setBackground(new java.awt.Color(255, 153, 51));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnEliminar.setText("Eliminar");
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 450, 250, -1));

        btnAgregar.setBackground(new java.awt.Color(255, 153, 51));
        btnAgregar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAgregar.setText("Agregar");
        jPanel1.add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 150, 100, 40));

        jPanel1.add(comboEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, 300, 30));

        jPanel1.add(comboProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 140, 240, 30));
        jPanel1.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 170, 240, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Cantidad:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, -1, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Producto:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 140, -1, 30));

        jPanel1.add(comboCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 300, 30));

        btnRegistrar.setBackground(new java.awt.Color(255, 153, 51));
        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnRegistrar.setText("Registrar");
        jPanel1.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 330, 250, -1));

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre_Producto", "Cantidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaProductos);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 210, 340, 90));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Fecha de");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, -1, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Pedido:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, -1, 30));
        jPanel1.add(txtFechaPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 300, 30));

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
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
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTable tablaReservas;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtFechaPedido;
    // End of variables declaration//GEN-END:variables
}
