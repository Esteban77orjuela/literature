package com.alura.literatura.service;

import com.alura.literatura.model.Author;
import com.alura.literatura.model.Book;
import com.alura.literatura.repository.AuthorRepository;
import com.alura.literatura.repository.BookRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GutendexService {
    private final String API_URL = "https://gutendex.com/books";
    private final RestTemplate restTemplate;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public GutendexService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.restTemplate = new RestTemplate();
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.objectMapper = new ObjectMapper();
    }

    public void searchAndSaveBooksByTitle(String title) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                    .queryParam("search", title)
                    .build()
                    .encode()
                    .toUriString();

            System.out.println("Buscando en URL: " + url);

            String response = restTemplate.getForObject(url, String.class);

            if (response == null) {
                System.out.println("No se recibió respuesta de la API");
                return;
            }

            JsonNode root = objectMapper.readTree(response);
            JsonNode results = root.get("results");

            if (results == null || results.isEmpty()) {
                System.out.println("No se encontraron resultados para: " + title);
                return;
            }

            System.out.println("Número de libros encontrados: " + results.size());

            for (JsonNode bookNode : results) {
                Book book = new Book();
                book.setTitle(bookNode.get("title").asText());

                List<String> languages = new ArrayList<>();
                bookNode.get("languages").forEach(lang -> languages.add(lang.asText()));
                book.setLanguages(String.join(",", languages));

                List<Author> authors = new ArrayList<>();
                JsonNode authorsNode = bookNode.get("authors");
                if (authorsNode != null && !authorsNode.isEmpty()) {
                    authorsNode.forEach(authorNode -> {
                        Author author = new Author();
                        author.setName(authorNode.get("name").asText());

                        JsonNode birthYearNode = authorNode.get("birth_year");
                        if (birthYearNode != null && !birthYearNode.isNull()) {
                            author.setBirthYear(birthYearNode.asInt());
                        }

                        JsonNode deathYearNode = authorNode.get("death_year");
                        if (deathYearNode != null && !deathYearNode.isNull()) {
                            author.setDeathYear(deathYearNode.asInt());
                        }

                        authors.add(author);
                    });
                }
                book.setAuthors(authors);

                Book savedBook = bookRepository.save(book);
                // Imprimir solo el título y los autores del libro guardado
                String authorNames = authors.stream()
                        .map(Author::getName)
                        .collect(Collectors.joining(", "));
                System.out.println("----- LIBRO -----");
                System.out.println("Título: " + savedBook.getTitle());
                System.out.println("Autores: " + (authorNames.isEmpty() ? "Sin autores" : authorNames));
                System.out.println("Idiomas: " + book.getLanguages());
                System.out.println("-----------------");
            }
            System.out.println("Búsqueda completada y guardada en la base de datos.");
        } catch (Exception e) {
            System.err.println("Error al procesar la búsqueda: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Author> getAuthorsAliveInYear(Integer year) {
        return authorRepository.findAuthorsAliveInYear(year);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByLanguage(String language) {
        return bookRepository.findByLanguagesContainingIgnoreCase(language);
    }
}