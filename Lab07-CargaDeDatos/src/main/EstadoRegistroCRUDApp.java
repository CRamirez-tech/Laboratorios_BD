package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EstadoRegistroCRUDApp extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldCodigo;
    private JTextField textFieldNombre;
    private JTextArea textAreaDescripcion;
    private JButton buttonCrear;
    private JButton buttonActualizar;
    private JButton buttonEliminar;
    private JButton buttonBuscar;
    private JTable tableEstadoRegistro;
    private DefaultTableModel tableModel;
    private Connection connection;
    private Component verticalStrut;
    private Component verticalStrut_1;

    public EstadoRegistroCRUDApp(Connection connection) {
        // Configurar la ventana principal
        setTitle("EstadoRegistro CRUD App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(529, 548);
        setLocationRelativeTo(null);

        // Configurar la tabla
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Código");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Descripción");
        tableEstadoRegistro = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableEstadoRegistro);

        // Agregar los componentes al panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel();
        GridBagLayout gbl_inputPanel = new GridBagLayout();
        gbl_inputPanel.columnWidths = new int[]{36, 239, 201, 36, 0};
        gbl_inputPanel.rowHeights = new int[]{18, 36, 33, 120, 36, 36, 0, 0};
        gbl_inputPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_inputPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        inputPanel.setLayout(gbl_inputPanel);
        
        verticalStrut = Box.createVerticalStrut(20);
        GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
        gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
        gbc_verticalStrut.gridx = 1;
        gbc_verticalStrut.gridy = 0;
        inputPanel.add(verticalStrut, gbc_verticalStrut);
        
        verticalStrut_1 = Box.createVerticalStrut(20);
        GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
        gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
        gbc_verticalStrut_1.gridx = 0;
        gbc_verticalStrut_1.gridy = 1;
        inputPanel.add(verticalStrut_1, gbc_verticalStrut_1);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 1;
        gbc.gridy = 1;
        JLabel label_2 = new JLabel("Código:");
        inputPanel.add(label_2, gbc);
        
        // Configurar los componentes de la interfaz
        textFieldCodigo = new JTextField(10);
        GridBagConstraints gbc_textFieldCodigo = new GridBagConstraints();
        gbc_textFieldCodigo.fill = GridBagConstraints.BOTH;
        gbc_textFieldCodigo.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldCodigo.gridx = 2;
        gbc_textFieldCodigo.gridy = 1;
        inputPanel.add(textFieldCodigo, gbc_textFieldCodigo);
        
        
        GridBagConstraints gbc_1 = new GridBagConstraints();
        gbc_1.fill = GridBagConstraints.BOTH;
        gbc_1.insets = new Insets(0, 0, 5, 5);
        gbc_1.gridx = 1;
        gbc_1.gridy = 2;
        JLabel label = new JLabel("Nombre:");
        inputPanel.add(label, gbc_1);
        textFieldNombre = new JTextField(20);
        GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
        gbc_textFieldNombre.fill = GridBagConstraints.BOTH;
        gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldNombre.gridx = 2;
        gbc_textFieldNombre.gridy = 2;
        inputPanel.add(textFieldNombre, gbc_textFieldNombre);
        GridBagConstraints gbc_2 = new GridBagConstraints();
        gbc_2.fill = GridBagConstraints.BOTH;
        gbc_2.insets = new Insets(0, 0, 5, 5);
        gbc_2.gridx = 1;
        gbc_2.gridy = 3;
        JLabel label_1 = new JLabel("Descripción:");
        inputPanel.add(label_1, gbc_2);
        textAreaDescripcion = new JTextArea(5, 20);
        textAreaDescripcion.setLineWrap(true);
        GridBagConstraints gbc_3 = new GridBagConstraints();
        gbc_3.fill = GridBagConstraints.BOTH;
        gbc_3.insets = new Insets(0, 0, 5, 5);
        gbc_3.gridx = 2;
        gbc_3.gridy = 3;
        JScrollPane scrollPane_1 = new JScrollPane(textAreaDescripcion);
        inputPanel.add(scrollPane_1, gbc_3);
        buttonCrear = new JButton("Adicionar");
        GridBagConstraints gbc_buttonCrear = new GridBagConstraints();
        gbc_buttonCrear.fill = GridBagConstraints.BOTH;
        gbc_buttonCrear.insets = new Insets(0, 0, 5, 5);
        gbc_buttonCrear.gridx = 1;
        gbc_buttonCrear.gridy = 4;
        inputPanel.add(buttonCrear, gbc_buttonCrear);
        
        // Agregar listeners a los botones
        buttonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearEstadoRegistro();
            }
        });
        
        buttonActualizar = new JButton("Modificar");
        GridBagConstraints gbc_buttonActualizar = new GridBagConstraints();
        gbc_buttonActualizar.fill = GridBagConstraints.BOTH;
        gbc_buttonActualizar.insets = new Insets(0, 0, 5, 5);
        gbc_buttonActualizar.gridx = 2;
        gbc_buttonActualizar.gridy = 4;
        inputPanel.add(buttonActualizar, gbc_buttonActualizar);
        
        buttonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEstadoRegistro();
            }
        });
        
        buttonEliminar = new JButton("Eliminar");
        GridBagConstraints gbc_buttonEliminar = new GridBagConstraints();
        gbc_buttonEliminar.fill = GridBagConstraints.BOTH;
        gbc_buttonEliminar.insets = new Insets(0, 0, 5, 5);
        gbc_buttonEliminar.gridx = 1;
        gbc_buttonEliminar.gridy = 5;
        inputPanel.add(buttonEliminar, gbc_buttonEliminar);
        
        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEstadoRegistro();
            }
        });
        
        buttonBuscar = new JButton("Buscar");
        GridBagConstraints gbc_buttonBuscar = new GridBagConstraints();
        gbc_buttonBuscar.insets = new Insets(0, 0, 5, 5);
        gbc_buttonBuscar.fill = GridBagConstraints.BOTH;
        gbc_buttonBuscar.gridx = 2;
        gbc_buttonBuscar.gridy = 5;
        inputPanel.add(buttonBuscar, gbc_buttonBuscar);
        
        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarEstadoRegistro();
            }
        });
        
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Agregar el panel al contenedor de la ventana
        Container container = getContentPane();
        container.add(panel);

        // Establecer la conexión a la base de datos
        this.connection = connection;
        
        // Cargar los datos de la tabla al iniciar la aplicación
        cargarDatosTabla();
    }

    private void cargarDatosTabla() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM EstadoRegistro");

            // Limpiar la tabla antes de cargar los datos
            tableModel.setRowCount(0);

            // Cargar los datos en la tabla
            while (resultSet.next()) {
                int codigo = resultSet.getInt("EstRegCod");
                String nombre = resultSet.getString("EstRegNom");
                String descripcion = resultSet.getString("EstRegDes");
                String estado= resultSet.getString("EstRegEstReg");

                Object[] rowData = {codigo, nombre, descripcion};
                tableModel.addRow(rowData);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void crearEstadoRegistro() {
        int codigo = Integer.parseInt(textFieldCodigo.getText());
        String nombre = textFieldNombre.getText();
        String descripcion = textAreaDescripcion.getText();

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT IGNORE INTO EstadoRegistro (EstRegCod, EstRegNom, EstRegDes) VALUES (?, ?, ?)");
            statement.setInt(1, codigo);
            statement.setString(2, nombre);
            statement.setString(3, descripcion);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "EstadoRegistro creado correctamente");
                cargarDatosTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear el EstadoRegistro");
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actualizarEstadoRegistro() {
        int codigo = Integer.parseInt(textFieldCodigo.getText());
        String nombre = textFieldNombre.getText();
        String descripcion = textAreaDescripcion.getText();

        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE EstadoRegistro SET EstRegNom = ?, EstRegDes = ? WHERE EstRegCod = ?");
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setInt(3, codigo);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "EstadoRegistro actualizado correctamente");
                cargarDatosTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el EstadoRegistro");
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void eliminarEstadoRegistro() {
        int codigo = Integer.parseInt(textFieldCodigo.getText());

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM EstadoRegistro WHERE EstRegCod = ?");
            statement.setInt(1, codigo);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "EstadoRegistro eliminado correctamente");
                cargarDatosTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el EstadoRegistro");
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void buscarEstadoRegistro() {
        int codigo = Integer.parseInt(textFieldCodigo.getText());

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM EstadoRegistro WHERE EstRegCod = ?");
            statement.setInt(1, codigo);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nombre = resultSet.getString("EstRegNom");
                String descripcion = resultSet.getString("EstRegDes");

                textFieldNombre.setText(nombre);
                textAreaDescripcion.setText(descripcion);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el EstadoRegistro");
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

