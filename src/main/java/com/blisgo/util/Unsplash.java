package com.blisgo.util;

import org.slf4j.Logger;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class Unsplash {
    final static String host = "https://api.unsplash.com/photos/random";
    final static String query = "waste,garbage,trash,recycling";
    final static String options = "&fm=webp&w=1500&q=50&blur=50";
    final static String clientId = "CTW7rq3n5wwaqHphLTlv47RsPHweBqy4QWe7_YVCvk8";
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Unsplash.class);

    public static void changeWallpaper() throws JSONException, IOException, InterruptedException {
        Optional<String> imageUrl = Optional.of(getImageUrl());

        replaceImage(imageUrl.get());
    }


    public static String getImageUrl() throws JSONException, IOException, InterruptedException {
        String link = String.format(host + "?query=" + query + "&client_id=" + clientId);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(link)).method("GET", HttpRequest.BodyPublishers.noBody()).build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonObj = new JSONObject(response.body());
        jsonObj = jsonObj.getJSONObject("urls");
        String editedImageLink = jsonObj.getString("raw").concat(options);
        log.info("이미지 경로>" + editedImageLink);
        return editedImageLink;
    }


    private static void replaceImage(String editedImageLink) throws IOException {
        URL url = new URL(editedImageLink);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        Resource resource = new ClassPathResource("static/assets/img/index_wallpaper.webp");
        Path wallpaperDir = Paths.get(resource.getURI());
        log.info("파일 위치>" + wallpaperDir);
        try (FileOutputStream fos = new FileOutputStream(wallpaperDir.toFile())) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }
}
