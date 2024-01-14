package es.practica2.tema2PSP.rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.practica2.tema2PSP.exception.VideoclubException;
import es.practica2.tema2PSP.models.Movie;
import es.practica2.tema2PSP.models.MovieError;
import es.practica2.tema2PSP.models.User;



@RequestMapping(value = "/videoclub", produces = {"application/json"})
@RestController
public class videoclubRest {
	//se le pasa archivo json y la sesion para almacenar en sesion
	@RequestMapping(method = RequestMethod.POST, value = "/movies/upload", consumes ={"multipart/form-data"})
	public ResponseEntity<?> uploadMovies(@RequestPart("jsonFile") MultipartFile jsonFile, HttpSession session) throws VideoclubException {
	    try {
	        // Lee el json como un string
	        String moviesJson = new String(FileCopyUtils.copyToByteArray(jsonFile.getInputStream()));
	        // Convierte el json a una lista de objetos Movie utilizando ObjectMapper
	        ObjectMapper objectMapper = new ObjectMapper();
	        List<Movie> movies = objectMapper.readValue(moviesJson, new TypeReference<List<Movie>>() {});
	        // Almacena la lista de películas en sesión
	        session.setAttribute("movies", movies);
	        System.out.println("Películas guardadas en sesión: " + movies);
	        return ResponseEntity.ok("La carga de datos fue realizada correctamente");
	    } catch(IOException exception) {
	        String error = "Error al leer el archivo";
	        throw new VideoclubException(500, error);
	    }
	}
	@RequestMapping(method = RequestMethod.GET, value = "/movies/download", produces = {"application/json"})
	public ResponseEntity<?> downloadMovies(HttpSession session) {
	    // Obtener la lista de películas de la sesión
	    List<Movie> movies = (List<Movie>) session.getAttribute("movies");
	    if (movies != null) {
	        try {
	            // Convertir la lista de películas a JSON utilizando ObjectMapper
	            ObjectMapper objectMapper = new ObjectMapper();
	            String moviesJson = objectMapper.writeValueAsString(movies);
	            // Crear un archivo temporal
	            File tempFile = File.createTempFile("movies", ".json");
	            // Escribir los datos de películas en el archivo temporal
	            FileWriter writer = new FileWriter(tempFile);
	            writer.write(moviesJson);
	            writer.close();
	            // Obtener el contenido del archivo temporal en un array de bytes
	            byte[] contenidoDelFichero = Files.readAllBytes(tempFile.toPath());
	            // Convertir el array de bytes en un formato que entiende Spring Boot
	            InputStreamResource outcomeInputStreamResource = new InputStreamResource(new ByteArrayInputStream(contenidoDelFichero));
	            // Para enviar cabeceras en la respuesta, haremos uso de la clase HttpHeaders
	            HttpHeaders headers = new HttpHeaders();
	            headers.set("Content-Disposition", "attachment; filename=" + tempFile.getName());
	            // Generar la respuesta
	            return ResponseEntity.ok().headers(headers).body(outcomeInputStreamResource);
	        } catch (IOException ex) {
	            // Manejar cualquier excepción que ocurra durante el proceso
	            return ResponseEntity.status(500).body("Error al generar el archivo JSON de películas");
	        }
	    } else {
	        return ResponseEntity.status(404).body("No se encontraron datos de películas en la sesión");
	    }
	}
	 @RequestMapping(method = RequestMethod.POST, value = "/users/upload", consumes = {"multipart/form-data"})
	 public ResponseEntity<?> uploadUsers(@RequestPart("jsonFile") MultipartFile jsonFile, HttpSession session) throws VideoclubException {
	     try {
	         // Leer el json como un string
	         String usersJson = new String(FileCopyUtils.copyToByteArray(jsonFile.getInputStream()));
	         // Convertir el json a una lista de objetos User utilizando ObjectMapper
	         ObjectMapper objectMapper = new ObjectMapper();
	         List<User> users = objectMapper.readValue(usersJson, new TypeReference<List<User>>() {});
	         // Almacenar la lista de usuarios en sesión
	         session.setAttribute("users", users);
	         System.out.println("Usuarios guardados en sesión: " + users);
	         return ResponseEntity.ok("La carga de datos fue realizada correctamente");
	     } catch (IOException exception) {
	         String error = "Error al leer el archivo";
	         throw new VideoclubException(500, error);
	     }
	 }
	@RequestMapping(method = RequestMethod.GET, value = "/users/download", produces = {"application/json"})
	public ResponseEntity<?> downloadUsers(HttpSession session) {
	    // Obtener la lista de usuarios de la sesión
	    List<User> users = (List<User>) session.getAttribute("users");
	    if (users != null) {
	        try {
	            // Convertir la lista de usuarios a JSON utilizando ObjectMapper
	            ObjectMapper objectMapper = new ObjectMapper();
	            String usersJson = objectMapper.writeValueAsString(users);
	            // Crear un archivo temporal
	            File tempFile = File.createTempFile("users", ".json");
	            // Escribir los datos de usuarios en el archivo temporal
	            FileWriter writer = new FileWriter(tempFile);
	            writer.write(usersJson);
	            writer.close();
	            // Obtener el contenido del archivo temporal en un array de bytes
	            byte[] contenidoDelFichero = Files.readAllBytes(tempFile.toPath());
	            // Convertir el array de bytes en un formato que entiende Spring Boot
	            InputStreamResource outcomeInputStreamResource = new InputStreamResource(new ByteArrayInputStream(contenidoDelFichero));
	            // Para enviar cabeceras en la respuesta, haremos uso de la clase HttpHeaders
	            HttpHeaders headers = new HttpHeaders();
	            headers.set("Content-Disposition", "attachment; filename=" + tempFile.getName());
	            // Generar la respuesta
	            return ResponseEntity.ok().headers(headers).body(outcomeInputStreamResource);
	        } catch (IOException ex) {
	            // Manejar cualquier excepción que ocurra durante el proceso
	            return ResponseEntity.status(500).body("Error al generar el archivo JSON de usuarios");
	        }
	    } else {
	        return ResponseEntity.status(404).body("No se encontraron datos de usuarios en la sesión");
	    }
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/booking/get/movies", produces = {"application/json"})
	public ResponseEntity<List<Movie>> getMovies(HttpSession session) {
		
	    List<Movie> movies = (List<Movie>) session.getAttribute("movies");
	    if (movies != null) {
	        return ResponseEntity.ok().body(movies);
	    } else {
	        return ResponseEntity.status(404).body(null);
	    }
	}
	@RequestMapping(method = RequestMethod.DELETE, value = "/booking/cancel/movie", produces = {"application/json"})
	public ResponseEntity<?> cancelBookingMovie(@RequestParam("movieId") int movieId, HttpSession session) {
	    // Obtener la lista de películas de la sesión
	    List<Movie> movies = (List<Movie>) session.getAttribute("movies");
	    if (movies != null) {
	        // Buscar la película por su ID
	        Optional<Movie> movieOptional = movies.stream().filter(movie -> movie.getMovieId() == movieId).findFirst();
	        if (movieOptional.isPresent()) {
	            // Eliminar la película de la lista
	            Movie movie = movieOptional.get();
	            movies.remove(movie);
	            return ResponseEntity.ok().body("Cancelación de reserva realizada");
	        } else {
	            // La película no fue encontrada
	            MovieError movieError = new MovieError(1, "Movie was not found");
	            return ResponseEntity.status(400).body(movieError);
	        }
	    } else {
	        // No se encontraron datos de películas en la sesión
	        MovieError movieError = new MovieError(1, "User was not found");
	        return ResponseEntity.status(404).body(movieError);
	    }
	}
}
