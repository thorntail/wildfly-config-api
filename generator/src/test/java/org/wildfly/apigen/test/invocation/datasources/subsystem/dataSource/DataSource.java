package org.wildfly.apigen.test.invocation.datasources.subsystem.dataSource;

import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.ModelNodeBinding;
import java.util.Map;
import java.util.List;
import org.wildfly.apigen.invocation.Subresource;
import org.wildfly.apigen.invocation.ModelNodeSubresources;
import org.wildfly.apigen.test.invocation.datasources.subsystem.dataSource.connectionProperties.ConnectionProperties;

/**
 * A JDBC data-source configuration
 */
@Address("/subsystem=datasources/data-source=*")
public class DataSource<T extends DataSource> {

	private String key;
	private Integer allocationRetry;
	private Long allocationRetryWaitMillis;
	private Boolean allowMultipleUsers;
	private Boolean backgroundValidation;
	private Long backgroundValidationMillis;
	private Long blockingTimeoutWaitMillis;
	private String capacityDecrementerClass;
	private Map capacityDecrementerProperties;
	private String capacityIncrementerClass;
	private Map capacityIncrementerProperties;
	private String checkValidConnectionSql;
	private Boolean connectable;
	private String connectionListenerClass;
	private Map connectionListenerProperty;
	private Map connectionProperties;
	private String connectionUrl;
	private String datasourceClass;
	private String driverClass;
	private String driverName;
	private Boolean enabled;
	private Boolean enlistmentTrace;
	private String exceptionSorterClassName;
	private Map exceptionSorterProperties;
	private String flushStrategy;
	private Long idleTimeoutMinutes;
	private Integer initialPoolSize;
	private String jndiName;
	private Boolean jta;
	private Integer maxPoolSize;
	private String mcp;
	private Integer minPoolSize;
	private String newConnectionSql;
	private String password;
	private Boolean poolPrefill;
	private Boolean poolUseStrictMin;
	private Long preparedStatementsCacheSize;
	private Long queryTimeout;
	private String reauthPluginClassName;
	private Map reauthPluginProperties;
	private String securityDomain;
	private Boolean setTxQueryTimeout;
	private Boolean sharePreparedStatements;
	private Boolean spy;
	private String staleConnectionCheckerClassName;
	private Map staleConnectionCheckerProperties;
	private Boolean statisticsEnabled;
	private String trackStatements;
	private Boolean tracking;
	private String transactionIsolation;
	private String urlDelimiter;
	private String urlSelectorStrategyClassName;
	private Boolean useCcm;
	private Boolean useFastFail;
	private Boolean useJavaContext;
	private Long useTryLock;
	private String userName;
	private String validConnectionCheckerClassName;
	private Map validConnectionCheckerProperties;
	private Boolean validateOnMatch;
	private DataSourceResources subresources = new DataSourceResources();

	public DataSource(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	/**
	 * The allocation retry element indicates the number of times that allocating a connection should be tried before throwing an exception
	 */
	@ModelNodeBinding(detypedName = "allocation-retry")
	public Integer allocationRetry() {
		return this.allocationRetry;
	}

	/**
	 * The allocation retry element indicates the number of times that allocating a connection should be tried before throwing an exception
	 */
	@SuppressWarnings("unchecked")
	public T allocationRetry(Integer value) {
		this.allocationRetry = value;
		return (T) this;
	}

	/**
	 * The allocation retry wait millis element specifies the amount of time, in milliseconds, to wait between retrying to allocate a connection
	 */
	@ModelNodeBinding(detypedName = "allocation-retry-wait-millis")
	public Long allocationRetryWaitMillis() {
		return this.allocationRetryWaitMillis;
	}

	/**
	 * The allocation retry wait millis element specifies the amount of time, in milliseconds, to wait between retrying to allocate a connection
	 */
	@SuppressWarnings("unchecked")
	public T allocationRetryWaitMillis(Long value) {
		this.allocationRetryWaitMillis = value;
		return (T) this;
	}

	/**
	 * Specifies if multiple users will access the datasource through the getConnection(user, password) method and hence if the internal pool type should account for that
	 */
	@ModelNodeBinding(detypedName = "allow-multiple-users")
	public Boolean allowMultipleUsers() {
		return this.allowMultipleUsers;
	}

	/**
	 * Specifies if multiple users will access the datasource through the getConnection(user, password) method and hence if the internal pool type should account for that
	 */
	@SuppressWarnings("unchecked")
	public T allowMultipleUsers(Boolean value) {
		this.allowMultipleUsers = value;
		return (T) this;
	}

	/**
	 * An element to specify that connections should be validated on a background thread versus being validated prior to use. Changing this value can be done only on disabled datasource,  requires a server restart otherwise.
	 */
	@ModelNodeBinding(detypedName = "background-validation")
	public Boolean backgroundValidation() {
		return this.backgroundValidation;
	}

	/**
	 * An element to specify that connections should be validated on a background thread versus being validated prior to use. Changing this value can be done only on disabled datasource,  requires a server restart otherwise.
	 */
	@SuppressWarnings("unchecked")
	public T backgroundValidation(Boolean value) {
		this.backgroundValidation = value;
		return (T) this;
	}

	/**
	 * The background-validation-millis element specifies the amount of time, in milliseconds, that background validation will run. Changing this value can be done only on disabled datasource,  requires a server restart otherwise
	 */
	@ModelNodeBinding(detypedName = "background-validation-millis")
	public Long backgroundValidationMillis() {
		return this.backgroundValidationMillis;
	}

	/**
	 * The background-validation-millis element specifies the amount of time, in milliseconds, that background validation will run. Changing this value can be done only on disabled datasource,  requires a server restart otherwise
	 */
	@SuppressWarnings("unchecked")
	public T backgroundValidationMillis(Long value) {
		this.backgroundValidationMillis = value;
		return (T) this;
	}

	/**
	 * The blocking-timeout-millis element specifies the maximum time, in milliseconds, to block while waiting for a connection before throwing an exception. Note that this blocks only while waiting for locking a connection, and will never throw an exception if creating a new connection takes an inordinately long time
	 */
	@ModelNodeBinding(detypedName = "blocking-timeout-wait-millis")
	public Long blockingTimeoutWaitMillis() {
		return this.blockingTimeoutWaitMillis;
	}

	/**
	 * The blocking-timeout-millis element specifies the maximum time, in milliseconds, to block while waiting for a connection before throwing an exception. Note that this blocks only while waiting for locking a connection, and will never throw an exception if creating a new connection takes an inordinately long time
	 */
	@SuppressWarnings("unchecked")
	public T blockingTimeoutWaitMillis(Long value) {
		this.blockingTimeoutWaitMillis = value;
		return (T) this;
	}

	/**
	 * Class defining the policy for decrementing connections in the pool
	 */
	@ModelNodeBinding(detypedName = "capacity-decrementer-class")
	public String capacityDecrementerClass() {
		return this.capacityDecrementerClass;
	}

	/**
	 * Class defining the policy for decrementing connections in the pool
	 */
	@SuppressWarnings("unchecked")
	public T capacityDecrementerClass(String value) {
		this.capacityDecrementerClass = value;
		return (T) this;
	}

	/**
	 * Properties to be injected in class defining the policy for decrementing connections in the pool
	 */
	@ModelNodeBinding(detypedName = "capacity-decrementer-properties")
	public Map capacityDecrementerProperties() {
		return this.capacityDecrementerProperties;
	}

	/**
	 * Properties to be injected in class defining the policy for decrementing connections in the pool
	 */
	@SuppressWarnings("unchecked")
	public T capacityDecrementerProperties(Map value) {
		this.capacityDecrementerProperties = value;
		return (T) this;
	}

	/**
	 * Class defining the policy for incrementing connections in the pool
	 */
	@ModelNodeBinding(detypedName = "capacity-incrementer-class")
	public String capacityIncrementerClass() {
		return this.capacityIncrementerClass;
	}

	/**
	 * Class defining the policy for incrementing connections in the pool
	 */
	@SuppressWarnings("unchecked")
	public T capacityIncrementerClass(String value) {
		this.capacityIncrementerClass = value;
		return (T) this;
	}

	/**
	 * Properties to be injected in class defining the policy for incrementing connections in the pool
	 */
	@ModelNodeBinding(detypedName = "capacity-incrementer-properties")
	public Map capacityIncrementerProperties() {
		return this.capacityIncrementerProperties;
	}

	/**
	 * Properties to be injected in class defining the policy for incrementing connections in the pool
	 */
	@SuppressWarnings("unchecked")
	public T capacityIncrementerProperties(Map value) {
		this.capacityIncrementerProperties = value;
		return (T) this;
	}

	/**
	 * Specify an SQL statement to check validity of a pool connection. This may be called when managed connection is obtained from the pool
	 */
	@ModelNodeBinding(detypedName = "check-valid-connection-sql")
	public String checkValidConnectionSql() {
		return this.checkValidConnectionSql;
	}

	/**
	 * Specify an SQL statement to check validity of a pool connection. This may be called when managed connection is obtained from the pool
	 */
	@SuppressWarnings("unchecked")
	public T checkValidConnectionSql(String value) {
		this.checkValidConnectionSql = value;
		return (T) this;
	}

	/**
	 * Enable the use of CMR. This feature means that a local resource can reliably participate in an XA transaction.
	 */
	@ModelNodeBinding(detypedName = "connectable")
	public Boolean connectable() {
		return this.connectable;
	}

	/**
	 * Enable the use of CMR. This feature means that a local resource can reliably participate in an XA transaction.
	 */
	@SuppressWarnings("unchecked")
	public T connectable(Boolean value) {
		this.connectable = value;
		return (T) this;
	}

	/**
	 * Speciefies class name extending org.jboss.jca.adapters.jdbc.spi.listener.ConnectionListener that provides a possible to listen for connection activation and passivation in order to perform actions before the connection is returned to the application or returned to the pool.
	 */
	@ModelNodeBinding(detypedName = "connection-listener-class")
	public String connectionListenerClass() {
		return this.connectionListenerClass;
	}

	/**
	 * Speciefies class name extending org.jboss.jca.adapters.jdbc.spi.listener.ConnectionListener that provides a possible to listen for connection activation and passivation in order to perform actions before the connection is returned to the application or returned to the pool.
	 */
	@SuppressWarnings("unchecked")
	public T connectionListenerClass(String value) {
		this.connectionListenerClass = value;
		return (T) this;
	}

	/**
	 * Properties to be injected in class specidied in connection-listener-class
	 */
	@ModelNodeBinding(detypedName = "connection-listener-property")
	public Map connectionListenerProperty() {
		return this.connectionListenerProperty;
	}

	/**
	 * Properties to be injected in class specidied in connection-listener-class
	 */
	@SuppressWarnings("unchecked")
	public T connectionListenerProperty(Map value) {
		this.connectionListenerProperty = value;
		return (T) this;
	}

	/**
	 * The connection-properties element allows you to pass in arbitrary connection properties to the Driver.connect(url, props) method
	 */
	@ModelNodeBinding(detypedName = "connection-properties")
	public Map connectionProperties() {
		return this.connectionProperties;
	}

	/**
	 * The connection-properties element allows you to pass in arbitrary connection properties to the Driver.connect(url, props) method
	 */
	@SuppressWarnings("unchecked")
	public T connectionProperties(Map value) {
		this.connectionProperties = value;
		return (T) this;
	}

	/**
	 * The JDBC driver connection URL
	 */
	@ModelNodeBinding(detypedName = "connection-url")
	public String connectionUrl() {
		return this.connectionUrl;
	}

	/**
	 * The JDBC driver connection URL
	 */
	@SuppressWarnings("unchecked")
	public T connectionUrl(String value) {
		this.connectionUrl = value;
		return (T) this;
	}

	/**
	 * The fully qualified name of the JDBC datasource class
	 */
	@ModelNodeBinding(detypedName = "datasource-class")
	public String datasourceClass() {
		return this.datasourceClass;
	}

	/**
	 * The fully qualified name of the JDBC datasource class
	 */
	@SuppressWarnings("unchecked")
	public T datasourceClass(String value) {
		this.datasourceClass = value;
		return (T) this;
	}

	/**
	 * The fully qualified name of the JDBC driver class
	 */
	@ModelNodeBinding(detypedName = "driver-class")
	public String driverClass() {
		return this.driverClass;
	}

	/**
	 * The fully qualified name of the JDBC driver class
	 */
	@SuppressWarnings("unchecked")
	public T driverClass(String value) {
		this.driverClass = value;
		return (T) this;
	}

	/**
	 * Defines the JDBC driver the datasource should use. It is a symbolic name matching the the name of installed driver. In case the driver is deployed as jar, the name is the name of deployment unit
	 */
	@ModelNodeBinding(detypedName = "driver-name")
	public String driverName() {
		return this.driverName;
	}

	/**
	 * Defines the JDBC driver the datasource should use. It is a symbolic name matching the the name of installed driver. In case the driver is deployed as jar, the name is the name of deployment unit
	 */
	@SuppressWarnings("unchecked")
	public T driverName(String value) {
		this.driverName = value;
		return (T) this;
	}

	/**
	 * Specifies if the datasource should be enabled. Note this attribute will not be supported runtime in next versions.
	 */
	@ModelNodeBinding(detypedName = "enabled")
	public Boolean enabled() {
		return this.enabled;
	}

	/**
	 * Specifies if the datasource should be enabled. Note this attribute will not be supported runtime in next versions.
	 */
	@SuppressWarnings("unchecked")
	public T enabled(Boolean value) {
		this.enabled = value;
		return (T) this;
	}

	/**
	 * Defines if WildFly/IronJacamar should record enlistment traces
	 */
	@ModelNodeBinding(detypedName = "enlistment-trace")
	public Boolean enlistmentTrace() {
		return this.enlistmentTrace;
	}

	/**
	 * Defines if WildFly/IronJacamar should record enlistment traces
	 */
	@SuppressWarnings("unchecked")
	public T enlistmentTrace(Boolean value) {
		this.enlistmentTrace = value;
		return (T) this;
	}

	/**
	 * An org.jboss.jca.adapters.jdbc.ExceptionSorter that provides an isExceptionFatal(SQLException) method to validate if an exception should broadcast an error
	 */
	@ModelNodeBinding(detypedName = "exception-sorter-class-name")
	public String exceptionSorterClassName() {
		return this.exceptionSorterClassName;
	}

	/**
	 * An org.jboss.jca.adapters.jdbc.ExceptionSorter that provides an isExceptionFatal(SQLException) method to validate if an exception should broadcast an error
	 */
	@SuppressWarnings("unchecked")
	public T exceptionSorterClassName(String value) {
		this.exceptionSorterClassName = value;
		return (T) this;
	}

	/**
	 * The exception sorter properties
	 */
	@ModelNodeBinding(detypedName = "exception-sorter-properties")
	public Map exceptionSorterProperties() {
		return this.exceptionSorterProperties;
	}

	/**
	 * The exception sorter properties
	 */
	@SuppressWarnings("unchecked")
	public T exceptionSorterProperties(Map value) {
		this.exceptionSorterProperties = value;
		return (T) this;
	}

	/**
	 * Specifies how the pool should be flush in case of an error. Valid values are: FailingConnectionOnly (default), IdleConnections and EntirePool
	 */
	@ModelNodeBinding(detypedName = "flush-strategy")
	public String flushStrategy() {
		return this.flushStrategy;
	}

	/**
	 * Specifies how the pool should be flush in case of an error. Valid values are: FailingConnectionOnly (default), IdleConnections and EntirePool
	 */
	@SuppressWarnings("unchecked")
	public T flushStrategy(String value) {
		this.flushStrategy = value;
		return (T) this;
	}

	/**
	 * The idle-timeout-minutes elements specifies the maximum time, in minutes, a connection may be idle before being closed. The actual maximum time depends also on the IdleRemover scan time, which is half of the smallest idle-timeout-minutes value of any pool. Changing this value can be done only on disabled datasource, requires a server restart otherwise.
	 */
	@ModelNodeBinding(detypedName = "idle-timeout-minutes")
	public Long idleTimeoutMinutes() {
		return this.idleTimeoutMinutes;
	}

	/**
	 * The idle-timeout-minutes elements specifies the maximum time, in minutes, a connection may be idle before being closed. The actual maximum time depends also on the IdleRemover scan time, which is half of the smallest idle-timeout-minutes value of any pool. Changing this value can be done only on disabled datasource, requires a server restart otherwise.
	 */
	@SuppressWarnings("unchecked")
	public T idleTimeoutMinutes(Long value) {
		this.idleTimeoutMinutes = value;
		return (T) this;
	}

	/**
	 * The initial-pool-size element indicates the initial number of connections a pool should hold.
	 */
	@ModelNodeBinding(detypedName = "initial-pool-size")
	public Integer initialPoolSize() {
		return this.initialPoolSize;
	}

	/**
	 * The initial-pool-size element indicates the initial number of connections a pool should hold.
	 */
	@SuppressWarnings("unchecked")
	public T initialPoolSize(Integer value) {
		this.initialPoolSize = value;
		return (T) this;
	}

	/**
	 * Specifies the JNDI name for the datasource
	 */
	@ModelNodeBinding(detypedName = "jndi-name")
	public String jndiName() {
		return this.jndiName;
	}

	/**
	 * Specifies the JNDI name for the datasource
	 */
	@SuppressWarnings("unchecked")
	public T jndiName(String value) {
		this.jndiName = value;
		return (T) this;
	}

	/**
	 * Enable JTA integration
	 */
	@ModelNodeBinding(detypedName = "jta")
	public Boolean jta() {
		return this.jta;
	}

	/**
	 * Enable JTA integration
	 */
	@SuppressWarnings("unchecked")
	public T jta(Boolean value) {
		this.jta = value;
		return (T) this;
	}

	/**
	 * The max-pool-size element specifies the maximum number of connections for a pool. No more connections will be created in each sub-pool
	 */
	@ModelNodeBinding(detypedName = "max-pool-size")
	public Integer maxPoolSize() {
		return this.maxPoolSize;
	}

	/**
	 * The max-pool-size element specifies the maximum number of connections for a pool. No more connections will be created in each sub-pool
	 */
	@SuppressWarnings("unchecked")
	public T maxPoolSize(Integer value) {
		this.maxPoolSize = value;
		return (T) this;
	}

	/**
	 * Defines the ManagedConnectionPool implementation, f.ex. org.jboss.jca.core.connectionmanager.pool.mcp.SemaphoreArrayListManagedConnectionPool
	 */
	@ModelNodeBinding(detypedName = "mcp")
	public String mcp() {
		return this.mcp;
	}

	/**
	 * Defines the ManagedConnectionPool implementation, f.ex. org.jboss.jca.core.connectionmanager.pool.mcp.SemaphoreArrayListManagedConnectionPool
	 */
	@SuppressWarnings("unchecked")
	public T mcp(String value) {
		this.mcp = value;
		return (T) this;
	}

	/**
	 * The min-pool-size element specifies the minimum number of connections for a pool
	 */
	@ModelNodeBinding(detypedName = "min-pool-size")
	public Integer minPoolSize() {
		return this.minPoolSize;
	}

	/**
	 * The min-pool-size element specifies the minimum number of connections for a pool
	 */
	@SuppressWarnings("unchecked")
	public T minPoolSize(Integer value) {
		this.minPoolSize = value;
		return (T) this;
	}

	/**
	 * Specifies an SQL statement to execute whenever a connection is added to the connection pool
	 */
	@ModelNodeBinding(detypedName = "new-connection-sql")
	public String newConnectionSql() {
		return this.newConnectionSql;
	}

	/**
	 * Specifies an SQL statement to execute whenever a connection is added to the connection pool
	 */
	@SuppressWarnings("unchecked")
	public T newConnectionSql(String value) {
		this.newConnectionSql = value;
		return (T) this;
	}

	/**
	 * Specifies the password used when creating a new connection
	 */
	@ModelNodeBinding(detypedName = "password")
	public String password() {
		return this.password;
	}

	/**
	 * Specifies the password used when creating a new connection
	 */
	@SuppressWarnings("unchecked")
	public T password(String value) {
		this.password = value;
		return (T) this;
	}

	/**
	 * Should the pool be prefilled. Changing this value can be done only on disabled datasource, requires a server restart otherwise.
	 */
	@ModelNodeBinding(detypedName = "pool-prefill")
	public Boolean poolPrefill() {
		return this.poolPrefill;
	}

	/**
	 * Should the pool be prefilled. Changing this value can be done only on disabled datasource, requires a server restart otherwise.
	 */
	@SuppressWarnings("unchecked")
	public T poolPrefill(Boolean value) {
		this.poolPrefill = value;
		return (T) this;
	}

	/**
	 * Specifies if the min-pool-size should be considered strictly
	 */
	@ModelNodeBinding(detypedName = "pool-use-strict-min")
	public Boolean poolUseStrictMin() {
		return this.poolUseStrictMin;
	}

	/**
	 * Specifies if the min-pool-size should be considered strictly
	 */
	@SuppressWarnings("unchecked")
	public T poolUseStrictMin(Boolean value) {
		this.poolUseStrictMin = value;
		return (T) this;
	}

	/**
	 * The number of prepared statements per connection in an LRU cache
	 */
	@ModelNodeBinding(detypedName = "prepared-statements-cache-size")
	public Long preparedStatementsCacheSize() {
		return this.preparedStatementsCacheSize;
	}

	/**
	 * The number of prepared statements per connection in an LRU cache
	 */
	@SuppressWarnings("unchecked")
	public T preparedStatementsCacheSize(Long value) {
		this.preparedStatementsCacheSize = value;
		return (T) this;
	}

	/**
	 * Any configured query timeout in seconds. If not provided no timeout will be set
	 */
	@ModelNodeBinding(detypedName = "query-timeout")
	public Long queryTimeout() {
		return this.queryTimeout;
	}

	/**
	 * Any configured query timeout in seconds. If not provided no timeout will be set
	 */
	@SuppressWarnings("unchecked")
	public T queryTimeout(Long value) {
		this.queryTimeout = value;
		return (T) this;
	}

	/**
	 * The fully qualified class name of the reauthentication plugin implementation
	 */
	@ModelNodeBinding(detypedName = "reauth-plugin-class-name")
	public String reauthPluginClassName() {
		return this.reauthPluginClassName;
	}

	/**
	 * The fully qualified class name of the reauthentication plugin implementation
	 */
	@SuppressWarnings("unchecked")
	public T reauthPluginClassName(String value) {
		this.reauthPluginClassName = value;
		return (T) this;
	}

	/**
	 * The properties for the reauthentication plugin
	 */
	@ModelNodeBinding(detypedName = "reauth-plugin-properties")
	public Map reauthPluginProperties() {
		return this.reauthPluginProperties;
	}

	/**
	 * The properties for the reauthentication plugin
	 */
	@SuppressWarnings("unchecked")
	public T reauthPluginProperties(Map value) {
		this.reauthPluginProperties = value;
		return (T) this;
	}

	/**
	 * Specifies the security domain which defines the javax.security.auth.Subject that are used to distinguish connections in the pool
	 */
	@ModelNodeBinding(detypedName = "security-domain")
	public String securityDomain() {
		return this.securityDomain;
	}

	/**
	 * Specifies the security domain which defines the javax.security.auth.Subject that are used to distinguish connections in the pool
	 */
	@SuppressWarnings("unchecked")
	public T securityDomain(String value) {
		this.securityDomain = value;
		return (T) this;
	}

	/**
	 * Whether to set the query timeout based on the time remaining until transaction timeout. Any configured query timeout will be used if there is no transaction
	 */
	@ModelNodeBinding(detypedName = "set-tx-query-timeout")
	public Boolean setTxQueryTimeout() {
		return this.setTxQueryTimeout;
	}

	/**
	 * Whether to set the query timeout based on the time remaining until transaction timeout. Any configured query timeout will be used if there is no transaction
	 */
	@SuppressWarnings("unchecked")
	public T setTxQueryTimeout(Boolean value) {
		this.setTxQueryTimeout = value;
		return (T) this;
	}

	/**
	 * Whether to share prepared statements, i.e. whether asking for same statement twice without closing uses the same underlying prepared statement
	 */
	@ModelNodeBinding(detypedName = "share-prepared-statements")
	public Boolean sharePreparedStatements() {
		return this.sharePreparedStatements;
	}

	/**
	 * Whether to share prepared statements, i.e. whether asking for same statement twice without closing uses the same underlying prepared statement
	 */
	@SuppressWarnings("unchecked")
	public T sharePreparedStatements(Boolean value) {
		this.sharePreparedStatements = value;
		return (T) this;
	}

	/**
	 * Enable spying of SQL statements
	 */
	@ModelNodeBinding(detypedName = "spy")
	public Boolean spy() {
		return this.spy;
	}

	/**
	 * Enable spying of SQL statements
	 */
	@SuppressWarnings("unchecked")
	public T spy(Boolean value) {
		this.spy = value;
		return (T) this;
	}

	/**
	 * An org.jboss.jca.adapters.jdbc.StaleConnectionChecker that provides an isStaleConnection(SQLException) method which if it returns true will wrap the exception in an org.jboss.jca.adapters.jdbc.StaleConnectionException
	 */
	@ModelNodeBinding(detypedName = "stale-connection-checker-class-name")
	public String staleConnectionCheckerClassName() {
		return this.staleConnectionCheckerClassName;
	}

	/**
	 * An org.jboss.jca.adapters.jdbc.StaleConnectionChecker that provides an isStaleConnection(SQLException) method which if it returns true will wrap the exception in an org.jboss.jca.adapters.jdbc.StaleConnectionException
	 */
	@SuppressWarnings("unchecked")
	public T staleConnectionCheckerClassName(String value) {
		this.staleConnectionCheckerClassName = value;
		return (T) this;
	}

	/**
	 * The stale connection checker properties
	 */
	@ModelNodeBinding(detypedName = "stale-connection-checker-properties")
	public Map staleConnectionCheckerProperties() {
		return this.staleConnectionCheckerProperties;
	}

	/**
	 * The stale connection checker properties
	 */
	@SuppressWarnings("unchecked")
	public T staleConnectionCheckerProperties(Map value) {
		this.staleConnectionCheckerProperties = value;
		return (T) this;
	}

	/**
	 * define if runtime statistics is enabled or not.
	 */
	@ModelNodeBinding(detypedName = "statistics-enabled")
	public Boolean statisticsEnabled() {
		return this.statisticsEnabled;
	}

	/**
	 * define if runtime statistics is enabled or not.
	 */
	@SuppressWarnings("unchecked")
	public T statisticsEnabled(Boolean value) {
		this.statisticsEnabled = value;
		return (T) this;
	}

	/**
	 * Whether to check for unclosed statements when a connection is returned to the pool, result sets are closed, a statement is closed or return to the prepared statement cache. Valid values are: "false" - do not track statements, "true" - track statements and result sets and warn when they are not closed, "nowarn" - track statements but do not warn about them being unclosed
	 */
	@ModelNodeBinding(detypedName = "track-statements")
	public String trackStatements() {
		return this.trackStatements;
	}

	/**
	 * Whether to check for unclosed statements when a connection is returned to the pool, result sets are closed, a statement is closed or return to the prepared statement cache. Valid values are: "false" - do not track statements, "true" - track statements and result sets and warn when they are not closed, "nowarn" - track statements but do not warn about them being unclosed
	 */
	@SuppressWarnings("unchecked")
	public T trackStatements(String value) {
		this.trackStatements = value;
		return (T) this;
	}

	/**
	 * Defines if IronJacamar should track connection handles across transaction boundaries
	 */
	@ModelNodeBinding(detypedName = "tracking")
	public Boolean tracking() {
		return this.tracking;
	}

	/**
	 * Defines if IronJacamar should track connection handles across transaction boundaries
	 */
	@SuppressWarnings("unchecked")
	public T tracking(Boolean value) {
		this.tracking = value;
		return (T) this;
	}

	/**
	 * Set the java.sql.Connection transaction isolation level. Valid values are: TRANSACTION_READ_UNCOMMITTED, TRANSACTION_READ_COMMITTED, TRANSACTION_REPEATABLE_READ, TRANSACTION_SERIALIZABLE and TRANSACTION_NONE
	 */
	@ModelNodeBinding(detypedName = "transaction-isolation")
	public String transactionIsolation() {
		return this.transactionIsolation;
	}

	/**
	 * Set the java.sql.Connection transaction isolation level. Valid values are: TRANSACTION_READ_UNCOMMITTED, TRANSACTION_READ_COMMITTED, TRANSACTION_REPEATABLE_READ, TRANSACTION_SERIALIZABLE and TRANSACTION_NONE
	 */
	@SuppressWarnings("unchecked")
	public T transactionIsolation(String value) {
		this.transactionIsolation = value;
		return (T) this;
	}

	/**
	 * Specifies the delimiter for URLs in connection-url for HA datasources
	 */
	@ModelNodeBinding(detypedName = "url-delimiter")
	public String urlDelimiter() {
		return this.urlDelimiter;
	}

	/**
	 * Specifies the delimiter for URLs in connection-url for HA datasources
	 */
	@SuppressWarnings("unchecked")
	public T urlDelimiter(String value) {
		this.urlDelimiter = value;
		return (T) this;
	}

	/**
	 * A class that implements org.jboss.jca.adapters.jdbc.URLSelectorStrategy
	 */
	@ModelNodeBinding(detypedName = "url-selector-strategy-class-name")
	public String urlSelectorStrategyClassName() {
		return this.urlSelectorStrategyClassName;
	}

	/**
	 * A class that implements org.jboss.jca.adapters.jdbc.URLSelectorStrategy
	 */
	@SuppressWarnings("unchecked")
	public T urlSelectorStrategyClassName(String value) {
		this.urlSelectorStrategyClassName = value;
		return (T) this;
	}

	/**
	 * Enable the use of a cached connection manager
	 */
	@ModelNodeBinding(detypedName = "use-ccm")
	public Boolean useCcm() {
		return this.useCcm;
	}

	/**
	 * Enable the use of a cached connection manager
	 */
	@SuppressWarnings("unchecked")
	public T useCcm(Boolean value) {
		this.useCcm = value;
		return (T) this;
	}

	/**
	 * Whether to fail a connection allocation on the first try if it is invalid (true) or keep trying until the pool is exhausted of all potential connections (false)
	 */
	@ModelNodeBinding(detypedName = "use-fast-fail")
	public Boolean useFastFail() {
		return this.useFastFail;
	}

	/**
	 * Whether to fail a connection allocation on the first try if it is invalid (true) or keep trying until the pool is exhausted of all potential connections (false)
	 */
	@SuppressWarnings("unchecked")
	public T useFastFail(Boolean value) {
		this.useFastFail = value;
		return (T) this;
	}

	/**
	 * Setting this to false will bind the datasource into global JNDI
	 */
	@ModelNodeBinding(detypedName = "use-java-context")
	public Boolean useJavaContext() {
		return this.useJavaContext;
	}

	/**
	 * Setting this to false will bind the datasource into global JNDI
	 */
	@SuppressWarnings("unchecked")
	public T useJavaContext(Boolean value) {
		this.useJavaContext = value;
		return (T) this;
	}

	/**
	 * Any configured timeout for internal locks on the resource adapter objects in seconds
	 */
	@ModelNodeBinding(detypedName = "use-try-lock")
	public Long useTryLock() {
		return this.useTryLock;
	}

	/**
	 * Any configured timeout for internal locks on the resource adapter objects in seconds
	 */
	@SuppressWarnings("unchecked")
	public T useTryLock(Long value) {
		this.useTryLock = value;
		return (T) this;
	}

	/**
	 * Specify the user name used when creating a new connection
	 */
	@ModelNodeBinding(detypedName = "user-name")
	public String userName() {
		return this.userName;
	}

	/**
	 * Specify the user name used when creating a new connection
	 */
	@SuppressWarnings("unchecked")
	public T userName(String value) {
		this.userName = value;
		return (T) this;
	}

	/**
	 * An org.jboss.jca.adapters.jdbc.ValidConnectionChecker that provides an isValidConnection(Connection) method to validate a connection. If an exception is returned that means the connection is invalid. This overrides the check-valid-connection-sql element
	 */
	@ModelNodeBinding(detypedName = "valid-connection-checker-class-name")
	public String validConnectionCheckerClassName() {
		return this.validConnectionCheckerClassName;
	}

	/**
	 * An org.jboss.jca.adapters.jdbc.ValidConnectionChecker that provides an isValidConnection(Connection) method to validate a connection. If an exception is returned that means the connection is invalid. This overrides the check-valid-connection-sql element
	 */
	@SuppressWarnings("unchecked")
	public T validConnectionCheckerClassName(String value) {
		this.validConnectionCheckerClassName = value;
		return (T) this;
	}

	/**
	 * The valid connection checker properties
	 */
	@ModelNodeBinding(detypedName = "valid-connection-checker-properties")
	public Map validConnectionCheckerProperties() {
		return this.validConnectionCheckerProperties;
	}

	/**
	 * The valid connection checker properties
	 */
	@SuppressWarnings("unchecked")
	public T validConnectionCheckerProperties(Map value) {
		this.validConnectionCheckerProperties = value;
		return (T) this;
	}

	/**
	 * The validate-on-match element specifies if connection validation should be done when a connection factory attempts to match a managed connection. This is typically exclusive to the use of background validation
	 */
	@ModelNodeBinding(detypedName = "validate-on-match")
	public Boolean validateOnMatch() {
		return this.validateOnMatch;
	}

	/**
	 * The validate-on-match element specifies if connection validation should be done when a connection factory attempts to match a managed connection. This is typically exclusive to the use of background validation
	 */
	@SuppressWarnings("unchecked")
	public T validateOnMatch(Boolean value) {
		this.validateOnMatch = value;
		return (T) this;
	}

	public DataSourceResources subresources() {
		return this.subresources;
	}

	/**
	 * Add all ConnectionProperties objects to this subresource
	 * @return this
	 * @param value List of ConnectionProperties objects.
	 */
	@SuppressWarnings("unchecked")
	public T connectionProperties(List<ConnectionProperties> value) {
		this.subresources.connectionProperties.addAll(value);
		return (T) this;
	}

	/**
	 * Add the ConnectionProperties object to the list of subresources
	 * @param value The ConnectionProperties to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T connectionProperties(ConnectionProperties value) {
		this.subresources.connectionProperties.add(value);
		return (T) this;
	}

	/**
	 * Child mutators for DataSource
	 */
	@ModelNodeSubresources
	public class DataSourceResources {
		/**
		 * The connection-properties element allows you to pass in arbitrary connection properties to the Driver.connect(url, props) method
		 */
		private List<ConnectionProperties> connectionProperties = new java.util.ArrayList<>();

		/**
		 * Get the list of ConnectionProperties resources
		 * @return the list of resources
		 */
		@Subresource
		public List<ConnectionProperties> connectionProperties() {
			return this.connectionProperties;
		}
	}
}