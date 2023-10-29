# STT - Speech-to-Text

A Spring boot service to transcribe audio files and generate text.
This solution is based on **Google Cloud Speech-to-Text** service.

### Prerequisites

- Google Cloud Service account key must be generated and configured as environment variable.
  ```
  export GOOGLE_APPLICATION_CREDENTIALS=/path/to/credentials
  ```
  Detailed instructions: https://cloud.google.com/speech-to-text/docs/before-you-begin
- Create a Speech Recognizer and update the google cloud project id and recognizer in Service Class.
  `com.wedaa.stt.web.rest.SpeechToTextV2`

### Get Started
Start the application with the following command:
```
./mvnw
```

Sample API based on Cloud Speech-to-Text V1 API
```
curl --location 'http://localhost:5501/api/stt/short'
```
Sample API based on Cloud Speech-to-Text V2 API
```
curl --location 'http://localhost:5501/api/stt-v2/short' \
--header 'Content-Type: application/json' \
--data '/path/to/audio/file'
```

Note: Google Cloud Speech-to-Text Service can process audio files upto 60 seconds or 10MB synchronously. Larger files have to be processed asynchronously.

References:

V1 documentation:  https://cloud.google.com/speech-to-text/docs/sync-recognize

V2 documentation: https://cloud.google.com/speech-to-text/v2/docs/transcribe-client-libraries


This application was generated using WeDAA 1.0.0, you can find documentation and help at [https://www.wedaa.tech/docs/guide/service-node](https://www.wedaa.tech/docs/guide/service-node).

## Project Structure

Node is required for generation and recommended for development. `package.json` is always generated for a better development experience with prettier, commit hooks, scripts and so on.

In the project root, JHipster generates configuration files for tools like git, prettier, eslint, husky, and others that are well known and you can find references in the web.

`/src/*` structure follows default Java structure.

- `.yo-rc.json` - Yeoman configuration file
  JHipster configuration is stored in this file at `generator-jhipster` key. You may find `generator-jhipster-*` for specific blueprints configuration.
- `.yo-resolve` (optional) - Yeoman conflict resolver
  Allows to use a specific action when conflicts are found skipping prompts for files that matches a pattern. Each line should match `[pattern] [action]` with pattern been a [Minimatch](https://github.com/isaacs/minimatch#minimatch) pattern and action been one of skip (default if ommited) or force. Lines starting with `#` are considered comments and are ignored.
- `.jhipster/*.json` - JHipster entity configuration files
- `/src/main/docker` - Docker configurations for the application and services that the application depends on

## Development

To start your application in the dev profile, run:

```
./mvnw
```

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

### JHipster Control Center

JHipster Control Center can help you manage and control your application(s). You can start a local control center server (accessible on http://localhost:7419) with:

```
docker compose -f src/main/docker/jhipster-control-center.yml up
```

## Building for production

### Packaging as jar

To build the final jar and optimize the stt application for production, run:

```
./mvnw -Pprod clean verify
```

To ensure everything worked, run:

```
java -jar target/*.jar
```

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```
./mvnw -Pprod,war clean verify
```

## Testing

To launch your application's tests, run:

```
./mvnw verify
```

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker compose -f src/main/docker/sonar.yml up -d
```

Note: we have turned off forced authentication redirect for UI in [src/main/docker/sonar.yml](src/main/docker/sonar.yml) for out of the box experience while trying out SonarQube, for real use cases turn it back on.

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin.

Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar -Dsonar.login=admin -Dsonar.password=admin
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar -Dsonar.login=admin -Dsonar.password=admin
```

Additionally, Instead of passing `sonar.password` and `sonar.login` as CLI arguments, these parameters can be configured from [sonar-project.properties](sonar-project.properties) as shown below:

```
sonar.login=admin
sonar.password=admin
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
npm run java:docker
```

Or build a arm64 docker image when using an arm64 processor os like MacOS with M1 processor family running:

```
npm run java:docker:arm64
```

Then run:

```
docker compose -f src/main/docker/app.yml up -d
```

When running Docker Desktop on MacOS Big Sur or later, consider enabling experimental `Use the new Virtualization framework` for better processing performance ([disk access performance is worse](https://github.com/docker/roadmap/issues/7)).

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[JHipster Homepage and latest documentation]: https://www.jhipster.tech
[JHipster 7.10.0 archive]: https://www.jhipster.tech/documentation-archive/v7.10.0
[Doing microservices with JHipster]: https://www.jhipster.tech/documentation-archive/v7.10.0/microservices-architecture/
[Using JHipster in development]: https://www.jhipster.tech/documentation-archive/v7.10.0/development/
[Using Docker and Docker-Compose]: https://www.jhipster.tech/documentation-archive/v7.10.0/docker-compose
[Using JHipster in production]: https://www.jhipster.tech/documentation-archive/v7.10.0/production/
[Running tests page]: https://www.jhipster.tech/documentation-archive/v7.10.0/running-tests/
[Code quality page]: https://www.jhipster.tech/documentation-archive/v7.10.0/code-quality/
[Setting up Continuous Integration]: https://www.jhipster.tech/documentation-archive/v7.10.0/setting-up-ci/
[Node.js]: https://nodejs.org/
[NPM]: https://www.npmjs.com/
