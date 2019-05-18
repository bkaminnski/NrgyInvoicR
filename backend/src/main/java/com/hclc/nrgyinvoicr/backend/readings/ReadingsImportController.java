package com.hclc.nrgyinvoicr.backend.readings;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class ReadingsImportController {

    @PostMapping(value = "/api/readings")
    @ResponseStatus(HttpStatus.OK)
    public void handleUploadedReadingFile(@RequestParam("file") MultipartFile file) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("line = " + line);
            }
        }
    }
}
