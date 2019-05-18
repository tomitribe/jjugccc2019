/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.superbiz.val;

import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.json.JsonArray;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@RequireClaim("役割")
@IsArray("役割")
@Documented
@javax.validation.Constraint(validatedBy = {役割.Constraint.class})
@Target({METHOD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface 役割 {

    String value();

    Class<?>[] groups() default {};

    String message() default "The '役割' claim must contain '{value}'";

    Class<? extends Payload>[] payload() default {};


    class Constraint implements ConstraintValidator<役割, JsonWebToken> {
        private 役割 役割;

        @Override
        public void initialize(final 役割 constraint) {
            this.役割 = constraint;
        }

        @Override
        public boolean isValid(final JsonWebToken value, final ConstraintValidatorContext context) {
            final JsonArray claim = value.getClaim("役割");
            List<String> list = toStrings(claim);
            return list.contains(役割.value());
        }

        private List<String> toStrings(final JsonArray jsonArray) {
            final List<String> list = new ArrayList<String>();
            for (final JsonValue jsonValue : jsonArray) {
                final JsonString jsonString = (JsonString) jsonValue;
                list.add(jsonString.getString());
            }
            return list;
        }
    }
}
