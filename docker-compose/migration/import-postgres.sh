#!/bin/bash

# PostgreSQL로 스키마 임포트
docker exec -i postgres psql -U msaportal -d msaportal < schema.sql

# PostgreSQL로 데이터 임포트
docker exec -i postgres psql -U msaportal -d msaportal < data.sql
