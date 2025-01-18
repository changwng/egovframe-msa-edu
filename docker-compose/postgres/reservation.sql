\c reservation;

-- Create reserve_item table
CREATE TABLE reserve_item (
    reserve_item_id BIGSERIAL PRIMARY KEY,
    reserve_item_name VARCHAR(200) NOT NULL,
    reserve_item_content TEXT,
    location_id BIGINT,
    category_id VARCHAR(255),
    total_qty INTEGER DEFAULT 0,
    created_date TIMESTAMP,
    modified_date TIMESTAMP,
    created_by VARCHAR(255),
    last_modified_by VARCHAR(255)
);

COMMENT ON TABLE reserve_item IS '예약 물품';
COMMENT ON COLUMN reserve_item.reserve_item_id IS '예약 물품 ID';
COMMENT ON COLUMN reserve_item.reserve_item_name IS '예약 물품 명';
COMMENT ON COLUMN reserve_item.reserve_item_content IS '예약 물품 설명';
COMMENT ON COLUMN reserve_item.location_id IS '예약 물품-지역 ID';
COMMENT ON COLUMN reserve_item.category_id IS '예약 물품-유형 ID';
COMMENT ON COLUMN reserve_item.total_qty IS '전체 수량';
COMMENT ON COLUMN reserve_item.created_date IS '생성일';
COMMENT ON COLUMN reserve_item.modified_date IS '수정일';
COMMENT ON COLUMN reserve_item.created_by IS '생성자';
COMMENT ON COLUMN reserve_item.last_modified_by IS '수정자';

-- Create reserve_item_time table
CREATE TABLE reserve_item_time (
    reserve_item_id BIGINT NOT NULL,
    day_of_week INTEGER NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    created_date TIMESTAMP,
    modified_date TIMESTAMP,
    created_by VARCHAR(255),
    last_modified_by VARCHAR(255),
    PRIMARY KEY (reserve_item_id, day_of_week, start_time)
);

COMMENT ON TABLE reserve_item_time IS '예약 물품 운영시간';
COMMENT ON COLUMN reserve_item_time.reserve_item_id IS '예약 물품 ID';
COMMENT ON COLUMN reserve_item_time.day_of_week IS '요일';
COMMENT ON COLUMN reserve_item_time.start_time IS '시작 시간';
COMMENT ON COLUMN reserve_item_time.end_time IS '종료 시간';
COMMENT ON COLUMN reserve_item_time.created_date IS '생성일';
COMMENT ON COLUMN reserve_item_time.modified_date IS '수정일';
COMMENT ON COLUMN reserve_item_time.created_by IS '생성자';
COMMENT ON COLUMN reserve_item_time.last_modified_by IS '수정자';

-- Create location table
CREATE TABLE location (
    location_id BIGSERIAL PRIMARY KEY,
    location_name VARCHAR(200) NOT NULL,
    sort_seq INTEGER DEFAULT 99999,
    created_date TIMESTAMP,
    modified_date TIMESTAMP,
    created_by VARCHAR(255),
    last_modified_by VARCHAR(255)
);

COMMENT ON TABLE location IS '지역';
COMMENT ON COLUMN location.location_id IS '지역 ID';
COMMENT ON COLUMN location.location_name IS '지역 명';
COMMENT ON COLUMN location.sort_seq IS '정렬 순서';
COMMENT ON COLUMN location.created_date IS '생성일';
COMMENT ON COLUMN location.modified_date IS '수정일';
COMMENT ON COLUMN location.created_by IS '생성자';
COMMENT ON COLUMN location.last_modified_by IS '수정자';

-- Insert location data
INSERT INTO location (location_name, sort_seq, created_by, created_date, last_modified_by, modified_date) VALUES
('서울', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('부산', 2, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('대구', 3, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('인천', 4, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('광주', 5, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('대전', 6, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('울산', 7, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('세종', 8, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('경기', 9, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('강원', 10, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('충북', 11, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('충남', 12, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('전북', 13, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('전남', 14, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('경북', 15, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('경남', 16, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('제주', 17, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

SELECT setval('location_location_id_seq', (SELECT MAX(location_id) FROM location));
SELECT setval('reserve_item_reserve_item_id_seq', (SELECT MAX(reserve_item_id) FROM reserve_item));
