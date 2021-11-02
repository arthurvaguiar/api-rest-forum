package br.com.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.forum.controller.dto.TopicoDto;
import br.com.forum.controller.form.AtualizacaoTopicoForm;
import br.com.forum.controller.form.TopicoForm;
import br.com.forum.service.TopicoService;

@RestController
@RequestMapping(value = "/topicos")
public class TopicosController {

    @Autowired
    private TopicoService topicoService;

    @GetMapping
    @Cacheable(value = "listaDeTopicos")
    public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso,
                                 @PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
        return topicoService.lista(nomeCurso, paginacao);

    }

    @PostMapping
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
        return topicoService.cadastrar(form, uriBuilder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable("id") Long id) {
        return topicoService.detalhar(id);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDto> atualizar(@PathVariable("id") Long id,
                                               @RequestBody @Valid AtualizacaoTopicoForm form) {
        return topicoService.atualizar(id, form);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<?> remover(@PathVariable Long id) {
        return topicoService.remover(id);
    }

}
