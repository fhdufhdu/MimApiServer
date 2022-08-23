package com.fhdufhdu.mim.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity<Key> {
    public abstract Key getId();
    public abstract void setId(Key id);
}
