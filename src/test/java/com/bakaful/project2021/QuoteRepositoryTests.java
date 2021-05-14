package com.bakaful.project2021;


import com.bakaful.project2021.domains.Quote;
import com.bakaful.project2021.domains.Task;
import com.bakaful.project2021.repositories.QuoteRepository;
import com.bakaful.project2021.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class QuoteRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QuoteRepository quoteRepository;


    @Test
    public void testCreateQuote() throws IOException {
        List<Quote> quoteL = new ArrayList<>();
        Quote quote = new Quote();
        String read = "";
        Quote savedQuote;
        Quote existQuote;


        String fileName = "/Users/ilariasantangelo/pp_202021_the_bakaful_team/src/Mot_quotes.txt";

        List<String> lines = Files.readAllLines(Paths.get(fileName),
                StandardCharsets.UTF_8);
        lines.forEach(System.out::println);
        lines.forEach(line-> quote.setContent(line)
                        );

        for(int i=0;i<=lines.size(); i--) {
            savedQuote=quoteRepository.save(quote);
            existQuote = entityManager.find(Quote.class, savedQuote.getId());
            assertThat(quote.getContent()).isEqualTo(existQuote.getContent());
        }


        }
    }











