package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Ruta del archivo de texto que contiene los datos de conexión
        String rutaArchivo = "connection.txt";

        // Cargar los datos de conexión desde el archivo
        ConnectionData connectionData = loadConnectionData(rutaArchivo);

        if (connectionData != null) {
            // Establecer la conexión
            try {
                Connection conexion = DriverManager.getConnection(connectionData.getUrl());
                System.out.println("Conexion exitosa a la base de datos!");

                // Realizar operaciones en la base de datos...
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new ProductoCRUDApp(conexion).setVisible(true);
                    }
                });
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        } else {
            System.out.println("Error al cargar los datos de conexión desde el archivo.");
        }
    }

    private static ConnectionData loadConnectionData(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String url = br.readLine();

            return new ConnectionData(url);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        return null;
    }
}

class ConnectionData {
    private String url;

    public ConnectionData(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
