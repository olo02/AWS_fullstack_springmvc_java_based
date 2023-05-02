package city.olooe.domain;

import city.olooe.controller.UploadController;
import lombok.Data;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
public class AttachFileDTO {
    private String name = "";
    private String path;
    private String uuid;
    private boolean image;
    private int odr;

    public String getUrl() throws UnsupportedEncodingException {
        return UriComponentsBuilder.fromPath("")
                .queryParam("name", URLEncoder.encode(name, "utf-8"))
                .queryParam("path", path)
                .queryParam("uuid", uuid)
                .queryParam("image", image)
                .build().toUriString();
    }

    public File getOriginFile() {
        return new File(UploadController.getPATH(), path + File.separator + uuid + "_" + name);
    }

    public File getThumbFile() {
        return new File(UploadController.getPATH(), path + File.separator + "s_" + uuid + name);
    }
}
