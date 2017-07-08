package ru.cadmy.api.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Cadmy on 08.07.2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String token;
    private LocalDateTime dateAdded;
    private LocalDateTime validDate;
    private Profile profile;
}
