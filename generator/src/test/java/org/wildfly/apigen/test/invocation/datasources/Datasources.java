package org.wildfly.apigen.test.invocation.datasources;

import org.wildfly.apigen.invocation.Implicit;
import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.ModelNodeBinding;
import java.util.List;
import java.util.Map;
import org.wildfly.apigen.invocation.Subresource;
import org.wildfly.apigen.test.invocation.datasources.subsystem.dataSource.DataSource;
import org.wildfly.apigen.test.invocation.datasources.subsystem.jdbcDriver.JdbcDriver;
import org.wildfly.apigen.test.invocation.datasources.subsystem.xaDataSource.XaDataSource;

/**
 * The data-sources subsystem, used to declare JDBC data-sources
 */
@Address("/subsystem=datasources")
@Implicit
public class Datasources {

	private String key;
	private List<java.util.Map> installedDrivers;
	private DatasourcesResources subresources = new DatasourcesResources();

	public Datasources() {
		this.key = "datasources";
	}

	public String getKey() {
		return this.key;
	}

	/**
	 * List of JDBC drivers that have been installed in the runtime
	 */
	@ModelNodeBinding(detypedName = "installed-drivers")
	public List<Map> installedDrivers() {
		return this.installedDrivers;
	}

	/**
	 * List of JDBC drivers that have been installed in the runtime
	 */
	public Datasources installedDrivers(List<java.util.Map> value) {
		this.installedDrivers = value;
		return this;
	}

	public DatasourcesResources subresources() {
		return this.subresources;
	}

	/**
	 * Add all JdbcDriver objects to this subresource
	 * @return this
	 * @param value List of JdbcDriver objects.
	 */
	public Datasources jdbcDrivers(List<JdbcDriver> value) {
		this.subresources.jdbcDrivers.addAll(value);
		return this;
	}

	/**
	 * Add the JdbcDriver object to the list of subresources
	 * @param value The JdbcDriver to add
	 * @return this
	 */
	public Datasources jdbcDriver(JdbcDriver value) {
		this.subresources.jdbcDrivers.add(value);
		return this;
	}

	/**
	 * Add all XaDataSource objects to this subresource
	 * @return this
	 * @param value List of XaDataSource objects.
	 */
	public Datasources xaDataSources(List<XaDataSource> value) {
		this.subresources.xaDataSources.addAll(value);
		return this;
	}

	/**
	 * Add the XaDataSource object to the list of subresources
	 * @param value The XaDataSource to add
	 * @return this
	 */
	public Datasources xaDataSource(XaDataSource value) {
		this.subresources.xaDataSources.add(value);
		return this;
	}

	/**
	 * Add all DataSource objects to this subresource
	 * @return this
	 * @param value List of DataSource objects.
	 */
	public Datasources dataSources(List<DataSource> value) {
		this.subresources.dataSources.addAll(value);
		return this;
	}

	/**
	 * Add the DataSource object to the list of subresources
	 * @param value The DataSource to add
	 * @return this
	 */
	public Datasources dataSource(DataSource value) {
		this.subresources.dataSources.add(value);
		return this;
	}

	/**
	 * Child mutators for Datasources
	 */
	public class DatasourcesResources {
		/**
		 * Service that make a JDBC driver available for use in the runtime
		 */
		private List<JdbcDriver> jdbcDrivers = new java.util.ArrayList<>();
		/**
		 * A JDBC XA data-source configuration
		 */
		private List<XaDataSource> xaDataSources = new java.util.ArrayList<>();
		/**
		 * A JDBC data-source configuration
		 */
		private List<DataSource> dataSources = new java.util.ArrayList<>();

		/**
		 * Get the list of JdbcDriver resources
		 * @return the list of resources
		 */
		@Subresource
		public List<JdbcDriver> jdbcDrivers() {
			return this.jdbcDrivers;
		}

		/**
		 * Get the list of XaDataSource resources
		 * @return the list of resources
		 */
		@Subresource
		public List<XaDataSource> xaDataSources() {
			return this.xaDataSources;
		}

		/**
		 * Get the list of DataSource resources
		 * @return the list of resources
		 */
		@Subresource
		public List<DataSource> dataSources() {
			return this.dataSources;
		}
	}
}