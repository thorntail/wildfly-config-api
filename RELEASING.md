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

Then, perform the release. This pushes the build artifacts to the JBoss
Nexus sonatype repository.

    $ mvn release:perform

Next, publish the documentation. TODO: This step could/should be improved.

    $ cd api
    $ git co $WHATEVER_VERSION_YOU_JUST_PUBLISHED
    $ mvn javadoc:javadoc
    $ mvn scm-publish:publish-scm
    $ git co master

Then you'll need to update the javadoc index page to include the latest
version that you just published. It's simple enough to do this directly
from the github interface.

    https://github.com/wildfly-swarm/wildfly-config-api/edit/gh-pages/index.html

Finally, head over to JBoss Nexus website and login. https://repository.jboss.org/nexus/
Click `Staging Repositories`. Click the `Updated` tag twice to get your just
published repository at the top of the list. Select it and confirm the contents.
Click `Close`. Click `Refresh`. Click `Release`. Ridiculous, isn't it?

Congratulations, you are done.
