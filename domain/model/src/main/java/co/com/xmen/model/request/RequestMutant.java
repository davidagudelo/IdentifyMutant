package co.com.xmen.model.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class RequestMutant {
    private List<String> dna;
}
