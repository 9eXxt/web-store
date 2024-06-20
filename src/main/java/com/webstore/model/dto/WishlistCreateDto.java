package com.webstore.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistCreateDto {
    @NotNull
    Integer item_id;
}
