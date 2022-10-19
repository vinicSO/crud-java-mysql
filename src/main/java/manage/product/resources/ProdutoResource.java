package manage.product.resources;

import manage.product.domain.Produto;
import manage.product.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Produto>> findAll () {
        ArrayList<Produto> result = new ArrayList<>();
        result = service.listAll();

        return ResponseEntity.ok().body(result);
    }
}
