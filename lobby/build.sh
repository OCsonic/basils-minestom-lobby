cd "$(dirname $0)"
ver="$(cat build.gradle.kts | grep "version = \"[0-9]*\.[0-9]*\.[0-9]*[a-z]\"" | grep -o "[0-9]*\.[0-9]*\.[0-9]*[a-z]")"
serverJar="lobby-${ver}.jar"

echo "Building Lobby version $ver"
./gradlew clean build && mv "build/libs/$serverJar" ../server.jar
