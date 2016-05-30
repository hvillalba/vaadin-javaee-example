package net.hdavid.vaadinjeeexample.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
public class Product extends AbstractEntity {

    @Getter @Setter private String name;
    @Getter @Setter private String description;

}
