package com.example.mocksexemples;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController()
public class FirstController {
    String bodyGet;
    String bodyPost;

    @PostConstruct
    public void init() throws IOException {
        Path pathGet = Path.of("src/main/mocksexemples/answerGet.json");
        bodyGet = Files.readString(pathGet);
        System.out.println("FirstController created");

        Path pathPost = Path.of("src/main/mocksexemples/answerPost.json");
        bodyPost = Files.readString(pathPost);
        System.out.println("FirstController created");
    }

    @GetMapping(value = "/app/v1/getRequest")
    public ResponseEntity getStatus(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "name") String name
    ) throws InterruptedException {
        if (id <= 10) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: id <= 10");
        }
        if (name.length() <= 5) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: name length <= 5");
        }

        if (id < 50) {
            Thread.sleep(1000);
            System.out.println("1000 ms");
        }
        if (id >= 50) {
            Thread.sleep(500);
            System.out.println("500 ms");
        }
        String bd = String.format(bodyGet, id, name);

        return ResponseEntity.ok(bd);
    }


    @PostMapping(value = "/app/v1/postRequest")
    public ResponseEntity updateStatus(@RequestBody Person b) {
        System.out.println("body = " + b);
        String bd = String.format(
                bodyPost,
                b.getName(),
                b.getSurname(),
                b.getAge(),
                b.getSurname(),
                b.getName(),
                b.getAge() * 2);

        return ResponseEntity.ok(bd);
    }
}

