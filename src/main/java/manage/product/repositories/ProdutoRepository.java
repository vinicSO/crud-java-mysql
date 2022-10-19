package manage.product.repositories;

import manage.product.db.Utils;
import manage.product.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    default ArrayList<Produto> listAll() {
        return Utils.listar();
    }
}
