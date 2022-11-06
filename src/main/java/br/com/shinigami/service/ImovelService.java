package br.com.shinigami.service;


import br.com.shinigami.dto.PageDTO;
import br.com.shinigami.dto.imovel.ImovelCreateDTO;
import br.com.shinigami.dto.imovel.ImovelDTO;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.model.Imovel;
import br.com.shinigami.model.Tipo;
import br.com.shinigami.repository.ImovelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ImovelService implements ServiceInterface<ImovelDTO, ImovelCreateDTO> {
    private final ImovelRepository imovelRepository;
    private final EnderecoService enderecoService;
    private final ClienteService clienteService;
    private final ObjectMapper objectMapper;

    public PageDTO<ImovelDTO> list(Integer page){

        PageRequest pageRequest = PageRequest.of(page, 1);
        Page<Imovel> pageImovel = imovelRepository.findAllByAtivo(Tipo.S, pageRequest);
        List<ImovelDTO> listar = pageImovel.getContent().stream()
                .map(imovel -> converteParaImovelDTO(imovel))
                .toList();
        return new PageDTO<>(pageImovel.getTotalElements(),
                pageImovel.getTotalPages(),
                page, pageRequest.getPageSize(), listar);
    }

    public ImovelDTO findByIdImovel(Integer id) throws RegraDeNegocioException {
        Imovel imovel = objectMapper.convertValue(imovelRepository.findById(id), Imovel.class);
        if (imovel == null) {
            throw new RegraDeNegocioException("Imovel não encontrando!");
        }
        ImovelDTO imovelDTO = converteParaImovelDTO(imovel);
        return imovelDTO;
    }

    @Override
    public ImovelDTO create(ImovelCreateDTO imovelNovo) throws RegraDeNegocioException {
        Imovel imovel = objectMapper.convertValue(imovelNovo, Imovel.class);
        imovel.setEndereco(enderecoService.findById(imovelNovo.getIdEndereco()));
        imovel.setCliente(clienteService.findById(imovelNovo.getIdDono()));
        imovel.setAtivo(Tipo.S);
        imovelRepository.save(imovel);
        ImovelDTO imovelDTO = converteParaImovelDTO(imovel);
        return imovelDTO;
    }

    @Override
    public ImovelDTO update(Integer id, ImovelCreateDTO imovelNovo) throws RegraDeNegocioException {
        Imovel imovel = objectMapper.convertValue(imovelRepository.findByIdImovelAndAtivo(id, Tipo.S), Imovel.class);
        if (imovel == null) {
            throw new RegraDeNegocioException("Imovel Não Encontrado");
        }
        imovel.setEndereco(enderecoService.findById(imovelNovo.getIdEndereco()));
        imovel.setCliente(clienteService.findById(imovelNovo.getIdDono()));
        imovelRepository.save(imovel);
        ImovelDTO imovelDTO = converteParaImovelDTO(imovel);
        return imovelDTO;
    }

    @Override
    public void delete(Integer id) throws RegraDeNegocioException {
        Imovel imovel = objectMapper.convertValue(imovelRepository.findById(id), Imovel.class);
        if (imovel == null) {
            throw new RegraDeNegocioException("Imovel não Encontrado!");
        }
        imovelRepository.delete(imovel);
    }

    public List<ImovelDTO> listarImoveisDisponiveis() throws RegraDeNegocioException {
        List<Imovel> listar = imovelRepository.findAllByAlugadoAndAtivo(Tipo.N, Tipo.S);
        return listar.stream()
                .map(imovel -> converteParaImovelDTO(imovel))
                .toList();
    }

    private ImovelDTO converteParaImovelDTO(Imovel imovel){

            ImovelDTO imovelDTO = objectMapper.convertValue(imovel, ImovelDTO.class);
            imovelDTO.setEndereco(enderecoService.findByIdEnderecoDto(imovel.getIdEndereco()));
            imovelDTO.setDono(clienteService.findByIdClienteDto(imovel.getIdDono()));
            return imovelDTO;

    }

    public Imovel findById(Integer id) throws RegraDeNegocioException {
        Imovel imovel = objectMapper.convertValue(imovelRepository.findById(id), Imovel.class);
        if (imovel == null) {
            throw new RegraDeNegocioException("Imovel não encontrando!");
        }
        return imovel;
    }

}

