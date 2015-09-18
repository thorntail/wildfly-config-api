package org.wildfly.apigen.test.invocation.datasources.subsystem.xaDataSource;

import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.ModelNodeBinding;
import java.util.Map;
import java.util.List;
import org.wildfly.apigen.invocation.Subresource;
import org.wildfly.apigen.test.invocation.datasources.subsystem.xaDataSource.xaDatasourceProperties.XaDatasourceProperties;

/**
 * A JDBC XA data-source configuration
 */
@Address("/subsystem=datasources/xa-data-source=*")
public class XaDataSource {

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
	private String driverName;
	private Boolean enabled;
	private String exceptionSorterClassName;
	private Map exceptionSorterProperties;
	private String flushStrategy;
	private Long idleTimeoutMinutes;
	private Integer initialPoolSize;
	private Boolean interleaving;
	private String jndiName;
	private Integer maxPoolSize;
	private Integer minPoolSize;
	private String newConnectionSql;
	private Boolean noRecovery;
	private Boolean noTxSeparatePool;
	private Boolean padXid;
	private String password;
	private Boolean poolPrefill;
	private Boolean poolUseStrictMin;
	private Long preparedStatementsCacheSize;
	private Long queryTimeout;
	private String reauthPluginClassName;
	private Map reauthPluginProperties;
	private String recoveryPassword;
	private String recoveryPluginClassName;
	private Map recoveryPluginProperties;
	private String recoverySecurityDomain;
	private String recoveryUsername;
	private Boolean sameRmOverride;
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
	private String urlProperty;
	private String urlSelectorStrategyClassName;
	private Boolean useCcm;
	private Boolean useFastFail;
	private Boolean useJavaContext;
	private Long useTryLock;
	private String userName;
	private String validConnectionCheckerClassName;
	private Map validConnectionCheckerProperties;
	private Boolean validateOnMatch;
	private Boolean wrapXaResource;
	private String xaDatasourceClass;
	private Integer xaResourceTimeout;
	private XaDataSourceResources subresources = new XaDataSourceResources();

	public XaDataSource(String key) {
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
	public XaDataSource allocationRetry(Integer value) {
		this.allocationRetry = value;
		return this;
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
	public XaDataSource allocationRetryWaitMillis(Long value) {
		this.allocationRetryWaitMillis = value;
		return this;
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
	public XaDataSource allowMultipleUsers(Boolean value) {
		this.allowMultipleUsers = value;
		return this;
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
	public XaDataSource backgroundValidation(Boolean value) {
		this.backgroundValidation = value;
		return this;
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
	public XaDataSource backgroundValidationMillis(Long value) {
		this.backgroundValidationMillis = value;
		return this;
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
	public XaDataSource blockingTimeoutWaitMillis(Long value) {
		this.blockingTimeoutWaitMillis = value;
		return this;
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
	public XaDataSource capacityDecrementerClass(String value) {
		this.capacityDecrementerClass = value;
		return this;
	}

	/**
	 * Properties to inject in class defining the policy for decrementing connections in the pool
	 */
	@ModelNodeBinding(detypedName = "capacity-decrementer-properties")
	public Map capacityDecrementerProperties() {
		return this.capacityDecrementerProperties;
	}

	/**
	 * Properties to inject in class defining the policy for decrementing connections in the pool
	 */
	public XaDataSource capacityDecrementerProperties(Map value) {
		this.capacityDecrementerProperties = value;
		return this;
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
	public XaDataSource capacityIncrementerClass(String value) {
		this.capacityIncrementerClass = value;
		return this;
	}

	/**
	 * Properties to inject in class defining the policy for incrementing connections in the pool
	 */
	@ModelNodeBinding(detypedName = "capacity-incrementer-properties")
	public Map capacityIncrementerProperties() {
		return this.capacityIncrementerProperties;
	}

	/**
	 * Properties to inject in class defining the policy for incrementing connections in the pool
	 */
	public XaDataSource capacityIncrementerProperties(Map value) {
		this.capacityIncrementerProperties = value;
		return this;
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
	public XaDataSource checkValidConnectionSql(String value) {
		this.checkValidConnectionSql = value;
		return this;
	}

	/**
	 * Enable the use of CMR for this datasource. This feature means that a local resource can reliably participate in an XA transaction.
	 */
	@ModelNodeBinding(detypedName = "connectable")
	public Boolean connectable() {
		return this.connectable;
	}

	/**
	 * Enable the use of CMR for this datasource. This feature means that a local resource can reliably participate in an XA transaction.
	 */
	public XaDataSource connectable(Boolean value) {
		this.connectable = value;
		return this;
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
	public XaDataSource connectionListenerClass(String value) {
		this.connectionListenerClass = value;
		return this;
	}

	/**
	 * Properties to be injected in class specified in connection-listener-class
	 */
	@ModelNodeBinding(detypedName = "connection-listener-property")
	public Map connectionListenerProperty() {
		return this.connectionListenerProperty;
	}

	/**
	 * Properties to be injected in class specified in connection-listener-class
	 */
	public XaDataSource connectionListenerProperty(Map value) {
		this.connectionListenerProperty = value;
		return this;
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
	public XaDataSource driverName(String value) {
		this.driverName = value;
		return this;
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
	public XaDataSource enabled(Boolean value) {
		this.enabled = value;
		return this;
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
	public XaDataSource exceptionSorterClassName(String value) {
		this.exceptionSorterClassName = value;
		return this;
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
	public XaDataSource exceptionSorterProperties(Map value) {
		this.exceptionSorterProperties = value;
		return this;
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
	public XaDataSource flushStrategy(String value) {
		this.flushStrategy = value;
		return this;
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
	public XaDataSource idleTimeoutMinutes(Long value) {
		this.idleTimeoutMinutes = value;
		return this;
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
	public XaDataSource initialPoolSize(Integer value) {
		this.initialPoolSize = value;
		return this;
	}

	/**
	 * An element to enable interleaving for XA connections
	 */
	@ModelNodeBinding(detypedName = "interleaving")
	public Boolean interleaving() {
		return this.interleaving;
	}

	/**
	 * An element to enable interleaving for XA connections
	 */
	public XaDataSource interleaving(Boolean value) {
		this.interleaving = value;
		return this;
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
	public XaDataSource jndiName(String value) {
		this.jndiName = value;
		return this;
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
	public XaDataSource maxPoolSize(Integer value) {
		this.maxPoolSize = value;
		return this;
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
	public XaDataSource minPoolSize(Integer value) {
		this.minPoolSize = value;
		return this;
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
	public XaDataSource newConnectionSql(String value) {
		this.newConnectionSql = value;
		return this;
	}

	/**
	 * Specifies if the connection pool should be excluded from recovery
	 */
	@ModelNodeBinding(detypedName = "no-recovery")
	public Boolean noRecovery() {
		return this.noRecovery;
	}

	/**
	 * Specifies if the connection pool should be excluded from recovery
	 */
	public XaDataSource noRecovery(Boolean value) {
		this.noRecovery = value;
		return this;
	}

	/**
	 * Oracle does not like XA connections getting used both inside and outside a JTA transaction. To workaround the problem you can create separate sub-pools for the different contexts
	 */
	@ModelNodeBinding(detypedName = "no-tx-separate-pool")
	public Boolean noTxSeparatePool() {
		return this.noTxSeparatePool;
	}

	/**
	 * Oracle does not like XA connections getting used both inside and outside a JTA transaction. To workaround the problem you can create separate sub-pools for the different contexts
	 */
	public XaDataSource noTxSeparatePool(Boolean value) {
		this.noTxSeparatePool = value;
		return this;
	}

	/**
	 * Should the Xid be padded
	 */
	@ModelNodeBinding(detypedName = "pad-xid")
	public Boolean padXid() {
		return this.padXid;
	}

	/**
	 * Should the Xid be padded
	 */
	public XaDataSource padXid(Boolean value) {
		this.padXid = value;
		return this;
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
	public XaDataSource password(String value) {
		this.password = value;
		return this;
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
	public XaDataSource poolPrefill(Boolean value) {
		this.poolPrefill = value;
		return this;
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
	public XaDataSource poolUseStrictMin(Boolean value) {
		this.poolUseStrictMin = value;
		return this;
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
	public XaDataSource preparedStatementsCacheSize(Long value) {
		this.preparedStatementsCacheSize = value;
		return this;
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
	public XaDataSource queryTimeout(Long value) {
		this.queryTimeout = value;
		return this;
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
	public XaDataSource reauthPluginClassName(String value) {
		this.reauthPluginClassName = value;
		return this;
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
	public XaDataSource reauthPluginProperties(Map value) {
		this.reauthPluginProperties = value;
		return this;
	}

	/**
	 * The password used for recovery
	 */
	@ModelNodeBinding(detypedName = "recovery-password")
	public String recoveryPassword() {
		return this.recoveryPassword;
	}

	/**
	 * The password used for recovery
	 */
	public XaDataSource recoveryPassword(String value) {
		this.recoveryPassword = value;
		return this;
	}

	/**
	 * The fully qualified class name of the recovery plugin implementation
	 */
	@ModelNodeBinding(detypedName = "recovery-plugin-class-name")
	public String recoveryPluginClassName() {
		return this.recoveryPluginClassName;
	}

	/**
	 * The fully qualified class name of the recovery plugin implementation
	 */
	public XaDataSource recoveryPluginClassName(String value) {
		this.recoveryPluginClassName = value;
		return this;
	}

	/**
	 * The properties for the recovery plugin
	 */
	@ModelNodeBinding(detypedName = "recovery-plugin-properties")
	public Map recoveryPluginProperties() {
		return this.recoveryPluginProperties;
	}

	/**
	 * The properties for the recovery plugin
	 */
	public XaDataSource recoveryPluginProperties(Map value) {
		this.recoveryPluginProperties = value;
		return this;
	}

	/**
	 * The security domain used for recovery
	 */
	@ModelNodeBinding(detypedName = "recovery-security-domain")
	public String recoverySecurityDomain() {
		return this.recoverySecurityDomain;
	}

	/**
	 * The security domain used for recovery
	 */
	public XaDataSource recoverySecurityDomain(String value) {
		this.recoverySecurityDomain = value;
		return this;
	}

	/**
	 * The user name used for recovery
	 */
	@ModelNodeBinding(detypedName = "recovery-username")
	public String recoveryUsername() {
		return this.recoveryUsername;
	}

	/**
	 * The user name used for recovery
	 */
	public XaDataSource recoveryUsername(String value) {
		this.recoveryUsername = value;
		return this;
	}

	/**
	 * The is-same-rm-override element allows one to unconditionally set whether the javax.transaction.xa.XAResource.isSameRM(XAResource) returns true or false
	 */
	@ModelNodeBinding(detypedName = "same-rm-override")
	public Boolean sameRmOverride() {
		return this.sameRmOverride;
	}

	/**
	 * The is-same-rm-override element allows one to unconditionally set whether the javax.transaction.xa.XAResource.isSameRM(XAResource) returns true or false
	 */
	public XaDataSource sameRmOverride(Boolean value) {
		this.sameRmOverride = value;
		return this;
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
	public XaDataSource securityDomain(String value) {
		this.securityDomain = value;
		return this;
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
	public XaDataSource setTxQueryTimeout(Boolean value) {
		this.setTxQueryTimeout = value;
		return this;
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
	public XaDataSource sharePreparedStatements(Boolean value) {
		this.sharePreparedStatements = value;
		return this;
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
	public XaDataSource spy(Boolean value) {
		this.spy = value;
		return this;
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
	public XaDataSource staleConnectionCheckerClassName(String value) {
		this.staleConnectionCheckerClassName = value;
		return this;
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
	public XaDataSource staleConnectionCheckerProperties(Map value) {
		this.staleConnectionCheckerProperties = value;
		return this;
	}

	/**
	 * define if runtime statistics is enabled or not
	 */
	@ModelNodeBinding(detypedName = "statistics-enabled")
	public Boolean statisticsEnabled() {
		return this.statisticsEnabled;
	}

	/**
	 * define if runtime statistics is enabled or not
	 */
	public XaDataSource statisticsEnabled(Boolean value) {
		this.statisticsEnabled = value;
		return this;
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
	public XaDataSource trackStatements(String value) {
		this.trackStatements = value;
		return this;
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
	public XaDataSource tracking(Boolean value) {
		this.tracking = value;
		return this;
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
	public XaDataSource transactionIsolation(String value) {
		this.transactionIsolation = value;
		return this;
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
	public XaDataSource urlDelimiter(String value) {
		this.urlDelimiter = value;
		return this;
	}

	/**
	 * Specifies the property for the URL property in the xa-datasource-property values
	 */
	@ModelNodeBinding(detypedName = "url-property")
	public String urlProperty() {
		return this.urlProperty;
	}

	/**
	 * Specifies the property for the URL property in the xa-datasource-property values
	 */
	public XaDataSource urlProperty(String value) {
		this.urlProperty = value;
		return this;
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
	public XaDataSource urlSelectorStrategyClassName(String value) {
		this.urlSelectorStrategyClassName = value;
		return this;
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
	public XaDataSource useCcm(Boolean value) {
		this.useCcm = value;
		return this;
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
	public XaDataSource useFastFail(Boolean value) {
		this.useFastFail = value;
		return this;
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
	public XaDataSource useJavaContext(Boolean value) {
		this.useJavaContext = value;
		return this;
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
	public XaDataSource useTryLock(Long value) {
		this.useTryLock = value;
		return this;
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
	public XaDataSource userName(String value) {
		this.userName = value;
		return this;
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
	public XaDataSource validConnectionCheckerClassName(String value) {
		this.validConnectionCheckerClassName = value;
		return this;
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
	public XaDataSource validConnectionCheckerProperties(Map value) {
		this.validConnectionCheckerProperties = value;
		return this;
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
	public XaDataSource validateOnMatch(Boolean value) {
		this.validateOnMatch = value;
		return this;
	}

	/**
	 * Should the XAResource instances be wrapped in an org.jboss.tm.XAResourceWrapper instance
	 */
	@ModelNodeBinding(detypedName = "wrap-xa-resource")
	public Boolean wrapXaResource() {
		return this.wrapXaResource;
	}

	/**
	 * Should the XAResource instances be wrapped in an org.jboss.tm.XAResourceWrapper instance
	 */
	public XaDataSource wrapXaResource(Boolean value) {
		this.wrapXaResource = value;
		return this;
	}

	/**
	 * The fully qualified name of the javax.sql.XADataSource implementation
	 */
	@ModelNodeBinding(detypedName = "xa-datasource-class")
	public String xaDatasourceClass() {
		return this.xaDatasourceClass;
	}

	/**
	 * The fully qualified name of the javax.sql.XADataSource implementation
	 */
	public XaDataSource xaDatasourceClass(String value) {
		this.xaDatasourceClass = value;
		return this;
	}

	/**
	 * The value is passed to XAResource.setTransactionTimeout(), in seconds. Default is zero
	 */
	@ModelNodeBinding(detypedName = "xa-resource-timeout")
	public Integer xaResourceTimeout() {
		return this.xaResourceTimeout;
	}

	/**
	 * The value is passed to XAResource.setTransactionTimeout(), in seconds. Default is zero
	 */
	public XaDataSource xaResourceTimeout(Integer value) {
		this.xaResourceTimeout = value;
		return this;
	}

	public XaDataSourceResources subresources() {
		return this.subresources;
	}

	/**
	 * Add all XaDatasourceProperties objects to this subresource
	 * @return this
	 * @param value List of XaDatasourceProperties objects.
	 */
	public XaDataSource xaDatasourceProperties(
			List<XaDatasourceProperties> value) {
		this.subresources.xaDatasourceProperties.addAll(value);
		return this;
	}

	/**
	 * Add the XaDatasourceProperties object to the list of subresources
	 * @param value The XaDatasourceProperties to add
	 * @return this
	 */
	public XaDataSource xaDatasourceProperties(XaDatasourceProperties value) {
		this.subresources.xaDatasourceProperties.add(value);
		return this;
	}

	/**
	 * Child mutators for XaDataSource
	 */
	public class XaDataSourceResources {
		/**
		 * List of xa-datasource-property
		 */
		private List<XaDatasourceProperties> xaDatasourceProperties = new java.util.ArrayList<>();

		/**
		 * Get the list of XaDatasourceProperties resources
		 * @return the list of resources
		 */
		@Subresource
		public List<XaDatasourceProperties> xaDatasourceProperties() {
			return this.xaDatasourceProperties;
		}
	}
}