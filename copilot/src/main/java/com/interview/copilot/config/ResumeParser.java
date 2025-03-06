package com.interview.copilot.config;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResumeParser {

//    public String extractTextFromPdf(MultipartFile file) throws IOException {
//        try (InputStream inputStream = file.getInputStream();
//             PDDocument document = PDDocument.load(inputStream)) {
//
//            PDFTextStripper stripper = new PDFTextStripper();
//            return stripper.getText(document);
//        }
//    }

    public String extractTextFromPdf(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            // Check if the document is encrypted
            if (document.isEncrypted()) {
                throw new IOException("Encrypted PDF is not supported");
            }

            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (Exception e) {
            throw new IOException("Error extracting text from PDF", e);
        }
    }


    public List<String> extractSkills(String resumeText) {
        List<String> skills = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b(Java|Python|JavaScript|Spring Boot|AWS|React|SQL|Docker)\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(resumeText);
        while (matcher.find()) {
            skills.add(matcher.group(1));
        }
        return skills.stream().distinct().toList();
    }
}