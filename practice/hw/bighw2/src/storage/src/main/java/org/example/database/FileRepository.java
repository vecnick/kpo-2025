package org.example.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<MyFileEntity, String> {
}

