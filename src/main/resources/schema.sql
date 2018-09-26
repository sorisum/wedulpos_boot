-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS wedulpos DEFAULT CHARACTER SET UTF8;

-- User 테이블 생성
CREATE TABLE IF NOT EXISTS `wedulpos`.`user` (
  `idx` INT NOT NULL AUTO_INCREMENT COMMENT '고유 인덱스',
  `nickname` VARCHAR(255) NOT NULL COMMENT '별명',
  `sns_id` TEXT NULL COMMENT 'SNS ID',
  `email` VARCHAR(255) NOT NULL COMMENT '이메일',
  `password` TEXT NULL COMMENT '비밀번호',
  `isadmin` BOOLEAN NOT NULL DEFAULT 0 COMMENT '최고관리자 여부',
  `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
  `update_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '마지막 수정 시간',
  PRIMARY KEY (`idx`),
  UNIQUE INDEX `user_uq1` (`email`))
ENGINE = InnoDB
charset=utf8
COMMENT = '사용자';

-- 관리자 설정이 들어가는 테이블 생성
CREATE TABLE IF NOT EXISTS `wedulpos`.`variables` (
	`name` VARCHAR(255) NOT NULL COMMENT '설정 이름' PRIMARY KEY,
	`value` TEXT NOT NULL COMMENT '설정 값'
) charset=utf8;