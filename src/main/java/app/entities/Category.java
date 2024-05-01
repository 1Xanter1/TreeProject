package app.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "categories_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "left_key")
    private int leftKey;

    @Column(name = "right_key")
    private int rightKey;

    @Column(name = "level")
    private int level;

    public long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public int getLeftKey() {
        return leftKey;
    }

    public int getRightKey() {
        return rightKey;
    }

    public int getLevel() {
        return level;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLeftKey(int leftKey) {
        this.leftKey = leftKey;
    }

    public void setRightKey(int rightKey) {
        this.rightKey = rightKey;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
