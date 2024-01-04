package poster.controller;

import io.pojo.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import poster.pojo.entity.MutationEntity;
import poster.pojo.request.MutateRequest;
import poster.service.MutationService;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor
public class MutationController {
    private final MutationService mutationService;
    @GetMapping("mutate")
    public CommonResponse<MutationEntity> mutate(@Valid @RequestBody MutateRequest request) throws IOException, InterruptedException {
        CommonResponse<MutationEntity> commonResponse = CommonResponse.success(mutationService.getMutationEntity(request.getType()));
        commonResponse.setCode(104);
        return commonResponse;
    }
}
