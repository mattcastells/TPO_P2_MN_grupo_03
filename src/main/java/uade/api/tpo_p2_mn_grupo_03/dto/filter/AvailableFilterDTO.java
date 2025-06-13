package uade.api.tpo_p2_mn_grupo_03.dto.filter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvailableFilterDTO<T> {
    public AvailableFilterDTO(String id, T value) {
        this.id = id;
        this.value = value;
    }
    private String id;
    private T value;
    private String type = "single_value";
} 