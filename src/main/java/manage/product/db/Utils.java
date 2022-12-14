package manage.product.db;

import manage.product.domain.Produto;
import org.springframework.context.annotation.Bean;

import java.sql.*;
import java.util.ArrayList;
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

    public static ArrayList<Produto> listar() {
        ArrayList<Produto> list = new ArrayList<>();

        String BUSCAR_TODOS = "SELECT * FROM produto;";

        try {
            Connection conn = conectar();
            PreparedStatement produtos = conn.prepareStatement(BUSCAR_TODOS);
            ResultSet res = produtos.executeQuery();

            if (res.next() != false) {
                do {
                    Produto p = new Produto();

                    p.setId(res.getInt(1));
                    p.setNome(res.getString(2));
                    p.setPreco(res.getDouble(3));
                    p.setEstoque(res.getInt(4));

                    list.add(p);
                } while (res.next());
            }
            produtos.close();
            desconectar(conn);

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao listar produtos.");
            System.exit(-42);
        }

        return new ArrayList<>();
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

    public static void atualizar () {
        System.out.println("Informe o código do produto que deseja atualizar: ");
        Integer id = Integer.parseInt(teclado.nextLine());

        String BUSCA_POR_ID = "SELECT * FROM produto WHERE id=?;";

        try {
            Connection conn = conectar();
            PreparedStatement produto = conn.prepareStatement(BUSCA_POR_ID);
            produto.setInt(1, id);
            ResultSet res = produto.executeQuery();

            if (res.next() != false) {
                System.out.println("Informe o nome do produto: ");
                String nome = teclado.nextLine();

                System.out.println("Informe o preço do(a) " + nome.toUpperCase() + ": ");
                Float preco = teclado.nextFloat();

                System.out.println("Informe a quantidade do(a) " + nome.toUpperCase() + " em estoque: ");
                Integer estoque = teclado.nextInt();

                String ATUALIZAR = "UPDATE produto SET nome=?, preco=?, estoque=? WHERE id=?;";
                PreparedStatement upd = conn.prepareStatement(ATUALIZAR);

                upd.setString(1, nome);
                upd.setFloat(2, preco);
                upd.setInt(3, estoque);
                upd.setInt(4, id);

                upd.executeUpdate();
                upd.close();
                desconectar(conn);
                System.out.println("O produto " + nome + " foi atualizado com sucesso.");
            } else {
                System.out.println("Não existe produto com ID = " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar o produto!");
            System.exit(-42);
        }
    }

    public static void deletar () {
        System.out.println("Informe o codigo do produto que deseja deletar: ");
        Integer id = Integer.parseInt(teclado.nextLine());

        try {
            Connection conn = conectar();
            String BUSCAR_POR_ID = "SELECT * FROM produto WHERE id = ?;";
            PreparedStatement produto = conn.prepareStatement(BUSCAR_POR_ID);

            produto.setInt(1, id);
            ResultSet res = produto.executeQuery();

            if (res.next() != false) {
                String DELETAR = "DELETE FROM produto WHERE id = ?;";
                PreparedStatement deletar = conn.prepareStatement(DELETAR);

                deletar.setInt(1, id);

                deletar.executeUpdate();
                deletar.close();
                desconectar(conn);
                System.out.println("O produtos id = " + id + " foi deletado com sucesso.");
            } else {
                System.out.println("Erro ao deletar o produto");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao deletar o produto com ID = " + id);
            System.exit(-42);
        }
    }

    public static void menu () {
        System.out.println("===========Gerenciamento de Produtos===========");
        System.out.println("\n1 - Listar produtos");
        System.out.println("2 - Inserir produto");
        System.out.println("3 - Atualizar produto");
        System.out.println("4 - Deletar produto");

        System.out.println("\nSelecione uma opcao: ");

        Integer opcao = Integer.parseInt(teclado.nextLine());

        switch (opcao) {
            case 1:
                listar();
                break;
            case 2:
                inserir();
                break;
            case 3:
                atualizar();
                break;
            case 4:
                deletar();
                break;
            default:
                menu();
        }
    }
}
