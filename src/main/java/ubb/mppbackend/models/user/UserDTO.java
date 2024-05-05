package ubb.mppbackend.models.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String pictureUrl;
    private int age;

    public UserDTO(User userToConvert) {
        this.id = userToConvert.getId();
        this.firstName = userToConvert.getFirstName();
        this.lastName = userToConvert.getLastName();
        this.pictureUrl = userToConvert.getPictureUrl();
        this.age = userToConvert.getAge();
    }
}
