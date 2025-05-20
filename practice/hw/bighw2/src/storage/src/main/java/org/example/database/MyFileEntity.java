package org.example.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "files")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyFileEntity {

    @Id
    @Column(length = 64)
    private String id;

    private String filename;

    private String contentType;

    @Lob
    @Column(columnDefinition = "BYTEA")
    private byte[] data;

}
