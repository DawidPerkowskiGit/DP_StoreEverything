package com.dmrl.storeverything.DP_Testy;

import com.dmrl.storeverything.category.Category;
import com.dmrl.storeverything.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testAddCategory() {

        String categoryNames[] = {"informacja" , "sprzedam" , "kupię" , "oddam"};

        //String categoryNames[] = {"testololo"};

        for (String singleCategory : categoryNames
             ) {
            Category category = new Category();
            category.setCategoryName(singleCategory);

            categoryRepository.save(category);

            Category saveCategory = categoryRepository.save(category);

            Category existCategory = entityManager.find(Category.class, saveCategory.getId());

            assertThat(existCategory.getCategoryName()).isEqualTo(category.getCategoryName());
        }



    }

    @Test
    public void testFindCategoryByName() {
        String categoryName = "oddam";

        Category category = categoryRepository.findByCategoryName(categoryName);

        assertThat(category).isNotNull();
    }

}