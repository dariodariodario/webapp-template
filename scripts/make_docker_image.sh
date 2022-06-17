tag_hash=$(git rev-parse --short HEAD)
echo building version $tag_hash
docker build --platform linux/amd64 -f docker/Dockerfile . -t pitch_app:$tag_hash