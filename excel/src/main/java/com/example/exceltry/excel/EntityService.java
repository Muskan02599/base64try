package com.example.exceltry.excel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EntityService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getAllEntities() {
        String query = "SELECT * FROM excel";
        return jdbcTemplate.queryForList(query);
    }
    public void insertEntity(EntityClass entity) {
        String query = "INSERT INTO excel (name, age) VALUES (?, ?)";
        jdbcTemplate.update(query, entity.getName(), entity.getAge());
    }
}
