package br.com.newgo.mercado.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class Disco {

    @Value("${contato.disco.raiz}")
    private String raiz;

    @Value("${contato.disco.diretorio-fotos}")
    private String diretorioFotos;

    public String salvarFoto(MultipartFile foto){
        return this.salvar(this.diretorioFotos, foto);
    }

    public String salvar(String diretorio, MultipartFile arquivo){
        Path diretorioPath = Paths.get(this.raiz, diretorio);
        String novoNome = UUID.randomUUID().toString() +
                getExtensao(arquivo.getOriginalFilename());
        Path arquivoPath = diretorioPath.resolve(novoNome);
        try{
            Files.createDirectories(diretorioPath);
            arquivo.transferTo(arquivoPath.toFile());
            return novoNome;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo.");
        }
    }

    private String getExtensao(String arquivoNome){
        return arquivoNome.substring(arquivoNome.lastIndexOf(".") - 1);
    }
}
