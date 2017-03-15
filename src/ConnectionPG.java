import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by joanes on 3/13/17.
 */
public class ConnectionPG {

	public static Connection connect() {
		Connection c = null;

		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection("jdbc:postgresql://localhost:5432/gaztea",
							"gaztea", "gaztea");
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Succesfully");
		return c;

	}

	public static void insertValue(Connection conn, Long tiempo, Integer threads, Integer tasks, String app, Long trabajo) {

		// create a Statement from the connection
		Statement statement = null;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("INSERT INTO resultados (tiempo, threads, tasks, app, trabajo) VALUES " +
					"(" + tiempo + ", " + threads + ", " + tasks + ", '" + app + "', " + trabajo + ")");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


}
