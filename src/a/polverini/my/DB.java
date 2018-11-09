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

import a.polverini.my.exceptions.AlreadyConnectedException;
import a.polverini.my.exceptions.InvalidKeyException;
import a.polverini.my.exceptions.NotConnectedException;
import a.polverini.my.exceptions.UnexpectedTypeException;

public abstract class DB {

	/**
	 * @return the URL to connect to the database
	 */
	abstract public String getURL();

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

		
	private final String user;
	private final String pswd;
	private final String name;

	/**
	 * @return the database name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * constructor
	 * @param name the database name
	 * @param user the database user
	 * @param pswd the database password
	 */
	public DB(String name, String user, String pswd) {
		this.name = name;
		this.user = user;
		this.pswd = pswd;

	}

	/**
	 * @param table the table name
	 * @return the mapping between the field-name and the relevant type
	 * @throws SQLException
	 * @throws NotConnectedException
	 */
	public Map<String, Type> getTypes(String table) throws SQLException, NotConnectedException, UnexpectedTypeException {
		if(connection==null) {
			throw new NotConnectedException();
		}
		Map<String, Type> types = new HashMap<String, Type>();
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
		return types;
	}

	/**
	 * the connection
	 */
	protected Connection connection;
	protected String url;

	/**
	 * @return true if connected
	 * @throws SQLException
	 */
	public boolean isConnected() throws SQLException {
		return (connection!=null && !connection.isClosed());
	}

	/**
	 * @param url the URL to be used for the connection
	 * @throws SQLException
	 * @throws AlreadyConnectedException
	 */
	public void connect(String url) throws SQLException, AlreadyConnectedException {
		if(connection!=null) {
			throw new AlreadyConnectedException();
		}
		this.url = url;
		connection = DriverManager.getConnection(url, user, pswd);
	}
	
	/**
	 * connect
	 * @throws SQLException
	 * @throws AlreadyConnectedException
	 */
	public void connect() throws SQLException, AlreadyConnectedException {
		connect(url!=null?url:getURL());
	}

	/**
	 * disconnect
	 * @throws SQLException
	 * @throws NotConnectedException
	 */
	public void disconnect() throws SQLException, NotConnectedException {
		if(connection==null) {
			throw new NotConnectedException();
		}
		connection.close();
		connection = null;
	}

	/**
	 * @param table the table name
	 * @return the number of entries in the specified table
	 * @throws NotConnectedException
	 * @throws SQLException
	 */
	public int count(String table) throws NotConnectedException, SQLException {
		if(connection==null) {
			throw new NotConnectedException();
		}
		int count = 0;
		String sql = String.format("SELECT count(*) FROM %s", table);
		try (   Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sql)) {
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			throw e;
		}
		return count;
	}

	/**
	 * @param table the table name
	 * @return the next key value
	 * @throws NotConnectedException
	 * @throws SQLException
	 */
	public int pk(String table) throws NotConnectedException, SQLException {
		if(connection==null) {
			throw new NotConnectedException();
		}
		int pk = 0;
		String sql = String.format("SELECT max(pk) FROM %s", table);
		try (   Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sql)) {
			rs.next();
			pk = rs.getInt(1);
		} catch (SQLException e) {
			throw e;
		}
		return pk+1;
	}

	/**
	 * delete the content of the specified table and the linked entries in the other tables
	 * @param table the table name
	 * @throws NotConnectedException
	 * @throws SQLException
	 */
	public void truncate(String table) throws NotConnectedException, SQLException {
		if(connection==null) {
			throw new NotConnectedException();
		}
		String sql = String.format("TRUNCATE TABLE %s CASCADE;", table);
		try(Statement statement = connection.createStatement()) {
			statement.execute(sql);
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * query the content of the specified table
	 * @param table the table name
	 * @param keys the mapping key vs field
	 * @return the list of properties
	 * @throws NotConnectedException
	 * @throws SQLException
	 */
	public List<Properties> query(String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
		if(connection==null) {
			throw new NotConnectedException();
		}
		Object[] k = keys.keySet().toArray();
		String[] f = new String[k.length];
		for(int i=0; i<k.length; i++){
			f[i] = keys.get(k[i]);
		}
		List<Properties> list = new ArrayList<>();
		String sql = String.format("SELECT %s FROM %s", String.join(", ", f), table);
		try (   Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sql)) {
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
		}
		return list;
	}


	/**
	 * insert the entries in the specified table
	 * @param table the table name
	 * @param keys the mapping key vs field
	 * @param data the list of properties
	 * @throws NotConnectedException
	 * @throws SQLException
	 * @throws UnexpectedTypeException
	 */
	public void insert(String table, Map<String, String> keys, List<Properties> data) throws UnexpectedTypeException, NotConnectedException, SQLException {
		if(connection==null) {
			throw new NotConnectedException();
		}
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
		try ( PreparedStatement statement = connection.prepareStatement(sql);){
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
		}
	}
	
	/**
	 * update the entries in the specified table
	 * @param table the table name
	 * @param keys the mapping key vs field
	 * @param data the list of properties
	 * @param pk the table key
	 * @throws InvalidKeyException
	 * @throws NotConnectedException
	 * @throws SQLException
	 * @throws UnexpectedTypeException
	 */
	public void update(String table, Map<String, String> keys, List<Properties> data, String pk) throws InvalidKeyException, NotConnectedException, SQLException, UnexpectedTypeException {
		if(connection==null) {
			throw new NotConnectedException();
		}
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
		try ( PreparedStatement statement = connection.prepareStatement(sql);){
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
		}
	}
	
}
