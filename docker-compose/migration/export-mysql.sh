#!/bin/bash

# MySQL 데이터 덤프
docker exec mysql mysqldump -u msaportal --password=msaportal \
  --compatible=postgresql --default-character-set=utf8 \
  --no-create-info --no-data --no-create-db --skip-opt \
  msaportal > schema.sql

# 데이터만 덤프
docker exec mysql mysqldump -u msaportal --password=msaportal \
  --compatible=postgresql --default-character-set=utf8 \
  --no-create-info --skip-opt \
  msaportal > data.sql

# PostgreSQL 문법으로 변환
sed -i '' 's/\\N/NULL/g' data.sql
sed -i '' 's/`//g' schema.sql
sed -i '' 's/`//g' data.sql
