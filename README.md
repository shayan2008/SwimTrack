Swim Results Dashboard (JavaFX)

A small JavaFX app that lets you enter swimmers, add their race results, and instantly see simple analytics (Top-5 bar chart, personal trend line, and event distribution pie). It’s intentionally lightweight (no database) so it runs easily in IntelliJ.

✨ What it does

Manage swimmers: add swimmers with a name and team.

Add results: pick an event, date, and time (supports SS.ss or M:SS.ss).

Filter + Search: filter by event and search by swimmer/team.

Analytics:

Top-5 Bar Chart (fastest times for the selected event)

Trend Line for the selected swimmer & event

Event Distribution Pie (count of results by event)

Quality of Life:

Pagination for results

Context menu (right-click a swimmer to add result or delete)

Tooltips on bars to show formatted times

PB label (best time) with diff vs last swim

New/uncommon GUI elements used for bonus: Pagination, ContextMenu, plus multiple Chart types and Tooltips.

🗂 Project structure
src/
  main/
    java/
      com/example/gui_optionalassignment1_doroudianishayan/
        AppLauncher.java              // main() entrypoint
        MainApplication.java          // JavaFX Application (loads FXML)
        MainController.java           // Controller for all UI logic
        model/
          Swimmer.java
          SwimEvent.java
          SwimResult.java
        store/
          DataStore.java              // in-memory data
        util/
          TimeUtil.java               // parse/format times
        module-info.java              // exports + opens for JavaFX (see below)
    resources/
      com/example/gui_optionalassignment1_doroudianishayan/
        main.fxml                     // SceneBuilder layout


No CSS is required in this simplified version (we avoided custom styles on purpose).

🧰 Requirements

JDK 21 (tested with Temurin 21)

IntelliJ IDEA (Community is fine)

JavaFX 21.0.6 (pulled via Maven dependencies in pom.xml, or add your local SDK)

If Maven plugin resolution is blocked in your environment, you can still run directly from IntelliJ as a normal Application (see below).

▶️ Running in IntelliJ (recommended)

Open the project in IntelliJ.

Set Project SDK:
File → Project Structure → Project → SDK = JDK 21.

Mark resources: IntelliJ usually detects src/main/resources automatically; if not, right-click → Mark Directory as → Resources Root.

Run configuration:

Type: Application

Main class: com.example.gui_optionalassignment1_doroudianishayan.AppLauncher

Use classpath of module: select your main module (the one with module-info.java)

That’s it. Press Run.

If you see Unsupported JavaFX configuration: classes were loaded from 'unnamed module', it’s just a warning when running on the classpath. This project includes module-info.java, so running with “Use classpath of module” will remove the warning.

🧩 module-info.java (important)

JavaFX’s PropertyValueFactory uses reflection. To avoid IllegalAccessException when reading table columns, we need to open the model package to javafx.base.

This project already includes something like:

module com.example.gui_optionalassignment1_doroudianishayan {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.gui_optionalassignment1_doroudianishayan to javafx.fxml;
    opens com.example.gui_optionalassignment1_doroudianishayan.model to javafx.base; // <-- needed for TableView

    exports com.example.gui_optionalassignment1_doroudianishayan;
}


If you move packages around, keep that opens ...model to javafx.base line updated.

🧪 How to use (quick tour)

Home: basic landing page.

Swimmers:

Type in the search box to filter the swimmer list.

Select an event from the dropdown to filter results.

Right-click a swimmer row → “Add Result…” or “Delete Swimmer”.

Analytics:

Top-5 Bar shows the fastest times for the selected event.

Trend Line shows the selected swimmer’s times over dates.

Pie Chart shows how many results you have per event.

PB label shows best time and the difference vs latest result.

Tip: Times accept 26.75 or 1:02.33. Bad formats are blocked with an error dialog.

💾 Data model (simple + clear)

Swimmer: id, name, team

SwimEvent: distance (e.g., 50/100/200), stroke (FREE/BACK/BREAST/FLY/IM)

SwimResult: swimmerId, event, timeSeconds, date, meet

All data lives in memory (DataStore). Restarting clears it—this keeps the app stable and easy to mark.

🧱 Design choices tied to the rubric

Creativity: swim-analytics mini-dashboard with three visualizations and PB logic.

Technical difficulty: multiple charts, custom tooltips, filtering, sorting, pagination, right-click context actions, modular JavaFX with package opens.

Cleanness: SceneBuilder FXML + clear layout containers; simple colors (no CSS in this version). Window switching uses a single StackPane (one scene, multiple views).

Documentation: This README explains setup, structure, and logic. Controller code has clear, short comments.

🐛 Common issues & fixes

Can’t run / “JDK isn’t specified”
Set Project SDK = JDK 21 and language level = 21.

IllegalAccessException reading table columns
Make sure module-info.java has
opens com.example.gui_optionalassignment1_doroudianishayan.model to javafx.base;

FXML load error
Ensure main.fxml is in
src/main/resources/com/example/gui_optionalassignment1_doroudianishayan/main.fxml
and the fx:controller matches
com.example.gui_optionalassignment1_doroudianishayan.MainController.

Warning: “Unsupported JavaFX configuration”
It’s harmless when running on the classpath. Use the module run config to remove it.
