package dbtest.dbtest.service;

import dbtest.dbtest.domain.Board;
import dbtest.dbtest.domain.Image;
import dbtest.dbtest.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ImageManager {

    private final ImageRepository imageRepository;

    @Value("${file.dir}")
    private String directory;

    // 확장자 추출 메소드, 저장하는 이미지 파일의 확장자를 구하는 기능을 수행합니다.
    public String extractExtension(String originalFileName) {
        int fileExtensionIndex = originalFileName.lastIndexOf('.');

        String fileExtension = originalFileName.substring(fileExtensionIndex + 1);

        if(validateExtension(fileExtension)) return fileExtension;
        throw new RuntimeException("지원하지않는 확장자");
    }

    // 저장할 파일 이름 구성 메소드, 저장되는 이미지 파일이 어떠한 이름으로 저장될지를 결정합니다.
    public String organizeStoredFileName(String originalFileName) {
        String uuidValue = UUID.randomUUID().toString();

        String rmExt = extractExtension(originalFileName);

        return uuidValue + "." + rmExt;
    }

    // 저장 경로 반환 메소드, 해당 이미지 파일이 저장될 경로를 반환해줍니다.
    public String getImageDirectory(String storedFileName, String imageType) {
        String extension = imageType + "/";

        return directory + extension + storedFileName;
    }

    // 이미지 저장 로직
    public Image saveImage(MultipartFile multipartFile, Board board) throws IOException {
        if(multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storedFileName = organizeStoredFileName(originalFilename);
        String imageType = extractExtension(originalFilename);

        multipartFile.transferTo(new File(getImageDirectory(storedFileName, imageType)));

        Image savedImage = Image.builder()
                .originalName(originalFilename)
                .storedName(storedFileName)
                .board(board)
                .build();

        return imageRepository.save(savedImage);
    }

    // 전체 이미지 저장
    public List<Image> saveImages(List<MultipartFile> multipartFiles, Board board) throws IOException{
        List<Image> imageList = new ArrayList<>();

        for(MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                imageList.add(saveImage(multipartFile, board));
            }
        }

        return imageList;
    }

    // 파일 확장자 확인
    public boolean validateExtension(String fileExtension) {
        String[] extension = {"jpg", "jpeg", "bmp", "gif", "png"};

        if(Arrays.stream(extension).anyMatch(value -> value.equals(fileExtension))) return true;
        return false;
    }
}