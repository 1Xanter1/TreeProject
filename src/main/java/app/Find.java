package app;

import app.entities.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Find {

    public static void main(String[] args) {
        // Введите название категории: Процессоры

        // Процессоры
        // - Intel
        // - AMD

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("main");
        EntityManager em = emf.createEntityManager();

            try {
                Scanner scanner = new Scanner(System.in);

                System.out.print("Введите название категории: ");
                String categoryName = scanner.nextLine();

                System.out.println();

                printSubcategories(categoryName, em);

                scanner.close();
            } finally {
                em.close();
                emf.close();
            }
        }

        private static void printSubcategories(String categoryName, EntityManager em) {
            TypedQuery<Category> query = em.createQuery(
                    "SELECT c FROM Category c WHERE c.name = :name", Category.class);
            query.setParameter("name", categoryName);

            Category category = query.getSingleResult();

            TypedQuery<Category> subcategoriesQuery = em.createQuery(
                    "SELECT c FROM Category c WHERE c.leftKey >= :left AND c.rightKey <= :right ORDER BY c.leftKey", Category.class);
            subcategoriesQuery.setParameter("left", category.getLeftKey());
            subcategoriesQuery.setParameter("right", category.getRightKey());

            List<Category> subcategories = subcategoriesQuery.getResultList();

            for (Category subcategory : subcategories) {
                printCategoryTree(subcategory, subcategory.getLevel() - category.getLevel());
            }
        }

        private static void printCategoryTree(Category category, int depth) {

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                stringBuilder.append("- ");
            }
            stringBuilder.append(category.getName());
            System.out.println(stringBuilder);

        }
    }
