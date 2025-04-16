package in.guardianservices.coverletter.service;

import in.guardianservices.coverletter.request.CoverLetterRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.IOException;

public interface TemplateService {
    String buildCoverLetter(@NotNull @Valid String firstName, @NotNull @Valid String lastName, @NotNull @Valid String companyName) throws IOException;

    String buildCoverLetterWithRequest(CoverLetterRequest request) throws IOException;
}
