package br.com.forum.service;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.forum.controller.dto.TopicoDto;
import br.com.forum.controller.form.AtualizacaoTopicoForm;
import br.com.forum.controller.form.TopicoForm;
import br.com.forum.modelo.Curso;
import br.com.forum.modelo.Topico;
import br.com.forum.repository.TopicoRepository;

@Service
public class TopicoService {

	@Autowired
	private TopicoRepository topicoRepository;

	@Autowired
	private CursoService cursoService;

	public Page<TopicoDto> lista(String nomeCurso, Pageable paginacao) {
		if (nomeCurso == null) {
			Page<Topico> topicos = topicoRepository.findAll(paginacao);
			return TopicoDto.converter(topicos);
		} else {
			Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
			return TopicoDto.converter(topicos);
		}
	}

	@Transactional
	public ResponseEntity<TopicoDto> cadastrar(@Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = this.converter(form);
		topicoRepository.save(topico);
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}

	private Topico converter(TopicoForm form) {
		Curso curso = cursoService.buscarPorNome(form.getNomeCurso());
		return new Topico(form.getNomeCurso(), form.getMensagem(), curso);
	}

	public ResponseEntity<DetalhesDoTopicoDto> detalhar(Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if (!topico.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));

	}

	@Transactional
	public ResponseEntity<TopicoDto> atualizar(Long id, @Valid AtualizacaoTopicoForm form) {
		Optional<Topico> topicoFind = topicoRepository.findById(id);

		if (!topicoFind.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Topico topico = form.atualizar(id, topicoRepository);
		return ResponseEntity.ok(new TopicoDto(topico));
	}

	@Transactional
	public ResponseEntity<?> remover(Long id) {
		Optional<Topico> topicoFind = topicoRepository.findById(id);
		
		if (!topicoFind.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		topicoRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
