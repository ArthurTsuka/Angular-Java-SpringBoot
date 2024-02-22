package com.example.ponderada.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ponderada.dto.UsuarioDTO;
import com.example.ponderada.model.entity.UsuarioEntity;
import com.example.ponderada.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDTO converter(UsuarioEntity usuarioEntity) {
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setUsuario_id(usuarioEntity.getUsuario_id());
        usuario.setNome(usuarioEntity.getNome());
        usuario.setEmail(usuarioEntity.getEmail());
        usuario.setCpf(usuarioEntity.getCpf());
        usuario.setSenha(usuarioEntity.getSenha());
        return usuario;
    }

    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream().map(this::converter).collect(Collectors.toList());
    }

    public UsuarioDTO createUser(UsuarioDTO usuarioDTO) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsuario_id(usuarioDTO.getUsuario_id());
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setSenha(usuarioDTO.getSenha());
        usuarioRepository.save(usuario);
        return usuarioDTO;
    }

    public UsuarioDTO updateUser(UsuarioDTO usuarioDTO, Integer userId) {
        Optional<UsuarioEntity> userOptional = usuarioRepository.findById(userId);
        if (userOptional.isPresent()) {
            UsuarioEntity usuario = userOptional.get();
            usuario.setNome(usuarioDTO.getNome());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setCpf(usuarioDTO.getCpf());
            usuario.setSenha(usuarioDTO.getSenha());

            usuarioRepository.save(usuario);
            
            return usuarioDTO;
        } else {
            throw new NoSuchElementException("Usuario com id: " + userId + " não foi encontrado");
        }
    }

    public UsuarioDTO getUserById(Integer userId) {
        Optional<UsuarioEntity> userOptional = usuarioRepository.findById(userId);
        if (userOptional.isPresent()) {
            UsuarioEntity usuario = userOptional.get();
            return converter(usuario);
        } else {
            throw new NoSuchElementException("Usuario com id: " + userId + " não foi encontrado");

        }
    }

    public String deleteUser (@PathVariable("userId") Integer userId) {
        usuarioRepository.deleteById(userId);
        return "Usuário Deletado com Sucesso";
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
