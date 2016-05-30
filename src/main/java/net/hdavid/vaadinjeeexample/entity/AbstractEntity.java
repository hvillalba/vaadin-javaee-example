package net.hdavid.vaadinjeeexample.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

@MappedSuperclass
public class AbstractEntity implements Serializable, Cloneable {

    @Id
    @Getter
    @Setter
    private Long id;

    @Version
    int version;

    @Override
    public boolean equals(Object obj) {
        if (this.id == null)
            throw new RuntimeException ("id not assigned before equals called");
        if (this == obj)
            return true;
        if (obj != null && obj.getClass().equals(getClass()))
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
