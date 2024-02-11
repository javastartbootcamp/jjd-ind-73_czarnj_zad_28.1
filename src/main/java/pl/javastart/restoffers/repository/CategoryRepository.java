package pl.javastart.restoffers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.javastart.restoffers.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Category findByNameIgnoreCase(String name);
    @Query(value = "select count(*) from category c " +
            "left join offer o on c.id = o.category_id where c.id =?1", nativeQuery = true)
    public int countOffersForCategoryId(long id);
}
