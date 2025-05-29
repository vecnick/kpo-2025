package analysis.web.dtos;

public record StatisticResponse(
                Integer id,
                Integer paragraphs,
                Integer words,
                Integer symbols) {
}