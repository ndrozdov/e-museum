package ru.mkrf.label.util.to;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.mkrf.label.entity.db.Media;
import ru.mkrf.label.util.configuration.LabelConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MultipartFileZipToMedias {
    public static Media zipEntryToMedia(ZipEntry zipEntry) {

        String originalFileName = Paths.get(zipEntry.getName()).getFileName().toString();
        String url = zipEntry.getName().replaceAll(originalFileName, "");
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + FilenameUtils.getExtension(zipEntry.getName());
        String time = "" + LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        Media media = new Media();
        media.setOriginalFileName(originalFileName);
        media.setFileName(fileName);
        media.setFileSize((int)zipEntry.getSize());
        media.setUploadDate(time);
        media.setUrl(url);
        media.setDeleted(0);

        return media;
    }

    public static List<Media> unzippingArchive(MultipartFile file, LabelConfig config) throws IOException {
        ZipInputStream zis = new ZipInputStream(file.getInputStream());

        byte[] buffer = new byte[1024];
        ZipEntry zipEntry = zis.getNextEntry();

        List<Media> medias = new ArrayList<>();
        Media media;

        while(zipEntry != null){
            if(!zipEntry.isDirectory()) {
                media = zipEntryToMedia(zipEntry);
                Path source = Paths.get(config.getPathToMedia(), media.getFileName());
                File newFile = source.toFile();
                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();

                media.setMimeType(Files.probeContentType(source));
                medias.add(media);
            }

            zipEntry = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();

        return medias;
    }
}
