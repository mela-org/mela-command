package com.github.stupremee.mela.repository.annotations;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 31.05.19
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonProperty("_id")
@JsonInclude(Include.NON_NULL)
public @interface Identifier {

}
