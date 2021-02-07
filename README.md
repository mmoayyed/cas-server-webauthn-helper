```bash
./gradlew clean build 
```

To release:

```bash
./gradlew build publish -PpublishReleases=true -PSonatypeUsername=secret-user -PSonatypePassword=secret-password
```

To publish snapshots:

```bash
./gradlew build publish -PpublishSnapshots=true -PSonatypeUsername=secret-user -PSonatypePassword=secret-password
```