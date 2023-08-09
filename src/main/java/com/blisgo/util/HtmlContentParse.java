package com.blisgo.util;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 게시판에 출력될 정보 중 일부 데이터를 재가공하여 출력해줌
 *
 * @author okjae
 */
public class HtmlContentParse {
    public static String parseThumbnail(String str) {
        Optional<String> src = Optional.ofNullable(getImgSrc(str));
        StringBuilder sb = new StringBuilder();

        if (src.isPresent()) {
            sb.append(src.get());
            // 49+1
            int appendIndex = sb.indexOf("/v") + 1;
            sb.insert(appendIndex, "c_fill,h_240,w_240/");
        }
        return sb.toString();
    }

    public static String parseContentPreview(String str) {
        Matcher mat;
        Pattern pattern;

        pattern = Pattern.compile("<p.*>.*?</p>");
        mat = pattern.matcher(str);
        if (mat.find()) {
            str = mat.group(0);
            str = str.substring(0, str.indexOf("</p>"));
        }

        String[] filterList = {"<p></p>", "<img[^>]*>", "<p.*>", "</p>", "<br>", "<p data-f-id=\"pbf\"[^>]*>.*</p>"};

        for (String filter : filterList) {
            pattern = Pattern.compile(filter, Pattern.DOTALL);
            mat = pattern.matcher(str);
            str = mat.replaceAll("");
        }

        return str.isEmpty() ? null : str;
    }


    private static String getImgSrc(String str) {
        Pattern nonValidPattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
        Matcher matcher = nonValidPattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}
