import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2023.05"

project {

    vcsRoot(HttpsGithubComruzina-0607ExampleTeamcityRefsHeadsMaster)

    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    artifactRules = "+:target/*.jar"
    publishArtifacts = PublishMode.SUCCESSFUL

    vcs {
        root(HttpsGithubComruzina-0607ExampleTeamcityRefsHeadsMaster)
    }

    steps {
        maven {
            name = "clean test"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE

            conditions {
                doesNotContain("teamcity.build.branch", "master")
            }
            goals = "clean test"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
            userSettingsSelection = "settings.xml"
        }
        maven {
            name = "clean deploy"

            conditions {
                contains("teamcity.build.branch", "master")
            }
            goals = "clean deploy"
            userSettingsSelection = "settings.xml"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object HttpsGithubComruzina-0607ExampleTeamcityRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/ruzina-0607/example-teamcity#refs/heads/master"
    url = "https://github.com/ruzina-0607/example-teamcity"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = password {
        userName = "ruzina-0607"
        password = "sdf2ea09397475b7eb534t32d67b91649ae72ba46a4d5fe72ba"
    }
})
