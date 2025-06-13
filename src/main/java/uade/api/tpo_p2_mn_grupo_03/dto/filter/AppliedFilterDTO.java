package uade.api.tpo_p2_mn_grupo_03.dto.filter;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppliedFilterDTO<T> {
    public AppliedFilterDTO(String id, T value) {
        this.id = id;
        this.value = value;
    }
    private String id;
    private T value;
    private String type = "single_value";
} 