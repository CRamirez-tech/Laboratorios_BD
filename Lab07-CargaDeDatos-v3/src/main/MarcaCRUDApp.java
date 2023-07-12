package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class MarcaCRUDApp extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldCodigo;
	private JTextField textFieldNombre;
	private JTextArea textAreaDescripcion;
	private JButton buttonAdicionar;
	private JButton buttonModificar;
	private JButton buttonEliminar;
	private JButton buttonReactivar;
	private JTable tableEstadoRegistro;
	private DefaultTableModel tableModel;
	private Connection connection;
	private Component verticalStrut;
	private Component verticalStrut_1;
	private JButton buttonInactivar;
	private JButton buttonCancelar;
	private JButton buttonActualizar;
	private JButton buttonSalir;
	private JLabel label_3;
	private JTextField textFieldEstReg;
	private JButton[] controllers;
	private boolean hasUpdate = false;
	private int lastOption = -1;
	private PreparedStatement statement;

	public MarcaCRUDApp(Connection connection) {
		// Configurar la ventana principal
		setTitle("EstadoRegistro CRUD App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(496, 548);
		setLocationRelativeTo(null);

		// Configurar la tabla
		tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableModel.addColumn("Código");
		tableModel.addColumn("Nombre");
		tableModel.addColumn("Descripción");
		tableModel.addColumn("Est. Reg.");
		tableEstadoRegistro = new JTable(tableModel);	
		tableEstadoRegistro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				int row = tableEstadoRegistro.rowAtPoint(evt.getPoint());
				if (row >= 0) {
					textFieldCodigo.setText(tableEstadoRegistro.getModel().getValueAt(row, 0).toString());
					textFieldNombre.setText(tableEstadoRegistro.getModel().getValueAt(row, 1).toString());
					textAreaDescripcion.setText(tableEstadoRegistro.getModel().getValueAt(row, 2).toString());
					textFieldEstReg.setText(tableEstadoRegistro.getModel().getValueAt(row, 3).toString());
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(tableEstadoRegistro);

		// Agregar los componentes al panel principal
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JPanel inputPanel = new JPanel();
		GridBagLayout gbl_inputPanel = new GridBagLayout();
		gbl_inputPanel.columnWidths = new int[] { 36, 100, 100, 100, 100, 36, 0 };
		gbl_inputPanel.rowHeights = new int[] { 18, 30, 30, 120, 30, 24, 30, 30, 30, 0 };
		gbl_inputPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_inputPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		inputPanel.setLayout(gbl_inputPanel);

		verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.fill = GridBagConstraints.HORIZONTAL;
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
		gbc_textFieldCodigo.gridwidth = 2;
		gbc_textFieldCodigo.fill = GridBagConstraints.BOTH;
		gbc_textFieldCodigo.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCodigo.gridx = 3;
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
		gbc_textFieldNombre.gridwidth = 2;
		gbc_textFieldNombre.fill = GridBagConstraints.BOTH;
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.gridx = 3;
		gbc_textFieldNombre.gridy = 2;
		inputPanel.add(textFieldNombre, gbc_textFieldNombre);
		GridBagConstraints gbc_2 = new GridBagConstraints();
		gbc_2.fill = GridBagConstraints.BOTH;
		gbc_2.insets = new Insets(0, 0, 5, 5);
		gbc_2.gridx = 1;
		gbc_2.gridy = 3;
		JLabel label_1 = new JLabel("Descripción:");
		inputPanel.add(label_1, gbc_2);
		GridBagConstraints gbc_3 = new GridBagConstraints();
		gbc_3.gridwidth = 2;
		gbc_3.fill = GridBagConstraints.BOTH;
		gbc_3.insets = new Insets(0, 0, 5, 5);
		gbc_3.gridx = 3;
		gbc_3.gridy = 3;
		JScrollPane scrollPane_1 = new JScrollPane();
		inputPanel.add(scrollPane_1, gbc_3);
		textAreaDescripcion = new JTextArea(5, 20);
		scrollPane_1.setViewportView(textAreaDescripcion);
		textAreaDescripcion.setLineWrap(true);

		label_3 = new JLabel("Estado de Registro:");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.fill = GridBagConstraints.BOTH;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 4;
		inputPanel.add(label_3, gbc_label_3);

		textFieldEstReg = new JTextField(20);
		GridBagConstraints gbc_textFieldEstReg = new GridBagConstraints();
		gbc_textFieldEstReg.fill = GridBagConstraints.BOTH;
		gbc_textFieldEstReg.gridwidth = 2;
		gbc_textFieldEstReg.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldEstReg.gridx = 3;
		gbc_textFieldEstReg.gridy = 4;
		inputPanel.add(textFieldEstReg, gbc_textFieldEstReg);

		buttonAdicionar = new JButton("Adicionar");
		GridBagConstraints gbc_buttonAdicionar = new GridBagConstraints();
		gbc_buttonAdicionar.fill = GridBagConstraints.BOTH;
		gbc_buttonAdicionar.insets = new Insets(0, 0, 5, 5);
		gbc_buttonAdicionar.gridx = 1;
		gbc_buttonAdicionar.gridy = 6;
		inputPanel.add(buttonAdicionar, gbc_buttonAdicionar);

		buttonModificar = new JButton("Modificar");
		GridBagConstraints gbc_buttonModificar = new GridBagConstraints();
		gbc_buttonModificar.fill = GridBagConstraints.BOTH;
		gbc_buttonModificar.insets = new Insets(0, 0, 5, 5);
		gbc_buttonModificar.gridx = 2;
		gbc_buttonModificar.gridy = 6;
		inputPanel.add(buttonModificar, gbc_buttonModificar);

		buttonEliminar = new JButton("Eliminar");
		GridBagConstraints gbc_buttonEliminar = new GridBagConstraints();
		gbc_buttonEliminar.fill = GridBagConstraints.BOTH;
		gbc_buttonEliminar.insets = new Insets(0, 0, 5, 5);
		gbc_buttonEliminar.gridx = 3;
		gbc_buttonEliminar.gridy = 6;
		inputPanel.add(buttonEliminar, gbc_buttonEliminar);

		buttonCancelar = new JButton("Cancelar");
		GridBagConstraints gbc_buttonCancelar = new GridBagConstraints();
		gbc_buttonCancelar.fill = GridBagConstraints.BOTH;
		gbc_buttonCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_buttonCancelar.gridx = 4;
		gbc_buttonCancelar.gridy = 6;
		inputPanel.add(buttonCancelar, gbc_buttonCancelar);

		buttonInactivar = new JButton("Inactivar");
		GridBagConstraints gbc_buttonInactivar = new GridBagConstraints();
		gbc_buttonInactivar.fill = GridBagConstraints.BOTH;
		gbc_buttonInactivar.insets = new Insets(0, 0, 5, 5);
		gbc_buttonInactivar.gridx = 1;
		gbc_buttonInactivar.gridy = 7;
		inputPanel.add(buttonInactivar, gbc_buttonInactivar);

		buttonReactivar = new JButton("Reactivar");
		GridBagConstraints gbc_buttonReactivar = new GridBagConstraints();
		gbc_buttonReactivar.insets = new Insets(0, 0, 5, 5);
		gbc_buttonReactivar.fill = GridBagConstraints.BOTH;
		gbc_buttonReactivar.gridx = 2;
		gbc_buttonReactivar.gridy = 7;
		inputPanel.add(buttonReactivar, gbc_buttonReactivar);
		panel.add(inputPanel, BorderLayout.NORTH);

		buttonActualizar = new JButton("Actualizar");
		GridBagConstraints gbc_buttonActualizar = new GridBagConstraints();
		gbc_buttonActualizar.fill = GridBagConstraints.BOTH;
		gbc_buttonActualizar.insets = new Insets(0, 0, 5, 5);
		gbc_buttonActualizar.gridx = 3;
		gbc_buttonActualizar.gridy = 7;
		inputPanel.add(buttonActualizar, gbc_buttonActualizar);

		buttonSalir = new JButton("Salir");
		GridBagConstraints gbc_buttonSalir = new GridBagConstraints();
		gbc_buttonSalir.fill = GridBagConstraints.BOTH;
		gbc_buttonSalir.insets = new Insets(0, 0, 5, 5);
		gbc_buttonSalir.gridx = 4;
		gbc_buttonSalir.gridy = 7;
		inputPanel.add(buttonSalir, gbc_buttonSalir);

		panel.add(scrollPane, BorderLayout.CENTER);

		// Agregar el panel al contenedor de la ventana
		Container container = getContentPane();
		container.add(panel);

		// Agregar listeners a los botones
		buttonAdicionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				evaluateOption(0);
			}
		});
		buttonModificar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				evaluateOption(1);
			}
		});
		buttonEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				evaluateOption(2);
			}
		});

		buttonInactivar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				evaluateOption(3);
			}
		});
		buttonReactivar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				evaluateOption(4);
			}
		});

		buttonActualizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				evaluateOption(5);
			}
		});
		buttonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				evaluateOption(6);
			}
		});
		buttonSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				evaluateOption(7);
			}
		});

		// Establecer la conexión a la base de datos
		this.connection = connection;
		// Valores Iniciales
		buttonCancelar.setEnabled(false);

		textFieldCodigo.setEditable(false);
		textFieldNombre.setEditable(false);
		textAreaDescripcion.setEditable(false);
		textFieldEstReg.setEditable(false);

		// Cargar los datos de la tabla al iniciar la aplicación
		controllers = new JButton[8];
		controllers[0] = buttonAdicionar;
		controllers[1] = buttonModificar;
		controllers[2] = buttonEliminar;
		controllers[3] = buttonInactivar;
		controllers[4] = buttonReactivar;

		controllers[5] = buttonActualizar;
		controllers[6] = buttonCancelar;
		controllers[7] = buttonSalir;
		cargarDatosTabla();
	}

	protected void evaluateOption(int option) {
		int codigo;
		String estado;
		try {
			switch (option) {
			case 0:
				limpiarCampos();
				statement = connection.prepareStatement(
						"INSERT IGNORE INTO Marca (MarCod, MarNom, MarDes) VALUES (?, ?, ?)");
				textFieldCodigo.setEditable(true);
				textFieldNombre.setEditable(true);
				textAreaDescripcion.setEditable(true);
				textFieldEstReg.setText("A");

				hasUpdate = true;
				break;
			case 1:
				codigo = Integer.parseInt(textFieldCodigo.getText());
				estado = textFieldEstReg.getText();
				System.out.println("Estado: "+estado);
				if(!estado.equals("1")) {
					throw new Exception("El registro primero tiene que estar en estado Activo");
				}
				statement = connection
						.prepareStatement("UPDATE Marca SET MarNom = ?, MarDes = ? WHERE MarCod = ?");
				textFieldNombre.setEditable(true);
				textAreaDescripcion.setEditable(true);
				hasUpdate = true;
				break;
			case 2:
				codigo = Integer.parseInt(textFieldCodigo.getText());
				statement = connection
						.prepareStatement("UPDATE Marca SET EstRegCod = 3 WHERE MarCod = ? ;");
				textFieldEstReg.setText("*");
				hasUpdate = true;
				break;
			case 3:
				codigo = Integer.parseInt(textFieldCodigo.getText());
				estado = textFieldEstReg.getText();
				if(estado.equals("2")) {
					throw new Exception("El registro ya se encuentra Inactivo");
				}
				statement = connection
						.prepareStatement("UPDATE Marca SET EstRegCod = 2 WHERE MarCod = ? ;");
				textFieldEstReg.setText("I");
				hasUpdate = true;
				break;
			case 4:
				codigo = Integer.parseInt(textFieldCodigo.getText());
				estado = textFieldEstReg.getText();
				if(estado.equals("1")) {
					throw new Exception("El registro ya se encuentra Activo");
				}
				statement = connection
						.prepareStatement("UPDATE Marca SET EstRegCod = 1 WHERE MarCod = ? ;");
				textFieldEstReg.setText("A");
				
				hasUpdate = true;
				break;
			case 5:
				codigo = Integer.parseInt(textFieldCodigo.getText());
				String nombre = textFieldNombre.getText();
				String descripcion = textAreaDescripcion.getText();

				if (hasUpdate) {
					if (lastOption == 1) {
						statement.setString(1, nombre);
						statement.setString(2, descripcion);
						statement.setInt(3, codigo);
					} else {
						statement.setInt(1, codigo);
						if (lastOption  == 0) {
							statement.setString(2, nombre);
							statement.setString(3, descripcion);
						}
					}
					int rowsAffected = statement.executeUpdate();
					if (rowsAffected > 0) {
						JOptionPane.showMessageDialog(this, "Operacion completada satisfactoriamente.");
						cargarDatosTabla();
					} else {
						JOptionPane.showMessageDialog(this, "Este código tiene un valor historico. Use otro código.");
					}

					statement.close();
					hasUpdate = false;
				}
				cargarDatosTabla();
				break;
			case 6:
				if (hasUpdate) {
					statement.close();
					hasUpdate = false;
				}
				cargarDatosTabla();
				break;
			case 7:
				if (hasUpdate) {
					statement.close();
					hasUpdate = false;
				}
				JOptionPane.showMessageDialog(this, "Gracias por usar el programa");
				setVisible(false);
				dispose();
				break;
			default:
				JOptionPane.showMessageDialog(this, "Esta no es una opcion valida");
				break;
			}
			editMode(hasUpdate);
			lastOption = option;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "No se selecciono un registro");
			hasUpdate = false;
		} catch (SQLException e) {
			System.out.println("Ocurrio un problema con la base de datos: " + e.getMessage());
			hasUpdate = false;
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			hasUpdate = false;
		}
	}

	protected void editMode(boolean isEditing) {
		for (int i = 0; i < 5; i++) {
			controllers[i].setEnabled(!isEditing);
		}
		controllers[6].setEnabled(isEditing);
	}

	protected void limpiarCampos() {
		textFieldCodigo.setText("");
		textFieldNombre.setText("");
		textAreaDescripcion.setText("");
		textFieldEstReg.setText("");
	}

	protected void protegerCampos(boolean isProtect) {
		textFieldCodigo.setEditable(!isProtect);
		textFieldNombre.setEditable(!isProtect);
		textAreaDescripcion.setEditable(!isProtect);
	}

	private void cargarDatosTabla() {
		try {
			limpiarCampos();
			protegerCampos(true);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Marca WHERE NOT EstRegCod='3';");

			// Limpiar la tabla antes de cargar los datos
			tableModel.setRowCount(0);

			// Cargar los datos en la tabla
			while (resultSet.next()) {
				int codigo = resultSet.getInt("MarCod");
				String nombre = resultSet.getString("MarNom");
				String descripcion = resultSet.getString("MarDes");
				String estado = resultSet.getString("EstRegCod");

				Object[] rowData = { codigo, nombre, descripcion, estado };
				tableModel.addRow(rowData);
			}

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
