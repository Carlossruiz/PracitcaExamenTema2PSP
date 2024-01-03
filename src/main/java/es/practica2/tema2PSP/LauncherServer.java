package es.practica2.tema2PSP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication

@ComponentScan(basePackages = "es.practica2.tema2PSP")
public class LauncherServer {
	public static void main(String[] args)
    {
        SpringApplication.run(LauncherServer.class, args) ;
    }
}
