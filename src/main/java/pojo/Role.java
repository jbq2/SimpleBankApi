package pojo;

import lombok.Data;

import java.sql.Date;

@Data
public class Role {
    private Long id;
    private String name;
    private Date created;
    private Date updated;
}
