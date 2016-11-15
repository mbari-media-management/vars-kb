package vars.knowledgebase;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Collection;
import java.util.ArrayList;
import org.mbari.sql.QueryFunction;

import org.mbari.sql.QueryableImpl;
import vars.VARSException;

/**
 * Created by IntelliJ IDEA.
 * User: brian
 * Date: Sep 29, 2009
 * Time: 12:58:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class KnowledgebasePersistenceServiceImpl extends QueryableImpl implements KnowledgebasePersistenceService {

    private static final String jdbcPassword;
    private static final String jdbcUrl;
    private static final String jdbcUsername;
    private static final String jdbcDriver;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("knowledgebase-jdbc", Locale.US);
        jdbcUrl = bundle.getString("jdbc.url");
        jdbcUsername = bundle.getString("jdbc.username");
        jdbcPassword = bundle.getString("jdbc.password");
        jdbcDriver = bundle.getString("jdbc.driver");
    }

    private final Map<Concept, List<String>> descendantNameCache = Collections.synchronizedMap(new HashMap<Concept, List<String>>());

    /**
     * Constructs ...
     */
    public KnowledgebasePersistenceServiceImpl() {
        super(jdbcUrl, jdbcUsername, jdbcPassword, jdbcDriver);
    }


    /**
     *
     * @param conceptname
     * @return
     */
    public boolean doesConceptNameExist(String conceptname) {

        String sql = "SELECT count(*) FROM ConceptName WHERE ConceptName = ?";

        final QueryFunction<Boolean> queryFunction = resultSet -> {
            Integer n = 0;
            while (resultSet.next()) {
                n = resultSet.getInt(1);
            }
            return n > 0;
        };

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, conceptname);
            boolean exists = queryFunction.apply(preparedStatement.executeQuery());
            preparedStatement.close();
            return exists;
        }
        catch (Exception e) {
            throw new VARSException("Failed to execute " + sql, e);
        }

    }


}
