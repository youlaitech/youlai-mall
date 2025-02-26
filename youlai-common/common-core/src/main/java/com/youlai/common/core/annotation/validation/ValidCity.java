package com.youlai.common.core.annotation.validation;

import com.youlai.common.core.validator.CityValidator;
import com.youlai.common.enums.AddressFieldEnum;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验城市地名等是否合法，不接受null
 *
 * @author Gadfly
 * @since 2021-08-06 16:02
 */
@Documented
@Constraint(validatedBy = CityValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface ValidCity {
    /* 校验字段类型 */
    AddressFieldEnum value() default AddressFieldEnum.CITY;

    String message() default "{city.valid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
