package org.partapp.kicktask4.entity;

import java.util.Objects;

public class ItemEntity {
    private Long id;
    private String name;
    private String description;
    private Long ownerId;

    public ItemEntity() {

    }

    public ItemEntity(String name, String description, Long ownerId) {
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
    }

    public ItemEntity(Long id, String name, String description, Long ownerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntity that = (ItemEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(ownerId, that.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, ownerId);
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }
}
