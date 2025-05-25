package org.example.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;

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
    @JdbcTypeCode(Types.VARBINARY)
    private byte[] data;
}
