package com.alura.literatura;

import com.alura.literatura.model.Author;
import com.alura.literatura.model.Book;
import com.alura.literatura.service.GutendexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

    @Autowired
    private GutendexService gutendexService;

    public static void main(String[] args) {
        SpringApplication.run(LiteraturaApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        int option = -1; // Inicializar con un valor no válido

        do {
            System.out.println("\nElija la opción a través de su número:");
            System.out.println("1- buscar libro por título");
            System.out.println("2- listar libros registrados");
            System.out.println("3- listar autores registrados");
            System.out.println("4- listar autores vivos en un determinado año");
            System.out.println("5- listar libros por idioma");
            System.out.println("0- salir");

            try {
                option = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (option) {
                    case 1:
                        System.out.println("Ingrese el título del libro:");
                        String title = scanner.nextLine();
                        gutendexService.searchAndSaveBooksByTitle(title);
                        System.out.println("Búsqueda completada y guardada en la base de datos.");
                        break;
                    case 2:
                        List<Book> books = gutendexService.getAllBooks();
                        books.forEach(book -> {
                            System.out.println("\nTítulo: " + book.getTitle());
                            System.out.println("Idiomas: " + book.getLanguages());
                            String authorNames = book.getAuthors().stream()
                                    .map(Author::getName)
                                    .collect(Collectors.joining(", "));
                            System.out.println("Autores: " + (authorNames.isEmpty() ? "Sin autores" : authorNames));
                        });
                        break;
                    case 3:
                        List<Author> authors = gutendexService.getAllAuthors();
                        authors.forEach(author -> {
                            System.out.println("\nNombre: " + author.getName());
                            System.out.println("Año de nacimiento: " +
                                    (author.getBirthYear() != null ? author.getBirthYear() : "Desconocido"));
                            System.out.println("Año de muerte: " +
                                    (author.getDeathYear() != null ? author.getDeathYear() : "Desconocido"));
                        });
                        break;
                    case 4:
                        System.out.println("Ingrese el año:");
                        int year = scanner.nextInt();
                        List<Author> aliveAuthors = gutendexService.getAuthorsAliveInYear(year);
                        aliveAuthors.forEach(author -> {
                            System.out.println("\nNombre: " + author.getName());
                            System.out.println("Año de nacimiento: " + author.getBirthYear());
                            System.out.println("Año de muerte: " +
                                    (author.getDeathYear() != null ? author.getDeathYear() : "Aún vivo"));
                        });
                        break;
                    case 5:
                        System.out.println("Ingrese el idioma (ejemplo: en, es, fr):");
                        String language = scanner.nextLine();
                        List<Book> booksByLanguage = gutendexService.getBooksByLanguage(language);
                        booksByLanguage.forEach(book -> {
                            System.out.println("\nTítulo: " + book.getTitle());
                            System.out.println("Idiomas: " + book.getLanguages());
                        });
                        break;
                    case 0:
                        System.out.println("¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine(); // Limpiar el buffer del scanner
            }
        } while (option != 0);

        scanner.close(); // Cerrar el escáner aquí
    }
}