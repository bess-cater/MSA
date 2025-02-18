package com.msa.accounts.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
    name = "Customer",
    description = "Customer info"
)
public class CustomerDTO {

    @NotEmpty(message = "Can not be empty!")
    @Size(min=5, max = 30)
    private String name;
    @NotEmpty(message = "Can not be empty!")
    @Email(message="Email address should be a valid value")
    private String email;
    @NotEmpty(message = "Can not be empty!")
    // @Pattern(regexp = "(^$|[0-9]{15})")
    private String mobileNumber;

    private AccountsDTO accountDTO;

}
