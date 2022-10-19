package manage.product.services;

import manage.product.domain.Produto;
import manage.product.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public ArrayList<Produto> listAll() {
        return repository.listAll();
    }
}
