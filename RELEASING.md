# How to Release a New Version

Releasing a new version of the project is easy, but you need to take
all of the right steps, so that everything is in the right place.

First, make sure you've got a good build.

    $ mvn clean install

Then prepare for the release. The following command will ask you lots
of questions about version numbers and whatnot. The defaults are fine.

    $ mvn release:prepare

This will create the git tags, and counter intuitively, you'll need to
push those tags upstream before continuing.

    $ git push origin master --tags

Next, publish the documentation. TODO: This step could/should be improved.

    $ mvn javadoc:javadoc
    $ mvn scm-publish:publish-scm


Then, perform the release. This pushes the build artifacts to the JBoss
Nexus sonatype repository.

  mvn release:perform

Finally, head over to JBoss Nexus website and login. https://repository.jboss.org/nexus/
Click `Staging Repositories`. Click the `Updated` tag twice to get your just
published repository at the top of the list. Select it and confirm the contents.
Click `Close`. Click `Refresh`. Click `Release`. Ridiculous, isn't it?

Congratulations, you are done.
