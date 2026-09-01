package com.MBEMNOVA.PULSEHR.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * RECHERCHE CONCEPTS:
 * Choix technique: Validation par annotation personnalisee au niveau de la classe (ConstraintValidator).
 * Justification: Permet la validation declenchée automatiquement par @Valid dans la couche Web/REST
 * et re-utilisable directement sur les DTOs sans polluer les services.
 * Source: Documentation Jakarta Bean Validation 3.0 / Baeldung "Custom Validation with Bean Validation".
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateContratValidator.class)
@Documented
public @interface DateContratValide {
    String message() default "La date de fin doit être supérieure ou égale à la date de début";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}