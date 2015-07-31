# Java API generator and configuration library 

This projects breaks down into two parts:

- API Generator (`generator` module)
- Configuration library (`config-lib` module)

The former provides a generator to create a Java API which represents the Wildfly management model (or parts of it) and marshalling layer to  turn the Java object model into a DMR representation and vice versa.

The configuration library contains the object model created by the generator and it intended to be used by actually clients that need to manage Wildfly configurations through the a Java API. 

## Generating the config library

The config library is created from the Wildfly management model as part of the maven build. It requires an active Wildfly instance to access the meta data and execute the integration tests.

1. Start a stock Wildfly distribution in standalone mode
2. Update the `*-config.json` to reflect your setup
3. Perform a regular maven build `mvn clean install`

If all goes well, you be able to access the generated sources at `config-lib/target/generated-sources`.

A generated config class looks like this

```
package org.wildfly.swarm.config.mail;

[...]

/**
 * Mail session definition
 */
@Address("/subsystem=mail/mail-session=*")
public class MailSession {

	private Boolean debug;
	private String from;
	private String jndiName;
	private List<Server> servers;
	private List<Custom> customs;

	/**
	 * Enables JavaMail debugging
	 */
	@Binding(detypedName = "debug")
	public Boolean getDebug() {
		return debug;
	}

	/**
	 * Enables JavaMail debugging
	 */
	public void setDebug(Boolean debug) {
		this.debug = debug;
	}

	[...]

	@Subresource
	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}

	@Subresource
	public List<Custom> getCustoms() {
		return customs;
	}

	public void setCustoms(List<Custom> customs) {
		this.customs = customs;
	}

	public MailSession() {
		this.servers = new java.util.ArrayList<>();
		this.customs = new java.util.ArrayList<>();
	}
}
```

The annotation meta data (i.e. `@Binding`) is used by the marshalling layer to drive the conversion between DMR and Java types. This will be explained further down.

## Working with the config library

### Maven dependencies

Clients typically just depend on the config library itself:

```
<dependency>
	<groupId>org.wildfly</groupId>
	<artifactId>config-lib</artifactId>
	<version>0.1.0.Final</version>
</dependency>
```

### API usage

**Java to DMR**

To generate DMR from Java simply instantiate the object model and run it through the `EntityAdapter<T>`:

``` 
DataSource dataSource = ...;
EntityAdapter<DataSource> entityAdapter = new EntityAdapter<>(DataSource.class);
ModelNode modelNode = entityAdapter.fromEntity(dataSource);

```

**DMR to Java**

Creating object instances work 
in a similar way, by using the `EntityAdapter<T>`:

```
ModelNode modelNode = ...;
EntityAdapter<DataSource> entityAdapter = new EntityAdapter<>(DataSource.class);
DataSource dataSource = entityAdapter.fromDMR(payload);
```

The `IntegrationTestCase.java` contains more examples.


## Status and limitations

This is pretty much work in it's early stages. Use cases covered by the test cases seem to work, but we didn't test plenty of scenarios. 

But the project as it is should give you an idea where the config-lib is heading and what it might be used for.


## Contributions


Feel free to create ticket right here on github, fork the code and send pull request or join us on the Wildfly IRC channels and mailing lists.





