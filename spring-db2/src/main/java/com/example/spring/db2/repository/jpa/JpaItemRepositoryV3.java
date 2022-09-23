package com.example.spring.db2.repository.jpa;

import com.example.spring.db2.domain.Item;
import com.example.spring.db2.repository.ItemRepository;
import com.example.spring.db2.repository.ItemSearchCond;
import com.example.spring.db2.repository.ItemUpdateDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.example.spring.db2.domain.QItem.item;

@RequiredArgsConstructor
@Repository
@Transactional
public class JpaItemRepositoryV3 implements ItemRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    public JpaItemRepositoryV3(EntityManager em) {
        this.em = em;
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

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
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        // boolean builder 사용 버전
//        BooleanBuilder booleanBuilder = new BooleanBuilder();
//
//        if (StringUtils.hasText(itemName))
//            booleanBuilder.and(item.itemName.like("%" + itemName + "%"));
//
//        if (maxPrice != null)
//            booleanBuilder.and(item.price.loe(maxPrice));
//
//        return jpaQueryFactory.select(item)
//                .from(item)
//                .where(booleanBuilder)
//                .fetch();

        // where 안에 boolean expression 사용
        return jpaQueryFactory.select(item)
                .from(item)
                .where(
                        likeItemName(itemName),
                        priceLoe(maxPrice)
                )
                .fetch();
    }

    private BooleanExpression likeItemName(String itemName) {
        if (StringUtils.hasText(itemName))
            return item.itemName.like("%" + itemName + "%");
        else
            return null;
    }

    private BooleanExpression priceLoe(Integer maxPrice) {
        if (maxPrice != null)
            return item.price.loe(maxPrice);
        else
            return null;
    }
}
