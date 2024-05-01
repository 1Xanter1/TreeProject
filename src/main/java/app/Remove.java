package app;

import app.entities.Category;
import jakarta.persistence.*;

import java.util.Scanner;

public class Remove {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("main");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        try{
            em.getTransaction().begin();

            System.out.println("Выберите id категории для удаления: ");
            String removeId = scanner.nextLine();

            TypedQuery<Category> categoryTypedQuery = em.createQuery("select c from Category c where c.categoryId = :removeId", Category.class);
            categoryTypedQuery.setParameter("removeId", removeId);

            Category category = categoryTypedQuery.getSingleResult();

            em.createQuery("delete from Category c where c.leftKey between :leftKey and :rightKey")
            .setParameter("leftKey", category.getLeftKey())
            .setParameter("rightKey", category.getRightKey())
            .executeUpdate();

            int difference = category.getRightKey() - category.getLeftKey() + 1;

//            em.createQuery("update Category c set c.leftKey = c.leftKey + :difference where c.rightKey > :leftKey")
//                    .setParameter()

            em.createQuery("update Category c set c.rightKey = c.rightKey - :difference where c.rightKey > :rightKey")
            .setParameter("difference", difference)
            .setParameter("rightKey", category.getRightKey())
            .executeUpdate();

            em.getTransaction().commit();
            System.out.println("Удаление выполнено!");
        } catch (Exception e){
//            throw new RuntimeException();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
