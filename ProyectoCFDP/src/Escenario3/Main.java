package Escenario3;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
            List<Vendedor> vendedores = cargarVendedores();
            List<Producto> productos = cargarProductos();

            for (Vendedor vendedor : vendedores) {
                String fileName = "data/ventas_" + vendedor.getId() + ".txt";
                File file = new File(fileName);
                if (file.exists()) {
                    System.out.println("Reporte de ventas para: " + vendedor.getNombre() + " " + vendedor.getApellido());
                    mostrarReporte(fileName);
                    System.out.println();
                } else {
                    System.out.println("Archivo no encontrado para el vendedor: " + vendedor.getNombre());
                }
            }
        } catch (Exception e) {
            System.err.println("Error al generar los reportes: " + e.getMessage());
        }
    }

    public static List<Vendedor> cargarVendedores() throws IOException {
        List<Vendedor> vendedores = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("data/vendedores.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            if (parts.length >= 4) {
                long id = Long.parseLong(parts[1]);
                String nombre = parts[2];
                String apellido = parts[3];
                vendedores.add(new Vendedor(id, nombre, apellido));
            }
        }
        reader.close();
        return vendedores;
    }

    public static List<Producto> cargarProductos() throws IOException {
        List<Producto> productos = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("data/productos.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            if (parts.length >= 3) {
                String id = parts[0];
                String nombre = parts[1];
                double precio = Double.parseDouble(parts[2]);
                productos.add(new Producto(id, nombre, precio));
            }
        }
        reader.close();
        return productos;
    }

    public static void mostrarReporte(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }
}

