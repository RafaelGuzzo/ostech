package com.techrafa.ostech.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.techrafa.ostech.api.model.Comentario;
import com.techrafa.ostech.domain.exception.EntidadeNaoEncontradaException;
import com.techrafa.ostech.domain.exception.NegocioException;
import com.techrafa.ostech.domain.model.Cliente;
import com.techrafa.ostech.domain.model.OrdemServico;
import com.techrafa.ostech.domain.model.StatusOrdemServico;
import com.techrafa.ostech.domain.repository.ClienteRepository;
import com.techrafa.ostech.domain.repository.ComentarioRepository;
import com.techrafa.ostech.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private ComentarioRepository comentarioRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado!"));

		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());

		return ordemServicoRepository.save(ordemServico);
	}

	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);

		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);

		return comentarioRepository.save(comentario);
	}

	public void finalizar(@PathVariable Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		ordemServico.finalizar();

		ordemServicoRepository.save(ordemServico);

	}

	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada!"));
	}

}
