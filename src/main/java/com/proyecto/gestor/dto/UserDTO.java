package com.proyecto.gestor.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO implements Serializable {

    @Serial  private static final long serialVersionUID = 1L;

    private Long userId;
    private String username;
    private String email;
    private String role;
}
