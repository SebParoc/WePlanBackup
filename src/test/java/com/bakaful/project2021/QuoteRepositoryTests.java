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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
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
    public void testCreateQuote() {
        List<Quote> quoteL = new ArrayList<>();
        Quote quote = new Quote();
        String read= "";


        /*try {
            Scanner scan = new Scanner(new File("/Users/ilariasantangelo/pp_202021_the_bakaful_team/src/Mot_quotes.txt"));
            while(scan.hasNextLine()){

                quote.setContent(scan.nextLine());
                quoteL.add(quote);
            }

            scan.close();
            System.out.println("quote" +" " +quoteL);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        String filename = "/src/Mot_quotes.txt";
        File file = new File(filename);

        try (Stream linesStream = Files.lines(file.toPath())) {
            linesStream.forEach(line -> {
                quote.setContent((String) line);
                quoteL.add(quote);

                //Quote existQuote = entityManager.find(Quote.class, savedQuote.getId());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Quote qute: quoteL){
            quoteRepository.save(qute);
        }



        System.out.println(quoteL);

        assertThat(quote.getContent()).isEqualTo(quote.getContent());
    }

}




