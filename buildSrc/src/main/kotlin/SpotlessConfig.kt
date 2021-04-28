import com.diffplug.spotless.extra.wtp.EclipseWtpFormatterStep
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

fun Project.configureSpotless() {
  apply(plugin = Plugins.BuildPlugins.spotlessPlugin)
  configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
      target("**/*.kt")
      targetExclude("**/build/")
      ktlint().userData(mapOf("indent_size" to "2", "continuation_indent_size" to "2"))
    }
    kotlinGradle {
      target("*.gradle.kts")
      ktlint().userData(mapOf("indent_size" to "2", "continuation_indent_size" to "2"))
    }
    format("xml") {
      target("**/*.xml")
      targetExclude("**/build/", ".idea/")
      eclipseWtp(EclipseWtpFormatterStep.XML).configFile("$rootDir/config/spotless.xml.prefs")
    }
  }
}
