package a.polverini.my;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import org.h2.jdbc.JdbcClob;

import a.polverini.my.exceptions.InvalidKeyException;
import a.polverini.my.exceptions.UnexpectedTypeException;

public abstract class DB {

	/**
	 * the type of the table fields
	 */
	public enum Type {
		STRING,
		INTEGER,
		BOOLEAN,
		FLOAT,
		TIMESTAMP
	}

	/** 
	 * database URL
	 */
	private final String url;
	
	/** 
	 * database username
	 */
	private final String user;
	
	/** 
	 * database password
	 */
	private final String pswd;
	
	/**
	 * the connection
	 */
	private Connection connection;
	
	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * @return the URL
	 */
	public String getURL() {
		return this.url;
	}

	/**
	 * constructor
	 * @param url the database URL
	 * @param user the database user
	 * @param pswd the database password
	 * @throws SQLException 
	 */
	public DB(String url, String user, String pswd) throws SQLException {
		this.url = url;
		this.user = user;
		this.pswd = pswd;
		connection = DriverManager.getConnection(url, this.user, this.pswd);
	}

	/**
	 * @throws SQLException
	 */
	public void dispose() {
		try {
			if(isConnected()) {
				connection.close();
				connection = null;
			}
		} catch (SQLException e) {
		}
	}

	/**
	 * @return true if connected
	 * @throws SQLException
	 */
	public boolean isConnected() throws SQLException {
		return (connection!=null && !connection.isClosed());
	}

	/**
	 * @param table the table name
	 * @return the mapping between the field-name and the relevant type
	 * @throws SQLException
	 * @throws UnexpectedTypeException
	 */
	public Map<String, Type> getTypes(String table) throws SQLException, UnexpectedTypeException {
		Map<String, Type> types = new HashMap<String, Type>();
		if(isConnected()) {
			DatabaseMetaData metadata = connection.getMetaData();
			if(metadata!=null) {
				ResultSet resultSet = metadata.getColumns(null, null, table, null);
				while (resultSet.next()) {
					String name = resultSet.getString("COLUMN_NAME");
					String type = resultSet.getString("TYPE_NAME");
					switch(type) {
					case "varchar":
					case "text":
						types.put(name, Type.STRING);
						break;
					case "int4":
					case "int8":
						types.put(name, Type.INTEGER);
						break;
					case "bool":
						types.put(name, Type.BOOLEAN);
						break;
					case "float8":
						types.put(name, Type.FLOAT);
						break;
					case "timestamp":
						types.put(name, Type.TIMESTAMP);
						break;
					default:
						throw new UnexpectedTypeException(name, type);
					}
				}
			}
		}
		return types;
	}

	/**
	 * @param table the table name
	 * @return the number of entries in the specified table
	 * @throws SQLException
	 */
	public int count(String table) throws SQLException {
		int count = 0;
		if(isConnected()) {
			String sql = String.format("SELECT count(*) FROM %s", table);
			Statement statement = null;
			ResultSet rs = null;
			try {
				statement = connection.createStatement();
				rs = statement.executeQuery(sql);
				rs.next();
				count = rs.getInt(1);
			} catch (SQLException e) {
				throw e;
			} finally {
				if(rs!=null) rs.close();
				if(statement!=null) statement.close();
			}
		}
		return count;
	}

	/**
	 * @param table the table name
	 * @return the next key value
	 * @throws SQLException
	 */
	public int pk(String table) throws SQLException {
		int pk = 0;
		if(isConnected()) {
			String sql = String.format("SELECT max(pk) FROM %s", table);
			Statement statement = null;
			ResultSet rs = null;
			try {
				statement = connection.createStatement();
				rs = statement.executeQuery(sql);
				rs.next();
				pk = rs.getInt(1);
			} catch (SQLException e) {
				throw e;
			} finally {
				if(rs!=null) rs.close();
				if(statement!=null) statement.close();
			}
		}
		return pk+1;
	}

	/**
	 * delete the content of the specified table and the linked entries in the other tables
	 * @param table the table name
	 * @throws SQLException
	 */
	public void truncate(String table) throws SQLException {
		if(isConnected()) {
			String sql = String.format("TRUNCATE TABLE %s CASCADE;", table);
			Statement statement = null;
			try {
				statement = connection.createStatement();
				statement.execute(sql);
			} catch (SQLException e) {
				throw e;
			} finally {
				if(statement!=null) statement.close();
			}
		}
	}

	/**
	 * delete the content of the specified table 
	 * @param table the table name
	 * @throws SQLException
	 */
	public void delete(String table) throws SQLException {
		if(isConnected()) {
			String sql = String.format("DELETE FROM %s;", table);
			Statement statement = null;
			try {
				statement = connection.createStatement();
				statement.execute(sql);
			} catch (SQLException e) {
				throw e;
			} finally {
				if(statement!=null) statement.close();
			}
		}
	}
	
	/**
	 * query the content of the specified table
	 * @param table the table name
	 * @param keys the mapping key vs field
	 * @return the list of properties
	 * @throws SQLException
	 */
	public List<Properties> query(String table, Map<Object, String> keys) throws SQLException {
		List<Properties> list = new ArrayList<>();
		if(isConnected()) {
			Object[] k = keys.keySet().toArray();
			String[] f = new String[k.length];
			for(int i=0; i<k.length; i++){
				f[i] = keys.get(k[i]);
			}
			String sql = String.format("SELECT %s FROM %s", String.join(", ", f), table);
			Statement statement = null;
			ResultSet rs = null;
			try {   
				statement = connection.createStatement();
				rs = statement.executeQuery(sql);
				while (rs.next()) {
					Properties properties = new Properties();
					for(int i=0; i<k.length; i++) {
						Object val = rs.getObject(i+1);
						if(val!=null) {
							if (val instanceof JdbcClob) {
								JdbcClob clob = (JdbcClob)val;
								properties.put(k[i], clob.getSubString(1, (int) clob.length()));
							} else {
								properties.put(k[i], val);
							}
						}
					}
					list.add(properties);
				}
			} catch (SQLException e) {
				throw e;
			} finally {
				if(rs!=null) rs.close();
				if(statement!=null) statement.close();
			}
		} else {
			System.err.println("not-connected!");
		}
		return list;
	}

	/**
	 * insert the entries in the specified table
	 * @param table the table name
	 * @param keys the mapping key vs field
	 * @param data the list of properties
	 * @throws SQLException
	 * @throws UnexpectedTypeException
	 */
	public void insert(String table, Map<Object, String> keys, List<Properties> data) throws UnexpectedTypeException, SQLException {
		if(isConnected()) {
			Map<String, Type> types = getTypes(table);
			Object[] k = keys.keySet().toArray();
			String[] f = new String[k.length];
			String[] v = new String[k.length];
			Type[]   t = new Type[k.length];
			for(int i=0; i<k.length; i++){
				f[i] = keys.get(k[i]);
				v[i] = "?";
				t[i] = types.get(f[i]);
			}
			String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", table, String.join(", ", f), String.join(", ", v));
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(sql);
				int n=0;
				for(Properties properties : data) {
					for(int i=0; i<k.length; i++) {
						Object val = properties.get(k[i]);
						switch(t[i]) {
						case STRING:
							if(val instanceof String) {
								statement.setString(i+1, (String)val);
							} else {
								statement.setObject(i+1, val);
							}
							break;
						case INTEGER:
							if(val instanceof Long) {
								statement.setLong(i+1, (Long)val);
							} else if(val instanceof String) {
								statement.setLong(i+1, Long.parseLong((String)val));
							} else {
								statement.setObject(i+1, val);
							}
							break;
						case FLOAT:
							if(val instanceof Float) {
								statement.setFloat(i+1, (Float)val);
							} else if(val instanceof String) {
								statement.setFloat(i+1, Float.parseFloat((String)val));
							} else {
								statement.setObject(i+1, val);
							}
							break;
						case BOOLEAN:
							if(val instanceof Boolean) {
								statement.setBoolean(i+1, (Boolean)val);
							} else if(val instanceof String) {
								statement.setBoolean(i+1, Boolean.parseBoolean((String)val));
							} else {
								statement.setObject(i+1, val);
							}
							break;
						case TIMESTAMP:
							if(val instanceof Timestamp) {
								statement.setTimestamp(i+1, (Timestamp)val);
							} else if(val instanceof String) {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm:ss", Locale.UK);
								sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
								statement.setTimestamp(i+1, Timestamp.valueOf((String)val));
							} else {
								statement.setObject(i+1, val);
							}
							break;
						default:
							throw new UnexpectedTypeException(f[i], t[i].toString());
						}
					}
					statement.addBatch();
					n++;
					if(n%1000==0 || n==data.size()) {
						statement.executeBatch();
					}
				}
			} catch (SQLException e) {
				throw e;
			} finally {
				if(statement!=null) statement.close();
			}
		}
	}

	/**
	 * update the entries in the specified table
	 * @param table the table name
	 * @param keys the mapping key vs field
	 * @param data the list of properties
	 * @param pk the table key
	 * @throws SQLException
	 * @throws InvalidKeyException
	 * @throws UnexpectedTypeException
	 */
	public void update(String table, Map<String, String> keys, List<Properties> data, String pk) throws SQLException, UnexpectedTypeException, InvalidKeyException {
		if(isConnected()) {
			Map<String, Type> types = getTypes(table);
			Object[] k = new Object[keys.size()];
			String[] f = new String[k.length];
			String[] v = new String[k.length-1];
			Type[]   t = new Type[k.length];
			if(!keys.containsKey(pk) || !types.containsKey(keys.get(pk))) {
				throw new InvalidKeyException(pk);
			} else {
				int i = 0;
				for(Object key : keys.keySet()){
					if(!key.equals(pk)) {
						k[i] = key;
						f[i] = keys.get(k[i]);
						v[i] = String.format("%s = ?", f[i]);
						t[i] = types.get(f[i]);
						i++;
					}
				}
				k[i] = pk;
				f[i] = keys.get(k[i]);
				t[i] = types.get(f[i]);
				i++;
			}

			String sql = String.format("UPDATE %s SET %s WHERE %s = ?", table, String.join(", ", v), pk);
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(sql);
				int n=0;
				for(Properties properties : data) {
					for(int i=0; i<k.length; i++) {
						Object val = properties.get(k[i]);
						switch(t[i]) {
						case STRING:
							if(val instanceof String) {
								statement.setString(i+1, (String)val);
							} else {
								statement.setObject(i+1, val);
							}
							break;
						case INTEGER:
							if(val instanceof Long) {
								statement.setLong(i+1, (Long)val);
							} else if(val instanceof String) {
								statement.setLong(i+1, Long.parseLong((String)val));
							} else {
								statement.setObject(i+1, val);
							}
							break;
						case FLOAT:
							if(val instanceof Float) {
								statement.setFloat(i+1, (Float)val);
							} else if(val instanceof String) {
								statement.setFloat(i+1, Float.parseFloat((String)val));
							} else {
								statement.setObject(i+1, val);
							}
							break;
						case BOOLEAN:
							if(val instanceof Boolean) {
								statement.setBoolean(i+1, (Boolean)val);
							} else if(val instanceof String) {
								statement.setBoolean(i+1, Boolean.parseBoolean((String)val));
							} else {
								statement.setObject(i+1, val);
							}
							break;
						case TIMESTAMP:
							if(val instanceof Timestamp) {
								statement.setTimestamp(i+1, (Timestamp)val);
							} else if(val instanceof String) {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm:ss", Locale.UK);
								sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
								statement.setTimestamp(i+1, Timestamp.valueOf((String)val));
							} else {
								statement.setObject(i+1, val);
							}
							break;
						default:
							throw new UnexpectedTypeException(f[i], t[i].toString());
						}
					}
				}
				statement.addBatch();
				n++;
				if(n%1000==0 || n==data.size()) {
					statement.executeBatch();
				}
			} catch (SQLException e) {
				throw e;
			} finally {
				if(statement!=null) statement.close();
			}
		}
	}

}
