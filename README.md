# -_java_oracle_19c_procedimiento_almacenado_- :.
🧾 Java + Oracle 19c (Procedimiento Almacenado).

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/6eebd204-f427-434e-9bef-f7345dc628f7" />  

```

📌 Actualización y Consulta de Productos de Papelería.

Solución completa, funcional y lista para ejecutar en Java (consola en IntelliJ) + Oracle 19c, utilizando PL/SQL para actualizar productos y mostrar los resultados por consola.

🧩 1. Script Oracle 19c (Tabla + Datos)
-- Tabla de productos de papelería
CREATE TABLE PRODUCTOS_PAPELERIA (
    ID          NUMBER PRIMARY KEY,
    NOMBRE      VARCHAR2(100),
    PRECIO      NUMBER(10,2),
    STOCK       NUMBER
);

-- Datos de ejemplo
INSERT INTO PRODUCTOS_PAPELERIA VALUES (1, 'Cuaderno', 5000, 20);
INSERT INTO PRODUCTOS_PAPELERIA VALUES (2, 'Lapiz', 1000, 50);
INSERT INTO PRODUCTOS_PAPELERIA VALUES (3, 'Borrador', 800, 30);
INSERT INTO PRODUCTOS_PAPELERIA VALUES (4, 'Regla', 1500, 15);
INSERT INTO PRODUCTOS_PAPELERIA VALUES (5, 'Marcador', 2500, 25);

COMMIT;

🧩 2. Procedimiento Almacenado (UPDATE)
CREATE OR REPLACE PROCEDURE ACTUALIZAR_PRODUCTO (
    P_ID        IN NUMBER,
    P_NOMBRE    IN VARCHAR2,
    P_PRECIO    IN NUMBER,
    P_STOCK     IN NUMBER
)
IS
BEGIN
    UPDATE PRODUCTOS_PAPELERIA
    SET 
        NOMBRE = P_NOMBRE,
        PRECIO = P_PRECIO,
        STOCK = P_STOCK
    WHERE ID = P_ID;

    COMMIT;
END;
/

🧩 3. Dependencia (Driver Oracle)

Asegúrate de incluir el driver JDBC de Oracle:

ojdbc8.jar

📌 Agregar en IntelliJ:
Project Structure → Libraries → Add JAR

🧩 4. Programa Java (Consola)
import java.sql.*;

public class Main {

    // Cambia estos datos según tu entorno
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "SYSTEM";
    private static final String PASS = "12345";

    public static void main(String[] args) {

        Connection conn = null;

        try {
            // Conexión
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ Conectado a Oracle");

            // 🔹 1. Actualizar producto usando procedimiento almacenado
            actualizarProducto(conn, 1, "Cuaderno Profesional", 7000, 40);

            // 🔹 2. Mostrar todos los productos
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

    // Método para llamar el procedimiento almacenado
    public static void actualizarProducto(Connection conn, int id, String nombre, double precio, int stock) {
        try {
            CallableStatement cs = conn.prepareCall("{call ACTUALIZAR_PRODUCTO(?, ?, ?, ?)}");

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

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar producto");
            e.printStackTrace();
        }
    }

    // Método para consultar e imprimir todos los registros
    public static void mostrarProductos(Connection conn) {
        try {
            String sql = "SELECT * FROM PRODUCTOS_PAPELERIA ORDER BY ID";
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

        } catch (SQLException e) {
            System.out.println("❌ Error al consultar productos");
            e.printStackTrace();
        }
    }
}

🧩 5. Resultado Esperado en Consola
✅ Conectado a Oracle

🔄 Producto actualizado correctamente:
ID: 1
Nombre: Cuaderno Profesional
Precio: 7000.0
Stock: 40

📋 LISTADO DE PRODUCTOS:
--------------------------------------------------
ID: 1 | Nombre: Cuaderno Profesional | Precio: 7000.0 | Stock: 40
ID: 2 | Nombre: Lapiz | Precio: 1000.0 | Stock: 50
ID: 3 | Nombre: Borrador | Precio: 800.0 | Stock: 30
ID: 4 | Nombre: Regla | Precio: 1500.0 | Stock: 15
ID: 5 | Nombre: Marcador | Precio: 2500.0 | Stock: 25
--------------------------------------------------
✅ Características Técnicas
Uso de CallableStatement para ejecutar procedimientos PL/SQL
Transacción controlada desde Oracle (COMMIT en el SP)
Consulta eficiente con PreparedStatement
Salida formateada en consola
Arquitectura simple (ideal para aprendizaje, pruebas técnicas o base de proyectos) / .
