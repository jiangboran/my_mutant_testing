package poster.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
@Accessors(chain = true)
public class MutationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String score;

    private String message;

    private String pool;
}
