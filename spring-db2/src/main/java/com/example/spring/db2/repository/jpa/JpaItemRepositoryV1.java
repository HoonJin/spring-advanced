package com.example.spring.db2.repository.jpa;

import com.example.spring.db2.domain.Item;
import com.example.spring.db2.repository.ItemRepository;
import com.example.spring.db2.repository.ItemSearchCond;
import com.example.spring.db2.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional
// @Repository, @Transactional annotation 으로 인하여 JPA 예외가 스프링 예외로 변환됨
// 컴포넌트 스캔시 PersistenceExceptionTranslationPostProcessor 으로 예외 변환 등록
// EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible
public class JpaItemRepositoryV1 implements ItemRepository {

    private final EntityManager em;

    @Override
    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item item = em.find(Item.class, itemId);
        item.setPrice(updateParam.getPrice());
        item.setQuantity(updateParam.getQuantity());
        item.setItemName(updateParam.getItemName());
        // em.persist(item); // 저장 안해도 알아서 저장함
    }

    @Override
    public Optional<Item> findById(Long id) {
        Item item = em.find(Item.class, id);
        return Optional.ofNullable(item);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String jpql = "select i from Item i";

        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        String clause = "";
        if (StringUtils.hasText(itemName) || maxPrice != null) {
            clause = " where ";

            boolean andFlag = false;
            if (StringUtils.hasText(itemName)) {
                clause += "i.itemName like concat('%', :itemName, '%')";
                andFlag = true;
            }

            if (maxPrice != null) {
                if (andFlag) {
                    clause += " and ";
                }
                clause += "price <= :maxPrice";
            }
        }
        TypedQuery<Item> query = em.createQuery(jpql + clause, Item.class);

        if (StringUtils.hasText(itemName))
            query.setParameter("itemName", itemName);

        if (maxPrice != null)
            query.setParameter("maxPrice", maxPrice);

        return query.getResultList();
    }
}
