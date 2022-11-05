package pojo;

import lombok.Data;

import java.sql.Date;

@Data
public class User {
    private Long id;
    private String email;
    private String username;
    private String password;
    private Date created;
    private Date updated;
}
