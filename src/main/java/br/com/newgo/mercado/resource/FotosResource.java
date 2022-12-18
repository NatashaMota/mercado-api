package br.com.newgo.mercado.resource;

import br.com.newgo.mercado.storage.Disco;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/fotos")
public class FotosResource {

    private Disco disco;

    public FotosResource(Disco disco) {
        this.disco = disco;
    }

    @PostMapping({"", "/"})
    public void upload(@RequestParam MultipartFile foto){
        disco.salvarFoto(foto);
    }
}
