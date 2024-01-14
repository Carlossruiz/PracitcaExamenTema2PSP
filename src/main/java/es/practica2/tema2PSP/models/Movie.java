package es.practica2.tema2PSP.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
	private long movieId;
    private String title;
    private String duration;
}
