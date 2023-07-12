package main;

import javax.swing.*;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class ProductoCRUDApp extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldCodigo;
	private JTextField textFieldNombre;
	private JTextField textFieldStock;
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
	private JTextField textFieldEstReg;
	private JButton[] controllers;
	private boolean hasUpdate = false;
	private int lastOption = -1;
	private PreparedStatement statement;
	private JLabel lblStock;
	private JSpinner spinnerPreVenta;
	private JSpinner spinnerPreCompra;
	private JComboBox<Marca> comboMarca;
	private JComboBox<Categoria> comboCategoria;
	private ArrayList<Marca> marcas;
	private ArrayList<Categoria> categorias;

	public ProductoCRUDApp(Connection connection) {
		// Configurar la ventana principal
		setTitle("Producto CRUD App");
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
		tableModel.addColumn("Stock");
		tableModel.addColumn("Precio Venta");
		tableModel.addColumn("Precio Compra");
		tableModel.addColumn("Cod. Marca");
		tableModel.addColumn("Num. Categoria");
		tableModel.addColumn("Est. Reg.");
		tableEstadoRegistro = new JTable(tableModel);

		JScrollPane scrollPane = new JScrollPane(tableEstadoRegistro);

		// Agregar los componentes al panel principal
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JPanel inputPanel = new JPanel();
		GridBagLayout gbl_inputPanel = new GridBagLayout();
		gbl_inputPanel.columnWidths = new int[] { 36, 100, 100, 100, 100, 36, 0 };
		gbl_inputPanel.rowHeights = new int[] { 18, 30, 30, 30, 30, 30, 30, 30, 0 };
		gbl_inputPanel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_inputPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
		GridBagConstraints gbc_lblStock = new GridBagConstraints();
		gbc_lblStock.fill = GridBagConstraints.BOTH;
		gbc_lblStock.insets = new Insets(0, 0, 5, 5);
		gbc_lblStock.gridx = 1;
		gbc_lblStock.gridy = 3;
		JLabel label_3;
		lblStock = new JLabel("Stock:");
		inputPanel.add(lblStock, gbc_lblStock);
		textFieldStock = new JTextField(20);
		GridBagConstraints gbc_textFieldStock = new GridBagConstraints();
		gbc_textFieldStock.gridwidth = 2;
		gbc_textFieldStock.fill = GridBagConstraints.BOTH;
		gbc_textFieldStock.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldStock.gridx = 3;
		gbc_textFieldStock.gridy = 3;
		inputPanel.add(textFieldStock, gbc_textFieldStock);
		GridBagConstraints gbc_lblPrecioVenta = new GridBagConstraints();
		gbc_lblPrecioVenta.fill = GridBagConstraints.BOTH;
		gbc_lblPrecioVenta.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrecioVenta.gridx = 1;
		gbc_lblPrecioVenta.gridy = 4;
		JLabel lblPrecioVenta = new JLabel("Precio Venta:");
		inputPanel.add(lblPrecioVenta, gbc_lblPrecioVenta);
		
		spinnerPreVenta = new JSpinner();
		spinnerPreVenta.setModel(new SpinnerNumberModel(0.10, 0.00, null, 0.01));
		GridBagConstraints gbc_spinnerPreVenta = new GridBagConstraints();
		gbc_spinnerPreVenta.fill = GridBagConstraints.BOTH;
		gbc_spinnerPreVenta.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerPreVenta.gridx = 2;
		gbc_spinnerPreVenta.gridy = 4;
		inputPanel.add(spinnerPreVenta, gbc_spinnerPreVenta);
		GridBagConstraints gbc_lblPrecioCompra = new GridBagConstraints();
		gbc_lblPrecioCompra.fill = GridBagConstraints.BOTH;
		gbc_lblPrecioCompra.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrecioCompra.gridx = 3;
		gbc_lblPrecioCompra.gridy = 4;
		JLabel lblPrecioCompra = new JLabel("Precio Compra:");
		inputPanel.add(lblPrecioCompra, gbc_lblPrecioCompra);
		
		spinnerPreCompra = new JSpinner();
		spinnerPreCompra.setModel(new SpinnerNumberModel(0.10, 0.00, null, 0.01));
		GridBagConstraints gbc_spinnerPreCompra = new GridBagConstraints();
		gbc_spinnerPreCompra.fill = GridBagConstraints.BOTH;
		gbc_spinnerPreCompra.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerPreCompra.gridx = 4;
		gbc_spinnerPreCompra.gridy = 4;
		inputPanel.add(spinnerPreCompra, gbc_spinnerPreCompra);
		GridBagConstraints gbc_lblTelfono = new GridBagConstraints();
		gbc_lblTelfono.anchor = GridBagConstraints.EAST;
		gbc_lblTelfono.fill = GridBagConstraints.VERTICAL;
		gbc_lblTelfono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelfono.gridx = 1;
		gbc_lblTelfono.gridy = 5;
		JLabel lblTelfono = new JLabel("Marca :");
		inputPanel.add(lblTelfono, gbc_lblTelfono);
		
		comboMarca = new JComboBox<Marca>();
		GridBagConstraints gbc_comboMarca = new GridBagConstraints();
		gbc_comboMarca.gridwidth = 2;
		gbc_comboMarca.insets = new Insets(0, 0, 5, 5);
		gbc_comboMarca.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboMarca.gridx = 3;
		gbc_comboMarca.gridy = 5;
		inputPanel.add(comboMarca, gbc_comboMarca);
		GridBagConstraints gbc_lblCategoria = new GridBagConstraints();
		gbc_lblCategoria.anchor = GridBagConstraints.EAST;
		gbc_lblCategoria.fill = GridBagConstraints.VERTICAL;
		gbc_lblCategoria.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategoria.gridx = 1;
		gbc_lblCategoria.gridy = 6;
		JLabel lblCategoria = new JLabel("Categoria:");
		inputPanel.add(lblCategoria, gbc_lblCategoria);
		
		comboCategoria = new JComboBox<Categoria>();
		GridBagConstraints gbc_comboCategoria = new GridBagConstraints();
		gbc_comboCategoria.gridwidth = 2;
		gbc_comboCategoria.insets = new Insets(0, 0, 5, 5);
		gbc_comboCategoria.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboCategoria.gridx = 3;
		gbc_comboCategoria.gridy = 6;
		inputPanel.add(comboCategoria, gbc_comboCategoria);

		label_3 = new JLabel("Estado de Registro:");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.fill = GridBagConstraints.BOTH;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 7;
		inputPanel.add(label_3, gbc_label_3);

		textFieldEstReg = new JTextField(20);
		GridBagConstraints gbc_textFieldEstReg = new GridBagConstraints();
		gbc_textFieldEstReg.fill = GridBagConstraints.BOTH;
		gbc_textFieldEstReg.gridwidth = 2;
		gbc_textFieldEstReg.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldEstReg.gridx = 3;
		gbc_textFieldEstReg.gridy = 7;
		inputPanel.add(textFieldEstReg, gbc_textFieldEstReg);

		buttonAdicionar = new JButton("Adicionar");
		GridBagConstraints gbc_buttonAdicionar = new GridBagConstraints();
		gbc_buttonAdicionar.fill = GridBagConstraints.BOTH;
		gbc_buttonAdicionar.insets = new Insets(0, 0, 5, 5);
		gbc_buttonAdicionar.gridx = 1;
		gbc_buttonAdicionar.gridy = 8;
		inputPanel.add(buttonAdicionar, gbc_buttonAdicionar);

		buttonModificar = new JButton("Modificar");
		GridBagConstraints gbc_buttonModificar = new GridBagConstraints();
		gbc_buttonModificar.fill = GridBagConstraints.BOTH;
		gbc_buttonModificar.insets = new Insets(0, 0, 5, 5);
		gbc_buttonModificar.gridx = 2;
		gbc_buttonModificar.gridy = 8;
		inputPanel.add(buttonModificar, gbc_buttonModificar);

		buttonEliminar = new JButton("Eliminar");
		GridBagConstraints gbc_buttonEliminar = new GridBagConstraints();
		gbc_buttonEliminar.fill = GridBagConstraints.BOTH;
		gbc_buttonEliminar.insets = new Insets(0, 0, 5, 5);
		gbc_buttonEliminar.gridx = 3;
		gbc_buttonEliminar.gridy = 8;
		inputPanel.add(buttonEliminar, gbc_buttonEliminar);

		buttonCancelar = new JButton("Cancelar");
		GridBagConstraints gbc_buttonCancelar = new GridBagConstraints();
		gbc_buttonCancelar.fill = GridBagConstraints.BOTH;
		gbc_buttonCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_buttonCancelar.gridx = 4;
		gbc_buttonCancelar.gridy = 8;
		inputPanel.add(buttonCancelar, gbc_buttonCancelar);

		buttonInactivar = new JButton("Inactivar");
		GridBagConstraints gbc_buttonInactivar = new GridBagConstraints();
		gbc_buttonInactivar.fill = GridBagConstraints.BOTH;
		gbc_buttonInactivar.insets = new Insets(0, 0, 0, 5);
		gbc_buttonInactivar.gridx = 1;
		gbc_buttonInactivar.gridy = 9;
		inputPanel.add(buttonInactivar, gbc_buttonInactivar);

		buttonReactivar = new JButton("Reactivar");
		GridBagConstraints gbc_buttonReactivar = new GridBagConstraints();
		gbc_buttonReactivar.insets = new Insets(0, 0, 0, 5);
		gbc_buttonReactivar.fill = GridBagConstraints.BOTH;
		gbc_buttonReactivar.gridx = 2;
		gbc_buttonReactivar.gridy = 9;
		inputPanel.add(buttonReactivar, gbc_buttonReactivar);

		buttonActualizar = new JButton("Actualizar");
		GridBagConstraints gbc_buttonActualizar = new GridBagConstraints();
		gbc_buttonActualizar.fill = GridBagConstraints.BOTH;
		gbc_buttonActualizar.insets = new Insets(0, 0, 0, 5);
		gbc_buttonActualizar.gridx = 3;
		gbc_buttonActualizar.gridy = 9;
		inputPanel.add(buttonActualizar, gbc_buttonActualizar);

		buttonSalir = new JButton("Salir");
		GridBagConstraints gbc_buttonSalir = new GridBagConstraints();
		gbc_buttonSalir.fill = GridBagConstraints.BOTH;
		gbc_buttonSalir.insets = new Insets(0, 0, 0, 5);
		gbc_buttonSalir.gridx = 4;
		gbc_buttonSalir.gridy = 9;
		inputPanel.add(buttonSalir, gbc_buttonSalir);

		panel.add(scrollPane, BorderLayout.CENTER);
		panel.add(inputPanel, BorderLayout.NORTH);
		
		tableEstadoRegistro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				int row = tableEstadoRegistro.rowAtPoint(evt.getPoint());
				if (row >= 0) {
					textFieldCodigo.setText(tableEstadoRegistro.getModel().getValueAt(row, 0).toString());
					textFieldNombre.setText(tableEstadoRegistro.getModel().getValueAt(row, 1).toString());
					textFieldStock.setText(tableEstadoRegistro.getModel().getValueAt(row, 2).toString());
					spinnerPreVenta.setValue(Double.parseDouble(tableEstadoRegistro.getModel().getValueAt(row, 3).toString()));
					spinnerPreCompra.setValue(Double.parseDouble(tableEstadoRegistro.getModel().getValueAt(row, 4).toString()));
					comboMarca.setSelectedItem(tableEstadoRegistro.getModel().getValueAt(row, 4));
					comboCategoria.setSelectedItem(tableEstadoRegistro.getModel().getValueAt(row, 6).toString());
					textFieldEstReg.setText(tableEstadoRegistro.getModel().getValueAt(row, 7).toString());
				}
			}
		});
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
		textFieldStock.setEditable(false);
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
		llenarComboBox();
	}

	protected void evaluateOption(int option) {
		String codigo;
		String estReg;
		try {
			switch (option) {
			case 0:
				limpiarCampos();
				statement = connection.prepareStatement(
						"INSERT IGNORE INTO Producto ( PrdNom, PrdSto, PrdPreVen, PrdPreCom, MarCod, CatNum) VALUES (?, ?, ?, ?, ?, ?)");
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
						"UPDATE Producto SET PrdNom = ?, PrdSto = ?, PrdPreVen = ?, PrdPreCom = ?, MarCod = ?, CatNum = ? WHERE PrdCod = ?");
				protegerCampos(false);
				hasUpdate = true;
				break;
			case 2:
				codigo = textFieldCodigo.getText();
				if (codigo.length()<1) {
					throw new Exception("No hay un registro seleccionado");
				}
				statement = connection.prepareStatement("UPDATE Producto SET EstRegCod = 3 WHERE PrdCod = ?");
				textFieldEstReg.setText("ELIMINADO");
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
				statement = connection.prepareStatement("UPDATE Producto SET EstRegCod = 2 WHERE PrdCod = ?");
				textFieldEstReg.setText("INACTIVO");
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
				statement = connection.prepareStatement("UPDATE Producto SET EstRegCod = 1 WHERE PrdCod = ?");
				textFieldEstReg.setText("ACTIVO");

				hasUpdate = true;
				break;
			case 5:
				if (hasUpdate) {
					int PrdCod = Integer.parseInt(textFieldCodigo.getText());
					String PrdNom = textFieldNombre.getText();
					int PrdSto = Integer.parseInt(textFieldStock.getText());
					double PrdPreVen = Double.parseDouble(spinnerPreVenta.getValue().toString());
					double PrdPreCom = Double.parseDouble(spinnerPreCompra.getValue().toString());
					int MarCod = ((Marca)comboMarca.getSelectedItem()).getMarCod();
					int CatNum = ((Categoria)comboCategoria.getSelectedItem()).getCatNum();
				
					switch (lastOption) {
					case 0:
						statement.setString(1, PrdNom);
						statement.setInt(2, PrdSto);
						statement.setDouble(3, PrdPreVen);
						statement.setDouble(4, PrdPreCom);
						statement.setInt(5, MarCod);
						statement.setInt(6, CatNum);
						break;
					case 1:
						statement.setString(1, PrdNom);
						statement.setInt(2, PrdSto);
						statement.setDouble(3, PrdPreVen);
						statement.setDouble(4, PrdPreCom);
						statement.setInt(5, MarCod);
						statement.setInt(6, CatNum);
						statement.setInt(7, PrdCod);
						break;
					case 2:
					case 3:
					case 4:
						statement.setInt(1, PrdCod);
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
		textFieldCodigo.setEditable(!isProtect);
		textFieldNombre.setEditable(!isProtect);
		textFieldStock.setEditable(!isProtect);
		((DefaultEditor) spinnerPreVenta.getEditor()).getTextField().setEditable(!isProtect);
		((DefaultEditor) spinnerPreCompra.getEditor()).getTextField().setEditable(!isProtect);
		comboMarca.setEditable(!isProtect);
		comboCategoria.setEditable(!isProtect);
	}

	private void cargarTabla() {
		try {
			limpiarCampos();
			protegerCampos(true);
			// Limpiar la tabla antes de cargar los datos
			tableModel.setRowCount(0);

			// Consultar los registros de la base de datos
			String sql = "SELECT * FROM Producto WHERE NOT EstRegCod='3';";
			statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();

			// Agregar los registros a la tabla
			while (result.next()) {
				String PrdCod = result.getString("PrdCod");
				String PrdNom = result.getString("PrdNom");
				String PrdSto = result.getString("PrdSto");
				String PrdPreVen = result.getString("PrdPreVen");
				String PrdPreCom = result.getString("PrdPreCom");
				String MarCod = result.getString("MarCod");
				String CatNum = result.getString("CatNum");
				String EstRegCod = result.getString("EstRegCod");
				
				Object[] row = {PrdCod, PrdNom, PrdSto, PrdPreVen, PrdPreCom, MarCod, CatNum, EstRegCod};
				tableModel.addRow(row);
			}

			// Cerrar el resultado y la declaración
			result.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void llenarComboBox() {
		try {
			// Consultar los registros de la base de datos
			String sql = "SELECT MarCod,MarNom FROM Marca WHERE NOT EstRegCod='3';";
			statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			
			
			MutableComboBoxModel<Marca> model = (MutableComboBoxModel<Marca>)comboMarca.getModel();
			// Agregar los registros a la tabla
			marcas = new ArrayList<Marca>();
			while (result.next()) {
				marcas.add(new Marca(Integer.parseInt(result.getString("MarCod")),result.getString("MarNom")));
				model.addElement( marcas.get(marcas.size()-1));
			}
			comboMarca = new JComboBox<Marca>(model);
			
			// Cerrar el resultado y la declaración
			result.close();
			statement.close();
			// Consultar los registros de la base de datos
			sql = "SELECT CatNum,CatNom FROM Categoria WHERE NOT EstRegCod='3';";
			statement = connection.prepareStatement(sql);
			result = statement.executeQuery();
			
			
			MutableComboBoxModel<Categoria> model2 = (MutableComboBoxModel<Categoria>)comboCategoria.getModel();
			
			// Agregar los registros a la tabla
			categorias = new ArrayList<Categoria>();
			while (result.next()) {
				categorias.add(new Categoria(Integer.parseInt(result.getString("CatNum")),result.getString("CatNom")));
				model2.addElement(categorias.get(categorias.size()-1));
			}
			comboCategoria = new JComboBox<Categoria>(model2);
			// Cerrar el resultado y la declaración
			
			result.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void limpiarCampos() {
		textFieldCodigo.setText("");
		textFieldNombre.setText("");
		textFieldStock.setText("");
		textFieldEstReg.setText("");
	}
}
