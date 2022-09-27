package com.blisgo.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Cloudinary 클라우드 스토리지 게시판 리소스 혹은 마이페이지 프로필 업데이트에서 업로드된 리소스를 업로드하여 url로 반한
 *
 * @author okjae
 */
@SuppressWarnings("rawtypes")
public class CloudinaryUtil {
    Cloudinary cloudinary;

    // FIXME [Cloudinary 생성자] 하드코딩된 API 연결 정보
    public CloudinaryUtil() {
        cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "hos3yyb97", "api_key", "275985644681352",
                "api_secret", "AGGX9la_4o2pUT0Hr1nwVLjG_oY"));
    }

    /**
     * 최적의 프로필 이미지를 위해 이미지 변환(Webp) 후 업로드
     *
     * @param profile_img 프로필 이미지
     * @return 업로드된 이미지 URl
     */
    // TODO [Cloudinary 업로드] uploadImage 와 uploadFile 메서드 합치기. 너뮤 유사한 코드. switch문으로
    // 교체
    public String uploadImage(MultipartFile profile_img) {
        Map result = null;
        File uuidFile = null;
        //TODO 오래된 방식(try-catch)
        try {
            uuidFile = convert(profile_img);
            result = cloudinary.uploader().upload(uuidFile,
                    ObjectUtils.asMap("folder", "userprofile", "transformation", new Transformation()
                            .gravity("auto:classic").width(1000).height(1000).crop("thumb").fetchFormat("webp")));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (Objects.requireNonNull(uuidFile).exists()) {
                uuidFile.delete();
            }
        }
        return (String) Objects.requireNonNull(result).get("secure_url");
    }

    /**
     * 게시판에서 업로드한 리소스를 클라우드에 저장
     *
     * @param file 파일
     * @return 업로드된 파일 URl
     */
    public String uploadFile(MultipartFile file) {
        Map result = null;
        File uuidFile = null;
        //TODO 오래된 방식(try-catch)
        try {
            uuidFile = convert(file);
            result = cloudinary.uploader().upload(uuidFile, ObjectUtils.asMap("folder", "board"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (uuidFile.exists()) {
                uuidFile.delete();
            }
        }
        return (String) Objects.requireNonNull(result).get("secure_url");
    }

    /**
     * 파일 이름 변경하여 저장
     *
     * @param file 파일
     * @return 파일 저장
     */
    private File convert(MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        String uuidFilename = uuid + "";
        File convFile = new File(uuidFilename);
        //TODO 오래된 방식(try-catch)
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convFile;
    }
}
