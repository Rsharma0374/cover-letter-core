package in.guardianservices.coverletter.utility;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

public class ConversionUtility {

//    public static String convertImageToBase64Code(String imagePath) throws Exception{
//
//        String base64Code = null;
//        if (StringUtils.isNotBlank(imagePath)) {
//            byte[] tickMarkByte = Files.readAllBytes(new ClassPathResource(imagePath).getFile().toPath());
//            base64Code = new String(Base64.getEncoder().encode(tickMarkByte), StandardCharsets.UTF_8);
//        }
//        return base64Code;
//    }

    public static String convertImageToBase64Code(String imagePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(imagePath);
        try (InputStream inputStream = resource.getInputStream()) {
            byte[] bytes = inputStream.readAllBytes();
            return Base64.getEncoder().encodeToString(bytes);
        }
    }

}
