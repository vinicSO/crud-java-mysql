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

    public static void listar() {
        String BUSCAR_TODOS = "SELECT * FROM produto;";

        try {
            Connection conn = conectar();
            PreparedStatement produtos = conn.prepareStatement(BUSCAR_TODOS);
            ResultSet res = produtos.executeQuery();

            if (res.next() != false) {
                System.out.println("Listando produtos...");
                System.out.println("--------------------");

                do {
                    System.out.println("ID: " + res.getInt(1));
                    System.out.println("PRODUTO: " + res.getString(2));
                    System.out.println("PREÇO: " + res.getFloat(3));
                    System.out.println("ESTOQUE: " + res.getInt(4));
                    System.out.println("--------------------");
                } while (res.next());
            } else {
                System.out.println("Não possui registros.");
            }
            produtos.close();
            desconectar(conn);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao listar produtos.");
            System.exit(-42);
        }
    }

    public static void inserir () {
        System.out.println("Informe o nome do produto: ");
        String nome = teclado.nextLine();

        System.out.println("Informe o preço do(a) " + nome.toUpperCase() + ": ");
        Float preco = teclado.nextFloat();

        System.out.println("Informe a quantidade do(a) " + nome.toUpperCase() + " em estoque: ");
        Integer estoque = teclado.nextInt();

        String INSERIR = "INSERT INTO produto (nome, preco, estoque) VALUES (?, ?, ?);";
        //SQL INJECTION

        try {
            Connection conn = conectar();
            PreparedStatement salvar = conn.prepareStatement(INSERIR);

            salvar.setString(1, nome);
            salvar.setFloat(2, preco);
            salvar.setInt(3, estoque);

            salvar.executeUpdate();
            salvar.close();
            desconectar(conn);
            System.out.println("O produto " + nome.toUpperCase() + " foi inserido com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao inserir produto!");
            System.exit(-42);
        }
    }
}
