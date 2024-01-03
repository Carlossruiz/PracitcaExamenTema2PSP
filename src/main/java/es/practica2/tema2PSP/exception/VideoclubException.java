package es.practica2.tema2PSP.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class VideoclubException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1313433586115667151L;
	
	/** Atributo - Código */
	private int code ;
	
	/** Atributo - Mensaje */
	private String message ;
	

}
