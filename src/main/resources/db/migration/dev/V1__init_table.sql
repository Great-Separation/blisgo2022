-- auto-generated definition
create table IF NOT EXISTS user
(
    mem_no        int           not null comment '회원 번호(PK)'
        primary key,
    created_date  datetime      not null comment '생성시간(공통)',
    modified_date datetime      not null comment '수정시간(공통)',
    email         varchar(45)   not null comment '회원 이메일',
    mem_point     int default 0 null comment '회원 포인트',
    nickname      varchar(45)   not null comment '회원 닉네임',
    pass          varchar(200)  not null comment '회원 비밀번호',
    profile_image varchar(100)  null comment '회원 프로필 이미지'
);


-- auto-generated definition
create table IF NOT EXISTS dictionary
(
    dic_no     int          not null comment '폐기물 번호(PK)'
        primary key,
    category   varchar(45)  not null comment '폐기물 대분류',
    eng_name   varchar(45)  not null comment '폐기물 영어 이름',
    hit        smallint     not null comment '폐기물 조회 수',
    name       varchar(45)  not null comment '폐기물 이름',
    popularity int          not null comment '폐기물 인지도',
    thumbnail  varchar(200) not null comment '폐기물 대표 이미지',
    treatment  varchar(200) not null comment '폐기물 처리 안내'
);



-- auto-generated definition
create table IF NOT EXISTS guide
(
    guide_code    varchar(10)  not null comment '가이드 코드(PK)'
        primary key,
    guide_content varchar(200) not null comment '폐기물 처리 안내',
    guide_name    varchar(50)  not null comment '폐기물 중분류',
    image_path    varchar(100) not null comment '폐기물 처리 안내 이미지 url'
);

-- auto-generated definition
create table IF NOT EXISTS hashtag
(
    guide_code varchar(10) not null comment '사전 코드(FK)',
    dic_no     int         not null comment '사전 번호(FK)',
    primary key (dic_no, guide_code),
    constraint FKbw4wbx2t9b5jl4f5mh9sb3qw8
        foreign key (guide_code) references guide (guide_code)
            on delete cascade,
    constraint FKsjorlkqbvrqkx3i8ilmj7ekfv
        foreign key (dic_no) references dictionary (dic_no)
            on delete cascade
);

-- auto-generated definition
create table IF NOT EXISTS dogam
(
    mem_no int not null comment '회원 번호(PK, FK)',
    dic_no int not null comment '사전 번호(PK, FK)',
    primary key (dic_no, mem_no),
    constraint FK655b2k9csuwerfgpbsss0hqmo
        foreign key (mem_no) references user (mem_no)
            on delete cascade,
    constraint FKawe5fsn3ylg2p3790of6sltc1
        foreign key (dic_no) references dictionary (dic_no)
            on delete cascade
);

-- auto-generated definition
create table IF NOT EXISTS board
(
    bd_no          int           not null comment '글 번호(PK)'
        primary key,
    created_date   datetime      not null comment '생성시간(공통)',
    modified_date  datetime      not null comment '수정시간(공통)',
    bd_category    varchar(45)   null comment '글 분류(enum 일반, 공지)',
    bd_content     mediumtext    not null comment '글 내용',
    bd_favorite    int default 0 null comment '글 좋아요',
    bd_reply_count int default 0 null comment '댓글 수',
    bd_thumbnail   varchar(255)  null comment '글 썸네일',
    bd_title       varchar(45)   not null comment '글 제목',
    bd_views       int default 0 null comment '글 조회수',
    mem_no         int           not null comment '회원 번호(FK)',
    constraint FKh66c33oaib9cb6p8ynhjjl9i0
        foreign key (mem_no) references user (mem_no)
            on delete cascade
);


-- auto-generated definition
create table IF NOT EXISTS reply
(
    reply_no      int          not null comment '댓글 번호(PK)'
        primary key,
    created_date  datetime     not null comment '생성시간(공통)',
    modified_date datetime     not null comment '수정시간(공통)',
    content       varchar(300) not null comment '댓글 내용',
    bd_no         int          not null comment '게시글 번호(FK)',
    mem_no        int          not null comment '회원 번호(FK)',
    constraint FK75xtnbr3sv46q4706r6dn2ici
        foreign key (bd_no) references board (bd_no)
            on delete cascade,
    constraint FKcm5oaan245oh3gjgmk3f1a4ff
        foreign key (mem_no) references user (mem_no)
            on delete cascade
);




