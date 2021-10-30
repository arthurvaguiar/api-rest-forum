package br.com.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.forum.modelo.Curso;
import br.com.forum.repository.CursoRepository;

@Service
public class CursoService {

	@Autowired
	private CursoRepository cursoRepository;

	public Curso buscarPorNome(String nomeCurso) {
		return cursoRepository.findByNome(nomeCurso);
	}

}
