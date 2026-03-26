import oracle.jdbc.proxy.annotation.Pre;

import java.sql.*;

public class Main {

    // cambia estos datos segun tu entorno .
    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/orcl";
    private static final String USER = "system";
    private static final String PASS = "Tapiero123";

    public static void main(String[] args) {

        Connection conn = null;

        try {
            // conexion .
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ Conectado a Oracle");
            
            // actualizar producto usando procedimiento almacenado .
            actualizarProducto(conn, 1, "Cuaderno Profesional", 7000, 40);
            
            // mostrar  todos los productos .
            mostrarProductos(conn);
        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // metodo para llamar al procedimiento almacenado .
    private static void actualizarProducto(Connection conn, int id, String nombre, double precio, int stock) {
        try {
            CallableStatement cs =  conn.prepareCall("{ call ACTUALIZAR_PRODUCTO(?, ?, ?, ?)}");

            cs.setInt(1, id);
            cs.setString(2, nombre);
            cs.setDouble(3, precio);
            cs.setInt(4, stock);

            cs.execute();

            System.out.println("\n🔄 Producto actualizado correctamente:");
            System.out.println("ID: " + id);
            System.out.println("Nombre: " + nombre);
            System.out.println("Precio: " + precio);
            System.out.println("Stock: " + stock);

            cs.close();
        } catch (Exception e) {
            System.out.println("❌ Error al actualizar producto");
            e.printStackTrace();
        }
    }

    // Método para consultar e imprimir todos los registros .
    private static void mostrarProductos(Connection conn) {
        try {
            String sql = "SELECT *  FROM PRODUCTOS_PAPELERIA ORDER BY ID";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n📋 LISTADO DE PRODUCTOS:");
            System.out.println("--------------------------------------------------");

            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getInt("ID") +
                                " | Nombre: " + rs.getString("NOMBRE") +
                                " | Precio: " + rs.getDouble("PRECIO") +
                                " | Stock: " + rs.getInt("STOCK")
                );
            }

            System.out.println("--------------------------------------------------");

            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("❌ Error al consultar productos");
            e.printStackTrace();
        }
    }   
}