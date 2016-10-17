import java.sql.*;

public class Test {
	public static void main(String args[]) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/studentDB?useSSL=false","root","adbms1234");
			Statement statement = connection.createStatement();
			ResultSet resultSet;
			int resultUpdate;
			/*resultUpdate = statement.executeUpdate("create table student(id int PRIMARY KEY, name varchar(40), department varchar(40), year int)");
			resultUpdate = statement.executeUpdate("insert into student values(1, \"Vatsal Kanakiya\", \"Computer\", 3);");
			resultUpdate = statement.executeUpdate("insert into student values(2, \"Hasti Sawla\", \"Computer\", 3);");*/
			resultSet = statement.executeQuery("select * from student;");
			while(resultSet.next()) {
				System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getInt(4));
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
