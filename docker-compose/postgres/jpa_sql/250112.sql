   
    create table api_log (
       log_id  bigserial not null,
        created_date timestamp,
        modified_date timestamp,
        http_method varchar(10),
        ip_addr varchar(100),
        request_url varchar(500),
        site_id int8,
        user_id varchar(255),
        primary key (log_id)
    )
Hibernate: 
    
    create table login_log (
       log_id  bigserial not null,
        created_date timestamp,
        modified_date timestamp,
        email_addr varchar(100),
        fail_content varchar(200),
        ip_addr varchar(100),
        site_id int8,
        success_at boolean,
        primary key (log_id)
    )
Hibernate: 
    
    create table role_authorization (
       authorization_no int4 not null,
        role_id varchar(20) not null,
        created_by varchar(255),
        created_date timestamp,
        primary key (authorization_no, role_id)
    )
Hibernate: 
    
    create table u_authorization (
       authorization_no  serial not null,
        created_date timestamp,
        modified_date timestamp,
        created_by varchar(255),
        last_modified_by varchar(255),
        authorization_name varchar(50) not null,
        http_method_code varchar(20) not null,
        sort_seq integer default 0,
        url_pattern_value varchar(200) not null,
        primary key (authorization_no)
    )
Hibernate: 
    
    create table u_role (
       role_id varchar(20) not null,
        created_date timestamp,
        role_content varchar(200),
        role_name varchar(50) not null,
        sort_seq integer default 0,
        primary key (role_id)
    )
Hibernate: 
    
    create table u_user (
       user_no  bigserial not null,
        created_date timestamp,
        modified_date timestamp,
        created_by varchar(255),
        last_modified_by varchar(255),
        email_addr varchar(100) not null,
        encrypted_password varchar(100),
        google_id varchar(100),
        kakao_id varchar(100),
        last_login_date timestamp,
        login_fail_count integer default 0 not null,
        naver_id varchar(100),
        refresh_token varchar(255),
        role_id varchar(255) not null,
        user_id varchar(255) not null,
        user_name varchar(50) not null,
        user_state_code varchar(20) default '00' not null,
        primary key (user_no)
    )
Hibernate: 
    
    create table user_find_password (
       email_addr varchar(50) not null,
        request_no int4 not null,
        created_date timestamp,
        modified_date timestamp,
        change_at boolean default false not null,
        token_value varchar(50) not null,
        primary key (email_addr, request_no)
    )
Hibernate: 
    
    alter table u_user 
       add constraint UK_my4bmq7p4kvdqwfovp6aqqj1j unique (email_addr)
Hibernate: 
    
    alter table u_user 
       add constraint UK_t0rckjm1mr9mfcf0lirehueio unique (user_id)
Hibernate: 
    
    alter table role_authorization 
       add constraint FKho6uylbxrrq6w2uac51njxpte 
       foreign key (authorization_no) 
       references u_authorization 
       on delete cascade


       ------------- board service
          drop table if exists api_log cascade
Hibernate: 
    
    drop table if exists board cascade
Hibernate: 
    
    drop table if exists code cascade
Hibernate: 
    
    drop table if exists comment cascade
Hibernate: 
    
    drop table if exists posts cascade
Hibernate: 
    
    drop table if exists posts_read cascade
Hibernate: 
    
    drop table if exists u_user cascade
Hibernate: 
    
    create table api_log (
       log_id  bigserial not null,
        created_date timestamp,
        modified_date timestamp,
        http_method varchar(10),
        ip_addr varchar(100),
        request_url varchar(500),
        site_id int8,
        user_id varchar(255),
        primary key (log_id)
    )
Hibernate: 
    
    create table board (
       board_no  serial not null,
        created_date timestamp,
        modified_date timestamp,
        created_by varchar(255),
        last_modified_by varchar(255),
        board_name varchar(100) not null,
        comment_use_at boolean default false not null,
        editor_use_at boolean default false not null,
        new_display_day_count integer default 3 not null,
        page_display_count integer default 10 not null,
        post_display_count integer default 10 not null,
        skin_type_code varchar(20) not null,
        title_display_length integer default 20 not null,
        upload_limit_count integer default 0 not null,
        upload_limit_size bigint,
        upload_use_at boolean default false not null,
        user_write_at boolean default false not null,
        primary key (board_no)
    )
Hibernate: 
    
    create table code (
       code_id varchar(255) not null,
        created_date timestamp,
        modified_date timestamp,
        created_by varchar(255),
        last_modified_by varchar(255),
        code_name varchar(255),
        parent_code_id varchar(255),
        primary key (code_id)
    )
Hibernate: 
    
    create table comment (
       comment_no integer not null,
        created_date timestamp,
        modified_date timestamp,
        created_by varchar(255),
        last_modified_by varchar(255),
        comment_content text not null,
        delete_at integer default 0 not null,
        depth_seq smallint default 0 not null,
        group_no integer default 0 not null,
        parent_comment_no int4,
        sort_seq integer default 0 not null,
        board_no int4 not null,
        posts_no int4 not null,
        primary key (comment_no, board_no, posts_no)
    )
Hibernate: 
    
    create table posts (
       board_no int4 not null,
        posts_no integer not null,
        created_date timestamp,
        modified_date timestamp,
        created_by varchar(255),
        last_modified_by varchar(255),
        attachment_code varchar(255),
        delete_at boolean default false not null,
        notice_at boolean default false not null,
        posts_answer_content text,
        posts_content text not null,
        posts_title varchar(100) not null,
        read_count integer default 0 not null,
        primary key (board_no, posts_no)
    )
Hibernate: 
    
    create table posts_read (
       board_no integer not null,
        posts_no integer not null,
        read_no integer not null,
        created_date timestamp,
        ip_addr varchar(100) not null,
        user_id varchar(255),
        primary key (board_no, posts_no, read_no)
    )
Hibernate: 
    
    create table u_user (
       user_no int8 not null,
        created_date timestamp,
        modified_date timestamp,
        created_by varchar(255),
        last_modified_by varchar(255),
        user_id varchar(255),
        user_name varchar(255),
        primary key (user_no)
    )
Hibernate: 
    
    alter table u_user 
       add constraint UK_t0rckjm1mr9mfcf0lirehueio unique (user_id)
Hibernate: 
    
    alter table comment 
       add constraint FKbksnfhpupo15wo7qm6txawhg 
       foreign key (created_by) 
       references u_user (user_id)
Hibernate: 
    
    alter table comment 
       add constraint FKjcsurpgbnfkrur901t5jdulir 
       foreign key (board_no, posts_no) 
       references posts 
       on delete cascade
Hibernate: 
    
    alter table posts 
       add constraint FKecadu0m9x5kcvgt7xemjbbs8j 
       foreign key (board_no) 
       references board 
       on delete cascade
Hibernate: 
    
    alter table posts 
       add constraint FKfod7cbm7qr2ijc5lrmlno5m1f 
       foreign key (created_by) 
       references u_user (user_id)

-- PostgreSQL compatibility fixes for reserve services

-- 1. Reserve table (reserve is a reserved word in PostgreSQL)
ALTER TABLE IF EXISTS "reserve" RENAME TO "u_reserve";

-- Update the sequence if it exists
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_sequences WHERE schemaname = 'public' AND sequencename = 'reserve_seq') THEN
        ALTER SEQUENCE reserve_seq RENAME TO r_reserve_seq;
    END IF;
END
$$;

-- Column type conversions for r_reserve
ALTER TABLE "r_reserve"
    ALTER COLUMN "reserve_id" TYPE varchar(255),
    ALTER COLUMN "reserve_item_id" TYPE int8,
    ALTER COLUMN "reserve_qty" TYPE int4,
    ALTER COLUMN "reserve_status_id" TYPE varchar(20),
    ALTER COLUMN "reserve_type_id" TYPE varchar(20),
    ALTER COLUMN "reserve_start_date" TYPE timestamp,
    ALTER COLUMN "reserve_end_date" TYPE timestamp,
    ALTER COLUMN "user_id" TYPE varchar(255),
    ALTER COLUMN "user_contact_no" TYPE varchar(50),
    ALTER COLUMN "user_email" TYPE varchar(200),
    ALTER COLUMN "created_by" TYPE varchar(255),
    ALTER COLUMN "created_date" TYPE timestamp,
    ALTER COLUMN "last_modified_by" TYPE varchar(255),
    ALTER COLUMN "last_modified_date" TYPE timestamp;

-- 2. ReserveItem table column conversions
ALTER TABLE "reserve_item"
    ALTER COLUMN "reserve_item_id" TYPE int8,
    ALTER COLUMN "reserve_item_name" TYPE varchar(200),
    ALTER COLUMN "location_id" TYPE int8,
    ALTER COLUMN "category_id" TYPE varchar(20),
    ALTER COLUMN "total_qty" TYPE int4,
    ALTER COLUMN "inventory_qty" TYPE int4,
    ALTER COLUMN "operation_start_date" TYPE timestamp,
    ALTER COLUMN "operation_end_date" TYPE timestamp,
    ALTER COLUMN "reserve_method_id" TYPE varchar(20),
    ALTER COLUMN "reserve_means_id" TYPE varchar(20),
    ALTER COLUMN "request_start_date" TYPE timestamp,
    ALTER COLUMN "request_end_date" TYPE timestamp,
    ALTER COLUMN "period_at" TYPE boolean,
    ALTER COLUMN "period_max_count" TYPE int4,
    ALTER COLUMN "external_url" TYPE varchar(500),
    ALTER COLUMN "selection_means_id" TYPE varchar(20),
    ALTER COLUMN "paid_at" TYPE boolean,
    ALTER COLUMN "usage_cost" TYPE numeric(19,2),
    ALTER COLUMN "use_at" TYPE boolean,
    ALTER COLUMN "purpose_content" TYPE varchar(4000),
    ALTER COLUMN "item_addr" TYPE varchar(500),
    ALTER COLUMN "target_id" TYPE varchar(20),
    ALTER COLUMN "excluded_content" TYPE varchar(2000),
    ALTER COLUMN "homepage_url" TYPE varchar(500),
    ALTER COLUMN "contact_no" TYPE varchar(50),
    ALTER COLUMN "manager_dept_name" TYPE varchar(200),
    ALTER COLUMN "manager_name" TYPE varchar(200),
    ALTER COLUMN "manager_contact_no" TYPE varchar(50),
    ALTER COLUMN "created_by" TYPE varchar(255),
    ALTER COLUMN "created_date" TYPE timestamp,
    ALTER COLUMN "last_modified_by" TYPE varchar(255),
    ALTER COLUMN "last_modified_date" TYPE timestamp;

-- 3. Location table column conversions
ALTER TABLE "location"
    ALTER COLUMN "location_id" TYPE int8,
    ALTER COLUMN "location_name" TYPE varchar(200),
    ALTER COLUMN "created_by" TYPE varchar(255),
    ALTER COLUMN "created_date" TYPE timestamp,
    ALTER COLUMN "last_modified_by" TYPE varchar(255),
    ALTER COLUMN "last_modified_date" TYPE timestamp;

-- Add comments for documentation
COMMENT ON TABLE "r_reserve" IS '예약 정보';
COMMENT ON TABLE "reserve_item" IS '예약 물품 정보';
COMMENT ON TABLE "location" IS '예약 장소 정보';

-- Add column comments
-- r_reserve table
COMMENT ON COLUMN "r_reserve".reserve_id IS '예약 ID';
COMMENT ON COLUMN "r_reserve".reserve_item_id IS '예약 물품 ID';
COMMENT ON COLUMN "r_reserve".reserve_qty IS '예약 수량';
COMMENT ON COLUMN "r_reserve".reserve_status_id IS '예약 상태 ID';
COMMENT ON COLUMN "r_reserve".reserve_type_id IS '예약 유형 ID';
COMMENT ON COLUMN "r_reserve".reserve_start_date IS '예약 시작일시';
COMMENT ON COLUMN "r_reserve".reserve_end_date IS '예약 종료일시';
COMMENT ON COLUMN "r_reserve".user_id IS '사용자 ID';
COMMENT ON COLUMN "r_reserve".user_contact_no IS '사용자 연락처';
COMMENT ON COLUMN "r_reserve".user_email IS '사용자 이메일';

-- reserve_item table
COMMENT ON COLUMN "reserve_item".reserve_item_id IS '예약 물품 ID';
COMMENT ON COLUMN "reserve_item".reserve_item_name IS '예약 물품명';
COMMENT ON COLUMN "reserve_item".location_id IS '위치 ID';
COMMENT ON COLUMN "reserve_item".category_id IS '카테고리 ID';
COMMENT ON COLUMN "reserve_item".total_qty IS '전체 수량';
COMMENT ON COLUMN "reserve_item".inventory_qty IS '재고 수량';
COMMENT ON COLUMN "reserve_item".period_at IS '기간 여부';
COMMENT ON COLUMN "reserve_item".paid_at IS '유료 여부';
COMMENT ON COLUMN "reserve_item".use_at IS '사용 여부';

-- location table
COMMENT ON COLUMN "location".location_id IS '위치 ID';
COMMENT ON COLUMN "location".location_name IS '위치명';