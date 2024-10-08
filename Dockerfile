# Sử dụng hình ảnh base từ OpenJDK
FROM openjdk:17-jdk-slim

# Cài đặt git
RUN apt-get update && apt-get install -y git

# Đặt thư mục làm việc
WORKDIR /app

# Sao chép file JAR vào trong hình ảnh
COPY target/course.jar course.jar

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "course.jar"]




