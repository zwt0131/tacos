package com.example.demo.data;

import com.example.demo.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.function.Predicate;

// @Repository，Spring组件扫描自动发现，并初始化为spring应用上下文的bean
@Repository
public class JdbcIngredientRepository implements IngredientRepository{

    private JdbcTemplate jdbc;

    // @Autowired可以用在构造器，方法，参数，属性，注解类型定义上
    // 创建JdbcIngredientRepository bean的时候会通过该构造器将JdbcTemplate注入赋值给实例变量
    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbc.query("select id, name, type from Ingredient",
                this::mapRowToIngredient);
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                // Ingredient下定义的简单枚举类Type,编译器生成的继承Enum，包含了静态方法valueOf
                Ingredient.Type.valueOf(rs.getString("type"))
        );
    }

    @Override
    public Ingredient findOne(String id) {
        return jdbc.queryForObject("select id, name, type from Ingredient where id=?",
                new RowMapper<>() {
                    @Override
                    public Ingredient mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new Ingredient(resultSet.getString("id"),
                                resultSet.getString("name"),
                                Ingredient.Type.valueOf(resultSet.getString("type")));
                    }
                }, id);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbc.update(
                "insert into Ingredient (id, name, type) values (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString()
        );
        return ingredient;
    }
}
