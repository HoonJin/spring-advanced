package com.example.spring.db2.repository.jdbctemplate;

import com.example.spring.db2.domain.Item;
import com.example.spring.db2.repository.ItemRepository;
import com.example.spring.db2.repository.ItemSearchCond;
import com.example.spring.db2.repository.ItemUpdateDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateItemRepositoryV2 implements ItemRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcTemplateItemRepositoryV2(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("item")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Item save(Item item) {
        BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(item);
        Number id = simpleJdbcInsert.executeAndReturnKey(source);

        item.setId(id.longValue());
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        String sql = "update item set item_name = :itemName, price = :price, quantity = :quantity where id = :id";

        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("itemName", updateParam.getItemName())
                .addValue("price", updateParam.getPrice())
                .addValue("quantity", updateParam.getQuantity())
                .addValue("id", itemId);
        jdbcTemplate.update(sql, source);
    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "select id, item_name as ItemName, price, quantity from item where id = :id";
        // item_name as ItemName 은 옵션

        try {
            Map<String, Long> source = Map.of("id", id);
            Item item = jdbcTemplate.queryForObject(sql, source, itemRowMapper());
            return Optional.of(item);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Item> itemRowMapper() {
        return BeanPropertyRowMapper.newInstance(Item.class);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(cond);

        String clause = "";
        if (StringUtils.hasText(itemName) || maxPrice != null) {
            clause = " where ";

            boolean andFlag = false;
            if (StringUtils.hasText(itemName)) {
                clause += "item_name like concat('%', :itemName, '%')";
                andFlag = true;
            }

            if (maxPrice != null) {
                if (andFlag) {
                    clause += " and ";
                }
                clause += "price <= :maxPrice";
            }
        }

        String sql = "select id, item_name, price, quantity from item" + clause;
        return jdbcTemplate.query(sql, params, itemRowMapper());
    }

}
