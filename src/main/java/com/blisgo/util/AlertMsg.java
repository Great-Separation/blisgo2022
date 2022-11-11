package com.blisgo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 뷰단 sweetalert2 modal 메시지
 *
 * @author okjae
 */
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class AlertMsg {

    public enum Icons {
        success, error, warning, info, question
    }

    private String icon;
    private String title;
    private String text;
}
