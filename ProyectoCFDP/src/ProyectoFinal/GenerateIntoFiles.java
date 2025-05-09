package ProyectoFinal;

import java.io.*;
import java.util.*;

public class GenerateIntoFiles {

    public static void main(String[] args) {
        try {
            int numVendedores = 10;
            int numProductos = 19;
            int ventasPorVendedor = 10;

            File folder = new File("data");
            if (!folder.exists()) folder.mkdir();

            createProductsFile(numProductos);

            // Nombres y apellidos 
            String[] nombresLista = {
                "Carlos", "Oscar", "Nicolas", "Cristian", "Nicolas",
                "Luisa", "Juliana", "Karen", "Laura", "Camila"
            };
            String[] apellidosLista = {
                "Parra", "Blanco", "Peña", "Leon", "Jimenez",
                "Arango", "Hernandez", "Fontecha", "Lopez", "Campiño"
            };

         
            BufferedWriter writerVendedores = new BufferedWriter(new FileWriter("data/vendedores.txt"));
            Random rand = new Random();

            List<Long> cedulasVendedores = new ArrayList<>();

            for (int i = 0; i < numVendedores; i++) {
                String tipoDoc = "CC";
                long id = 10000000L + rand.nextInt(90000000);

              
                while (cedulasVendedores.contains(id)) {
                    id = 10000000L + rand.nextInt(90000000);
                }

                cedulasVendedores.add(id);
                String nombre = nombresLista[i];
                String apellido = apellidosLista[i];

                writerVendedores.write(tipoDoc + ";" + id + ";" + nombre + ";" + apellido);
                writerVendedores.newLine();

               
                createSalesMenFile(ventasPorVendedor, nombre + " " + apellido, id, numProductos);
            }

            writerVendedores.close();
            System.out.println("Archivos generados exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al generar archivos: " + e.getMessage());
        }
    }

    public static void createProductsFile(int productsCount) throws IOException {
        String[] productos = {
            "IPHONE 11", "IPHONE 12", "IPHONE 13", "IPHONE 14", "IPHONE 15",
            "IPHONE 16", "IPHONE 15 PRO", "IPHONE 15 PRO MAX", "IPHONE 16 PRO", "IPHONE 16 PRO MAX",
            "IPHONE 11 PRO", "IPHONE 11 PRO MAX", "IPHONE 12 PRO", "IPHONE 12 PRO MAX",
            "IMAC", "MAC BOOK AIR", "IPAD AIR", "IPAD MINI", "IPAD PRO"
        };

        double[] preciosCOP = {
            2.876040, 3.287912, 3.699784, 4.111656, 4.523528,
            4.935400, 5.347272, 5.759144, 6.171016, 6.582888,
            3.078540, 3.480412, 3.882284, 4.284156,
            5.347272, 4.111656, 2.462936, 1.641956, 3.287912
        };

        BufferedWriter writer = new BufferedWriter(new FileWriter("data/productos.txt"));

        for (int i = 1; i <= productsCount; i++) {
            writer.write("P" + i + ";" + productos[i - 1] + ";" + preciosCOP[i - 1]);
            writer.newLine();
        }

        writer.close();
    }

    public static void createSalesMenFile(int randomSalesCount, String name, long id, int numProductos) throws IOException {
        Map<Integer, String> productosMap = loadProductsMap();

        BufferedWriter writer = new BufferedWriter(new FileWriter("data/ventas_" + id + ".txt"));

        writer.write("Nombre: " + name);
        writer.newLine();
        writer.write("Cédula: " + id);
        writer.newLine();

        Random rand = new Random();
        int[] ventasPorProducto = new int[numProductos + 1];

        for (int i = 0; i < randomSalesCount; i++) {
            int productoId = rand.nextInt(numProductos) + 1;
            String productoNombre = productosMap.get(productoId);
            int cantidad = rand.nextInt(5) + 1;

            writer.write(productoNombre + ";" + cantidad);
            writer.newLine();

            ventasPorProducto[productoId] += cantidad;
        }

        writer.newLine();
        writer.write("Unidades vendidas de los 19 productos:");
        writer.newLine();
        for (int i = 1; i <= numProductos; i++) {
            String productoNombre = productosMap.get(i);
            writer.write(productoNombre + ": " + ventasPorProducto[i] + " unidades");
            writer.newLine();
        }

        writer.newLine();
        writer.write("Top 10 productos más vendidos:");
        writer.newLine();

        List<Map.Entry<Integer, Integer>> listaVentas = new ArrayList<>();
        for (int i = 1; i <= numProductos; i++) {
            listaVentas.add(new AbstractMap.SimpleEntry<>(i, ventasPorProducto[i]));
        }

        listaVentas.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        for (int i = 0; i < Math.min(10, listaVentas.size()); i++) {
            int productoId = listaVentas.get(i).getKey();
            String productoNombre = productosMap.get(productoId);
            int cantidad = listaVentas.get(i).getValue();
            writer.write(productoNombre + ": " + cantidad + " unidades");
            writer.newLine();
        }

        writer.close();
    }

    private static Map<Integer, String> loadProductsMap() throws IOException {
        Map<Integer, String> productosMap = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader("data/productos.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            int productId = Integer.parseInt(parts[0].substring(1)); // Eliminar 'P' del ID
            productosMap.put(productId, parts[1]); // Almacenar el nombre del producto
        }
        reader.close();
        return productosMap;
    }
}
