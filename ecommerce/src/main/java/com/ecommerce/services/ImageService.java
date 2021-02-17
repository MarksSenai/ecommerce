package com.ecommerce.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.services.exceptions.FileException;

@Service
public class ImageService {

    public BufferedImage getImageFromFile(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if ("png".equals(extension) || "jpg".equals(extension)) {
            try {
                BufferedImage image = ImageIO.read(file.getInputStream());
                if ("png".equals(extension)) {
                    image = pngToJpg(image);
                }
                return image;
            } catch (IOException e) {
                throw new FileException("Erro ao ler arquivo de imagem");
            }
        } else {
            throw new FileException("Apenas imagens PNG e JPG são permitidas");
        }

    }

    private BufferedImage pngToJpg(BufferedImage image) {
        BufferedImage jpgImage = new BufferedImage(image.getWidth(),
                image.getHeight(), BufferedImage.TYPE_INT_RGB);
        jpgImage.createGraphics().drawImage(image, 0,0, Color.WHITE, null);
        return jpgImage;
    }

    public InputStream getInputStream(BufferedImage image, String extension) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, extension, os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo");
        }
    }

    public BufferedImage chopImageShare(BufferedImage image) {
        int min = (image.getHeight() <= image.getWidth()) ? image.getHeight() : image.getWidth();
        return Scalr.crop(
                image,
                (image.getWidth()/2) - (min/2),
                (image.getHeight()/2) - (min/2),
                min,
                min);
    }

    public BufferedImage resize(BufferedImage sourceImg, int size) {
        return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);
    }
}
