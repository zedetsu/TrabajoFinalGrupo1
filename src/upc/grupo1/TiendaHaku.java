package upc.grupo1;

import java.util.Scanner;

public class TiendaHaku {
    private static String[] productos = {"Otaku Anime", "Otaku Manga", "Otaku Idols", "Otaku Militar"};
    private static double[] costos = {17, 15, 12, 8};
    private static String[] tallas = {"S", "M", "L"};
    private static String[] colores = {"Blanco", "Negro"};
    private static int[][][] stock = {
            {
                    {10, 15, 20},  // Otaku Anime (S, M, L) - Blanco
                    {5, 8, 12}     // Otaku Anime (S, M, L) - Negro
            },
            {
                    {8, 12, 18},   // Otaku Manga (S, M, L) - Blanco
                    {4, 6, 10}     // Otaku Manga (S, M, L) - Negro
            },
            {
                    {6, 9, 15},    // Otaku Idols (S, M, L) - Blanco
                    {3, 5, 9}      // Otaku Idols (S, M, L) - Negro
            },
            {
                    {12, 18, 24},  // Otaku Militar (S, M, L) - Blanco
                    {10, 15, 20}   // Otaku Militar (S, M, L) - Negro
            }
    };

    private static String[][] ventas = {
            {"Otaku Anime", "Blanco", "S", "2", "Sin personalización", "2023-07-09"},
            {"Otaku Manga", "Negro", "M", "1", "Con personalización", "2023-07-10"},
            {"Otaku Idols", "Blanco", "L", "3", "Sin personalización", "2023-07-11"}
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        while (continuar) {
            System.out.println("Bienvenido al sistema de gestión de pedidos de Haku");
            System.out.println("1. Realizar pedido");
            System.out.println("2. Verificar stock");
            System.out.println("3. Ver stock total");
            System.out.println("4. Ver los productos más vendidos");
            System.out.println("5. Ver las tallas más vendidas");
            System.out.println("6. Ver los colores más vendidos");
            System.out.println("7. Salir");
            System.out.print("Ingrese la opción deseada: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1:
                    realizarPedido(scanner);
                    break;
                case 2:
                    verificarStock(scanner);
                    break;
                case 3:
                    verStockTotal();
                    break;
                case 4:
                    verProductosMasVendidos();
                    break;
                case 5:
                    verTallasMasVendidas();
                    break;
                case 6:
                    verColoresMasVendidos();
                    break;
                case 7:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
                    break;
            }
            System.out.println();
        }
        System.out.println("Gracias por utilizar el sistema de gestión de pedidos de Haku. ¡Hasta luego!");
    }

    private static void realizarPedido(Scanner scanner) {
        System.out.println("Ingrese los detalles del pedido:");

        // Solicitar información del pedido
        System.out.print("Producto (Otaku Anime, Otaku Manga, Otaku Idols, Otaku Militar): ");
        String producto = scanner.nextLine();
        System.out.print("Color (Blanco, Negro): ");
        String color = scanner.nextLine();
        System.out.print("Talla (S, M, L): ");
        String talla = scanner.nextLine();
        System.out.print("Cantidad: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Detalles de personalización: ");
        String personalizacion = scanner.nextLine();
        System.out.print("Fecha de entrega: ");
        String fechaEntrega = scanner.nextLine();

        // Verificar disponibilidad de productos
        int productoIndex = buscarProducto(producto);
        int colorIndex = buscarColor(color);
        int tallaIndex = buscarTalla(talla);
        if (productoIndex != -1 && colorIndex != -1 && tallaIndex != -1) {
            int stockDisponible = stock[productoIndex][colorIndex][tallaIndex];
            if (stockDisponible >= cantidad) {
                // Confirmar el pedido
                double costoTotal = costos[productoIndex] * cantidad;
                System.out.println("¡Pedido confirmado!");
                System.out.println("Producto: " + producto);
                System.out.println("Color: " + color);
                System.out.println("Talla: " + talla);
                System.out.println("Cantidad: " + cantidad);
                System.out.println("Detalles de personalización: " + personalizacion);
                System.out.println("Fecha de entrega: " + fechaEntrega);
                System.out.println("Costo total: $" + costoTotal);

                // Actualizar stock
                stock[productoIndex][colorIndex][tallaIndex] -= cantidad;

                // Guardar la venta realizada
                String[] venta = {producto, color, talla, Integer.toString(cantidad), personalizacion, fechaEntrega};
                ventas = agregarVenta(ventas, venta);
            } else {
                System.out.println("No hay suficiente stock disponible para el producto solicitado.");
            }
        } else {
            System.out.println("El producto, color o talla ingresados no existen.");
        }
    }

    private static void verificarStock(Scanner scanner) {
        System.out.println("Ingrese el producto, color y talla para verificar el stock:");

        // Solicitar información del producto
        System.out.print("Producto (Otaku Anime, Otaku Manga, Otaku Idols, Otaku Militar): ");
        String producto = scanner.nextLine();
        System.out.print("Color (Blanco, Negro): ");
        String color = scanner.nextLine();
        System.out.print("Talla (S, M, L): ");
        String talla = scanner.nextLine();

        // Verificar disponibilidad de productos
        int productoIndex = buscarProducto(producto);
        int colorIndex = buscarColor(color);
        int tallaIndex = buscarTalla(talla);
        if (productoIndex != -1 && colorIndex != -1 && tallaIndex != -1) {
            int stockDisponible = stock[productoIndex][colorIndex][tallaIndex];
            System.out.println("Stock disponible para " + producto + " en color " + color + " y talla " + talla + ": " + stockDisponible);
        } else {
            System.out.println("El producto, color o talla ingresados no existen.");
        }
    }

    private static void verStockTotal() {
        System.out.println("Stock total:");

        for (int i = 0; i < productos.length; i++) {
            System.out.println(productos[i] + ":");
            for (int j = 0; j < colores.length; j++) {
                System.out.print("\tColor " + colores[j] + ":");
                for (int k = 0; k < tallas.length; k++) {
                    System.out.print(" " + tallas[k] + ": " + stock[i][j][k]);
                }
                System.out.println();
            }
        }
    }

    private static int buscarProducto(String producto) {
        for (int i = 0; i < productos.length; i++) {
            if (productos[i].equalsIgnoreCase(producto)) {
                return i;
            }
        }
        return -1;
    }

    private static int buscarColor(String color) {
        for (int i = 0; i < colores.length; i++) {
            if (colores[i].equalsIgnoreCase(color)) {
                return i;
            }
        }
        return -1;
    }

    private static int buscarTalla(String talla) {
        for (int i = 0; i < tallas.length; i++) {
            if (tallas[i].equalsIgnoreCase(talla)) {
                return i;
            }
        }
        return -1;
    }

    private static String[][] agregarVenta(String[][] ventasActuales, String[] venta) {
        String[][] nuevasVentas = new String[ventasActuales.length + 1][6];
        for (int i = 0; i < ventasActuales.length; i++) {
            nuevasVentas[i] = ventasActuales[i];
        }
        nuevasVentas[nuevasVentas.length - 1] = venta;
        return nuevasVentas;
    }
    private static void verProductosMasVendidos() {
        System.out.println("Productos más vendidos:");

        String[] productosVendidos = new String[productos.length];
        int[] cantidadVendida = new int[productos.length];

        for (String[] venta : ventas) {
            String producto = venta[0];
            int cantidad = Integer.parseInt(venta[3]);

            int index = buscarProducto(producto);
            if (index != -1) {
                cantidadVendida[index] += cantidad;
            }
        }

        // Ordenar los productos y cantidades de forma descendente
        for (int i = 0; i < productos.length - 1; i++) {
            for (int j = i + 1; j < productos.length; j++) {
                if (cantidadVendida[j] > cantidadVendida[i]) {
                    // Intercambiar productos
                    String tempProducto = productos[j];
                    productos[j] = productos[i];
                    productos[i] = tempProducto;

                    // Intercambiar cantidades
                    int tempCantidad = cantidadVendida[j];
                    cantidadVendida[j] = cantidadVendida[i];
                    cantidadVendida[i] = tempCantidad;
                }
            }
        }

        // Mostrar los productos más vendidos
        for (int i = 0; i < productos.length; i++) {
            System.out.println(productos[i] + ": " + cantidadVendida[i]);
        }
    }

    private static void verTallasMasVendidas() {
        System.out.println("Tallas más vendidas:");

        String[] tallasVendidas = new String[tallas.length];
        int[] cantidadVendida = new int[tallas.length];

        for (String[] venta : ventas) {
            String talla = venta[2];
            int cantidad = Integer.parseInt(venta[3]);

            int index = buscarTalla(talla);
            if (index != -1) {
                cantidadVendida[index] += cantidad;
            }
        }

        // Ordenar las tallas y cantidades de forma descendente
        for (int i = 0; i < tallas.length - 1; i++) {
            for (int j = i + 1; j < tallas.length; j++) {
                if (cantidadVendida[j] > cantidadVendida[i]) {
                    // Intercambiar tallas
                    String tempTalla = tallas[j];
                    tallas[j] = tallas[i];
                    tallas[i] = tempTalla;

                    // Intercambiar cantidades
                    int tempCantidad = cantidadVendida[j];
                    cantidadVendida[j] = cantidadVendida[i];
                    cantidadVendida[i] = tempCantidad;
                }
            }
        }

        // Mostrar las tallas más vendidas
        for (int i = 0; i < tallas.length; i++) {
            System.out.println(tallas[i] + ": " + cantidadVendida[i]);
        }
    }

    private static void verColoresMasVendidos() {
        System.out.println("Colores más vendidos:");

        String[] coloresVendidos = new String[colores.length];
        int[] cantidadVendida = new int[colores.length];

        for (String[] venta : ventas) {
            String color = venta[1];
            int cantidad = Integer.parseInt(venta[3]);

            int index = buscarColor(color);
            if (index != -1) {
                cantidadVendida[index] += cantidad;
            }
        }

        // Ordenar los colores y cantidades de forma descendente
        for (int i = 0; i < colores.length - 1; i++) {
            for (int j = i + 1; j < colores.length; j++) {
                if (cantidadVendida[j] > cantidadVendida[i]) {
                    // Intercambiar colores
                    String tempColor = colores[j];
                    colores[j] = colores[i];
                    colores[i] = tempColor;

                    // Intercambiar cantidades
                    int tempCantidad = cantidadVendida[j];
                    cantidadVendida[j] = cantidadVendida[i];
                    cantidadVendida[i] = tempCantidad;
                }
            }
        }

        // Mostrar los colores más vendidos
        for (int i = 0; i < colores.length; i++) {
            System.out.println(colores[i] + ": " + cantidadVendida[i]);
        }
    }
}