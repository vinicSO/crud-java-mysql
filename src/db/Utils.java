package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Utils {

    static Scanner teclado = new Scanner(System.in);

    public static Connection conectar () {
        String CLASSE_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USUARIO = "vinic";
        String SENHA = "vinic";
        String URL_SERVIDOR = "jdbc:mysql://localhost:3306/jmysql?useSSL=false";

        try {
            Class.forName(CLASSE_DRIVER);
            return DriverManager.getConnection(URL_SERVIDOR, USUARIO, SENHA);
        } catch (Exception e) {
            if (e instanceof ClassNotFoundException) {
                System.out.println("Verifique o driver de conexão.");
            } else {
                System.out.println("Verifique se o servidor está ativo.");
            }
            System.exit(-42);
            return null;
        }
    }

    public static void desconectar (Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println("Não foi possível fechar a conexão.");
                e.printStackTrace();
            }
        }
    }
}
