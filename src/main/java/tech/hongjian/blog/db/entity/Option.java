package tech.hongjian.blog.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`option`")
public class Option {
    @Id
    private String name;

    private String value;

    private String description;

    public Option(String name, String value) {
        this.name = name;
        this.value = value;
    }
}