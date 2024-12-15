pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS) // 설정 파일에서 리포지토리 우선 사용
    repositories {
        google() // Android와 Firebase 관련 종속성 검색
        mavenCentral() // 다른 Java/Kotlin 라이브러리
    }
}

// 프로젝트 이름 설정
rootProject.name = "bookie"

// 모듈 추가
include(":app")
