package ed.lab.ed1labo04.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ed.lab.ed1labo04.entity.ShoppingCartEntity;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {}

