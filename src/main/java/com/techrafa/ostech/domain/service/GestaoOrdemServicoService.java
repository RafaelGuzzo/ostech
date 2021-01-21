package com.techrafa.ostech.domain.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techrafa.ostech.domain.exception.NegocioException;
import com.techrafa.ostech.domain.model.Cliente;
import com.techrafa.ostech.domain.model.OrdemServico;
import com.techrafa.ostech.domain.model.StatusOrdemServico;
import com.techrafa.ostech.domain.repository.ClienteRepository;
import com.techrafa.ostech.domain.repository.OrderServicoRepository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrderServicoRepository orderServicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado!"));

		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(LocalDateTime.now());

		return orderServicoRepository.save(ordemServico);
	}

}
