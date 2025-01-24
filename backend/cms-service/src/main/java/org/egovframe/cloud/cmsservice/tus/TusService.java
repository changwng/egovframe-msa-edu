package org.egovframe.cloud.cmsservice.tus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import me.desair.tus.server.upload.UploadInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TusService {

    private final TusFileUploadService tusFileUploadService;

    @Value("${tus.save.path}")
    private String savePath;

    public String tusUpload(HttpServletRequest request, HttpServletResponse response) {
        try {
            tusFileUploadService.process(request, response);
            UploadInfo uploadInfo = tusFileUploadService.getUploadInfo(request.getRequestURI());
            if(uploadInfo != null) {
                log.info("uploadInfo={},{},{}", uploadInfo.getFileName(), uploadInfo.getOffset(), uploadInfo.isUploadInProgress());
            }else{
                log.info("uploadInfo is null");
            }
            if (uploadInfo != null && !uploadInfo.isUploadInProgress()) {
                log.info(uploadInfo.getFileName() +" upload finished and file getRequestURI:{}, getOffset={}", request.getRequestURI(), uploadInfo.getOffset());
                String copyPathAndFileName = createFile(tusFileUploadService.getUploadedBytes(request.getRequestURI()), uploadInfo.getFileName());
                tusFileUploadService.deleteUpload(request.getRequestURI());
                log.info("copyPathAndFileName={}", copyPathAndFileName);
                 return copyPathAndFileName;
                //return "success";
            }

            return null;
        } catch (IOException | TusException e) {
            log.error("exception was occurred. message={}", e.getMessage(), e);

            throw new RuntimeException(e);
        }
    }

    private String createFile(InputStream is, String filename) throws IOException {

        String today = LocalDate.now().toString().replace("-", "");
        String uploadedPath = savePath + "/" + today;

        String vodName = getVodName(filename);

        File file = new File(uploadedPath, vodName);

        FileUtils.copyInputStreamToFile(is, file);

        String type = URLConnection.guessContentTypeFromStream(is);
        if (    type != null) {
            if(type.startsWith("video/")) {
                ThumbnailExtractor.extract(file);
            }
        }

        return uploadedPath+"/"+vodName;

         //return file.getAbsolutePath();
    }



    private String getVodName(String filename) {
        String[] split = filename.split("\\.");
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid + "." + split[split.length - 1];
    }
}