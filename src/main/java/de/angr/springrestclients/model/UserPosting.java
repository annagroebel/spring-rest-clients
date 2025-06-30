package de.angr.springrestclients.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class UserPosting implements Serializable {

    private int userId;
    private int id;
    private String title;
    private String body;
}
