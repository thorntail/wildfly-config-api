package org.wildfly.apigen.test.invocation.datasources.subsystem.jdbcDriver;

import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.ModelNodeBinding;
/**
 * Service that make a JDBC driver available for use in the runtime
 */
@Address("/subsystem=datasources/jdbc-driver=*")
public class JdbcDriver {

	private String key;
	private String deploymentName;
	private String driverClassName;
	private String driverDatasourceClassName;
	private Integer driverMajorVersion;
	private Integer driverMinorVersion;
	private String driverModuleName;
	private String driverName;
	private String driverXaDatasourceClassName;
	private Boolean jdbcCompliant;
	private String moduleSlot;
	private String profile;
	private String xaDatasourceClass;

	public JdbcDriver(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	/**
	 * The name of the deployment unit from which the driver was loaded
	 */
	@ModelNodeBinding(detypedName = "deployment-name")
	public String deploymentName() {
		return this.deploymentName;
	}

	/**
	 * The name of the deployment unit from which the driver was loaded
	 */
	public JdbcDriver deploymentName(String value) {
		this.deploymentName = value;
		return this;
	}

	/**
	 * The fully qualified class name of the java.sql.Driver implementation
	 */
	@ModelNodeBinding(detypedName = "driver-class-name")
	public String driverClassName() {
		return this.driverClassName;
	}

	/**
	 * The fully qualified class name of the java.sql.Driver implementation
	 */
	public JdbcDriver driverClassName(String value) {
		this.driverClassName = value;
		return this;
	}

	/**
	 * The fully qualified class name of the javax.sql.DataSource implementation
	 */
	@ModelNodeBinding(detypedName = "driver-datasource-class-name")
	public String driverDatasourceClassName() {
		return this.driverDatasourceClassName;
	}

	/**
	 * The fully qualified class name of the javax.sql.DataSource implementation
	 */
	public JdbcDriver driverDatasourceClassName(String value) {
		this.driverDatasourceClassName = value;
		return this;
	}

	/**
	 * The driver's major version number
	 */
	@ModelNodeBinding(detypedName = "driver-major-version")
	public Integer driverMajorVersion() {
		return this.driverMajorVersion;
	}

	/**
	 * The driver's major version number
	 */
	public JdbcDriver driverMajorVersion(Integer value) {
		this.driverMajorVersion = value;
		return this;
	}

	/**
	 * The driver's minor version number
	 */
	@ModelNodeBinding(detypedName = "driver-minor-version")
	public Integer driverMinorVersion() {
		return this.driverMinorVersion;
	}

	/**
	 * The driver's minor version number
	 */
	public JdbcDriver driverMinorVersion(Integer value) {
		this.driverMinorVersion = value;
		return this;
	}

	/**
	 * The name of the module from which the driver was loaded, if it was loaded from the module path
	 */
	@ModelNodeBinding(detypedName = "driver-module-name")
	public String driverModuleName() {
		return this.driverModuleName;
	}

	/**
	 * The name of the module from which the driver was loaded, if it was loaded from the module path
	 */
	public JdbcDriver driverModuleName(String value) {
		this.driverModuleName = value;
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
	public JdbcDriver driverName(String value) {
		this.driverName = value;
		return this;
	}

	/**
	 * The fully qualified class name of the javax.sql.XADataSource implementation
	 */
	@ModelNodeBinding(detypedName = "driver-xa-datasource-class-name")
	public String driverXaDatasourceClassName() {
		return this.driverXaDatasourceClassName;
	}

	/**
	 * The fully qualified class name of the javax.sql.XADataSource implementation
	 */
	public JdbcDriver driverXaDatasourceClassName(String value) {
		this.driverXaDatasourceClassName = value;
		return this;
	}

	/**
	 * Whether or not the driver is JDBC compliant
	 */
	@ModelNodeBinding(detypedName = "jdbc-compliant")
	public Boolean jdbcCompliant() {
		return this.jdbcCompliant;
	}

	/**
	 * Whether or not the driver is JDBC compliant
	 */
	public JdbcDriver jdbcCompliant(Boolean value) {
		this.jdbcCompliant = value;
		return this;
	}

	/**
	 * The slot of the module from which the driver was loaded, if it was loaded from the module path
	 */
	@ModelNodeBinding(detypedName = "module-slot")
	public String moduleSlot() {
		return this.moduleSlot;
	}

	/**
	 * The slot of the module from which the driver was loaded, if it was loaded from the module path
	 */
	public JdbcDriver moduleSlot(String value) {
		this.moduleSlot = value;
		return this;
	}

	/**
	 * Domain Profile in which driver is defined. Null in case of standalone server
	 */
	@ModelNodeBinding(detypedName = "profile")
	public String profile() {
		return this.profile;
	}

	/**
	 * Domain Profile in which driver is defined. Null in case of standalone server
	 */
	public JdbcDriver profile(String value) {
		this.profile = value;
		return this;
	}

	/**
	 * XA datasource class
	 */
	@ModelNodeBinding(detypedName = "xa-datasource-class")
	public String xaDatasourceClass() {
		return this.xaDatasourceClass;
	}

	/**
	 * XA datasource class
	 */
	public JdbcDriver xaDatasourceClass(String value) {
		this.xaDatasourceClass = value;
		return this;
	}
}