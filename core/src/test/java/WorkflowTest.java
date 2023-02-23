/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.Properties;

public class WorkflowTest {
  private static final String MYSQL_DB_CONN_URL = "10.0.0.103:3306";
  private static final String MYSQL_DB_USER = "root";
  private static final String MYSQL_DB_PASSWORD = "admin";
  private static final String PG_DB_CONN_URL = "10.0.0.103:5432";
  private static final String PG_DB_USER = "root";
  private static final String PG_DB_PASSWORD = "";
  @Test void workflow() {
    Properties info = new Properties();
    final boolean mysqlSource = true;

    final String dbName = "broadleaf" + "_" + "base";
    final String sourceName = mysqlSource ? "mysql" : "postgresql";
    final String schemaName = mysqlSource ? dbName : "public";
    final String url = mysqlSource ? MYSQL_DB_CONN_URL : PG_DB_CONN_URL;
    final String user = mysqlSource ? MYSQL_DB_USER : PG_DB_USER;
    final String password = mysqlSource ? MYSQL_DB_PASSWORD : PG_DB_PASSWORD;

    info.put("model", "inline:{" +
        "  version: '1.0'," +
        "  defaultSchema: '" + "default" + "'," +
        "  schemas: [" +
        "    {" +
        "      name: '" + "default" + "'," +
        "      type: 'custom'," +
        "      factory: 'org.apache.calcite.adapter.jdbc.JdbcSchema$Factory'," +
        "      operand: {" +
        "        jdbcDriver: 'com.mysql.jdbc.Driver'," +
        // "        jdbcUrl:'jdbc:log4jdbc:" + sourceName + "://" + url + "/" + dbName + "'," +
        "        jdbcUrl:'jdbc:" + sourceName + "://" + url + "/" + dbName + "'," +
        "        jdbcUser: '" + user + "'," +
      "        jdbcPassword: '" + password + "'" +
      "      }" +
      "    }" +
      "  ]" +
      "}");

    final String sql =
        "SELECT u1.ADMIN_USER_ID FROM blc_admin_user u1 " +
            "WHERE ADMIN_USER_ID = 1 AND ADMIN_USER_ID IS NOT NULL";
    try {
      Class.forName("com.mysql.jdbc.Driver");

      final Connection connection = DriverManager.getConnection("jdbc:calcite:caseSensitive=false", info);
      // final CalciteConnection calciteConnection = (CalciteConnection) connection.unwrap(CalciteConnection.class);
      //statement = connection.prepareStatement(sql);
      //resultSet = statement.executeQuery();
      final Statement statement = connection.createStatement();
      final ResultSet resultSet = statement.executeQuery(sql);

      final StringBuilder buf = new StringBuilder();
      while(resultSet.next()) {
        int n = resultSet.getMetaData().getColumnCount();
        for(int i = 1; i <= n; ++i) {
          buf.append(i > 1 ? "; " : "").
              append(resultSet.getMetaData().getColumnLabel(i)).append("=").append(resultSet.getObject(i));
        }
        System.out.println(buf);
        buf.setLength(0);
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }



}
