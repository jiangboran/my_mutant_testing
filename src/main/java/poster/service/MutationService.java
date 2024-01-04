package poster.service;

import poster.pojo.entity.MutationEntity;

import java.io.IOException;

public interface MutationService {
    MutationEntity getMutationEntity(String type) throws IOException, InterruptedException;
}
