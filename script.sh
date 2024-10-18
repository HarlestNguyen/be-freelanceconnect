#!/bin/bash
#docker exec cons-be-easyjob bash -c 'git pull && exit' && docker restart cons-be-easyjob

# Cập nhật mã nguồn từ GitHub
docker exec cons-be-easyjob bash -c "git pull"

# Sao chép file JAR mới vào container
docker cp target/easyjob.jar cons-be-easyjob:/app/easyjob.jar

# Khởi động lại container để sử dụng file JAR mới
docker restart cons-be-easyjob