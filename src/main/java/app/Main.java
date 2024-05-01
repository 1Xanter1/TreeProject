package app;

import jakarta.persistence.*;

import app.entities.Category;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");

        EntityManager manager = factory.createEntityManager();
        try{

            TypedQuery<Category> categoryTypedQuery = manager.createQuery(
                    "select c from Category c", Category.class
            );
            List<Category> categories = categoryTypedQuery.getResultList();
            String value = "";
            for (Category category: categories){
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < category.getLevel(); i++) {
                    stringBuilder.append("- ");
                }
                System.out.println(stringBuilder + category.getName());
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
