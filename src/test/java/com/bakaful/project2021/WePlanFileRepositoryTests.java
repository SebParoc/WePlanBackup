package com.bakaful.project2021;

import static org.assertj.core.api.Assertions.assertThat;
import com.bakaful.project2021.domains.WePlanFile;
import com.bakaful.project2021.repositories.WePlanFileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WePlanFileRepositoryTests {

    @Autowired
            private WePlanFileRepository wePlanFileRepository;

    @Autowired
            private TestEntityManager entityManager;

    StringBuilder builder = new StringBuilder()
            .append(System.getProperty("user.dir"))
            .append(File.separator).append("src")
            .append(File.separator).append("main")
            .append(File.separator).append("resources")
            .append(File.separator).append("static")
            .append(File.separator);

    @Test
    @Rollback(value = false)
    void testUploadFile() throws IOException {

        String fileDirectory = builder + "profile_pictures" +
                File.separator +
                "Default.png";
        File file = new File(fileDirectory);
        WePlanFile wePlanFile = new WePlanFile();
        wePlanFile.setName(file.getName());

        byte[] bytes = Files.readAllBytes(file.toPath());
        wePlanFile.setContent(bytes);
        long fileSize = bytes.length;
        wePlanFile.setSize(fileSize);
        wePlanFile.setUploadTime(new Date());

        WePlanFile savedFile = wePlanFileRepository.save(wePlanFile);
        WePlanFile existFile = entityManager.find(WePlanFile.class, savedFile.getId());

        assertThat(existFile.getSize()).isEqualTo(fileSize);
    }
}
