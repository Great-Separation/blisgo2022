package com.blisgo.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "blisgo", "api_key", "428898964121829",
                "api_secret", "pRBsjO-mi6-OFLEp4eTUxKplTyQ"));
    }

    /**
     * 게시판에서 업로드한 리소스를 클라우드에 저장
     *
     * @param mfile 파일
     * @return 업로드된 파일 URl
     */
    public String uploadFile(MultipartFile mfile, String forWhat) {
        Map map = null;
        UUID uuid = UUID.randomUUID();
        String fileName = mfile.getOriginalFilename();
        String uuidFilename = uuid + "-" + fileName;
        Path savePath = Paths.get(uuidFilename);
        File uploadFile = new File(savePath.toUri());
        String url;
        String opt = "";

        try {
            mfile.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        switch (forWhat) {
            case "account" -> map = ObjectUtils.asMap("folder", "userprofile", "transformation", new Transformation()
                    .gravity("auto:classic").width(1000).height(1000).crop("thumb").fetchFormat("webp"));
            case "community" -> map = ObjectUtils.asMap("folder", "board");
        }
        try {
            map = cloudinary.uploader().upload(uploadFile, map);
            Files.deleteIfExists(savePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        switch (forWhat) {
            case "account" -> {}
            case "community" -> opt = "f_jpg,fl_progressive/";
        }
        url = addOpt((String) Objects.requireNonNull(map).get("secure_url"), opt);
        return url;
    }

    private String addOpt(String str, String opt) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        int appendIndex = sb.indexOf("/v") + 1;
        sb.insert(appendIndex, opt);
        return sb.toString();
    }
}