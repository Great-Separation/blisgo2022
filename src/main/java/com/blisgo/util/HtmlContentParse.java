package com.blisgo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 게시판에 출력될 정보 중 일부 데이터를 재가공하여 출력해줌
 *
 * @author okjae
 */
public class HtmlContentParse {
    public static String parseThumbnail(String str) {
        List<String> list;
        list = getImgSrc(str);

        StringBuilder sb = new StringBuilder();
        //TODO 오래된 방식(try-catch)
        try {
            sb.append(list.get(0));
            // 49+1
            int appendIndex = sb.indexOf("/v") + 1;

            sb.insert(appendIndex, "c_fill,f_webp,h_400,w_400/");
        } catch (Exception e) {
            return null;
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

        String[] filterList = {"<p></p>","<img[^>]*>", "<p.*>", "</p>", "<br>", "<p data-f-id=\"pbf\"[^>]*>.*</p>"};

        for (String filter : filterList) {
            pattern = Pattern.compile(filter, Pattern.DOTALL);
            mat = pattern.matcher(str);
            str = mat.replaceAll("");
        }

        return str.equals("") ? null: str;
    }



    private static List<String> getImgSrc(String str) {
        Pattern nonValidPattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
        List<String> result = new ArrayList<>();
        Matcher matcher = nonValidPattern.matcher(str);
        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        return result;
    }
}
