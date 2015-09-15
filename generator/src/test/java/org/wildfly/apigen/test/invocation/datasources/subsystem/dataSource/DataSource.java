package org.wildfly.apigen.test.invocation.datasources.subsystem.dataSource;

import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.Binding;
import java.util.Map;
import java.util.List;
import org.wildfly.apigen.invocation.Subresource;
import org.wildfly.apigen.test.invocation.datasources.subsystem.dataSource.connectionProperties.ConnectionProperties;
/**
 * A JDBC data-source configuration
 */
@Address("/subsystem=datasources/data-source=*")
public class DataSource {

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
	private String connectionProperties;
	private String connectionUrl;
	private String datasourceClass;
	private String driverClass;
	private String driverName;
	private Boolean enabled;
	private String exceptionSorterClassName;
	private Map exceptionSorterProperties;
	private String flushStrategy;
	private Long idleTimeoutMinutes;
	private Integer initialPoolSize;
	private String jndiName;
	private Boolean jta;
	private Integer maxPoolSize;
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
	@Binding(detypedName = "allocation-retry")
	public Integer allocationRetry() {
		return this.allocationRetry;
	}

	/**
	 * The allocation retry element indicates the number of times that allocating a connection should be tried before throwing an exception
	 */
	public DataSource allocationRetry(Integer value) {
		this.allocationRetry = value;
		return this;
	}

	/**
	 * The allocation retry wait millis element specifies the amount of time, in milliseconds, to wait between retrying to allocate a connection
	 */
	@Binding(detypedName = "allocation-retry-wait-millis")
	public Long allocationRetryWaitMillis() {
		return this.allocationRetryWaitMillis;
	}

	/**
	 * The allocation retry wait millis element specifies the amount of time, in milliseconds, to wait between retrying to allocate a connection
	 */
	public DataSource allocationRetryWaitMillis(Long value) {
		this.allocationRetryWaitMillis = value;
		return this;
	}

	/**
	 * Specifies if multiple users will access the datasource through the getConnection(user, password) method and hence if the internal pool type should account for that
	 */
	@Binding(detypedName = "allow-multiple-users")
	public Boolean allowMultipleUsers() {
		return this.allowMultipleUsers;
	}

	/**
	 * Specifies if multiple users will access the datasource through the getConnection(user, password) method and hence if the internal pool type should account for that
	 */
	public DataSource allowMultipleUsers(Boolean value) {
		this.allowMultipleUsers = value;
		return this;
	}

	/**
	 * An element to specify that connections should be validated on a background thread versus being validated prior to use. Changing this value can be done only on disabled datasource,  requires a server restart otherwise.
	 */
	@Binding(detypedName = "background-validation")
	public Boolean backgroundValidation() {
		return this.backgroundValidation;
	}

	/**
	 * An element to specify that connections should be validated on a background thread versus being validated prior to use. Changing this value can be done only on disabled datasource,  requires a server restart otherwise.
	 */
	public DataSource backgroundValidation(Boolean value) {
		this.backgroundValidation = value;
		return this;
	}

	/**
	 * The background-validation-millis element specifies the amount of time, in milliseconds, that background validation will run. Changing this value can be done only on disabled datasource,  requires a server restart otherwise
	 */
	@Binding(detypedName = "background-validation-millis")
	public Long backgroundValidationMillis() {
		return this.backgroundValidationMillis;
	}

	/**
	 * The background-validation-millis element specifies the amount of time, in milliseconds, that background validation will run. Changing this value can be done only on disabled datasource,  requires a server restart otherwise
	 */
	public DataSource backgroundValidationMillis(Long value) {
		this.backgroundValidationMillis = value;
		return this;
	}

	/**
	 * The blocking-timeout-millis element specifies the maximum time, in milliseconds, to block while waiting for a connection before throwing an exception. Note that this blocks only while waiting for locking a connection, and will never throw an exception if creating a new connection takes an inordinately long time
	 */
	@Binding(detypedName = "blocking-timeout-wait-millis")
	public Long blockingTimeoutWaitMillis() {
		return this.blockingTimeoutWaitMillis;
	}

	/**
	 * The blocking-timeout-millis element specifies the maximum time, in milliseconds, to block while waiting for a connection before throwing an exception. Note that this blocks only while waiting for locking a connection, and will never throw an exception if creating a new connection takes an inordinately long time
	 */
	public DataSource blockingTimeoutWaitMillis(Long value) {
		this.blockingTimeoutWaitMillis = value;
		return this;
	}

	/**
	 * Class defining the policy for decrementing connections in the pool
	 */
	@Binding(detypedName = "capacity-decrementer-class")
	public String capacityDecrementerClass() {
		return this.capacityDecrementerClass;
	}

	/**
	 * Class defining the policy for decrementing connections in the pool
	 */
	public DataSource capacityDecrementerClass(String value) {
		this.capacityDecrementerClass = value;
		return this;
	}

	/**
	 * Properties to be injected in class defining the policy for decrementing connections in the pool
	 */
	@Binding(detypedName = "capacity-decrementer-properties")
	public Map capacityDecrementerProperties() {
		return this.capacityDecrementerProperties;
	}

	/**
	 * Properties to be injected in class defining the policy for decrementing connections in the pool
	 */
	public DataSource capacityDecrementerProperties(Map value) {
		this.capacityDecrementerProperties = value;
		return this;
	}

	/**
	 * Class defining the policy for incrementing connections in the pool
	 */
	@Binding(detypedName = "capacity-incrementer-class")
	public String capacityIncrementerClass() {
		return this.capacityIncrementerClass;
	}

	/**
	 * Class defining the policy for incrementing connections in the pool
	 */
	public DataSource capacityIncrementerClass(String value) {
		this.capacityIncrementerClass = value;
		return this;
	}

	/**
	 * Properties to be injected in class defining the policy for incrementing connections in the pool
	 */
	@Binding(detypedName = "capacity-incrementer-properties")
	public Map capacityIncrementerProperties() {
		return this.capacityIncrementerProperties;
	}

	/**
	 * Properties to be injected in class defining the policy for incrementing connections in the pool
	 */
	public DataSource capacityIncrementerProperties(Map value) {
		this.capacityIncrementerProperties = value;
		return this;
	}

	/**
	 * Specify an SQL statement to check validity of a pool connection. This may be called when managed connection is obtained from the pool
	 */
	@Binding(detypedName = "check-valid-connection-sql")
	public String checkValidConnectionSql() {
		return this.checkValidConnectionSql;
	}

	/**
	 * Specify an SQL statement to check validity of a pool connection. This may be called when managed connection is obtained from the pool
	 */
	public DataSource checkValidConnectionSql(String value) {
		this.checkValidConnectionSql = value;
		return this;
	}

	/**
	 * Enable the use of CMR. This feature means that a local resource can reliably participate in an XA transaction.
	 */
	@Binding(detypedName = "connectable")
	public Boolean connectable() {
		return this.connectable;
	}

	/**
	 * Enable the use of CMR. This feature means that a local resource can reliably participate in an XA transaction.
	 */
	public DataSource connectable(Boolean value) {
		this.connectable = value;
		return this;
	}

	/**
	 * Speciefies class name extending org.jboss.jca.adapters.jdbc.spi.listener.ConnectionListener that provides a possible to listen for connection activation and passivation in order to perform actions before the connection is returned to the application or returned to the pool.
	 */
	@Binding(detypedName = "connection-listener-class")
	public String connectionListenerClass() {
		return this.connectionListenerClass;
	}

	/**
	 * Speciefies class name extending org.jboss.jca.adapters.jdbc.spi.listener.ConnectionListener that provides a possible to listen for connection activation and passivation in order to perform actions before the connection is returned to the application or returned to the pool.
	 */
	public DataSource connectionListenerClass(String value) {
		this.connectionListenerClass = value;
		return this;
	}

	/**
	 * Properties to be injected in class specidied in connection-listener-class
	 */
	@Binding(detypedName = "connection-listener-property")
	public Map connectionListenerProperty() {
		return this.connectionListenerProperty;
	}

	/**
	 * Properties to be injected in class specidied in connection-listener-class
	 */
	public DataSource connectionListenerProperty(Map value) {
		this.connectionListenerProperty = value;
		return this;
	}

	/**
	 * The connection-properties element allows you to pass in arbitrary connection properties to the Driver.connect(url, props) method
	 */
	@Binding(detypedName = "connection-properties")
	public String connectionProperties() {
		return this.connectionProperties;
	}

	/**
	 * The connection-properties element allows you to pass in arbitrary connection properties to the Driver.connect(url, props) method
	 */
	public DataSource connectionProperties(String value) {
		this.connectionProperties = value;
		return this;
	}

	/**
	 * The JDBC driver connection URL
	 */
	@Binding(detypedName = "connection-url")
	public String connectionUrl() {
		return this.connectionUrl;
	}

	/**
	 * The JDBC driver connection URL
	 */
	public DataSource connectionUrl(String value) {
		this.connectionUrl = value;
		return this;
	}

	/**
	 * The fully qualified name of the JDBC datasource class
	 */
	@Binding(detypedName = "datasource-class")
	public String datasourceClass() {
		return this.datasourceClass;
	}

	/**
	 * The fully qualified name of the JDBC datasource class
	 */
	public DataSource datasourceClass(String value) {
		this.datasourceClass = value;
		return this;
	}

	/**
	 * The fully qualified name of the JDBC driver class
	 */
	@Binding(detypedName = "driver-class")
	public String driverClass() {
		return this.driverClass;
	}

	/**
	 * The fully qualified name of the JDBC driver class
	 */
	public DataSource driverClass(String value) {
		this.driverClass = value;
		return this;
	}

	/**
	 * Defines the JDBC driver the datasource should use. It is a symbolic name matching the the name of installed driver. In case the driver is deployed as jar, the name is the name of deployment unit
	 */
	@Binding(detypedName = "driver-name")
	public String driverName() {
		return this.driverName;
	}

	/**
	 * Defines the JDBC driver the datasource should use. It is a symbolic name matching the the name of installed driver. In case the driver is deployed as jar, the name is the name of deployment unit
	 */
	public DataSource driverName(String value) {
		this.driverName = value;
		return this;
	}

	/**
	 * Specifies if the datasource should be enabled. Note this attribute will not be supported runtime in next versions.
	 */
	@Binding(detypedName = "enabled")
	public Boolean enabled() {
		return this.enabled;
	}

	/**
	 * Specifies if the datasource should be enabled. Note this attribute will not be supported runtime in next versions.
	 */
	public DataSource enabled(Boolean value) {
		this.enabled = value;
		return this;
	}

	/**
	 * An org.jboss.jca.adapters.jdbc.ExceptionSorter that provides an isExceptionFatal(SQLException) method to validate if an exception should broadcast an error
	 */
	@Binding(detypedName = "exception-sorter-class-name")
	public String exceptionSorterClassName() {
		return this.exceptionSorterClassName;
	}

	/**
	 * An org.jboss.jca.adapters.jdbc.ExceptionSorter that provides an isExceptionFatal(SQLException) method to validate if an exception should broadcast an error
	 */
	public DataSource exceptionSorterClassName(String value) {
		this.exceptionSorterClassName = value;
		return this;
	}

	/**
	 * The exception sorter properties
	 */
	@Binding(detypedName = "exception-sorter-properties")
	public Map exceptionSorterProperties() {
		return this.exceptionSorterProperties;
	}

	/**
	 * The exception sorter properties
	 */
	public DataSource exceptionSorterProperties(Map value) {
		this.exceptionSorterProperties = value;
		return this;
	}

	/**
	 * Specifies how the pool should be flush in case of an error. Valid values are: FailingConnectionOnly (default), IdleConnections and EntirePool
	 */
	@Binding(detypedName = "flush-strategy")
	public String flushStrategy() {
		return this.flushStrategy;
	}

	/**
	 * Specifies how the pool should be flush in case of an error. Valid values are: FailingConnectionOnly (default), IdleConnections and EntirePool
	 */
	public DataSource flushStrategy(String value) {
		this.flushStrategy = value;
		return this;
	}

	/**
	 * The idle-timeout-minutes elements specifies the maximum time, in minutes, a connection may be idle before being closed. The actual maximum time depends also on the IdleRemover scan time, which is half of the smallest idle-timeout-minutes value of any pool. Changing this value can be done only on disabled datasource, requires a server restart otherwise.
	 */
	@Binding(detypedName = "idle-timeout-minutes")
	public Long idleTimeoutMinutes() {
		return this.idleTimeoutMinutes;
	}

	/**
	 * The idle-timeout-minutes elements specifies the maximum time, in minutes, a connection may be idle before being closed. The actual maximum time depends also on the IdleRemover scan time, which is half of the smallest idle-timeout-minutes value of any pool. Changing this value can be done only on disabled datasource, requires a server restart otherwise.
	 */
	public DataSource idleTimeoutMinutes(Long value) {
		this.idleTimeoutMinutes = value;
		return this;
	}

	/**
	 * The initial-pool-size element indicates the initial number of connections a pool should hold.
	 */
	@Binding(detypedName = "initial-pool-size")
	public Integer initialPoolSize() {
		return this.initialPoolSize;
	}

	/**
	 * The initial-pool-size element indicates the initial number of connections a pool should hold.
	 */
	public DataSource initialPoolSize(Integer value) {
		this.initialPoolSize = value;
		return this;
	}

	/**
	 * Specifies the JNDI name for the datasource
	 */
	@Binding(detypedName = "jndi-name")
	public String jndiName() {
		return this.jndiName;
	}

	/**
	 * Specifies the JNDI name for the datasource
	 */
	public DataSource jndiName(String value) {
		this.jndiName = value;
		return this;
	}

	/**
	 * Enable JTA integration
	 */
	@Binding(detypedName = "jta")
	public Boolean jta() {
		return this.jta;
	}

	/**
	 * Enable JTA integration
	 */
	public DataSource jta(Boolean value) {
		this.jta = value;
		return this;
	}

	/**
	 * The max-pool-size element specifies the maximum number of connections for a pool. No more connections will be created in each sub-pool
	 */
	@Binding(detypedName = "max-pool-size")
	public Integer maxPoolSize() {
		return this.maxPoolSize;
	}

	/**
	 * The max-pool-size element specifies the maximum number of connections for a pool. No more connections will be created in each sub-pool
	 */
	public DataSource maxPoolSize(Integer value) {
		this.maxPoolSize = value;
		return this;
	}

	/**
	 * The min-pool-size element specifies the minimum number of connections for a pool
	 */
	@Binding(detypedName = "min-pool-size")
	public Integer minPoolSize() {
		return this.minPoolSize;
	}

	/**
	 * The min-pool-size element specifies the minimum number of connections for a pool
	 */
	public DataSource minPoolSize(Integer value) {
		this.minPoolSize = value;
		return this;
	}

	/**
	 * Specifies an SQL statement to execute whenever a connection is added to the connection pool
	 */
	@Binding(detypedName = "new-connection-sql")
	public String newConnectionSql() {
		return this.newConnectionSql;
	}

	/**
	 * Specifies an SQL statement to execute whenever a connection is added to the connection pool
	 */
	public DataSource newConnectionSql(String value) {
		this.newConnectionSql = value;
		return this;
	}

	/**
	 * Specifies the password used when creating a new connection
	 */
	@Binding(detypedName = "password")
	public String password() {
		return this.password;
	}

	/**
	 * Specifies the password used when creating a new connection
	 */
	public DataSource password(String value) {
		this.password = value;
		return this;
	}

	/**
	 * Should the pool be prefilled. Changing this value can be done only on disabled datasource, requires a server restart otherwise.
	 */
	@Binding(detypedName = "pool-prefill")
	public Boolean poolPrefill() {
		return this.poolPrefill;
	}

	/**
	 * Should the pool be prefilled. Changing this value can be done only on disabled datasource, requires a server restart otherwise.
	 */
	public DataSource poolPrefill(Boolean value) {
		this.poolPrefill = value;
		return this;
	}

	/**
	 * Specifies if the min-pool-size should be considered strictly
	 */
	@Binding(detypedName = "pool-use-strict-min")
	public Boolean poolUseStrictMin() {
		return this.poolUseStrictMin;
	}

	/**
	 * Specifies if the min-pool-size should be considered strictly
	 */
	public DataSource poolUseStrictMin(Boolean value) {
		this.poolUseStrictMin = value;
		return this;
	}

	/**
	 * The number of prepared statements per connection in an LRU cache
	 */
	@Binding(detypedName = "prepared-statements-cache-size")
	public Long preparedStatementsCacheSize() {
		return this.preparedStatementsCacheSize;
	}

	/**
	 * The number of prepared statements per connection in an LRU cache
	 */
	public DataSource preparedStatementsCacheSize(Long value) {
		this.preparedStatementsCacheSize = value;
		return this;
	}

	/**
	 * Any configured query timeout in seconds. If not provided no timeout will be set
	 */
	@Binding(detypedName = "query-timeout")
	public Long queryTimeout() {
		return this.queryTimeout;
	}

	/**
	 * Any configured query timeout in seconds. If not provided no timeout will be set
	 */
	public DataSource queryTimeout(Long value) {
		this.queryTimeout = value;
		return this;
	}

	/**
	 * The fully qualified class name of the reauthentication plugin implementation
	 */
	@Binding(detypedName = "reauth-plugin-class-name")
	public String reauthPluginClassName() {
		return this.reauthPluginClassName;
	}

	/**
	 * The fully qualified class name of the reauthentication plugin implementation
	 */
	public DataSource reauthPluginClassName(String value) {
		this.reauthPluginClassName = value;
		return this;
	}

	/**
	 * The properties for the reauthentication plugin
	 */
	@Binding(detypedName = "reauth-plugin-properties")
	public Map reauthPluginProperties() {
		return this.reauthPluginProperties;
	}

	/**
	 * The properties for the reauthentication plugin
	 */
	public DataSource reauthPluginProperties(Map value) {
		this.reauthPluginProperties = value;
		return this;
	}

	/**
	 * Specifies the security domain which defines the javax.security.auth.Subject that are used to distinguish connections in the pool
	 */
	@Binding(detypedName = "security-domain")
	public String securityDomain() {
		return this.securityDomain;
	}

	/**
	 * Specifies the security domain which defines the javax.security.auth.Subject that are used to distinguish connections in the pool
	 */
	public DataSource securityDomain(String value) {
		this.securityDomain = value;
		return this;
	}

	/**
	 * Whether to set the query timeout based on the time remaining until transaction timeout. Any configured query timeout will be used if there is no transaction
	 */
	@Binding(detypedName = "set-tx-query-timeout")
	public Boolean setTxQueryTimeout() {
		return this.setTxQueryTimeout;
	}

	/**
	 * Whether to set the query timeout based on the time remaining until transaction timeout. Any configured query timeout will be used if there is no transaction
	 */
	public DataSource setTxQueryTimeout(Boolean value) {
		this.setTxQueryTimeout = value;
		return this;
	}

	/**
	 * Whether to share prepared statements, i.e. whether asking for same statement twice without closing uses the same underlying prepared statement
	 */
	@Binding(detypedName = "share-prepared-statements")
	public Boolean sharePreparedStatements() {
		return this.sharePreparedStatements;
	}

	/**
	 * Whether to share prepared statements, i.e. whether asking for same statement twice without closing uses the same underlying prepared statement
	 */
	public DataSource sharePreparedStatements(Boolean value) {
		this.sharePreparedStatements = value;
		return this;
	}

	/**
	 * Enable spying of SQL statements
	 */
	@Binding(detypedName = "spy")
	public Boolean spy() {
		return this.spy;
	}

	/**
	 * Enable spying of SQL statements
	 */
	public DataSource spy(Boolean value) {
		this.spy = value;
		return this;
	}

	/**
	 * An org.jboss.jca.adapters.jdbc.StaleConnectionChecker that provides an isStaleConnection(SQLException) method which if it returns true will wrap the exception in an org.jboss.jca.adapters.jdbc.StaleConnectionException
	 */
	@Binding(detypedName = "stale-connection-checker-class-name")
	public String staleConnectionCheckerClassName() {
		return this.staleConnectionCheckerClassName;
	}

	/**
	 * An org.jboss.jca.adapters.jdbc.StaleConnectionChecker that provides an isStaleConnection(SQLException) method which if it returns true will wrap the exception in an org.jboss.jca.adapters.jdbc.StaleConnectionException
	 */
	public DataSource staleConnectionCheckerClassName(String value) {
		this.staleConnectionCheckerClassName = value;
		return this;
	}

	/**
	 * The stale connection checker properties
	 */
	@Binding(detypedName = "stale-connection-checker-properties")
	public Map staleConnectionCheckerProperties() {
		return this.staleConnectionCheckerProperties;
	}

	/**
	 * The stale connection checker properties
	 */
	public DataSource staleConnectionCheckerProperties(Map value) {
		this.staleConnectionCheckerProperties = value;
		return this;
	}

	/**
	 * define if runtime statistics is enabled or not.
	 */
	@Binding(detypedName = "statistics-enabled")
	public Boolean statisticsEnabled() {
		return this.statisticsEnabled;
	}

	/**
	 * define if runtime statistics is enabled or not.
	 */
	public DataSource statisticsEnabled(Boolean value) {
		this.statisticsEnabled = value;
		return this;
	}

	/**
	 * Whether to check for unclosed statements when a connection is returned to the pool, result sets are closed, a statement is closed or return to the prepared statement cache. Valid values are: "false" - do not track statements, "true" - track statements and result sets and warn when they are not closed, "nowarn" - track statements but do not warn about them being unclosed
	 */
	@Binding(detypedName = "track-statements")
	public String trackStatements() {
		return this.trackStatements;
	}

	/**
	 * Whether to check for unclosed statements when a connection is returned to the pool, result sets are closed, a statement is closed or return to the prepared statement cache. Valid values are: "false" - do not track statements, "true" - track statements and result sets and warn when they are not closed, "nowarn" - track statements but do not warn about them being unclosed
	 */
	public DataSource trackStatements(String value) {
		this.trackStatements = value;
		return this;
	}

	/**
	 * Defines if IronJacamar should track connection handles across transaction boundaries
	 */
	@Binding(detypedName = "tracking")
	public Boolean tracking() {
		return this.tracking;
	}

	/**
	 * Defines if IronJacamar should track connection handles across transaction boundaries
	 */
	public DataSource tracking(Boolean value) {
		this.tracking = value;
		return this;
	}

	/**
	 * Set the java.sql.Connection transaction isolation level. Valid values are: TRANSACTION_READ_UNCOMMITTED, TRANSACTION_READ_COMMITTED, TRANSACTION_REPEATABLE_READ, TRANSACTION_SERIALIZABLE and TRANSACTION_NONE
	 */
	@Binding(detypedName = "transaction-isolation")
	public String transactionIsolation() {
		return this.transactionIsolation;
	}

	/**
	 * Set the java.sql.Connection transaction isolation level. Valid values are: TRANSACTION_READ_UNCOMMITTED, TRANSACTION_READ_COMMITTED, TRANSACTION_REPEATABLE_READ, TRANSACTION_SERIALIZABLE and TRANSACTION_NONE
	 */
	public DataSource transactionIsolation(String value) {
		this.transactionIsolation = value;
		return this;
	}

	/**
	 * Specifies the delimiter for URLs in connection-url for HA datasources
	 */
	@Binding(detypedName = "url-delimiter")
	public String urlDelimiter() {
		return this.urlDelimiter;
	}

	/**
	 * Specifies the delimiter for URLs in connection-url for HA datasources
	 */
	public DataSource urlDelimiter(String value) {
		this.urlDelimiter = value;
		return this;
	}

	/**
	 * A class that implements org.jboss.jca.adapters.jdbc.URLSelectorStrategy
	 */
	@Binding(detypedName = "url-selector-strategy-class-name")
	public String urlSelectorStrategyClassName() {
		return this.urlSelectorStrategyClassName;
	}

	/**
	 * A class that implements org.jboss.jca.adapters.jdbc.URLSelectorStrategy
	 */
	public DataSource urlSelectorStrategyClassName(String value) {
		this.urlSelectorStrategyClassName = value;
		return this;
	}

	/**
	 * Enable the use of a cached connection manager
	 */
	@Binding(detypedName = "use-ccm")
	public Boolean useCcm() {
		return this.useCcm;
	}

	/**
	 * Enable the use of a cached connection manager
	 */
	public DataSource useCcm(Boolean value) {
		this.useCcm = value;
		return this;
	}

	/**
	 * Whether to fail a connection allocation on the first try if it is invalid (true) or keep trying until the pool is exhausted of all potential connections (false)
	 */
	@Binding(detypedName = "use-fast-fail")
	public Boolean useFastFail() {
		return this.useFastFail;
	}

	/**
	 * Whether to fail a connection allocation on the first try if it is invalid (true) or keep trying until the pool is exhausted of all potential connections (false)
	 */
	public DataSource useFastFail(Boolean value) {
		this.useFastFail = value;
		return this;
	}

	/**
	 * Setting this to false will bind the datasource into global JNDI
	 */
	@Binding(detypedName = "use-java-context")
	public Boolean useJavaContext() {
		return this.useJavaContext;
	}

	/**
	 * Setting this to false will bind the datasource into global JNDI
	 */
	public DataSource useJavaContext(Boolean value) {
		this.useJavaContext = value;
		return this;
	}

	/**
	 * Any configured timeout for internal locks on the resource adapter objects in seconds
	 */
	@Binding(detypedName = "use-try-lock")
	public Long useTryLock() {
		return this.useTryLock;
	}

	/**
	 * Any configured timeout for internal locks on the resource adapter objects in seconds
	 */
	public DataSource useTryLock(Long value) {
		this.useTryLock = value;
		return this;
	}

	/**
	 * Specify the user name used when creating a new connection
	 */
	@Binding(detypedName = "user-name")
	public String userName() {
		return this.userName;
	}

	/**
	 * Specify the user name used when creating a new connection
	 */
	public DataSource userName(String value) {
		this.userName = value;
		return this;
	}

	/**
	 * An org.jboss.jca.adapters.jdbc.ValidConnectionChecker that provides an isValidConnection(Connection) method to validate a connection. If an exception is returned that means the connection is invalid. This overrides the check-valid-connection-sql element
	 */
	@Binding(detypedName = "valid-connection-checker-class-name")
	public String validConnectionCheckerClassName() {
		return this.validConnectionCheckerClassName;
	}

	/**
	 * An org.jboss.jca.adapters.jdbc.ValidConnectionChecker that provides an isValidConnection(Connection) method to validate a connection. If an exception is returned that means the connection is invalid. This overrides the check-valid-connection-sql element
	 */
	public DataSource validConnectionCheckerClassName(String value) {
		this.validConnectionCheckerClassName = value;
		return this;
	}

	/**
	 * The valid connection checker properties
	 */
	@Binding(detypedName = "valid-connection-checker-properties")
	public Map validConnectionCheckerProperties() {
		return this.validConnectionCheckerProperties;
	}

	/**
	 * The valid connection checker properties
	 */
	public DataSource validConnectionCheckerProperties(Map value) {
		this.validConnectionCheckerProperties = value;
		return this;
	}

	/**
	 * The validate-on-match element specifies if connection validation should be done when a connection factory attempts to match a managed connection. This is typically exclusive to the use of background validation
	 */
	@Binding(detypedName = "validate-on-match")
	public Boolean validateOnMatch() {
		return this.validateOnMatch;
	}

	/**
	 * The validate-on-match element specifies if connection validation should be done when a connection factory attempts to match a managed connection. This is typically exclusive to the use of background validation
	 */
	public DataSource validateOnMatch(Boolean value) {
		this.validateOnMatch = value;
		return this;
	}

	public DataSourceResources subresources() {
		return this.subresources;
	}

	/**
	 * Add all ConnectionProperties objects to this subresource
	 * @return this
	 * @param value List of ConnectionProperties objects.
	 */
	public DataSource connectionProperties(List<ConnectionProperties> value) {
		this.subresources.connectionProperties.addAll(value);
		return this;
	}

	/**
	 * Add the ConnectionProperties object to the list of subresources
	 * @param value The ConnectionProperties to add
	 * @return this
	 */
	public DataSource connectionProperties(ConnectionProperties value) {
		this.subresources.connectionProperties.add(value);
		return this;
	}

	/**
	 * Child mutators for DataSource
	 */
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