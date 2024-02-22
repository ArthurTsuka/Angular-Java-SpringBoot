package com.example.ponderada.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ponderada.dto.UsuarioDTO;
import com.example.ponderada.service.UsuarioService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("")
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<UsuarioDTO> createUser(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO createdUser = usuarioService.createUser(usuarioDTO);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<UsuarioDTO> updateUser(@PathVariable("userId") Integer userId, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO updatedUser = usuarioService.updateUser(usuarioDTO, userId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    @ResponseBody
    public String deleteUser(@PathVariable("userId") Integer userId) {
        return usuarioService.deleteUser(userId);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<UsuarioDTO> loginUser(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO authenticatedUser = usuarioService.authenticateUser(usuarioDTO.getEmail(), usuarioDTO.getSenha());
            return ResponseEntity.ok(authenticatedUser);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<UsuarioDTO> getUserById(@PathVariable("userId") Integer userId) {
        try {
            UsuarioDTO user = usuarioService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
