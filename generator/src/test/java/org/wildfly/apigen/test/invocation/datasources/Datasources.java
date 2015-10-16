package org.wildfly.apigen.test.invocation.datasources;

import org.wildfly.swarm.config.runtime.Implicit;
import org.wildfly.swarm.config.runtime.Address;
import org.wildfly.swarm.config.runtime.ModelNodeBinding;
import java.util.List;
import java.util.Map;
import org.wildfly.swarm.config.runtime.Subresource;
import org.wildfly.swarm.config.runtime.ModelNodeSubresources;
import org.wildfly.apigen.test.invocation.datasources.subsystem.jdbcDriver.JdbcDriver;
import org.wildfly.apigen.test.invocation.datasources.subsystem.xaDataSource.XaDataSource;
import org.wildfly.apigen.test.invocation.datasources.subsystem.dataSource.DataSource;
/**
 * The data-sources subsystem, used to declare JDBC data-sources
 */
@Address("/subsystem=datasources")
@Implicit
public class Datasources<T extends Datasources> {

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
	@SuppressWarnings("unchecked")
	public T installedDrivers(List<java.util.Map> value) {
		this.installedDrivers = value;
		return (T) this;
	}

	public DatasourcesResources subresources() {
		return this.subresources;
	}

	/**
	 * Add all JdbcDriver objects to this subresource
	 * @return this
	 * @param value List of JdbcDriver objects.
	 */
	@SuppressWarnings("unchecked")
	public T jdbcDrivers(List<JdbcDriver> value) {
		this.subresources.jdbcDrivers.addAll(value);
		return (T) this;
	}

	/**
	 * Add the JdbcDriver object to the list of subresources
	 * @param value The JdbcDriver to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T jdbcDriver(JdbcDriver value) {
		this.subresources.jdbcDrivers.add(value);
		return (T) this;
	}

	/**
	 * Add all XaDataSource objects to this subresource
	 * @return this
	 * @param value List of XaDataSource objects.
	 */
	@SuppressWarnings("unchecked")
	public T xaDataSources(List<XaDataSource> value) {
		this.subresources.xaDataSources.addAll(value);
		return (T) this;
	}

	/**
	 * Add the XaDataSource object to the list of subresources
	 * @param value The XaDataSource to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T xaDataSource(XaDataSource value) {
		this.subresources.xaDataSources.add(value);
		return (T) this;
	}

	/**
	 * Add all DataSource objects to this subresource
	 * @return this
	 * @param value List of DataSource objects.
	 */
	@SuppressWarnings("unchecked")
	public T dataSources(List<DataSource> value) {
		this.subresources.dataSources.addAll(value);
		return (T) this;
	}

	/**
	 * Add the DataSource object to the list of subresources
	 * @param value The DataSource to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T dataSource(DataSource value) {
		this.subresources.dataSources.add(value);
		return (T) this;
	}

	/**
	 * Child mutators for Datasources
	 */
	@ModelNodeSubresources
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