package poster.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import poster.pojo.entity.MutationEntity;

public interface MutationDao extends JpaRepository<MutationEntity, Long> {
    MutationEntity findById(String id);
}
