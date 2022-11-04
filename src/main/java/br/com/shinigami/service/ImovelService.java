package br.com.shinigami.service;


import br.com.shinigami.dto.imovel.ImovelCreateDTO;
import br.com.shinigami.dto.imovel.ImovelDTO;
import br.com.shinigami.exceptions.BancoDeDadosException;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.model.Imovel;
import br.com.shinigami.repository.ImovelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ImovelService implements ServiceInterface<ImovelDTO, ImovelCreateDTO> {
    private final ImovelRepository imovelRepository;
    private final EnderecoService enderecoService;
    private final ClienteService clienteService;
    private final ObjectMapper objectMapper;

    @Override
    public List<ImovelDTO> list() throws RegraDeNegocioException {
        List<Imovel> listar = imovelRepository.findAll();
        return listar.stream()
                .map(imovel -> {
                    try {
                        ImovelDTO imovelDTO = objectMapper.convertValue(imovel, ImovelDTO.class);
                        imovelDTO.setEndereco(enderecoService.findByIdDto(imovel.getIdEndereco()));
                        imovelDTO.setDono(clienteService.buscarCliente(imovel.getIdDono()));
                        return imovelDTO;
                    } catch (RegraDeNegocioException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    public ImovelDTO buscarImovel(Integer id) throws RegraDeNegocioException {
        Imovel imovel = objectMapper.convertValue(imovelRepository.findById(id), Imovel.class);
        if (imovel == null) {
            throw new RegraDeNegocioException("Imovel não encontrando!");
        }
        ImovelDTO imovelDTO = objectMapper.convertValue(imovel, ImovelDTO.class);
        imovelDTO.setEndereco(enderecoService.findByIdDto(imovel.getIdEndereco()));
        imovelDTO.setDono(clienteService.buscarCliente(imovel.getIdDono()));
        return imovelDTO;
    }

    @Override
    public ImovelDTO create(ImovelCreateDTO imovel) throws RegraDeNegocioException {
        Imovel imovelEntity = imovelRepository.save(objectMapper.convertValue(imovel, Imovel.class));
        ImovelDTO imovelDTO = objectMapper.convertValue(imovelEntity, ImovelDTO.class);
        imovelDTO.setEndereco(enderecoService.findByIdDto(imovel.getIdEndereco()));
        imovelDTO.setDono(clienteService.buscarCliente(imovel.getIdDono()));
        return imovelDTO;
    }

    @Override
    public ImovelDTO update(Integer id, ImovelCreateDTO imovel) throws RegraDeNegocioException {
        Imovel imovelEntity = objectMapper.convertValue(imovelRepository.findById(id), Imovel.class);
        if ( imovelEntity == null) {
            throw new RegraDeNegocioException("Imovel Não Encontrado");
        }
        imovelRepository.save(imovelEntity);
        ImovelDTO imovelDTO = objectMapper.convertValue(imovelEntity, ImovelDTO.class);
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

//    public List<ImovelDTO> listarImoveisDisponiveis() throws RegraDeNegocioException {
//        List<Imovel> listar = imovelRepository.findAllByAlugadoAndAtivo();
//        return listar.stream()
//                .map(imovel -> {
//                    try {
//                        ImovelDTO imovelDTO = objectMapper.convertValue(imovel, ImovelDTO.class);
//                        imovelDTO.setEndereco(enderecoService.findByIdDto(imovel.getIdEndereco()));
//                        imovelDTO.setDono(clienteService.buscarCliente(imovel.getIdDono()));
//                        return imovelDTO;
//                    } catch (RegraDeNegocioException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .toList();
//    }

}

