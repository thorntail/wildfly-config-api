package org.wildfly.apigen.test.invocation.datasources.subsystem.jdbcDriver;

import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.ModelNodeBinding;
/**
 * Service that make a JDBC driver available for use in the runtime
 */
@Address("/subsystem=datasources/jdbc-driver=*")
public class JdbcDriver<T extends JdbcDriver> {

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
	@SuppressWarnings("unchecked")
	public T deploymentName(String value) {
		this.deploymentName = value;
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T driverClassName(String value) {
		this.driverClassName = value;
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T driverDatasourceClassName(String value) {
		this.driverDatasourceClassName = value;
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T driverMajorVersion(Integer value) {
		this.driverMajorVersion = value;
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T driverMinorVersion(Integer value) {
		this.driverMinorVersion = value;
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T driverModuleName(String value) {
		this.driverModuleName = value;
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
	 * The fully qualified class name of the javax.sql.XADataSource implementation
	 */
	@ModelNodeBinding(detypedName = "driver-xa-datasource-class-name")
	public String driverXaDatasourceClassName() {
		return this.driverXaDatasourceClassName;
	}

	/**
	 * The fully qualified class name of the javax.sql.XADataSource implementation
	 */
	@SuppressWarnings("unchecked")
	public T driverXaDatasourceClassName(String value) {
		this.driverXaDatasourceClassName = value;
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T jdbcCompliant(Boolean value) {
		this.jdbcCompliant = value;
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T moduleSlot(String value) {
		this.moduleSlot = value;
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T profile(String value) {
		this.profile = value;
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T xaDatasourceClass(String value) {
		this.xaDatasourceClass = value;
		return (T) this;
	}
}