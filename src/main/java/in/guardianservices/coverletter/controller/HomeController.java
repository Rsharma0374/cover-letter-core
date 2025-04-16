package in.guardianservices.coverletter.controller;

import in.guardianservices.coverletter.request.CoverLetterRequest;
import in.guardianservices.coverletter.service.TemplateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/v1")
public class HomeController {

    @Autowired
    private TemplateService templateService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to cover letter repo.";
    }

    @GetMapping("/cover-letter/" + "{sFirstName}" + "/" + "{sLastName}" + "/" + "{sCompanyName}")
    public ResponseEntity<?> coverLetter(
            @PathVariable("sFirstName") @NotNull @Valid String firstName,
            @PathVariable("sLastName") @NotNull @Valid String lastName,
            @PathVariable("sCompanyName") @NotNull @Valid String companyName
    ) throws IOException {
        try {
            String base64 = templateService.buildCoverLetter(firstName, lastName, companyName);
            // Decode the Base64 string
            byte[] decodedBytes = Base64.getDecoder().decode(base64);

            // Create a file (optional: for storage)
            File pdfFile = new File("cover-letter.pdf");
            try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
                fos.write(decodedBytes);
            }

            // Set response headers for file download
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cover-letter.pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

            return new ResponseEntity<>(decodedBytes, headers, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/cover-letter")
    public ResponseEntity<?> coverLetter(@RequestBody CoverLetterRequest request) throws IOException {
        try {
            String base64 = templateService.buildCoverLetterWithRequest(request);
            // Decode the Base64 string
            byte[] decodedBytes = Base64.getDecoder().decode(base64);

            // Create a file (optional: for storage)
            File pdfFile = new File("cover-letter.pdf");
            try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
                fos.write(decodedBytes);
            }

            // Set response headers for file download
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cover-letter.pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

            return new ResponseEntity<>(decodedBytes, headers, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
