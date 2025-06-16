FROM openjdk:17-jdk-slim

LABEL maintainer="Athlete Performance Team <contato@athleteperformance.com>"

# Instalar dependências necessárias
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Criar diretório da aplicação
WORKDIR /app

# Copiar arquivos de configuração do Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Dar permissão de execução ao mvnw
RUN chmod +x mvnw

# Baixar dependências (cache layer)
RUN ./mvnw dependency:go-offline -B

# Copiar código fonte
COPY src src

# Compilar aplicação
RUN ./mvnw clean package -DskipTests

# Expor porta
EXPOSE 8080

# Variáveis de ambiente padrão
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Executar aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar target/athlete-performance-saas-1.0.0.jar"]