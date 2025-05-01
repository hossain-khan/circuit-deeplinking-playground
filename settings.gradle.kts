pluginManagement {
  repositories {
    mavenCentral()
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    gradlePluginPortal()
    maven { 
      url = uri("https://oss.sonatype.org/content/repositories/snapshots")
      mavenContent { snapshotsOnly() }
    }
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    maven { 
      url = uri("https://oss.sonatype.org/content/repositories/snapshots")
      mavenContent { snapshotsOnly() }
    }
  }
}

rootProject.name = "Circuit DeepLinking"
include(":app")
