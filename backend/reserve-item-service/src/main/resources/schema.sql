-- location Table Create SQL
CREATE TABLE IF NOT EXISTS location (
    location_id BIGSERIAL PRIMARY KEY,
    location_name VARCHAR(200),
    sort_seq SMALLINT,
    use_at BOOLEAN DEFAULT true,
    created_by VARCHAR(255),
    create_date TIMESTAMP,
    last_modified_by VARCHAR(255),
    modified_date TIMESTAMP
);

COMMENT ON TABLE location IS '예약 지역';
COMMENT ON COLUMN location.location_id IS '지역 id';
COMMENT ON COLUMN location.location_name IS '지역 이름';
COMMENT ON COLUMN location.sort_seq IS '정렬 순서';
COMMENT ON COLUMN location.use_at IS '사용 여부';
COMMENT ON COLUMN location.created_by IS '생성자';
COMMENT ON COLUMN location.create_date IS '생성일';
COMMENT ON COLUMN location.last_modified_by IS '수정자';
COMMENT ON COLUMN location.modified_date IS '수정일';

-- reserve_item Table Create SQL
CREATE TABLE IF NOT EXISTS reserve_item (
    reserve_item_id BIGSERIAL PRIMARY KEY,
    reserve_item_name VARCHAR(200),
    location_id BIGINT,
    category_id VARCHAR(20),
    total_qty BIGINT,
    inventory_qty BIGINT,
    operation_start_date TIMESTAMP,
    operation_end_date TIMESTAMP,
    reserve_method_id VARCHAR(20),
    reserve_means_id VARCHAR(20),
    request_start_date TIMESTAMP,
    request_end_date TIMESTAMP,
    period_at BOOLEAN DEFAULT false,
    period_max_count SMALLINT,
    external_url VARCHAR(500),
    selection_means_id VARCHAR(20),
    paid_at BOOLEAN DEFAULT false,
    usage_cost DECIMAL(18,0),
    use_at BOOLEAN DEFAULT true,
    purpose_content VARCHAR(4000),
    item_addr VARCHAR(500),
    target_id VARCHAR(20),
    excluded_content VARCHAR(2000),
    homepage_url VARCHAR(500),
    contact_no VARCHAR(50),
    manager_dept_name VARCHAR(200),
    manager_name VARCHAR(200),
    manager_contact_no VARCHAR(50),
    create_date TIMESTAMP,
    created_by VARCHAR(255),
    modified_date TIMESTAMP,
    last_modified_by VARCHAR(255),
    CONSTRAINT FK_reserve_item_location_id FOREIGN KEY (location_id)
        REFERENCES location (location_id)
);

COMMENT ON TABLE reserve_item IS '예약 물품';
COMMENT ON COLUMN reserve_item.reserve_item_id IS '예약 물품 id';
COMMENT ON COLUMN reserve_item.reserve_item_name IS '예약 물품 이름';
COMMENT ON COLUMN reserve_item.location_id IS '지역 id';
COMMENT ON COLUMN reserve_item.category_id IS '예약유형 - 공통코드 reserve-category';
COMMENT ON COLUMN reserve_item.total_qty IS '총 재고/수용인원 수';
COMMENT ON COLUMN reserve_item.inventory_qty IS '현재 남은 재고/수용인원 수';
COMMENT ON COLUMN reserve_item.operation_start_date IS '운영 시작 일';
COMMENT ON COLUMN reserve_item.operation_end_date IS '운영 종료 일';
COMMENT ON COLUMN reserve_item.reserve_method_id IS '예약 방법 - 공통코드 reserve-method';
COMMENT ON COLUMN reserve_item.reserve_means_id IS '예약 구분 (인터넷 예약 시) - 공통코드 reserve-means';
COMMENT ON COLUMN reserve_item.request_start_date IS '예약 신청 시작 일시';
COMMENT ON COLUMN reserve_item.request_end_date IS '예약 신청 종료 일시';
COMMENT ON COLUMN reserve_item.period_at IS '기간 지정 가능 여부 - true: 지정 가능, false: 지정 불가';
COMMENT ON COLUMN reserve_item.period_max_count IS '최대 예약 가능 일 수';
COMMENT ON COLUMN reserve_item.external_url IS '외부링크';
COMMENT ON COLUMN reserve_item.selection_means_id IS '선별 방법 - 공통코드 reserve-selection-means';
COMMENT ON COLUMN reserve_item.paid_at IS '유/무료 - false: 무료, true: 유료';
COMMENT ON COLUMN reserve_item.usage_cost IS '이용 요금';
COMMENT ON COLUMN reserve_item.use_at IS '사용 여부';
COMMENT ON COLUMN reserve_item.purpose_content IS '용도';
COMMENT ON COLUMN reserve_item.item_addr IS '주소';
COMMENT ON COLUMN reserve_item.target_id IS '이용 대상 - 공통코드 reserve-target';
COMMENT ON COLUMN reserve_item.excluded_content IS '사용허가 제외대상';
COMMENT ON COLUMN reserve_item.homepage_url IS '홈페이지 url';
COMMENT ON COLUMN reserve_item.contact_no IS '문의처';
COMMENT ON COLUMN reserve_item.manager_dept_name IS '담당자 소속';
COMMENT ON COLUMN reserve_item.manager_name IS '담당자 이름';
COMMENT ON COLUMN reserve_item.manager_contact_no IS '담당자 연락처';
COMMENT ON COLUMN reserve_item.create_date IS '생성일';
COMMENT ON COLUMN reserve_item.created_by IS '생성자';
COMMENT ON COLUMN reserve_item.modified_date IS '수정일';
COMMENT ON COLUMN reserve_item.last_modified_by IS '수정자';
