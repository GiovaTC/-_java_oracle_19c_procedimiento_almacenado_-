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

    private static void mostrarProductos(Connection conn) {
    }

    private static void actualizarProducto(Connection conn, int i, String cuadernoProfesional, int i1, int i2) {
        
    }

    // metodo para llamar al procedimiento almacenado .
    
}