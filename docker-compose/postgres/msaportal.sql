\c msaportal;

-- Create code table
CREATE TABLE code (
    code_id VARCHAR(20) NOT NULL PRIMARY KEY,
    code_name VARCHAR(200),
    parent_code_id VARCHAR(20),
    use_at BOOLEAN DEFAULT true,
    sort_seq INTEGER DEFAULT 99999,
    created_date TIMESTAMP,
    modified_date TIMESTAMP,
    created_by VARCHAR(255),
    last_modified_by VARCHAR(255)
);

COMMENT ON TABLE code IS '공통코드';
COMMENT ON COLUMN code.code_id IS '코드 ID';
COMMENT ON COLUMN code.code_name IS '코드 명';
COMMENT ON COLUMN code.parent_code_id IS '부모 코드 ID';
COMMENT ON COLUMN code.use_at IS '사용 여부';
COMMENT ON COLUMN code.sort_seq IS '정렬 순서';
COMMENT ON COLUMN code.created_date IS '생성일';
COMMENT ON COLUMN code.modified_date IS '수정일';
COMMENT ON COLUMN code.created_by IS '생성자';
COMMENT ON COLUMN code.last_modified_by IS '수정자';

-- Insert code data
INSERT INTO code (code_id, code_name, parent_code_id, use_at, sort_seq, created_by, created_date, last_modified_by, modified_date) VALUES
('board-type', '게시판 유형', NULL, true, 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('notice', '공지사항', 'board-type', true, 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('inquiry', '문의게시판', 'board-type', true, 2, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('community', '커뮤니티', 'board-type', true, 3, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('menu-type', '메뉴 유형', NULL, true, 2, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('url', 'URL', 'menu-type', true, 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('board', '게시판', 'menu-type', true, 2, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('content', '컨텐츠', 'menu-type', true, 3, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('reserve-status', '예약 상태', NULL, true, 3, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('request', '신청', 'reserve-status', true, 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('approve', '승인', 'reserve-status', true, 2, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('reject', '반려', 'reserve-status', true, 3, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('cancel', '취소', 'reserve-status', true, 4, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('reserve-type', '예약 유형', NULL, true, 4, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('education', '교육', 'reserve-type', true, 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('lecture', '강연', 'reserve-type', true, 2, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('equipment', '장비', 'reserve-type', true, 3, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('space', '공간', 'reserve-type', true, 4, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- Create role table
CREATE TABLE role (
    role_id VARCHAR(20) NOT NULL PRIMARY KEY,
    role_name VARCHAR(200),
    role_content TEXT,
    sort_seq INTEGER DEFAULT 99999,
    created_date TIMESTAMP,
    modified_date TIMESTAMP,
    created_by VARCHAR(255),
    last_modified_by VARCHAR(255)
);

COMMENT ON TABLE role IS '권한';
COMMENT ON COLUMN role.role_id IS '권한 ID';
COMMENT ON COLUMN role.role_name IS '권한 명';
COMMENT ON COLUMN role.role_content IS '권한 설명';
COMMENT ON COLUMN role.sort_seq IS '정렬 순서';
COMMENT ON COLUMN role.created_date IS '생성일';
COMMENT ON COLUMN role.modified_date IS '수정일';
COMMENT ON COLUMN role.created_by IS '생성자';
COMMENT ON COLUMN role.last_modified_by IS '수정자';

-- Insert role data
INSERT INTO role (role_id, role_name, role_content, sort_seq, created_by, created_date, last_modified_by, modified_date) VALUES
('ROLE_ADMIN', '관리자', '관리자', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('ROLE_USER', '사용자', '사용자', 2, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('ROLE_ANONYMOUS', '손님', '손님', 3, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- Create role_authorization table
CREATE TABLE role_authorization (
    role_id VARCHAR(20) NOT NULL,
    authorization_no INTEGER NOT NULL,
    created_date TIMESTAMP,
    modified_date TIMESTAMP,
    created_by VARCHAR(255),
    last_modified_by VARCHAR(255),
    PRIMARY KEY (role_id, authorization_no)
);

COMMENT ON TABLE role_authorization IS '권한별 인가';
COMMENT ON COLUMN role_authorization.role_id IS '권한 ID';
COMMENT ON COLUMN role_authorization.authorization_no IS '인가 번호';
COMMENT ON COLUMN role_authorization.created_date IS '생성일';
COMMENT ON COLUMN role_authorization.modified_date IS '수정일';
COMMENT ON COLUMN role_authorization.created_by IS '생성자';
COMMENT ON COLUMN role_authorization.last_modified_by IS '수정자';

-- Insert role_authorization data
INSERT INTO role_authorization (role_id, authorization_no, created_by, created_date, last_modified_by, modified_date) VALUES
('ROLE_ADMIN', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('ROLE_ADMIN', 2, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('ROLE_ADMIN', 3, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('ROLE_ADMIN', 4, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('ROLE_ADMIN', 5, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('ROLE_ADMIN', 6, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('ROLE_ADMIN', 7, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('ROLE_ADMIN', 8, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('ROLE_ADMIN', 9, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('ROLE_ADMIN', 10, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('ROLE_ADMIN', 11, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('ROLE_ADMIN', 12, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('ROLE_ADMIN', 13, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP),
('ROLE_ADMIN', 14, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
