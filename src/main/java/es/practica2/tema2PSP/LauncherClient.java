package es.practica2.tema2PSP;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;


import es.practica2.tema2PSP.exception.VideoclubException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LauncherClient {
	private static final Logger LOGGER = LogManager.getLogger();

	public static void main(String[] args) throws VideoclubException {
		LauncherClient miLauncher = new LauncherClient();
		miLauncher. uploadUsersPetition();;
	}
	

	private void uploadMoviesPetition() throws VideoclubException {
		CloseableHttpClient httpClientRequest = HttpClients.createDefault();
		
	    HttpPost httpPost = new HttpPost("http://localhost:8080/videoclub/movies/upload");
	    
	  
        String jsonFilePath = "C:\\Users\\Carlos\\eclipse-workspace\\practicaTema2PSP\\src\\main\\resources\\movies.json";
        
        
        HttpEntity httpEntity = MultipartEntityBuilder.create().addBinaryBody("jsonFile", new File("movies.json"),ContentType.MULTIPART_FORM_DATA, "movies.json").build();
        httpPost.setEntity(httpEntity);

        CloseableHttpResponse httpServerResponse = null ;
        String responseBody = null ;
	        
	   try
	   {
		   httpServerResponse = httpClientRequest.execute(httpPost);
		   
		   StatusLine statusLine = httpServerResponse.getStatusLine();
		   if (statusLine.getStatusCode() != 200)
           {
           	// Obtenemos el cuerpo del mensaje de error de la respuesta con el error
           	responseBody = EntityUtils.toString(httpServerResponse.getEntity());
           	
           	// Generamos el mensaje de error
           	String errorString = "(Endpoint /movies/upload) - El servidor ha devuelto un código de error (" + statusLine.getStatusCode() + ") " + 
           						 "con la siguiente información: " + responseBody ;
           	
           	// Lo incluimos en los logs y enviamos excepción
           	LOGGER.error(errorString) ;
           	throw new VideoclubException(6, errorString) ;            	
           }
		// Para conseguir la respuesta del servidor, hacemos un getEntity() que convertiremos a su vez en un string que contendrá el valor de respuesta
           responseBody = EntityUtils.toString(httpServerResponse.getEntity());

           // Mostramos por pantalla en los logs
           System.out.println("Respuesta del servidor: " + responseBody);
		   
	   }catch (IOException ioException)
       {
       	// Generamos el mensaje de error
       	String errorString = "(Endpoint /movies/upload) - Excepción mientras procesaba la llamada a través del fichero" ;
       	
       	// Lo incluimos en los logs y enviamos excepción
       	LOGGER.error(errorString, ioException) ;
       	throw new VideoclubException(400, errorString) ;
       }finally
       {
       	// Cerramos los flujos abiertos de request y response
       	this.cerramosFlujos(httpClientRequest, httpServerResponse) ;
       }
	      
	}
	private void uploadUsersPetition() throws VideoclubException {
		CloseableHttpClient httpClientRequest = HttpClients.createDefault();
		
	    HttpPost httpPost = new HttpPost("http://localhost:8080/videoclub/users/upload");
	    
	  
        String jsonFilePath = "C:\\Users\\Carlos\\eclipse-workspace\\practicaTema2PSP\\src\\main\\resources\\users.json";
        
        
        HttpEntity httpEntity = MultipartEntityBuilder.create().addBinaryBody("jsonFile", new File("users.json"),ContentType.MULTIPART_FORM_DATA, "users.json").build();
        httpPost.setEntity(httpEntity);

        CloseableHttpResponse httpServerResponse = null ;
        String responseBody = null ;
	        
	   try
	   {
		   httpServerResponse = httpClientRequest.execute(httpPost);
		   
		   StatusLine statusLine = httpServerResponse.getStatusLine();
		   if (statusLine.getStatusCode() != 200)
           {
           	// Obtenemos el cuerpo del mensaje de error de la respuesta con el error
           	responseBody = EntityUtils.toString(httpServerResponse.getEntity());
           	
           	// Generamos el mensaje de error
           	String errorString = "(Endpoint /users/upload) - El servidor ha devuelto un código de error (" + statusLine.getStatusCode() + ") " + 
           						 "con la siguiente información: " + responseBody ;
           	
           	// Lo incluimos en los logs y enviamos excepción
           	LOGGER.error(errorString) ;
           	throw new VideoclubException(6, errorString) ;            	
           }
		// Para conseguir la respuesta del servidor, hacemos un getEntity() que convertiremos a su vez en un string que contendrá el valor de respuesta
           responseBody = EntityUtils.toString(httpServerResponse.getEntity());

           // Mostramos por pantalla en los logs
           System.out.println("Respuesta del servidor: " + responseBody);
		   
	   }catch (IOException ioException)
       {
       	// Generamos el mensaje de error
       	String errorString = "(Endpoint /users/upload) - Excepción mientras procesaba la llamada a través del fichero" ;
       	
       	// Lo incluimos en los logs y enviamos excepción
       	LOGGER.error(errorString, ioException) ;
       	throw new VideoclubException(400, errorString) ;
       }finally
       {
       	// Cerramos los flujos abiertos de request y response
       	this.cerramosFlujos(httpClientRequest, httpServerResponse) ;
       }
	      
	}
	private void cerramosFlujos(CloseableHttpClient httpClientRequest, CloseableHttpResponse httpServerResponse) throws VideoclubException
	{
		// Cerramos el canal response del servidor HTTP
        if (httpServerResponse != null)
        {
            try
            {
            	// Intentamos cerrar el canal
            	httpServerResponse.close();
            }
            catch (IOException ioException)
            {
            	// Generamos el mensaje de error
            	String errorString = "Excepción mientras procesaba el cierre del canal response del servidor HTTP" ;
            	
            	// Lo incluimos en los logs y enviamos excepción
            	LOGGER.error(errorString, ioException) ;
            	throw new VideoclubException(1, errorString) ;
            }
        }
    	
        // Cerramos el canal request del cliente HTTP
        if (httpClientRequest != null)
        {
            try
            {
            	// Intentamos cerrar el canal
                httpClientRequest.close();
            }
            catch (IOException ioException)
            {
            	// Generamos el mensaje de error
            	String errorString = "Excepción mientras procesaba el cierre del canal request del cliente HTTP" ;
            	
            	// Lo incluimos en los logs y enviamos excepción
            	LOGGER.error(errorString, ioException) ;
            	throw new VideoclubException(1, errorString) ;
            }
        }
	}
	
}