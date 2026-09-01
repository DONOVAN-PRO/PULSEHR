package com.MBEMNOVA.PULSEHR.dto;

import com.MBEMNOVA.PULSEHR.entity.PosteType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeCreerDTO {
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotNull(message = "Le poste est requis")
    private PosteType poste;

    @NotNull(message = "Le salaire est requis")
    @DecimalMin(value = "0.01", message = "Le salaire doit être supérieur à 0")
    @DecimalMax(value = "9999999.99", message = "Le salaire doit être inférieur à 10 000 000")
    private BigDecimal salaire;

    @NotNull(message = "La date d'embauche est requise")
    private LocalDate dateEmbauche;

    @NotNull(message = "Le département est obligatoire")
    private Long departementId;
}