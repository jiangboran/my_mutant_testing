package poster.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Schema(description = "变异的请求")
@Getter
public class MutateRequest {
    @Schema(description = "变异算子类型", required = true)
    @NotNull
    private String type;
}
