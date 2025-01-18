-- reserve Table Create SQL
CREATE TABLE IF NOT EXISTS u_reserve (
    reserve_id VARCHAR(255) NOT NULL,
    reserve_item_id BIGINT,
    location_id BIGINT,
    category_id VARCHAR(255),
    reserve_qty BIGINT,
    reserve_purpose_content VARCHAR(4000),
    attachment_code VARCHAR(255),
    reserve_start_date TIMESTAMP,
    reserve_end_date TIMESTAMP,
    reserve_status_id VARCHAR(20),
    reason_cancel_content VARCHAR(4000),
    user_id VARCHAR(255),
    user_contact_no VARCHAR(50),
    user_email_addr VARCHAR(500),
    create_date TIMESTAMP,
    created_by VARCHAR(255),
    modified_date TIMESTAMP,
    last_modified_by VARCHAR(255),
    PRIMARY KEY (reserve_id)
);

-- Add comments
COMMENT ON TABLE u_reserve IS '예약 신청&확인';
COMMENT ON COLUMN u_reserve.reserve_id IS '예약 id';
COMMENT ON COLUMN u_reserve.reserve_item_id IS '예약 물품 id';
COMMENT ON COLUMN u_reserve.location_id IS '예약 물품-지역 id';
COMMENT ON COLUMN u_reserve.category_id IS '예약 물품-유형 id';
COMMENT ON COLUMN u_reserve.reserve_qty IS '예약 신청인원/수량';
COMMENT ON COLUMN u_reserve.reserve_purpose_content IS '예약신청 목적';
COMMENT ON COLUMN u_reserve.attachment_code IS '첨부파일 코드';
COMMENT ON COLUMN u_reserve.reserve_start_date IS '예약 신청 시작일';
COMMENT ON COLUMN u_reserve.reserve_end_date IS '예약 신청 종료일';
COMMENT ON COLUMN u_reserve.reserve_status_id IS '예약상태 - 공통코드(reserve-status)';
COMMENT ON COLUMN u_reserve.reason_cancel_content IS '예약 취소 사유';
COMMENT ON COLUMN u_reserve.user_id IS '예약자 id';
COMMENT ON COLUMN u_reserve.user_contact_no IS '예약자 연락처';
COMMENT ON COLUMN u_reserve.user_email_addr IS '예약자 이메일';
COMMENT ON COLUMN u_reserve.create_date IS '생성일';
COMMENT ON COLUMN u_reserve.created_by IS '생성자';
COMMENT ON COLUMN u_reserve.modified_date IS '수정일';
COMMENT ON COLUMN u_reserve.last_modified_by IS '수정자';
