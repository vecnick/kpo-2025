package analysis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "analys")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextAnalys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public int paragraphs;
    public int words;
    public int symbols;
    public int plagiatePoints;

    public String WCPicName;
    public String WCPicPath;

    public int fileId;
}
