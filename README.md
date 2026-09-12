# SwimTrack

Desktop application for entering swimmers and race results and turning them into something a coach can actually read: personal bests, trends over a season, and comparisons across a squad.

Written in Java with a JavaFX interface, built with Maven. This is the tool I built before [LaneLab](https://lanelab.studio), from the coaching side of the problem rather than the athlete side.

## What it does

- Enter swimmers and race results, or import them from CSV
- Track personal bests per event and course
- Chart progression over time
- Compare swimmers across an event
- Export results back out to CSV

## Repository layout

| Path | Contents |
| --- | --- |
| `Java code/` | Application source |
| `GUI/` | JavaFX interface |
| `pom.xml` | Maven build |
| `mvnw`, `mvnw.cmd` | Maven wrapper |

## Building and running

```bash
./mvnw clean javafx:run
```

On Windows use `mvnw.cmd`. Java 17 or newer and Maven are required, though the wrapper handles Maven if you do not have it installed.

Maintained by [Shayan Doroudiani](https://github.com/shayan2008).
