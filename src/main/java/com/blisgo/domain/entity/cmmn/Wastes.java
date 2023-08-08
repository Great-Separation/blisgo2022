package com.blisgo.domain.entity.cmmn;

import lombok.Getter;

@Getter
public enum Wastes {
    Ir("고철"),
    Me("금속캔"),
    Bu("대형"),
    St("발포합성"),
    NF("불연성-종량제"),
    Vi("비닐"),
    Gl("유리병"),
    Pr("음식물"),
    Cl("의류"),
    OB("전용함"),
    Pa("종이"),
    Ca("종이팩"),
    HA("가전제품"),
    Pl("플라스틱"),
    PB("종량제봉투"),
    SM("재질별분리"),
    C("주의"),
    PF("전문시설");

    final private String name;

    Wastes(String name) {
        this.name = name;
    }
}
