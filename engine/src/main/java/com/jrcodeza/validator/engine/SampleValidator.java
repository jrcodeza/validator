package sk.jrcodeza.output;

import com.jrcodeza.validator.api.SimpleValidator;
import com.jrcodeza.validator.api.ValidationError;
import com.jrcodeza.validator.engine.Sample;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

public class SampleValidator {
    private final List<SimpleValidator> simpleValidators;

    public SampleValidator(List<SimpleValidator> simpleValidators) {
        this.simpleValidators = simpleValidators;
    }

    public List<ValidationError> validate(Sample input) {
        List<ValidationError> validationErrors = new ArrayList<>();;
        for (SimpleValidator simpleValidator : simpleValidators) {
            if (simpleValidator.supportsAnnotation(NotNull.class)) {
                validationErrors.addAll(simpleValidator.validate(input.getFieldB(), null));
            }
        }
        return validationErrors;
    }
}
