```bash
./gradlew clean build 
```

To release:

```bash
./gradlew publish -PpublishReleases=true -PSonatypeReleasesUsername=secret-user -PSonatypeReleasesPassword=secret-password
```

To publish snapshots:

```bash
./gradlew publish -PpublishSnapshots=true -PSonatypeReleasesUsername=secret-user -PSonatypeReleasesPassword=secret-password
```