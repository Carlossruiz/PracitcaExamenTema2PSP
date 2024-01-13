package es.practica2.tema2PSP.rest;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import es.practica2.tema2PSP.exception.VideoclubException;



@RequestMapping(value = "/videoclub", produces = {"application/json"})
@RestController
public class videoclubRest {
	//se le pasa archivo json y la sesion para almacenar en sesion
	@RequestMapping(method = RequestMethod.POST, value = "/movies/upload", consumes ={"multipart/form-data"})
	public ResponseEntity<?> uploadMovies(@RequestPart("jsonFile") MultipartFile jsonFile, HttpSession session) throws VideoclubException
	{
		try
		{
			//Lee el json como un string
			String movies = new String(FileCopyUtils.copyToByteArray(jsonFile.getInputStream()));
			//Almacenar en sesion
			session.setAttribute("movies",movies);
			System.out.println("Contenido JSON guardado en sesión: " + movies);
			return ResponseEntity.ok("La carga de datos fue realizada correctamente");
		}
		catch(IOException exception)
		{
			String error = "Error al leer el archivo";
			throw new VideoclubException(500, error);
		}
	}
	 @RequestMapping(method = RequestMethod.GET, value = "/movies/download", produces = {"multipart/form-data"})
	public ResponseEntity<?> downloadMovies(@SessionAttribute("movies") String movies)
	{
		
		return ResponseEntity.ok().body(movies);
		
	}
	@RequestMapping(method = RequestMethod.POST, value = "/users/upload", consumes = {"multipart/form-data"})
	public ResponseEntity<?> uploadUsers(@RequestPart("jsonFile") MultipartFile jsonFile, HttpSession session) throws VideoclubException
	{
		try
		{
			//Lee el json como un string
			String users = new String(FileCopyUtils.copyToByteArray(jsonFile.getInputStream()));
			//Almacenar en sesion
			session.setAttribute("users", users);
			
			return ResponseEntity.ok("La carga de datos fue realizada correctamente");
		}
		catch(IOException exception)
		{
			String error = "Error al leer el archivo";
			throw new VideoclubException(500, error);
		}
	}
	@RequestMapping(method = RequestMethod.GET, value = "/users/download", produces = {"multipart/form-data"})
	public ResponseEntity<?> downloadUsers(@SessionAttribute("users") String users)
	{
		//String jsonContent = (String)session.getAttribute("jsonContent");
		return ResponseEntity.ok().body(users);
		
	}
	
}
