package app;

import app.entities.Category;
import jakarta.persistence.*;

import java.util.Scanner;

public class Create {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("main");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Введите id родительской категории: ");
            long parentId = scanner.nextLong();
            scanner.nextLine();

            System.out.print("Введите название категории: ");
            String categoryName = scanner.nextLine();

            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            TypedQuery<Category> parentCategoryQuery = em.createQuery("SELECT c FROM Category c WHERE c.categoryId = :parentId", Category.class);
            parentCategoryQuery.setParameter("parentId", parentId);
            Category parentCategory = parentCategoryQuery.getSingleResult();

//            int maxRightKey = em.createQuery("SELECT MAX(c.rightKey) FROM Category c WHERE c.leftKey > :leftKey AND c.rightKey < :rightKey", Integer.class)
//                    .setParameter("leftKey", parentCategory.getLeftKey())
//                    .setParameter("rightKey", parentCategory.getRightKey())
//                    .getSingleResult();

            int maxRightKey = parentCategory.getRightKey();

            int newLeftKey = maxRightKey;
            int newRightKey = maxRightKey + 1;
            int newLevel = parentCategory.getLevel() + 1;


            em.createQuery("UPDATE Category c SET c.rightKey = c.rightKey + 2 WHERE c.rightKey >= :parentRightKey")
                    .setParameter("parentRightKey", maxRightKey)
                    .executeUpdate();

            em.createQuery("UPDATE Category c SET c.leftKey = c.leftKey + 2 WHERE c.leftKey > :parentRightKey")
                    .setParameter("parentRightKey", maxRightKey)
                    .executeUpdate();

            Category newCategory = new Category();
            newCategory.setName(categoryName);
            newCategory.setLeftKey(newLeftKey);
            newCategory.setRightKey(newRightKey);
            newCategory.setLevel(newLevel);

            em.persist(newCategory);

            transaction.commit();

            System.out.println("категория успешно добавлена");
        } finally {
            em.close();
            emf.close();
        }
    }
}
