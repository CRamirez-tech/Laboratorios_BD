package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class ClienteCRUDApp extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldCodigo;
	private JTextField textFieldNombre;
	private JTextField textFieldApePat;
	private JTextField textFieldApeMat;
	private JTextField textFieldDir;
	private JTextField textFieldTel;
	private JTextField textFieldCorElc;
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

	public ClienteCRUDApp(Connection connection) {
		// Configurar la ventana principal
		setTitle("Cliente CRUD App");
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
		tableModel.addColumn("Apellido Paterno");
		tableModel.addColumn("Apellido Materno");
		tableModel.addColumn("Dirección");
		tableModel.addColumn("Teléfono");
		tableModel.addColumn("Correo Electrónico");
		tableModel.addColumn("Est. Reg.");
		tableEstadoRegistro = new JTable(tableModel);
		tableEstadoRegistro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				int row = tableEstadoRegistro.rowAtPoint(evt.getPoint());
				if (row >= 0) {
					textFieldCodigo.setText(tableEstadoRegistro.getModel().getValueAt(row, 0).toString());
					textFieldNombre.setText(tableEstadoRegistro.getModel().getValueAt(row, 1).toString());
					textFieldApePat.setText(tableEstadoRegistro.getModel().getValueAt(row, 2).toString());
					textFieldApeMat.setText(tableEstadoRegistro.getModel().getValueAt(row, 3).toString());
					textFieldDir.setText(tableEstadoRegistro.getModel().getValueAt(row, 4).toString());
					textFieldTel.setText(tableEstadoRegistro.getModel().getValueAt(row, 5).toString());
					textFieldCorElc.setText(tableEstadoRegistro.getModel().getValueAt(row, 6).toString());
					textFieldEstReg.setText(tableEstadoRegistro.getModel().getValueAt(row, 7).toString());
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
		gbl_inputPanel.rowHeights = new int[] { 18, 30, 30, 30, 30, 30, 30, 30, 30, 0 };
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
		JLabel label_3 = new JLabel("Apellido Paterno:");
		inputPanel.add(label_3, gbc_2);
		textFieldApePat = new JTextField(20);
		GridBagConstraints gbc_textFieldApePat = new GridBagConstraints();
		gbc_textFieldApePat.gridwidth = 2;
		gbc_textFieldApePat.fill = GridBagConstraints.BOTH;
		gbc_textFieldApePat.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldApePat.gridx = 3;
		gbc_textFieldApePat.gridy = 3;
		inputPanel.add(textFieldApePat, gbc_textFieldApePat);
		GridBagConstraints gbc_4 = new GridBagConstraints();
		gbc_4.fill = GridBagConstraints.BOTH;
		gbc_4.insets = new Insets(0, 0, 5, 5);
		gbc_4.gridx = 1;
		gbc_4.gridy = 4;
		JLabel label_4 = new JLabel("Apellido Materno:");
		inputPanel.add(label_4, gbc_4);
		textFieldApeMat = new JTextField(20);
		GridBagConstraints gbc_textFieldApeMat = new GridBagConstraints();
		gbc_textFieldApeMat.gridwidth = 2;
		gbc_textFieldApeMat.fill = GridBagConstraints.BOTH;
		gbc_textFieldApeMat.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldApeMat.gridx = 3;
		gbc_textFieldApeMat.gridy = 4;
		inputPanel.add(textFieldApeMat, gbc_textFieldApeMat);
		GridBagConstraints gbc_5 = new GridBagConstraints();
		gbc_5.fill = GridBagConstraints.BOTH;
		gbc_5.insets = new Insets(0, 0, 5, 5);
		gbc_5.gridx = 1;
		gbc_5.gridy = 5;
		JLabel label_5 = new JLabel("Dirección:");
		inputPanel.add(label_5, gbc_5);
		textFieldDir = new JTextField(50);
		GridBagConstraints gbc_textFieldDir = new GridBagConstraints();
		gbc_textFieldDir.gridwidth = 2;
		gbc_textFieldDir.fill = GridBagConstraints.BOTH;
		gbc_textFieldDir.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDir.gridx = 3;
		gbc_textFieldDir.gridy = 5;
		inputPanel.add(textFieldDir, gbc_textFieldDir);
		GridBagConstraints gbc_6 = new GridBagConstraints();
		gbc_6.fill = GridBagConstraints.BOTH;
		gbc_6.insets = new Insets(0, 0, 5, 5);
		gbc_6.gridx = 1;
		gbc_6.gridy = 6;
		JLabel label_6 = new JLabel("Teléfono:");
		inputPanel.add(label_6, gbc_6);
		textFieldTel = new JTextField(11);
		GridBagConstraints gbc_textFieldTel = new GridBagConstraints();
		gbc_textFieldTel.gridwidth = 2;
		gbc_textFieldTel.fill = GridBagConstraints.BOTH;
		gbc_textFieldTel.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldTel.gridx = 3;
		gbc_textFieldTel.gridy = 6;
		inputPanel.add(textFieldTel, gbc_textFieldTel);
		GridBagConstraints gbc_7 = new GridBagConstraints();
		gbc_7.fill = GridBagConstraints.BOTH;
		gbc_7.insets = new Insets(0, 0, 5, 5);
		gbc_7.gridx = 1;
		gbc_7.gridy = 7;
		JLabel label_7 = new JLabel("Correo Electrónico:");
		inputPanel.add(label_7, gbc_7);
		textFieldCorElc = new JTextField(20);
		GridBagConstraints gbc_textFieldCorElc = new GridBagConstraints();
		gbc_textFieldCorElc.gridwidth = 2;
		gbc_textFieldCorElc.fill = GridBagConstraints.BOTH;
		gbc_textFieldCorElc.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCorElc.gridx = 3;
		gbc_textFieldCorElc.gridy = 7;
		inputPanel.add(textFieldCorElc, gbc_textFieldCorElc);

		label_3 = new JLabel("Estado de Registro:");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.fill = GridBagConstraints.BOTH;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 8;
		inputPanel.add(label_3, gbc_label_3);

		textFieldEstReg = new JTextField(20);
		GridBagConstraints gbc_textFieldEstReg = new GridBagConstraints();
		gbc_textFieldEstReg.fill = GridBagConstraints.BOTH;
		gbc_textFieldEstReg.gridwidth = 2;
		gbc_textFieldEstReg.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldEstReg.gridx = 3;
		gbc_textFieldEstReg.gridy = 8;
		inputPanel.add(textFieldEstReg, gbc_textFieldEstReg);

		buttonAdicionar = new JButton("Adicionar");
		GridBagConstraints gbc_buttonAdicionar = new GridBagConstraints();
		gbc_buttonAdicionar.fill = GridBagConstraints.BOTH;
		gbc_buttonAdicionar.insets = new Insets(0, 0, 5, 5);
		gbc_buttonAdicionar.gridx = 1;
		gbc_buttonAdicionar.gridy = 9;
		inputPanel.add(buttonAdicionar, gbc_buttonAdicionar);

		buttonModificar = new JButton("Modificar");
		GridBagConstraints gbc_buttonModificar = new GridBagConstraints();
		gbc_buttonModificar.fill = GridBagConstraints.BOTH;
		gbc_buttonModificar.insets = new Insets(0, 0, 5, 5);
		gbc_buttonModificar.gridx = 2;
		gbc_buttonModificar.gridy = 9;
		inputPanel.add(buttonModificar, gbc_buttonModificar);

		buttonEliminar = new JButton("Eliminar");
		GridBagConstraints gbc_buttonEliminar = new GridBagConstraints();
		gbc_buttonEliminar.fill = GridBagConstraints.BOTH;
		gbc_buttonEliminar.insets = new Insets(0, 0, 5, 5);
		gbc_buttonEliminar.gridx = 3;
		gbc_buttonEliminar.gridy = 9;
		inputPanel.add(buttonEliminar, gbc_buttonEliminar);

		buttonCancelar = new JButton("Cancelar");
		GridBagConstraints gbc_buttonCancelar = new GridBagConstraints();
		gbc_buttonCancelar.fill = GridBagConstraints.BOTH;
		gbc_buttonCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_buttonCancelar.gridx = 4;
		gbc_buttonCancelar.gridy = 9;
		inputPanel.add(buttonCancelar, gbc_buttonCancelar);

		buttonInactivar = new JButton("Inactivar");
		GridBagConstraints gbc_buttonInactivar = new GridBagConstraints();
		gbc_buttonInactivar.fill = GridBagConstraints.BOTH;
		gbc_buttonInactivar.insets = new Insets(0, 0, 5, 5);
		gbc_buttonInactivar.gridx = 1;
		gbc_buttonInactivar.gridy = 10;
		inputPanel.add(buttonInactivar, gbc_buttonInactivar);

		buttonReactivar = new JButton("Reactivar");
		GridBagConstraints gbc_buttonReactivar = new GridBagConstraints();
		gbc_buttonReactivar.insets = new Insets(0, 0, 5, 5);
		gbc_buttonReactivar.fill = GridBagConstraints.BOTH;
		gbc_buttonReactivar.gridx = 2;
		gbc_buttonReactivar.gridy = 10;
		inputPanel.add(buttonReactivar, gbc_buttonReactivar);

		buttonActualizar = new JButton("Actualizar");
		GridBagConstraints gbc_buttonActualizar = new GridBagConstraints();
		gbc_buttonActualizar.fill = GridBagConstraints.BOTH;
		gbc_buttonActualizar.insets = new Insets(0, 0, 5, 5);
		gbc_buttonActualizar.gridx = 3;
		gbc_buttonActualizar.gridy = 10;
		inputPanel.add(buttonActualizar, gbc_buttonActualizar);

		buttonSalir = new JButton("Salir");
		GridBagConstraints gbc_buttonSalir = new GridBagConstraints();
		gbc_buttonSalir.fill = GridBagConstraints.BOTH;
		gbc_buttonSalir.insets = new Insets(0, 0, 5, 5);
		gbc_buttonSalir.gridx = 4;
		gbc_buttonSalir.gridy = 10;
		inputPanel.add(buttonSalir, gbc_buttonSalir);

		panel.add(scrollPane, BorderLayout.CENTER);
		panel.add(inputPanel, BorderLayout.NORTH);

		// Agregar el panel principal a la ventana
		getContentPane().add(panel);

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
		textFieldApePat.setEditable(false);
		textFieldApeMat.setEditable(false);
		textFieldCorElc.setEditable(false);
		textFieldDir.setEditable(false);
		textFieldTel.setEditable(false);
		textFieldEstReg.setEditable(false);

		// Cargar los datos de la tabla al iniciar la aplicaci�n
		controllers = new JButton[8];
		controllers[0] = buttonAdicionar;
		controllers[1] = buttonModificar;
		controllers[2] = buttonEliminar;
		controllers[3] = buttonInactivar;
		controllers[4] = buttonReactivar;

		controllers[5] = buttonActualizar;
		controllers[6] = buttonCancelar;
		controllers[7] = buttonSalir;
		cargarTabla();
	}

	protected void evaluateOption(int option) {
		String codigo;
		String estReg;
		try {
			switch (option) {
			case 0:
				limpiarCampos();
				statement = connection.prepareStatement(
						"INSERT IGNORE INTO Cliente ( CliNom, CliApePat, CliApeMat, CliDir, CliTel, CliCorElc) VALUES (?, ?, ?, ?, ?, ?)");
				protegerCampos(false);
				textFieldCodigo.setText("0");
				textFieldEstReg.setText("A");
				hasUpdate = true;
				break;
			case 1:
				codigo = textFieldCodigo.getText();
				if (codigo.length()<1) {
					throw new Exception("No hay un registro seleccionado");
				}
				estReg = textFieldEstReg.getText();
				if (!estReg.equals("1")) {
					throw new Exception("El registro primero tiene que estar en estado Activo");
				}
				statement = connection.prepareStatement(
						"UPDATE Cliente SET CliNom = ?, CliApePat = ?, CliApeMat = ?, CliDir = ?, CliTel = ?, CliCorElc = ? WHERE CliCod = ?");
				protegerCampos(false);
				hasUpdate = true;
				break;
			case 2:
				codigo = textFieldCodigo.getText();
				if (codigo.length()<1) {
					throw new Exception("No hay un registro seleccionado");
				}
				statement = connection.prepareStatement("UPDATE Cliente SET EstRegCod = 3 WHERE CliCod = ?");
				textFieldEstReg.setText("*");
				hasUpdate = true;
				break;
			case 3:
				codigo = textFieldCodigo.getText();
				if (codigo.length()<1) {
					throw new Exception("No hay un registro seleccionado");
				}
				estReg = textFieldEstReg.getText();
				if (estReg.equals("2")) {
					throw new Exception("El registro ya se encuentra Inactivo");
				}
				statement = connection.prepareStatement("UPDATE Cliente SET EstRegCod = 2 WHERE CliCod = ?");
				textFieldEstReg.setText("I");
				hasUpdate = true;
				break;
			case 4:
				codigo = textFieldCodigo.getText();
				if (codigo.length()<1) {
					throw new Exception("No hay un registro seleccionado");
				}
				estReg = textFieldEstReg.getText();
				if (estReg.equals("1")) {
					throw new Exception("El registro ya se encuentra Activo");
				}
				statement = connection.prepareStatement("UPDATE Cliente SET EstRegCod = 1 WHERE CliCod = ?");
				textFieldEstReg.setText("A");

				hasUpdate = true;
				break;
			case 5:
				if (hasUpdate) {
					int codMod = Integer.parseInt(textFieldCodigo.getText());
					String nombre = textFieldNombre.getText();
					String apePat = textFieldApePat.getText();
					String apeMat = textFieldApeMat.getText();
					String dir = textFieldDir.getText();
					int tel = Integer.parseInt(textFieldTel.getText());
					String corElc = textFieldCorElc.getText();
				
					switch (lastOption) {
					case 0:
						statement.setString(1, nombre);
						statement.setString(2, apePat);
						statement.setString(3, apeMat);
						statement.setString(4, dir);
						statement.setInt(5, tel);
						statement.setString(6, corElc);
						break;
					case 1:
						statement.setString(1, nombre);
						statement.setString(2, apePat);
						statement.setString(3, apeMat);
						statement.setString(4, dir);
						statement.setInt(5, tel);
						statement.setString(6, corElc);
						statement.setInt(7, codMod);
						break;
					case 2:
					case 3:
					case 4:
						statement.setInt(1, codMod);
						break;
					}

					int rowsAffected = statement.executeUpdate();
					if (rowsAffected > 0) {
						JOptionPane.showMessageDialog(this, "Operacion completada satisfactoriamente.");
						cargarTabla();
					} else {
						JOptionPane.showMessageDialog(this, "Este codigo tiene un valor historico. Use otro codigo.");
					}

					statement.close();
					hasUpdate = false;
				}
				cargarTabla();
				break;
			case 6:
				if (hasUpdate) {
					statement.close();
					hasUpdate = false;
				}
				cargarTabla();
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
			JOptionPane.showMessageDialog(this, "Algun valor numerico es invalido");
		} catch (SQLException e) {
			System.out.println("Ocurrio un problema con la base de datos: " + e.getMessage());
			hasUpdate = false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			hasUpdate = false;
		}
	}

	private void editMode(boolean isEditing) {
		for (int i = 0; i < 5; i++) {
			controllers[i].setEnabled(!isEditing);
		}
		controllers[6].setEnabled(isEditing);
	}

	protected void protegerCampos(boolean isProtect) {
		textFieldNombre.setEditable(!isProtect);
		textFieldApePat.setEditable(!isProtect);
		textFieldApeMat.setEditable(!isProtect);
		textFieldCorElc.setEditable(!isProtect);
		textFieldDir.setEditable(!isProtect);
		textFieldTel.setEditable(!isProtect);
	}

	private void cargarTabla() {
		try {
			limpiarCampos();
			protegerCampos(true);
			// Limpiar la tabla antes de cargar los datos
			tableModel.setRowCount(0);

			// Consultar los registros de la base de datos
			String sql = "SELECT * FROM Cliente WHERE NOT EstRegCod='3';";
			statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();

			// Agregar los registros a la tabla
			while (result.next()) {
				String codigo = result.getString("CliCod");
				String nombre = result.getString("CliNom");
				String apePat = result.getString("CliApePat");
				String apeMat = result.getString("CliApeMat");
				String dir = result.getString("CliDir");
				String tel = result.getString("CliTel");
				String corElc = result.getString("CliCorElc");
				String estReg = result.getString("EstRegCod");

				Object[] row = { codigo, nombre, apePat, apeMat, dir, tel, corElc, estReg };
				tableModel.addRow(row);
			}

			// Cerrar el resultado y la declaración
			result.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void limpiarCampos() {
		textFieldCodigo.setText("");
		textFieldNombre.setText("");
		textFieldApePat.setText("");
		textFieldApeMat.setText("");
		textFieldDir.setText("");
		textFieldTel.setText("");
		textFieldCorElc.setText("");
		textFieldEstReg.setText("");
	}
}
