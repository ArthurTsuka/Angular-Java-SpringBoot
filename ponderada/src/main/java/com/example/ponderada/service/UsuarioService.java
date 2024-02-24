package com.example.ponderada.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ponderada.dto.UsuarioDTO;
import com.example.ponderada.model.entity.UsuarioEntity;
import com.example.ponderada.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para converter de Entity para DTO, agora incluindo as novas variáveis
    public UsuarioDTO converter(UsuarioEntity usuarioEntity) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsuario_id(usuarioEntity.getUsuario_id());
        usuarioDTO.setNome(usuarioEntity.getNome());
        usuarioDTO.setEmail(usuarioEntity.getEmail());
        usuarioDTO.setCpf(usuarioEntity.getCpf());
        usuarioDTO.setSenha(usuarioEntity.getSenha());
        usuarioDTO.setNacionalidade(usuarioEntity.getNacionalidade());
        usuarioDTO.setTelefone(usuarioEntity.getTelefone());
        usuarioDTO.setDataNascimento(usuarioEntity.getDataNascimento());
        return usuarioDTO;
    }

    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream().map(this::converter).collect(Collectors.toList());
    }

    // Método para criar um usuário, agora incluindo as novas variáveis
    public UsuarioDTO createUser(UsuarioDTO usuarioDTO) {
        UsuarioEntity usuario = new UsuarioEntity();
        // Configuração dos atributos, incluindo os novos
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setNacionalidade(usuarioDTO.getNacionalidade());
        usuario.setTelefone(usuarioDTO.getTelefone());
        usuario.setDataNascimento(usuarioDTO.getDataNascimento());
        usuarioRepository.save(usuario);
        return converter(usuario);
    }

    // Método para atualizar um usuário, agora incluindo as novas variáveis
    public UsuarioDTO updateUser(UsuarioDTO usuarioDTO, Integer userId) {
        Optional<UsuarioEntity> userOptional = usuarioRepository.findById(userId);
        if (userOptional.isPresent()) {
            UsuarioEntity usuario = userOptional.get();
            usuario.setNome(usuarioDTO.getNome());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setCpf(usuarioDTO.getCpf());
            usuario.setSenha(usuarioDTO.getSenha());
            usuario.setNacionalidade(usuarioDTO.getNacionalidade());
            usuario.setTelefone(usuarioDTO.getTelefone());
            usuario.setDataNascimento(usuarioDTO.getDataNascimento());

            usuarioRepository.save(usuario);
            return converter(usuario);
        } else {
            throw new NoSuchElementException("Usuário com id: " + userId + " não foi encontrado");
        }
    }

    public UsuarioDTO getUserById(Integer userId) {
        Optional<UsuarioEntity> userOptional = usuarioRepository.findById(userId);
        if (userOptional.isPresent()) {
            UsuarioEntity usuario = userOptional.get();
            return converter(usuario);
        } else {
            throw new NoSuchElementException("Usuário com id: " + userId + " não foi encontrado");
        }
    }

    public String deleteUser(Integer userId) {
        usuarioRepository.deleteById(userId);
        return "Usuário deletado com sucesso";
    }

    public UsuarioDTO authenticateUser(String email, String senha) {
        Optional<UsuarioEntity> userOptional = usuarioRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            UsuarioEntity usuario = userOptional.get();
            if(usuario.getSenha().equals(senha)) {
                return converter(usuario);
            } else {
                throw new IllegalArgumentException("Senha incorreta");
            }
        } else {
            throw new NoSuchElementException("Usuário não encontrado");
        }
    }
}
