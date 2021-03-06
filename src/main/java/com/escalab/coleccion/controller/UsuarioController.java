package com.escalab.coleccion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escalab.coleccion.exception.ModeloNotFoundException;
import com.escalab.coleccion.model.Usuario;
import com.escalab.coleccion.service.IUsuarioService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private IUsuarioService userService;

//Listar usuarios
	@GetMapping("/lista")
	@ApiOperation(value="Listar Usuarios", notes="Listar todos los usuarios")
	public ResponseEntity<List<Usuario>> getAll(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("DBA")) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN") )) {
			List<Usuario> lista = userService.listar();
			return new ResponseEntity<List<Usuario>>(lista, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Usuario>>( HttpStatus.UNAUTHORIZED);
		}
	}

//ListarUsuario por ID
	@GetMapping("/{id}")
	@ApiOperation(value="Buscar Usuario por ID", notes="Buscar usuario por ID en el endpoint")
	public ResponseEntity<Usuario> listarPorId(@PathVariable("id") Integer id){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("DBA")) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN") )) {
			Usuario obj = userService.leerPorId(id);
			if(obj.getIdUsuario() == null) {
				throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
			}
			return new ResponseEntity<Usuario>(obj, HttpStatus.OK);
		}else {
			return new ResponseEntity<Usuario>(new Usuario(), HttpStatus.UNAUTHORIZED);
		}
	}

}

//@PreAuthorize("hasRole('ADMIN')")