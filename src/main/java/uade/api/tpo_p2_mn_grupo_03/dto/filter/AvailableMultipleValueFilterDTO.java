package uade.api.tpo_p2_mn_grupo_03.dto.filter;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AvailableMultipleValueFilterDTO extends AvailableFilterDTO<List<MultipleFilterValueDTO>> {
    private String type = "multiple_value";
    public AvailableMultipleValueFilterDTO(String id, List<MultipleFilterValueDTO> values) {
        super(id, values);
    }
} 