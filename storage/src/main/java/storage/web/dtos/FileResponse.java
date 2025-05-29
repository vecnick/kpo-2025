package storage.web.dtos;

public record FileResponse(
                Integer id,
                String name,
                String path,
                String hash) {
}