# 1. Base image 선택
FROM openjdk:17-jdk-slim

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. JAR 파일 복사
COPY build/libs/chat-0.0.1-SNAPSHOT.jar app.jar

# 4. 환경 변수 설정
ENV JAVA_OPTS=""
# 기본 프로파일 설정
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-prod}

# 5. 애플리케이션 실행 명령
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

# 6. 네트워크 포트 개방 (해당코드는 그냥 명시역할만 하는거고 없어도 아무 문제가 없음)
EXPOSE 8077
