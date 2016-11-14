package org.mbari.vars.kb.server.services;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.mbari.vars.kb.server.PhylogenyDAO;
import org.mbari.vars.kb.server.model.ConceptNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Brian Schlining
 * @since 2016-11-11T16:30:00
 */
public class SQLServerPhylogenyDAO implements PhylogenyDAO {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private String upSQL;
    private String downSQL;
    private final HikariDataSource dataSource;

    public SQLServerPhylogenyDAO(String jdbcUrl,String jdbcUsername, String jdbcPassword) {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(jdbcUsername);
        config.setPassword(jdbcPassword);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setConnectionTestQuery("SELECT 1");
        dataSource = new HikariDataSource(config);

        downSQL = readSQL(getClass().getResource("/sql/down.sql"));
        upSQL = readSQL(getClass().getResource("/sql/up.sql"));
    }

    @Override
    public Optional<ConceptNode> getDown(String name) {
        List<PhylogenyRow> rows = executeQuery(downSQL, name);
        return rowsToConceptNode(rows);
    }

    @Override
    public Optional<ConceptNode> getUp(String name) {
        List<PhylogenyRow> rows = executeQuery(upSQL, name);
        return rowsToConceptNode(rows);
    }

    private Optional<ConceptNode> rowsToConceptNode(List<PhylogenyRow> rows) {

        List<ConceptNode> nodes = new ArrayList<>();
        for (PhylogenyRow row : rows) {
            Optional<ConceptNode> parentNode = nodes.stream()
                    .filter(n -> n.getName().equals(row.getParentName()))
                    .findFirst();

            if (!parentNode.isPresent()) {
                parentNode = Optional.of(new ConceptNode(row.getParentName(), null, new HashSet<>(), row.getParentRank()));
                nodes.add(parentNode.get());
            }

            ConceptNode parent = parentNode.get();
            ConceptNode child = new ConceptNode(row.getChildName(), parent, new HashSet<>(), row.getChildRank());
            nodes.add(child);
            parent.getChildren().add(child);
        }

        Optional<ConceptNode> root = nodes.stream()
                .filter(n -> !n.getParent().isPresent())
                .findFirst();

        if (!root.isPresent()) {
            log.error("No root concept was found");
        }
        return root;
    }



    private List<PhylogenyRow> executeQuery(String sql, String name) {

        List<PhylogenyRow> rows = new ArrayList<>();

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long parentId = resultSet.getLong(1);
                String parentName = resultSet.getString(2);
                String parentRank = resultSet.getString(3);
                long childID = resultSet.getLong(4);
                String childName = resultSet.getString(5);
                String childRank = resultSet.getString(6);
                PhylogenyRow r = new PhylogenyRow(parentId, parentName, parentRank, childID, childName, childRank);
                rows.add(r);
            }
        }
        catch (SQLException e) {
            log.error("Failed to execute SQL", e);
        }
        return rows;

    }

    private String readSQL(URL url) {

        String s = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            s = reader.lines().collect(Collectors.joining(" "));
        }
        catch (IOException e) {
            log.error("Failed to read SQL template from " + url, e);
        }

        return s;
    }
}

class PhylogenyRow {
    private long parentID;
    private String parentName;
    private String parentRank;
    private long childID;
    private String childName;
    private String childRank;

    public PhylogenyRow(long parentID, String parentName, String parentRank, long childID, String childName, String childRank) {
        this.childID = childID;
        this.childName = childName;
        this.parentID = parentID;
        this.parentName = parentName;
        this.parentRank = parentRank;
        this.childRank = childRank;
    }

    public long getChildID() {
        return childID;
    }

    public String getChildName() {
        return childName;
    }

    public long getParentID() {
        return parentID;
    }

    public String getParentName() {
        return parentName;
    }

    public String getParentRank() {
        return parentRank;
    }

    public String getChildRank() {
        return childRank;
    }
}
