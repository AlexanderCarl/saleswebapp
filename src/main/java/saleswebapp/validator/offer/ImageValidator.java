package saleswebapp.validator.offer;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import saleswebapp.repository.impl.Offer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Carl on 11.08.2017.
 */
public class ImageValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Offer.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Offer offer = (Offer) target;

        List<MultipartFile> multipartFiles = new ArrayList<MultipartFile>();
        try {
            multipartFiles.add(offer.getFirstOfferImage());
        } catch (Exception e) {
            // the firstOfferImage is null
        }

        try {
            multipartFiles.add(offer.getSecondOfferImage());
        } catch (Exception e) {
            // the secondOfferImage is null
        }

        try {
            multipartFiles.add(offer.getThirdOfferImage());
        } catch (Exception e) {
            // the thirdOfferImage is null
        }

        for(MultipartFile multipartFile : multipartFiles) {
            double fileSizeInMegabytes = multipartFile.getSize() / (1024 * 1024);
            String fileType = multipartFile.getContentType();

            if(fileSizeInMegabytes > 5) {
                errors.rejectValue("firstOfferImage", "offer.validation.imageSize");
            }

            if(!(fileType.equals("image/jpeg") || fileType.equals("application/octet-stream"))) {
                errors.rejectValue("firstOfferImage", "offer.validation.imageType");
            }
        }
    }
}
