package uade.api.tpo_p2_mn_grupo_03.dto.filter;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AppliedMultipleValueFilterDTO extends AppliedFilterDTO<List<MultipleFilterValueDTO>> {
    public AppliedMultipleValueFilterDTO(String id, List<MultipleFilterValueDTO> values) {
        super(id, values);
        setType("multiple_value");
    }
} 