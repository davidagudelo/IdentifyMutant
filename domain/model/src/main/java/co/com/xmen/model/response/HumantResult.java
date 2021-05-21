package co.com.xmen.model.response;

import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class HumantResult {

    private int count_mutant_dna;
    private int count_human_dna;
    private double ratio;

}
