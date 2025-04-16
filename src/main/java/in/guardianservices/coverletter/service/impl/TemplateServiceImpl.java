package in.guardianservices.coverletter.service.impl;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.font.FontProvider;
import in.guardianservices.coverletter.request.CoverLetterRequest;
import in.guardianservices.coverletter.service.TemplateService;
import in.guardianservices.coverletter.utility.ConversionUtility;
import in.guardianservices.coverletter.utility.TemplateFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Base64;


@Service
public class TemplateServiceImpl implements TemplateService {
    public static final String UTF_8 = "UTF-8";
    private static Logger log = LoggerFactory.getLogger(TemplateServiceImpl.class);


    @Override
    public String buildCoverLetter(String firstName, String lastName, String companyName) throws IOException {
        String base64String = null;
        byte[] bytes = null;

        CoverLetterRequest request = new CoverLetterRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setCompanyName(companyName);
        String coverLetterHtml = getCoverLetterHtml(request);
        bytes = generatePdf(coverLetterHtml);
        if (null != bytes) {
          base64String = new String(Base64.getEncoder().encode(bytes));
        }
        return base64String;
    }


    private String getCoverLetterHtml(CoverLetterRequest request) throws IOException {
        Template template = new Template();
        StringWriter stringWriter = null;
        try {
            TemplateFactory templateFactory = new TemplateFactory();
            template = templateFactory.fetchTemplate("templates/coverLetterTemplate.vm");
            VelocityContext context = mapCoverLetter(request);
            stringWriter = new StringWriter();
            template.merge(context, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            log.error("Exception occurred while get cover letter html with probable cause- ", e);
            return null;
        }finally {
            if (null != stringWriter) {
                stringWriter.flush();
                stringWriter.close();
            }
        }
    }

    private VelocityContext mapCoverLetter(CoverLetterRequest request) throws Exception {
        VelocityContext context = new VelocityContext();
        if (StringUtils.isNotBlank(request.getFirstName())) {
            context.put("firstName", request.getFirstName());
        }
        if (StringUtils.isNotBlank(request.getLastName())) {
            context.put("lastName", request.getLastName());
        }
        if (StringUtils.isNotBlank(request.getCompanyName())) {
            context.put("companyName", request.getCompanyName());
        }
        if (StringUtils.isNotBlank(request.getHireManagerName())) {
            context.put("hireManager", request.getHireManagerName());
        }
        context.put("emailIcon", ConversionUtility.convertImageToBase64Code("images/email.png"));
        context.put("linkedinIcon", ConversionUtility.convertImageToBase64Code("images/linkedin.png"));
        context.put("linkShortenerIcon", ConversionUtility.convertImageToBase64Code("images/linkShortener.png"));
        context.put("locationIcon", ConversionUtility.convertImageToBase64Code("images/location.png"));
        context.put("passwordManagerIcon", ConversionUtility.convertImageToBase64Code("images/passwordManager.png"));
        context.put("phoneIcon", ConversionUtility.convertImageToBase64Code("images/phone.png"));
        context.put("portfolioIcon", ConversionUtility.convertImageToBase64Code("images/portfolio.png"));
        return context;
    }


    private byte[] generatePdf(String coverLetterHtml) {
        if (StringUtils.isBlank(coverLetterHtml)) {
            return null;
        }
        // Prepare a ByteArrayOutputStream to hold the PDF
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        // Create Document
        try (Document document = new Document(pdfDocument)) {


            // Set page margins (top, right, bottom, left)
            document.setMargins(10, 20, 10, 20);

            // Create a FontProvider and add the required fonts
            FontProvider fontProvider = new FontProvider();
            fontProvider.addSystemFonts();
            // Set up converter properties
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setCharset(UTF_8); // Ensure UTF-8 encoding
            converterProperties.setFontProvider(fontProvider);

            HtmlConverter.convertToPdf(coverLetterHtml, pdfDocument, converterProperties);
            return byteArrayOutputStream.toByteArray();

        } catch (Exception e) {
            log.error("Exception occurred while generating pdf with probable cause- ", e);
            return null;
        } finally {

            if (null != byteArrayOutputStream) {
                try {
                    byteArrayOutputStream.close();
                } catch (Exception e) {
                    log.error("Exception occurred while closing baos with probable cause ", e);
                }
            }
            if (null != pdfDocument) {
                pdfDocument.close();
            }
            if (null != writer) {
                try {
                    writer.close();
                } catch (Exception e) {
                    log.error("Exception occurred while closing writer with probable cause ", e);
                }
            }

        }

    }


    @Override
    public String buildCoverLetterWithRequest(CoverLetterRequest request) throws IOException {
        String base64String = null;
        byte[] bytes = null;

        String coverLetterHtml = getCoverLetterHtml(request);
        bytes = generatePdf(coverLetterHtml);
        if (null != bytes) {
            base64String = new String(Base64.getEncoder().encode(bytes));
        }
        return base64String;
    }
}
