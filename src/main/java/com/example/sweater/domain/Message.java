package com.example.sweater.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String text;
    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")           // по умолчанию было бы author_id
    private User author;

    public String getAuthorName(){
        return author != null ? author.getUsername() : "<none>";
    }

}
