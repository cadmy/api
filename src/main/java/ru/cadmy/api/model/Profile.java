package ru.cadmy.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ssemenov on 28.06.17.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    Integer id;
    String name;
    String password;
}
