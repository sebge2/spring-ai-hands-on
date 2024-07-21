package be.sgerard.springai.model.document;

import org.springframework.core.io.AbstractResource;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class MultipartFileResource extends AbstractResource {

    private final MultipartFile multipartFile;

    public MultipartFileResource(MultipartFile multipartFile) {
        Assert.notNull(multipartFile, "MultipartFile must not be null");
        this.multipartFile = multipartFile;
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public long contentLength() {
        return this.multipartFile.getSize();
    }

    @Override
    public String getFilename() {
        return this.multipartFile.getOriginalFilename();
    }

    @Override
    public InputStream getInputStream() throws IOException, IllegalStateException {
        return this.multipartFile.getInputStream();
    }

    @Override
    public String getDescription() {
        return "MultipartFile resource [" + this.multipartFile.getName() + "]";
    }
}
