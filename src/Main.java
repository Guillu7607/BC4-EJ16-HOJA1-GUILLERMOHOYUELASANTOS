import java.sql.*;

public class Main {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "RIBERA";
    private static final String PASS = "ribera";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            System.out.println("Conexión exitosa a Oracle.");

            ejecutar(conn, "VENTAS");

        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }

    public static void ejecutar(Connection conn, String nombreDepto) {
        String sql = "SELECT E.NOMBRE AS NOM_EMP, D.NOMBRE AS NOM_DEP " +
                "FROM EMPLEADO E " +
                "JOIN DEPARTAMENTO D ON E.DEP_ID = D.ID " +
                "WHERE D.NOMBRE = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombreDepto);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("--- Empleados del departamento: " + nombreDepto + " ---");

                boolean encontrado = false;
                while (rs.next()) {
                    encontrado = true;
                    // Extraemos el nombre del empleado
                    String nomEmp = rs.getString("NOM_EMP");
                    System.out.println("Empleado: " + nomEmp);
                }

                if (!encontrado) {
                    System.out.println("No se encontraron empleados en el departamento: " + nombreDepto);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }
    }
}
