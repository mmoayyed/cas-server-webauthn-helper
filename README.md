```bash
./gradlew clean build 
```

To release:

```bash
./gradlew build publish -PpublishReleases=true -PSonatypeReleasesUsername=secret-user -PSonatypeReleasesPassword=secret-password
```

To publish snapshots:

```bash
./gradlew build publish -PpublishSnapshots=true -PSonatypeSnapshotsUsername=secret-user -PSonatypeSnapshotsPassword=secret-password
```