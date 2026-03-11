#!/usr/bin/env bash
set -euo pipefail

if [ $# -lt 1 ]; then
  echo "❌ Error: Debes proporcionar el nombre del microservicio. Ej: ./scaffold_micro.sh registration-service"
  exit 1
fi

NEW_SERVICE="$1"
TEMPLATE_SERVICE="${2:-users-service}"  # opcional: puedes pasar otro template

# Paquete base (con puntos) y su path (con /)
BASE_PKG="com.nana.torneos"
BASE_DIR="com/nana/torneos"

# Paquete específico del micro (opcional, pero recomendado)
# registration-service -> registration
SERVICE_PKG_SUFFIX="$(echo "$NEW_SERVICE" | sed 's/-service$//' | tr '-' '_' )"
SERVICE_PKG="${BASE_PKG}.${SERVICE_PKG_SUFFIX}"
SERVICE_DIR="${BASE_DIR}/${SERVICE_PKG_SUFFIX}"

echo "🚀 Creando microservicio: $NEW_SERVICE (template: $TEMPLATE_SERVICE)"
echo "📦 Package: $SERVICE_PKG"

if [ ! -d "$TEMPLATE_SERVICE" ]; then
  echo "❌ No existe el servicio template '$TEMPLATE_SERVICE' en el directorio actual."
  exit 1
fi

# sed portable (mac vs linux)
sedi() {
  if sed --version >/dev/null 2>&1; then
    # GNU sed (Linux)
    sed -i "$@"
  else
    # BSD sed (macOS)
    sed -i '' "$@"
  fi
}

# Estructura
mkdir -p "$NEW_SERVICE/applications/app-service/src/main/java/$SERVICE_DIR"
mkdir -p "$NEW_SERVICE/applications/app-service/src/main/resources"
mkdir -p "$NEW_SERVICE/domain/model/src/main/java/$SERVICE_DIR"
mkdir -p "$NEW_SERVICE/domain/usecase/src/main/java/$SERVICE_DIR"
mkdir -p "$NEW_SERVICE/infrastructure/driven-adapters/src/main/java/$SERVICE_DIR"
mkdir -p "$NEW_SERVICE/infrastructure/entry-points/src/main/java/$SERVICE_DIR"
mkdir -p "$NEW_SERVICE/deployment"

# Copiar wrapper y configs base
cp "$TEMPLATE_SERVICE/build.gradle" "$NEW_SERVICE/"
cp "$TEMPLATE_SERVICE/settings.gradle" "$NEW_SERVICE/"
cp "$TEMPLATE_SERVICE/gradlew" "$NEW_SERVICE/"
cp "$TEMPLATE_SERVICE/.gitignore" "$NEW_SERVICE/" 2>/dev/null || true
cp -r "$TEMPLATE_SERVICE/gradle" "$NEW_SERVICE/"

# Copiar gradles de submódulos
cp "$TEMPLATE_SERVICE/applications/app-service/build.gradle" "$NEW_SERVICE/applications/app-service/"
cp "$TEMPLATE_SERVICE/domain/model/build.gradle" "$NEW_SERVICE/domain/model/"
cp "$TEMPLATE_SERVICE/domain/usecase/build.gradle" "$NEW_SERVICE/domain/usecase/"
cp "$TEMPLATE_SERVICE/infrastructure/driven-adapters/build.gradle" "$NEW_SERVICE/infrastructure/driven-adapters/"
cp "$TEMPLATE_SERVICE/infrastructure/entry-points/build.gradle" "$NEW_SERVICE/infrastructure/entry-points/"

# Renombrar rootProject
sedi "s/rootProject.name = 'users-service'/rootProject.name = '$NEW_SERVICE'/g" "$NEW_SERVICE/settings.gradle"
sedi "s/rootProject.name = \"$TEMPLATE_SERVICE\"/rootProject.name = \"$NEW_SERVICE\"/g" "$NEW_SERVICE/settings.gradle" || true

# MainApplication
cat > "$NEW_SERVICE/applications/app-service/src/main/java/$SERVICE_DIR/MainApplication.java" <<EOF
package ${SERVICE_PKG};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {
  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }
}
EOF

# application.yml mínimo (lo ajustas luego)
cat > "$NEW_SERVICE/applications/app-service/src/main/resources/application.yml" <<EOF
spring:
  application:
    name: ${NEW_SERVICE}

server:
  port: 0

management:
  endpoints:
    web:
      exposure:
        include: health,info,prometheus
EOF

echo "✅ Listo: Microservicio '$NEW_SERVICE' generado."
echo "👉 Siguiente: cd $NEW_SERVICE && ./gradlew clean build"